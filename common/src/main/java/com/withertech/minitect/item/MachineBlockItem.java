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

package com.withertech.minitect.item;

import com.withertech.mine_flux.api.IMFContainer;
import com.withertech.mine_flux.api.IMFStorage;
import com.withertech.mine_flux.util.EnergyUtil;
import com.withertech.mine_flux.util.TextUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class MachineBlockItem extends BlockItem implements IMFContainer
{
	public MachineBlockItem(Block block, Properties properties)
	{
		super(block, properties);
	}

	@Override
	public void verifyTagAfterLoad(@NotNull CompoundTag compoundTag)
	{
		if (!compoundTag.contains("Energy", Tag.TAG_INT))
			compoundTag.putInt("Energy", 0);
		super.verifyTagAfterLoad(compoundTag);
	}

	@Override
	public void onCraftedBy(ItemStack itemStack, Level level, Player player)
	{
		EnergyUtil.insertEnergy(itemStack, 0, true);
	}

	@Override
	public Optional<IMFStorage> getStorageFor(Object that)
	{
		if (that instanceof ItemStack stack)
		{
			return Optional.of(EnergyUtil.create(stack.getOrCreateTag(), 1_000_000, 100));
		}
		return Optional.empty();
	}

	@Override
	public boolean isBarVisible(ItemStack itemStack)
	{
		return true;
	}

	@Override
	public int getBarColor(ItemStack itemStack)
	{
		return DyeColor.RED.getTextColor();
	}

	@Override
	public int getBarWidth(ItemStack itemStack)
	{
		return getStorageFor(itemStack).map(energy -> Math.round(((float) energy.getEnergyStored() / (float) energy.getMaxEnergyStored()) * 13.0F)).orElse(0);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag)
	{
		if (itemStack.getItem() instanceof MachineBlockItem)
		{
			EnergyUtil.getEnergyStorage(itemStack).ifPresent(storage ->
					list.add(new TextComponent(TextUtil.getEnergyStorageString(storage))));
			if (itemStack.getOrCreateTag().contains("Inventory") || itemStack.getOrCreateTag().contains("Upgrades"))
			{
				list.add(new TextComponent("Contents Saved"));
			}
		}
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}
}
