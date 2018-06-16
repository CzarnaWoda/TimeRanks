package pl.blackwater.timeranks.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import pl.blackwater.chestpvpcore.base.User;
import pl.blackwater.timeranks.base.TimeRanks;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;

public class TimeRanksManager {
	public static ConcurrentHashMap<String, TimeRanks> timeranks = new ConcurrentHashMap<String, TimeRanks>();
	
	public static TimeRanks getTimeRankByName(String lastname)
	{
		for(TimeRanks tr : timeranks.values())
		{
			if(lastname.equalsIgnoreCase(tr.getLastname()))
			{
				return tr;
			}
		}
		return null;
	}
	public static TimeRanks getTimeRankByPlayer(Player player)
	{
		for(TimeRanks tr : timeranks.values())
		{
			if(player.getName().equalsIgnoreCase(tr.getLastname()))
			{
				return tr;
			}
		}
		return null;
	}
	public static TimeRanks getTimeRankByUser(User u)
	{
		for(TimeRanks tr : timeranks.values())
		{
			if(u.getLastName().equalsIgnoreCase(tr.getLastname()))
			{
				return tr;
			}
		}
		return null;
	}
	public static void addTimeRank(String lastname,String newrank, String oldrank,Long time)
	{
		timeranks.put(lastname, new TimeRanks(lastname, newrank, oldrank, time));
	}
	public static void deleteTimeRankByName(String lastname)
	{
		timeranks.remove(lastname);
		API.getStore().update(false,"DELETE FROM `{P}timeranks` WHERE `lastname` = '" + lastname + "';");
	}
	public static void deleteTimeRank(TimeRanks timerank)
	{
		timeranks.remove(timerank.getLastname());
		API.getStore().update(false,"DELETE FROM `{P}timeranks` WHERE `lastname` = '" + timerank.getLastname() + "';");
	}
	  public static void loadTimeRanks()
	  {
	    try
	    {
	      ResultSet rs = API.getStore().query("SELECT * FROM `{P}timeranks`");
	      while (rs.next())
	      {
	        TimeRanks tr = new TimeRanks(rs);
	        timeranks.put(tr.getLastname(), tr);
	      }
	      rs.close();
	      Logger.info(new String[] {"Loaded " + timeranks.size() + " timeranks users" });
	    }
	    catch (SQLException e)
	    {
	      Logger.info(new String[] {"Can not load timeranks Error " + e.getMessage() });
	      e.printStackTrace();
	    }
	  }
	  
	  public static ConcurrentHashMap<String, TimeRanks> getTimeRanks()
	  {
	    return timeranks;
	  }
}
