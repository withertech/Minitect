package com.withertech.minitect.inventory;

import com.google.common.collect.Streams;
import com.withertech.minitect.block.AbstractMachineBlock;
import com.withertech.minitect.block.Connection;
import com.withertech.minitect.block.Face;
import com.withertech.minitect.block.MTFurnaceBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;

public abstract class AbstractMachineInventory extends SimpleContainer implements WorldlyContainer
{
	public final int[] INPUT_SLOTS;
	public final int[] OUTPUT_SLOTS;

	public AbstractMachineInventory(int i, int[] input_slots, int[] output_slots)
	{
		super(i);
		INPUT_SLOTS = input_slots;
		OUTPUT_SLOTS = output_slots;
	}

	protected Connection getConnectionFromDirection(Direction side)
	{
		return getBlockState().getValue(AbstractMachineBlock.FACING_TO_PROPERTY_MAP.get(Face.getFaceFromDirection(side, getBlockState())));
	}

	public int[] getSlotsForFace(Direction side)
	{
		if (side == null) return Streams.concat(Arrays.stream(INPUT_SLOTS), Arrays.stream(OUTPUT_SLOTS)).toArray();
		final Connection connection = getConnectionFromDirection(side);
		if (connection.TYPE == Connection.Type.ITEM && connection.MODE == Connection.Mode.INPUT)
		{
			return INPUT_SLOTS;
		} else if (connection.TYPE == Connection.Type.ITEM && connection.MODE == Connection.Mode.OUTPUT)
		{
			return OUTPUT_SLOTS;
		}
		return new int[0];
	}

	public boolean testIngredient(Ingredient ingredient, int count)
	{
		return Arrays.stream(INPUT_SLOTS).mapToObj(this::getItem).anyMatch(stack -> ingredient.test(stack) && stack.getCount() >= count);
	}

	public boolean canAddToInput(ItemStack stack)
	{
		boolean bl = false;
		Iterator<ItemStack> var3 = Arrays.stream(INPUT_SLOTS).mapToObj(this::getItem).iterator();

		while(var3.hasNext())
		{
			ItemStack itemStack = var3.next();
			if (itemStack.isEmpty() || ItemStack.isSameItemSameTags(itemStack, stack) && itemStack.getCount() < itemStack.getMaxStackSize())
			{
				bl = true;
				break;
			}
		}

		return bl;
	}

	public boolean canAddToOutput(ItemStack stack)
	{
		boolean bl = false;
		Iterator<ItemStack> var3 = Arrays.stream(OUTPUT_SLOTS).mapToObj(this::getItem).iterator();

		while(var3.hasNext())
		{
			ItemStack itemStack = var3.next();
			if (itemStack.isEmpty() || ItemStack.isSameItemSameTags(itemStack, stack) && itemStack.getCount() < itemStack.getMaxStackSize())
			{
				bl = true;
				break;
			}
		}

		return bl;
	}

	public ItemStack addItemToOutput(ItemStack stack)
	{
		ItemStack itemStack = stack.copy();
		this.moveItemToOccupiedSlotsInOutputWithSameType(itemStack);
		if (itemStack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.moveItemToEmptySlotsInOutput(itemStack);
			return itemStack.isEmpty() ? ItemStack.EMPTY : itemStack;
		}
	}

	public ItemStack addItemToInput(ItemStack stack)
	{
		ItemStack itemStack = stack.copy();
		this.moveItemToOccupiedSlotsInInputWithSameType(itemStack);
		if (itemStack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.moveItemToEmptySlotsInInput(itemStack);
			return itemStack.isEmpty() ? ItemStack.EMPTY : itemStack;
		}
	}

	private void moveItemToEmptySlotsInOutput(ItemStack stack)
	{
		for(int i : OUTPUT_SLOTS)
		{
			ItemStack itemStack = this.getItem(i);
			if (itemStack.isEmpty())
			{
				this.setItem(i, stack.copy());
				stack.setCount(0);
				return;
			}
		}
	}

	private void moveItemToEmptySlotsInInput(ItemStack stack)
	{
		for(int i : INPUT_SLOTS)
		{
			ItemStack itemStack = this.getItem(i);
			if (itemStack.isEmpty())
			{
				this.setItem(i, stack.copy());
				stack.setCount(0);
				return;
			}
		}
	}

	private void moveItemToOccupiedSlotsInOutputWithSameType(ItemStack stack)
	{
		for (int i : OUTPUT_SLOTS)
		{
			ItemStack itemStack = this.getItem(i);
			if (ItemStack.isSameItemSameTags(itemStack, stack))
			{
				this.moveItemsBetweenStacks(stack, itemStack);
				if (stack.isEmpty())
				{
					return;
				}
			}
		}
	}

	private void moveItemToOccupiedSlotsInInputWithSameType(ItemStack stack)
	{
		for (int i : INPUT_SLOTS)
		{
			ItemStack itemStack = this.getItem(i);
			if (ItemStack.isSameItemSameTags(itemStack, stack))
			{
				this.moveItemsBetweenStacks(stack, itemStack);
				if (stack.isEmpty())
				{
					return;
				}
			}
		}
	}

	private void moveItemsBetweenStacks(ItemStack itemStack, ItemStack itemStack2) {
		int i = Math.min(this.getMaxStackSize(), itemStack2.getMaxStackSize());
		int j = Math.min(itemStack.getCount(), i - itemStack2.getCount());
		if (j > 0) {
			itemStack2.grow(j);
			itemStack.shrink(j);
			this.setChanged();
		}

	}

	protected abstract BlockState getBlockState();

	public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction)
	{
		return Arrays.stream(INPUT_SLOTS).anyMatch(value -> value == index);
	}

	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction)
	{
		return Arrays.stream(OUTPUT_SLOTS).anyMatch(value -> value == index);
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack)
	{
		return Arrays.stream(INPUT_SLOTS).anyMatch(value -> value == index);
	}
}
