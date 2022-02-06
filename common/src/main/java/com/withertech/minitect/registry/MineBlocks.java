package com.withertech.minitect.registry;

import com.withertech.minitect.block.MTCrusherBlock;
import com.withertech.minitect.block.MTFurnaceBlock;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;

public class MineBlocks
{
	public static RegistrySupplier<MTFurnaceBlock> FURNACE;
	public static RegistrySupplier<MTCrusherBlock> CRUSHER;
	@ExpectPlatform
	public static void register()
	{
		throw new AssertionError();
	}
}
