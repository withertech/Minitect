package com.withertech.minitect.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.registry.MineRecipes;
import dev.architectury.registry.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class CrushingRecipeBuilder
{
	private final Map<ItemStack, Float> results = new LinkedHashMap<>();
	private final Ingredient ingredient;
	private final int count;
	private final int processTime;

	private CrushingRecipeBuilder(Ingredient ingredient, int count, int processTime)
	{
		this.ingredient = ingredient;
		this.count = count;
		this.processTime = processTime;
	}

	public static CrushingRecipeBuilder builder(ItemLike ingredient, int count)
	{
		return builder(ingredient, count, 400);
	}

	public static CrushingRecipeBuilder builder(ItemLike ingredient, int count, int processTime)
	{
		return builder(Ingredient.of(ingredient), count, processTime);
	}

	public static CrushingRecipeBuilder builder(Tag<Item> ingredient, int count)
	{
		return builder(ingredient, count, 400);
	}

	public static CrushingRecipeBuilder builder(Tag<Item> ingredient, int count, int processTime)
	{
		return builder(Ingredient.of(ingredient), count, processTime);
	}

	public static CrushingRecipeBuilder builder(Ingredient ingredient, int count)
	{
		return builder(ingredient, count, 400);
	}

	public static CrushingRecipeBuilder builder(Ingredient ingredient, int count, int processTime)
	{
		return new CrushingRecipeBuilder(ingredient, count, processTime);
	}

	public static CrushingRecipeBuilder crushingIngot(Tag<Item> ingot, ItemLike dust, int processTime)
	{
		return builder(ingot, 1, processTime)
				.result(dust, 1);
	}

	public static CrushingRecipeBuilder crushingOre(Tag<Item> ore, ItemLike dust, int processTime, @Nullable ItemLike extra, float extraChance)
	{
		CrushingRecipeBuilder builder = builder(ore, 1, processTime);
		builder.result(dust, 2);
		if (extra != null)
		{
			builder.result(extra, 1, extraChance);
		}
		return builder;
	}

	public static CrushingRecipeBuilder crushingOre(ItemLike ore, ItemLike dust, int processTime, @Nullable ItemLike extra, float extraChance)
	{
		CrushingRecipeBuilder builder = builder(ore, 1, processTime);
		builder.result(dust, 2);
		if (extra != null)
		{
			builder.result(extra, 1, extraChance);
		}
		return builder;
	}

	public CrushingRecipeBuilder result(ItemLike item, int count, float chance)
	{
		results.put(new ItemStack(item, count), chance);
		return this;
	}

	public CrushingRecipeBuilder result(ItemLike item, int count)
	{
		return result(item, count, 1f);
	}

	public void save(Consumer<FinishedRecipe> consumer)
	{
		ResourceLocation resultId = Registries.getId(results.keySet().iterator().next().getItem(), Registry.ITEM_REGISTRY);
		ResourceLocation id = new ResourceLocation(
				"minecraft".equals(resultId.getNamespace()) ? Minitect.MOD_ID : resultId.getNamespace(),
				"crushing/" + resultId.getPath());
		save(consumer, id);
	}

	public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id)
	{
		consumer.accept(new Result(id, this));
	}

	public static class Result implements FinishedRecipe
	{
		private final ResourceLocation id;
		private final CrushingRecipeBuilder builder;

		public Result(ResourceLocation id, CrushingRecipeBuilder builder)
		{
			this.id = id;
			this.builder = builder;
		}

		private JsonObject serializeResult(ItemStack stack, float chance)
		{
			JsonObject json = new JsonObject();
			json.addProperty("item", Registries.getId(stack.getItem(), Registry.ITEM_REGISTRY).toString());
			if (stack.getCount() > 1)
			{
				json.addProperty("count", stack.getCount());
			}
			if (chance < 1)
			{
				json.addProperty("chance", chance);
			}
			return json;
		}

		private JsonObject serializeIngredient(Ingredient ingredient, int count)
		{
			JsonObject json = ingredient.toJson().getAsJsonObject();
			if (count > 1)
			{
				json.addProperty("count", count);
			}
			return json;
		}

		@Override
		public void serializeRecipeData(JsonObject json)
		{
			json.addProperty("process_time", builder.processTime);
			JsonArray ingredients = new JsonArray();

			ingredients.add(serializeIngredient(builder.ingredient, builder.count));
			json.add("ingredients", ingredients);

			JsonArray results = new JsonArray();
			builder.results.forEach((stack, chance) ->
					results.add(serializeResult(stack, chance)));
			json.add("results", results);
		}

		@Override
		public ResourceLocation getId()
		{
			return id;
		}

		@Override
		public RecipeSerializer<?> getType()
		{
			return MineRecipes.Serializers.CRUSHING.get();
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement()
		{
			return null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId()
		{
			return null;
		}
	}
}
