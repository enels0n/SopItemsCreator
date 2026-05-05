package net.enelson.sopitemscreator.commands.subcommands;

import net.enelson.sopitemscreator.SopItemsCreator;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class SaveCommand {
    public SaveCommand(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("sopitemscreator.admin")) {
            sender.sendMessage("You do not have permission.");
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can be used only by a player.");
            return;
        }
        if (args.length != 1) {
            sender.sendMessage("/" + label + " save <itemId>");
            return;
        }

        String id = args[0].toLowerCase();
        Player player = (Player) sender;
        ItemStack item = player.getEquipment().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) {
            sender.sendMessage("Item in hand is empty.");
            return;
        }
        if (SopItemsCreator.getInstance().getUtils().getPresetItem(id) != null) {
            sender.sendMessage("An item with this ID already exists.");
            return;
        }

        SopItemsCreator.getInstance().getUtils().savePresetItem(id, item);
        sender.sendMessage("Item has been saved.");
    }
}
