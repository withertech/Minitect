package com.withertech.minitect.client.screen;

import com.withertech.mine_gui.client.MineGuiInventoryScreen;
import com.withertech.minitect.container.MTCrusherContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class MTCrusherScreen extends MineGuiInventoryScreen<MTCrusherContainer>
{
	public MTCrusherScreen(MTCrusherContainer description, Player player, Component title)
	{
		super(description, player, title);
	}
}
