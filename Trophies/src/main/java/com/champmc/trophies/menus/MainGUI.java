package com.champmc.trophies.menus;

import com.champmc.trophies.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainGUI {

    public Inventory GUI (Player p){
        Inventory inv;
        inv = Bukkit.createInventory(null, 27, Utils.chat("&2&lTrophies"));

        Utils.createItem(inv, Material.BARRIER, 1, 1, Utils.chat("&4Exit"));
        Utils.createItem(inv, Material.BARRIER, 1, 9, Utils.chat("&4Exit"));
        Utils.createItem(inv, Material.BARRIER, 1, 19, Utils.chat("&4Exit"));
        Utils.createItem(inv, Material.BARRIER, 1, 27, Utils.chat("&4Exit"));

        Utils.createItem(inv, Material.ARMOR_STAND, 1, 12, Utils.chat("&aCreate Trophy"));
        Utils.createItem(inv, Material.PAPER, 1, 16, Utils.chat("&aReload Config"));

        return inv;
    }

}
