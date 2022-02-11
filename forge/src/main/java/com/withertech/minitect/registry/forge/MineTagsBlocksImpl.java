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
