package me.unleqitq.vanillaplustweaks.modules.flintAndSteel;

import me.unleqitq.vanillaplustweaks.Module;
import me.unleqitq.vanillaplustweaks.VanillaPlusTweaks;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class FlintAndSteel extends Module {
	
	FlintAndSteelListener listener = new FlintAndSteelListener();
	
	public FlintAndSteel() {
		super("FlintAndSteel");
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
