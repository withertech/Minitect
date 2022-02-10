package com.withertech.minitect.registry;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.config.MineConfig;
import com.withertech.minitect.config.OreConfigs;
import dev.architectury.injectables.annotations.ExpectPlatform;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.gui.registry.api.GuiRegistryAccess;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.stream.Collectors;

public class MineConfigs
{
	public static void register()
	{
		registerConfigs();
	}

	private static List<AbstractConfigListEntry> getChildren(String i13n, Class<?> fieldType, Object iConfig, Object iDefaults, GuiRegistryAccess guiProvider) {
		return Arrays.stream(fieldType.getDeclaredFields()).map((iField) -> {
			String iI13n = String.format("%s.%s", i13n, iField.getName());
			return guiProvider.getAndTransform(iI13n, iField, iConfig, iDefaults, guiProvider);
		}).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
	}
	@ExpectPlatform
	public static void registerConfigs()
	{

	}

	public static Screen createScreen(Screen parent)
	{
		ConfigBuilder builder = ConfigBuilder.create()
				.setParentScreen(parent)
				.setTitle(new TextComponent("Minitect"));

		ConfigCategory main = builder.getOrCreateCategory(new TextComponent("Main"));

		ConfigCategory ores = builder.getOrCreateCategory(new TextComponent("Ores"));
		OreConfigs oreConfigs = AutoConfig.getConfigHolder(MineConfig.class).getConfig().ores;
		ores.addEntry(builder.entryBuilder().startBooleanToggle(new TextComponent("Master"), oreConfigs.isMaster())
				.setDefaultValue(true)
				.setSaveConsumer(oreConfigs::setMaster)
				.build());
		oreConfigs.ores.forEach((mineOres, oreConfig) ->
		{

			final SubCategoryBuilder categoryEntry = builder.entryBuilder().startSubCategory(new TextComponent(WordUtils.capitalize(mineOres.getName())));
			categoryEntry.add(builder.entryBuilder().startBooleanToggle(new TextComponent("Enabled"), oreConfig.isEnabled())
					.setDefaultValue(mineOres.getDefaultOreConfigs().enabled())
					.setSaveConsumer(oreConfig::setEnabled)
					.build());
			categoryEntry.add(builder.entryBuilder().startIntSlider(new TextComponent("Vein Count"), oreConfig.getVeinCount(), 1, 16)
					.setDefaultValue(mineOres.getDefaultOreConfigs().veinCount())
					.setSaveConsumer(oreConfig::setVeinCount)
					.build());
			categoryEntry.add(builder.entryBuilder().startIntSlider(new TextComponent("Vein Size"), oreConfig.getVeinSize(), 1, 16)
					.setDefaultValue(mineOres.getDefaultOreConfigs().veinSize())
					.setSaveConsumer(oreConfig::setVeinSize)
					.build());
			categoryEntry.add(builder.entryBuilder().startIntSlider(new TextComponent("Min Height"), oreConfig.getMinHeight(), -64, 320)
					.setDefaultValue(mineOres.getDefaultOreConfigs().minHeight())
					.setSaveConsumer(oreConfig::setMinHeight)
					.build());
			categoryEntry.add(builder.entryBuilder().startIntSlider(new TextComponent("Max Height"), oreConfig.getMaxHeight(), -64, 320)
					.setDefaultValue(mineOres.getDefaultOreConfigs().maxHeight())
					.setSaveConsumer(oreConfig::setMaxHeight)
					.build());
			ores.addEntry(categoryEntry.build());
		});
		return builder.build();
	}
}
