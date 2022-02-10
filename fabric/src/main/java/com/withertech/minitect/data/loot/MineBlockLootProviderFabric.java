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
