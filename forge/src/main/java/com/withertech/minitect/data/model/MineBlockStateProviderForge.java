package com.withertech.minitect.data.model;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.block.AbstractMachineBlock;
import com.withertech.minitect.block.Connection;
import com.withertech.minitect.block.Face;
import com.withertech.minitect.registry.MineBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;

public class MineBlockStateProviderForge extends BlockStateProvider
{
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
	}

	private void registerItems()
	{
		registerBlockItem(MineBlocks.FURNACE.get(), modLoc("block/furnace_frame"));
		registerBlockItem(MineBlocks.CRUSHER.get(), modLoc("block/crusher_frame"));
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
		registerFrame(builder, frame);
		Arrays.stream(Port.values()).forEach(port -> registerPort(builder, port));
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
