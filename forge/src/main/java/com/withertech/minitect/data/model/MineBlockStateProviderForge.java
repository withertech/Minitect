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

package com.withertech.minitect.data.model;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.block.AbstractMachineBlock;
import com.withertech.minitect.block.Connection;
import com.withertech.minitect.block.Face;
import com.withertech.minitect.registry.*;
import com.withertech.minitect.util.BlockLike;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MineBlockStateProviderForge extends BlockStateProvider
{
	private final ModelFile itemGenerated = itemModels().getExistingFile(mcLoc("item/generated"));
	private final ModelFile itemHandheld = itemModels().getExistingFile(mcLoc("item/handheld"));

	public MineBlockStateProviderForge(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, Minitect.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels()
	{
		registerBlocks();
		registerItems();
	}

	private void registerBlocks()
	{
		registerMachine(MineBlocks.FURNACE.get(), modLoc("block/furnace_frame"));
		registerMachine(MineBlocks.CRUSHER.get(), modLoc("block/crusher_frame"));
		registerMachine(MineBlocks.ALLOY_SMELTER.get(), modLoc("block/alloy_smelter_frame"));
		for (MineMetals metal : MineMetals.values())
		{
			metal.getStorageBlock(true).ifPresent(this::simpleBlock);
			metal.getOre(true).ifPresent(this::simpleBlock);
			metal.getDeepOre(true).ifPresent(this::simpleBlock);
		}
		for (MineGems gem : MineGems.values())
		{
			gem.getStorageBlock(true).ifPresent(this::simpleBlock);
			gem.getOre(true).ifPresent(this::simpleBlock);
			gem.getDeepOre(true).ifPresent(this::simpleBlock);
		}
	}

	private void registerItems()
	{
		for (MineMetals metal : MineMetals.values())
		{
			metal.getStorageBlock(true).ifPresent(this::registerBlockItem);
			metal.getOre(true).ifPresent(this::registerBlockItem);
			metal.getDeepOre(true).ifPresent(this::registerBlockItem);
			metal.getIngot(true).ifPresent(this::registerGeneratedItem);
			metal.getNugget(true).ifPresent(this::registerGeneratedItem);
			metal.getDust(true).ifPresent(this::registerGeneratedItem);
			metal.getGear(true).ifPresent(this::registerGeneratedItem);
			metal.getPlate(true).ifPresent(this::registerGeneratedItem);
			metal.getRod(true).ifPresent(this::registerGeneratedItem);
		}
		for (MineGems gem : MineGems.values())
		{
			gem.getStorageBlock(true).ifPresent(this::registerBlockItem);
			gem.getOre(true).ifPresent(this::registerBlockItem);
			gem.getDeepOre(true).ifPresent(this::registerBlockItem);
			gem.getGem(true).ifPresent(this::registerGeneratedItem);
			gem.getDust(true).ifPresent(this::registerGeneratedItem);
		}
		for (MineUpgrades upgrade : MineUpgrades.values())
		{
			registerGeneratedItem(upgrade);
		}
		for (MineCraftingTools tool : MineCraftingTools.values())
		{
			registerHandheldItem(tool);
		}
	}

	private void registerBlockItem(Block block, ResourceLocation model)
	{
		itemModels().getBuilder(block.getRegistryName().getPath()).parent(itemModels().getExistingFile(model));
	}

	private void registerFrame(MultiPartBlockStateBuilder builder, ResourceLocation frame)
	{
		Direction.Plane.HORIZONTAL.stream().forEach(facing ->
		{
			int yRot = switch (facing)
					{
						case SOUTH -> 180;
						case EAST -> 90;
						case WEST -> 270;
						default -> 0;
					};
			builder
					.part()
					.modelFile(models().getExistingFile(frame))
					.rotationY(yRot)
					.addModel()
					.condition(AbstractMachineBlock.FACING, facing)
					.end();
		});
	}

	private void registerPort(MultiPartBlockStateBuilder builder, Port port)
	{
		Arrays.stream(Direction.values()).forEach(direction ->
		{
			Direction.Plane.HORIZONTAL.stream().forEach(facing ->
			{
				if (Face.getFaceFromDirection(direction, facing) != Face.FRONT)
				{
					int xRot = switch (direction)
							{
								case UP -> 270;
								case DOWN -> 90;
								default -> 0;
							};
					int yRot = switch (direction)
							{
								case SOUTH -> 180;
								case EAST -> 90;
								case WEST -> 270;
								default -> 0;
							};
					builder
							.part()
							.modelFile(models().getExistingFile(port.model))
							.rotationX(xRot)
							.rotationY(yRot)
							.addModel()
							.condition(AbstractMachineBlock.FACING_TO_PROPERTY_MAP.get(Face.getFaceFromDirection(direction, facing)), port.connection)
							.condition(AbstractMachineBlock.FACING, facing)
							.end();
				}
			});
		});
	}

	private void registerMachine(AbstractMachineBlock block, ResourceLocation frame)
	{
		final MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
		registerBlockItem(block, frame);
		registerFrame(builder, frame);
		Arrays.stream(Port.values()).filter(port -> block.getValidConnections().contains(port.connection)).forEach(port -> registerPort(builder, port));
	}

	private void registerBlockItem(BlockLike block)
	{
		registerBlockItem(block.asBlock());
	}

	private void registerBlockItem(Block block)
	{
		String name = block.getRegistryName().getPath();
		itemModels().withExistingParent(name, modLoc("block/" + name));
	}

	private void registerGeneratedItem(ItemLike item)
	{
		registerItem(item, itemGenerated);
	}

	private void registerHandheldItem(ItemLike item)
	{
		registerItem(item, itemHandheld);
	}

	private void registerItem(ItemLike item, ModelFile parent)
	{
		String name = item.asItem().getRegistryName().getPath();
		registerItem(item, parent, "item/" + name);
	}

	private void registerItem(ItemLike item, ModelFile parent, String texture)
	{
		itemModels().getBuilder(item.asItem().getRegistryName().getPath())
				.parent(parent)
				.texture("layer0", modLoc(texture));
	}


	private enum Port
	{
		ITEM_IN(Minitect.modLoc("block/item_input"), Connection.ITEM_IN),
		ITEM_OUT(Minitect.modLoc("block/item_output"), Connection.ITEM_OUT),
		FLUID_IN(Minitect.modLoc("block/fluid_input"), Connection.FLUID_IN),
		FLUID_OUT(Minitect.modLoc("block/fluid_output"), Connection.FLUID_OUT),
		ENERGY_IN(Minitect.modLoc("block/energy_input"), Connection.ENERGY_IN),
		ENERGY_OUT(Minitect.modLoc("block/energy_output"), Connection.ENERGY_OUT);
		public final ResourceLocation model;
		public final Connection connection;

		Port(ResourceLocation model, Connection connection)
		{
			this.model = model;
			this.connection = connection;
		}
	}

}
