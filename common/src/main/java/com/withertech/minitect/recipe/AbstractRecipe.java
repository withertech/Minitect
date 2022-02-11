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

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.inventory.AbstractMachineInventory;
import dev.architectury.core.AbstractRecipeSerializer;
import dev.architectury.registry.registries.Registries;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractRecipe implements Recipe<AbstractMachineInventory>
{
	protected final Map<ItemStack, Float> results = new LinkedHashMap<>();
	protected final Map<Ingredient, Integer> ingredients = new LinkedHashMap<>();
	protected final ResourceLocation recipeId;
	protected int processTime;

	public AbstractRecipe(ResourceLocation recipeId) {this.recipeId = recipeId;}

	public List<ItemStack> getResults()
	{
		final Random random = new Random();

		return results.entrySet().stream()
				.filter(e ->
				{
					float chance = e.getValue();
					return (random.nextFloat(0, 1) < chance);
				})
				.map(e -> e.getKey().copy())
				.collect(Collectors.toList());
	}

	public boolean matches(AbstractMachineInventory container, Level level)
	{
		return this.getIngredientsWithCount().stream().allMatch(ingredient -> container.testIngredient(ingredient.getFirst(), ingredient.getSecond()));
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(Ingredient.EMPTY, ingredients.keySet().toArray(Ingredient[]::new));
	}

	public List<Pair<Ingredient, Integer>> getIngredientsWithCount()
	{
		return ingredients.entrySet().stream()
				.map(e -> new Pair<>(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}

	public int getProcessTime()
	{
		return processTime;
	}

	public Set<ItemStack> getPossibleResults()
	{
		return results.keySet();
	}

	public List<Pair<ItemStack, Float>> getPossibleResultsWithChances()
	{
		return results.entrySet().stream()
				.map(e -> new Pair<>(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}

	public Map<ItemStack, Float> getResultsMap()
	{
		return ImmutableMap.copyOf(results);
	}

	@Override
	public ResourceLocation getId()
	{
		return recipeId;
	}

	@Deprecated
	@Override
	public ItemStack assemble(AbstractMachineInventory container)
	{
		return getResultItem();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	}

	@Deprecated
	@Override
	public ItemStack getResultItem()
	{
		return !results.isEmpty() ? getPossibleResults().iterator().next() : ItemStack.EMPTY;
	}

	@Override
	public String toString()
	{
		return "AbstractRecipe{" +
				"recipeId=" + recipeId +
				'}';
	}

	public static abstract class DefaultSerializer<T extends AbstractRecipe> extends AbstractRecipeSerializer<T>
	{
		protected abstract Function<ResourceLocation, T> getFactory();

		@Override
		public T fromJson(ResourceLocation recipeId, JsonObject serializedRecipe)
		{
			T recipe = getFactory().apply(recipeId);
			recipe.processTime = GsonHelper.getAsInt(serializedRecipe, "process_time", 400);
			JsonArray ingredientsArray = serializedRecipe.getAsJsonArray("ingredients");
			ingredientsArray.forEach(jsonElement ->
			{
				JsonObject obj = jsonElement.getAsJsonObject();
				Ingredient ingredient = Ingredient.fromJson(obj);
				int count = GsonHelper.getAsInt(obj, "count", 1);
				recipe.ingredients.put(ingredient, count);
			});
			JsonArray resultsArray = serializedRecipe.getAsJsonArray("results");
			resultsArray.forEach(jsonElement ->
			{
				JsonObject obj = jsonElement.getAsJsonObject();
				String id = GsonHelper.getAsString(obj, "item");
				Item item = Registries.get(Minitect.MOD_ID).get(Registry.ITEM_REGISTRY).get(ResourceLocation.tryParse(id));
				int count = GsonHelper.getAsInt(obj, "count", 1);
				ItemStack stack = new ItemStack(item, count);
				float chance = GsonHelper.getAsFloat(obj, "chance", 1);
				recipe.results.put(stack, chance);
			});
			return recipe;
		}

		@Override
		public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
		{
			T recipe = getFactory().apply(recipeId);
			recipe.processTime = buffer.readVarInt();
			int ingredientCount = buffer.readByte();
			for (int i = 0; i < ingredientCount; ++i)
			{
				Ingredient ingredient = Ingredient.fromNetwork(buffer);
				int count = buffer.readVarInt();
				recipe.ingredients.put(ingredient, count);
			}
			int resultCount = buffer.readByte();
			for (int i = 0; i < resultCount; ++i)
			{
				ResourceLocation id = buffer.readResourceLocation();
				int count = buffer.readVarInt();
				float chance = buffer.readFloat();
				Item item = Registries.get(Minitect.MOD_ID).get(Registry.ITEM_REGISTRY).get(id);
				recipe.results.put(new ItemStack(item, count), chance);
			}
			return recipe;
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, T recipe)
		{
			buffer.writeVarInt(recipe.getProcessTime());
			buffer.writeByte(recipe.ingredients.size());
			recipe.ingredients.forEach((ingredient, count) ->
			{
				ingredient.toNetwork(buffer);
				buffer.writeVarInt(count);
			});
			buffer.writeByte(recipe.results.size());
			recipe.results.forEach((stack, chance) ->
			{
				buffer.writeResourceLocation(Registries.getId(stack.getItem(), Registry.ITEM_REGISTRY));
				buffer.writeVarInt(stack.getCount());
				buffer.writeFloat(chance);
			});
		}
	}
}
