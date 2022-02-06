package com.withertech.minitect.forge;

import dev.architectury.platform.forge.EventBuses;
import com.withertech.minitect.Minitect;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Minitect.MOD_ID)
public class MinitectForge
{
	public MinitectForge()
	{
		// Submit our event bus to let architectury register our content on the right time
		EventBuses.registerModEventBus(Minitect.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
//		FMLJavaModLoadingContext.get().getModEventBus().register(DataGenerators.class);
		Minitect.init();
	}
}