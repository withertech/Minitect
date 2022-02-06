package com.withertech.minitect.data.lang;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.registry.MineBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.network.chat.TranslatableComponent;

public class MineLanguageProviderFabric extends FabricLanguageProvider
{
	public MineLanguageProviderFabric(FabricDataGenerator gen)
	{
		super(gen, "en_us");
	}

	@Override
	protected void addTranslations()
	{
		addBlock(MineBlocks.FURNACE, "Electric Furnace");
		addBlock(MineBlocks.CRUSHER, "Electric Crusher");
		add(((TranslatableComponent) Minitect.TAB.getDisplayName()).getKey(), "Minitect");
	}
}
