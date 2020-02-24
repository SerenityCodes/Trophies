package com.champmc.trophies.tasks;

import com.champmc.trophies.Trophies;
import com.champmc.trophies.managers.TrophyManager;
import com.champmc.trophies.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.scheduler.BukkitRunnable;

public class AddCurrencyTask extends BukkitRunnable {

    Trophies plugin;
    TrophyManager trophyManager = new TrophyManager();

    public AddCurrencyTask(Trophies plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (ArmorStand armorStand : trophyManager.getTrophies()) {
            if (trophyManager.hasTrophy(armorStand)){
                for (int i = 1; i < plugin.getConfig().getInt("Amount-Of-Tiers") + 1; i++){
                    if (armorStand.getCustomName().equals(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + i + ".name")))){
                        int amount = plugin.getConfig().getInt("Tiers.Tier" + i + ".amount");
                        trophyManager.addMobCoins(armorStand, amount);
                        break;
                    }
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        InventoryView view = p.getOpenInventory();
                        if (view != null) {
                            if (view.getTitle().equals(Utils.chat(plugin.getConfig().getString("Tiers.Tier" + i + ".name")))) {
                                p.updateInventory();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
