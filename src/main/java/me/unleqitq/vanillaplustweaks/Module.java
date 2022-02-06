package me.unleqitq.vanillaplustweaks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class Module {
	
	private Map<String, Object> configs = new HashMap<>();
	
	@NotNull
	private final String name;
	
	public Module(@NotNull String name) {
		this.name = name;
		loadDefaultConfigs();
	}
	
	@NotNull
	public String getName() {
		return name;
	}
	
	@NotNull
	public void addDefault(String path, Object value) {
		configs.put(path, value);
	}
	
	@NotNull
	public Collection<String> getConfigPaths() {
		return configs.keySet();
	}
	
	@Nullable
	public Object getDefault(String configPath) {
		return configs.get(configPath);
	}
	
	public abstract void loadDefaultConfigs();
	
	public abstract void register();
	
	public abstract void unregister();
	
	public abstract void reload();
	
	public void stopTasks() {}
	
}
