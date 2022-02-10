package com.withertech.minitect.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class TagUtil
{
	@ExpectPlatform
	public static <T> Tag.Named<T> make(String name, Supplier<TagCollection<T>> collection)
	{
		throw new AssertionError();
	}

	public static Tag.Named<Block> makeBlock(String name)
	{
		return make(name, BlockTags::getAllTags);
	}

	public static Tag.Named<Item> makeItem(String name)
	{
		return make(name, ItemTags::getAllTags);
	}
}
