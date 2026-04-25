package net.enelson.astract.itemscreator.commands.subcommands;

import org.bukkit.command.CommandSender;

import net.enelson.astract.itemscreator.AItemsCreator;

public class ReloadCommand {

	public ReloadCommand(CommandSender sender) {
		// /aitemscreator reload
		if (!(sender.isOp()) && !sender.hasPermission("aitemscreator.admin"))
			return;
		
		AItemsCreator.getInstance().getUtils().reloadPresets();
		sender.sendMessage("The plugin has been reloaded.");
	}
}
