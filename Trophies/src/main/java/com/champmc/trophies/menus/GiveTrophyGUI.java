package com.champmc.trophies.menus;

import com.champmc.trophies.Trophies;
import com.champmc.trophies.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GiveTrophyGUI {

    Trophies plugin;

    public Inventory MainGUI (Player p){
        Inventory inv;
        inv = Bukkit.createInventory(null, 9, Utils.chat("&2&lTrophy Giver"));

        Utils.createItem(inv, Material.BARRIER, 1, 1, "&4&lExit");
        Utils.createItem(inv, Material.ARMOR_STAND, 1, 5, "&a&lCreate a Trophy");
        Utils.createItem(inv, Material.BARRIER, 1, 9, "&4&lExit");

        return inv;
    }

    public Inventory CreateGUI (Player p){
        Inventory inv;
        inv = Bukkit.createInventory(null, 9, Utils.chat("&2&lTier List"));
        for (int i = 1; i < (Utils.getConfig().getInt("Amount-Of-Tiers")) + 1; i++){
            Utils.createItem(inv, Material.ARMOR_STAND, 1, i, Utils.getConfig().getString("Tiers.Tier" + i + ".name"));
        }
        return inv;
    }

}
