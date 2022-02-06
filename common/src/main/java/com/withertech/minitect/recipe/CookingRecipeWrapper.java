package com.withertech.minitect.recipe;

import com.withertech.minitect.tile.AbstractMachineInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Collections;
import java.util.List;

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
