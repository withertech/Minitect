package com.withertech.minitect.registry.forge;

import com.withertech.minitect.item.MachineBlockItem;
import com.withertech.minitect.item.forge.MachineBlockItemForge;
import com.withertech.minitect.registry.MineBlocks;
import com.withertech.minitect.registry.MineItems;
import com.withertech.minitect.registry.MineRegistries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.BlockItem;

public class MineItemsImpl
{
	static
	{
		MineItems.FURNACE = MineRegistries.ITEMS.register("furnace", () -> new MachineBlockItemForge(MineBlocks.FURNACE.get()));
		MineItems.CRUSHER = MineRegistries.ITEMS.register("crusher", () -> new MachineBlockItemForge(MineBlocks.CRUSHER.get()));
	}
	public static void register()
	{
	}
}
