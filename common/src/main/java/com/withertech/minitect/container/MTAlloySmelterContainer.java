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

package com.withertech.minitect.container;

import com.withertech.mine_gui.GuiDescription;
import com.withertech.mine_gui.SyncedGuiDescription;
import com.withertech.mine_gui.client.BackgroundPainter;
import com.withertech.mine_gui.widget.*;
import com.withertech.mine_gui.widget.data.Insets;
import com.withertech.minitect.gui.SettingsPanel;
import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineContainers;
import com.withertech.minitect.registry.MineTiles;
import com.withertech.minitect.tile.AbstractMachineTile;
import com.withertech.minitect.tile.MTAlloySmelterTile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class MTAlloySmelterContainer extends SyncedGuiDescription implements ProgressContainer
{

	private final AlloySmelterPanel alloySmelterPanel;
	private boolean clickArea = false;

	public MTAlloySmelterContainer(int syncId, Inventory playerInventory, FriendlyByteBuf buf)
	{
		this(syncId, playerInventory, ContainerLevelAccess.NULL, buf.readComponent());

	}

	public MTAlloySmelterContainer(int syncId, Inventory playerInventory, ContainerLevelAccess context, Component title)
	{
		super(MineContainers.ALLOY_SMELTER.get(), syncId, playerInventory, getBlockInventory(context, MTAlloySmelterTile.INV_SIZE), getBlockPropertyDelegate(context, MTAlloySmelterTile.DATA_SIZE));
		WTabPanel root = new WTabPanel();
		WGridPanel main = new WGridPanel();
		WGridPanel sub = new WGridPanel();
		setTitleVisible(false);
		setRootPanel(root);
		main.setInsets(Insets.ROOT_PANEL);
		alloySmelterPanel = new AlloySmelterPanel(this, blockInventory, title);
		main.add(alloySmelterPanel, 0, 0);
		sub.setInsets(Insets.ROOT_PANEL);
		sub.add(new SettingsPanel(context, MineBlocks.ALLOY_SMELTER.get(), context.evaluate((level, blockPos) -> level.getBlockEntity(blockPos, MineTiles.ALLOY_SMELTER.get()).map(AbstractMachineTile::getUpgrades).orElse(new AbstractMachineTile.UpgradeInventory())).orElse(new AbstractMachineTile.UpgradeInventory()), this), 0, 0);

		main.add(this.createPlayerInventoryPanel(), 2, 3);
		sub.add(this.createPlayerInventoryPanel(), 2, 3);

		root.add(main, builder -> builder.title(new TextComponent("Main")));
		root.add(sub, builder -> builder.title(new TextComponent("Settings")));
		getRootPanel().validate(this);
	}

	@Override
	public ProgressPanel getProgressPanel()
	{
		return alloySmelterPanel;
	}

	@Override
	public boolean showClickArea()
	{
		return clickArea;
	}

	private static class AlloySmelterPanel extends WGridPanel implements ProgressPanel
	{

		private final MTAlloySmelterContainer container;
		private final WLabel title;
		private final WItemSlot inputSlot;
		private final WItemSlot outputSlot;
		private final WEnergyBar energyBar;
		private final WProgressBar progressBar;

		public AlloySmelterPanel(MTAlloySmelterContainer container, Container inv, Component comp)
		{
			this.container = container;
			title = new WLabel(comp);
			inputSlot = WItemSlot.of(inv, 0, 3, 1);
			outputSlot = WItemSlot.of(inv, 3, 1, 1);
			energyBar = new WEnergyBar(0, 1);
			progressBar = new WProgressBar(2, 3);
			this.add(title, 2, 0);
			this.add(inputSlot, 3, 1);
			this.add(outputSlot, 9, 1);
			this.add(energyBar, 0, 0);
			this.add(progressBar, 7, 1);
		}

		@Override
		public WProgressBar getProgressBar()
		{
			return progressBar;
		}

		@Override
		public int getWidth()
		{
			return 13 * 18;
		}

		@Override
		public WPanel setBackgroundPainter(BackgroundPainter painter)
		{
			super.setBackgroundPainter(null);
			inputSlot.setBackgroundPainter(painter);
			outputSlot.setBackgroundPainter(painter);
			return this;
		}

		@Override
		public void onShown()
		{
			super.onShown();
			container.clickArea = true;
		}

		@Override
		public void onHidden()
		{
			super.onHidden();
			container.clickArea = false;
		}

		@Override
		public void validate(GuiDescription c)
		{
			super.validate(c);
			title.setColor(c.getTitleColor());
		}
	}
}
