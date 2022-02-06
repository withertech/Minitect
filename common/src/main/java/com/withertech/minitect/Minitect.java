package com.withertech.minitect;

import com.withertech.minitect.registry.MineItems;
import com.withertech.minitect.registry.MineRegistries;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Minitect
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "minitect";

	public static final CreativeModeTab TAB = CreativeTabRegistry.create(modLoc("tab"), () -> new ItemStack(MineItems.FURNACE.get()));

	public static void init()
	{
		LifecycleEvent.SETUP.register(Minitect::setup);
		ClientLifecycleEvent.CLIENT_SETUP.register(Minitect::clientInit);
		MineRegistries.register();
	}

	public static void setup()
	{
	}

	public static ResourceLocation modLoc(String key)
	{
		return new ResourceLocation(MOD_ID, key);
	}

	private static void clientInit(Minecraft instance)
	{
		MineRegistries.registerClient();
	}
}