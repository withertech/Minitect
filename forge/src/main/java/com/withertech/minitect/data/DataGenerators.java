package com.withertech.minitect.data;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.data.lang.MineLanguageProviderForge;
import com.withertech.minitect.data.model.MineBlockStateProviderForge;
import com.withertech.minitect.data.recipe.MineRecipeProviderForge;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Minitect.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event)
	{
		final DataGenerator generator = event.getGenerator();
		generator.addProvider(new MineBlockStateProviderForge(generator, event.getExistingFileHelper()));
		generator.addProvider(new MineLanguageProviderForge(generator));
		generator.addProvider(new MineRecipeProviderForge(generator));
	}
}
