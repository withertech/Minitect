package com.withertech.minitect.util;

import com.withertech.minitect.Minitect;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

public class RegistryUtil
{
	public static <T> boolean isVanilla(T object, ResourceKey<Registry<T>> registry)
	{
		ResourceLocation id = Registries.getId(object, registry);
		if (id == null) return false;
		return Objects.equals(id.getNamespace(), "minecraft");
	}

	public static boolean isVanilla(Item item)
	{
		return isVanilla(item, Registry.ITEM_REGISTRY);
	}

	public static boolean isVanilla(Block block)
	{
		return isVanilla(block, Registry.BLOCK_REGISTRY);
	}

	public static <T> RegistrySupplier<T> delegate(T object, ResourceKey<Registry<T>> registry)
	{
		return Registries.get(Minitect.MOD_ID).get(registry).delegate(Registries.getId(object, registry));
	}

	public static RegistrySupplier<Item> delegate(Item item)
	{
		return delegate(item, Registry.ITEM_REGISTRY);
	}

	public static RegistrySupplier<Block> delegate(Block item)
	{
		return delegate(item, Registry.BLOCK_REGISTRY);
	}
}
