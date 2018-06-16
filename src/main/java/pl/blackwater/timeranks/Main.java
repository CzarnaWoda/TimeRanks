package pl.blackwater.timeranks;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import pl.blackwater.timeranks.commands.TimeRanksCommand;
import pl.blackwater.timeranks.listeners.PlayerJoinListener;
import pl.blackwater.timeranks.managers.TimeRanksManager;
import pl.blackwater.timeranks.settings.Config;
import pl.blackwater.timeranks.tasks.TimeRankCheckTask;
import pl.blackwaterapi.API;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.TimeUtil;

public class Main extends JavaPlugin{
	private static Main plugin;
	
	public void onLoad(){
		plugin = this;
	}
	public void onEnable(){
		API.registerListener(this, new PlayerJoinListener());
		API.registerCommand((Command)new TimeRanksCommand());
		new TimeRankCheckTask().runTaskTimerAsynchronously((Plugin)this,20L,(long)TimeUtil.SECOND.getTick(Config.TIMERANKS_REFRESHTIME));
		TimeRanksManager.loadTimeRanks();
		Config.reloadConfig();
	}
	
	public static Main getPlugin(){
		return plugin;
	}

}
