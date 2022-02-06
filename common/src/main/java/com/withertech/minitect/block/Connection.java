package com.withertech.minitect.block;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum Connection implements StringRepresentable
{
	ITEM_IN(Type.ITEM, Mode.INPUT),
	ITEM_OUT(Type.ITEM, Mode.OUTPUT),
	ENERGY_IN(Type.ENERGY, Mode.INPUT),
	ENERGY_OUT(Type.ENERGY, Mode.OUTPUT),
	FLUID_IN(Type.FLUID, Mode.INPUT),
	FLUID_OUT(Type.FLUID, Mode.OUTPUT),
	NONE(Type.NONE, Mode.NONE);

	public final Type TYPE;
	public final Mode MODE;

	Connection(Type type, Mode mode) {
		TYPE = type;
		MODE = mode;
	}

	@Override
	public String getSerializedName()
	{
		return TYPE.getSerializedName() + "_" + MODE.getSerializedName();
	}

	public enum Type implements StringRepresentable
	{
		ITEM,
		ENERGY,
		FLUID,
		NONE;

		@Override
		public String getSerializedName()
		{
			return this.name().toLowerCase(Locale.ROOT);
		}
	}

	public enum Mode implements StringRepresentable
	{
		INPUT,
		OUTPUT,
		NONE;

		@Override
		public String getSerializedName()
		{
			return this.name().toLowerCase(Locale.ROOT);
		}
	}
}
