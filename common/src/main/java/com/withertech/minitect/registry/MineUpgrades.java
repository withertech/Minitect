package com.withertech.minitect.registry;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.item.UpgradeItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;

public enum MineUpgrades implements ItemLike
{
	SPEED("speed", 8),
	EFFICIENCY("efficiency", 8);

	private RegistrySupplier<UpgradeItem> item;
	private final ResourceLocation id;
	private final int stackSize;

	MineUpgrades(String id, int stackSize)
	{
		this.id = Minitect.modLoc(id + "_upgrade");
		this.stackSize = stackSize;
	}

	public static void register()
	{
		Arrays.stream(values()).forEach(mineUpgrades ->
		{
			mineUpgrades.item = MineRegistries.ITEMS.register(mineUpgrades.id, () -> new UpgradeItem(new Item.Properties().stacksTo(mineUpgrades.stackSize), mineUpgrades));
		});
	}

	@Override
	public Item asItem()
	{
		return item.get();
	}
}
