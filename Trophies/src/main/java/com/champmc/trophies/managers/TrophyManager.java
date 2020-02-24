package com.champmc.trophies.managers;

import me.swanis.mobcoins.MobCoinsAPI;
import me.swanis.mobcoins.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TrophyManager {

    public static HashMap<UUID, Integer> mobCoins = new HashMap<>();

    public void saveTrophyFile() throws IOException {

        File file = new File(Bukkit.getPluginManager().getPlugin("Trophies").getDataFolder(), "currency.dat");

        for (ArmorStand armorStand : getTrophies()) {
            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            if (mobCoins.containsKey(armorStand.getUniqueId())) {
                mobCoins.put(armorStand.getUniqueId(), mobCoins.get(armorStand.getUniqueId()));
            }

            try {
                output.writeObject(mobCoins);
                output.flush();
                output.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void loadTrophyFile() throws IOException, ClassNotFoundException {

        File file = new File(Bukkit.getPluginManager().getPlugin("Trophies").getDataFolder(), "currency.dat");

        if (!file.exists()){
            boolean success = file.createNewFile();
            System.out.println("[Trophies] Currency.dat file creation: " + success);
        }

        try {
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObject = input.readObject();
            input.close();

            mobCoins = (HashMap<UUID, Integer>) readObject;

            if (readObject == null){
                throw new IOException("Data is not in a hashmap");
            }
            for (UUID key : mobCoins.keySet()){
                mobCoins.put(key, mobCoins.get(key));
            }
        } catch (EOFException e){
            e.printStackTrace();
        }
    }

    public void addMobCoins(ArmorStand armorStand, int amount){
        if (mobCoins.get(armorStand.getUniqueId()) != null){
            mobCoins.put(armorStand.getUniqueId(), mobCoins.get(armorStand.getUniqueId()) + amount);
        } else {
            mobCoins.put(armorStand.getUniqueId(), amount);
        }
    }

    public void setMobCoins(ArmorStand armorStand, int amount){
        mobCoins.put(armorStand.getUniqueId(), amount);
    }

    public int getMobCoins(ArmorStand armorStand){
        if (mobCoins.containsKey(armorStand.getUniqueId())){
            return mobCoins.get(armorStand.getUniqueId());
        }
        return 0;
    }

    public boolean hasTrophy(ArmorStand armorStand){
        return mobCoins.containsKey(armorStand.getUniqueId());
    }

    public void addTrophy(ArmorStand armorStand){
        mobCoins.put(armorStand.getUniqueId(), 0);
    }

    public void removeTrophy(ArmorStand armorStand){
        mobCoins.remove(armorStand.getUniqueId());
    }

    public void addMobCoinsToPlayer(Player p, ArmorStand armorStand){
        int coins = getMobCoins(armorStand);
        Profile profile = MobCoinsAPI.getProfileManager().getProfile(p);
        profile.setMobCoins(profile.getMobCoins() + coins);
        setMobCoins(armorStand, 0);
    }

    public ArrayList<ArmorStand> getTrophies(){
        ArrayList<ArmorStand> list = new ArrayList<>();
        for (World world : Bukkit.getWorlds()){
            for (Entity entity : world.getEntities()){
                if (entity instanceof ArmorStand){
                    ArmorStand armorStand = (ArmorStand) entity;
                    if (hasTrophy(armorStand)){
                        list.add(armorStand);
                    }
                }
            }
        }
        return list;
    }
}
