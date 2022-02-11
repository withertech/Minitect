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

package com.withertech.minitect.client.screen;

import com.withertech.mine_gui.client.MineGuiInventoryScreen;
import com.withertech.minitect.container.MTFurnaceContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class MTFurnaceScreen extends MineGuiInventoryScreen<MTFurnaceContainer>
{
	public MTFurnaceScreen(MTFurnaceContainer description, Player player, Component title)
	{
		super(description, player, title);
	}
}
