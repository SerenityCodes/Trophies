package com.champmc.trophies.listeners;

import com.champmc.trophies.Trophies;
import com.champmc.trophies.menus.InsideGUI;
import com.champmc.trophies.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class OpenTrophyListener implements Listener {

    Trophies plugin;
    InsideGUI insideGUI = new InsideGUI();

    public OpenTrophyListener(Trophies plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent e){
        Entity entity = e.getRightClicked();
        Player p = e.getPlayer();

        if (entity instanceof ArmorStand){
            ArmorStand armorStand = (ArmorStand) entity;
            for (int i = 1; i < plugin.getConfig().getInt("Amount-Of-Tiers") + 1; i++) {
                if (armorStand.isCustomNameVisible() && armorStand.getCustomName().equals(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + i + ".name")))) {
                    p.openInventory(insideGUI.GUI(p, armorStand.getCustomName(), armorStand, plugin));
                    e.setCancelled(true);
                }
            }
        }
    }
}
