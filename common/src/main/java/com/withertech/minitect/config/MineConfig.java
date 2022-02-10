package com.withertech.minitect.config;

import com.withertech.minitect.Minitect;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = Minitect.MOD_ID)
public class MineConfig extends PartitioningSerializer.GlobalData
{
	@ConfigEntry.Category("Main")
	public MainConfig main = new MainConfig();

	@ConfigEntry.Category("Ores")
	public OreConfigs ores = new OreConfigs();
}
