package com.withertech.minitect.data.tag;

import com.withertech.mine_tags.tags.PlatformTags;
import com.withertech.minitect.item.ResourceItemType;
import com.withertech.minitect.registry.MineGems;
import com.withertech.minitect.registry.MineMetals;
import com.withertech.minitect.registry.MineTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class MineBlockTagProviderFabric extends FabricTagProvider.BlockTagProvider
{
	public MineBlockTagProviderFabric(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator);
	}

	@Override
	protected void generateTags()
	{
		for (MineMetals metal : MineMetals.values())
		{
			metal.getStorageBlockTag().ifPresent(tag ->
					metal.getStorageBlock(true).ifPresent(block ->
							tag(tag).add(block)));
			metal.getStorageBlock(true).ifPresent(block ->
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block));
			metal.getOreTag().ifPresent(tag ->
					metal.getOre(true).ifPresent(ore ->
							tag(tag).add(ore)));
			metal.getOreTag().ifPresent(tag ->
					metal.getDeepOre(true).ifPresent(ore ->
							tag(tag).add(ore)));
			metal.getOre(true).ifPresent(ore ->
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ore));
			metal.getDeepOre(true).ifPresent(ore ->
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ore));
			metal.getMineOres().ifPresent(mineOres ->
					metal.getOre(true).ifPresent(ore ->
							tag(MineTags.Blocks.TIERS.get(mineOres.getHarvestLevel())).add(ore)));
			metal.getMineOres().ifPresent(mineOres ->
					metal.getDeepOre(true).ifPresent(ore ->
							tag(MineTags.Blocks.TIERS.get(mineOres.getHarvestLevel())).add(ore)));
		}
		for (MineGems gem : MineGems.values())
		{
			gem.getStorageBlockTag().ifPresent(tag ->
					gem.getStorageBlock(true).ifPresent(block ->
							tag(tag).add(block)));
			gem.getStorageBlock(true).ifPresent(block ->
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block));
			gem.getOreTag().ifPresent(tag ->
					gem.getOre(true).ifPresent(block ->
							tag(tag).add(block)));
			gem.getOreTag().ifPresent(tag ->
					gem.getDeepOre(true).ifPresent(block ->
							tag(tag).add(block)));
			gem.getOre(true).ifPresent(block ->
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block));
			gem.getDeepOre(true).ifPresent(block ->
					tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block));
			gem.getMineOres().ifPresent(mineOres ->
					gem.getOre(true).ifPresent(ore ->
							tag(MineTags.Blocks.TIERS.get(mineOres.getHarvestLevel())).add(ore)));
			gem.getMineOres().ifPresent(mineOres ->
					gem.getDeepOre(true).ifPresent(ore ->
							tag(MineTags.Blocks.TIERS.get(mineOres.getHarvestLevel())).add(ore)));
		}

		groupMetalBuilder(PlatformTags.Blocks.STORAGE_BLOCKS, MineMetals::getStorageBlockTag, ResourceItemType.STORAGE);
		groupMetalBuilder(PlatformTags.Blocks.ORES, MineMetals::getOreTag, ResourceItemType.ORE);

		groupGemBuilder(PlatformTags.Blocks.STORAGE_BLOCKS, MineGems::getStorageBlockTag, ResourceItemType.STORAGE);
		groupGemBuilder(PlatformTags.Blocks.ORES, MineGems::getOreTag, ResourceItemType.ORE);
	}

	@SafeVarargs
	private void groupMetalBuilder(Tag.Named<Block> tag, Function<MineMetals, Optional<Tag.Named<Block>>> tagGetter, ResourceItemType type, Tag.Named<Block>... extras)
	{
		groupMetalBuilder(tag, tagGetter, metal -> metal.getBlockFromType(type, true).isPresent(), extras);
	}

	@SafeVarargs
	private void groupMetalBuilder(Tag.Named<Block> tag, Function<MineMetals, Optional<Tag.Named<Block>>> tagGetter, Predicate<MineMetals> predicate, Tag.Named<Block>... extras)
	{
		TagAppender<Block> builder = tag(tag);
		for (MineMetals metal : MineMetals.values())
		{
			if (predicate.test(metal))
				tagGetter.apply(metal).ifPresent(builder::addTag);
		}
		for (Tag.Named<Block> extraTag : extras)
		{
			builder.addTag(extraTag);
		}
	}

	@SafeVarargs
	private void groupGemBuilder(Tag.Named<Block> tag, Function<MineGems, Optional<Tag.Named<Block>>> tagGetter, ResourceItemType type, Tag.Named<Block>... extras)
	{
		groupGemBuilder(tag, tagGetter, gem -> gem.getBlockFromType(type, true).isPresent(), extras);
	}

	@SafeVarargs
	private void groupGemBuilder(Tag.Named<Block> tag, Function<MineGems, Optional<Tag.Named<Block>>> tagGetter, Predicate<MineGems> predicate, Tag.Named<Block>... extras)
	{
		TagAppender<Block> builder = tag(tag);
		for (MineGems gem : MineGems.values())
		{
			if (predicate.test(gem))
				tagGetter.apply(gem).ifPresent(builder::addTag);
		}
		for (Tag.Named<Block> extraTag : extras)
		{
			builder.addTag(extraTag);
		}
	}
}
