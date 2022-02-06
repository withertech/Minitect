package com.withertech.minitect.data.lang;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.registry.MineBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.data.LanguageProvider;

public class MineLanguageProviderForge extends LanguageProvider
{
	public MineLanguageProviderForge(DataGenerator gen)
	{
		super(gen, Minitect.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations()
	{
		addBlock(MineBlocks.FURNACE, "Electric Furnace");
		addBlock(MineBlocks.CRUSHER, "Electric Crusher");
		add(((TranslatableComponent) Minitect.TAB.getDisplayName()).getKey(), "Minitect");
	}
}
