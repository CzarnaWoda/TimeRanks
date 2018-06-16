package pl.blackwater.timeranks.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import pl.blackwater.timeranks.base.TimeRanks;
import pl.blackwater.timeranks.managers.TimeRanksManager;
import pl.blackwaterapi.utils.Util;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TimeRankCheckTask extends BukkitRunnable{

	public void run() {
		for(TimeRanks timeranks : TimeRanksManager.getTimeRanks().values()){
		      if ((timeranks.getExpireTime().longValue() != 0L) && (System.currentTimeMillis() >= timeranks.getExpireTime().longValue()))
		      {
		  			PermissionUser user = PermissionsEx.getUser(timeranks.getLastname());
		  			user.removeGroup(timeranks.getRank());
		  			user.addGroup(timeranks.getPreviousRank());
			        Player p = Bukkit.getPlayer(timeranks.getLastname());
			        if(p != null)
			        {
			        	Util.sendMsg(p, "&8» &6Czas twojej rangi &8(&a&n" + timeranks.getRank() + "&8) &c&nskonczyl sie&6!");
			        }
			        TimeRanksManager.deleteTimeRank(timeranks);
		      }
		}
		
	}

}
