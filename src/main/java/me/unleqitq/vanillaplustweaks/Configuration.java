package me.unleqitq.vanillaplustweaks;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class Configuration {
	
	private static File folder;
	private static File configFile;
	private static YamlConfiguration config;
	
	private Configuration() {}
	
	public static void loadConfig() {
		folder = VanillaPlusTweaks.getInstance().getDataFolder();
		folder.mkdirs();
		configFile = new File(folder, "config.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				Bukkit.getLogger().log(Level.WARNING, "Could not create config", e);
			}
		}
		config = new YamlConfiguration();
		if (configFile.exists()) {
			try {
				config.load(configFile);
			} catch (IOException e) {
				Bukkit.getLogger().log(Level.WARNING, "Could not access config", e);
			} catch (InvalidConfigurationException e) {
				Bukkit.getLogger().log(Level.WARNING, "Config is invalid", e);
			}
		}
		else {
			Bukkit.getLogger().log(Level.WARNING, "Config not found");
		}
		config.addDefault("flintAndSteel.enable", true);
		config.addDefault("flintAndSteel.hitEntityIgnite.enable", true);
		config.addDefault("flintAndSteel.hitEntityIgnite.ticks", 40);
		config.addDefault("flintAndSteel.smeltBlocks.enable", true);
		config.addDefault("flintAndSteel.smeltBlocks.useFortune", true);
		if (configFile.exists()) {
			try {
				config.save(configFile);
			} catch (IOException e) {
				Bukkit.getLogger().log(Level.WARNING, "Could not save config", e);
			}
		}
	}
	
	public static void setConfig(YamlConfiguration config) {
		Configuration.config = config;
	}
	
	public static YamlConfiguration getConfig() {
		return config;
	}
	
	public static final class FlintAndSteel {
		
		public static boolean enable() {
			return Configuration.config.getBoolean("flintAndSteel.enable");
		}
		
		public static final class HitEntityIgnite {
			
			public static boolean enable() {
				return Configuration.config.getBoolean("flintAndSteel.hitEntityIgnite.enable");
			}
			
			public static int ticks() {
				return Configuration.config.getInt("flintAndSteel.hitEntityIgnite.ticks");
			}
			
		}
		
		public static final class SmeltBlocks {
			
			public static boolean enable() {
				return Configuration.config.getBoolean("flintAndSteel.smeltBlocks.enable");
			}
			
			public static boolean useFortune() {
				return Configuration.config.getBoolean("flintAndSteel.smeltBlocks.useFortune");
			}
			
		}
		
	}
	
}
