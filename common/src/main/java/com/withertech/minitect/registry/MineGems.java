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

package com.withertech.minitect.registry;

import com.withertech.mine_tags.tags.PlatformTags;
import com.withertech.mine_tags.util.TagUtil;
import com.withertech.minitect.block.MetalBlock;
import com.withertech.minitect.item.ResourceBlockItem;
import com.withertech.minitect.item.ResourceItem;
import com.withertech.minitect.item.ResourceItemType;
import com.withertech.minitect.util.RegistryUtil;
import dev.architectury.hooks.tags.TagHooks;
import dev.architectury.registry.block.BlockProperties;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

public enum MineGems
{
	COAL(builder("coal").vanilla().dust().gemExisting(RegistryUtil.delegate(Items.COAL)).oreTagOnly()),
	REDSTONE(builder("redstone").vanilla().gem().dustExisting(RegistryUtil.delegate(Items.REDSTONE)).oreTagOnly()),
	DIAMOND(builder("diamond").vanilla().dust().gemExisting(RegistryUtil.delegate(Items.DIAMOND)).oreTagOnly()),
	LAPIS(builder("lapis").vanilla().dust().gemExisting(RegistryUtil.delegate(Items.LAPIS_LAZULI)).oreTagOnly()),
	EMERALD(builder("emerald").vanilla().dust().gemExisting(RegistryUtil.delegate(Items.EMERALD)).oreTagOnly()),
	GLOWSTONE(builder("glowstone").vanilla().gem().dustExisting(RegistryUtil.delegate(Items.GLOWSTONE_DUST))),
	ENDER_PEARL(builder("ender_pearl").vanilla().dust().gemExisting(RegistryUtil.delegate(Items.ENDER_PEARL), PlatformTags.Items.ENDER_PEARLS)),

	RUBY(builderBaseWithOre("ruby", MineOres.RUBY));

	private final String oreName;
	private final boolean vanilla;
	private final Supplier<Block> oreSupplier;
	private final Supplier<Block> deepOreSupplier;
	private final Supplier<Block> storageBlockSupplier;
	private final Supplier<Item> dustSupplier;
	private final Supplier<Item> gemSupplier;
	private final Tag.Named<Block> storageBlockTag;
	private final Tag.Named<Block> oreTag;
	private final Tag.Named<Item> storageBlockItemTag;
	private final Tag.Named<Item> oreItemTag;
	private final Tag.Named<Item> dustTag;
	private final Tag.Named<Item> gemTag;
	private final MineOres mineOres;
	private RegistrySupplier<Block> ore;
	private RegistrySupplier<Block> deepOre;
	private RegistrySupplier<Block> storageBlock;
	private RegistrySupplier<BlockItem> oreItem;
	private RegistrySupplier<BlockItem> deepOreItem;
	private RegistrySupplier<BlockItem> storageBlockItem;
	private RegistrySupplier<Item> dust;
	private RegistrySupplier<Item> gem;

	MineGems(Builder builder)
	{
		this(builder, builder.name);
	}

	MineGems(Builder builder, String oreName)
	{
		if (!builder.name.equals(this.getName()))
		{
			throw new IllegalArgumentException("Builder name is incorrect, should be " + this.getName());
		}
		this.oreName = oreName;
		this.vanilla = builder.vanilla;
		this.storageBlockSupplier = builder.storageBlock;
		this.mineOres = builder.mineOres;
		this.oreSupplier = builder.ore;
		this.deepOreSupplier = builder.ore;
		this.dustSupplier = builder.dust instanceof RegistrySupplier<Item> ? null : builder.dust;
		this.dust = builder.dust instanceof RegistrySupplier<Item> ? (RegistrySupplier<Item>) builder.dust : null;
		this.gemSupplier = builder.gem instanceof RegistrySupplier<Item> ? null : builder.gem;
		this.gem = builder.gem instanceof RegistrySupplier<Item> ? (RegistrySupplier<Item>) builder.gem : null;
		this.oreTag = builder.oreTag;
		this.storageBlockTag = builder.storageBlockTag;
		this.oreItemTag = this.oreTag != null ? Builder.itemTag(this.oreTag.getName()) : null;
		this.storageBlockItemTag = this.storageBlockTag != null ? Builder.itemTag(this.storageBlockTag.getName()) : null;
		this.dustTag = builder.dustTag;
		this.gemTag = builder.gemTag;
	}

	public static void register()
	{
		registerBlocks();
		registerItems();
	}

