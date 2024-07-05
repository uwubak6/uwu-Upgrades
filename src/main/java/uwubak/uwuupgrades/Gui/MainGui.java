package uwubak.uwuupgrades.Gui;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import uwubak.uwuupgrades.Utils.GuiLoader;
import uwubak.uwuupgrades.Uwu_Upgrades;

import java.util.ArrayList;
import java.util.UUID;

public class MainGui {

    private final Uwu_Upgrades plugin;

    public MainGui(Uwu_Upgrades plugin) {
        this.plugin = plugin;
    }

    public ArrayList<UUID> players = new ArrayList<>();


    public Gui ExchangeGUI(Player player) {

        Gui gui = Gui.gui()
                .title(Component.text(""))
                .rows(5)
                .create();

        gui.setCloseGuiAction(event -> {
            if (players.contains(player.getUniqueId())) {
                players.remove(player.getUniqueId());
            }

            GuiLoader guiloader = new GuiLoader(plugin);
            if (guiloader.playeritems.containsKey(player.getUniqueId())) {
                guiloader.playeritems.remove(player.getUniqueId());
            }
        });

        GuiLoader loader = new GuiLoader(plugin);
        loader.Loader(gui, player);


        return gui;
    }

}
