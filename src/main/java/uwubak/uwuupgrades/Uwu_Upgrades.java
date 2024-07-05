package uwubak.uwuupgrades;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import uwubak.uwuupgrades.Commands.MainCmd;

import java.util.HashMap;
import java.util.UUID;

public final class Uwu_Upgrades extends JavaPlugin {

    private static Uwu_Upgrades plugin;



    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

        getCommand("ulepszanie").setExecutor(new MainCmd(this));

        Bukkit.getConsoleSender().sendMessage("####################################################");
        Bukkit.getConsoleSender().sendMessage("#                                                  #");
        Bukkit.getConsoleSender().sendMessage("#                    uwu-Upgrades                  #");
        Bukkit.getConsoleSender().sendMessage("#                    wersja: 1.0                   #");
        Bukkit.getConsoleSender().sendMessage("#                                                  #");
        Bukkit.getConsoleSender().sendMessage("#            discord: discord.gg/xxxxxxx           #");
        Bukkit.getConsoleSender().sendMessage("####################################################");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Uwu_Upgrades getPlugin() {
        return plugin;
    }

}
