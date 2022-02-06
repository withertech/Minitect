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
import com.withertech.minitect.tile.MTFurnaceTile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class MTFurnaceContainer extends SyncedGuiDescription
{

	public MTFurnaceContainer(int syncId, Inventory playerInventory, FriendlyByteBuf buf)
	{
		this(syncId, playerInventory, ContainerLevelAccess.NULL, buf.readComponent());

	}
	public MTFurnaceContainer(int syncId, Inventory playerInventory, ContainerLevelAccess context, Component title)
	{
		super(MineContainers.FURNACE.get(), syncId, playerInventory, getBlockInventory(context, MTFurnaceTile.INV_SIZE), getBlockPropertyDelegate(context, MTFurnaceTile.DATA_SIZE));
		WTabPanel root = new WTabPanel();
		WGridPanel main = new WGridPanel();
		WGridPanel sub = new WGridPanel();
		setTitleVisible(false);
		setRootPanel(root);
		main.setInsets(Insets.ROOT_PANEL);
		main.add(new FurnacePanel(blockInventory, title), 0, 0);
		sub.setInsets(Insets.ROOT_PANEL);
		sub.add(new SettingsPanel(context, MineBlocks.FURNACE.get(), context.evaluate((level, blockPos) -> level.getBlockEntity(blockPos, MineTiles.FURNACE.get()).map(AbstractMachineTile::getUpgrades).orElse(new AbstractMachineTile.UpgradeInventory())).orElse(new AbstractMachineTile.UpgradeInventory()), this), 0, 0);

		main.add(this.createPlayerInventoryPanel(), 2, 3);
		sub.add(this.createPlayerInventoryPanel(), 2, 3);

		root.add(main, builder -> builder.title(new TextComponent("Main")));
		root.add(sub, builder -> builder.title(new TextComponent("Settings")));
		getRootPanel().validate(this);
	}

	private static class FurnacePanel extends WGridPanel
	{

		private final WLabel title;
		private final WItemSlot inputSlot;
		private final WItemSlot outputSlot;
		private final WEnergyBar energyBar;
		private final WProgressBar progressBar;

		public FurnacePanel(Container inv, Component comp)
		{
			title = new WLabel(comp);
			inputSlot = WItemSlot.of(inv, 0);
			outputSlot = WItemSlot.outputOf(inv, 1);
			energyBar = new WEnergyBar(0, 1);
			progressBar = new WProgressBar(2, 3);
			this.add(title, 2, 0);
			this.add(inputSlot, 4, 1);
			this.add(outputSlot, 8, 1);
			this.add(energyBar, 0, 0);
			this.add(progressBar, 6, 1);
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
		public void validate(GuiDescription c)
		{
			super.validate(c);
			title.setColor(c.getTitleColor());
		}
	}
}
