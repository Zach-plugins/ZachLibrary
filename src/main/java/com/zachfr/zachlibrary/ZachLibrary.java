package com.zachfr.zachlibrary;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ZachLibrary extends JavaPlugin {
	protected boolean preEnable(Plugin plugin) {
		System.out.println("Hello world on preEnable");
		return true;
	}

	public abstract void onDataLoad();
}
