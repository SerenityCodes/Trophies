package com.champmc.trophies.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Messages {

    private static File file;
    private static FileConfiguration messagesFile;

    public static void setupFile(){

        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Trophies")).getDataFolder(), "messages.yml");

        if (!(file.exists())){
            try {
                boolean success = file.createNewFile();
                System.out.println("[Trophies] Messages file creation: " + success);
            } catch (IOException e){
                System.out.println("[Trophies] Messages file creation: " + false);
                e.printStackTrace();
            }
        }
        messagesFile = new YamlConfiguration();
        try{
            messagesFile.load(file);
        } catch (IOException | InvalidConfigurationException e){
            System.out.println("[Trophies] Messages.yml loading was unsuccessful");
            e.printStackTrace();
        }
    }

    public FileConfiguration getFile(){
        return messagesFile;
    }

    public void saveFile(){
        try {
            messagesFile.save(file);
        } catch (IOException e) {
            System.out.println("[Trophies] Messages.yml saving was unsuccessful");
            e.printStackTrace();
        }
    }

    public static void reload(){
        messagesFile = YamlConfiguration.loadConfiguration(file);
    }

    public void loadDefaults(){
        messagesFile.addDefault("Reload-Message", "&7[&aTrophies&7] &r&aReload successful");
        messagesFile.addDefault("No-Perms", "&7[&aTrophies&7] &r&4Insufficient Permission");
        messagesFile.addDefault("Claim-Message", "&7[&aTrophies&7] &r&bTrophy Claimed");
        messagesFile.addDefault("Trophy-Place-Message", "&7[&aTrophies&7] <name> &bplaced");
        messagesFile.addDefault("Trophy-Pickup-Message", "&7[&aTrophies&7] <name> &bbroken");
        messagesFile.addDefault("Illegal-Placement", "&7[&aTrophies&7] &bYou cannot place that here");
        messagesFile.addDefault("Cap-Reached", "&7[&aTrophies&7] &bCap has been reached");
    }

}
