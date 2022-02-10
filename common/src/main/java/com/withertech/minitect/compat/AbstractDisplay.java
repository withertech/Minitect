package com.withertech.minitect.compat;

import com.withertech.minitect.recipe.AbstractRecipe;
import com.withertech.minitect.recipe.CrushingRecipe;
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
