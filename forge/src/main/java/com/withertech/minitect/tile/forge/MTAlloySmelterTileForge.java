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

package com.withertech.minitect.tile.forge;

import com.withertech.minitect.block.Connection;
import com.withertech.minitect.tile.MTAlloySmelterTile;
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

public class MTAlloySmelterTileForge extends MTAlloySmelterTile
{
	public MTAlloySmelterTileForge(BlockPos blockPos, BlockState blockState)
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
			if (side == null)
				return getStorageFor(null).map(imfStorage -> LazyOptional.of(() -> imfStorage)).orElse(LazyOptional.empty()).cast();
			final Connection connection = getConnectionFromDirection(side);
			if (connection.TYPE.equals(Connection.Type.ENERGY) && connection.MODE != Connection.Mode.NONE)
			{
				return getStorageFor(side).map(imfStorage -> LazyOptional.of(() -> imfStorage)).orElse(LazyOptional.empty()).cast();
			}
		}

		return super.getCapability(cap, side);
	}
}
