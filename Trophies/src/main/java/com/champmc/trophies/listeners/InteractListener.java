package com.champmc.trophies.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.champmc.trophies.Trophies;
import com.champmc.trophies.events.TrophyPlaceEvent;
import com.champmc.trophies.files.Messages;
import com.champmc.trophies.managers.TrophyManager;
import com.champmc.trophies.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InteractListener implements Listener {

    Trophies plugin;
    Messages messages = new Messages();
    TrophyManager trophyManager = new TrophyManager();

    private static HashMap<ArmorStand, Integer> tier = new HashMap<>();

    public InteractListener(Trophies plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        loadTrophiesIntoTier();
    }

    @EventHandler
    public void onPlace(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        // Island Cap Check
        Island island = SuperiorSkyblockAPI.getIslandAt(e.getPlayer().getLocation());
        for (int i = 0; i <= plugin.getConfig().getInt("Amount-Of-Tiers"); i++) {
            if (getTotalTierOnIsland(island, i + 1) > plugin.getConfig().getInt("Tiers.Tier" + (i + 1) + ".cap") && p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + (i + 1) + ".name")))
                    && p.getInventory().getItemInHand().getType().equals(Material.ARMOR_STAND)) {
                e.setCancelled(true);
                p.sendMessage(Utils.chat(plugin.getConfig().getString("Cap-Reached")));
                break;
            } else {
                if (p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + (i + 1) + ".name"))) && p.getInventory().getItemInHand().getType().equals(Material.ARMOR_STAND)
                && island.getAllMembers().contains(p.getUniqueId())) {
                    Bukkit.getPluginManager().callEvent(new TrophyPlaceEvent(p, e.getClickedBlock().getLocation(), island, i + 1));
                    break;
                }
            }
        }
    }

    public int getTotalTierOnIsland (Island island, int t) {
        int result = 0;
        for (Chunk chunk : island.getAllChunks()) {
            for (Entity entity : chunk.getEntities()) {
                if (entity instanceof ArmorStand) {
                    ArmorStand armorStand = (ArmorStand) entity;
                    if (trophyManager.hasTrophy(armorStand) && tier.containsValue(t)) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    public void loadTrophiesIntoTier() {
        for (World world : Bukkit.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (Entity entity : chunk.getEntities()) {
                    if (entity instanceof ArmorStand) {
                        ArmorStand armorStand = (ArmorStand) entity;
                        if (trophyManager.hasTrophy(armorStand)) {
                            for (int i = 1; i < plugin.getConfig().getInt("Amount-Of-Tiers") + 1; i++) {
                                if (armorStand.getCustomName().equalsIgnoreCase(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + i + ".name")))) {
                                    tier.put(armorStand, i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static int getTier (ArmorStand armorStand) {
        return tier.get(armorStand);
    }

    public static void clearTierList() {
        tier.clear();
    }
}

        /*
        for (int i = 1; i < plugin.getConfig().getInt("Amount-Of-Tiers") + 1; i++){
            if (p.getItemInHand().getItemMeta().getDisplayName().equals(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + i + ".name")))){
                if (SuperiorSkyblockAPI.getPlayer(p).isInsideIsland()) {
                    Island island = SuperiorSkyblockAPI.getIslandAt(p.getLocation());
                    for (UUID uuid : island.getAllMembers()){
                        if (!uuid.equals(p.getUniqueId())){
                            e.setCancelled(true);
                            p.sendMessage(Utils.chat(messages.getFile().getString("Illegal-Placement")));
                        }
                    }
                }
         */
