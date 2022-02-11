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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.registry.MineMetals;
import com.withertech.minitect.registry.MineRecipes;
import dev.architectury.registry.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class AlloySmeltingRecipeBuilder
{
	private final Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();
	private final List<ItemStack> results = new ArrayList<>();
	private final int processTime;

	private AlloySmeltingRecipeBuilder(ItemStack result, int processTime)
	{
		results.add(result);
		this.processTime = processTime;
	}

	public static AlloySmeltingRecipeBuilder builder(ItemLike ingredient, int count)
	{
		return builder(ingredient, count, 400);
	}

	public static AlloySmeltingRecipeBuilder builder(ItemLike ingredient, int count, int processTime)
	{
		return builder(new ItemStack(ingredient.asItem(), count), processTime);
	}

	public static AlloySmeltingRecipeBuilder builder(ItemStack result)
	{
		return builder(result, 400);
	}

	public static AlloySmeltingRecipeBuilder builder(ItemStack result, int processTime)
	{
		return new AlloySmeltingRecipeBuilder(result, processTime);
	}

	public static AlloySmeltingRecipeBuilder alloyIngot(MineMetals alloy, int processTime)
	{
		if (alloy.getAlloy().isPresent() && alloy.getIngot().isPresent())
		{
			final AlloySmeltingRecipeBuilder builder = builder(alloy.getIngot().get(), alloy.getAlloy().get().getResultCount());
			alloy.getAlloy().get().getIngotIngredients().forEach(builder::ingredient);
			return builder;
		}
		return builder(ItemStack.EMPTY);
	}

	public static AlloySmeltingRecipeBuilder alloyDust(MineMetals alloy, int processTime)
	{
		if (alloy.getAlloy().isPresent() && alloy.getIngot().isPresent())
		{
			final AlloySmeltingRecipeBuilder builder = builder(alloy.getIngot().get(), alloy.getAlloy().get().getResultCount());
			alloy.getAlloy().get().getDustIngredients().forEach(builder::ingredient);
			return builder;
		}
		return builder(ItemStack.EMPTY);
	}

	public AlloySmeltingRecipeBuilder ingredient(Ingredient ingredient, int count)
	{
		ingredients.put(ingredient, count);
		return this;
	}

	public void save(Consumer<FinishedRecipe> consumer)
	{
		ResourceLocation resultId = Registries.getId(results.iterator().next().getItem(), Registry.ITEM_REGISTRY);
		ResourceLocation id = new ResourceLocation(
				"minecraft".equals(resultId.getNamespace()) ? Minitect.MOD_ID : resultId.getNamespace(),
				"alloy_smelting/" + resultId.getPath());
		save(consumer, id);
	}

	public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id)
	{
		consumer.accept(new Result(id, this));
	}

	public static class Result implements FinishedRecipe
	{
		private final ResourceLocation id;
		private final AlloySmeltingRecipeBuilder builder;

		public Result(ResourceLocation id, AlloySmeltingRecipeBuilder builder)
		{
			this.id = id;
			this.builder = builder;
		}

		private JsonObject serializeResult(ItemStack stack)
		{
			JsonObject json = new JsonObject();
			json.addProperty("item", Registries.getId(stack.getItem(), Registry.ITEM_REGISTRY).toString());
			if (stack.getCount() > 1)
			{
				json.addProperty("count", stack.getCount());
			}
			return json;
		}

		private JsonObject serializeIngredient(Ingredient ingredient, int count)
		{
			JsonObject json = ingredient.toJson().getAsJsonObject();
			if (count > 1)
			{
				json.addProperty("count", count);
			}
			return json;
		}

		@Override
		public void serializeRecipeData(JsonObject json)
		{
			json.addProperty("process_time", builder.processTime);

			JsonArray ingredients = new JsonArray();
			builder.ingredients.forEach((ingredient, count) ->
					ingredients.add(serializeIngredient(ingredient, count)));
			json.add("ingredients", ingredients);

			JsonArray results = new JsonArray();
			builder.results.forEach(stack ->
					results.add(serializeResult(stack)));
			json.add("results", results);
		}

		@Override
		public ResourceLocation getId()
		{
			return id;
		}

		@Override
		public RecipeSerializer<?> getType()
		{
			return MineRecipes.Serializers.ALLOY_SMELTING.get();
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement()
		{
			return null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId()
		{
			return null;
		}
	}
}
