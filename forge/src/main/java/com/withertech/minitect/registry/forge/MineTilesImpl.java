package com.withertech.minitect.registry.forge;

import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineRegistries;
import com.withertech.minitect.registry.MineTiles;
import com.withertech.minitect.tile.MTCrusherTile;
import com.withertech.minitect.tile.forge.MTCrusherTileForge;
import com.withertech.minitect.tile.forge.MTFurnaceTileForge;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MineTilesImpl
{
	static
	{
		MineTiles.FURNACE = MineRegistries.TILES.register("furnace", () -> BlockEntityType.Builder.of(MTFurnaceTileForge::new, MineBlocks.FURNACE.get()).build(null));
		MineTiles.CRUSHER = MineRegistries.TILES.register("crusher", () -> BlockEntityType.Builder.of(MTCrusherTileForge::new, MineBlocks.CRUSHER.get()).build(null));
	}
	public static void register()
	{
	}
}
