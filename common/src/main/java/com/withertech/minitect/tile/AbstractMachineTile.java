package com.withertech.minitect.tile;

import com.mojang.datafixers.util.Pair;
import com.withertech.mine_flux.api.IMFContainer;
import com.withertech.mine_flux.api.IMFStorage;
import com.withertech.mine_gui.PropertyDelegateHolder;
import com.withertech.minitect.block.Connection;
import com.withertech.minitect.block.Face;
import com.withertech.minitect.block.MTFurnaceBlock;
import com.withertech.minitect.inventory.AbstractMachineInventory;
import com.withertech.minitect.recipe.AbstractRecipe;
import com.withertech.minitect.registry.MineRecipes;
import com.withertech.minitect.registry.MineUpgrades;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractMachineTile<T extends AbstractRecipe, R extends Recipe<Container>> extends BlockEntity implements MenuProvider, WorldlyContainerHolder, IMFContainer, PropertyDelegateHolder
{
	protected final UpgradeInventory upgrades = new UpgradeInventory();

	public AbstractMachineTile(BlockEntityType<? extends AbstractMachineTile<T, R>> blockEntityType, BlockPos blockPos, BlockState blockState)
	{
		super(blockEntityType, blockPos, blockState);
	}

	protected Connection getConnectionFromDirection(Direction side)
	{
		return getBlockState().getValue(MTFurnaceBlock.FACING_TO_PROPERTY_MAP.get(Face.getFaceFromDirection(side, getBlockState())));
	}

	public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState state, T t)
	{
		if (t instanceof AbstractMachineTile<?, ?> tile)
		{
			if (tile.getContainer(state, level, blockPos) instanceof AbstractMachineInventory container)
			{
				tile.tick(level, blockPos, state, container);
			}
		}
	}

	protected abstract void tick(Level level, BlockPos blockPos, BlockState state, AbstractMachineInventory container);

	@Override
	public Optional<IMFStorage> getStorageFor(Object that)
	{
		if (that == null) return getStorageFor(null);
		if (that instanceof Direction side)
		{
			return getStorageFor(side);
		}
		return Optional.empty();
	}

	public UpgradeInventory getUpgrades()
	{
		return upgrades;
	}

	protected int getMaxProgressDivisor()
	{
		return (int) Math.pow(4, Arrays.stream(UpgradeInventory.UPGRADE_SLOTS).mapToObj(upgrades::getItem).filter(stack -> stack.is(MineUpgrades.SPEED.asItem())).count() + 1);
	}

	protected int getEnergyConsumptionDivisor()
	{
		return (int) Math.pow(4, Arrays.stream(UpgradeInventory.UPGRADE_SLOTS).mapToObj(upgrades::getItem).filter(stack -> stack.is(MineUpgrades.EFFICIENCY.asItem())).count() + 1);
	}

	protected boolean canBurn(@Nullable T recipe, AbstractMachineInventory inv, int i)
	{
		Map<Integer, ItemStack> invIngredient = Util.make(new HashMap<>(), ingredient ->
		{
			Arrays.stream(inv.INPUT_SLOTS).forEach(value ->
			{
				if (inv.getItem(value) != ItemStack.EMPTY)
					ingredient.put(value, inv.getItem(value));
			});
		});
		if (!invIngredient.isEmpty() && recipe != null)
		{
			updateMaxProgress(inv);
			Map<Integer, ItemStack> recipeResult = Util.make(new HashMap<>(), result ->
			{
				final PrimitiveIterator.OfInt iterator = Arrays.stream(inv.OUTPUT_SLOTS).iterator();
				result.putAll(recipe.getResults().stream().collect(Collectors.toMap(stack ->
						iterator.next(), Function.identity())));
			});
			if (recipeResult.isEmpty())
			{
				return false;
			}
			else
			{
				Map<Integer, ItemStack> invResult = Util.make(new HashMap<>(), result ->
				{
					Arrays.stream(inv.OUTPUT_SLOTS).forEach(value ->
					{
						if (inv.getItem(value) != ItemStack.EMPTY)
							result.put(value, inv.getItem(value));
					});
				});
				if (invResult.isEmpty())
				{
					return true;
				}
				else return recipeResult.values().stream().allMatch(inv::canAddToOutput);
			}
		}
		else
		{
			return false;
		}
	}

	protected boolean burn(@Nullable T recipe, AbstractMachineInventory inv, int i)
	{
		if (recipe != null && canBurn(recipe, inv, i))
		{
			Map<Integer, ItemStack> invIngredient = Util.make(new HashMap<>(), ingredient ->
			{
				Arrays.stream(inv.INPUT_SLOTS).forEach(value ->
				{
					if (inv.getItem(value) != ItemStack.EMPTY && recipe.getIngredients().stream().anyMatch(ing -> ing.test(inv.getItem(value))))
						ingredient.put(value, inv.getItem(value));
				});
			});
			Map<Ingredient, Integer> recipeCounts = Util.make(new HashMap<>(), ingredient ->
			{
				ingredient.putAll(recipe.getIngredientsWithCount().stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));
			});
			Map<Integer, ItemStack> recipeResult = Util.make(new HashMap<>(), result ->
			{
				final PrimitiveIterator.OfInt iterator = Arrays.stream(inv.OUTPUT_SLOTS).iterator();
				result.putAll(recipe.getResults().stream().collect(Collectors.toMap(stack ->
						iterator.next(), Function.identity())));
			});

			if (recipeResult.values().stream().allMatch(inv::canAddToOutput))
			{
				recipeResult.forEach((index, stack) ->
				{
					inv.addItemToOutput(stack);
				});
				invIngredient.forEach((index, stack) ->
				{
					recipeCounts.entrySet().stream().filter(entry -> entry.getKey().test(stack) && stack.getCount() >= entry.getValue()).findFirst().ifPresent(entry ->
					{
						inv.removeItem(index, entry.getValue());
					});
				});
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void load(CompoundTag tag)
	{
		if (!tag.contains("Upgrades")) tag.put("Upgrades", upgrades.createTag());
		upgrades.fromTag(tag.getList("Upgrades", Tag.TAG_COMPOUND));
		super.load(tag);
	}

	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		tag.put("Upgrades", upgrades.createTag());
		super.saveAdditional(tag);
	}

	protected void updateCrafting(Level level, BlockPos blockPos, BlockState state, AbstractMachineInventory container)
	{
		final T recipe;
		if (getRecipeWrapperType() != null)
			recipe = getRecipeWrapperType().getRecipe(level, container);
		else recipe = level.getRecipeManager().getRecipeFor(getRecipeType(), container, level).orElse(null);
		int i = container.getMaxStackSize();
		if (recipe != null)
		{
			getStorageFor(null).ifPresent(energy ->
			{
				if (energy.getEnergyStored() > getMaxProgress() * (100 / getEnergyConsumptionDivisor()) && canBurn(recipe, container, i))
				{
					increaseProgress();
					energy.extractEnergy((100 * getMaxProgressDivisor()) / getEnergyConsumptionDivisor(), false);
					if (getProgress() == getMaxProgress())
					{
						setProgress(0);
						burn(recipe, container, i);
						setChanged(level, blockPos, state);
					}
				}
				else
				{
					setProgress(0);
				}
			});
		}
		else
		{
			setProgress(0);
		}

	}

	protected abstract Optional<IMFStorage> getStorageFor(@Nullable Direction side);

	protected abstract RecipeType<T> getRecipeType();

	protected MineRecipes.Types.MineRecipeTypeWrapper<T, R, Container> getRecipeWrapperType()
	{
		return null;
	}

	protected int getMaxProgressForRecipe(Level level, AbstractMachineInventory container)
	{
		return level.getRecipeManager().getRecipeFor(getRecipeType(), container, level).map(AbstractRecipe::getProcessTime).orElse(200);
	}

	protected abstract void setMaxProgress(int newTime);
	protected abstract void setProgress(int newTime);
	protected abstract int getMaxProgress();
	protected abstract int getProgress();

	protected void increaseProgress()
	{
		setProgress(getProgress()+1);
	}

	protected void updateMaxProgress(AbstractMachineInventory container)
	{
		setMaxProgress(getMaxProgressForRecipe(Objects.requireNonNull(getLevel()), container) / getMaxProgressDivisor());
	}

	public static class UpgradeInventory extends SimpleContainer
	{
		public static final int UPGRADE_SIZE = 2;
		public static final int[] UPGRADE_SLOTS = IntStream.range(0, UPGRADE_SIZE).toArray();

		public UpgradeInventory()
		{
			super(2);
		}
	}
}
