package com.withertech.minitect.registry.forge;

import com.withertech.minitect.block.forge.MTCrusherBlockForge;
import com.withertech.minitect.block.forge.MTFurnaceBlockForge;
import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineRegistries;
import com.withertech.minitect.registry.MineTiles;

public class MineBlocksImpl
{
	static
	{
		MineBlocks.FURNACE = MineRegistries.BLOCKS.register("furnace", MTFurnaceBlockForge::new);
		MineBlocks.CRUSHER = MineRegistries.BLOCKS.register("crusher", MTCrusherBlockForge::new);
	}
	public static void register()
	{
	}
}
