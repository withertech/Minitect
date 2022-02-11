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

package com.withertech.minitect.recipe;

import com.google.gson.JsonObject;
import dev.architectury.core.AbstractRecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class ExtendedShapedRecipe extends ShapedRecipe
{
	private static final RecipeSerializer<ShapedRecipe> BASE_SERIALIZER = RecipeSerializer.SHAPED_RECIPE;

	private final ShapedRecipe recipe;

	public ExtendedShapedRecipe(ShapedRecipe recipe)
	{
		super(recipe.getId(), recipe.getGroup(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem());
		this.recipe = recipe;
	}

	public ShapedRecipe getBaseRecipe()
	{
		return this.recipe;
	}

	@Override
	public abstract RecipeSerializer<?> getSerializer();

	@Override
	public abstract boolean matches(CraftingContainer inv, Level worldIn);

	@Override
	public abstract ItemStack assemble(CraftingContainer inv);

	public static class Serializer<T extends ExtendedShapedRecipe> extends AbstractRecipeSerializer<T>
	{
		private final Function<ShapedRecipe, T> recipeFactory;
		@Nullable
		private final BiConsumer<JsonObject, T> readJson;
		@Nullable
		private final BiConsumer<FriendlyByteBuf, T> readBuffer;
		@Nullable
		private final BiConsumer<FriendlyByteBuf, T> writeBuffer;

		public Serializer(Function<ShapedRecipe, T> recipeFactory,
		                  @Nullable BiConsumer<JsonObject, T> readJson,
		                  @Nullable BiConsumer<FriendlyByteBuf, T> readBuffer,
		                  @Nullable BiConsumer<FriendlyByteBuf, T> writeBuffer)
		{
			this.recipeFactory = recipeFactory;
			this.readJson = readJson;
			this.readBuffer = readBuffer;
			this.writeBuffer = writeBuffer;
		}

		public static <S extends ExtendedShapedRecipe> Serializer<S> basic(Function<ShapedRecipe, S> recipeFactory)
		{
			return new Serializer<>(recipeFactory, null, null, null);
		}

		@Deprecated
		public static <S extends ExtendedShapedRecipe> Serializer<S> basic(ResourceLocation serializerId, Function<ShapedRecipe, S> recipeFactory)
		{
			return new Serializer<>(recipeFactory, null, null, null);
		}

		@Override
		public T fromJson(ResourceLocation recipeId, JsonObject json)
		{
			ShapedRecipe recipe = BASE_SERIALIZER.fromJson(recipeId, json);
			T result = this.recipeFactory.apply(recipe);
			if (this.readJson != null)
			{
				this.readJson.accept(json, result);
			}
			return result;
		}

		@Override
		public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
		{
			ShapedRecipe recipe = BASE_SERIALIZER.fromNetwork(recipeId, buffer);
			T result = this.recipeFactory.apply(recipe);
			if (this.readBuffer != null)
			{
				this.readBuffer.accept(buffer, result);
			}
			return result;
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, T recipe)
		{
			BASE_SERIALIZER.toNetwork(buffer, recipe.getBaseRecipe());
			if (this.writeBuffer != null)
			{
				this.writeBuffer.accept(buffer, recipe);
			}
		}
	}
}