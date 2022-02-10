package com.withertech.minitect.compat;

import com.withertech.minitect.recipe.CrushingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;

public class CrushingDisplay extends AbstractDisplay<CrushingRecipe>
{

	public CrushingDisplay(CrushingRecipe recipe)
	{
		super(recipe);
	}

	@Override
	public int getWidth()
	{
		return 1;
	}

	@Override
	public int getHeight()
	{
		return 2;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier()
	{
		return REIPluginCommon.CRUSHING;
	}
}
