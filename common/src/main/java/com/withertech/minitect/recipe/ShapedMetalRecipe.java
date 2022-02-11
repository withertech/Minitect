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
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.Nullable;

public class ShapedMetalRecipe extends ShapedDamageItemRecipe
{
	public ShapedMetalRecipe(ShapedRecipe recipe)
	{
		super(recipe);
	}

	public static class Serializer extends AbstractRecipeSerializer<ShapedMetalRecipe>
	{
		@Override
		public ShapedMetalRecipe fromJson(ResourceLocation recipeId, JsonObject json)
		{
			return new ShapedMetalRecipe(ShapedDamageItemRecipe.SERIALIZER.fromJson(recipeId, json));
		}

		@Nullable
		@Override
		public ShapedMetalRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
		{
			ShapedDamageItemRecipe read = ShapedDamageItemRecipe.SERIALIZER.fromNetwork(recipeId, buffer);
			return read != null ? new ShapedMetalRecipe(read) : null;
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, ShapedMetalRecipe recipe)
		{
			ShapedDamageItemRecipe.SERIALIZER.toNetwork(buffer, recipe);
		}
	}
}
