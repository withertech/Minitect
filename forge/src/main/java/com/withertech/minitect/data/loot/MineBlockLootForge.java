/*
 * Minitect
 * Copyright (C) 2022 WitherTech
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
