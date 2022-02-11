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

package com.withertech.minitect.compat.rei;

import com.withertech.minitect.recipe.AbstractRecipe;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractDisplay<T extends AbstractRecipe> extends BasicDisplay implements SimpleGridMenuDisplay
{
	protected final T recipe;

	public AbstractDisplay(T recipe)
	{
		this(recipe, createInputEntries(recipe), createOutputEntries(recipe), Optional.ofNullable(recipe.getId()));
	}

	public AbstractDisplay(T recipe, List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location)
	{
		super(inputs, outputs, location);
		this.recipe = recipe;
	}

	protected static List<EntryIngredient> createInputEntries(AbstractRecipe recipe)
	{
		return recipe.getIngredientsWithCount().stream().map((ingredientIntegerPair) ->
		{
			final EntryIngredient.Builder builder = EntryIngredient.builder(ingredientIntegerPair.getFirst().getItems().length);
			Arrays.stream(ingredientIntegerPair.getFirst().getItems()).forEach(stack -> builder.add(EntryStacks.of(stack.getItem(), ingredientIntegerPair.getSecond())));
			return builder.build();
		}).collect(Collectors.toList());
	}

	protected static List<EntryIngredient> createOutputEntries(AbstractRecipe recipe)
	{
		return recipe.getPossibleResultsWithChances().stream().map(itemStackFloatPair -> EntryIngredients.of(itemStackFloatPair.getFirst())).toList();
	}

	public Optional<T> getRecipe()
	{
		return Optional.ofNullable(recipe);
	}
}
