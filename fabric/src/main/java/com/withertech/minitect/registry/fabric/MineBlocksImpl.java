package com.withertech.minitect.registry.fabric;

import com.withertech.minitect.block.fabric.MTCrusherBlockFabric;
import com.withertech.minitect.block.fabric.MTFurnaceBlockFabric;
import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineRegistries;

public class MineBlocksImpl
{
	static
	{
		MineBlocks.FURNACE = MineRegistries.BLOCKS.register("furnace", MTFurnaceBlockFabric::new);
		MineBlocks.CRUSHER = MineRegistries.BLOCKS.register("crusher", MTCrusherBlockFabric::new);
	}
	public static void register()
	{
	}
}
