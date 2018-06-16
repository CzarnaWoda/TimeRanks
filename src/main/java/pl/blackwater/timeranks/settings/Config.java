package pl.blackwater.timeranks.settings;

import java.lang.reflect.Field;

import org.bukkit.configuration.file.FileConfiguration;

import pl.blackwater.timeranks.Main;

public class Config
{
    public static String TIMERANKS_DEFAULTGROUP;
    public static int TIMERANKS_REFRESHTIME;
    
    public static void loadConfig() {
        try {
            Main.getPlugin().saveDefaultConfig();
            FileConfiguration c = Main.getPlugin().getConfig();
            for (Field f : Config.class.getFields()) {
                if (c.isSet("config." + f.getName().toLowerCase().replace("_", "."))) {
                    f.set(null, c.get("config." + f.getName().toLowerCase().replace("_", ".")));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveConfig() {
        try {
            FileConfiguration c = Main.getPlugin().getConfig();
            for (Field f : Config.class.getFields()) {
                c.set("config." + f.getName().toLowerCase().replace("_", "."), f.get(null));
            }
            Main.getPlugin().saveConfig();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void reloadConfig() {
        Main.getPlugin().reloadConfig();
        loadConfig();
        saveConfig();
    }
    
    static {
        Config.TIMERANKS_DEFAULTGROUP = "default";
        Config.TIMERANKS_REFRESHTIME = 10;
    }
}
