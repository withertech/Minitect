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

import com.withertech.mine_gui.SyncedGuiDescription;
import com.withertech.mine_gui.client.MineGuiInventoryScreen;
import com.withertech.mine_gui.widget.WProgressBar;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.client.screen.MTAlloySmelterScreen;
import com.withertech.minitect.client.screen.MTCrusherScreen;
import com.withertech.minitect.client.screen.MTFurnaceScreen;
import com.withertech.minitect.container.ProgressContainer;
import com.withertech.minitect.recipe.AlloySmeltingRecipe;
import com.withertech.minitect.recipe.CrushingRecipe;
import com.withertech.minitect.recipe.ShapedMetalRecipe;
import com.withertech.minitect.recipe.ShapelessMetalRecipe;
import com.withertech.minitect.registry.MineCraftingTools;
import com.withertech.minitect.registry.MineItems;
import com.withertech.minitect.registry.MineRecipes;
import dev.architectury.utils.GameInstance;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategoryView;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.comparison.EntryComparator;
import me.shedaniel.rei.api.common.entry.comparison.ItemComparatorRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;

public abstract class REIPluginCommon implements REIClientPlugin
{
	public static final CategoryIdentifier<CrushingDisplay> CRUSHING = CategoryIdentifier.of(Minitect.MOD_ID, "plugins/crushing");
	public static final CategoryIdentifier<AlloySmeltingDisplay> ALLOY_SMELTING = CategoryIdentifier.of(Minitect.MOD_ID, "plugins/alloy_smelting");

	private static <T extends MineGuiInventoryScreen<C>, C extends SyncedGuiDescription> Rectangle clickArea(T screen)
	{
		if (screen.getMenu() instanceof ProgressContainer container)
		{
			if (container.showClickArea())
			{
				WProgressBar bar = container.getProgressPanel().getProgressBar();
				return new Rectangle(bar.getAbsoluteX(), bar.getAbsoluteY(), bar.getWidth(), bar.getHeight());
			}
		}
		return new Rectangle();
	}

	@Override
	public void registerCategories(CategoryRegistry registry)
	{
		registry.add(new CrushingDisplayCategory());
		registry.add(new AlloySmeltingDisplayCategory());

		registry.addWorkstations(BuiltinPlugin.SMELTING, EntryStacks.of(MineItems.FURNACE::get));
		registry.addWorkstations(CRUSHING, EntryStacks.of(MineItems.CRUSHER.get()));
		registry.addWorkstations(ALLOY_SMELTING, EntryStacks.of(MineItems.ALLOY_SMELTER.get()));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry)
	{
		registry.registerRecipeFiller(CrushingRecipe.class, MineRecipes.Types.CRUSHING, CrushingDisplay::new);
		registry.registerRecipeFiller(AlloySmeltingRecipe.class, MineRecipes.Types.ALLOY_SMELTING, AlloySmeltingDisplay::new);
	}

	@Override
	public void registerScreens(ScreenRegistry registry)
	{
		registry.registerContainerClickArea(REIPluginCommon::clickArea, MTFurnaceScreen.class, BuiltinPlugin.SMELTING);
		registry.registerContainerClickArea(REIPluginCommon::clickArea, MTCrusherScreen.class, CRUSHING);
		registry.registerContainerClickArea(REIPluginCommon::clickArea, MTAlloySmelterScreen.class, ALLOY_SMELTING);
	}
}
