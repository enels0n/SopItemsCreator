package net.enelson.astract.itemscreator.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import net.enelson.astract.itemscreator.AItemsCreator;
import net.md_5.bungee.api.ChatColor;

public class ListCommand {

	public ListCommand(CommandSender sender) {
		// /aitemscreator list
		if (!(sender.isOp()) && !sender.hasPermission("aitemscreator.admin"))
			return;
		
		String mask = "&a%id% &7- &e%material% &7- &r%name%";
		sender.sendMessage("\nList of the items (id - material - name):");
		Bukkit.getScheduler().runTaskAsynchronously(AItemsCreator.getInstance(), new Runnable() {
			@Override
			public void run() {
				for(String id : AItemsCreator.getInstance().getUtils().getPresetsID()) {
					ItemStack item = AItemsCreator.getInstance().getUtils().getPresetItem(id);
					if(item == null) {
						continue;
					}
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', mask
							.replaceAll("%id%", id)
							.replaceAll("%material%", item.getType().name())
							.replaceAll("%name%", item.getItemMeta().getDisplayName())
							));
				}
			}
		});
	}
}
