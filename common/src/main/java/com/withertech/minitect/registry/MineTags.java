package com.withertech.minitect.registry;

import com.withertech.minitect.util.TagUtil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class MineTags
{
	public static void register()
	{
		Blocks.register();
		Items.register();
	}
	public static class Blocks
	{
		public static Map<Tiers, Tag.Named<Block>> TIERS;
		@ExpectPlatform
		private static void register()
		{
		}
	}

	public static class Items
	{
		public static final Tag.Named<Item> PLATES = TagUtil.makeItem("plates");
		public static final Tag.Named<Item> RODS = TagUtil.makeItem("rods");
		public static final Tag.Named<Item> GEAR = TagUtil.makeItem("gear");

		private static void register()
		{
		}
	}
}
