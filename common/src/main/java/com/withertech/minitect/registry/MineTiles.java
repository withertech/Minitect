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

import com.withertech.minitect.tile.MTAlloySmelterTile;
import com.withertech.minitect.tile.MTCrusherTile;
import com.withertech.minitect.tile.MTFurnaceTile;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MineTiles
{
	public static RegistrySupplier<BlockEntityType<? extends MTFurnaceTile>> FURNACE;
	public static RegistrySupplier<BlockEntityType<? extends MTCrusherTile>> CRUSHER;
	public static RegistrySupplier<BlockEntityType<? extends MTAlloySmelterTile>> ALLOY_SMELTER;

	@ExpectPlatform
	public static void register()
	{
		throw new AssertionError();
	}
}
