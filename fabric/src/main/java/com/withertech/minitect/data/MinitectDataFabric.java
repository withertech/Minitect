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

package com.withertech.minitect.data;

import com.withertech.minitect.data.lang.MineLanguageProviderFabric;
import com.withertech.minitect.data.loot.MineBlockLootProviderFabric;
import com.withertech.minitect.data.model.MineBlockStateProviderFabric;
import com.withertech.minitect.data.recipe.MineRecipeProviderFabric;
import com.withertech.minitect.data.tag.MineBlockTagProviderFabric;
import com.withertech.minitect.data.tag.MineItemTagProviderFabric;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MinitectDataFabric implements DataGeneratorEntrypoint
{
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
	{
		fabricDataGenerator.addProvider(MineRecipeProviderFabric::new);
		fabricDataGenerator.addProvider(MineBlockStateProviderFabric::new);
		fabricDataGenerator.addProvider(MineLanguageProviderFabric::new);
		fabricDataGenerator.addProvider(generator ->
				new MineItemTagProviderFabric(generator, fabricDataGenerator.addProvider(MineBlockTagProviderFabric::new)));
		fabricDataGenerator.addProvider(MineBlockLootProviderFabric::new);
	}
}
