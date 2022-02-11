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

package com.withertech.minitect.item.forge;

import com.withertech.minitect.item.MachineBlockItem;
import com.withertech.minitect.registry.MineTabs;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MachineBlockItemForge extends MachineBlockItem
{
	public MachineBlockItemForge(Block block)
	{
		super(block, new Properties().tab(MineTabs.MACHINES));
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
	{
		return new ICapabilityProvider()
		{
			@NotNull
			@Override
			public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg)
			{
				if (capability == CapabilityEnergy.ENERGY)
				{
					return CapabilityEnergy.ENERGY.orEmpty(capability, getStorageFor(stack).map(imfStorage -> LazyOptional.of(() -> imfStorage)).orElse(LazyOptional.empty()).cast());
				}
				return LazyOptional.empty();
			}
		};
	}
}
