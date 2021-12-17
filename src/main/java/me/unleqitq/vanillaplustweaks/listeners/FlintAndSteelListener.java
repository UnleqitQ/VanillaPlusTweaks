package me.unleqitq.vanillaplustweaks.listeners;

import me.unleqitq.vanillaplustweaks.VanillaPlusTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;

import java.util.*;

public class FlintAndSteelListener implements Listener {
	
	Map<Material, FurnaceRecipe> recipes;
	
	public FlintAndSteelListener() {
		Bukkit.getPluginManager().registerEvents(this, VanillaPlusTweaks.getInstance());
		recipes = new HashMap<>();
		Recipe recipe;
		for (Iterator<Recipe> iterator = Bukkit.recipeIterator(); iterator.hasNext(); ) {
			recipe = iterator.next();
			if (recipe instanceof FurnaceRecipe) {
				recipes.put(((FurnaceRecipe) recipe).getInput().getType(), (FurnaceRecipe) recipe);
				FurnaceRecipe fr = ((FurnaceRecipe) recipe);
				if (fr.getInput().getType() == Material.SAND) {
					Bukkit.broadcast(String.format("%s %s %s %s", fr.getInput(), fr.getCookingTime(), fr.getResult(),
							fr.getInputChoice()), "");
				}
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
	public void onBreak(BlockDropItemEvent event) {
		Random rnd = VanillaPlusTweaks.getInstance().getRandom();
		ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
		ItemStack offHand = event.getPlayer().getInventory().getItemInOffHand();
		Block block = event.getBlock();
		List<Item> prevItems = event.getItems();
		if (offHand.getType() == Material.FLINT_AND_STEEL) {
			boolean used = false;
			for (Item itemEntity : prevItems) {
				ItemStack item = itemEntity.getItemStack();
				event.getPlayer().sendMessage(item.toString());
				if (recipes.containsKey(item.getType())) {
					FurnaceRecipe recipe = recipes.get(item.getType());
					event.getPlayer().sendMessage("YYYY" + recipe);
					used = true;
					itemEntity.setItemStack(recipe.getResult());
					int amount = item.getAmount();
					if (item.getType() == block.getType()) {
						if (mainHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) > 0) {
							int amount0 = amount;
							int level = mainHand.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
							amount = 0;
							for (int i = 0; i < amount0; i++) {
								if (rnd.nextFloat() < 2 / (2 + (float) level)) {
									amount += rnd.nextInt(level) + 2;
								}
								else
									amount++;
							}
						}
					}
					itemEntity.getItemStack().setAmount(
							amount / recipe.getInput().getAmount() * recipe.getResult().getAmount());
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
					}
				}
			}
		}
	}
	
}
