package com.withertech.minitect.data;

import com.withertech.minitect.data.lang.MineLanguageProviderFabric;
import com.withertech.minitect.data.model.MineBlockStateProviderFabric;
import com.withertech.minitect.data.recipe.MineRecipeProviderFabric;
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
	}
}
