package net.enelson.sopitemscreator.commands.subcommands;

import net.enelson.sopitemscreator.SopItemsCreator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public final class ListCommand {
    public ListCommand(CommandSender sender) {
        if (!sender.hasPermission("sopitemscreator.admin")) {
            sender.sendMessage("You do not have permission.");
            return;
        }

        String mask = "&a%id% &7- &e%material% &7- &r%name%";
        sender.sendMessage("\nList of the items (id - material - name):");
        Bukkit.getScheduler().runTaskAsynchronously(SopItemsCreator.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (String id : SopItemsCreator.getInstance().getUtils().getPresetsID()) {
                    ItemStack item = SopItemsCreator.getInstance().getUtils().getPresetItem(id);
                    if (item == null) {
                        continue;
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes(
                        '&',
                        mask.replace("%id%", id)
                            .replace("%material%", item.getType().name())
                            .replace("%name%", item.getItemMeta().getDisplayName())
                    ));
                }
            }
        });
    }
}
