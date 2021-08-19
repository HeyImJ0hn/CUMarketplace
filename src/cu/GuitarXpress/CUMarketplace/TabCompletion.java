package cu.GuitarXpress.CUMarketplace;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabCompletion implements TabCompleter {
	Main plugin;

	public TabCompletion(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String lbl, String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.hasPermission("market.use")) {
				if (cmd.getName().equalsIgnoreCase("market")) {
					if (args.length == 1) {
						list.add("help");
						list.add("open");
						list.add("info");
						if (player.hasPermission("market.sell")) {
							list.add("sell");
						}
						if (player.hasPermission("market.remove")) {
							list.add("remove");
						}
					} else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("sell")) {
							list.add("price");
						} else {
							if (player.hasPermission("market.remove")) {
								if (args[0].equalsIgnoreCase("remove")) {
									list.add("confirm");
								}
							}
						}
					} else if (args.length == 3) {
						if (args[0].equalsIgnoreCase("sell")) {
							list.add("amount");
							list.add("confirm");
						}
					} else if (args.length == 4) {
						if (args[0].equalsIgnoreCase("sell")) {
							list.add("confirm");
						}
					}
				}
			}
		}
		return list;
	}
}