package fr.atom.bounty.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.atom.api.command.Command;
import fr.atom.api.command.CommandArgs;
import fr.atom.api.utils.PluginUtils;
import fr.atom.bounty.config.ConfigManager;
import fr.atom.bounty.manager.BountyManager;

public class PrimeCommand
{
	private BountyManager bounty = BountyManager.getINSTANCE();
	private ConfigManager config = ConfigManager.getINSTANCE();
	
	@Command(name = "prime")
	public void onCommand(CommandArgs args)
	{
		if(!args.isPlayer()) return;

		bounty.showPrime(args.getPlayer());
	}
	
	@Command(name = "prime.help")
	public void onHelp(CommandArgs args) 
	{
		if(!args.isPlayer()) return;
		
		Player player = args.getPlayer();
		
		player.sendMessage("");
		player.sendMessage("§8[§6§lPrime§8] §7- §e§lCommandes Disponibles:");
		player.sendMessage("§8➥ §6§l/prime §7-> §eouvre l'inventaire des primes");
		player.sendMessage("§8➥ §6§l/prime help §7-> §eaffiche la liste des commandes");
		player.sendMessage("§8➥ §6§l/prime add §e<joueur> <prix> §7-> §eajouter une prime à un joueur");
		if(player.isOp())
		{
			player.sendMessage("§c➥ §6§l/prime reload §7-> §ePermet de reload la configuration §7(§4Commande Admin§7)");
			player.sendMessage("");
			player.sendMessage("§7§lSupport -> " + PluginUtils.getSupportDiscord());
		}
		player.sendMessage("");

	}
	
	@Command(name = "prime.add")
	public void onAdd(CommandArgs args)
	{
		if(!args.isPlayer()) return;
		
		if(args.getArgs().length < 2)
		{
			args.getPlayer().sendMessage("§6§l/prime add §e<joueur> <prix>");
			return;
		}
		
		Player attacker = args.getPlayer();
		Player victime = Bukkit.getPlayer(args.getArgs(0));
		int price = Integer.valueOf(args.getArgs(1));
		
		if(price < config.MINIMUM_PRICE)
		{
			attacker.sendMessage(config.MINIMUM_MONEY.replace("%minimum_price%", String.valueOf(config.MINIMUM_PRICE))); //TODO CONFIG

			return;
		}
		
		if(bounty.getBounty().getEconomy().getBalance(attacker) < price)
		{
			attacker.sendMessage(config.MESSAGE_PLAYER_NO_MONEY);
			return;
		}
		
		bounty.addPrime(attacker, victime, price);
	}
	
	@Command(name = "prime.reload", permission = "prime.reload")
	public void onReload(CommandArgs args)
	{
		bounty.reloadConfiguration();
		args.getSender().sendMessage(config.CONFIGURATION_RELOADED);
	}
}