	private static void registerBlocks()
	{
		for (MineGems gem : values())
		{
			if (gem.oreSupplier != null)
			{
				String name = gem.oreName + "_ore";
				gem.ore = MineRegistries.BLOCKS.register(name, gem.oreSupplier);
				gem.oreItem = MineRegistries.ITEMS.register(name, () -> new ResourceBlockItem(gem.ore.get(), new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.ORE));
			}
			if (gem.deepOreSupplier != null)
			{
				String name = gem.oreName + "_deep_ore";
				gem.deepOre = MineRegistries.BLOCKS.register(name, gem.deepOreSupplier);
				gem.deepOreItem = MineRegistries.ITEMS.register(name, () -> new ResourceBlockItem(gem.deepOre.get(), new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.ORE));
			}
		}
		for (MineGems gem : values())
		{
			if (gem.storageBlockSupplier != null)
			{
				String name = gem.getName() + "_block";
				gem.storageBlock = MineRegistries.BLOCKS.register(name, gem.storageBlockSupplier);
				gem.storageBlockItem = MineRegistries.ITEMS.register(name, () -> new ResourceBlockItem(gem.storageBlock.get(), new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.STORAGE));
			}
		}
	}

	private static void registerItems()
	{
		for (MineGems gem : values())
		{
			if (gem.dustSupplier != null)
			{
				gem.dust = MineRegistries.ITEMS.register(
						gem.getName() + "_dust", gem.dustSupplier);
			}
			if (gem.gemSupplier != null)
			{
				gem.gem = MineRegistries.ITEMS.register(
						gem.getName() + "_gem", gem.gemSupplier);
			}
		}
	}

	private static Builder builder(String name)
	{
		return new Builder(name);
	}

	private static Builder builderBaseWithOre(String name, MineOres ore)
	{
		return builder(name).storageBlock().ore(ore).dust().gem();
	}

//	private static Builder builderAlloy(String name)
//	{
//		return builder(name).storageBlock().dust().gem();
//	}

	public String getName()
	{
		return name().toLowerCase(Locale.ROOT);
	}

	public Optional<MineOres> getMineOres()
	{
		return Optional.ofNullable(mineOres);
	}

	public Optional<Block> getOre()
	{
		return getOre(false);
	}

	public Optional<Block> getOre(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return ore != null && !RegistryUtil.isVanilla(ore.get()) ? Optional.of(ore.get()) : Optional.empty();
		return ore != null ? Optional.of(ore.get()) : Optional.empty();
	}

	public Optional<Block> getDeepOre()
	{
		return getDeepOre(false);
	}

	public Optional<Block> getDeepOre(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return deepOre != null && !RegistryUtil.isVanilla(deepOre.get()) ? Optional.of(deepOre.get()) : Optional.empty();
		return deepOre != null ? Optional.of(deepOre.get()) : Optional.empty();
	}

	public Optional<Block> getStorageBlock()
	{
		return getStorageBlock(false);
	}

	public Optional<Block> getStorageBlock(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return storageBlock != null && !RegistryUtil.isVanilla(storageBlock.get()) ? Optional.of(storageBlock.get()) : Optional.empty();
		return storageBlock != null ? Optional.of(storageBlock.get()) : Optional.empty();
	}

	public Optional<BlockItem> getOreItem()
	{
		return getOreItem(false);
	}

	public Optional<BlockItem> getOreItem(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return oreItem != null && !RegistryUtil.isVanilla(oreItem.get()) ? Optional.of(oreItem.get()) : Optional.empty();
		return oreItem != null ? Optional.of(oreItem.get()) : Optional.empty();
	}

	public Optional<BlockItem> getDeepOreItem()
	{
		return getDeepOreItem(false);
	}

	public Optional<BlockItem> getDeepOreItem(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return deepOreItem != null && !RegistryUtil.isVanilla(deepOreItem.get()) ? Optional.of(deepOreItem.get()) : Optional.empty();
		return deepOreItem != null ? Optional.of(deepOreItem.get()) : Optional.empty();
	}

	public Optional<BlockItem> getStorageBlockItem()
	{
		return getStorageBlockItem(false);
	}

	public Optional<BlockItem> getStorageBlockItem(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return storageBlockItem != null && !RegistryUtil.isVanilla(storageBlockItem.get()) ? Optional.of(storageBlockItem.get()) : Optional.empty();
		return storageBlockItem != null ? Optional.of(storageBlockItem.get()) : Optional.empty();
	}

	public boolean isVanilla()
	{
		return this.vanilla;
	}

	public Optional<Item> getDust()
	{
		return getDust(false);
	}

	public Optional<Item> getDust(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return dust != null && !RegistryUtil.isVanilla(dust.get()) ? Optional.of(dust.get()) : Optional.empty();
		return dust != null ? Optional.of(dust.get()) : Optional.empty();
	}

	public Optional<Item> getGem()
	{
		return getGem(false);
	}

	public Optional<Item> getGem(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return gem != null && !RegistryUtil.isVanilla(gem.get()) ? Optional.of(gem.get()) : Optional.empty();
		return gem != null ? Optional.of(gem.get()) : Optional.empty();
	}

