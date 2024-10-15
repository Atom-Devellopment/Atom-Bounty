package fr.atom.bounty.manager;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import fr.atom.api.utils.PluginUtils;
import fr.atom.bounty.Bounty;
import fr.atom.bounty.command.PrimeCommand;
import fr.atom.bounty.config.ConfigManager;
import fr.atom.bounty.inventory.PrimeInventory;
import fr.atom.bounty.listener.PrimeListener;
import fr.atom.bounty.object.Prime;
import lombok.Getter;

@Getter
public class BountyManager 
{
	@Getter public static BountyManager INSTANCE;
	public Bounty bounty = Bounty.getINSTANCE();
	private ConfigManager config = ConfigManager.getINSTANCE();
	
	public HashMap<UUID,Prime> primes;
	
	public BountyManager()
	{
		INSTANCE = this;
		this.primes = Maps.newHashMap();
		
		PluginUtils.registerCommand(new PrimeCommand());
		PluginUtils.registerListener(new PrimeListener());
	}
	
	public void showPrime(Player player)
	{
		new PrimeInventory(player, 1, config.INVENTORY_NAME).open(player);
	}
	
	public void addPrime(Player attacker, Player victime, int price)
	{
		@SuppressWarnings("deprecation")
		String date = Date.from(Instant.now()).toLocaleString();
		
		if(primes.containsKey(victime.getUniqueId()))
		{
			attacker.sendMessage(config.MESSAGE_TARGET_HAS_BOUNTY);
			return;
		}
		
		primes.put(victime.getUniqueId(), new Prime(victime.getUniqueId(), attacker.getUniqueId(), price, date));
				
		bounty.getEconomy().withdrawPlayer(attacker, price);
				
		attacker.sendMessage(config.MESSAGE_BOUNTY_PLACED_ATTACKER.replace("%price%", String.valueOf(price)).replace("%victim%", victime.getName()));
		victime.sendMessage(config.MESSAGE_BOUNTY_PLACED_VICTIM.replace("%attacker%", attacker.getName()).replace("%price%", String.valueOf(price)));
				
		if(config.PUBLIC_MESSAGE)
		{
			Bukkit.broadcastMessage(config.MESSAGE_BOUNTY_PLACED_BROADCAST.replace("%attacker%", attacker.getName()).replace("%price%", String.valueOf(price)).replace("%victim%", victime.getName()));
		}
	}
	 
	public void takePrime(Player killeur, Player victime)
	{
		if(!(primes.containsKey(victime.getUniqueId()))) return; 
		
		double price = primes.get(victime.getUniqueId()).getPrice();
		Player attacker = Bukkit.getPlayer(primes.get(victime.getUniqueId()).getAttacker());
		
		bounty.getEconomy().depositPlayer(killeur, price);
				
		killeur.sendMessage(config.MESSAGE_BOUNTY_CLAIMED_KILLER.replace("%victim%", victime.getName()).replace("%price%", String.valueOf(price)));
		attacker.sendMessage(config.MESSAGE_BOUNTY_CLAIMED_ATTACKER.replace("%killer%", killeur.getName()).replace("%victim%", victime.getName()));
		
		primes.remove(victime.getUniqueId());
	}
	
	public void reloadConfiguration()
	{
		bounty.reloadConfig();
		config.loadConfiguration();
	}
	
}
