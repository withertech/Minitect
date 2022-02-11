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

package com.withertech.minitect.data.lang;

import com.withertech.minitect.registry.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Locale;

@SuppressWarnings("deprecation")
public class MineLanguageProviderFabric extends FabricLanguageProvider
{
	public MineLanguageProviderFabric(FabricDataGenerator gen)
	{
		super(gen, "en_us");
	}

	private static String metal(MineMetals metal, String suffix)
	{
		return metal(metal, "", suffix);
	}

	private static String metal(MineMetals metal, String prefix, String suffix)
	{
		return (!prefix.isEmpty() ? prefix + " " : "") + WordUtils.capitalize(metal.getName().replaceAll("_", " ")) + " " + suffix;
	}

	private static String gem(MineGems metal, String suffix)
	{
		return gem(metal, "", suffix);
	}

	private static String gem(MineGems metal, String prefix, String suffix)
	{
		return (!prefix.isEmpty() ? prefix + " " : "") + WordUtils.capitalize(metal.getName().replaceAll("_", " ")) + " " + suffix;
	}

	private static String upgrade(MineUpgrades upgrade)
	{
		return WordUtils.capitalize(upgrade.name().toLowerCase(Locale.ROOT).replaceAll("_", " ")) + " Upgrade";
	}

	@Override
	protected void addTranslations()
	{
		addBlock(MineBlocks.FURNACE, "Electric Furnace");
		addBlock(MineBlocks.CRUSHER, "Electric Crusher");
		for (MineMetals metal : MineMetals.values())
		{
			metal.getStorageBlock(true).ifPresent(block -> addBlock(() -> block, metal(metal, "Block")));
			metal.getOre(true).ifPresent(block -> addBlock(() -> block, metal(metal, "Ore")));
			metal.getDeepOre(true).ifPresent(block -> addBlock(() -> block, metal(metal, "Deepslate", "Ore")));
			metal.getIngot(true).ifPresent(item -> addItem(() -> item, metal(metal, "Ingot")));
			metal.getNugget(true).ifPresent(item -> addItem(() -> item, metal(metal, "Nugget")));
			metal.getDust(true).ifPresent(item -> addItem(() -> item, metal(metal, "Dust")));
			metal.getGear(true).ifPresent(item -> addItem(() -> item, metal(metal, "Gear")));
			metal.getPlate(true).ifPresent(item -> addItem(() -> item, metal(metal, "Plate")));
			metal.getRod(true).ifPresent(item -> addItem(() -> item, metal(metal, "Rod")));
		}
		for (MineGems gem : MineGems.values())
		{
			gem.getStorageBlock(true).ifPresent(block -> addBlock(() -> block, gem(gem, "Block")));
			gem.getOre(true).ifPresent(block -> addBlock(() -> block, gem(gem, "Ore")));
			gem.getDeepOre(true).ifPresent(block -> addBlock(() -> block, gem(gem, "Deepslate", "Ore")));
			gem.getGem(true).ifPresent(item -> addItem(() -> item, gem(gem, "Gem")));
			gem.getDust(true).ifPresent(item -> addItem(() -> item, gem(gem, "Dust")));
		}
		for (MineUpgrades upgrade : MineUpgrades.values())
		{
			addItem(upgrade::asItem, upgrade(upgrade));
		}
		add(((TranslatableComponent) MineTabs.MACHINES.getDisplayName()).getKey(), "Minitect: Machines");
		add(((TranslatableComponent) MineTabs.MATERIALS.getDisplayName()).getKey(), "Minitect: Materials");
	}
}
