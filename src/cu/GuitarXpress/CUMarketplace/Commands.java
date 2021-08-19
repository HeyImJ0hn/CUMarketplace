package cu.GuitarXpress.CUMarketplace;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.milkbowl.vault.economy.Economy;

public class Commands implements CommandExecutor {
	Main plugin;

	public Commands(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;
		Economy economy = Main.getEconomy();

		if (player.hasPermission("marketplace.use")) {
			if (cmd.getName().equalsIgnoreCase("market")) {
				if (args.length == 0) {
					MarketplaceManager.openMarketplace(player);
					return true;
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("help")) {
						player.sendMessage(
								prefix() + "§eHelpful Commands:\n" 
						+ "§6/market §7- §eOpens Marketplace Interface.\n"
						+ "§6/market open§7- §eOpens Marketplace Interface.\n"
						+ "§6/market info§7- §eDisplays plugin info.");
						if (player.hasPermission("marketplace.sell")) {
							player.sendMessage(
									"§6/market sell <price> §7- §eSells ALL of the item the player is currently holding for specific price.\n"
											+ "§6/market sell <price> <amount> §7- §eSells specific amount of the item the player is currently holding for specified price.");
						}
						if (player.hasPermission("marketplace.remove")) {
							player.sendMessage("§6/market remove §7- §eRemoves ALL your listings.");
						}

						return true;
					} else if (args[0].equalsIgnoreCase("open")) {
						MarketplaceManager.openMarketplace(player);
						return true;
					} else if (args[0].equalsIgnoreCase("info")) {
						player.sendMessage(prefix() + "§ePlugin being developed by §3GuitarXpress\n"
								+ "§e- Version 1.0.1\n"
								+ "Please submit any bugs/feedback on §6forums or §6discord.\n"
								+ "§eFor help use §6/market help");
						return true;
					} else {
						player.performCommand("market help");
					}
					if (player.hasPermission("marketplace.remove")) {
						if (args[0].equalsIgnoreCase("remove")) {
							player.sendMessage(prefix() + "§eAre you sure you want to remove all your listings?\n"
									+ "Confirm with §b/market remove confirm");
							return true;
						}
					}

				} else if (args.length == 2) {
					if (player.hasPermission("marketplace.sell")) {
						if (args[0].equalsIgnoreCase("sell")) {
							ItemStack item = player.getInventory().getItemInMainHand();
							if (item.getType() == Material.AIR) {
								player.sendMessage(prefix() + "§cYou can't sell air.");
								return true;
							} else if (Main.cui.api.IsItemSpawned(item)) {
								player.sendMessage(prefix() + "§cYou can't sell spawned items.");
								return true;
							}
							float price = 1;
							try {
								price = Float.parseFloat(args[1]);
							} catch (NumberFormatException exception) {
								player.sendMessage(prefix() + "§cInvalid Arguments. Usage: /market sell <price>");
								return true;
							}
							if (price < 0) {
								player.sendMessage(prefix() + "§cInvalid Price. Minimum price is 0.");
								return true;
							}
							if (item.hasItemMeta()) {
								player.sendMessage(prefix() + "§eAre you sure you would like to sell §bx"
										+ item.getAmount() + " §b" + item.getItemMeta().getDisplayName() + " §efor §b"
										+ economy.format(price) + "§e?\n" + "Please confirm with §b/market sell "
										+ price + " confirm");
							} else {
								player.sendMessage(prefix() + "§eAre you sure you would like to sell §bx"
										+ item.getAmount() + " §b" + item.getType().toString() + " §efor §b"
										+ economy.format(price) + "§e?\n" + "Please confirm with §b/market sell "
										+ price + " confirm");
							}
							return true;
						}
					} else {
						player.sendMessage(prefix() + "§4You don't have permission for this.");
						return true;
					}

					if (player.hasPermission("marketplace.remove")) {
						if (args[0].equalsIgnoreCase("remove") && args[1].equalsIgnoreCase("confirm")) {
							MarketplaceManager.removeAllListings(player);
							player.sendMessage(prefix() + "§aRemoved all listings");
							return true;
						}
					}
				} else if (args.length == 3) {
					if (player.hasPermission("marketplace.sell")) {
						if (args[0].equalsIgnoreCase("sell")) {
							if (args[2].equalsIgnoreCase("confirm")) {
								ItemStack item = player.getInventory().getItemInMainHand();
								if (item.getType() == Material.AIR) {
									player.sendMessage(prefix() + "§cYou can't sell air.");
									return true;
								} else if (Main.cui.api.IsItemSpawned(item)) {
									player.sendMessage(prefix() + "§cYou can't sell spawned items.");
									return true;
								}
								float price = 1;
								try {
									price = Float.parseFloat(args[1]);
								} catch (NumberFormatException exception) {
									player.sendMessage(
											prefix() + "§cInvalid Arguments. Usage: /market sell <price> <amount>");
									return true;
								}
								if (price < 0) {
									player.sendMessage(prefix() + "§cInvalid Price. Minimum price is 0.");
									return true;
								}

								MarketplaceItem marketItem;

								if (item.hasItemMeta()) {
									marketItem = new MarketplaceItem(player, item.getItemMeta().getDisplayName(), item,
											price, item.getAmount());
									player.sendMessage(prefix() + "§eListed §bx" + marketItem.getAmount() + " §b"
											+ marketItem.getName() + " §efor §b" + economy.format(price) + "§e.");
								} else {
									marketItem = new MarketplaceItem(player, item.getType().toString(), item, price,
											item.getAmount());
									player.sendMessage(prefix() + "§eListed §bx" + marketItem.getAmount() + " §b"
											+ marketItem.getItem().getType().toString() + " §efor §b"
											+ economy.format(price) + "§e.");
								}
								return true;
							}
							ItemStack item = player.getInventory().getItemInMainHand();
							if (item.getType() == Material.AIR) {
								player.sendMessage(prefix() + "§cYou can't sell air.");
								return true;
							} else if (Main.cui.api.IsItemSpawned(item)) {
								player.sendMessage(prefix() + "§cYou can't sell spawned items.");
								return true;
							}
							float price = 1;
							int amount = 1;
							try {
								price = Float.parseFloat(args[1]);
								amount = Integer.parseInt(args[2]);
							} catch (NumberFormatException exception) {
								player.sendMessage(
										prefix() + "§cInvalid Arguments. Usage: /market sell <price> <amount>");
								return true;
							}
							if (price < 0) {
								player.sendMessage(prefix() + "§cInvalid Price. Minimum price is 0.");
								return true;
							}
							if (amount > 64) {
								player.sendMessage(prefix() + "§cInvalid Amount. Maximum amount is 64.");
								return true;
							} else if (amount <= 0) {
								player.sendMessage(prefix() + "§cInvalid Amount. Minimum amount is 1.");
								return true;
							} else if (amount > item.getAmount()) {
								player.sendMessage(
										prefix() + "§cInvalid Amount. You don't own §bx" + amount + " §cof that item.");
								return true;
							}
							if (item.hasItemMeta()) {
								player.sendMessage(prefix() + "§eAre you sure you would like to sell §bx" + amount
										+ " §b" + item.getItemMeta().getDisplayName() + " §efor §b"
										+ economy.format(price) + "§e?\n" + "Please confirm with §b/market sell "
										+ price + " " + amount + " confirm");
							} else {
								player.sendMessage(prefix() + "§eAre you sure you would like to sell §bx" + amount
										+ " §b" + item.getType().toString() + " §efor §b" + economy.format(price)
										+ "§e?\n" + "Please confirm with §b/market sell " + price + " " + amount
										+ " confirm");
							}
							return true;
						}
					} else {
						player.sendMessage(prefix() + "§4You don't have permission for this.");
						return true;
					}
				} else if (args.length == 4) {
					if (player.hasPermission("marketplace.sell")) {
						if (args[0].equalsIgnoreCase("sell")) {
							if (args[3].equalsIgnoreCase("confirm")) {
								ItemStack item = player.getInventory().getItemInMainHand();
								if (item.getType() == Material.AIR) {
									player.sendMessage(prefix() + "§cYou can't sell air.");
									return true;
								} else if (Main.cui.api.IsItemSpawned(item)) {
									player.sendMessage(prefix() + "§cYou can't sell spawned items.");
									return true;
								}
								float price = 1;
								int amount = 1;
								try {
									price = Float.parseFloat(args[1]);
									amount = Integer.parseInt(args[2]);
								} catch (NumberFormatException exception) {
									player.sendMessage(
											prefix() + "§cInvalid Arguments. Usage: /market sell <price> <amount>");
									return true;
								}
								if (price < 0) {
									player.sendMessage(prefix() + "§cInvalid Price. Minimum price is 0.");
									return true;
								}
								if (amount > 64) {
									player.sendMessage(prefix() + "§cInvalid Amount. Maximum amount is 64.");
									return true;
								} else if (amount <= 0) {
									player.sendMessage(prefix() + "§cInvalid Amount. Minimum amount is 1.");
									return true;
								} else if (amount > item.getAmount()) {
									player.sendMessage(prefix() + "§cInvalid Amount. You don't own §bx" + amount
											+ " §cof that item.");
									return true;
								}

								MarketplaceItem marketItem;

								if (item.hasItemMeta()) {
									marketItem = new MarketplaceItem(player, item.getItemMeta().getDisplayName(), item,
											price, amount);
									player.sendMessage(prefix() + "§eListed §bx" + marketItem.getAmount() + " §b"
											+ marketItem.getName() + " §efor §b" + economy.format(price) + "§e.");
								} else {
									marketItem = new MarketplaceItem(player, item.getType().toString(), item, price,
											amount);
									player.sendMessage(prefix() + "§eListed §bx" + marketItem.getAmount() + " §b"
											+ marketItem.getItem().getType().toString() + " §efor §b"
											+ economy.format(price) + "§e.");
								}
								return true;
							}
						}
					} else {
						player.sendMessage(prefix() + "§4You don't have permission for this.");
						return true;
					}
				} else if (args.length >= 5) {
					player.performCommand("market help");
					return true;
				}
			}
		} else {
			player.sendMessage(prefix() + "§4You don't have permission for this.");
			return true;
		}
		return true;
	}

	public String prefix() {
		return "§8[§3C§fU§9Marketplace§8]: §r";
	}

}
