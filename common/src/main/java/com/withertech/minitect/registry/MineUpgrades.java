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

package com.withertech.minitect.registry;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.item.UpgradeItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;

public enum MineUpgrades implements ItemLike
{
	SPEED("speed", 8),
	EFFICIENCY("efficiency", 8);

	private final ResourceLocation id;
	private final int stackSize;
	private RegistrySupplier<UpgradeItem> item;

	MineUpgrades(String id, int stackSize)
	{
		this.id = Minitect.modLoc(id + "_upgrade");
		this.stackSize = stackSize;
	}

	public static void register()
	{
		Arrays.stream(values()).forEach(mineUpgrades ->
		{
			mineUpgrades.item = MineRegistries.ITEMS.register(mineUpgrades.id, () -> new UpgradeItem(new Item.Properties().tab(MineTabs.MACHINES).stacksTo(mineUpgrades.stackSize), mineUpgrades));
		});
	}

	@Override
	public Item asItem()
	{
		return item.get();
	}
}
