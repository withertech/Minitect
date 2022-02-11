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
import com.withertech.minitect.recipe.CrushingRecipe;
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

public class CrushingDisplayCategory extends AbstractDisplayCategory<CrushingDisplay, CrushingRecipe>
{
	@Override
	public Renderer getIcon()
	{
		return EntryStacks.of(MineItems.CRUSHER.get());
	}

	@Override
	public Component getTitle()
	{
		return new TextComponent("Crushing");
	}

	@Override
	public CategoryIdentifier<? extends CrushingDisplay> getCategoryIdentifier()
	{
		return REIPluginCommon.CRUSHING;
	}

	@Override
	public List<Widget> setupDisplay(CrushingDisplay display, Rectangle bounds)
	{
		Point startPoint = new Point(bounds.getCenterX() - 41, bounds.y + 5);
		final CrushingRecipe recipe = display.getRecipe().orElseThrow();
		List<Widget> widgets = Lists.newArrayList();
		widgets.add(Widgets.createCategoryBase(bounds));
		widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y + 18))
				.entries(getInputStackOrEmpty(display, 0))
				.markInput());
		widgets.add(Widgets.createArrow(new Point(startPoint.x + 18, startPoint.y + 18))
				.animationDurationTicks(recipe.getProcessTime()));
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 44, startPoint.y))
				.entries(getOutputStackOrEmpty(display, 0))
				.unmarkInputOrOutput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 44, startPoint.y + 18))
				.entries(getOutputStackOrEmpty(display, 1))
				.unmarkInputOrOutput());
		widgets.add(Widgets.createSlot(new Point(startPoint.x + 44, startPoint.y + 36))
				.entries(getOutputStackOrEmpty(display, 2))
				.unmarkInputOrOutput());
		widgets.add(Widgets.createLabel(new Point(startPoint.x + 62, startPoint.y + 6), new TextComponent(makeChanceString(recipe, display, 0))).leftAligned());
		widgets.add(Widgets.createLabel(new Point(startPoint.x + 62, startPoint.y + 18 + 6), new TextComponent(makeChanceString(recipe, display, 1))).leftAligned());
		widgets.add(Widgets.createLabel(new Point(startPoint.x + 62, startPoint.y + 36 + 6), new TextComponent(makeChanceString(recipe, display, 2))).leftAligned());
		return widgets;
	}

	@Override
	protected int getHeight()
	{
		return 3;
	}
}
