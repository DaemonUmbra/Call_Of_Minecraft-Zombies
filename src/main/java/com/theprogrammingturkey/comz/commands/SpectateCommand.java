package com.theprogrammingturkey.comz.commands;

import com.theprogrammingturkey.comz.game.Game;
import com.theprogrammingturkey.comz.game.GameManager;
import com.theprogrammingturkey.comz.util.CommandUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpectateCommand implements SubCommand
{
	public boolean onCommand(Player player, String[] args)
	{
		if(player.hasPermission("zombies.spectate") || player.hasPermission("zombies.user") || player.hasPermission("zombies.admin"))
		{
			if(args.length == 1)
			{
				CommandUtil.sendMessageToPlayer(player, ChatColor.RED + "Please specify an arena to spectate!");
				return true;
			}
			else
			{
				String name = args[1];
				if(GameManager.INSTANCE.isValidArena(name))
				{
					Game game = GameManager.INSTANCE.getGame(name);
					Location specLocation = game.getSpectateLocation();
					player.teleport(specLocation);
					CommandUtil.sendMessageToPlayer(player, ChatColor.RED + "You are now spectating " + ChatColor.GOLD + game.getName() + ChatColor.RED + "!");
					return true;
				}
				else
				{
					CommandUtil.sendMessageToPlayer(player, ChatColor.RED + "That is not a valid arena!");
					return true;
				}
			}
		}
		else
		{
			CommandUtil.sendMessageToPlayer(player, ChatColor.RED + "You do not have permission to spectate!");
		}
		return false;
	}

}
