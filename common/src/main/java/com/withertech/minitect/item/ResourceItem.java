package com.withertech.minitect.item;

import net.minecraft.world.item.Item;

public class ResourceItem extends Item
{
	private final ResourceItemType type;
	public ResourceItem(Properties properties, ResourceItemType type)
	{
		super(properties);
		this.type = type;
	}

	public ResourceItemType getType()
	{
		return type;
	}
}
