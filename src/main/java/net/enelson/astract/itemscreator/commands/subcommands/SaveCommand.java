package net.enelson.astract.itemscreator.commands.subcommands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.enelson.astract.itemscreator.AItemsCreator;

public class SaveCommand {

	public SaveCommand(CommandSender sender, String[] args) {
		// /aitemscreator save <itemId>
		if(!(sender.isOp()) && !sender.hasPermission("aitemscreator.admin"))
			return;
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("This command can be used only by player.");
			return;
		}
		
		if(args.length != 1) {
			sender.sendMessage("/aitemscreator save <itemId>");
			return;
		}
		
		String id = args[0].toLowerCase();
		
		Player player = (Player)sender;
		
		ItemStack item = player.getEquipment().getItemInMainHand();
		
		if(item == null || item.getType().equals(Material.AIR)) {
			sender.sendMessage("Item in hand is null.");
			return;
		}
		
		if(AItemsCreator.getInstance().getUtils().getPresetItem(id) != null) {
			sender.sendMessage("An item with this ID is exist.");
			return;
		}
		
		AItemsCreator.getInstance().getUtils().savePresetItem(id, item);
		sender.sendMessage("Item have been saved.");
	}
}
