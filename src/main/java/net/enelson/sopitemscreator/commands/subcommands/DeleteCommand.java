package net.enelson.sopitemscreator.commands.subcommands;

import net.enelson.sopitemscreator.SopItemsCreator;
import org.bukkit.command.CommandSender;

public final class DeleteCommand {
    public DeleteCommand(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("sopitemscreator.admin") && !sender.hasPermission("aitemscreator.admin")) {
            sender.sendMessage("You do not have permission.");
            return;
        }
        if (args.length != 1) {
            sender.sendMessage("/" + label + " delete <itemId>");
            return;
        }

        String id = args[0].toLowerCase();
        if (!SopItemsCreator.getInstance().getUtils().removePresetItem(id)) {
            sender.sendMessage("Item with this id does not exist.");
            return;
        }

        sender.sendMessage("The item has been deleted.");
    }
}
