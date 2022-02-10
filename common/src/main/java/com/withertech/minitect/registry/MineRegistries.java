package com.withertech.minitect.registry;

import com.withertech.minitect.Minitect;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

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
	}
}
