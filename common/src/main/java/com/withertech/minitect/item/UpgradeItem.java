package com.withertech.minitect.item;

import com.withertech.minitect.registry.MineUpgrades;
import net.minecraft.world.item.Item;

public class UpgradeItem extends Item
{
	private final MineUpgrades upgrade;

	public UpgradeItem(Properties properties, MineUpgrades upgrade)
	{
		super(properties);
		this.upgrade = upgrade;
	}

	public MineUpgrades getUpgrade()
	{
		return upgrade;
	}
}
