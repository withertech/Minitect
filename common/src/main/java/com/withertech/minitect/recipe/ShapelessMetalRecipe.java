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
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.Nullable;

public class ShapelessMetalRecipe extends ShapelessDamageItemRecipe
{
	public ShapelessMetalRecipe(ShapelessRecipe recipe)
	{
		super(recipe);
	}

	public static class Serializer extends AbstractRecipeSerializer<ShapelessMetalRecipe>
	{
		@Override
		public ShapelessMetalRecipe fromJson(ResourceLocation recipeId, JsonObject json)
		{
			return new ShapelessMetalRecipe(ShapelessDamageItemRecipe.SERIALIZER.fromJson(recipeId, json));
		}

		@Nullable
		@Override
		public ShapelessMetalRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
		{
			ShapelessDamageItemRecipe read = ShapelessDamageItemRecipe.SERIALIZER.fromNetwork(recipeId, buffer);
			return read != null ? new ShapelessMetalRecipe(read) : null;
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, ShapelessMetalRecipe recipe)
		{
			ShapelessDamageItemRecipe.SERIALIZER.toNetwork(buffer, recipe);
		}
	}
}
