package com.withertech.minitect.tile;

import com.withertech.mine_flux.api.IMFStorage;
import com.withertech.mine_flux.util.EnergyUtil;
import com.withertech.minitect.block.Connection;
import com.withertech.minitect.container.MTCrusherContainer;
import com.withertech.minitect.recipe.CrushingRecipe;
import com.withertech.minitect.registry.MineRecipes;
import com.withertech.minitect.registry.MineTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class MTCrusherTile extends AbstractMachineTile<CrushingRecipe, Recipe<Container>>
{
	public static final int ENERGY_SIZE = 1_000_000;
	public static final int ENERGY_TRANSFER = 500;
	public static final int INV_SIZE = 4;
	public static final int DATA_SIZE = 4;

	protected final IMFStorage energy = EnergyUtil.create(ENERGY_SIZE, ENERGY_TRANSFER);
	protected final CrusherInventory inventory = new CrusherInventory();

	protected int maxProgress = 100;
	protected int progress = 0;

	public MTCrusherTile(BlockPos blockPos, BlockState blockState)
	{
		super(MineTiles.CRUSHER.get(), blockPos, blockState);
	}

	@Override
	protected Optional<IMFStorage> getStorageFor(@Nullable Direction side)
	{
		if (side == null) return Optional.of(energy);
		final Connection connection = getConnectionFromDirection(side);
		if (connection.TYPE.equals(Connection.Type.ENERGY) && connection.MODE != Connection.Mode.NONE)
		{
			return Optional.of(energy);
		}
		return Optional.empty();
	}

	@Override
	public ContainerData getPropertyDelegate()
	{
		return new ContainerData() {
			@Override
			public int get(int index)
			{
				return switch (index)
						{
							case 0 -> energy.getEnergyStored();
							case 1 -> energy.getMaxEnergyStored();
							case 2 -> progress;
							case 3 -> maxProgress;
							default -> throw new IllegalStateException("Unexpected value: " + index);
						};
			}

			@Override
			public void set(int index, int value)
			{

			}

			@Override
			public int getCount()
			{
				return DATA_SIZE;
			}
		};
	}

	@Override
	public Component getDisplayName()
	{
		return new TextComponent("Electric Crusher");
	}

	@Override
	public WorldlyContainer getContainer(BlockState state, LevelAccessor level, BlockPos pos)
	{
		return inventory;
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
	{
		return new MTCrusherContainer(i, inventory, ContainerLevelAccess.create(level, worldPosition), getDisplayName());
	}

	@Override
	public void load(CompoundTag compoundTag)
	{
		if (!compoundTag.contains("Energy")) compoundTag.putInt("Energy", 0);
		if (!compoundTag.contains("Progress")) compoundTag.putInt("Progress", 0);
		if (!compoundTag.contains("MaxProgress")) compoundTag.putInt("MaxProgress", 100);
		if (!compoundTag.contains("Inventory")) compoundTag.put("Inventory", inventory.createTag());
		progress = compoundTag.getInt("Progress");
		maxProgress = compoundTag.getInt("MaxProgress");
		energy.deserializeNBT(compoundTag.get("Energy"));
		inventory.fromTag(compoundTag.getList("Inventory", Tag.TAG_COMPOUND));
		super.load(compoundTag);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag)
	{
		compoundTag.putInt("Progress", progress);
		compoundTag.putInt("MaxProgress", maxProgress);
		compoundTag.put("Energy", energy.serializeNBT());
		compoundTag.put("Inventory", inventory.createTag());
		super.saveAdditional(compoundTag);
	}

	@Override
	protected void tick(Level level, BlockPos blockPos, BlockState state, AbstractMachineInventory container)
	{
		updateCrafting(level, blockPos, state, container);
	}

	@Override
	protected RecipeType<CrushingRecipe> getRecipeType()
	{
		return MineRecipes.Types.CRUSHING;
	}

	@Override
	protected void setMaxProgress(int newTime)
	{
		this.maxProgress = newTime;
	}

	@Override
	protected int getMaxProgress()
	{
		return this.maxProgress;
	}

	@Override
	protected void setProgress(int newTime)
	{
		this.progress = newTime;
	}

	@Override
	protected int getProgress()
	{
		return this.progress;
	}

	public class CrusherInventory extends AbstractMachineInventory
	{
		public CrusherInventory()
		{
			super(INV_SIZE, new int[] {0}, new int[] {1, 2, 3});
		}

		@Override
		public void setItem(int index, ItemStack stack)
		{
			if (getLevel() != null)
				updateMaxProgress(this);
			MTCrusherTile.this.setChanged();
			super.setItem(index, stack);
		}

		@Override
		protected BlockState getBlockState()
		{
			return MTCrusherTile.this.getBlockState();
		}
	}
}
