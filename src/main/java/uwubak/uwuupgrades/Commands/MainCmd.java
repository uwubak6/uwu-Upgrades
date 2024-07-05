package uwubak.uwuupgrades.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uwubak.uwuupgrades.Gui.MainGui;
import uwubak.uwuupgrades.Uwu_Upgrades;

public class MainCmd implements CommandExecutor {

    private final Uwu_Upgrades plugin;

    public MainCmd(Uwu_Upgrades plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player player = (Player) sender;

        MainGui gui = new MainGui(plugin);
        gui.players.add(player.getUniqueId());
        gui.ExchangeGUI(player).open(player);

        return true;
    }
}
