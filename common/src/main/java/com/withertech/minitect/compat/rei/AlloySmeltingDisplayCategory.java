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

import com.google.common.collect.Lists;
import com.withertech.minitect.recipe.AlloySmeltingRecipe;
import com.withertech.minitect.registry.MineItems;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.List;

public class AlloySmeltingDisplayCategory extends AbstractDisplayCategory<AlloySmeltingDisplay, AlloySmeltingRecipe>
{
	@Override
	public Renderer getIcon()
	{
		return EntryStacks.of(MineItems.ALLOY_SMELTER.get());
	}

	@Override
	public Component getTitle()
	{
		return new TextComponent("Alloy Smelting");
	}

	@Override
	public CategoryIdentifier<? extends AlloySmeltingDisplay> getCategoryIdentifier()
	{
		return REIPluginCommon.ALLOY_SMELTING;
	}

	@Override
	public List<Widget> setupDisplay(AlloySmeltingDisplay display, Rectangle bounds)
	{
		Point startPoint = new Point(bounds.getCenterX() - 50, bounds.y + 5);
		final AlloySmeltingRecipe recipe = display.getRecipe().orElseThrow();
		List<Widget> widgets = Lists.newArrayList();
		widgets.add(Widgets.createCategoryBase(bounds));
		widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y))
				.entries(getInputStackOrEmpty(display, 0))
				.markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 18, startPoint.y))
				.entries(getInputStackOrEmpty(display, 1))
				.markInput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 36, startPoint.y))
				.entries(getInputStackOrEmpty(display, 2))
				.markInput());
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 54, startPoint.y))
				.animationDurationTicks(recipe.getProcessTime()));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 80, startPoint.y))
				.entries(getOutputStackOrEmpty(display, 0))
				.unmarkInputOrOutput());
		return widgets;
	}

	@Override
	protected int getHeight()
	{
		return 1;
	}
}
