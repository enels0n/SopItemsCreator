package net.enelson.sopitemscreator.commands.subcommands;

import net.enelson.sopitemscreator.SopItemsCreator;
import org.bukkit.command.CommandSender;

public final class ReloadCommand {
    public ReloadCommand(CommandSender sender) {
        if (!sender.hasPermission("sopitemscreator.admin") && !sender.hasPermission("aitemscreator.admin")) {
            sender.sendMessage("You do not have permission.");
            return;
        }

        SopItemsCreator.getInstance().getUtils().reloadPresets();
        sender.sendMessage("SopItemsCreator reloaded.");
    }
}
