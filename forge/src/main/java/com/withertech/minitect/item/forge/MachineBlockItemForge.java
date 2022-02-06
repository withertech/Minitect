package com.withertech.minitect.item.forge;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.item.MachineBlockItem;
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
		super(block, new Properties().tab(Minitect.TAB));
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
