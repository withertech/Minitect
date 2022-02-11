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

package com.withertech.minitect.registry;

import com.withertech.mine_tags.util.TagUtil;
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
		public static final Tag.Named<Item> GEARS = TagUtil.makeItem("gear");

		private static void register()
		{
		}
	}
}
