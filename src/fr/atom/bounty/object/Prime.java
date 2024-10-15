package fr.atom.bounty.object;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Prime 
{
	public UUID attacker;
	public UUID victime;
	
	public double price;
	
	public String date;
	
	public Prime(UUID victime, UUID attacker, double price, String date)
	{
		this.victime = victime;
		this.attacker = attacker;
		this.price = price;
		this.date = date;
	}
	
	
}
