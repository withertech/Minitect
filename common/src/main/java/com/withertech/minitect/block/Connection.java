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

	Connection(Type type, Mode mode)
	{
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
