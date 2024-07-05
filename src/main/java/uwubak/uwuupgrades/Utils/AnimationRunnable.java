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

import java.util.*;

public class AnimationRunnable {

    private final Uwu_Upgrades plugin;

    public AnimationRunnable(Uwu_Upgrades plugin) {
        this.plugin = plugin;
    }

    private HashMap<UUID, Integer> index = new HashMap<>();

    public void Animations(Gui gui, int slot, String name, Player player, String upgrade) {

        AnimationsLoader loader = new AnimationsLoader(plugin);
        GuiLoader guiloader = new GuiLoader(plugin);
        MainGui GUI = new MainGui(plugin);
        loader.AnimationsLoad(name);



        BukkitTask runnable = new BukkitRunnable() {
            @Override
            public void run() {

                gui.setCloseGuiAction(event -> {
                    plugin.getServer().getScheduler().cancelTasks(plugin);
                });

                Random r = new Random();


                gui.update();

                gui.setItem(slot, ItemBuilder.from(loader.animation_items.get(name).get(r.nextInt(loader.animation_items.get(name).size()))).asGuiItem(event -> {
                    event.setCancelled(true);


                    if (upgrade.equalsIgnoreCase("true")) {

                        List<ItemStack> test = new ArrayList<>();

                        if (guiloader.playeritems.containsKey(player.getUniqueId())) {
                            guiloader.playeritems.put(player.getUniqueId(), test);
                            guiloader.playeritems.get(player.getUniqueId()).clear();
                        }
                        if (!guiloader.playeritems.containsKey(player.getUniqueId())) {
                            guiloader.playeritems.put(player.getUniqueId(), test);
                            guiloader.playeritems.get(player.getUniqueId()).clear();
                        }

                        System.out.println("clicked lol");




                        for (String key10 : plugin.getConfig().getConfigurationSection("gui.items").getKeys(false)) {


                            List<ItemStack> chitems1 = new ArrayList<>();

                            guiloader.items_in.put(key10, chitems1);

                            guiloader.items_in.get(key10).clear();

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

                                guiloader.items_in.get(key10).add(item_in);



                            }
                        }


                        guiloader.items_in.get(name).forEach(each -> {
                            if (player.getInventory().containsAtLeast(each, 1)) {
                                guiloader.playeritems.get(player.getUniqueId()).add(each);
                                System.out.println(guiloader.playeritems.get(player.getUniqueId()));
                            }


                        });


                        if (guiloader.playeritems.get(player.getUniqueId()).size() >= guiloader.items_in.get(name).size()) {
                            player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                            player.sendMessage("pomyslnie wymieniono przedmioty!");
                            guiloader.playeritems.remove(player.getUniqueId());
                            gui.close(player);
                            return;
                        } else {
                            player.sendMessage("nie posiadasz wymaganych przedmiotow!");
                        }

                    }



                }));
                gui.update();


                gui.update();
                System.out.println("working good");


            }
        }.runTaskTimer(plugin, 20, 20);

    }


}
