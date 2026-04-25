package net.enelson.astract.itemscreator.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.jetbrains.annotations.NotNull;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import net.enelson.astract.itemscreator.AItemsCreator;
import net.md_5.bungee.api.ChatColor;

public class Utils {
	
	private File file;
	private YamlConfiguration presets;
	private AItemsCreator plugin;
	
	public Utils(AItemsCreator plugin) {
		this.plugin = plugin;
		this.file = new File(plugin.getDataFolder(), "presets.yml");
		this.reloadPresets();
	}
	
	public ItemStack getItem(@NotNull String material, Object model, String name, List<String> lore, List<String> nbt) {
		ItemStack item = new ItemStack(Material.valueOf(material));
		item.setAmount(1);
		
		ItemMeta meta = item.getItemMeta();
		
		if(model != null) {
			String m = String.valueOf(model);
			CustomModelDataComponent cmd = meta.getCustomModelDataComponent(); // 1.21+
			cmd.setStrings(List.of(m));
			meta.setCustomModelDataComponent(cmd);
		}
		if(name != null)
			meta.setDisplayName(name);
		if(lore != null)
			meta.setLore(this.translateColorList(lore));
		item.setItemMeta(meta);
		
		if(nbt != null)
			this.setTags(item, nbt);
		
		return item;
	}
	
	public ItemStack getPresetItem(String id) {
		return this.presets.getItemStack(id);
	}
	
	public void savePresetItem(String id, ItemStack item) {
		this.presets.set(id, item.clone());
		this.savePresets();
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
		if(item == null || item.getType().equals(Material.AIR))
			return null;
		String value = NBT.get(item, nbt -> (String) nbt.getString(key));
		if(value == null || value.equals(""))
			return null;
		return value;
	}
	
	private List<String> translateColorList(List<String> list) {
		List<String> newList = new ArrayList<String>();
		for (String l : list) {
			newList.add(ChatColor.translateAlternateColorCodes('&', l));
		}
		return newList;
	}
	
	private void setTags(ItemStack item, List<String> tags) {
		NBT.modify(item, nbt -> {
			for(String t : tags) {
				nbt.setString(t.split("::")[0], t.split("::")[1]);
			}
		});
	}
	
	public boolean removePresetItem(String id) {
		if(this.getPresetItem(id) == null)
			return false;
		
		this.presets.set(id, null);
		this.savePresets();
		return true;
	}
	
	public void reloadPresets() {
		this.checkFile();
		this.presets = YamlConfiguration.loadConfiguration(file);
	}
	
	private void savePresets() {
		this.checkFile();
		try {
			this.presets.save(file);
		} catch (IOException e) {
		}
	}
	
	public Set<String> getPresetsID() {
		return this.presets.getConfigurationSection("").getKeys(false);
	}
	
	private void checkFile() {
		if (!file.exists()) plugin.saveResource("presets.yml", true);
	}
}
