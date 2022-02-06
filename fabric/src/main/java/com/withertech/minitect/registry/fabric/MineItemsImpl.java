package com.withertech.minitect.registry.fabric;

import com.withertech.minitect.item.fabric.MachineBlockItemFabric;
import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineItems;
import com.withertech.minitect.registry.MineRegistries;

public class MineItemsImpl
{
	static
	{
		MineItems.FURNACE = MineRegistries.ITEMS.register("furnace", () -> new MachineBlockItemFabric(MineBlocks.FURNACE.get()));
		MineItems.CRUSHER = MineRegistries.ITEMS.register("crusher", () -> new MachineBlockItemFabric(MineBlocks.CRUSHER.get()));
	}
	public static void register()
	{
	}
}
