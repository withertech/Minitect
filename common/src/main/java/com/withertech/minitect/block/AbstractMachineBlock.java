/*
 * Minitect
 * Copyright (C) 2022 WitherTech
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.withertech.minitect.block;

import com.google.common.collect.Maps;
import com.withertech.mine_flux.api.IMFStorage;
import com.withertech.mine_flux.util.EnergyUtil;
import com.withertech.minitect.item.MachineBlockItem;
import com.withertech.minitect.tile.AbstractMachineTile;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMachineBlock extends BaseEntityBlock implements WorldlyContainerHolder
{
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final EnumProperty<Connection> RIGHT = EnumProperty.create("right", Connection.class);
	public static final EnumProperty<Connection> BACK = EnumProperty.create("back", Connection.class);
	public static final EnumProperty<Connection> LEFT = EnumProperty.create("left", Connection.class);
	public static final EnumProperty<Connection> DOWN = EnumProperty.create("down", Connection.class);
	public static final EnumProperty<Connection> UP = EnumProperty.create("up", Connection.class);
	public static final Map<Face, EnumProperty<Connection>> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Face.class), (directions) ->
	{
		directions.put(Face.RIGHT, RIGHT);
		directions.put(Face.BACK, BACK);
		directions.put(Face.LEFT, LEFT);
		directions.put(Face.DOWN, DOWN);
		directions.put(Face.UP, UP);
	});

	protected AbstractMachineBlock(Properties properties)
	{
		super(properties);
		registerDefaultState(defaultBlockState()
				.setValue(FACING, Direction.NORTH)
				.setValue(RIGHT, Connection.NONE)
				.setValue(BACK, Connection.NONE)
				.setValue(LEFT, Connection.NONE)
				.setValue(DOWN, Connection.NONE)
				.setValue(UP, Connection.NONE));
	}

	protected static <T> T findNextInCollection(Collection<T> collection, T value)
	{
		Iterator<T> iterator = collection.iterator();

		do
		{
			if (!iterator.hasNext())
			{
				return iterator.next();
			}
		} while (!iterator.next().equals(value));

		if (iterator.hasNext())
		{
			return iterator.next();
		} else
		{
			return collection.iterator().next();
		}
	}

	public abstract Set<Connection> getValidConnections();

	public abstract boolean isTicker();

	@Override
	public RenderShape getRenderShape(BlockState blockState)
	{
		return RenderShape.MODEL;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext)
	{
		return !blockPlaceContext.getItemInHand().getOrCreateTag().contains("State") ? super.getStateForPlacement(blockPlaceContext).setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()) : NbtUtils.readBlockState(blockPlaceContext.getItemInHand().getOrCreateTag().getCompound("State")).setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity tileEntity, ItemStack itemStack)
	{
		if (!level.isClientSide())
		{
			ItemStack drop = new ItemStack(this);
			if (tileEntity instanceof AbstractMachineTile<?, ?> tile)
			{
				drop.addTagElement("State", NbtUtils.writeBlockState(blockState));
				drop.addTagElement("Energy", IntTag.valueOf(tile.getStorageFor(null).map(IMFStorage::getEnergyStored).orElse(0)));
				drop.addTagElement("Inventory", ((SimpleContainer) tile.getContainer(blockState, level, blockPos)).createTag());
				drop.addTagElement("Upgrades", tile.getUpgrades().createTag());
			}
			popResource(level, blockPos, drop);
		}
	}

	@Override
	public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack)
	{
		if (itemStack.getItem() instanceof MachineBlockItem)
		{
			if (level.getBlockEntity(blockPos) instanceof AbstractMachineTile<?, ?> tileEntity)
			{
				EnergyUtil.getEnergyStorage(itemStack).ifPresent(itemStorage ->
						EnergyUtil.getEnergyStorage(tileEntity).ifPresent(tileStorage ->
								tileStorage.deserializeNBT(itemStorage.serializeNBT())));
				((SimpleContainer) tileEntity.getContainer(blockState, level, blockPos)).fromTag(itemStack.getOrCreateTag().getList("Inventory", Tag.TAG_COMPOUND));
				tileEntity.getUpgrades().fromTag(itemStack.getOrCreateTag().getList("Upgrades", Tag.TAG_COMPOUND));
			}

		}
	}

	public void cyclePort(Level level, BlockPos pos, Face face)
	{
		BlockState state = level.getBlockState(pos);
		level.setBlockAndUpdate(pos, state.setValue(FACING_TO_PROPERTY_MAP.get(face), findNextInCollection(getValidConnections(), state.getValue(FACING_TO_PROPERTY_MAP.get(face)))));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
	{
		super.createBlockStateDefinition(builder);
		builder.add(FACING, RIGHT, BACK, LEFT, DOWN, UP);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
	{
		if (isTicker())
			return level.isClientSide ? null : AbstractMachineTile::tick;
		return null;
	}
}
