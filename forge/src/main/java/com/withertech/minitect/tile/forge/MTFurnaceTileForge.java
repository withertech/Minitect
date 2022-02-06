package com.withertech.minitect.tile.forge;

import com.withertech.minitect.block.AbstractMachineBlock;
import com.withertech.minitect.block.Connection;
import com.withertech.minitect.block.Face;
import com.withertech.minitect.block.MTFurnaceBlock;
import com.withertech.minitect.tile.MTFurnaceTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MTFurnaceTileForge extends MTFurnaceTile
{
	public MTFurnaceTileForge(BlockPos blockPos, BlockState blockState)
	{
		super(blockPos, blockState);
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (side == null) return LazyOptional.of(() -> new SidedInvWrapper(inventory, null)).cast();
			final Connection connection = getConnectionFromDirection(side);
			if (connection.TYPE.equals(Connection.Type.ITEM) && connection.MODE != Connection.Mode.NONE)
			{
				return LazyOptional.of(() -> new SidedInvWrapper(inventory, side)).cast();
			}
		}
		if (cap == CapabilityEnergy.ENERGY)
		{
			if (side == null) return getStorageFor(null).map(imfStorage -> LazyOptional.of(() -> imfStorage)).orElse(LazyOptional.empty()).cast();
			final Connection connection = getConnectionFromDirection(side);
			if (connection.TYPE.equals(Connection.Type.ENERGY) && connection.MODE != Connection.Mode.NONE)
			{
				return getStorageFor(side).map(imfStorage -> LazyOptional.of(() -> imfStorage)).orElse(LazyOptional.empty()).cast();
			}
		}

		return super.getCapability(cap, side);
	}
}
