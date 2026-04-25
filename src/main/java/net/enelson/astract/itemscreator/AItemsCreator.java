package net.enelson.astract.itemscreator;

import org.bukkit.plugin.java.JavaPlugin;

import net.enelson.astract.itemscreator.commands.CommandManager;
import net.enelson.astract.itemscreator.utils.Utils;

public class AItemsCreator extends JavaPlugin {

	private static AItemsCreator plugin;
	private Utils utils;
	
	public void onEnable() {
		plugin = this;
		this.utils = new Utils(this);
		this.getCommand("aitemscreator").setExecutor(new CommandManager());
	}
	
	public static AItemsCreator getInstance() {
		return plugin;
	}
	
	public Utils getUtils() {
		return this.utils;
	}
}
