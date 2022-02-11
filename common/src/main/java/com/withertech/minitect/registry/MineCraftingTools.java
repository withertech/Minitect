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

import com.withertech.minitect.item.CraftingToolItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.Locale;

public enum MineCraftingTools implements ItemLike
{
	HAMMER,
	FILE
	;

	private RegistrySupplier<CraftingToolItem> tool;

	public String getName()
	{
		return name().toLowerCase(Locale.ROOT);
	}

	public static void register()
	{
		for (MineCraftingTools tool : values())
		{
			tool.tool = MineRegistries.ITEMS.register(tool.getName(), CraftingToolItem::new);
		}
	}

	@Override
	public Item asItem()
	{
		return tool.get();
	}
}
