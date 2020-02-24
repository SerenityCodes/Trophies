package com.champmc.trophies.events;

import com.bgsoftware.superiorskyblock.api.island.Island;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TrophyPlaceEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    Player p;
    Location location;
    Island island;
    int tier;
    boolean cancelled;

    public TrophyPlaceEvent(Player p, Location location, Island island, int tier) {
        this.p = p;
        this.location = location;
        this.island = island;
        this.tier = tier;
    }

    public Player getPlayer() {
        return p;
    }

    public Location getLocation() {
        return location;
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
