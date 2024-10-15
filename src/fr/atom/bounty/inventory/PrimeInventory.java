package fr.atom.bounty.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import fr.atom.api.inventory.FastInv;
import fr.atom.api.inventory.ItemBuilder;
import fr.atom.bounty.config.ConfigManager;
import fr.atom.bounty.manager.BountyManager;
import fr.atom.bounty.object.Prime;

public class PrimeInventory extends FastInv
{
	private BountyManager bounty = BountyManager.getINSTANCE();
	private ConfigManager config = ConfigManager.getINSTANCE();
	
	private int page;
	private int ITEMS_PER_PAGE = config.ITEMS_PER_PAGE;
		
	public PrimeInventory(Player player, int page, String invName) 
	{
		super(9*6, invName +" (Page "+ page + ")");
		
		this.page = page;
        int start = (page - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, bounty.getPrimes().size());
		
        this.addDecoration();
	
		List<UUID> primeKeys = new ArrayList<>(bounty.getPrimes().keySet());
		
		for (int i = start; i < end; i++) {
		    UUID victimId = primeKeys.get(i);
		    Prime prime = bounty.getPrimes().get(victimId);
		    
		    if (prime == null) return;
		    
		    Player victim = Bukkit.getPlayer(prime.getVictime());
	        Player attacker = Bukkit.getPlayer(prime.getAttacker());
	        
	        if (victim == null || attacker == null) return;
	        
	        List<String> lores = config.PRIME_PROFILE_LORE;
			lores.replaceAll(lore -> lore.replace("&", "§").replace("%price%", String.valueOf(prime.getPrice()))
        			.replace("%author%", attacker.getName())
        			.replace("%date%", prime.getDate()));
	        	        
	        int slot = config.PRIME_SLOTS.get(i);
	        
	        setItem(slot,new ItemBuilder(Material.valueOf(config.PRIME_PROFILE_ICON))
	                .name(config.PRIME_PROFILE_NAME.replace("%name%", victim.getName()))
	                .addLore(lores)
	                .build());
		}
		
        this.addFooter(player);
	}
	
	private void addDecoration()
	{
		ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.valueOf(config.CONTOUR_COLOR).getData());
		int[] choice = config.CONTOUR.equals("CORNER") ? getCorners() : config.CONTOUR.equals("BORDER") ? getBorders() : null;		
		setItems(choice, new ItemBuilder(nop).name(" ").build());
	}
	
	private void addFooter(Player player)
	{
        UUID uuid = player.getUniqueId();
        String hasPrime = bounty.getPrimes().containsKey(uuid) ? config.MESSAGE_HAS_PRIME.replace("%price%", String.valueOf(bounty.getPrimes().get(uuid).getPrice())): config.MESSAGE_NO_PRIME;
        
        List<String> lores = config.PLAYER_PROFILE_LORE;
		lores.replaceAll(lore -> lore.replace("&", "§")
				.replace("%money%", String.valueOf(bounty.getBounty().getEconomy().getBalance(player))));

        setItem(config.PLAYER_PROFILE_SLOT, new ItemBuilder(Material.valueOf(config.PLAYER_PROFILE_ICON))
        		.name(config.PLAYER_PROFILE_NAME.replace("%player%", player.getName()))
        		.addLore(lores)
        		.build());
        
		setItem(config.ARROW_LEFT_SLOT, new ItemBuilder(Material.valueOf(config.ARROW_LEFT_ICON)) 
                .name(config.ARROW_LEFT_NAME).build(), event -> 
				{
                   if(page <= 1) return;
                   new PrimeInventory(player, page - 1, config.INVENTORY_NAME).open(player);
                });
        setItem(config.ARROW_RIGHT_SLOT, new ItemBuilder(Material.valueOf(config.ARROW_RIGHT_ICON))
                .name(config.ARROW_RIGHT_NAME).build(), event -> 
        		{
                	int maxPage = bounty.getPrimes().size() == 0 ? 1: (int) Math.ceil((double) bounty.getPrimes().size() / ITEMS_PER_PAGE);;
                    if(page == maxPage ) return;
                    new PrimeInventory(player, page + 1, config.INVENTORY_NAME).open(player);
                });
	}
	

}
