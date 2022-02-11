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

import com.withertech.minitect.Minitect;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MineRegistries
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Minitect.MOD_ID, Registry.BLOCK_REGISTRY);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Minitect.MOD_ID, Registry.ITEM_REGISTRY);
	public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(Minitect.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
	public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Minitect.MOD_ID, Registry.MENU_REGISTRY);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Minitect.MOD_ID, Registry.RECIPE_SERIALIZER_REGISTRY);

	public static void register()
	{
		registerRegistries();
		registerEntries();
	}

	public static void registerClient()
	{
		MineScreens.register();
	}

	public static void registerCommon()
	{
		MineConfigs.register();
		MineOres.register();
	}

	private static void registerRegistries()
	{
		BLOCKS.register();
		ITEMS.register();
		TILES.register();
		CONTAINERS.register();
		RECIPE_SERIALIZERS.register();
	}

	private static void registerEntries()
	{
		MineTags.register();
		MineTabs.register();
		MineUpgrades.register();
		MineBlocks.register();
		MineItems.register();
		MineTiles.register();
		MineContainers.register();
		MineRecipes.register();
		MineMetals.register();
		MineGems.register();
		MineCraftingTools.register();
	}
}
