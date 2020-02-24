package com.champmc.trophies.events;

import com.bgsoftware.superiorskyblock.api.island.Island;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TrophyPickupEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player p;
    ArmorStand armorStand;
    Island island;
    int tier;
    boolean cancelled;

    public TrophyPickupEvent(Player p, ArmorStand armorStand, Island island, int tier) {
        this.p = p;
        this.armorStand = armorStand;
        this.island = island;
        this.tier = tier;
    }

    public Player getPlayer() {
        return p;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public Island getIsland() {
        return island;
    }

    public int getTier() {
        return tier;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
