package uwubak.uwuupgrades.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uwubak.uwuupgrades.Uwu_Upgrades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimationsLoader {

    private final Uwu_Upgrades plugin;

    public HashMap<String, List<ItemStack>> animation_items = new HashMap<>();

    public HashMap<String, ItemStack[]> animation = new HashMap<>();


    public AnimationsLoader(Uwu_Upgrades plugin) {
        this.plugin = plugin;
    }

    public void AnimationsLoad(String name) {


        for (String key10 : plugin.getConfig().getConfigurationSection("gui.items").getKeys(false)) {

            List<ItemStack> chitems = new ArrayList<>();

            animation_items.put(key10, chitems);

            animation_items.get(key10).clear();

            for (String key11 : plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".animations.items").getKeys(false)) {
                ConfigurationSection section = plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".animations.items").getConfigurationSection(key11);


                String material = section.getString("material");

                String display_name = section.getString("display-name");

                ItemStack item = new ItemStack(Material.getMaterial(material));
                ItemMeta meta = item.getItemMeta();

                List<String> lore = new ArrayList<>();
                for (String lores : section.getStringList("lore")) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', lores));
                }

                if (plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".animations.items." + key11 + ".enchants") != null) {


                    for (String enchants : plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".animations.items." + key11 + ".enchants").getKeys(false)) {

                        ConfigurationSection enchantsection = plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".animations.items." + key11 + ".enchants").getConfigurationSection(enchants);

                        String enchant = enchantsection.getString("enchant");
                        int value = enchantsection.getInt("level");
                        meta.addEnchant(Enchantment.getByName(enchant), value, true);


                    }
                }

                for (String flags : plugin.getConfig().getStringList("gui.items." + key10 + ".animations.items." + key11 + ".item-flags")) {
                    if (flags != null) {
                        meta.addItemFlags(ItemFlag.valueOf(flags));
                    }
                }


                meta.setLore(lore);
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', display_name));
                item.setItemMeta(meta);

                animation_items.get(key10).add(item);

            }

        }

    }


}
