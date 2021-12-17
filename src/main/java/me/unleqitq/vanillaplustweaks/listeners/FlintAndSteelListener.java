package me.unleqitq.vanillaplustweaks.listeners;

import me.unleqitq.vanillaplustweaks.VanillaPlusTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class FlintAndSteelListener implements Listener {
	
	public FlintAndSteelListener() {
		Bukkit.getPluginManager().registerEvents(this, VanillaPlusTweaks.getInstance());
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item.getType() == Material.FLINT_AND_STEEL) {
				if (item.hasItemMeta() && item.getItemMeta() instanceof Damageable) {
					Damageable meta = (Damageable) item.getItemMeta();
					meta.setDamage(meta.getDamage() + 1);
					item.setItemMeta(meta);
				}
				event.getEntity().setFireTicks(event.getEntity().getFireTicks() + 40);
			}
		}
		if (event.getDamager() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) event.getDamager();
			if (damager.getEquipment() != null) {
				ItemStack item = damager.getEquipment().getItemInMainHand();
				if (item.getType() == Material.FLINT_AND_STEEL) {
					if (item.hasItemMeta() && item.getItemMeta() instanceof Damageable) {
						Damageable meta = (Damageable) item.getItemMeta();
						meta.setDamage(meta.getDamage() + 1);
						item.setItemMeta(meta);
					}
					event.getEntity().setFireTicks(event.getEntity().getFireTicks() + 40);
				}
			}
		}
	}
	
}
