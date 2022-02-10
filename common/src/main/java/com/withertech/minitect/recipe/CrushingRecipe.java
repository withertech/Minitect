package com.withertech.minitect.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.registry.MineRecipes;
import dev.architectury.core.AbstractRecipeSerializer;
import dev.architectury.registry.registries.Registries;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;
import java.util.function.Function;

public class CrushingRecipe extends AbstractRecipe
{

	public CrushingRecipe(ResourceLocation recipeId)
	{
		super(recipeId);
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return MineRecipes.Serializers.CRUSHING.get();
	}

	@Override
	public RecipeType<?> getType()
	{
		return MineRecipes.Types.CRUSHING;
	}

	public static class Serializer extends DefaultSerializer<CrushingRecipe>
	{
		@Override
		protected Function<ResourceLocation, CrushingRecipe> getFactory()
		{
			return CrushingRecipe::new;
		}
	}
}
