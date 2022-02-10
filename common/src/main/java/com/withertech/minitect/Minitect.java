package com.withertech.minitect;

import com.withertech.minitect.registry.*;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.injectables.targets.ArchitecturyTarget;
import dev.architectury.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Minitect
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "minitect";

	public static void init()
	{
		LifecycleEvent.SETUP.register(Minitect::common);
		ClientLifecycleEvent.CLIENT_SETUP.register(Minitect::client);
		Platform.getMod(MOD_ID).registerConfigurationScreen(MineConfigs::createScreen);
		MineRegistries.register();
	}

	public static void common()
	{
		MineRegistries.registerCommon();
	}

	public static ResourceLocation modLoc(String key)
	{
		return new ResourceLocation(MOD_ID, key);
	}
	public static ResourceLocation mcLoc(String key)
	{
		return new ResourceLocation("minecraft", key);
	}

	public static ResourceLocation loaderLoc(String key)
	{
		return new ResourceLocation(ArchitecturyTarget.getCurrentTarget(), key);
	}

	private static void client(Minecraft instance)
	{
		MineRegistries.registerClient();
	}
}