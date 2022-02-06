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
