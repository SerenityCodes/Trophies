package com.champmc.trophies.listeners;

import com.champmc.trophies.Trophies;
import com.champmc.trophies.events.TrophyPickupEvent;
import com.champmc.trophies.events.TrophyPlaceEvent;
import com.champmc.trophies.files.Messages;
import com.champmc.trophies.managers.TrophyManager;
import com.champmc.trophies.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TrophyPlacePickupListener implements Listener {

    Trophies plugin;
    TrophyManager trophyManager = new TrophyManager();
    Messages messages = new Messages();

    public TrophyPlacePickupListener(Trophies plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onTrophyPlace(TrophyPlaceEvent e) {
        Location toSpawn = e.getLocation();
        int tier = e.getTier();

        ArmorStand trophy = (ArmorStand) toSpawn.getWorld().spawnEntity(toSpawn, EntityType.ARMOR_STAND);
        trophy.setCustomName(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + tier + ".name")));
        trophy.setCustomNameVisible(true);
        trophy.setItemInHand(new ItemStack(Material.DOUBLE_PLANT));
        trophy.setHelmet(new ItemStack(Material.matchMaterial(plugin.getConfig().getString("Tiers.Tier" + tier + ".helmet"))));
        trophy.setChestplate(new ItemStack(Material.matchMaterial(plugin.getConfig().getString("Tiers.Tier" + tier + ".chest"))));
        trophy.setLeggings(new ItemStack(Material.matchMaterial(plugin.getConfig().getString("Tiers.Tier" + tier + ".leggings"))));
        trophy.setBoots(new ItemStack(Material.matchMaterial(plugin.getConfig().getString("Tiers.Tier" + tier + ".boots"))));
        trophy.setArms(true);
        trophyManager.addTrophy(trophy);
        trophyManager.getTrophies().add(trophy);
        e.getPlayer().sendMessage(Utils.chat(messages.getFile().getString("Trophy-Place-Message")));
    }

    @EventHandler
    public void onTrophyPickup(TrophyPickupEvent e) {
        Player p = e.getPlayer();
        ArmorStand armorStand = e.getArmorStand();
        int tier = e.getTier();

        ItemStack item;
        List<String> lore = new ArrayList<>();
        item = new ItemStack(Material.ARMOR_STAND, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Utils.chat(armorStand.getCustomName()));
        lore.add(Utils.chat("&a+" + plugin.getConfig().getInt("Tiers.Tier" + tier + ".level-gain") + " Island Levels when placed"));
        lore.add(Utils.chat("&bGenerates " + plugin.getConfig().getInt("Tiers.Tier" + tier + ".amount") + " MobCoins per minute"));
        lore.add(Utils.chat("&4Maximum " + plugin.getConfig().getInt("Tiers.Tier" + tier + ".cap") + " per island"));

        meta.setLore(lore);
        item.setItemMeta(meta);
        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
        p.getInventory().addItem(item);
        p.sendMessage(Utils.chat(messages.getFile().getString("Trophy-Pickup-Message").replace("<name>", meta.getDisplayName())));
        armorStand.remove();
        trophyManager.removeTrophy(armorStand);
        trophyManager.getTrophies().remove(armorStand);
    }
}
