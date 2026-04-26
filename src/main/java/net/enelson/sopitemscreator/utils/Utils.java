package net.enelson.sopitemscreator.utils;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.enelson.sopitemscreator.SopItemsCreator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

public final class Utils {
    private final SopItemsCreator plugin;
    private final File file;
    private YamlConfiguration presets;

    public Utils(SopItemsCreator plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "presets.yml");
        reloadPresets();
    }

    public ItemStack getItem(String material, Object model, String name, List<String> lore, List<String> nbt) {
        ItemStack item = new ItemStack(Material.valueOf(material));
        item.setAmount(1);

        ItemMeta meta = item.getItemMeta();
        if (model != null) {
            String value = String.valueOf(model);
            CustomModelDataComponent cmd = meta.getCustomModelDataComponent();
            cmd.setStrings(List.of(value));
            meta.setCustomModelDataComponent(cmd);
        }
        if (name != null) {
            meta.setDisplayName(name);
        }
        if (lore != null) {
            meta.setLore(translateColorList(lore));
        }
        item.setItemMeta(meta);

        if (nbt != null) {
            setTags(item, nbt);
        }
        return item;
    }

    public ItemStack getPresetItem(String id) {
        return this.presets.getItemStack(id);
    }

    public void savePresetItem(String id, ItemStack item) {
        this.presets.set(id, item.clone());
        savePresets();
    }

    public ItemStack getHead(String value, String name) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        NBT.modifyComponents(head, nbt -> {
            ReadWriteNBT profileNbt = nbt.getOrCreateCompound("minecraft:profile");
            profileNbt.setUUID("id", UUID.fromString("4fbecd49-c7d4-4c18-8410-adf7a7348728"));
            ReadWriteNBT propertiesNbt = profileNbt.getCompoundList("properties").addCompound();
            propertiesNbt.setString("name", "textures");
            propertiesNbt.setString("value", value);
        });
        return head;
    }

    public String getComponent(ItemStack item, String key) {
        if (item == null || item.getType() == Material.AIR) {
            return null;
        }
        String value = NBT.get(item, nbt -> (String) nbt.getString(key));
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }

    public boolean removePresetItem(String id) {
        if (getPresetItem(id) == null) {
            return false;
        }
        this.presets.set(id, null);
        savePresets();
        return true;
    }

    public void reloadPresets() {
        checkFile();
        this.presets = YamlConfiguration.loadConfiguration(this.file);
    }

    public Set<String> getPresetsID() {
        return this.presets.getConfigurationSection("").getKeys(false);
    }

    private List<String> translateColorList(List<String> list) {
        List<String> translated = new ArrayList<String>();
        for (String line : list) {
            translated.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return translated;
    }

    private void setTags(ItemStack item, List<String> tags) {
        NBT.modify(item, nbt -> {
            for (String tag : tags) {
                String[] parts = tag.split("::", 2);
                if (parts.length == 2) {
                    nbt.setString(parts[0], parts[1]);
                }
            }
        });
    }

    private void savePresets() {
        checkFile();
        try {
            this.presets.save(this.file);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to save presets.yml", exception);
        }
    }

    private void checkFile() {
        if (!this.file.exists()) {
            this.plugin.saveResource("presets.yml", true);
        }
    }
}
