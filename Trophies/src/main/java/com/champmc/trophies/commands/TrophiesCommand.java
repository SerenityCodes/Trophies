package com.champmc.trophies.commands;

import com.champmc.trophies.Trophies;
import com.champmc.trophies.files.Messages;
import com.champmc.trophies.menus.GiveTrophyGUI;
import com.champmc.trophies.menus.MainGUI;
import com.champmc.trophies.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrophiesCommand implements CommandExecutor {

    Trophies plugin;
    MainGUI mainGUI = new MainGUI();
    Messages messages = new Messages();

    public TrophiesCommand(Trophies plugin){
        this.plugin = plugin;
        plugin.getCommand("trophies").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.hasPermission("trophies.admin")){
                p.openInventory(mainGUI.GUI(p));
            } else {
                p.sendMessage(Utils.chat(messages.getFile().getString("No-Perms")));
            }
        }

        return true;
    }
}
