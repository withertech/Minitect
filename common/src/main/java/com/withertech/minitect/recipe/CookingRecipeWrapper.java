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

import com.mojang.datafixers.util.Pair;
import com.withertech.minitect.inventory.AbstractMachineInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CookingRecipeWrapper extends AbstractRecipe
{
	private final AbstractCookingRecipe recipe;

	public CookingRecipeWrapper(ResourceLocation recipeId, AbstractCookingRecipe recipe)
	{
		super(recipeId);
		this.recipe = recipe;
	}

	@Override
	public List<ItemStack> getResults()
	{
		return Collections.singletonList(recipe.getResultItem());
	}

	@Override
	public boolean matches(AbstractMachineInventory container, Level level)
	{
		return recipe.matches(container, level);
	}

	@Override
	public ResourceLocation getId()
	{
		return recipe.getId();
	}

	@Override
	public ItemStack assemble(AbstractMachineInventory container)
	{
		return recipe.assemble(container);
	}

	@Override
	public int getProcessTime()
	{
		return recipe.getCookingTime();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(AbstractMachineInventory container)
	{
		return recipe.getRemainingItems(container);
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return recipe.getIngredients();
	}

	@Override
	public List<Pair<Ingredient, Integer>> getIngredientsWithCount()
	{
		return getIngredients().stream().collect(Collectors.toMap(Function.identity(), o -> 1)).entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue())).collect(Collectors.toList());
	}

	@Override
	public boolean isSpecial()
	{
		return recipe.isSpecial();
	}

	@Override
	public String getGroup()
	{
		return recipe.getGroup();
	}

	@Override
	public ItemStack getToastSymbol()
	{
		return recipe.getToastSymbol();
	}

	@Override
	public boolean isIncomplete()
	{
		return recipe.isIncomplete();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return recipe.canCraftInDimensions(width, height);
	}

	@Override
	public ItemStack getResultItem()
	{
		return recipe.getResultItem();
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return recipe.getSerializer();
	}

	@Override
	public RecipeType<?> getType()
	{
		return recipe.getType();
	}
}
