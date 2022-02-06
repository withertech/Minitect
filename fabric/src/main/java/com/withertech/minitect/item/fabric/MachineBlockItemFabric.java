package com.withertech.minitect.item.fabric;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.item.MachineBlockItem;
import net.minecraft.world.level.block.Block;

public class MachineBlockItemFabric extends MachineBlockItem
{
	public MachineBlockItemFabric(Block block)
	{
		super(block, new Properties().tab(Minitect.TAB));
	}
}
