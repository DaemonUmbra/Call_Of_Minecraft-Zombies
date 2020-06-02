package com.theprogrammingturkey.comz.game.managers;

import com.theprogrammingturkey.comz.config.COMZConfig;
import com.theprogrammingturkey.comz.config.ConfigManager;
import com.theprogrammingturkey.comz.config.CustomConfig;
import com.theprogrammingturkey.comz.game.Game;
import com.theprogrammingturkey.comz.game.features.Barrier;
import com.theprogrammingturkey.comz.spawning.SpawnPoint;
import com.theprogrammingturkey.comz.util.BlockUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BarrierManager
{
	private Game game;
	private ArrayList<Barrier> barriers = new ArrayList<>();

	public BarrierManager(Game game)
	{
		this.game = game;
	}

	public void loadAllBarriersToGame()
	{
		CustomConfig conf = ConfigManager.getConfig(COMZConfig.ARENAS);
		barriers.clear();
		ConfigurationSection sec = conf.getConfigurationSection(game.getName() + ".Barriers");
		if(sec == null)
			return;
		for(String key : sec.getKeys(false))
		{
			double x = conf.getDouble(game.getName() + ".Barriers." + key + ".x");
			double y = conf.getDouble(game.getName() + ".Barriers." + key + ".y");
			double z = conf.getDouble(game.getName() + ".Barriers." + key + ".z");
			Location loc = new Location(game.getWorld(), x, y, z);
			int number = Integer.parseInt(key);
			Barrier barrier = new Barrier(number, game);
			barrier.setBarrierBlock(loc);

			BlockUtils.setBlockTypeHelper(loc.getBlock(), BlockUtils.getMaterialFromKey(conf.getString(game.getName() + ".Barriers." + key + ".bb")));

			double rx = conf.getDouble(game.getName() + ".Barriers." + key + ".rx");
			double ry = conf.getDouble(game.getName() + ".Barriers." + key + ".ry");
			double rz = conf.getDouble(game.getName() + ".Barriers." + key + ".rz");
			barrier.setRepairLoc(new Location(game.getWorld(), rx, ry, rz));
			String facing = conf.getString(game.getName() + ".Barriers." + key + ".facing");
			barrier.setSignFacing(BlockFace.valueOf(facing));

			SpawnPoint point = game.spawnManager.getSpawnPoint(conf.getInt(game.getName() + ".Barriers." + key + ".sp"));
			barrier.assingSpawnPoint(point);

			barrier.setReward(conf.getInt(game.getName() + ".Barriers." + key + ".reward"));

			barriers.add(barrier);
		}
	}

	public Barrier getBarrier(Location loc)
	{
		for(Barrier b : barriers)
		{
			if(b.getLocation().equals(loc))
			{
				return b;
			}
		}
		return null;
	}

	public Barrier getBarrier(int num)
	{
		for(Barrier b : barriers)
		{
			if(b.getNum() == num)
			{
				return b;
			}
		}
		return null;
	}

	public Barrier getBarrierFromRepair(Location loc)
	{
		for(Barrier b : barriers)
			if(b.getRepairLoc().equals(loc))
				return b;
		return null;
	}

	public Barrier getBarrier(SpawnPoint p)
	{
		for(Barrier b : barriers)
		{
			if(b.getSpawnPoint().getLocation().equals(p.getLocation()))
			{
				return b;
			}
		}
		return null;
	}

	public void removeBarrier(Player player, Barrier barrier)
	{
		if(barriers.contains(barrier))
		{
			CustomConfig conf = ConfigManager.getConfig(COMZConfig.ARENAS);
			conf.set(game.getName() + ".Barriers." + barrier.getNum(), null);
			conf.saveConfig();
			loadAllBarriersToGame();
			player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Barrier removed!");
			BlockUtils.setBlockToAir(barrier.getRepairLoc());
			barriers.remove(barrier);
		}
	}

	public void addBarrier(Barrier barrier)
	{
		CustomConfig conf = ConfigManager.getConfig(COMZConfig.ARENAS);
		if(game.getMode() == Game.ArenaStatus.DISABLED || game.getMode() == Game.ArenaStatus.WAITING)
		{
			boolean same = false;
			for(Barrier b : barriers)
			{
				if(b.getLocation().equals(barrier.getLocation()))
				{
					same = true;
					break;
				}
			}
			if(!same)
			{
				Location loc = barrier.getLocation();
				Location loc2 = barrier.getRepairLoc();
				SpawnPoint sp = barrier.getSpawnPoint();
				int name = barrier.getNum();

				conf.set(game.getName() + ".Barriers." + name + ".x", loc.getBlockX());
				conf.set(game.getName() + ".Barriers." + name + ".y", loc.getBlockY());
				conf.set(game.getName() + ".Barriers." + name + ".z", loc.getBlockZ());
				conf.set(game.getName() + ".Barriers." + name + ".rx", loc2.getBlockX());
				conf.set(game.getName() + ".Barriers." + name + ".ry", loc2.getBlockY());
				conf.set(game.getName() + ".Barriers." + name + ".rz", loc2.getBlockZ());
				conf.set(game.getName() + ".Barriers." + name + ".facing", barrier.getSignFacing().name());
				conf.set(game.getName() + ".Barriers." + name + ".sp", sp.getID());
				conf.set(game.getName() + ".Barriers." + name + ".bb", barrier.getBlock().getType().getKey().getKey());
				conf.set(game.getName() + ".Barriers." + name + ".reward", barrier.getReward());
				conf.saveConfig();
				conf.reloadConfig();
				barriers.add(barrier);
			}
		}
	}

	public void UpdateBarrier(Barrier barrier)
	{
		CustomConfig conf = ConfigManager.getConfig(COMZConfig.ARENAS);
		if(game.getMode() == Game.ArenaStatus.DISABLED || game.getMode() == Game.ArenaStatus.WAITING)
		{
			boolean same = false;
			for(Barrier b : barriers)
			{
				if(b.getLocation().equals(barrier.getLocation()))
				{
					same = true;
					break;
				}
			}
			if(!same)
			{
				Location loc = barrier.getLocation();
				Location loc2 = barrier.getRepairLoc();
				SpawnPoint sp = barrier.getSpawnPoint();
				int name = barrier.getNum();
				conf.set(game.getName() + ".Barriers." + name + ".x", loc.getBlockX());
				conf.set(game.getName() + ".Barriers." + name + ".y", loc.getBlockY());
				conf.set(game.getName() + ".Barriers." + name + ".z", loc.getBlockZ());
				conf.set(game.getName() + ".Barriers." + name + ".rx", loc2.getBlockX());
				conf.set(game.getName() + ".Barriers." + name + ".ry", loc2.getBlockY());
				conf.set(game.getName() + ".Barriers." + name + ".rz", loc2.getBlockZ());
				conf.set(game.getName() + ".Barriers." + name + ".facing", barrier.getSignFacing().name());
				conf.set(game.getName() + ".Barriers." + name + ".sp", sp.getID());
				conf.set(game.getName() + ".Barriers." + name + ".bb", barrier.getBlock().getType().getKey().getKey());
				conf.set(game.getName() + ".Barriers." + name + ".reward", barrier.getReward());
				conf.saveConfig();
				barriers.add(barrier);
			}
		}
	}

	public ArrayList<Barrier> getBrriers()
	{
		return barriers;
	}

	public int getTotalBarriers()
	{
		return barriers.size();
	}

	public Game getGame()
	{
		return game;
	}

	public int getNextBarrierNumber()
	{
		int a = 0;
		while(this.getBarrier(a) != null)
		{
			a++;
		}
		return a;
	}

	public void unloadAllBarriers()
	{
		for(Barrier b : barriers)
		{
			b.repairFull();
		}
	}
}