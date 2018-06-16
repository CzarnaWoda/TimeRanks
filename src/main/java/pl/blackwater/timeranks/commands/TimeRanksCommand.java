package pl.blackwater.timeranks.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import pl.blackwater.timeranks.base.TimeRanks;
import pl.blackwater.timeranks.managers.TimeRanksManager;
import pl.blackwater.timeranks.settings.Config;
import pl.blackwaterapi.commands.Command;
import pl.blackwaterapi.utils.Util;

public class TimeRanksCommand extends Command{

	public TimeRanksCommand() {
		super("timerank", "timeranks", "/timerank <add/remove/info> [player] [rank] [time]", "timerank.command", new String[]{"setrank","timeranks","pexs"});
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onExecute(CommandSender sender, String[] args) {
		String getUsage = "&8» &6Poprawne uzycie: &c&n" + getUsage();
		if(args.length == 2)
		{
			if(args[0].equalsIgnoreCase("remove")||args[0].equalsIgnoreCase("delete")||args[0].equalsIgnoreCase("del"))
			{
				TimeRanks timerank = TimeRanksManager.getTimeRankByName(args[1]);
				if(timerank != null)
				{
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + timerank.getLastname() + " group set " + timerank.getPreviousRank());
					TimeRanksManager.deleteTimeRank(timerank);
					return Util.sendMsg(sender, "&8» &6Usunieto range czasowa gracza &c&n" + timerank.getLastname() + "&6!");
				}else
				{
					return Util.sendMsg(sender, "&8» &6Ten gracz nie posiada &c&nrangi czasowej&6!");
				}
			}
			else
			{
				if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("informacje")||args[0].equalsIgnoreCase("information")){
					TimeRanks timerank = TimeRanksManager.getTimeRankByName(args[1]);
					if(timerank != null)
					{
						Util.sendMsg(sender, "&8» &6Gracz: &c&n" + timerank.getLastname());
						Util.sendMsg(sender, "&8» &6Ranga: &c&n" + timerank.getRank());
						Util.sendMsg(sender, "&8» &6Ranga poprzednia: &c&n" + timerank.getPreviousRank());
						Util.sendMsg(sender, "&8» &6Czas wygasniecia : &c&n" + Util.getDate((long)timerank.getExpireTime()) + " &8(&e" + Util.secondsToString((int)((int)(timerank.getExpireTime() - System.currentTimeMillis()) / 1000L)) + "&8)");
						return true;
					}else
					{
						return Util.sendMsg(sender, "&8» &6Ten gracz nie posiada &c&nrangi czasowej&6!");
					}
				}else{
					return Util.sendMsg(sender, getUsage);		
				}
			}
		}else
		{
			if (args.length < 3) 
			{
				return Util.sendMsg(sender, getUsage);		
		}
		if(!args[0].equalsIgnoreCase("add")&&!args[0].equalsIgnoreCase("set"))
		{
			return Util.sendMsg(sender, getUsage);
		}
		if(args.length > 4)
		{
			return Util.sendMsg(sender, getUsage);
		}
		TimeRanks timerank = TimeRanksManager.getTimeRankByName(args[1]);
		if(timerank != null)
		{
			return Util.sendMsg(sender, "&8» &6Ten gracz posiada &c&nrange czasowa&6!");
		}
		String rank = args[2];
		String previousRank = Config.TIMERANKS_DEFAULTGROUP;
		Long time = 0L;
		if(args.length == 4)
		{
			time = Util.parseDateDiff(args[3], true);
		}
		TimeRanksManager.addTimeRank(args[1], rank, previousRank, time);
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "pex user " + args[1] + " group set " + rank);
        return (time == 0L) ? Util.sendMsg(sender, "&8» &6Dano graczowi &c&n" + args[1] + " &6range &c&n" + rank + " &6na zawsze!") : Util.sendMsg(sender, "&8» &6Dano graczowi &c&n" + args[1] + " &6range &c&n" + rank + " &6do dnia &c&n" + Util.getDate((long)time) + "&6!");
	}
	}

}
