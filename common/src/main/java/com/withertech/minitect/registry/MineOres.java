package com.withertech.minitect.registry;

import com.withertech.mine_tags.tags.PlatformTags;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.config.MineConfig;
import com.withertech.minitect.config.OreConfigs;
import com.withertech.minitect.util.BlockLike;
import dev.architectury.registry.block.ToolType;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public enum MineOres
{
	TIN(() -> MineMetals.TIN, null, 3, Tiers.STONE, new DefaultOreConfigs(true, 10, 8, -34, 56)),
	SILVER(() -> MineMetals.SILVER, null, 4, Tiers.IRON, new DefaultOreConfigs(true, 6, 8, -64, -4)),
	LEAD(() -> MineMetals.LEAD, null, 4, Tiers.IRON, new DefaultOreConfigs(true, 6, 8, -64, -19)),
	NICKEL(() -> MineMetals.NICKEL, null, 5, Tiers.IRON, new DefaultOreConfigs(true, 3, 6, -64, -28)),
	PLATINUM(() -> MineMetals.PLATINUM, null, 5, Tiers.IRON, new DefaultOreConfigs(true, 3, 8, -56, -34)),
	ZINC(() -> MineMetals.ZINC, null, 3, Tiers.STONE, new DefaultOreConfigs(true, 6, 8, -34, 26)),
	BISMUTH(() -> MineMetals.BISMUTH, null, 3, Tiers.STONE, new DefaultOreConfigs(true, 6, 8, -40, 32)),
	BAUXITE(() -> MineMetals.ALUMINUM, null, 4, Tiers.STONE, new DefaultOreConfigs(true, 8, 8, -41, 11)),
	URANIUM(() -> MineMetals.URANIUM, null, 6, Tiers.IRON, new DefaultOreConfigs(true, 3, 4, -64, -37)),
	RUBY(null, () -> MineGems.RUBY, 6, Tiers.IRON, new DefaultOreConfigs(true, 3, 4, -64, -37)),
	;

	private final Supplier<MineMetals> metal;
	private final Supplier<MineGems> gem;
	private final DefaultOreConfigs defaultOreConfigs;
	private final int hardness;
	private final Tiers harvestLevel;
	private RegistrySupplier<ConfiguredFeature<?, ?>> configuredFeature;
	private RegistrySupplier<PlacedFeature> placedFeature;

	MineOres(Supplier<MineMetals> metal, Supplier<MineGems> gem, int hardness, Tiers harvestLevel, DefaultOreConfigs defaultOreConfigs)
	{
		this.metal = metal;
		this.gem = gem;
		this.defaultOreConfigs = defaultOreConfigs;
		this.hardness = hardness;
		this.harvestLevel = harvestLevel;
	}

	public static void register()
	{
		registerFeatures();
		BiomeModifications.addProperties(biomeContext -> biomeContext.getProperties().getCategory() != Biome.BiomeCategory.NETHER && biomeContext.getProperties().getCategory() != Biome.BiomeCategory.THEEND, (biomeContext, mutable) ->
		{
			for (MineOres ore : values())
			{
				if (ore.getConfig().isEnabled() && ore.getGlobalConfig().isMaster())
					mutable.getGenerationProperties().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ore.getPlacedFeature());
			}
		});
	}

	public static void registerFeatures()
	{
		for (MineOres ore : values())
		{
			ore.configuredFeature = registerConfigured(ore.getName(), () ->
			{
				OreConfigs.OreConfig config = ore.getConfig();
				assert config != null;

				return Feature.ORE
						.configured(new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ore.getBlock().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ore.getDeepBlock().defaultBlockState())), config.getVeinSize()));
			});
			ore.placedFeature = registerPlaced(ore.getName(), (() ->
			{
				OreConfigs.OreConfig config = ore.getConfig();
				assert config != null;
				int bottom = config.getMinHeight();
				int top = config.getMaxHeight();
				int count = config.getVeinCount();
				return ore.configuredFeature.get()
						.placed(CountPlacement.of(count),
								InSquarePlacement.spread(),
								HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(bottom), VerticalAnchor.belowTop(top)));
			}));
		}
	}

	private static RegistrySupplier<ConfiguredFeature<?, ?>> registerConfigured(String name, Supplier<ConfiguredFeature<?, ?>> feature)
	{
		return Registries.get(Minitect.MOD_ID).get(BuiltinRegistries.CONFIGURED_FEATURE).register(Minitect.modLoc(name), feature);
	}

	private static RegistrySupplier<PlacedFeature> registerPlaced(String name, Supplier<PlacedFeature> feature)
	{
		return Registries.get(Minitect.MOD_ID).get(BuiltinRegistries.PLACED_FEATURE).register(Minitect.modLoc(name), feature);
	}

	public OreConfigs.OreConfig getConfig()
	{
		return AutoConfig.getConfigHolder(MineConfig.class).getConfig().ores.ores.get(this);
	}

	public OreConfigs getGlobalConfig()
	{
		return AutoConfig.getConfigHolder(MineConfig.class).getConfig().ores;
	}

	public String getName()
	{
		return name().toLowerCase(Locale.ROOT);
	}

	public int getHardness()
	{
		return hardness;
	}

	public Tiers getHarvestLevel()
	{
		return harvestLevel;
	}

	public DefaultOreConfigs getDefaultOreConfigs()
	{
		return defaultOreConfigs;
	}

	public ConfiguredFeature<?, ?> getConfiguredFeature()
	{
		return configuredFeature.get();
	}
	public PlacedFeature getPlacedFeature()
	{
		return placedFeature.get();
	}

	public Block getBlock()
	{
		if (gem != null)
			return gem.get().getOre().orElse(null);
		if (metal != null)
			return metal.get().getOre().orElse(null);
		return null;

	}

	public Block getDeepBlock()
	{
		if (gem != null)
			return gem.get().getDeepOre().orElse(null);
		if (metal != null)
			return metal.get().getDeepOre().orElse(null);
		return null;

	}

	public record DefaultOreConfigs(boolean enabled, int veinCount, int veinSize, int minHeight, int maxHeight)
	{

	}
}
