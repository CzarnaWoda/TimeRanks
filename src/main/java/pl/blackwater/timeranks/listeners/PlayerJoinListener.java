package pl.blackwater.timeranks.listeners;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import pl.blackwater.chestpvpcore.Core;
import pl.blackwater.timeranks.base.TimeRanks;
import pl.blackwater.timeranks.managers.TimeRanksManager;
import pl.blackwater.timeranks.settings.Config;
import pl.blackwaterapi.utils.ActionBarUtil;
import pl.blackwaterapi.utils.Util;

public class PlayerJoinListener implements Listener{
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e){
		final Player p = e.getPlayer();
		final TimeRanks timerank = TimeRanksManager.getTimeRankByPlayer(p);
		if(timerank != null){
		    Bukkit.getScheduler().runTaskLater((Plugin)Core.getInstance(), new Runnable()
		    {
		      public void run()
		      {
		    	  ActionBarUtil.sendActionBar(p, Util.fixColor("&8» &6Ranga &c" + timerank.getRank() + " &6jest aktywna do &c" + Util.getDate((long)timerank.getExpireTime()) + " &8(&e" + Util.secondsToString((int)((int)(timerank.getExpireTime() - System.currentTimeMillis()) / 1000L))+ "&8)"));
		    	  Util.sendMsg(p, "§8» &6Twoja ranga &c&n" + timerank.getRank() + "&6 jest aktywna do &c&n" + Util.getDate((long)timerank.getExpireTime()) + " &8(&e" + Util.secondsToString((int)((int)(timerank.getExpireTime() - System.currentTimeMillis()) / 1000L))+ "&8)");
		      }
		    }, 15L);
			if(timerank.getExpireTime() != 0L && System.currentTimeMillis() >= timerank.getExpireTime())
			{
			    Bukkit.getScheduler().runTaskLater((Plugin)Core.getInstance(), new Runnable()
			    {
			      public void run()
			      {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "pex user " + timerank.getLastname() + " group set " + Config.TIMERANKS_DEFAULTGROUP);
                Util.sendMsg((CommandSender)p, "&8» &6Czas twojej rangi sie skonczyl! &8(&c&n" + timerank.getRank() + "&8)");
                TimeRanksManager.deleteTimeRank(timerank);
			      }
			    }, 15L);
			}
		}
	}
}
