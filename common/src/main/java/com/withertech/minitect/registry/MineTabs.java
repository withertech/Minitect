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
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MineTabs
{
	public static final CreativeModeTab MACHINES = CreativeTabRegistry.create(Minitect.modLoc("machines"), () -> new ItemStack(MineItems.FURNACE.get()));
	public static final CreativeModeTab MATERIALS = CreativeTabRegistry.create(Minitect.modLoc("materials"), () -> new ItemStack(MineMetals.TIN.getIngot().orElse(Items.AIR)));

	public static void register()
	{

	}
}
