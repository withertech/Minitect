package com.withertech.minitect.block;

import com.withertech.mine_gui.example.tile.TestTile;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
