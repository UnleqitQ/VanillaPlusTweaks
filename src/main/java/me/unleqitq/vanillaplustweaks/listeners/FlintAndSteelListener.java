package me.unleqitq.vanillaplustweaks.listeners;

import me.unleqitq.vanillaplustweaks.VanillaPlusTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Random;
import java.util.Set;

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
				if (item.getItemMeta() instanceof Damageable) {
					int level = item.getEnchantmentLevel(Enchantment.DURABILITY);
					if (VanillaPlusTweaks.getInstance().getRandom().nextInt(level + 1) == 0) {
						Damageable meta = (Damageable) item.getItemMeta();
						meta.setDamage(meta.getDamage() + 1);
						item.setItemMeta(meta);
					}
					event.getEntity().setFireTicks(event.getEntity().getFireTicks() + 40);
				}
			}
		}
		else if (event.getDamager() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) event.getDamager();
			if (damager.getEquipment() != null) {
				ItemStack item = damager.getEquipment().getItemInMainHand();
				if (item.getType() == Material.FLINT_AND_STEEL) {
					if (item.getItemMeta() instanceof Damageable) {
						int level = item.getEnchantmentLevel(Enchantment.DURABILITY);
						if (VanillaPlusTweaks.getInstance().getRandom().nextInt(level + 1) == 0) {
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
	
}
