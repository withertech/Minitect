package com.withertech.minitect.data.tag;

import com.withertech.mine_tags.tags.PlatformTags;
import com.withertech.minitect.Minitect;
import com.withertech.minitect.item.ResourceItemType;
import com.withertech.minitect.registry.MineGems;
import com.withertech.minitect.registry.MineMetals;
import com.withertech.minitect.registry.MineTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class MineItemTagProviderForge extends ItemTagsProvider
{
	public MineItemTagProviderForge(DataGenerator arg, BlockTagsProvider blocks, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(arg, blocks, Minitect.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags()
	{
		for (MineMetals metal : MineMetals.values())
		{
			metal.getStorageBlockTag().ifPresent(tag ->
					metal.getStorageBlockItemTag().ifPresent(block ->
							copy(tag, block)));
			metal.getOreTag().ifPresent(tag ->
					metal.getOreItemTag().ifPresent(ore ->
							copy(tag, ore)));
			metal.getDustTag().ifPresent(tag ->
					metal.getDust(true).ifPresent(item ->
							tag(tag).add(item)));
			metal.getIngotTag().ifPresent(tag ->
					metal.getIngot(true).ifPresent(item ->
							tag(tag).add(item)));
			metal.getNuggetTag().ifPresent(tag ->
					metal.getNugget(true).ifPresent(item ->
							tag(tag).add(item)));
			metal.getPlateTag().ifPresent(tag ->
					metal.getPlate(true).ifPresent(item ->
							tag(tag).add(item)));
			metal.getRodTag().ifPresent(tag ->
					metal.getRod(true).ifPresent(item ->
							tag(tag).add(item)));
			metal.getGearTag().ifPresent(tag ->
					metal.getGear(true).ifPresent(item ->
							tag(tag).add(item)));
		}
		for (MineGems gem : MineGems.values())
		{
			gem.getStorageBlockTag().ifPresent(tag ->
					gem.getStorageBlockItemTag().ifPresent(block ->
							copy(tag, block)));
			gem.getOreTag().ifPresent(tag ->
					gem.getOreItemTag().ifPresent(ore ->
							copy(tag, ore)));
			gem.getGemTag().ifPresent(tag ->
					gem.getGem(true).ifPresent(item ->
							tag(tag).add(item)));
			gem.getDustTag().ifPresent(tag ->
					gem.getDust(true).ifPresent(item ->
							tag(tag).add(item)));
		}

		copy(PlatformTags.Blocks.ORES, PlatformTags.Items.ORES);
		copy(PlatformTags.Blocks.STORAGE_BLOCKS, PlatformTags.Items.STORAGE_BLOCKS);
		groupMetalBuilder(PlatformTags.Items.INGOTS, MineMetals::getIngotTag, ResourceItemType.INGOT);
		groupMetalBuilder(PlatformTags.Items.DUSTS, MineMetals::getDustTag, ResourceItemType.DUST);
		groupMetalBuilder(PlatformTags.Items.NUGGETS, MineMetals::getNuggetTag, ResourceItemType.NUGGET);
		groupMetalBuilder(MineTags.Items.PLATES, MineMetals::getPlateTag, ResourceItemType.PLATE);
		groupMetalBuilder(MineTags.Items.RODS, MineMetals::getRodTag, ResourceItemType.ROD);
		groupMetalBuilder(MineTags.Items.GEAR, MineMetals::getGearTag, ResourceItemType.GEAR);

		groupGemBuilder(PlatformTags.Items.GEMS, MineGems::getGemTag, ResourceItemType.GEM);
		groupGemBuilder(PlatformTags.Items.DUSTS, MineGems::getDustTag, ResourceItemType.DUST);
	}

	@SafeVarargs
	private void groupMetalBuilder(Tag.Named<Item> tag, Function<MineMetals, Optional<Tag.Named<Item>>> tagGetter, ResourceItemType type, Tag.Named<Item>... extras)
	{
		groupMetalBuilder(tag, tagGetter, metal -> metal.getItemFromType(type, true).isPresent(), extras);
	}

	@SafeVarargs
	private void groupMetalBuilder(Tag.Named<Item> tag, Function<MineMetals, Optional<Tag.Named<Item>>> tagGetter, Predicate<MineMetals> predicate, Tag.Named<Item>... extras)
	{
		if (Arrays.stream(MineMetals.values()).anyMatch(predicate))
		{
			TagAppender<Item> builder = tag(tag);
			for (MineMetals metal : MineMetals.values())
			{
				if (predicate.test(metal))
					tagGetter.apply(metal).ifPresent(builder::addTag);
			}
			for (Tag.Named<Item> extraTag : extras)
			{
				builder.addTag(extraTag);
			}
		}
	}

	@SafeVarargs
	private void groupGemBuilder(Tag.Named<Item> tag, Function<MineGems, Optional<Tag.Named<Item>>> tagGetter, ResourceItemType type, Tag.Named<Item>... extras)
	{
		groupGemBuilder(tag, tagGetter, gem -> gem.getItemFromType(type, true).isPresent(), extras);
	}

	@SafeVarargs
	private void groupGemBuilder(Tag.Named<Item> tag, Function<MineGems, Optional<Tag.Named<Item>>> tagGetter, Predicate<MineGems> predicate, Tag.Named<Item>... extras)
	{
		if (Arrays.stream(MineGems.values()).anyMatch(predicate))
		{
			TagAppender<Item> builder = tag(tag);
			for (MineGems gem : MineGems.values())
			{
				if (predicate.test(gem))
					tagGetter.apply(gem).ifPresent(builder::addTag);
			}
			for (Tag.Named<Item> extraTag : extras)
			{
				builder.addTag(extraTag);
			}
		}
	}
}