	public Optional<Tag.Named<Block>> getOreTag()
	{
		return oreTag != null ? Optional.of(oreTag) : Optional.empty();
	}

	public Optional<Tag.Named<Block>> getStorageBlockTag()
	{
		return storageBlockTag != null ? Optional.of(storageBlockTag) : Optional.empty();
	}

	public Optional<Tag.Named<Item>> getOreItemTag()
	{
		return oreItemTag != null ? Optional.of(oreItemTag) : Optional.empty();
	}

	public Optional<Tag.Named<Item>> getStorageBlockItemTag()
	{
		return storageBlockItemTag != null ? Optional.of(storageBlockItemTag) : Optional.empty();
	}

	public Optional<Tag.Named<Item>> getDustTag()
	{
		return dustTag != null ? Optional.of(dustTag) : Optional.empty();
	}

	public Optional<Tag.Named<Item>> getGemTag()
	{
		return gemTag != null ? Optional.of(gemTag) : Optional.empty();
	}

	public Optional<? extends Item> getItemFromType(ResourceItemType type, boolean ignoreVanilla)
	{
		return switch (type)
				{
					case DUST -> getDust(ignoreVanilla);
					case GEM -> getGem(ignoreVanilla);
					case ORE -> getOreItem(ignoreVanilla);
					case DEEP_ORE -> getDeepOreItem(ignoreVanilla);
					case STORAGE -> getStorageBlockItem(ignoreVanilla);
					default -> Optional.empty();
				};
	}

	public Optional<Block> getBlockFromType(ResourceItemType type, boolean ignoreVanilla)
	{
		return switch (type)
				{
					case ORE -> getOre(ignoreVanilla);
					case DEEP_ORE -> getDeepOre(ignoreVanilla);
					case STORAGE -> getStorageBlock(ignoreVanilla);
					default -> Optional.empty();
				};
	}

//	public Ingredient getSmeltables()
//	{
//		return getSmeltables(true);
//	}
//
//	public Ingredient getSmeltables(boolean includeIngot)
//	{
//		Stream.Builder<ITag.INamedTag<Item>> builder = Stream.builder();
//		if (includeIngot)
//		{
//			getIngotTag().ifPresent(builder::add);
//		}
//		getDustTag().ifPresent(builder::add);
//		return Ingredient.fromItemListStream(builder.build().map(Ingredient.TagList::new));
//	}

	private static class Builder
	{
		final String name;
		boolean vanilla = false;
		Supplier<Block> ore;
		Supplier<Block> storageBlock;
		Supplier<Item> dust;
		Supplier<Item> gem;
		Tag.Named<Block> oreTag;
		Tag.Named<Block> storageBlockTag;
		Tag.Named<Item> dustTag;
		Tag.Named<Item> gemTag;
		MineOres mineOres;

		Builder(String name)
		{
			this.name = name;
		}

		private static Tag.Named<Block> blockTag(String path)
		{
			return TagUtil.makeBlock(path);
		}

		private static Tag.Named<Item> itemTag(String path)
		{
			return TagUtil.makeItem(path);
		}

		private static Tag.Named<Item> itemTag(ResourceLocation tag)
		{
			return TagHooks.optionalItem(tag);
		}

		Builder ore(MineOres ore)
		{
			this.mineOres = ore;
			this.ore = () -> new OreBlock(BlockProperties.of(Material.STONE)
					.requiresCorrectToolForDrops()
					.strength(ore.getHardness(), 3)
					.sound(SoundType.STONE));
			this.oreTag = blockTag("ores/" + name);
			return this;
		}

		Builder storageBlock()
		{
			this.storageBlock = MetalBlock::new;
			this.storageBlockTag = blockTag("storage_blocks/" + name);
			return this;
		}

		Builder vanilla()
		{
			this.vanilla = true;
			return this;
		}

		Builder dust()
		{
			this.dust = () -> new ResourceItem(new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.DUST);
			this.dustTag = itemTag("dusts/" + name);
			return this;
		}

		Builder dustExisting(RegistrySupplier<Item> existing)
		{
			this.dust = existing;
			this.dustTag = itemTag("dusts/" + name);
			return this;
		}

		Builder gem()
		{
			this.gem = () -> new ResourceItem(new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.GEM);
			this.gemTag = itemTag("gems/" + name);
			return this;
		}

		Builder gemExisting(RegistrySupplier<Item> existing)
		{
			return gemExisting(existing, itemTag("gems/" + name));
		}

		Builder gemExisting(RegistrySupplier<Item> item, Tag.Named<Item> tag)
		{
			this.gem = item;
			this.gemTag = tag;
			return this;
		}

		Builder oreTagOnly()
		{
			this.oreTag = blockTag("ores/" + name);
			return this;
		}
	}
}
