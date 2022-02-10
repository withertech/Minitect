package com.withertech.minitect.item.fabric;

import com.withertech.minitect.item.MachineBlockItem;
import com.withertech.minitect.registry.MineTabs;
import net.minecraft.world.level.block.Block;

public class MachineBlockItemFabric extends MachineBlockItem
{
	public MachineBlockItemFabric(Block block)
	{
		super(block, new Properties().tab(MineTabs.MACHINES));
	}
}
