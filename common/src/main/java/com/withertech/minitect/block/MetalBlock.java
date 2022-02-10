package com.withertech.minitect.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class MetalBlock extends Block
{
	public MetalBlock()
	{
		super(Properties.of(Material.METAL)
				.strength(4, 20)
				.sound(SoundType.METAL)
		);
	}
}