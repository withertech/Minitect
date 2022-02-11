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

package com.withertech.minitect.gui;

import com.withertech.mine_gui.SyncedGuiDescription;
import com.withertech.mine_gui.networking.NetworkSide;
import com.withertech.mine_gui.networking.ScreenNetworking;
import com.withertech.mine_gui.widget.TooltipBuilder;
import com.withertech.mine_gui.widget.WButton;
import com.withertech.minitect.block.Face;
import net.minecraft.network.chat.Component;

public class FaceButton extends WButton
{
	private Component tooltip = null;

	public FaceButton(Face face, SyncedGuiDescription description)
	{
		setOnClick(() ->
		{
			ScreenNetworking.of(description, NetworkSide.CLIENT).send(SettingsPanel.PORT_PACKET_C2S, friendlyByteBuf -> friendlyByteBuf.writeEnum(face));
		});
	}

	public FaceButton setTooltip(Component tooltip)
	{
		this.tooltip = tooltip;
		return this;
	}

	@Override
	public void addTooltip(TooltipBuilder tooltip)
	{
		if (this.tooltip != null)
			tooltip.add(this.tooltip);
		super.addTooltip(tooltip);
	}
}
