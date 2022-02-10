package com.withertech.minitect.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public interface BlockLike extends ItemLike
{
	Block asBlock();

	default BlockState asBlockState()
	{
		return asBlock().defaultBlockState();
	}

	@NotNull
	@Override
	default Item asItem()
	{
		return asBlock().asItem();
	}
}
