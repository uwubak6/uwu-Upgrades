package uwubak.uwuupgrades.Utils;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import uwubak.uwuupgrades.Gui.MainGui;
import uwubak.uwuupgrades.Uwu_Upgrades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GuiLoader {

    private final Uwu_Upgrades plugin;

    public HashMap<String, List<ItemStack>> items_in = new HashMap<>();

    public HashMap<UUID, List<ItemStack>> playeritems = new HashMap<>();

    public HashMap<String, ItemStack> reward_item = new HashMap<>();


    public GuiLoader(Uwu_Upgrades plugin) {
        this.plugin = plugin;
    }


    public void Loader(Gui gui, Player player) {

        for (String key2 : plugin.getConfig().getConfigurationSection("gui.items").getKeys(false)) {
            ConfigurationSection section1 = plugin.getConfig().getConfigurationSection("gui.items").getConfigurationSection(key2);


                String material = section1.getString("material");

                String display_name = section1.getString("display-name");

                String animation = section1.getString("animation");

                String upgrade = section1.getString("upgrade");


                int slot = section1.getInt("slot");

                ItemStack item = new ItemStack(Material.getMaterial(material));
                ItemMeta meta = item.getItemMeta();

                List<String> lore = new ArrayList<>();
                for (String lores : section1.getStringList("lore")) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', lores));
                }

                if (plugin.getConfig().getConfigurationSection("gui.items." + key2 + ".enchants") != null) {


                    for (String enchants : plugin.getConfig().getConfigurationSection("gui.items." + key2 + ".enchants").getKeys(false)) {

                        ConfigurationSection enchantsection = plugin.getConfig().getConfigurationSection("gui.items." + key2 + ".enchants").getConfigurationSection(enchants);

                        String enchant = enchantsection.getString("enchant");
                        int value = enchantsection.getInt("level");
                        meta.addEnchant(Enchantment.getByName(enchant), value, true);


                    }
                }

                for (String flags : plugin.getConfig().getStringList("gui.items." + key2 + ".item-flags")) {
                    if (flags != null) {
                        meta.addItemFlags(ItemFlag.valueOf(flags));
                    }
                }


                meta.setLore(lore);
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', display_name));
                item.setItemMeta(meta);

                gui.setItem(slot, ItemBuilder.from(item).asGuiItem(event -> {
                    event.setCancelled(true);

                    if (upgrade.equalsIgnoreCase("true")) {

                        List<ItemStack> test = new ArrayList<>();

                        if (playeritems.containsKey(player.getUniqueId())) {
                            playeritems.put(player.getUniqueId(), test);
                            playeritems.get(player.getUniqueId()).clear();
                        }
                        if (!playeritems.containsKey(player.getUniqueId())) {
                            playeritems.put(player.getUniqueId(), test);
                            playeritems.get(player.getUniqueId()).clear();
                        }

                        System.out.println("clicked lol");




                        for (String key10 : plugin.getConfig().getConfigurationSection("gui.items").getKeys(false)) {


                            List<ItemStack> chitems1 = new ArrayList<>();

                            items_in.put(key10, chitems1);

                            items_in.get(key10).clear();

                            for (String key11 : plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".upgrades.items-in").getKeys(false)) {
                                ConfigurationSection section = plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".upgrades.items-in").getConfigurationSection(key11);


                                String material_in = section.getString("material");

                                String display_name_in = section.getString("display-name");

                                ItemStack item_in = new ItemStack(Material.getMaterial(material_in));
                                ItemMeta meta_in = item_in.getItemMeta();

                                List<String> lore_in = new ArrayList<>();
                                for (String lores : section.getStringList("lore")) {
                                    lore_in.add(ChatColor.translateAlternateColorCodes('&', lores));
                                }

                                if (plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".upgrades.items-in." + key11 + ".enchants") != null) {


                                    for (String enchants : plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".upgrades.items-in." + key11 + ".enchants").getKeys(false)) {

                                        ConfigurationSection enchantsection = plugin.getConfig().getConfigurationSection("gui.items." + key10 + ".upgrades.items-in." + key11 + ".enchants").getConfigurationSection(enchants);

                                        String enchant = enchantsection.getString("enchant");
                                        int value = enchantsection.getInt("level");
                                        meta_in.addEnchant(Enchantment.getByName(enchant), value, true);


                                    }
                                }

                                for (String flags : plugin.getConfig().getStringList("gui.items." + key10 + ".upgrades.items-in." + key11 + ".item-flags")) {
                                    if (flags != null) {
                                        meta_in.addItemFlags(ItemFlag.valueOf(flags));
                                    }
                                }


                                meta_in.setLore(lore_in);
                                meta_in.setDisplayName(ChatColor.translateAlternateColorCodes('&', display_name_in));
                                item_in.setItemMeta(meta_in);

                                items_in.get(key10).add(item_in);



                            }
                        }


                        items_in.get(key2).forEach(each -> {
                            if (player.getInventory().containsAtLeast(each, 1)) {
                                playeritems.get(player.getUniqueId()).add(each);
                                System.out.println(playeritems.get(player.getUniqueId()));
                            }


                        });


                        if (playeritems.get(player.getUniqueId()).size() >= items_in.get(key2).size()) {
                            player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                            player.sendMessage("pomyslnie wymieniono przedmioty!");
                            playeritems.remove(player.getUniqueId());
                            gui.close(player);
                            return;
                        } else {
                            player.sendMessage("nie posiadasz wymaganych przedmiotow!");
                        }

                    }
                }));


                if (animation.equalsIgnoreCase("true")) {
                    AnimationRunnable runnable = new AnimationRunnable(plugin);
                    runnable.Animations(gui, slot, key2, player, upgrade);
                } else {
                    ///
                }

        }

    }



}
