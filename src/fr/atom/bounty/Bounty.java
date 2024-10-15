package fr.atom.bounty;

import org.bukkit.plugin.RegisteredServiceProvider;

import fr.atom.api.AtomApi;
import fr.atom.bounty.config.ConfigManager;
import fr.atom.bounty.manager.BountyManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;


public class Bounty extends AtomApi
{
	@Getter public static Economy economy;
	@Getter public static Bounty INSTANCE;

	
	public Bounty() 
	{
		super("Bounty", 22891);		
	}
	
	@Override
	public void enable() 
	{
	    INSTANCE = this; 
	    
	    this.saveDefaultConfig();
	    ConfigManager config = new ConfigManager();
	    config.loadConfiguration();
	    
	    this.setupEconomy();
	    
	    new BountyManager();
	}


	
	@Override
	public void disable() 
	{
		getLogger().info("---------------------------------------");
		getLogger().info(this.getName() + ": OFF");
		getLogger().info("---------------------------------------");
	}
	
	 public boolean setupEconomy() 
	 {
		 if (getServer().getPluginManager().getPlugin("Vault") == null) {return false;}
		 RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		 
	     if (rsp == null) {return false;}
	     economy = rsp.getProvider();
	     
	     return economy != null;
	 }
}
