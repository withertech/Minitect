package com.withertech.minitect.registry;

import com.withertech.minitect.tile.MTCrusherTile;
import com.withertech.minitect.tile.MTFurnaceTile;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MineTiles
{
	public static RegistrySupplier<BlockEntityType<? extends MTFurnaceTile>> FURNACE;
	public static RegistrySupplier<BlockEntityType<? extends MTCrusherTile>> CRUSHER;
	@ExpectPlatform
	public static void register()
	{
		throw new AssertionError();
	}
}
