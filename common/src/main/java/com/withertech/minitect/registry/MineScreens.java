package com.withertech.minitect.registry;

import com.withertech.minitect.client.screen.MTCrusherScreen;
import com.withertech.minitect.client.screen.MTFurnaceScreen;
import com.withertech.minitect.container.MTCrusherContainer;
import com.withertech.minitect.container.MTFurnaceContainer;
import dev.architectury.registry.menu.MenuRegistry;

public class MineScreens
{
	public static void register()
	{
		MenuRegistry.<MTFurnaceContainer, MTFurnaceScreen>registerScreenFactory(MineContainers.FURNACE.get(), (containerMenu, inventory, component) -> new MTFurnaceScreen(containerMenu, inventory.player, component));
		MenuRegistry.<MTCrusherContainer, MTCrusherScreen>registerScreenFactory(MineContainers.CRUSHER.get(), (containerMenu, inventory, component) -> new MTCrusherScreen(containerMenu, inventory.player, component));
	}
}
