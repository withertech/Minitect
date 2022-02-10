package com.withertech.minitect.compat;

import com.withertech.mine_gui.SyncedGuiDescription;
import com.withertech.mine_gui.client.MineGuiInventoryScreen;
import com.withertech.mine_gui.widget.WProgressBar;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.client.screen.MTCrusherScreen;
import com.withertech.minitect.client.screen.MTFurnaceScreen;
import com.withertech.minitect.container.ProgressContainer;
import com.withertech.minitect.recipe.CrushingRecipe;
import com.withertech.minitect.registry.MineItems;
import com.withertech.minitect.registry.MineRecipes;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;

public abstract class REIPluginCommon implements REIClientPlugin
{
	public static final CategoryIdentifier<CrushingDisplay> CRUSHING = CategoryIdentifier.of(Minitect.MOD_ID, "plugins/crushing");
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
		registry.addWorkstations(BuiltinPlugin.SMELTING, EntryStacks.of(MineItems.FURNACE::get));
		registry.addWorkstations(CRUSHING, EntryStacks.of(MineItems.CRUSHER.get()));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry)
	{
		registry.registerRecipeFiller(CrushingRecipe.class, MineRecipes.Types.CRUSHING, CrushingDisplay::new);
	}

	@Override
	public void registerScreens(ScreenRegistry registry)
	{
		registry.registerContainerClickArea(REIPluginCommon::clickArea, MTFurnaceScreen.class, BuiltinPlugin.SMELTING);
		registry.registerContainerClickArea(REIPluginCommon::clickArea, MTCrusherScreen.class, CRUSHING);
	}
}
