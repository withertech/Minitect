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

package com.withertech.minitect.config;

import com.withertech.minitect.Minitect;
import com.withertech.minitect.registry.MineOres;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.Util;

import java.util.HashMap;
import java.util.Map;

@Config(name = Minitect.MOD_ID + "_ores")
public class OreConfigs implements ConfigData
{
	public Map<MineOres, OreConfig> ores = Util.make(new HashMap<>(), oresMap ->
	{
		for (MineOres ore : MineOres.values())
		{
			final MineOres.DefaultOreConfigs defaultOreConfig = ore.getDefaultOreConfigs();
			oresMap.put(ore, new OreConfig(
					defaultOreConfig.enabled(),
					defaultOreConfig.veinCount(),
					defaultOreConfig.veinSize(),
					defaultOreConfig.minHeight(),
					defaultOreConfig.maxHeight()));
		}
	});
	private boolean master = true;

	public boolean isMaster()
	{
		return master;
	}

	public void setMaster(boolean master)
	{
		this.master = master;
	}

	public static class OreConfig implements ConfigData
	{
		private boolean enabled;
		private int veinCount;
		private int veinSize;
		private int minHeight;
		private int maxHeight;

		public OreConfig(boolean enabled, int veinCount, int veinSize, int minHeight, int maxHeight)
		{
			this.enabled = enabled;
			this.veinCount = veinCount;
			this.veinSize = veinSize;
			this.minHeight = minHeight;
			this.maxHeight = maxHeight;
		}

		public boolean isEnabled()
		{
			return enabled;
		}

		public void setEnabled(boolean enabled)
		{
			this.enabled = enabled;
		}

		public int getVeinCount()
		{
			return veinCount;
		}

		public void setVeinCount(int veinCount)
		{
			this.veinCount = veinCount;
		}

		public int getVeinSize()
		{
			return veinSize;
		}

		public void setVeinSize(int veinSize)
		{
			this.veinSize = veinSize;
		}

		public int getMinHeight()
		{
			return minHeight;
		}

		public void setMinHeight(int minHeight)
		{
			this.minHeight = minHeight;
		}

		public int getMaxHeight()
		{
			return maxHeight;
		}

		public void setMaxHeight(int maxHeight)
		{
			this.maxHeight = maxHeight;
		}
	}
}
