package me.unleqitq.vanillaplustweaks;

import me.unleqitq.vanillaplustweaks.modules.autoSeed.AutoSeed;
import me.unleqitq.vanillaplustweaks.modules.flintAndSteel.FlintAndSteel;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class VanillaPlusTweaks extends JavaPlugin {
	
	private static VanillaPlusTweaks instance;
	private final Random random;
	public Set<Module> modules = new HashSet<>();
	
	public VanillaPlusTweaks() {
		instance = this;
		random = new Random();
		modules.add(new AutoSeed());
		modules.add(new FlintAndSteel());
		//modules.add(new BedrockPlace());
		
	}
	
	@Override
	public void onEnable() {
		Configuration.loadConfig();
		for (Module module : modules) {
			module.register();
		}
	}
	
	@Override
	public void onDisable() {
		for (Module module : modules) {
			module.unregister();
		}
	}
	
	public static VanillaPlusTweaks getInstance() {
		return instance;
	}
	
	public Random getRandom() {
		return random;
	}
	
}
