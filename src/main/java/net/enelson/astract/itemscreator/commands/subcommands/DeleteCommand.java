package net.enelson.astract.itemscreator.commands.subcommands;

import org.bukkit.command.CommandSender;

import net.enelson.astract.itemscreator.AItemsCreator;

public class DeleteCommand {

	public DeleteCommand(CommandSender sender, String[] args) {
		// /aitemscreator delete <itemId>
		if (!(sender.isOp()) && !sender.hasPermission("aitemscreator.admin"))
			return;

		if (args.length != 1) {
			sender.sendMessage("/aitemscreator delete <itemId>");
			return;
		}

		String id = args[0].toLowerCase();

		if (!AItemsCreator.getInstance().getUtils().removePresetItem(id)) {
			sender.sendMessage("Item with this id is not exist.");
			return;
		}
		
		sender.sendMessage("The item has been deleted.");
	}
}
