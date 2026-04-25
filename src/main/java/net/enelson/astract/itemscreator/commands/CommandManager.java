package net.enelson.astract.itemscreator.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import net.enelson.astract.itemscreator.AItemsCreator;
import net.enelson.astract.itemscreator.commands.subcommands.DeleteCommand;
import net.enelson.astract.itemscreator.commands.subcommands.GiveCommand;
import net.enelson.astract.itemscreator.commands.subcommands.ListCommand;
import net.enelson.astract.itemscreator.commands.subcommands.ReloadCommand;
import net.enelson.astract.itemscreator.commands.subcommands.SaveCommand;

public class CommandManager implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			return false;
		}

		if (args[0].equalsIgnoreCase("save")) {
			new SaveCommand(sender, this.removeElement(args, 0));
		}
		else if (args[0].equalsIgnoreCase("give")) {
			new GiveCommand(sender, this.removeElement(args, 0));
		}
		else if (args[0].equalsIgnoreCase("delete")) {
			new DeleteCommand(sender, this.removeElement(args, 0));
		}
		else if (args[0].equalsIgnoreCase("list")) {
			new ListCommand(sender);
		}
		else if (args[0].equalsIgnoreCase("reload")) {
			new ReloadCommand(sender);
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> completions = new ArrayList<>();
		// Команда /acustomblocks about доступна для всех
		if (args.length == 1 && args[0].equalsIgnoreCase("about")) {
			completions.add("about");
			return completions;
		}

		// Команды для администраторов
		if (sender.hasPermission("alootablechests.admin")) {
			if (args.length == 1) {
				if("save".startsWith(args[0]))
					completions.add("save");
				if("give".startsWith(args[0]))
					completions.add("give");
				if("delete".startsWith(args[0]))
					completions.add("delete");
				if("list".startsWith(args[0]))
					completions.add("list");
				if("reload".startsWith(args[0]))
					completions.add("reload");
				
			}
			else if(args[0].equalsIgnoreCase("give")) {
				if (args.length == 2) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						completions.add(p.getName());
					}
				}

				if (args.length == 3) {
					completions.addAll(AItemsCreator.getInstance().getUtils().getPresetsID());
				}

				if (args.length == 4) {
					completions.addAll(Arrays.asList("1", "4", "8", "16", "32", "64"));
				}

				if (args.length == 5) {
					completions.add("-s");
				}
			}
			else if (args.length == 2) {
				if(args[0].equalsIgnoreCase("save") && args[1].equals("")) {
					completions.add("itemId");
				}
				if(args[0].equalsIgnoreCase("delete")) {
					completions.addAll(AItemsCreator.getInstance().getUtils().getPresetsID());
				}
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
