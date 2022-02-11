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

package com.withertech.minitect.registry.forge;

import com.withertech.minitect.item.forge.MachineBlockItemForge;
import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineItems;
import com.withertech.minitect.registry.MineRegistries;

public class MineItemsImpl
{
	static
	{
		MineItems.FURNACE = MineRegistries.ITEMS.register("furnace", () -> new MachineBlockItemForge(MineBlocks.FURNACE.get()));
		MineItems.CRUSHER = MineRegistries.ITEMS.register("crusher", () -> new MachineBlockItemForge(MineBlocks.CRUSHER.get()));
		MineItems.ALLOY_SMELTER = MineRegistries.ITEMS.register("alloy_smelter", () -> new MachineBlockItemForge(MineBlocks.ALLOY_SMELTER.get()));
	}

	public static void register()
	{
	}
}
