package com.withertech.minitect.registry;

import com.withertech.minitect.Minitect;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MineTabs
{
	public static final CreativeModeTab MACHINES = CreativeTabRegistry.create(Minitect.modLoc("machines"), () -> new ItemStack(MineItems.FURNACE.get()));
	public static final CreativeModeTab MATERIALS = CreativeTabRegistry.create(Minitect.modLoc("materials"), () -> new ItemStack(MineMetals.TIN.getIngot().orElse(Items.AIR)));
	public static void register()
	{

	}
}
