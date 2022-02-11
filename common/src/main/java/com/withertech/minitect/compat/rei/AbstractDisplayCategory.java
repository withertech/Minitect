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

package com.withertech.minitect.compat.rei;

import com.withertech.minitect.recipe.AbstractRecipe;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Collections;

public abstract class AbstractDisplayCategory<T extends AbstractDisplay<R>, R extends AbstractRecipe> implements DisplayCategory<T>
{
	protected String makeChanceString(R recipe, T display, int index)
	{
		return (display.getOutputEntries().size() > index) ? (recipe.getPossibleResultsWithChances().get(index).getSecond() < 1) ? recipe.getPossibleResultsWithChances().get(index).getSecond() * 100f + "%" : "" : "";
	}

	protected Collection<EntryStack<?>> getOutputStackOrEmpty(T display, int index)
	{
		return (display.getOutputEntries().size() > index) ? display.getOutputEntries().get(index) : Collections.singletonList(EntryStacks.of(ItemStack.EMPTY));
	}

	protected Collection<EntryStack<?>> getInputStackOrEmpty(T display, int index)
	{
		return (display.getInputEntries().size() > index) ? display.getInputEntries().get(index) : Collections.singletonList(EntryStacks.of(ItemStack.EMPTY));
	}

	@Override
	public int getDisplayHeight()
	{
		return (getHeight() * 18) + 10;
	}

	protected abstract int getHeight();
}