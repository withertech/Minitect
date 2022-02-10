package com.withertech.minitect.registry.fabric;

import com.withertech.minitect.registry.MineTags;
import com.withertech.minitect.util.TagUtil;
import net.minecraft.Util;
import net.minecraft.world.item.Tiers;

import java.util.HashMap;

public class MineTagsBlocksImpl
{
	static
	{
		MineTags.Blocks.TIERS = Util.make(new HashMap<>(), map ->
		{
			for (Tiers tier : Tiers.values())
			{
				map.put(tier, TagUtil.makeBlock("needs_tool_level_" + tier.getLevel()));
			}
		});
	}
	public static void register()
	{
	}
}
