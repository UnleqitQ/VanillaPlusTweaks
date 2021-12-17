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
		event.getPlayer().sendMessage("Action:        " + event.getAction(), "EventName:     " + event.getEventName(),
				"BlockFace:     " + event.getBlockFace(), "Clicked Block: " + event.getClickedBlock(),
				"Hand:          " + event.getHand(), "Item:          " + event.getItem(),
				"Material:      " + event.getMaterial(), "", "");
	}
	
}
