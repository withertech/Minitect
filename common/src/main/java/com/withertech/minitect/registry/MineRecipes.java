package com.withertech.minitect.registry;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.recipe.AbstractRecipe;
import com.withertech.minitect.recipe.CookingRecipeWrapper;
import com.withertech.minitect.recipe.CrushingRecipe;
import dev.architectury.core.RegistryEntry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

public class MineRecipes
{
	public static void register()
	{
		Types.register();
		Serializers.register();
	}

	public static class Serializers
	{
		public static final RegistrySupplier<RecipeSerializer<CrushingRecipe>> CRUSHING = MineRegistries.RECIPE_SERIALIZERS.register("crushing", CrushingRecipe.Serializer::new);
		public static void register()
		{

		}
	}

	public static class Types
	{
		public static final MineRecipeType<CrushingRecipe> CRUSHING = register("crushing");
		public static final MineRecipeTypeWrapper<CookingRecipeWrapper, SmeltingRecipe, Container> SMELTING = new MineRecipeTypeWrapper<>(RecipeType.SMELTING)
		{
			@Override
			public @NotNull CookingRecipeWrapper createWrapper(SmeltingRecipe recipe)
			{
				return new CookingRecipeWrapper(recipe.getId(), recipe);
			}
		};

		public static void register()
		{

		}

		private static <T extends AbstractRecipe> MineRecipeType<T> register(String id)
		{
			return Registry.register(Registry.RECIPE_TYPE, Minitect.modLoc(id), new MineRecipeType<>());
		}

		public abstract static class MineRecipeTypeWrapper<T extends AbstractRecipe,R extends Recipe<C>, C extends Container> implements RecipeType<T>
		{
			private final RecipeType<R> type;

			public RecipeType<R> getType()
			{
				return type;
			}

			public <X extends C> T getRecipe(Level level, X container)
			{
				return level.getRecipeManager().getRecipeFor(type, container, level).map(this::createWrapper).orElse(null);
			}

			private MineRecipeTypeWrapper(RecipeType<R> type)
			{
				this.type = type;
			}

			@Override
			public <C extends Container> Optional<T> tryMatch(Recipe<C> recipe, Level level, C container)
			{
				return type.tryMatch(recipe, level, container).map(this::createWrapper);
			}

			@NotNull
			public abstract T createWrapper(R recipe);

			@Override
			public String toString()
			{
				return Registry.RECIPE_TYPE.getKey(type).toString();
			}
		}

		public static class MineRecipeType<T extends AbstractRecipe> extends RegistryEntry<RecipeType<T>> implements RecipeType<T>
		{
			public String toString()
			{
				return Registry.RECIPE_TYPE.getKey(this).toString();
			}
		}
	}

}
