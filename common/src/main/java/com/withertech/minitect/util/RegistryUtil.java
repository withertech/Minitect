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
