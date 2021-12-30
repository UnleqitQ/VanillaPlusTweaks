package me.unleqitq.vanillaplustweaks.listeners;

import me.unleqitq.vanillaplustweaks.Configuration;
import me.unleqitq.vanillaplustweaks.VanillaPlusTweaks;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.jetbrains.annotations.NotNull;

public class AutoSeedListener implements Listener {
	
	public AutoSeedListener() {
		Bukkit.getPluginManager().registerEvents(this, VanillaPlusTweaks.getInstance());
	}
	
	@EventHandler
	public void onDrop(@NotNull BlockDropItemEvent event) {
		if (Configuration.AutoSeed.enable()) {
			if (event.getPlayer().getGameMode() == GameMode.SURVIVAL && event.getBlockState().getType().data == Ageable.class) {
				if (Configuration.AutoSeed.types().contains(event.getBlockState().getType().name())) {
					if (VanillaPlusTweaks.getInstance().getRandom().nextFloat() < Configuration.AutoSeed.probability()) {
						if (event.getBlock().getType() == Material.AIR || event.getBlock().getType() == Material.WATER) {
							if (event.getBlock().getLocation().add(0, -1,
									0).getBlock().getType() == Material.FARMLAND) {
								event.getBlock().setType(event.getBlockState().getType());
							}
						}
					}
				}
			}
		}
	}
	
}
