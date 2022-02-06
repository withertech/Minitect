package com.withertech.minitect.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.registry.MineRecipes;
import com.withertech.minitect.tile.AbstractMachineInventory;
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
import net.minecraft.world.level.Level;

public class CrushingRecipe extends AbstractRecipe
{

	protected Ingredient ingredient;

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

	public Ingredient getIngredient()
	{
		return ingredient;
	}

	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(Ingredient.EMPTY, getIngredient());
	}

	public static class Serializer extends AbstractRecipeSerializer<CrushingRecipe>
	{

		@Override
		public CrushingRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe)
		{
			CrushingRecipe recipe = new CrushingRecipe(recipeId);
			recipe.processTime = GsonHelper.getAsInt(serializedRecipe, "process_time", 400);
			recipe.ingredient = Ingredient.fromJson(serializedRecipe.get("ingredient"));
			JsonArray resultsArray = serializedRecipe.getAsJsonArray("results");
			resultsArray.forEach(jsonElement ->
			{
				JsonObject obj = jsonElement.getAsJsonObject();
				String id = GsonHelper.getAsString(obj, "item");
				Item item = Registries.get(Minitect.MOD_ID).get(Registry.ITEM_REGISTRY).get(ResourceLocation.tryParse(id));
				int count = GsonHelper.getAsInt(obj, "count", 1);
				ItemStack stack = new ItemStack(item, count);
				float chance = GsonHelper.getAsFloat(obj, "chance", 1);
				recipe.results.put(stack, chance);
			});
			return recipe;
		}

		@Override
		public CrushingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
		{
			CrushingRecipe recipe = new CrushingRecipe(recipeId);
			recipe.processTime = buffer.readVarInt();
			recipe.ingredient = Ingredient.fromNetwork(buffer);
			int resultCount = buffer.readByte();
			for (int i = 0; i < resultCount; ++i)
			{
				ResourceLocation id = buffer.readResourceLocation();
				int count = buffer.readVarInt();
				float chance = buffer.readFloat();
				Item item = Registries.get(Minitect.MOD_ID).get(Registry.ITEM_REGISTRY).get(id);
				recipe.results.put(new ItemStack(item, count), chance);
			}
			return recipe;
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, CrushingRecipe recipe)
		{
			buffer.writeVarInt(recipe.getProcessTime());
			recipe.ingredient.toNetwork(buffer);
			buffer.writeByte(recipe.results.size());
			recipe.results.forEach((stack, chance) ->
			{
				buffer.writeResourceLocation(Registries.getId(stack.getItem(), Registry.ITEM_REGISTRY));
				buffer.writeVarInt(stack.getCount());
				buffer.writeFloat(chance);
			});
		}
	}
}
