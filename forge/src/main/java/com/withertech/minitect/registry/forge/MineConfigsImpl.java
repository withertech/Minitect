package com.withertech.minitect.registry.forge;

import com.withertech.minitect.config.MineConfig;
import com.withertech.minitect.config.OreConfigs;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

public class MineConfigsImpl
{
	public static void registerConfigs()
	{
		AutoConfig.register(MineConfig.class, PartitioningSerializer.wrap(Toml4jConfigSerializer::new));
	}
}
