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

package com.withertech.minitect.data.recipe;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.recipe.AlloySmeltingRecipeBuilder;
import com.withertech.minitect.recipe.CrushingRecipeBuilder;
import com.withertech.minitect.recipe.ExtendedShapedRecipeBuilder;
import com.withertech.minitect.recipe.ExtendedShapelessRecipeBuilder;
import com.withertech.minitect.registry.MineCraftingTools;
import com.withertech.minitect.registry.MineGems;
import com.withertech.minitect.registry.MineMetals;
import com.withertech.minitect.registry.MineRecipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

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
		registerAlloyCrafting(consumer);
		registerMetalCrafting(consumer);
		registerGemCrafting(consumer);
		registerMetalCrushing(consumer);
		registerGemCrushing(consumer);
		registerMetalSmelting(consumer);
	}

	private void registerAlloyCrafting(Consumer<FinishedRecipe> consumer)
	{
		for (MineMetals metal : MineMetals.values())
		{
			if (metal.getAlloy().isPresent() && metal.getDust().isPresent())
			{
				ExtendedShapelessRecipeBuilder builder = ExtendedShapelessRecipeBuilder.vanillaBuilder(metal.getDust().get(), metal.getAlloy().get().getResultCount());
				metal.getAlloy().get().getDustIngredients().forEach(builder::requires);
				builder.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/dust"));
			}
			if (metal.getAlloy().isPresent() && metal.getIngot().isPresent())
			{
				AlloySmeltingRecipeBuilder.alloyIngot(metal, 400)
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/alloy_smelting/ingot"));
				AlloySmeltingRecipeBuilder.alloyDust(metal, 400)
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/alloy_smelting/ingot_from_dust"));
			}
		}
	}

	private void registerMetalCrafting(Consumer<FinishedRecipe> consumer)
	{
		for (MineMetals metal : MineMetals.values())
		{
			if (metal.getIngot(true).isPresent() && metal.getNuggetTag().isPresent())
			{
				ExtendedShapedRecipeBuilder.vanillaBuilder(metal.getIngot(true).get())
						.pattern("###")
						.pattern("###")
						.pattern("###")
						.define('#', metal.getNuggetTag().get())
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/ingot_from_nugget"));
			}
			if (metal.getNugget(true).isPresent() && metal.getIngotTag().isPresent())
			{
				ExtendedShapelessRecipeBuilder.vanillaBuilder(metal.getNugget(true).get(), 9)
						.requires(metal.getIngotTag().get())
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/nugget"));
			}
			if (metal.getStorageBlockItem(true).isPresent() && metal.getIngotTag().isPresent())
			{
				ExtendedShapedRecipeBuilder.vanillaBuilder(metal.getStorageBlockItem(true).get())
						.pattern("###")
						.pattern("###")
						.pattern("###")
						.define('#', metal.getIngotTag().get())
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/block"));
			}
			if (metal.getIngot(true).isPresent() && metal.getStorageBlockItemTag().isPresent())
			{
				ExtendedShapelessRecipeBuilder.vanillaBuilder(metal.getIngot(true).get(), 9)
						.requires(metal.getStorageBlockItemTag().get())
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/ingot_from_block"));
			}
			if (metal.getPlate(true).isPresent() && metal.getIngotTag().isPresent())
			{
				ExtendedShapedRecipeBuilder.builder(MineRecipes.Serializers.SHAPED_METAL.get(), metal.getPlate(true).get())
						.pattern("T")
						.pattern("#")
						.define('T', MineCraftingTools.HAMMER)
						.define('#', metal.getIngotTag().get())
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/plate"));
			}
			if (metal.getRod(true).isPresent() && metal.getIngotTag().isPresent())
			{
				ExtendedShapedRecipeBuilder.builder(MineRecipes.Serializers.SHAPED_METAL.get(), metal.getRod(true).get())
						.pattern("T")
						.pattern("#")
						.define('T', MineCraftingTools.FILE)
						.define('#', metal.getIngotTag().get())
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/rod"));
			}
			if (metal.getGear().isPresent() && metal.getIngotTag().isPresent())
			{
				ExtendedShapedRecipeBuilder.builder(MineRecipes.Serializers.SHAPED_METAL.get(), metal.getGear().get(), 2)
						.pattern("$#%")
						.pattern("# #")
						.pattern(" # ")
						.define('#', metal.getIngotTag().get())
						.define('$', MineCraftingTools.HAMMER)
						.define('%', MineCraftingTools.FILE)
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/gear"));
			}
		}
	}

	private void registerGemCrafting(Consumer<FinishedRecipe> consumer)
	{
		for (MineGems gem : MineGems.values())
		{
			if (gem.getStorageBlockItem(true).isPresent() && gem.getGemTag().isPresent())
			{
				ExtendedShapedRecipeBuilder.vanillaBuilder(gem.getStorageBlockItem(true).get())
						.pattern("###")
						.pattern("###")
						.pattern("###")
						.define('#', gem.getGemTag().get())
						.save(consumer, Minitect.modLoc("materials/" + gem.getName() + "/block"));
			}
			if (gem.getGem(true).isPresent() && gem.getStorageBlockItemTag().isPresent())
			{
				ExtendedShapelessRecipeBuilder.vanillaBuilder(gem.getGem(true).get(), 9)
						.requires(gem.getStorageBlockItemTag().get())
						.save(consumer, Minitect.modLoc("materials/" + gem.getName() + "/gem_from_block"));
			}
		}
	}

	private void registerMetalCrushing(Consumer<FinishedRecipe> consumer)
	{
		for (MineMetals metal : MineMetals.values())
		{
			if (metal.getDust().isPresent() && metal.getIngotTag().isPresent())
			{
				CrushingRecipeBuilder.crushingIngot(metal.getIngotTag().get(), metal.getDust(true).get(), 400)
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/crushing/dust_from_ingot"));
			}
			if (metal.getDust().isPresent() && metal.getOreItemTag().isPresent())
			{
				CrushingRecipeBuilder.crushingOre(metal.getOreItemTag().get(), metal.getDust(true).get(), 400, metal.getDust(true).get(), 0.5f)
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/crushing/dust_from_ore"));
			}
			if (metal.getDust().isPresent() && metal.getRawTag().isPresent())
			{
				CrushingRecipeBuilder.crushingOre(metal.getRawTag().get(), metal.getDust().get(), 400, metal.getDust().get(), 0.5f)
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/crushing/dust_from_raw"));
			}
		}
	}

	public void registerGemCrushing(Consumer<FinishedRecipe> consumer)
	{
		for (MineGems gem : MineGems.values())
		{
			if (gem.getGem().isPresent() && gem.getOreItemTag().isPresent())
			{
				CrushingRecipeBuilder.crushingOre(gem.getOreItemTag().get(), gem.getGem().get(), 400, gem.getGem().get(), 0.5f)
						.save(consumer, Minitect.modLoc("materials/" + gem.getName() + "/crushing/gem_from_ore"));
			}
			if (gem.getDust().isPresent() && gem.getGemTag().isPresent())
			{
				CrushingRecipeBuilder.crushingIngot(gem.getGemTag().get(), gem.getDust().get(), 400)
						.save(consumer, Minitect.modLoc("materials/" + gem.getName() + "/crushing/dust_from_gem"));
			}
		}
		MineGems.GLOWSTONE.getGem().ifPresent(gem ->
		{
			CrushingRecipeBuilder.crushingOre(Items.GLOWSTONE, gem, 400, gem, 0.5f)
					.save(consumer, Minitect.modLoc("materials/" + MineGems.GLOWSTONE.getName() + "/crushing/gem_from_ore"));
		});
	}

	private void registerMetalSmelting(Consumer<FinishedRecipe> consumer)
	{
		for (MineMetals metal : MineMetals.values())
		{
			if (metal.getIngot().isPresent() && metal.getDustTag().isPresent())
			{
				SimpleCookingRecipeBuilder.smelting(Ingredient.of(metal.getDustTag().get()), metal.getIngot().get(), 1f, 200)
						.unlockedBy("has_item", has(metal.getDustTag().get()))
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/smelting/ingot_from_dust"));
				SimpleCookingRecipeBuilder.blasting(Ingredient.of(metal.getDustTag().get()), metal.getIngot().get(), 1f, 100)
						.unlockedBy("has_item", has(metal.getDustTag().get()))
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/blasting/ingot_from_dust"));
			}
			if (metal.getIngot().isPresent() && metal.getOreItemTag().isPresent() && !metal.isVanilla())
			{
				SimpleCookingRecipeBuilder.smelting(Ingredient.of(metal.getOreItemTag().get()), metal.getIngot().get(), 1f, 200)
						.unlockedBy("has_item", has(metal.getOreItemTag().get()))
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/smelting/ingot_from_ore"));
				SimpleCookingRecipeBuilder.blasting(Ingredient.of(metal.getOreItemTag().get()), metal.getIngot().get(), 1f, 100)
						.unlockedBy("has_item", has(metal.getOreItemTag().get()))
						.save(consumer, Minitect.modLoc("materials/" + metal.getName() + "/blasting/ingot_from_ore"));

			}

		}

	}
}
