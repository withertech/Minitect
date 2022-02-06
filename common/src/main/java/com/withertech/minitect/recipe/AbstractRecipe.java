package com.withertech.minitect.recipe;

import com.mojang.datafixers.util.Pair;
import com.withertech.minitect.tile.AbstractMachineInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractRecipe implements Recipe<AbstractMachineInventory>
{
	protected final Map<ItemStack, Float> results = new LinkedHashMap<>();
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
		return this.getIngredients().stream().allMatch(container::testIngredient);
	}

	@Override
	public abstract NonNullList<Ingredient> getIngredients();

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
}
