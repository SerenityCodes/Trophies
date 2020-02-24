package com.champmc.trophies.menus;

import com.champmc.trophies.Trophies;
import com.champmc.trophies.managers.TrophyManager;
import com.champmc.trophies.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InsideGUI {

    TrophyManager trophyManager = new TrophyManager();


    public Inventory GUI(Player p, String inventoryName, ArmorStand armorStand, Trophies plugin) {
        Inventory inv;
        inv = Bukkit.createInventory(null, 27, inventoryName);
        int tier = 0;

        for (int i = 1; i < plugin.getConfig().getInt("Amount-Of-Tiers") + 1; i++) {
            if (armorStand.getCustomName().equals(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + i + ".name")))) {
                tier = i;
                break;
            }
        }

        Utils.createItem(inv, Material.PAPER, 1, 12, "&bInformation", "&bGeneration Rate: "
                        + plugin.getConfig().getInt("Tiers.Tier" + tier + ".amount") + "/min",
                "&bIsland Level Gain: " + plugin.getConfig().getInt("Tiers.Tier" + tier + ".level-gain"),
                "&bCap per Island: " + plugin.getConfig().getInt("Tiers.Tier" + tier + ".cap"));
        Utils.createItem(inv, Material.EMERALD, 1, 14, "&bCollect " + trophyManager.getMobCoins(armorStand) + " Mob Coins");
        Utils.createItem(inv, Material.BARRIER, 1, 16, "&4Pickup Trophy");

        return inv;
    }


}
