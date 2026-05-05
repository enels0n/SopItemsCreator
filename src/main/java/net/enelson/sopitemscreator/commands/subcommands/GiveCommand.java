package net.enelson.sopitemscreator.commands.subcommands;

import net.enelson.sopitemscreator.SopItemsCreator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class GiveCommand {
    public GiveCommand(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("sopitemscreator.admin")) {
            sender.sendMessage("You do not have permission.");
            return;
        }
        if (args.length < 2 || args.length > 4) {
            sender.sendMessage("/" + label + " give <player> <itemId> [amount] [-s]");
            return;
        }

        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage("Player is not online.");
            return;
        }

        String id = args[1].toLowerCase();
        ItemStack item = SopItemsCreator.getInstance().getUtils().getPresetItem(id);
        if (item == null) {
            sender.sendMessage("Item with this id does not exist.");
            return;
        }

        if (args.length > 2) {
            item.setAmount(Integer.parseInt(args[2]));
        }

        if (player.getInventory().addItem(item).size() != 0) {
            player.getWorld().dropItem(player.getLocation(), item);
        }

        if (args.length != 4 || !args[3].equals("-s")) {
            sender.sendMessage("The item has been given to the player.");
        }
    }
}
