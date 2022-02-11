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

package com.withertech.minitect;

import com.withertech.minitect.registry.MineConfigs;
import com.withertech.minitect.registry.MineRegistries;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.injectables.targets.ArchitecturyTarget;
import dev.architectury.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class Minitect
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "minitect";
	public static final Random RANDOM = new Random();

	public static void init()
	{
		LifecycleEvent.SETUP.register(Minitect::common);
		ClientLifecycleEvent.CLIENT_SETUP.register(Minitect::client);
		Platform.getMod(MOD_ID).registerConfigurationScreen(MineConfigs::createScreen);
		MineRegistries.register();
	}

	public static void common()
	{
		MineRegistries.registerCommon();
	}

	public static ResourceLocation modLoc(String key)
	{
		return new ResourceLocation(MOD_ID, key);
	}

	public static ResourceLocation mcLoc(String key)
	{
		return new ResourceLocation("minecraft", key);
	}

	public static ResourceLocation loaderLoc(String key)
	{
		return new ResourceLocation(ArchitecturyTarget.getCurrentTarget(), key);
	}

	private static void client(Minecraft instance)
	{
		MineRegistries.registerClient();
	}
}