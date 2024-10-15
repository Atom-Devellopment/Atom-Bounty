package fr.atom.bounty.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import fr.atom.bounty.Bounty;
import lombok.Getter;

public class ConfigManager 
{
    @Getter public static ConfigManager INSTANCE;
    public ConfigManager(){ INSTANCE = this; }

    // INVENTORY
    public String INVENTORY_NAME;
    public String CONTOUR;
    public String CONTOUR_COLOR;
    public List<Integer> PRIME_SLOTS;
    public int ITEMS_PER_PAGE;
    public String PRIME_PROFILE_ICON;
    public String PRIME_PROFILE_NAME;
    public List<String> PRIME_PROFILE_LORE;
    public String ARROW_LEFT_ICON;
    public String ARROW_LEFT_NAME;
    public int ARROW_LEFT_SLOT;
    public String ARROW_RIGHT_ICON;
    public String ARROW_RIGHT_NAME;
    public int ARROW_RIGHT_SLOT;
    public String PLAYER_PROFILE_ICON;
    public String PLAYER_PROFILE_NAME;
    public int PLAYER_PROFILE_SLOT;
    public List<String> PLAYER_PROFILE_LORE;
    public String MESSAGE_HAS_PRIME;
    public String MESSAGE_NO_PRIME;

    // OPTIONS
    public int MINIMUM_PRICE;
    public boolean PUBLIC_MESSAGE;

    // MESSAGES
    public String MESSAGE_PLAYER_NO_MONEY;
    public String MESSAGE_TARGET_HAS_BOUNTY;
    public String MESSAGE_BOUNTY_PLACED_ATTACKER;
    public String MESSAGE_BOUNTY_PLACED_VICTIM;
    public String MESSAGE_BOUNTY_PLACED_BROADCAST;
    public String MESSAGE_BOUNTY_CLAIMED_KILLER;
    public String MESSAGE_BOUNTY_CLAIMED_ATTACKER;
    public String MINIMUM_MONEY;
    public String CONFIGURATION_RELOADED;

    public void loadConfiguration()
    {
        FileConfiguration config = Bounty.getINSTANCE().getConfig();

        // INVENTORY
        INVENTORY_NAME = config.getString("inventory.name");
        CONTOUR = config.getString("inventory.contour");
        CONTOUR_COLOR = config.getString("inventory.contour_color");
        PRIME_SLOTS = config.getIntegerList("inventory.prime_slot");
        ITEMS_PER_PAGE = config.getInt("inventory.items_per_page");
        PRIME_PROFILE_ICON = config.getString("inventory.prime_profile.icon");
        PRIME_PROFILE_NAME = config.getString("inventory.prime_profile.name").replace("&", "§");
        PRIME_PROFILE_LORE = config.getStringList("inventory.prime_profile.lore");
        PRIME_PROFILE_LORE.replaceAll(string -> string.replace("&", "§"));

        ARROW_LEFT_ICON = config.getString("inventory.arrow_left.icon");
        ARROW_LEFT_NAME = config.getString("inventory.arrow_left.name").replace("&", "§");
        ARROW_LEFT_SLOT = config.getInt("inventory.arrow_left.slot");

        ARROW_RIGHT_ICON = config.getString("inventory.arrow_right.icon");
        ARROW_RIGHT_NAME = config.getString("inventory.arrow_right.name").replace("&", "§");
        ARROW_RIGHT_SLOT = config.getInt("inventory.arrow_right.slot");

        PLAYER_PROFILE_ICON = config.getString("inventory.player_profile.icon");
        PLAYER_PROFILE_NAME = config.getString("inventory.player_profile.name").replace("&", "§");
        PLAYER_PROFILE_SLOT = config.getInt("inventory.player_profile.slot");
        PLAYER_PROFILE_LORE = config.getStringList("inventory.player_profile.lore");
        PLAYER_PROFILE_LORE.replaceAll(string -> string.replace("&", "§"));
        MESSAGE_HAS_PRIME = config.getString("inventory.player_profile.message-if-has-prime").replace("&", "§");
        MESSAGE_NO_PRIME = config.getString("inventory.player_profile.message-if-not-have-prime").replace("&", "§");

        // OPTIONS
        MINIMUM_PRICE = config.getInt("options.minimum_price");
        PUBLIC_MESSAGE = config.getBoolean("options.public_message");

        // MESSAGES
        MESSAGE_PLAYER_NO_MONEY = config.getString("messages.player_dont_have_enought_money").replace("&", "§");
        MESSAGE_TARGET_HAS_BOUNTY = config.getString("messages.target_have_already_prime_on_is_head").replace("&", "§");
        MESSAGE_BOUNTY_PLACED_ATTACKER = config.getString("messages.bounty_placed_attacker").replace("&", "§");
        MESSAGE_BOUNTY_PLACED_VICTIM = config.getString("messages.bounty_placed_victim").replace("&", "§");
        MESSAGE_BOUNTY_PLACED_BROADCAST = config.getString("messages.bounty_placed_broadcast").replace("&", "§");
        MESSAGE_BOUNTY_CLAIMED_KILLER = config.getString("messages.bounty_claimed_killer").replace("&", "§");
        MESSAGE_BOUNTY_CLAIMED_ATTACKER = config.getString("messages.bounty_claimed_attacker").replace("&", "§");
        MINIMUM_MONEY = config.getString("messages.minimum_money").replace("&", "§");
        CONFIGURATION_RELOADED = config.getString("messages.reload_configuration").replace("&", "§");
    }
}
