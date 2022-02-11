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

import com.withertech.minitect.registry.MineRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Function;

public class AlloySmeltingRecipe extends AbstractRecipe
{

	public AlloySmeltingRecipe(ResourceLocation recipeId)
	{
		super(recipeId);
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return MineRecipes.Serializers.ALLOY_SMELTING.get();
	}

	@Override
	public RecipeType<?> getType()
	{
		return MineRecipes.Types.ALLOY_SMELTING;
	}

	public static class Serializer extends DefaultSerializer<AlloySmeltingRecipe>
	{
		@Override
		protected Function<ResourceLocation, AlloySmeltingRecipe> getFactory()
		{
			return AlloySmeltingRecipe::new;
		}
	}
}
