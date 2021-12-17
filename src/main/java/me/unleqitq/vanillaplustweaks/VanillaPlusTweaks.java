package me.unleqitq.vanillaplustweaks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class VanillaPlusTweaks extends JavaPlugin {
	
	private static VanillaPlusTweaks instance;
	
	
	@Override
	public void onEnable() {
		instance = this;
		new VanillaPlusTweaks();
	}
	
	@Override
	public void onDisable() {
	}
	
	public static VanillaPlusTweaks getInstance() {
		return instance;
	}
	
}
