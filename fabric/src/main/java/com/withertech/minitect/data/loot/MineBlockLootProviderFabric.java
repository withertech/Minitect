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

package com.withertech.minitect.data.loot;

import com.withertech.minitect.registry.MineGems;
import com.withertech.minitect.registry.MineMetals;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTablesProvider;

public class MineBlockLootProviderFabric extends FabricBlockLootTablesProvider
{
	public MineBlockLootProviderFabric(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator);
	}

	@Override
	protected void generateBlockLootTables()
	{
		for (MineMetals metal : MineMetals.values())
		{
			metal.getStorageBlock(true).ifPresent(this::dropSelf);
			metal.getOre(true).ifPresent(this::dropSelf);
			metal.getDeepOre(true).ifPresent(this::dropSelf);
		}
		for (MineGems gem : MineGems.values())
		{
			gem.getStorageBlock(true).ifPresent(this::dropSelf);
			gem.getOre(true).ifPresent(this::dropSelf);
			gem.getDeepOre(true).ifPresent(this::dropSelf);
		}
	}
}
