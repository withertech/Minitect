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

package com.withertech.minitect.registry;

import com.withertech.minitect.container.MTAlloySmelterContainer;
import com.withertech.minitect.container.MTCrusherContainer;
import com.withertech.minitect.container.MTFurnaceContainer;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.inventory.MenuType;

public class MineContainers
{
	public static void register()
	{
	}	public static final RegistrySupplier<MenuType<MTFurnaceContainer>> FURNACE = MineRegistries.CONTAINERS.register("furnace", () -> MenuRegistry.ofExtended(MTFurnaceContainer::new));
	public static final RegistrySupplier<MenuType<MTCrusherContainer>> CRUSHER = MineRegistries.CONTAINERS.register("crusher", () -> MenuRegistry.ofExtended(MTCrusherContainer::new));
	public static final RegistrySupplier<MenuType<MTAlloySmelterContainer>> ALLOY_SMELTER = MineRegistries.CONTAINERS.register("alloy_smelter", () -> MenuRegistry.ofExtended(MTAlloySmelterContainer::new));


}
