package com.withertech.minitect.registry.fabric;

import com.withertech.minitect.config.MineConfig;
import com.withertech.minitect.config.OreConfigs;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class MineConfigsImpl
{
	public static void registerConfigs()
	{
		AutoConfig.register(MineConfig.class, PartitioningSerializer.wrap(GsonConfigSerializer::new));
	}
}
