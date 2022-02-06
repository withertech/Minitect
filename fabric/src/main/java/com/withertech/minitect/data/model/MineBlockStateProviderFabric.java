package com.withertech.minitect.data.model;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.block.AbstractMachineBlock;
import com.withertech.minitect.block.Connection;
import com.withertech.minitect.block.Face;
import com.withertech.minitect.registry.MineBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockStateDefinitionProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;

public class MineBlockStateProviderFabric extends FabricBlockStateDefinitionProvider
{
	public MineBlockStateProviderFabric(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator)
	{
		registerMachine(blockStateModelGenerator, MineBlocks.FURNACE.get(), Minitect.modLoc("block/furnace_frame"));
		registerMachine(blockStateModelGenerator, MineBlocks.CRUSHER.get(), Minitect.modLoc("block/crusher_frame"));
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator)
	{

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
		Arrays.stream(Port.values()).forEach(port -> registerPort(builder, port));
		generator.blockStateOutput.accept(builder);
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
