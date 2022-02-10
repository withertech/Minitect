package com.withertech.minitect.compat;

import com.google.common.collect.Lists;
import com.withertech.minitect.recipe.CrushingRecipe;
import com.withertech.minitect.registry.MineItems;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CrushingDisplayCategory implements DisplayCategory<CrushingDisplay>
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
		Point startPoint = new Point(bounds.getCenterX() - 41, bounds.y + 10);
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

	private String makeChanceString(CrushingRecipe recipe, CrushingDisplay display, int index)
	{
		return (display.getOutputEntries().size() > index) ? (recipe.getPossibleResultsWithChances().get(index).getSecond() < 1) ? recipe.getPossibleResultsWithChances().get(index).getSecond() * 100f + "%" : "" : "";
	}

	private Collection<EntryStack<?>> getOutputStackOrEmpty(CrushingDisplay display, int index)
	{
		return (display.getOutputEntries().size() > index)?display.getOutputEntries().get(index): Collections.singletonList(EntryStacks.of(ItemStack.EMPTY));
	}

	private Collection<EntryStack<?>> getInputStackOrEmpty(CrushingDisplay display, int index)
	{
		return (display.getInputEntries().size() > index)?display.getInputEntries().get(index): Collections.singletonList(EntryStacks.of(ItemStack.EMPTY));
	}
}
