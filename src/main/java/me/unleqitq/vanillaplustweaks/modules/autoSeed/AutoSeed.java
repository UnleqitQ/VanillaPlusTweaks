package me.unleqitq.vanillaplustweaks.modules.autoSeed;

import me.unleqitq.vanillaplustweaks.Module;
import me.unleqitq.vanillaplustweaks.VanillaPlusTweaks;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class AutoSeed extends Module {
	
	AutoSeedListener listener = new AutoSeedListener();
	
	public AutoSeed() {
		super("AutoSeed");
	}
	
	
	@Override
	public void loadDefaultConfigs() {
		addDefault("enable", true);
	}
	
	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(listener, VanillaPlusTweaks.getInstance());
	}
	
	@Override
	public void unregister() {
		HandlerList.unregisterAll(listener);
	}
	
	@Override
	public void reload() {
	
	}
	
}
