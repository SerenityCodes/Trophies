package com.champmc.trophies;

import com.champmc.trophies.commands.TrophiesCommand;
import com.champmc.trophies.files.Messages;
import com.champmc.trophies.listeners.MenuManager;
import com.champmc.trophies.listeners.OpenTrophyListener;
import com.champmc.trophies.listeners.InteractListener;
import com.champmc.trophies.managers.TrophyManager;
import com.champmc.trophies.tasks.AddCurrencyTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Trophies extends JavaPlugin {

    TrophyManager trophyManager = new TrophyManager();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerManagers();
        registerListeners();
        registerCommands();
        runCurrencyTask();
        setupFiles();
    }

    public void onDisable(){
        try {
            trophyManager.saveTrophyFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InteractListener.clearTierList();
    }

    public void registerCommands(){
        new TrophiesCommand(this);
    }

    public void runCurrencyTask(){
        new AddCurrencyTask(this).runTaskTimerAsynchronously(this, 0L, 1200L);
    }

    public void registerManagers(){
        try {
            trophyManager.loadTrophyFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void registerListeners(){
        new MenuManager(this);
        new OpenTrophyListener(this);
    }

    public void setupFiles(){
        Messages.setupFile();
        Messages messages = new Messages();
        messages.getFile().options().copyDefaults(true);
        messages.loadDefaults();
        messages.saveFile();
    }
}
