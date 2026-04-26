package net.enelson.sopitemscreator.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.enelson.sopitemscreator.SopItemsCreator;
import net.enelson.sopitemscreator.commands.subcommands.DeleteCommand;
import net.enelson.sopitemscreator.commands.subcommands.GiveCommand;
import net.enelson.sopitemscreator.commands.subcommands.ListCommand;
import net.enelson.sopitemscreator.commands.subcommands.ReloadCommand;
import net.enelson.sopitemscreator.commands.subcommands.SaveCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public final class CommandManager implements CommandExecutor, TabCompleter {
    private static final String ADMIN_PERMISSION = "sopitemscreator.admin";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("/" + label + " save <itemId>");
            sender.sendMessage("/" + label + " give <player> <itemId> [amount] [-s]");
            sender.sendMessage("/" + label + " delete <itemId>");
            sender.sendMessage("/" + label + " list");
            sender.sendMessage("/" + label + " reload");
            return true;
        }

        if (args[0].equalsIgnoreCase("save")) {
            new SaveCommand(sender, label, removeElement(args, 0));
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            new GiveCommand(sender, label, removeElement(args, 0));
            return true;
        }
        if (args[0].equalsIgnoreCase("delete")) {
            new DeleteCommand(sender, label, removeElement(args, 0));
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            new ListCommand(sender);
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            new ReloadCommand(sender);
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<String>();
        if (!sender.hasPermission(ADMIN_PERMISSION) && !sender.hasPermission("aitemscreator.admin")) {
            return completions;
        }

        if (args.length == 1) {
            if ("save".startsWith(args[0])) {
                completions.add("save");
            }
            if ("give".startsWith(args[0])) {
                completions.add("give");
            }
            if ("delete".startsWith(args[0])) {
                completions.add("delete");
            }
            if ("list".startsWith(args[0])) {
                completions.add("list");
            }
            if ("reload".startsWith(args[0])) {
                completions.add("reload");
            }
            return completions;
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (args.length == 2) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            } else if (args.length == 3) {
                completions.addAll(SopItemsCreator.getInstance().getUtils().getPresetsID());
            } else if (args.length == 4) {
                completions.addAll(Arrays.asList("1", "4", "8", "16", "32", "64"));
            } else if (args.length == 5) {
                completions.add("-s");
            }
            return completions;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("save") && args[1].isEmpty()) {
                completions.add("itemId");
            }
            if (args[0].equalsIgnoreCase("delete")) {
                completions.addAll(SopItemsCreator.getInstance().getUtils().getPresetsID());
            }
        }

        return completions;
    }

    private String[] removeElement(String[] arr, int index) {
        String[] copyArray = new String[arr.length - 1];
        System.arraycopy(arr, 0, copyArray, 0, index);
        System.arraycopy(arr, index + 1, copyArray, index, arr.length - index - 1);
        return copyArray;
    }
}
