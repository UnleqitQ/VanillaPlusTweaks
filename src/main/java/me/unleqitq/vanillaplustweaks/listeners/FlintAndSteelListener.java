package me.unleqitq.vanillaplustweaks.listeners;

import me.unleqitq.vanillaplustweaks.VanillaPlusTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;

import java.util.*;

public class FlintAndSteelListener implements Listener {
	
	Map<ItemStack, FurnaceRecipe> recipes;
	
	public FlintAndSteelListener() {
		Bukkit.getPluginManager().registerEvents(this, VanillaPlusTweaks.getInstance());
		recipes = new HashMap<>();
		Recipe recipe;
		for (Iterator<Recipe> iterator = Bukkit.recipeIterator(); iterator.hasNext(); ) {
			recipe = iterator.next();
			if (recipe instanceof FurnaceRecipe) {
				recipes.put(((FurnaceRecipe) recipe).getInput(), (FurnaceRecipe) recipe);
			}
		}
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
	
	@EventHandler
	public void onBreak(PlayerHarvestBlockEvent event) {
		ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
		//Block block = event.getHarvestedBlock();
		List<ItemStack> prevItems = event.getItemsHarvested();
		List<ItemStack> items = new ArrayList<>();
		if (offHand.getType() == Material.FLINT_AND_STEEL) {
			boolean used = false;
			for (ItemStack item : prevItems) {
				if (recipes.containsKey(item)) {
					Recipe recipe = recipes.get(item);
					used = true;
					items.add(recipe.getResult());
				}
				else {
					items.add(item);
				}
			}
			if (used) {
				if (offHand.getType() == Material.FLINT_AND_STEEL) {
					if (offHand.getItemMeta() instanceof Damageable) {
						int level = offHand.getEnchantmentLevel(Enchantment.DURABILITY);
						if (VanillaPlusTweaks.getInstance().getRandom().nextInt(level + 1) == 0) {
							Damageable meta = (Damageable) offHand.getItemMeta();
							meta.setDamage(meta.getDamage() + 1);
							offHand.setItemMeta(meta);
						}
						event.getItemsHarvested().clear();
						event.getItemsHarvested().addAll(items);
					}
				}
			}
		}
	}
	
}
