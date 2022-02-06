package com.withertech.minitect.registry.fabric;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineRegistries;
import com.withertech.minitect.registry.MineTiles;
import com.withertech.minitect.tile.fabric.MTCrusherTileFabric;
import com.withertech.minitect.tile.fabric.MTFurnaceTileFabric;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

public class MineTilesImpl
{
	static
	{
		MineTiles.FURNACE = MineRegistries.TILES.register("furnace", () -> FabricBlockEntityTypeBuilder.create(MTFurnaceTileFabric::new, MineBlocks.FURNACE.get()).build());
		MineTiles.CRUSHER = MineRegistries.TILES.register("crusher", () -> FabricBlockEntityTypeBuilder.create(MTCrusherTileFabric::new, MineBlocks.CRUSHER.get()).build());
	}
	public static void register()
	{
	}
}
