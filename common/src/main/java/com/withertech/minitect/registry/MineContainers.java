package com.withertech.minitect.registry;

import com.withertech.minitect.container.MTCrusherContainer;
import com.withertech.minitect.container.MTFurnaceContainer;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.inventory.MenuType;

public class MineContainers
{
	public static final RegistrySupplier<MenuType<MTFurnaceContainer>> FURNACE = MineRegistries.CONTAINERS.register("furnace", () -> MenuRegistry.ofExtended(MTFurnaceContainer::new));
	public static final RegistrySupplier<MenuType<MTCrusherContainer>> CRUSHER = MineRegistries.CONTAINERS.register("crusher", () -> MenuRegistry.ofExtended(MTCrusherContainer::new));
	public static void register()
	{
	}
}
