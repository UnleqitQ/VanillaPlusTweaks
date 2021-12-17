package me.unleqitq.vanillaplustweaks;

import me.unleqitq.vanillaplustweaks.listeners.FlintAndSteelListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class VanillaPlusTweaks extends JavaPlugin {
	
	private static VanillaPlusTweaks instance;
	private final Random random;
	
	public VanillaPlusTweaks() {
		instance = this;
		random = new Random();
	}
	
	@Override
	public void onEnable() {
		new FlintAndSteelListener();
		Configuration.loadConfig();
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}
	
	public static VanillaPlusTweaks getInstance() {
		return instance;
	}
	
	public Random getRandom() {
		return random;
	}
	
}
