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
import com.withertech.minitect.recipe.*;
import dev.architectury.core.RegistryEntry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MineRecipes
{
	public static void register()
	{
		Types.register();
		Serializers.register();
	}

	public static class Serializers
	{
		public static final RegistrySupplier<RecipeSerializer<CrushingRecipe>> CRUSHING = MineRegistries.RECIPE_SERIALIZERS.register("crushing", CrushingRecipe.Serializer::new);
		public static final RegistrySupplier<RecipeSerializer<AlloySmeltingRecipe>> ALLOY_SMELTING = MineRegistries.RECIPE_SERIALIZERS.register("alloy_smelting", AlloySmeltingRecipe.Serializer::new);
		public static final RegistrySupplier<RecipeSerializer<ShapedMetalRecipe>> SHAPED_METAL = MineRegistries.RECIPE_SERIALIZERS.register("shaped_metal", ShapedMetalRecipe.Serializer::new);
		public static final RegistrySupplier<RecipeSerializer<ShapelessMetalRecipe>> SHAPELESS_METAL = MineRegistries.RECIPE_SERIALIZERS.register("shapeless_metal", ShapelessMetalRecipe.Serializer::new);

		public static void register()
		{

		}
	}

	public static class Types
	{
		public static final MineRecipeType<CrushingRecipe> CRUSHING = register("crushing");
		public static final MineRecipeType<AlloySmeltingRecipe> ALLOY_SMELTING = register("alloy_smelting");
		public static final MineRecipeTypeWrapper<CookingRecipeWrapper, SmeltingRecipe, Container> SMELTING = new MineRecipeTypeWrapper<>(RecipeType.SMELTING)
		{
			@Override
			public @NotNull CookingRecipeWrapper createWrapper(SmeltingRecipe recipe)
			{
				return new CookingRecipeWrapper(recipe.getId(), recipe);
			}
		};

		public static void register()
		{

		}

		private static <T extends AbstractRecipe> MineRecipeType<T> register(String id)
		{
			return Registry.register(Registry.RECIPE_TYPE, Minitect.modLoc(id), new MineRecipeType<>());
		}

		public abstract static class MineRecipeTypeWrapper<T extends AbstractRecipe, R extends Recipe<C>, C extends Container> implements RecipeType<T>
		{
			private final RecipeType<R> type;

			private MineRecipeTypeWrapper(RecipeType<R> type)
			{
				this.type = type;
			}

			public RecipeType<R> getType()
			{
				return type;
			}

			public <X extends C> T getRecipe(Level level, X container)
			{
				return level.getRecipeManager().getRecipeFor(type, container, level).map(this::createWrapper).orElse(null);
			}

			@Override
			public <C extends Container> Optional<T> tryMatch(Recipe<C> recipe, Level level, C container)
			{
				return type.tryMatch(recipe, level, container).map(this::createWrapper);
			}

			@NotNull
			public abstract T createWrapper(R recipe);

			@Override
			public String toString()
			{
				return Registry.RECIPE_TYPE.getKey(type).toString();
			}
		}

		public static class MineRecipeType<T extends AbstractRecipe> extends RegistryEntry<RecipeType<T>> implements RecipeType<T>
		{
			public String toString()
			{
				return Registry.RECIPE_TYPE.getKey(this).toString();
			}
		}
	}

}
