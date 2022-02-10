package com.withertech.minitect.registry.forge;

import com.withertech.minitect.registry.MineTags;
import net.minecraft.Util;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;

public class MineTagsBlocksImpl
{
	static
	{
		MineTags.Blocks.TIERS = Util.make(new HashMap<>(), map ->
		{
			for (Tiers tier : Tiers.values())
			{
				map.put(tier, (Tag.Named<Block>) tier.getTag());
			}
		});
	}
	public static void register()
	{
	}
}
