package com.withertech.minitect.fabric;

import com.withertech.minitect.Minitect;
import net.fabricmc.api.ModInitializer;

public class MinitectFabric implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		Minitect.init();
	}
}