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

import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineRegistries;
import com.withertech.minitect.registry.MineTiles;
import com.withertech.minitect.tile.fabric.MTAlloySmelterTileFabric;
import com.withertech.minitect.tile.fabric.MTCrusherTileFabric;
import com.withertech.minitect.tile.fabric.MTFurnaceTileFabric;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

public class MineTilesImpl
{
	static
	{
		MineTiles.FURNACE = MineRegistries.TILES.register("furnace", () -> FabricBlockEntityTypeBuilder.create(MTFurnaceTileFabric::new, MineBlocks.FURNACE.get()).build());
		MineTiles.CRUSHER = MineRegistries.TILES.register("crusher", () -> FabricBlockEntityTypeBuilder.create(MTCrusherTileFabric::new, MineBlocks.CRUSHER.get()).build());
		MineTiles.ALLOY_SMELTER = MineRegistries.TILES.register("alloy_smelter", () -> FabricBlockEntityTypeBuilder.create(MTAlloySmelterTileFabric::new, MineBlocks.ALLOY_SMELTER.get()).build());
	}

	public static void register()
	{
	}
}
