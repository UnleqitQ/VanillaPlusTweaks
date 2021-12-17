package me.unleqitq.vanillaplustweaks;

import org.bukkit.plugin.java.JavaPlugin;

public final class VanillaPlusTweaks extends JavaPlugin {
	
	private static VanillaPlusTweaks instance;
	
	public VanillaPlusTweaks() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
	
	}
	
	@Override
	public void onDisable() {
	
	}
	
	public static VanillaPlusTweaks getInstance() {
		return instance;
	}
	
}
