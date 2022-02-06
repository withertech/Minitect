package com.withertech.minitect.block;

import com.withertech.minitect.registry.MineTiles;
import com.withertech.minitect.tile.MTCrusherTile;
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
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public abstract class MTCrusherBlock extends AbstractMachineBlock
{
	protected MTCrusherBlock()
	{
		super(Properties.of(Material.METAL));
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

	@Override
	public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos)
	{
		return level.getBlockEntity(pos, MineTiles.CRUSHER.get()).map(tile -> tile.getContainer(state, level, pos)).orElseThrow();
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return MineTiles.CRUSHER.get().create(pos, state);
	}
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
	{
		if (!level.isClientSide())
			MenuRegistry.openExtendedMenu((ServerPlayer) player, blockState.getMenuProvider(level, blockPos), friendlyByteBuf -> friendlyByteBuf.writeComponent(level.getBlockEntity(blockPos, MineTiles.CRUSHER.get()).map(MTCrusherTile::getDisplayName).orElse(new TextComponent("null"))));
		return InteractionResult.SUCCESS;
	}

}
