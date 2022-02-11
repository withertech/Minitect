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

package com.withertech.minitect.data;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.data.lang.MineLanguageProviderForge;
import com.withertech.minitect.data.loot.MineLootProviderForge;
import com.withertech.minitect.data.model.MineBlockStateProviderForge;
import com.withertech.minitect.data.recipe.MineRecipeProviderForge;
import com.withertech.minitect.data.tag.MineBlockTagProviderForge;
import com.withertech.minitect.data.tag.MineItemTagProviderForge;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Minitect.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event)
	{
		final DataGenerator generator = event.getGenerator();
		generator.addProvider(new MineRecipeProviderForge(generator));
		generator.addProvider(new MineBlockStateProviderForge(generator, event.getExistingFileHelper()));
		generator.addProvider(new MineLanguageProviderForge(generator));
		MineBlockTagProviderForge blockTags = new MineBlockTagProviderForge(generator, event.getExistingFileHelper());
		generator.addProvider(blockTags);
		generator.addProvider(new MineItemTagProviderForge(generator, blockTags, event.getExistingFileHelper()));
		generator.addProvider(new MineLootProviderForge(generator));
	}
}
