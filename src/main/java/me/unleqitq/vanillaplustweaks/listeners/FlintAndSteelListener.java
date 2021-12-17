package me.unleqitq.vanillaplustweaks.listeners;

import me.unleqitq.vanillaplustweaks.VanillaPlusTweaks;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class FlintAndSteelListener implements Listener {
	
	public FlintAndSteelListener() {
		Bukkit.getPluginManager().registerEvents(this, VanillaPlusTweaks.getInstance());
	}
	
	@EventHandler
	public void onHit(PlayerInteractEvent event) {
		event.getPlayer().sendMessage(event.getAction() + "", event.getEventName(), event.getBlockFace() + "",
				event.getClickedBlock() + "", event.getHand() + "", event.getItem() + "", event.getMaterial() + "");
	}
	
}
