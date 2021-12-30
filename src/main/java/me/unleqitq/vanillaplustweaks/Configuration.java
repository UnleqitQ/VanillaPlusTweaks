package me.unleqitq.vanillaplustweaks;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public final class Configuration {
	
	private static File folder;
	private static File configFile;
	private static YamlConfiguration config;
	
	private static Map<String, Object> defaults = new HashMap<>();
	
	static {
		createDefaults();
	}
	
	private static void createDefaults() {
		{
			defaults.put("flintAndSteel.enable", true);
			defaults.put("flintAndSteel.hitEntityIgnite.enable", true);
			defaults.put("flintAndSteel.hitEntityIgnite.ticks", 40);
			defaults.put("flintAndSteel.smeltBlocks.enable", true);
			defaults.put("flintAndSteel.smeltBlocks.useFortune", true);
		}
		{
			defaults.put("autoSeed.enable", true);
			defaults.put("autoSeed.probability", 0.5);
			{
				List<String> l = new ArrayList<>();
				l.add(Material.WHEAT.name());
				l.add(Material.CARROTS.name());
				l.add(Material.POTATOES.name());
				l.add(Material.BEETROOTS.name());
				defaults.put("autoSeed.types", l);
			}
		}
	}
	
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
			config = YamlConfiguration.loadConfiguration(configFile);
		}
		else {
			Bukkit.getLogger().log(Level.WARNING, "Config not found");
		}
		addDefaults();
		if (configFile.exists()) {
			try {
				config.save(configFile);
			} catch (IOException e) {
				Bukkit.getLogger().log(Level.WARNING, "Could not save config", e);
			}
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	private static void addDefaults() {
		for (Map.Entry<String, Object> entry : defaults.entrySet()) {
			if (!config.contains(entry.getKey(), true)) {
				config.set(entry.getKey(), entry.getValue());
				continue;
			}
			if (entry.getValue() instanceof String) {
				if (!config.isString(entry.getKey())) {
					config.set(entry.getKey(), entry.getValue());
				}
				continue;
			}
			if (entry.getValue() instanceof Integer) {
				if (!config.isInt(entry.getKey())) {
					config.set(entry.getKey(), entry.getValue());
				}
				continue;
			}
			if (entry.getValue() instanceof Boolean) {
				if (!config.isBoolean(entry.getKey())) {
					config.set(entry.getKey(), entry.getValue());
				}
				continue;
			}
			if (entry.getValue() instanceof Double) {
				if (!config.isDouble(entry.getKey())) {
					config.set(entry.getKey(), entry.getValue());
				}
				continue;
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
	
	public static final class AutoSeed {
		
		public static boolean enable() {
			return Configuration.config.getBoolean("autoSeed.enable");
		}
		
		public static double probability() {
			return Configuration.config.getDouble("autoSeed.probability");
		}
		
		@NotNull
		public static List<String> types() {
			return Lists.transform(Configuration.config.getStringList("autoSeed.types"), (in) -> in.toUpperCase());
		}
		
	}
	
}
