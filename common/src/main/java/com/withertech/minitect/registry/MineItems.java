package com.withertech.minitect.registry;

import com.withertech.minitect.item.MachineBlockItem;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;

public class MineItems
{
	public static RegistrySupplier<MachineBlockItem> FURNACE;
	public static RegistrySupplier<MachineBlockItem> CRUSHER;
	@ExpectPlatform
	public static void register()
	{
		throw new AssertionError();
	}
}
