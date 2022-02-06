package com.withertech.minitect.gui;

import com.google.common.collect.Maps;
import com.withertech.mine_gui.SyncedGuiDescription;
import com.withertech.mine_gui.client.BackgroundPainter;
import com.withertech.mine_gui.networking.NetworkSide;
import com.withertech.mine_gui.networking.ScreenNetworking;
import com.withertech.mine_gui.widget.WGridPanel;
import com.withertech.mine_gui.widget.WItemSlot;
import com.withertech.mine_gui.widget.WPanel;
import com.withertech.mine_gui.widget.icon.TextureIcon;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.block.AbstractMachineBlock;
import com.withertech.minitect.block.Face;
import com.withertech.minitect.tile.AbstractMachineTile;
import com.withertech.minitect.tile.MTFurnaceTile;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.storage.loot.IntRange;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class SettingsPanel extends WGridPanel
{
	public static final ResourceLocation PORT_PACKET_C2S = Minitect.modLoc("port_update_c2s");
	public static final ResourceLocation PORT_PACKET_S2C = Minitect.modLoc("port_update_s2c");
	public static final ResourceLocation PORT_INIT_PACKET_C2S = Minitect.modLoc("port_init_c2s");
	private final WItemSlot UPGRADES;
	private final Map<Face, FaceButton> FACE_TO_BUTTON_MAP = Maps.newEnumMap(Face.class);

	public SettingsPanel(ContainerLevelAccess context, AbstractMachineBlock block, AbstractMachineTile.UpgradeInventory upgradeInventory, SyncedGuiDescription description)
	{
		FACE_TO_BUTTON_MAP.put(Face.RIGHT, new FaceButton(Face.RIGHT, description));
		FACE_TO_BUTTON_MAP.put(Face.BACK, new FaceButton(Face.BACK, description));
		FACE_TO_BUTTON_MAP.put(Face.LEFT, new FaceButton(Face.LEFT, description));
		FACE_TO_BUTTON_MAP.put(Face.DOWN, new FaceButton(Face.DOWN, description));
		FACE_TO_BUTTON_MAP.put(Face.UP, new FaceButton(Face.UP, description));
		UPGRADES = WItemSlot.of(upgradeInventory, 0, AbstractMachineTile.UpgradeInventory.UPGRADE_SIZE, 1);
		this.add(FACE_TO_BUTTON_MAP.get(Face.RIGHT), 0, 1);
		this.add(FACE_TO_BUTTON_MAP.get(Face.BACK), 0, 0);
		this.add(FACE_TO_BUTTON_MAP.get(Face.LEFT), 2, 1);
		this.add(FACE_TO_BUTTON_MAP.get(Face.DOWN), 1, 2);
		this.add(FACE_TO_BUTTON_MAP.get(Face.UP), 1, 0);
		this.add(UPGRADES, 4, 1);
//		IntStream.rangeClosed(upgradeStart, upgradeEnd).forEach(i ->
//		{
//			this.add(WItemSlot.of(inventory, i), 4 + (i - 2), 1);
//		});

		ScreenNetworking.of(description, NetworkSide.SERVER).receive(PORT_INIT_PACKET_C2S, buf ->
		{
			Arrays.stream(Face.values()).filter(face -> face != Face.FRONT).forEach(face ->
			{
				context.execute((level, blockPos) ->
				{
					ScreenNetworking.of(description, NetworkSide.SERVER).send(PORT_PACKET_S2C, friendlyByteBuf -> friendlyByteBuf.writeEnum(face).writeComponent(new TextComponent(level.getBlockState(blockPos).getValue(AbstractMachineBlock.FACING_TO_PROPERTY_MAP.get(face)).getSerializedName())));
				});
			});
		});
		ScreenNetworking.of(description, NetworkSide.SERVER).receive(PORT_PACKET_C2S, buf ->
		{
			context.execute((level, blockPos) ->
			{
				Face face = buf.readEnum(Face.class);
				block.cyclePort(level, blockPos, face);
				ScreenNetworking.of(description, NetworkSide.SERVER).send(PORT_PACKET_S2C, friendlyByteBuf -> friendlyByteBuf.writeEnum(face).writeComponent(new TextComponent(level.getBlockState(blockPos).getValue(AbstractMachineBlock.FACING_TO_PROPERTY_MAP.get(face)).getSerializedName())));
			});
		});
		ScreenNetworking.of(description, NetworkSide.CLIENT).receive(PORT_PACKET_S2C, buf ->
		{
			Face face = buf.readEnum(Face.class);
			String name = buf.readComponent().getString();
			if (!Objects.equals(name, "none_none"))
			{
				//noinspection deprecation
				FACE_TO_BUTTON_MAP.get(face).setTooltip(new TextComponent(WordUtils.capitalizeFully(face.name().toLowerCase(Locale.ROOT)) + ": " + WordUtils.capitalizeFully(name.replace("_", " ")))).setIcon(new TextureIcon(Minitect.modLoc("textures/gui/port/" + name + ".png")));
			} else
			{
				//noinspection deprecation
				FACE_TO_BUTTON_MAP.get(face).setTooltip(new TextComponent(WordUtils.capitalizeFully(face.name().toLowerCase(Locale.ROOT)) + ": " + "None")).setIcon(null);
			}
		});

		ScreenNetworking.of(description, NetworkSide.CLIENT).send(PORT_INIT_PACKET_C2S, buf -> {});


	}

	@Override
	public WPanel setBackgroundPainter(BackgroundPainter painter)
	{
		super.setBackgroundPainter(null);
		UPGRADES.setBackgroundPainter(painter);
		return this;
	}

	@Override
	public int getWidth()
	{
		return 13 * 18;
	}
}
