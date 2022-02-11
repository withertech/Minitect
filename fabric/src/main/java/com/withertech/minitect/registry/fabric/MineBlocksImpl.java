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

package com.withertech.minitect.registry.fabric;

import com.withertech.minitect.block.fabric.MTAlloySmelterBlockFabric;
import com.withertech.minitect.block.fabric.MTCrusherBlockFabric;
import com.withertech.minitect.block.fabric.MTFurnaceBlockFabric;
import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineRegistries;

public class MineBlocksImpl
{
	static
	{
		MineBlocks.FURNACE = MineRegistries.BLOCKS.register("furnace", MTFurnaceBlockFabric::new);
		MineBlocks.CRUSHER = MineRegistries.BLOCKS.register("crusher", MTCrusherBlockFabric::new);
		MineBlocks.ALLOY_SMELTER = MineRegistries.BLOCKS.register("alloy_smelter", MTAlloySmelterBlockFabric::new);
	}

	public static void register()
	{
	}
}
