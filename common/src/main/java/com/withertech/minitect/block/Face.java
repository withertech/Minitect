package com.withertech.minitect.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public enum Face
{
	FRONT,
	BACK,
	LEFT,
	RIGHT,
	DOWN,
	UP;

	public static Face getFaceFromDirection(Direction dir, BlockState state)
	{
		return getFaceFromDirection(dir, state.getValue(AbstractMachineBlock.FACING));
	}

	public static Face getFaceFromDirection(Direction dir, Direction facing)
	{
		return switch (facing)
				{
					case NORTH -> switch (dir)
							{
								case NORTH -> Face.FRONT;
								case SOUTH -> Face.BACK;
								case EAST -> Face.RIGHT;
								case WEST -> Face.LEFT;
								case UP -> Face.UP;
								case DOWN -> Face.DOWN;
							};
					case SOUTH -> switch (dir)
							{
								case NORTH -> Face.BACK;
								case SOUTH -> Face.FRONT;
								case EAST -> Face.LEFT;
								case WEST -> Face.RIGHT;
								case UP -> Face.UP;
								case DOWN -> Face.DOWN;
							};
					case EAST -> switch (dir)
							{
								case NORTH -> Face.LEFT;
								case SOUTH -> Face.RIGHT;
								case EAST -> Face.FRONT;
								case WEST -> Face.BACK;
								case UP -> Face.UP;
								case DOWN -> Face.DOWN;
							};
					case WEST -> switch (dir)
							{
								case NORTH -> Face.RIGHT;
								case SOUTH -> Face.LEFT;
								case EAST -> Face.BACK;
								case WEST -> Face.FRONT;
								case UP -> Face.UP;
								case DOWN -> Face.DOWN;
							};
					default -> null;
				};
	}

	public static Direction getDirectionFromFace(Face face, BlockState state)
	{
		return getDirectionFromFace(face, state.getValue(AbstractMachineBlock.FACING));
	}

	public static Direction getDirectionFromFace(Face face, Direction facing)
	{
		return switch (facing)
				{
					case NORTH -> switch (face)
							{
								case FRONT -> Direction.NORTH;
								case BACK -> Direction.SOUTH;
								case RIGHT -> Direction.EAST;
								case LEFT -> Direction.WEST;
								case UP -> Direction.UP;
								case DOWN -> Direction.DOWN;
							};
					case SOUTH -> switch (face)
							{
								case BACK -> Direction.NORTH;
								case FRONT -> Direction.SOUTH;
								case LEFT -> Direction.EAST;
								case RIGHT -> Direction.WEST;
								case UP -> Direction.UP;
								case DOWN -> Direction.DOWN;
							};
					case EAST -> switch (face)
							{
								case LEFT -> Direction.NORTH;
								case RIGHT -> Direction.SOUTH;
								case FRONT -> Direction.EAST;
								case BACK -> Direction.WEST;
								case UP -> Direction.UP;
								case DOWN -> Direction.DOWN;
							};
					case WEST -> switch (face)
							{
								case RIGHT -> Direction.NORTH;
								case LEFT -> Direction.SOUTH;
								case BACK -> Direction.EAST;
								case FRONT -> Direction.WEST;
								case UP -> Direction.UP;
								case DOWN -> Direction.DOWN;
							};
					default -> null;
				};
	}
}
