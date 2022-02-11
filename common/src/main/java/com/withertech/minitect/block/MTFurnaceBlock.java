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

import com.withertech.minitect.registry.MineTiles;
import com.withertech.minitect.tile.MTFurnaceTile;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public abstract class MTFurnaceBlock extends AbstractMachineBlock
{
	public MTFurnaceBlock()
	{
		super(Properties.of(Material.METAL));
	}

	@Override
	public WorldlyContainer getContainer(BlockState blockState, @NotNull LevelAccessor levelAccessor, BlockPos blockPos)
	{
		return levelAccessor.getBlockEntity(blockPos, MineTiles.FURNACE.get()).map(tile -> tile.getContainer(blockState, levelAccessor, blockPos)).orElseThrow();
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return MineTiles.FURNACE.get().create(pos, state);
	}

	@Override
	public Set<Connection> getValidConnections()
	{
		return Set.of(
				Connection.NONE,
				Connection.ENERGY_IN,
				Connection.ITEM_IN,
				Connection.ITEM_OUT
		);
	}

	@Override
	public boolean isTicker()
	{
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
	{
		if (!level.isClientSide())
			MenuRegistry.openExtendedMenu((ServerPlayer) player, blockState.getMenuProvider(level, blockPos), friendlyByteBuf -> friendlyByteBuf.writeComponent(level.getBlockEntity(blockPos, MineTiles.FURNACE.get()).map(MTFurnaceTile::getDisplayName).orElse(new TextComponent("null"))));
		return InteractionResult.SUCCESS;
	}
}
