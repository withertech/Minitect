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

import com.withertech.minitect.Minitect;
import net.minecraft.core.NonNullList;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

public class ShapelessDamageItemRecipe extends ExtendedShapelessRecipe
{
	public static final ExtendedShapelessRecipe.Serializer<ShapelessDamageItemRecipe> SERIALIZER = new ExtendedShapelessRecipe.Serializer<>(
			ShapelessDamageItemRecipe::new,
			(json, recipe) -> recipe.damageToItems = GsonHelper.getAsInt(json, "damage", 1),
			(buffer, recipe) -> recipe.damageToItems = buffer.readVarInt(),
			(buffer, recipe) -> buffer.writeVarInt(recipe.damageToItems)
	);

	private int damageToItems = 1;

	public ShapelessDamageItemRecipe(ShapelessRecipe recipe)
	{
		super(recipe);
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return SERIALIZER;
	}

	@Override
	public boolean matches(CraftingContainer inv, Level worldIn)
	{
		return getBaseRecipe().matches(inv, worldIn);
	}

	@Override
	public ItemStack assemble(CraftingContainer inv)
	{
		return getBaseRecipe().assemble(inv);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv)
	{
		NonNullList<ItemStack> list = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

		for (int i = 0; i < list.size(); ++i)
		{
			ItemStack stack = inv.getItem(i);

			if (stack.getMaxDamage() > 0)
			{
				ItemStack tool = stack.copy();
				if (tool.hurt(this.damageToItems, Minitect.RANDOM, null))
				{
					tool.shrink(1);
				}
				list.set(i, tool);
			}
		}

		return list;
	}
}