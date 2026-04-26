package net.enelson.sopitemscreator;

import net.enelson.sopitemscreator.commands.CommandManager;
import net.enelson.sopitemscreator.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public final class SopItemsCreator extends JavaPlugin {
    private static SopItemsCreator instance;
    private Utils utils;

    @Override
    public void onEnable() {
        instance = this;
        this.utils = new Utils(this);

        CommandManager commandManager = new CommandManager();
        getCommand("sopitemscreator").setExecutor(commandManager);
        getCommand("sopitemscreator").setTabCompleter(commandManager);
    }

    public static SopItemsCreator getInstance() {
        return instance;
    }

    public Utils getUtils() {
        return this.utils;
    }
}
