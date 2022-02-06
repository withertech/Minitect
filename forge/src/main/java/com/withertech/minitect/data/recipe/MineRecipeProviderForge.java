package com.withertech.minitect.data.recipe;

import com.withertech.mine_tags.tags.PlatformTags;
import com.withertech.minitect.recipe.CrushingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MineRecipeProviderForge extends RecipeProvider
{
	public MineRecipeProviderForge(DataGenerator arg)
	{
		super(arg);
	}

	@Override
	protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer)
	{
		CrushingRecipeBuilder.builder(PlatformTags.Items.INGOTS_IRON)
				.result(Items.APPLE, 1)
				.build(consumer);

		CrushingRecipeBuilder.builder(PlatformTags.Items.BOOKSHELVES)
				.result(Items.BOOK, 2)
				.result(Items.BOOK, 1, 0.5f)
				.build(consumer);
	}
}
