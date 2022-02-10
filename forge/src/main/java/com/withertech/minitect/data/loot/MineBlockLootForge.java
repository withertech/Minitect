package com.withertech.minitect.data.loot;

import com.google.common.collect.Streams;
import com.withertech.minitect.registry.MineGems;
import com.withertech.minitect.registry.MineMetals;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class MineBlockLootForge extends BlockLoot
{
	@Override
	protected void addTables()
	{
		for (MineMetals metal : MineMetals.values())
		{
			metal.getStorageBlock(true).ifPresent(this::dropSelf);
			metal.getOre(true).ifPresent(this::dropSelf);
			metal.getDeepOre(true).ifPresent(this::dropSelf);
		}

		for (MineGems gem : MineGems.values())
		{
			gem.getStorageBlock(true).ifPresent(this::dropSelf);
			gem.getOre(true).ifPresent(this::dropSelf);
			gem.getDeepOre(true).ifPresent(this::dropSelf);
		}
	}

	@Override
	protected @NotNull Iterable<Block> getKnownBlocks()
	{
		return Streams.concat(
				Arrays.stream(MineMetals.values()).map(metal -> metal.getStorageBlock(true)).filter(Optional::isPresent),
				Arrays.stream(MineMetals.values()).map(metal -> metal.getOre(true)).filter(Optional::isPresent),
				Arrays.stream(MineMetals.values()).map(metal -> metal.getDeepOre(true)).filter(Optional::isPresent),

				Arrays.stream(MineGems.values()).map(gem -> gem.getStorageBlock(true)).filter(Optional::isPresent),
				Arrays.stream(MineGems.values()).map(gem -> gem.getOre(true)).filter(Optional::isPresent),
				Arrays.stream(MineGems.values()).map(gem -> gem.getDeepOre(true)).filter(Optional::isPresent)
		).map(Optional::orElseThrow).collect(Collectors.toList());
	}
}
