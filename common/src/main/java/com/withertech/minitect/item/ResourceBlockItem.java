package com.withertech.minitect.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class ResourceBlockItem extends BlockItem
{
	private final ResourceItemType type;
	public ResourceBlockItem(Block blockIn, Properties builder, ResourceItemType type)
	{
		super(blockIn, builder);
		this.type = type;
	}

	public ResourceItemType getType()
	{
		return type;
	}
}
