package com.withertech.minitect.registry;

import com.withertech.minitect.Minitect;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
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
		MineUpgrades.register();
		MineBlocks.register();
		MineItems.register();
		MineTiles.register();
		MineContainers.register();
		MineRecipes.register();
	}
}
