package com.withertech.minitect.data.recipe;

import com.withertech.mine_tags.tags.PlatformTags;
import com.withertech.minitect.recipe.CrushingRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class MineRecipeProviderFabric extends FabricRecipesProvider
{

	public MineRecipeProviderFabric(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator);
	}

	@Override
	protected void generateRecipes(Consumer<FinishedRecipe> consumer)
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
