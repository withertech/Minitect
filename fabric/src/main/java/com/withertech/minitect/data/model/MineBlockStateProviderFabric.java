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
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockStateDefinitionProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;

public class MineBlockStateProviderFabric extends FabricBlockStateDefinitionProvider
{
	public MineBlockStateProviderFabric(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generator)
	{
		registerMachine(generator, MineBlocks.FURNACE.get(), Minitect.modLoc("block/furnace_frame"));
		registerMachine(generator, MineBlocks.CRUSHER.get(), Minitect.modLoc("block/crusher_frame"));
		registerMachine(generator, MineBlocks.ALLOY_SMELTER.get(), Minitect.modLoc("block/alloy_smelter_frame"));
		for (MineMetals metal : MineMetals.values())
		{
			metal.getStorageBlock(true).ifPresent(generator::createTrivialCube);
			metal.getStorageBlock(true).ifPresent(block -> registerBlockItem(generator, block));
			metal.getOre(true).ifPresent(generator::createTrivialCube);
			metal.getOre(true).ifPresent(block -> registerBlockItem(generator, block));
			metal.getDeepOre(true).ifPresent(generator::createTrivialCube);
			metal.getDeepOre(true).ifPresent(block -> registerBlockItem(generator, block));
		}
		for (MineGems gem : MineGems.values())
		{
			gem.getStorageBlock(true).ifPresent(generator::createTrivialCube);
			gem.getStorageBlock(true).ifPresent(block -> registerBlockItem(generator, block));
			gem.getOre(true).ifPresent(generator::createTrivialCube);
			gem.getOre(true).ifPresent(block -> registerBlockItem(generator, block));
			gem.getDeepOre(true).ifPresent(generator::createTrivialCube);
			gem.getDeepOre(true).ifPresent(block -> registerBlockItem(generator, block));
		}
	}

	@Override
	public void generateItemModels(ItemModelGenerators generator)
	{
		for (MineMetals metal : MineMetals.values())
		{
			metal.getIngot(true).ifPresent(item -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
			metal.getNugget(true).ifPresent(item -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
			metal.getDust(true).ifPresent(item -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
			metal.getGear(true).ifPresent(item -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
			metal.getPlate(true).ifPresent(item -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
			metal.getRod(true).ifPresent(item -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
		}
		for (MineGems gem : MineGems.values())
		{
			gem.getGem(true).ifPresent(item -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
			gem.getDust(true).ifPresent(item -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
		}
		for (MineUpgrades upgrade : MineUpgrades.values())
		{
			generator.generateFlatItem(upgrade.asItem(), ModelTemplates.FLAT_ITEM);
		}
		for (MineCraftingTools tool : MineCraftingTools.values())
		{
			generator.generateFlatItem(tool.asItem(), ModelTemplates.FLAT_HANDHELD_ITEM);
		}
	}

	private void registerFrame(MultiPartGenerator builder, ResourceLocation frame)
	{

		Direction.Plane.HORIZONTAL.stream().forEach(facing ->
		{
			VariantProperties.Rotation yRot = switch (facing)
					{
						case SOUTH -> VariantProperties.Rotation.R180;
						case EAST -> VariantProperties.Rotation.R90;
						case WEST -> VariantProperties.Rotation.R270;
						default -> VariantProperties.Rotation.R0;
					};
			builder.with(
					Condition.condition().term(AbstractMachineBlock.FACING, facing),
					Variant.variant().with(VariantProperties.Y_ROT, yRot).with(VariantProperties.MODEL, frame));
		});
	}

	private void registerPort(MultiPartGenerator builder, Port port)
	{
		Arrays.stream(Direction.values()).forEach(direction ->
		{
			Direction.Plane.HORIZONTAL.stream().forEach(facing ->
			{
				if (Face.getFaceFromDirection(direction, facing) != Face.FRONT)
				{
					VariantProperties.Rotation xRot = switch (direction)
							{
								case UP -> VariantProperties.Rotation.R270;
								case DOWN -> VariantProperties.Rotation.R90;
								default -> VariantProperties.Rotation.R0;
							};
					VariantProperties.Rotation yRot = switch (direction)
							{
								case SOUTH -> VariantProperties.Rotation.R180;
								case EAST -> VariantProperties.Rotation.R90;
								case WEST -> VariantProperties.Rotation.R270;
								default -> VariantProperties.Rotation.R0;
							};
					builder.with(
							Condition.and(
									Condition.condition().term(AbstractMachineBlock.FACING_TO_PROPERTY_MAP.get(Face.getFaceFromDirection(direction, facing)), port.connection),
									Condition.condition().term(AbstractMachineBlock.FACING, facing)
							),
							Variant.variant()
									.with(VariantProperties.Y_ROT, yRot)
									.with(VariantProperties.X_ROT, xRot)
									.with(VariantProperties.MODEL, port.model)
					);
				}
			});
		});
	}

	private void registerMachine(BlockModelGenerators generator, AbstractMachineBlock block, ResourceLocation frame)
	{
		generator.skipAutoItemBlock(block);
		generator.delegateItemModel(block, frame);
		final MultiPartGenerator builder = MultiPartGenerator.multiPart(block);
		registerFrame(builder, frame);
		Arrays.stream(Port.values()).filter(port -> block.getValidConnections().contains(port.connection)).forEach(port -> registerPort(builder, port));
		generator.blockStateOutput.accept(builder);
	}

	private void registerBlockItem(BlockModelGenerators generator, Block block)
	{
		generator.skipAutoItemBlock(block);
		generator.delegateItemModel(block, ModelLocationUtils.getModelLocation(block));
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
