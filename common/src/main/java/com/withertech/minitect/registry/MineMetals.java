package com.withertech.minitect.registry;


import com.google.common.collect.Streams;
import com.withertech.minitect.block.MetalBlock;
import com.withertech.minitect.item.ResourceBlockItem;
import com.withertech.minitect.item.ResourceItem;
import com.withertech.minitect.item.ResourceItemType;
import com.withertech.minitect.util.RegistryUtil;
import com.withertech.minitect.util.TagUtil;
import dev.architectury.hooks.tags.TagHooks;
import dev.architectury.registry.block.BlockProperties;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MineMetals
{
	REFINED_IRON(builder("refined_iron").ingot()),
	IRON(builder("iron").vanilla().oreTagOnly().dust().plate().gear().rod().rawExisting(RegistryUtil.delegate(Items.RAW_IRON)).ingotExisting(RegistryUtil.delegate(Items.IRON_INGOT)).nuggetExisting(RegistryUtil.delegate(Items.IRON_NUGGET))),
	GOLD(builder("gold").vanilla().oreTagOnly().dust().plate().gear().rod().rawExisting(RegistryUtil.delegate(Items.RAW_GOLD)).ingotExisting(RegistryUtil.delegate(Items.GOLD_INGOT)).nuggetExisting(RegistryUtil.delegate(Items.GOLD_NUGGET))),
	COPPER(builder("copper").vanilla().oreTagOnly().dust().plate().gear().rod().rawExisting(RegistryUtil.delegate(Items.RAW_COPPER)).ingotExisting(RegistryUtil.delegate(Items.COPPER_INGOT)).nugget()),
	TIN(builderBaseWithOre("tin", MineOres.TIN)),
	SILVER(builderBaseWithOre("silver", MineOres.SILVER)),
	LEAD(builderBaseWithOre("lead", MineOres.LEAD)),
	NICKEL(builderBaseWithOre("nickel", MineOres.NICKEL)),
	PLATINUM(builderBaseWithOre("platinum", MineOres.PLATINUM)),
	ZINC(builderBaseWithOre("zinc", MineOres.ZINC)),
	BISMUTH(builderBaseWithOre("bismuth", MineOres.BISMUTH)),
	ALUMINUM(builderBaseWithOre("aluminum", MineOres.BAUXITE), "bauxite"),
	URANIUM(builderBaseWithOre("uranium", MineOres.URANIUM)),
	BRONZE(builderAlloy("bronze")
			.alloy(4)
			.metal(COPPER, 3)
			.metal(TIN, 1)
			.build()),
	BRASS(builderAlloy("brass")
			.alloy(4)
			.metal(COPPER, 3)
			.metal(ZINC, 1)
			.build()),
	INVAR(builderAlloy("invar")
			.alloy(3)
			.metal(IRON, 2)
			.metal(NICKEL, 1)
			.build()),
	ELECTRUM(builderAlloy("electrum")
			.alloy(2)
			.metal(GOLD, 1)
			.metal(SILVER, 1)
			.build()),
	STEEL(builderAlloy("steel")
			.alloy(2)
			.metal(IRON, 2)
			.gem(MineGems.COAL, 2)
			.build()),
	BISMUTH_BRASS(builderAlloy("bismuth_brass")
			.alloy(4)
			.metal(COPPER, 2)
			.metal(ZINC, 1)
			.metal(BISMUTH, 1)
			.build()),
	ALUMINUM_STEEL(builderAlloy("aluminum_steel")
			.alloy(4)
			.metal(IRON, 2)
			.metal(ALUMINUM, 1)
			.gem(MineGems.COAL, 3)
			.build()),
	BISMUTH_STEEL(builderAlloy("bismuth_steel")
			.alloy(4)
			.metal(IRON, 2)
			.metal(BISMUTH, 1)
			.gem(MineGems.COAL, 3)
			.build()),
	REDSTONE_ALLOY(builderAlloy("redstone_alloy")
			.alloy(2)
			.metal(IRON, 1)
			.gem(MineGems.REDSTONE, 4)
			.build()),
	SIGNALUM(builderAlloy("signalum")
			.alloy(4)
			.metal(COPPER, 3)
			.metal(SILVER, 1)
			.gem(MineGems.REDSTONE, 4)
			.build()),
	LUMIUM(builderAlloy("lumium")
			.alloy(4)
			.metal(TIN, 3)
			.metal(SILVER, 1)
			.gem(MineGems.GLOWSTONE, 4)
			.build()),
	ENDERIUM(builderAlloy("enderium")
			.alloy(4)
			.metal(LEAD, 3)
			.metal(PLATINUM, 1)
			.gem(MineGems.ENDER_PEARL, 4)
			.build());

	private final String oreName;
	private final Supplier<Block> oreSupplier;
	private final Supplier<Block> deepOreSupplier;
	private final Supplier<Block> storageBlockSupplier;
	private final Supplier<Item> dustSupplier;
	private final Supplier<Item> ingotSupplier;
	private final Supplier<Item> nuggetSupplier;
	private final Supplier<Item> plateSupplier;
	private final Supplier<Item> gearSupplier;
	private final Supplier<Item> rodSupplier;
	private final Supplier<Item> rawSupplier;
	private final Tag.Named<Block> storageBlockTag;
	private final Tag.Named<Block> oreTag;
	private final Tag.Named<Item> storageBlockItemTag;
	private final Tag.Named<Item> oreItemTag;
	private final Tag.Named<Item> dustTag;
	private final Tag.Named<Item> ingotTag;
	private final Tag.Named<Item> nuggetTag;
	private final Tag.Named<Item> plateTag;
	private final Tag.Named<Item> gearTag;
	private final Tag.Named<Item> rodTag;
	private final Tag.Named<Item> rawTag;
	private final MineOres mineOres;
	private final Alloy alloy;
	private final boolean vanilla;
	private RegistrySupplier<Block> ore;
	private RegistrySupplier<Block> deepOre;
	private RegistrySupplier<Block> storageBlock;
	private RegistrySupplier<BlockItem> oreItem;
	private RegistrySupplier<BlockItem> deepOreItem;
	private RegistrySupplier<BlockItem> storageBlockItem;
	private RegistrySupplier<Item> dust;
	private RegistrySupplier<Item> ingot;
	private RegistrySupplier<Item> nugget;
	private RegistrySupplier<Item> plate;
	private RegistrySupplier<Item> gear;
	private RegistrySupplier<Item> rod;
	private RegistrySupplier<Item> raw;

	MineMetals(Builder builder)
	{
		this(builder, builder.name);
	}

	MineMetals(Builder builder, String oreName)
	{
		if (!builder.name.equals(this.getName()))
		{
			throw new IllegalArgumentException("Builder name is incorrect, should be " + this.getName());
		}
		this.alloy = builder.alloy;
		this.oreName = oreName;
		this.storageBlockSupplier = builder.storageBlock;
		this.oreSupplier = builder.ore;
		this.deepOreSupplier = builder.ore;
		this.dustSupplier = builder.dust;
		this.ingotSupplier = builder.ingot instanceof RegistrySupplier<Item> ? null : builder.ingot;
		this.ingot = builder.ingot instanceof RegistrySupplier<Item> ? (RegistrySupplier<Item>) builder.ingot : null;
		this.nuggetSupplier = builder.nugget instanceof RegistrySupplier<Item> ? null : builder.nugget;
		this.nugget = builder.nugget instanceof RegistrySupplier<Item> ? (RegistrySupplier<Item>) builder.nugget : null;
		this.plateSupplier = builder.plate;
		this.gearSupplier = builder.gear;
		this.rodSupplier = builder.rod;
		this.rawSupplier = builder.raw instanceof RegistrySupplier<Item> ? null : builder.raw;
		this.raw = builder.raw instanceof RegistrySupplier<Item> ? (RegistrySupplier<Item>) builder.raw : null;
		this.mineOres = builder.mineOres;
		this.oreTag = builder.oreTag;
		this.storageBlockTag = builder.storageBlockTag;
		this.oreItemTag = this.oreTag != null ? Builder.itemTag(this.oreTag.getName()) : null;
		this.storageBlockItemTag = this.storageBlockTag != null ? Builder.itemTag(this.storageBlockTag.getName()) : null;
		this.dustTag = builder.dustTag;
		this.ingotTag = builder.ingotTag;
		this.nuggetTag = builder.nuggetTag;
		this.plateTag = builder.plateTag;
		this.gearTag = builder.gearTag;
		this.rodTag = builder.rodTag;
		this.rawTag = builder.rawTag;
		this.vanilla = builder.vanilla;
	}

	public static void register()
	{
		registerBlocks();
		registerItems();
	}

	private static void registerBlocks()
	{
		for (MineMetals metal : values())
		{
			if (metal.oreSupplier != null)
			{
				String name = metal.oreName + "_ore";
				metal.ore = MineRegistries.BLOCKS.register(name, metal.oreSupplier);
				metal.oreItem = MineRegistries.ITEMS.register(name, () -> new ResourceBlockItem(metal.ore.get(), new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.ORE));
			}
			if (metal.deepOreSupplier != null)
			{
				String name = metal.oreName + "_deep_ore";
				metal.deepOre = MineRegistries.BLOCKS.register(name, metal.deepOreSupplier);
				metal.deepOreItem = MineRegistries.ITEMS.register(name, () -> new ResourceBlockItem(metal.deepOre.get(), new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.DEEP_ORE));
			}
		}
		for (MineMetals metal : values())
		{
			if (metal.storageBlockSupplier != null)
			{
				String name = metal.getName() + "_block";
				metal.storageBlock = MineRegistries.BLOCKS.register(name, metal.storageBlockSupplier);
				metal.storageBlockItem = MineRegistries.ITEMS.register(name, () -> new ResourceBlockItem(metal.storageBlock.get(), new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.STORAGE));
			}
		}
	}

	private static void registerItems()
	{
		for (MineMetals metal : values())
		{
			if (metal.dustSupplier != null)
			{
				metal.dust = MineRegistries.ITEMS.register(
						metal.getName() + "_dust", metal.dustSupplier);
			}
			if (metal.ingotSupplier != null)
			{
				metal.ingot = MineRegistries.ITEMS.register(
						metal.getName() + "_ingot", metal.ingotSupplier);
			}
			if (metal.nuggetSupplier != null)
			{
				metal.nugget = MineRegistries.ITEMS.register(
						metal.getName() + "_nugget", metal.nuggetSupplier);
			}
			if (metal.plateSupplier != null)
			{
				metal.plate = MineRegistries.ITEMS.register(
						metal.getName() + "_plate", metal.plateSupplier);
			}
			if (metal.gearSupplier != null)
			{
				metal.gear = MineRegistries.ITEMS.register(
						metal.getName() + "_gear", metal.gearSupplier);
			}
			if (metal.rodSupplier != null)
			{
				metal.rod = MineRegistries.ITEMS.register(
						metal.getName() + "_rod", metal.rodSupplier);
			}
		}
	}

	private static Builder builder(String name)
	{
		return new Builder(name);
	}

	private static Builder builderBaseWithOre(String name, MineOres ore)
	{
		return builder(name).storageBlock().ore(ore).dust().ingot().nugget().plate().gear().rod();
	}

	private static Builder builderAlloy(String name)
	{
		return builder(name).storageBlock().dust().ingot().nugget().plate().gear().rod();
	}

	public String getName()
	{
		return name().toLowerCase(Locale.ROOT);
	}

	public boolean isVanilla()
	{
		return vanilla;
	}

	public Optional<Alloy> getAlloy()
	{
		return Optional.ofNullable(alloy);
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

	public Optional<Item> getIngot()
	{
		return getIngot(false);
	}

	public Optional<Item> getIngot(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return ingot != null && !RegistryUtil.isVanilla(ingot.get()) ? Optional.of(ingot.get()) : Optional.empty();
		return ingot != null ? Optional.of(ingot.get()) : Optional.empty();
	}

	public Optional<Item> getNugget()
	{
		return getNugget(false);
	}

	public Optional<Item> getNugget(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return nugget != null && !RegistryUtil.isVanilla(nugget.get()) ? Optional.of(nugget.get()) : Optional.empty();
		return nugget != null ? Optional.of(nugget.get()) : Optional.empty();
	}

	public Optional<Item> getPlate()
	{
		return getPlate(false);
	}

	public Optional<Item> getPlate(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return plate != null && !RegistryUtil.isVanilla(plate.get()) ? Optional.of(plate.get()) : Optional.empty();
		return plate != null ? Optional.of(plate.get()) : Optional.empty();
	}

	public Optional<Item> getGear()
	{
		return getGear(false);
	}

	public Optional<Item> getGear(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return gear != null && !RegistryUtil.isVanilla(gear.get()) ? Optional.of(gear.get()) : Optional.empty();
		return gear != null ? Optional.of(gear.get()) : Optional.empty();
	}

	public Optional<Item> getRod()
	{
		return getRod(false);
	}

	public Optional<Item> getRod(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return rod != null && !RegistryUtil.isVanilla(rod.get()) ? Optional.of(rod.get()) : Optional.empty();
		return rod != null ? Optional.of(rod.get()) : Optional.empty();
	}

	public Optional<Item> getRaw()
	{
		return getRaw(false);
	}

	public Optional<Item> getRaw(boolean ignoreVanilla)
	{
		if (ignoreVanilla)
			return raw != null && !RegistryUtil.isVanilla(raw.get()) ? Optional.of(raw.get()) : Optional.empty();
		return raw != null ? Optional.of(raw.get()) : Optional.empty();
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

	public Optional<Tag.Named<Item>> getIngotTag()
	{
		return ingotTag != null ? Optional.of(ingotTag) : Optional.empty();
	}

	public Optional<Tag.Named<Item>> getNuggetTag()
	{
		return nuggetTag != null ? Optional.of(nuggetTag) : Optional.empty();
	}

	public Optional<Tag.Named<Item>> getPlateTag()
	{
		return plateTag != null ? Optional.of(plateTag) : Optional.empty();
	}

	public Optional<Tag.Named<Item>> getGearTag()
	{
		return gearTag != null ? Optional.of(gearTag) : Optional.empty();
	}

	public Optional<Tag.Named<Item>> getRodTag()
	{
		return rodTag != null ? Optional.of(rodTag) : Optional.empty();
	}

	public Optional<Tag.Named<Item>> getRawTag()
	{
		return rawTag != null ? Optional.of(rawTag) : Optional.empty();
	}

	public Optional<? extends Item> getItemFromType(ResourceItemType type, boolean ignoreVanilla)
	{
		return switch (type)
				{
					case DUST -> getDust(ignoreVanilla);
					case INGOT -> getIngot(ignoreVanilla);
					case NUGGET -> getNugget(ignoreVanilla);
					case PLATE -> getPlate(ignoreVanilla);
					case GEAR -> getGear(ignoreVanilla);
					case ROD -> getRod(ignoreVanilla);
					case RAW -> getRaw(ignoreVanilla);
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

	public Ingredient getSmeltables()
	{
		return getSmeltables(true);
	}

	public Ingredient getSmeltables(boolean includeIngot)
	{
		Stream.Builder<Tag.Named<Item>> builder = Stream.builder();
		if (includeIngot)
		{
			getIngotTag().ifPresent(builder::add);
		}
		getDustTag().ifPresent(builder::add);
		return Ingredient.of(builder.build().flatMap(itemNamed -> itemNamed.getValues().stream().map(Item::getDefaultInstance)));
	}

	public static class Alloy
	{
		private final Map<MineMetals, Integer> metalIngredients;
		private final Map<MineGems, Integer> gemIngredients;
		private final Map<Ingredient, Integer> extraIngredients;
		private final int resultCount;

		private Alloy(Alloy.Builder builder)
		{
			metalIngredients = builder.metalIngredients;
			gemIngredients = builder.gemIngredients;
			extraIngredients = builder.extraIngredients;
			resultCount = builder.resultCount;
		}

		private static Builder builder(int resultCount, MineMetals.Builder parent)
		{
			return new Builder(resultCount, parent);
		}

		public int getResultCount()
		{
			return resultCount;
		}

		public Map<Ingredient, Integer> getDustIngredients()
		{
			return Streams.concat(
					metalIngredients.entrySet().stream().map(metal -> Map.entry(metal.getKey().getDustTag().map(Ingredient::of).orElseThrow(), metal.getValue())),
					gemIngredients.entrySet().stream().map(gem -> Map.entry(gem.getKey().getDustTag().map(Ingredient::of).orElseThrow(), gem.getValue())),
					extraIngredients.entrySet().stream()
			).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}

		public Map<Ingredient, Integer> getIngotIngredients()
		{
			return Streams.concat(
					metalIngredients.entrySet().stream().map(metal -> Map.entry(metal.getKey().getIngotTag().map(Ingredient::of).orElseThrow(), metal.getValue())),
					gemIngredients.entrySet().stream().map(gem -> Map.entry(gem.getKey().getGemTag().map(Ingredient::of).orElseThrow(), gem.getValue())),
					extraIngredients.entrySet().stream()
			).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		}

		private static class Builder
		{
			private final Map<MineMetals, Integer> metalIngredients = new HashMap<>();
			private final Map<MineGems, Integer> gemIngredients = new HashMap<>();
			private final Map<Ingredient, Integer> extraIngredients = new HashMap<>();
			private final int resultCount;
			private final MineMetals.Builder parent;

			private Builder(int resultCount, MineMetals.Builder parent)
			{
				this.resultCount = resultCount;
				this.parent = parent;
			}

			public Builder metal(MineMetals metal, int count)
			{
				metalIngredients.put(metal, count);
				return this;
			}

			public Builder gem(MineGems gem, int count)
			{
				gemIngredients.put(gem, count);
				return this;
			}

			public Builder other(Ingredient other, int count)
			{
				extraIngredients.put(other, count);
				return this;
			}

			public MineMetals.Builder build()
			{
				parent.alloy = new Alloy(this);
				return parent;
			}
		}
	}

	private static class Builder
	{
		final String name;
		MineOres mineOres;
		Supplier<Block> ore;
		Supplier<Block> storageBlock;
		Supplier<Item> dust;
		Supplier<Item> ingot;
		Supplier<Item> nugget;
		Supplier<Item> plate;
		Supplier<Item> gear;
		Supplier<Item> rod;
		Supplier<Item> raw;
		Tag.Named<Block> oreTag;
		Tag.Named<Block> storageBlockTag;
		Tag.Named<Item> dustTag;
		Tag.Named<Item> ingotTag;
		Tag.Named<Item> nuggetTag;
		Tag.Named<Item> plateTag;
		Tag.Named<Item> gearTag;
		Tag.Named<Item> rodTag;
		Tag.Named<Item> rawTag;
		Alloy alloy;
		boolean vanilla = false;

		public Builder(String name)
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


		Builder dust()
		{
			this.dust = () -> new ResourceItem(new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.DUST);
			this.dustTag = itemTag("dusts/" + name);
			return this;
		}

		Builder ingot()
		{
			this.ingot = () -> new ResourceItem(new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.INGOT);
			this.ingotTag = itemTag("ingots/" + name);
			return this;
		}

		Builder ingotExisting(RegistrySupplier<Item> existing)
		{
			this.ingot = existing;
			this.ingotTag = itemTag("ingots/" + name);
			return this;
		}

		Builder nugget()
		{
			this.nugget = () -> new ResourceItem(new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.NUGGET);
			this.nuggetTag = itemTag("nuggets/" + name);
			return this;
		}

		Builder nuggetExisting(RegistrySupplier<Item> existing)
		{
			this.nugget = existing;
			this.nuggetTag = itemTag("nuggets/" + name);
			return this;
		}

		Builder rawExisting(RegistrySupplier<Item> existing)
		{
			this.raw = existing;
			this.rawTag = itemTag("raw_materials/" + name);
			return this;
		}

		Builder plate()
		{
			this.plate = () -> new ResourceItem(new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.PLATE);
			this.plateTag = itemTag("plates/" + name);
			return this;
		}

		Builder gear()
		{
			this.gear = () -> new ResourceItem(new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.GEAR);
			this.gearTag = itemTag("gear/" + name);
			return this;
		}

		Builder rod()
		{
			this.rod = () -> new ResourceItem(new Item.Properties().tab(MineTabs.MATERIALS), ResourceItemType.ROD);
			this.rodTag = itemTag("rods/" + name);
			return this;
		}

		Alloy.Builder alloy(int resultCount)
		{
			return Alloy.builder(resultCount, this);
		}

		Builder oreTagOnly()
		{
			this.oreTag = blockTag("ores/" + name);
			return this;
		}

		Builder vanilla()
		{
			this.vanilla = true;
			return this;
		}

	}
}