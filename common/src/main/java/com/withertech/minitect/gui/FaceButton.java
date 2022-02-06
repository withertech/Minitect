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
