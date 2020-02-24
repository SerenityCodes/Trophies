package com.champmc.trophies.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.champmc.trophies.Trophies;
import com.champmc.trophies.events.TrophyPickupEvent;
import com.champmc.trophies.files.Messages;
import com.champmc.trophies.managers.TrophyManager;
import com.champmc.trophies.menus.GiveTrophyGUI;
import com.champmc.trophies.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuManager implements Listener {

    Trophies plugin;
    GiveTrophyGUI giveTrophyGUI = new GiveTrophyGUI();
    TrophyManager trophyManager = new TrophyManager();
    Messages message = new Messages();

    public MenuManager(Trophies plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void menuManager(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        // Trophy Giver Confirm Screen
        if (e.getClickedInventory().getTitle().equals(Utils.chat("&2&lTrophy Giver"))){
            switch (e.getCurrentItem().getType()){
                case ARMOR_STAND:
                    p.closeInventory();
                    p.openInventory(giveTrophyGUI.CreateGUI(p));
                    break;
                case BARRIER:
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                    break;
            }
            e.setCancelled(true);
        }

        // Main Tier List
        if (e.getClickedInventory().getTitle().equals(Utils.chat("&2&lTier List"))){
            ItemStack item;
            List<String> lore = new ArrayList<>();
            item = new ItemStack(Material.ARMOR_STAND, 1);
            ItemMeta meta = item.getItemMeta();
            String name = Utils.chat(e.getCurrentItem().getItemMeta().getDisplayName());

            meta.setDisplayName(Utils.chat(e.getCurrentItem().getItemMeta().getDisplayName()));


            for (int i = 1; i < plugin.getConfig().getInt("Amount-Of-Tiers") + 1; i++){
                if (name.equals(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + i + ".name")))){
                    lore.add(Utils.chat("&a+" + plugin.getConfig().getInt("Tiers.Tier" + i + ".level-gain") + " Island Levels when placed"));
                    lore.add(Utils.chat("&bGenerates " + plugin.getConfig().getInt("Tiers.Tier" + i + ".amount") + " MobCoins per minute"));
                    lore.add(Utils.chat("&4Maximum " + plugin.getConfig().getInt("Tiers.Tier" + i + ".cap") + " per island"));
                }
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 1);
            p.getInventory().addItem(item);
            p.sendMessage(Utils.chat("&7[&aTrophies&7] &r&aGiven " + name));

            e.setCancelled(true);
        }

        // Main Admin Menu
        if (e.getClickedInventory().getTitle().equals(Utils.chat("&2&lTrophies"))){
            switch (e.getCurrentItem().getType()){
                case ARMOR_STAND:
                    p.openInventory(giveTrophyGUI.MainGUI(p));
                    p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                    break;
                case PAPER:
                    plugin.reloadConfig();
                    Messages.reload();
                    p.sendMessage(Utils.chat(message.getFile().getString("Reload-Message")));
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                    break;
                case BARRIER:
                    p.playSound(p.getLocation(), Sound.NOTE_BASS, 1, 1);
                    p.closeInventory();
                    break;
            }
            e.setCancelled(true);
        }

        // Trophy Menu
        Island island = SuperiorSkyblockAPI.getIslandAt(p.getLocation());
        if (island.getAllMembers().contains(p.getUniqueId())) {
            for (int i = 1; i < plugin.getConfig().getInt("Amount-Of-Tiers") + 1; i++) {
                if (e.getClickedInventory().getTitle().equals(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + i + ".name")))) {
                    ArmorStand armorStand = getTarget(p);
                    switch (e.getCurrentItem().getType()){
                        case EMERALD:
                            //trophyManager.addMobCoinsToPlayer(p, armorStand);
                            p.sendMessage(Utils.chat("Acquire currency lol"));
                            break;
                        case BARRIER:
                            p.closeInventory();
                            Bukkit.getPluginManager().callEvent(new TrophyPickupEvent(p, armorStand, island, i));
                            break;
                    }
                    e.setCancelled(true);
                    break;
                }
            }
        } else {
            p.sendMessage(Utils.chat(message.getFile().getString("Deny-Message")));
        }
    }

    // Method for getting the trophy that the player is looking at
    public ArmorStand getTarget(Player p) {
        List<Entity> list = p.getNearbyEntities(p.getEyeLocation().getX(), p.getEyeLocation().getY(), p.getEyeLocation().getZ());
        for (Entity entity : list){
            if (entity instanceof ArmorStand){
                ArmorStand armorStand = (ArmorStand) entity;
                int radius = 2;
                if (armorStand.getLocation().distance(p.getLocation()) <= radius){
                    return armorStand;
                }
            }
        }
        return null;
    }
    
    
}
