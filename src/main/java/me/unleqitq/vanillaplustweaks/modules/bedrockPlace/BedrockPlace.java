package me.unleqitq.vanillaplustweaks.modules.bedrockPlace;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.MinecraftKey;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.unleqitq.vanillaplustweaks.Module;
import me.unleqitq.vanillaplustweaks.VanillaPlusTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

public class BedrockPlace extends Module implements Listener {
	
	BedrockPlaceTask listener = new BedrockPlaceTask();
	
	public BedrockPlace() {
		super("BedrockPlace");
	}
	
	
	@Override
	public void loadDefaultConfigs() {
		addDefault("enable", true);
	}
	
	@Override
	public void register() {
		Bukkit.getPluginManager().registerEvents(listener, VanillaPlusTweaks.getInstance());
	}
	
	@Override
	public void unregister() {
		HandlerList.unregisterAll(listener);
	}
	
	@Override
	public void reload() {
	
	}
	
	public static void sendBlockHighlight(Player pl, Location loc, int color) {
		ByteBuf packet = Unpooled.buffer();
		packet.writeLong(blockPosToLong(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
		packet.writeInt(color);
		writeString(packet, "");
		packet.writeInt(200);
		sendPayload(pl, "debug/game_test_add_marker", packet);
	}
	
	private static void sendPayload(Player receiver, String channel, ByteBuf bytes) {
		PacketContainer handle = new PacketContainer(PacketType.Play.Server.CUSTOM_PAYLOAD);
		handle.getMinecraftKeys().write(0, new MinecraftKey(channel));
		
		Object serializer = MinecraftReflection.getPacketDataSerializer(bytes);
		handle.getModifier().withType(ByteBuf.class).write(0, serializer);
		
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, handle);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Unable to send the packet", e);
		}
	}
	
	private static long blockPosToLong(int x, int y, int z) {
		return ((long) x & 67108863L) << 38 | (long) y & 4095L | ((long) z & 67108863L) << 12;
	}
	
	private static void d(ByteBuf packet, int i) {
		while ((i & -128) != 0) {
			packet.writeByte(i & 127 | 128);
			i >>>= 7;
		}
		
		packet.writeByte(i);
	}
	
	private static void writeString(ByteBuf packet, String s) {
		byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
		d(packet, abyte.length);
		packet.writeBytes(abyte);
	}
	
	@EventHandler
	public void blockPlaceEvent(PlayerInteractEvent e) {
		/*if (e.getHand() != EquipmentSlot.HAND)
			return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR)
			return;
		if (e.getItem() == null)
			return;
		Player player = e.getPlayer();
		if (!player.getPersistentDataContainer().has(key, PersistentDataType.INTEGER))
			return;
		ItemStack item = e.getItem();
		if (!item.getType().isBlock())
			return;
		Location loc = getPlayerReachAroundTarget(player);
		if (loc != null) {
			// BlockPlaceEvent event = new BlockPlaceEvent(loc.getBlock(), loc.getBlock().getState(), placedAgainst, itemInHand, thePlayer, canBuild, hand)
			VanillaPlusTweaks.getInstance().addPlacedPlayer(player);
			boolean placed = getPlugin().getNmsHelper().placeItem(player, loc, e.getItem(), BlockFace.UP, getName(),
					true);
			if (placed && player.getGameMode() != GameMode.CREATIVE && !plugin.is1_18()) {
				item.setAmount(item.getAmount() - 1);
			}
		}*/
	}
	
	// NOT IN USE
	// public boolean isValidMaterial(Material type){
	//     String blockName = type.toString();
	//     for (String string : getConfig().getStringList("black-list-materials")) {
	//         if(string.startsWith("^")){
	//             if(blockName.endsWith(string.replace("^", ""))){
	//                 return true;
	//             }
	//         }
	//         if(blockName.equals(string)){
	//             return true;
	//         }
	//     }
	//     return false;
	// }
	
	
	public Location getPlayerReachAroundTarget(Player player) {
		
		RayTraceResult rayTraceResult = player.getWorld().rayTraceBlocks(player.getEyeLocation(),
				player.getEyeLocation().getDirection(), 5);
		if (rayTraceResult == null) {
			Location target = getPlayerVerticalReachAround(player);
			if (target != null) {
				return target;
			}
			target = getPlayerHorizonTalReachAround(player);
			if (target != null) {
				return target;
			}
		}
		
		return null;
	}
	
	public Location getPlayerVerticalReachAround(Player player) {
		Vector vec = new Vector(0, 0.5, 0);
		RayTraceResult rayTrace = player.getWorld().rayTraceBlocks(player.getEyeLocation(),
				player.getEyeLocation().getDirection().clone().add(vec), 5);
		if (rayTrace != null) {
			if (rayTrace.getHitBlock() != null) {
				Location block = rayTrace.getHitBlock().getLocation();
				Location playerLoc = player.getLocation();
				if (playerLoc.getZ() - block.getZ() < 1.3 && playerLoc.getY() - block.getY() == 1 && playerLoc.getX() - block.getX() < 1.3) {
					Location target = block.subtract(0, 1, 0);
					if (target.getBlock().getType() == Material.AIR) {
						return target;
					}
				}
			}
		}
		return null;
	}
	
	
	public Location getPlayerHorizonTalReachAround(Player player) {
		Location playerLoc = player.getLocation();
		BlockFace facing = player.getFacing();
		Vector direction = player.getEyeLocation().getDirection();
		Vector vec = new Vector(0.5 * facing.getModX(), 0, 0.5 * facing.getModZ());
		RayTraceResult rayTrace = player.getWorld().rayTraceBlocks(player.getEyeLocation(),
				direction.clone().subtract(vec), 4);
		if (rayTrace != null) {
			if (rayTrace.getHitBlock() != null) {
				Location loc = rayTrace.getHitBlock().getLocation();
				double distance = (playerLoc.getX() - loc.getX()) + (playerLoc.getY() - loc.getY()) + (playerLoc.getZ() - loc.getZ()) / 3;
				if (distance < 1.85 && distance > 1.3) {
					Block target = loc.getBlock().getRelative(player.getFacing());
					if (target.getType() == Material.AIR) {
						return target.getLocation();
					}
				}
				
			}
		}
		
		return null;
	}
	
	
}
