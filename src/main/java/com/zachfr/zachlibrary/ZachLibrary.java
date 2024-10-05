package com.zachfr.zachlibrary;

import com.zachfr.zachlibrary.language.Language;
import com.zachfr.zachlibrary.utils.LogUtils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ZachLibrary extends JavaPlugin {

	private Language language;

	private static String version = "${version}";

	private BukkitAudiences adventure;

	public BukkitAudiences adventure() {
		if(this.adventure == null) {
			throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
		}
		return this.adventure;
	}

	protected boolean preEnable(Plugin plugin) {
		LogUtils.debug("ZachLibrary v" + version + " is being enabled!");
		this.adventure = BukkitAudiences.create(this);
		return true;
	}

	protected boolean preDisable(Plugin plugin) {
		LogUtils.debug("ZachLibrary v" + version + " is being disabled!");
		if(this.adventure != null) {
			this.adventure.close();
			this.adventure = null;
		}
		return true;
	}

	public abstract void onDataLoad();

	public Language getLanguage() {
		return language;
	}

	public void loadLanguage(){
		language = new Language(this);
	}

	public BukkitAudiences getAdventure() {
		return adventure;
	}

	public static ZachLibrary getInstance() {
		return getPlugin(ZachLibrary.class);
	}
}
