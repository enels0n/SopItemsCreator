package net.enelson.astract.itemscreator.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.enelson.astract.itemscreator.AItemsCreator;

public class GiveCommand {

	public GiveCommand(CommandSender sender, String[] args) {
		// /aitemscreator give <player> <itemId> [amount]
		if (!(sender.isOp()) && !sender.hasPermission("aitemscreator.admin"))
			return;

		if (args.length < 2 || args.length > 4) {
			sender.sendMessage("/aitemscreator give <player> <itemId> [amount [amount]] ");
			return;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		if (player == null) {
			sender.sendMessage("Player is not online.");
			return;
		}

		String id = args[1].toLowerCase();

		ItemStack item = AItemsCreator.getInstance().getUtils().getPresetItem(id);
		if (item == null) {
			sender.sendMessage("Item with this id is not exist.");
			return;
		}
		
		if(args.length > 2) {
			item.setAmount(Integer.parseInt(args[2]));
		}
		
		if(player.getInventory().addItem(item).size() != 0) {
			player.getWorld().dropItem(player.getLocation(), item);
		}
		
		if(args.length != 4 || !args[3].equals("-s")) {
			sender.sendMessage("The item has been given to the player.");
		}
		
	}
}
