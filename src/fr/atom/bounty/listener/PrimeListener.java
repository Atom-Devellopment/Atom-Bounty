package fr.atom.bounty.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.atom.bounty.manager.BountyManager;

public class PrimeListener implements Listener 
{
	private BountyManager bounty = BountyManager.getINSTANCE();	
	
	@EventHandler
	public void onKill(EntityDeathEvent event)
	{
		if(!(event.getEntity() instanceof Player)) return;
		if(!(event.getEntity().getKiller() instanceof Player)) return;
		
		Player victime = (Player) event.getEntity();
		Player killer = victime.getKiller();
		
		bounty.takePrime(killer, victime);
	}
}
