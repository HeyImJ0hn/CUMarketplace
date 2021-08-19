package cu.GuitarXpress.CUMarketplace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.milkbowl.vault.economy.Economy;

public class MarketplaceManager implements Listener {

	Main plugin;
	Economy economy;

	public static List<MarketplaceItem> items = new ArrayList<MarketplaceItem>();

	private static HashMap<Player, Inventory> homeGUIMap = new HashMap<Player, Inventory>();
	private static HashMap<Player, Inventory> marketPageMap = new HashMap<Player, Inventory>();
	private static HashMap<Player, Inventory> confirmationMenuMap = new HashMap<Player, Inventory>();
	private static HashMap<Player, Inventory> ownerEditMenuMap = new HashMap<Player, Inventory>();

	private static HashMap<Player, Inventory> myListingsPageMap = new HashMap<Player, Inventory>();

	public static Inventory homeGUI;
	public static Inventory marketPage;
	public static Inventory confirmationMenu;
	public static Inventory ownerEditMenu;

	public static Inventory myListingsPage;

	private static HashMap<Player, Integer> currentIndexMap = new HashMap<Player, Integer>();
	private static HashMap<Player, Integer> currentPageMap = new HashMap<Player, Integer>();

	private static int maxItemsPerPage = 28;

	public MarketplaceManager(Main plugin) {
		this.plugin = plugin;
		economy = Main.getEconomy();
	}

	private static void createHomeGUI(Player player) {
		currentIndexMap.put(player, 0);
		currentPageMap.put(player, 0);

		homeGUI = Bukkit.createInventory(player, 27, "§3C§fU §9Marketplace");
		for (int i = 0; i < homeGUI.getSize(); i++) {
			if (i == 10)
				homeGUI.setItem(i, ItemManager.browseItem);
			else if (i == 13)
				homeGUI.setItem(i, ItemManager.myListingsItem);
			else if (i == 16)
				homeGUI.setItem(i, ItemManager.createListingItem);
			else
				homeGUI.setItem(i, ItemManager.inventoryFillerItem);
		}
		homeGUIMap.put(player, homeGUI);
	}

	private static void createMarketPage(Player player, int pageNum) {
		marketPage = Bukkit.createInventory(player, 54, "§3C§fU §9Marketplace");

		int indexOnPage = 0;

		createPageBorder(marketPage);

		currentIndexMap.put(player, pageNum * maxItemsPerPage);

		for (int i = 0; i < marketPage.getSize(); i++) {
			if (indexOnPage < maxItemsPerPage && currentIndexMap.get(player) < items.size()) {
				if (marketPage.getItem(i) == null) {
					marketPage.setItem(i, items.get(currentIndexMap.get(player)).getItem());
					currentIndexMap.put(player, currentIndexMap.get(player) + 1);
					indexOnPage++;
				}
			}
		}

		marketPageMap.put(player, marketPage);
	}

	private static void createMyListingsPage(Player player, int pageNum) {
		myListingsPage = Bukkit.createInventory(player, 54, "§3C§fU §9Marketplace");

		int indexOnPage = 0;

		createPageBorder(myListingsPage);

		currentIndexMap.put(player, pageNum * maxItemsPerPage);

		for (int i = 0; i < myListingsPage.getSize(); i++) {
			if (indexOnPage < maxItemsPerPage && currentIndexMap.get(player) < items.size()) {
				if (myListingsPage.getItem(i) == null) {
					if (items.get(currentIndexMap.get(player)).getOwner().getUniqueId().toString().equals(player.getUniqueId().toString())) {
						myListingsPage.setItem(i, items.get(currentIndexMap.get(player)).getItem());
						currentIndexMap.put(player, currentIndexMap.get(player) + 1);
						indexOnPage++;
					}
				}
			}
		}

		myListingsPageMap.put(player, myListingsPage);
	}

	private static void createConfirmationMenu(Player player, MarketplaceItem item) {
		confirmationMenu = Bukkit.createInventory(player, 27, "§3C§fU §9Marketplace");
		for (int i = 0; i < confirmationMenu.getSize(); i++) {
			if (i == 11) {
				confirmationMenu.setItem(i, ItemManager.confirmPurchaseItem);
			} else if (i == 13) {
				confirmationMenu.setItem(i, item.getItem());
			} else if (i == 15) {
				confirmationMenu.setItem(i, ItemManager.cancelPurchaseItem);
			} else {
				confirmationMenu.setItem(i, ItemManager.inventoryFillerItem);
			}
		}
		confirmationMenuMap.put(player, confirmationMenu);
	}

	private static void createOwnerEditMenu(Player player, MarketplaceItem item) {
		ownerEditMenu = Bukkit.createInventory(player, 27, "§3C§fU §9Marketplace");
		for (int i = 0; i < ownerEditMenu.getSize(); i++) {
			if (i == 11) {
				ownerEditMenu.setItem(i, ItemManager.backFromEditItem);
			} else if (i == 13) {
				ownerEditMenu.setItem(i, item.getItem());
			} else if (i == 15) {
				ownerEditMenu.setItem(i, ItemManager.removeListingItem);
			} else {
				ownerEditMenu.setItem(i, ItemManager.inventoryFillerItem);
			}
		}
		ownerEditMenuMap.put(player, ownerEditMenu);
	}

	private static void createPageBorder(Inventory page) {
		for (int i = 0; i < page.getSize(); i++) {
			if (i == 4) {
				page.setItem(i, ItemManager.refreshListingsItem);
			} else if (i == 48) {
				page.setItem(i, ItemManager.prevPageItem);
			} else if (i == 49) {
				page.setItem(i, ItemManager.backItem);
			} else if (i == 50) {
				page.setItem(i, ItemManager.nextPageItem);
			} else if (i == 0 || i == 1 || i == 2 || i == 3 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 17
					|| i == 18 || i == 26 || i == 27 || i == 35 || i == 36 || i == 44 || i == 45 || i == 46 || i == 47
					|| i == 51 || i == 52 || i == 53) {
				page.setItem(i, ItemManager.inventoryFillerItem);
			}
		}
	}

	public static void openMarketplace(Player player) {
		createHomeGUI(player);
		player.openInventory(homeGUIMap.get(player));
	}

	public static void openMarketPage(Player player, int pageNum) {
		createMarketPage(player, pageNum);
		player.openInventory(marketPageMap.get(player));
	}

	public static void openMyListingsPage(Player player, int pageNum) {
		createMyListingsPage(player, pageNum);
		player.openInventory(myListingsPageMap.get(player));
	}

	public static void openOwnerEditMenu(Player player, MarketplaceItem item) {
		createOwnerEditMenu(player, item);
		player.openInventory(ownerEditMenuMap.get(player));
	}

	public static void openCreateConfirmationMenu(Player player, MarketplaceItem item) {
		createConfirmationMenu(player, item);
		player.openInventory(confirmationMenuMap.get(player));
	}

	public static void addToMarket(MarketplaceItem item) {
		items.add(item);
	}

	private void tryPurchaseItem(Player player) {
		MarketplaceItem item = null;
		for (int i = 0; i < confirmationMenuMap.get(player).getSize(); i++) {
			for (int j = 0; j < items.size(); j++) {
				if (items.get(j).getItem().equals(confirmationMenuMap.get(player).getItem(i))) {
					item = items.get(j);
				}
			}
		}
		if (item != null) {
			if (economy.getBalance(player) >= item.getPrice()) {
				economy.withdrawPlayer(player, item.getPrice());
				item.restoreMeta();
				items.remove(item);
				player.sendMessage(prefix() + "§aPurchased §bx" + item.getAmount() + " " + item.getName());
				if (player.getInventory().firstEmpty() == -1) {
					player.getWorld().dropItem(player.getLocation(), item.getItem());
					player.sendMessage(prefix() + "§cYour inventory was full. The item has been dropped on the ground.");
				} else {
					player.getInventory().addItem(item.getItem());
				}
				openMarketPage(player, currentPageMap.get(player));
				if (item.getOwner().isOnline()) {
					item.getOwner().getPlayer().sendMessage(prefix() + "§aYou just sold §6x" + item.getAmount() + " " + item.getName()
							+ " §afor §6" + economy.format(item.getPrice()) + "§a!");
				}
				economy.depositPlayer(item.getOwner(), item.getPrice());
			} else {
				player.sendMessage(prefix() + "§cYou can't afford this item!");
			}
		} else {
			player.sendMessage(prefix() + "§cSorry! §eThat item has been bought or removed.");
		}
	}

	private void removeListing(Player player) {
		MarketplaceItem item = null;
		for (int i = 0; i < ownerEditMenuMap.get(player).getSize(); i++) {
			for (int j = 0; j < items.size(); j++) {
				if (items.get(j).getItem().equals(ownerEditMenuMap.get(player).getItem(i))) {
					item = items.get(j);
				}
			}
		}
		if (item != null) {
			item.restoreMeta();
			items.remove(item);
			player.sendMessage(prefix() + "§eListing removed.");
			if (player.getInventory().firstEmpty() == -1) {
				player.getWorld().dropItem(player.getLocation(), item.getItem());
				player.sendMessage(prefix() + "§cYour inventory was full. The item has been dropped on the ground.");
			} else {
				player.getInventory().addItem(item.getItem());
			}
		} else {
			player.sendMessage(prefix() + "§eThat item has already been bought!");
		}
		
		openMarketPage(player, currentPageMap.get(player));
	}

	public static void removeAllListings(Player player) {
		for (int i = items.size() - 1; i >= 0; i--) {
			if (items.get(i) != null) {
				if (items.get(i).getOwner().getUniqueId().toString().equals(player.getUniqueId().toString())) {
					items.get(i).restoreMeta();
					if (player.getInventory().firstEmpty() == -1) {
						player.getWorld().dropItem(player.getLocation(), items.get(i).getItem());
						player.sendMessage(
								prefix() + "§cYour inventory was full. The item has been dropped on the ground.");
					} else {
						player.getInventory().addItem(items.get(i).getItem());
					}
					items.remove(items.get(i));
				}
			}
		}
	}

	public static String prefix() {
		return "§8[§3C§fU§9Marketplace§8]: §r";
	}

	public static void refreshInventories(Player player) {
		if (player.getOpenInventory().getTopInventory().equals(homeGUIMap.get(player))) {
			player.openInventory(homeGUIMap.get(player));
		} else if (player.getOpenInventory().getTopInventory().equals(marketPageMap.get(player))) {
			player.openInventory(marketPageMap.get(player));
		} else if (player.getOpenInventory().getTopInventory().equals(confirmationMenuMap.get(player))) {
			player.openInventory(confirmationMenuMap.get(player));
		} else if (player.getOpenInventory().getTopInventory().equals(ownerEditMenuMap.get(player))) {
			player.openInventory(ownerEditMenuMap.get(player));
		} else if (player.getOpenInventory().getTopInventory().equals(myListingsPageMap.get(player))) {
			player.openInventory(myListingsPageMap.get(player));
		}
	}

	//////////////////// EVENTS ////////////////////

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (event.getInventory().equals(homeGUIMap.get(player))) {
			event.setCancelled(true);
		} else if (event.getInventory().equals(marketPageMap.get(player))) {
			event.setCancelled(true);
		} else if (event.getInventory().equals(confirmationMenuMap.get(player))) {
			event.setCancelled(true);
		} else if (event.getInventory().equals(ownerEditMenuMap.get(player))) {
			event.setCancelled(true);
		} else if (event.getInventory().equals(myListingsPageMap.get(player))) {
			event.setCancelled(true);
		} else {
			return;
		}

		ItemStack clickedItem = event.getCurrentItem();

		if (clickedItem != null) {
			if (clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasLore()) {
				if (clickedItem.getItemMeta().getLore().get(0).equals("§7Browse current listings")) {
					openMarketPage(player, 0);
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Go to Next Page")) {
					if (currentPageMap.get(player) * maxItemsPerPage < items.size()) {
						currentPageMap.put(player, currentPageMap.get(player) + 1);
						if (player.getOpenInventory().getTopInventory().equals(marketPageMap.get(player))) {
							openMarketPage(player, currentPageMap.get(player));
						} else if (player.getOpenInventory().getTopInventory().equals(myListingsPageMap.get(player))) {
							openMyListingsPage(player, currentPageMap.get(player));
						}
					} else {
						player.sendMessage(prefix() + "§eThere are no more pages yet!");
					}
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Go to Previous Page")) {
					if (currentPageMap.get(player) == 0) {
						player.sendMessage(prefix() + "§eYou're already on the first page!");
					} else {
						currentPageMap.put(player, currentPageMap.get(player) - 1);
						if (player.getOpenInventory().getTopInventory().equals(marketPageMap.get(player))) {
							openMarketPage(player, currentPageMap.get(player));
						} else if (player.getOpenInventory().getTopInventory().equals(myListingsPageMap.get(player))) {
							openMyListingsPage(player, currentPageMap.get(player));
						}
					}
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Refresh current page")) {
					if (player.getOpenInventory().getTopInventory().equals(marketPageMap.get(player))) {
						openMarketPage(player, currentPageMap.get(player));
					} else if (player.getOpenInventory().getTopInventory().equals(myListingsPageMap.get(player))) {
						openMyListingsPage(player, currentPageMap.get(player));
					}
					player.sendMessage(prefix() + "§eRefreshing current listings page.");
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Back to main menu")) {
					openMarketplace(player);
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Confirm Purchase")) {
					if (player.hasPermission("marketplace.buy")) {
						tryPurchaseItem(player);
					} else {
						player.sendMessage(prefix() + "§4You don't have permission for that.");
					}
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Cancel Purchase")) {
					openMarketPage(player, currentPageMap.get(player));
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Back to listings")) {
					openMarketPage(player, currentPageMap.get(player));
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Remove listing from marketplace")) {
					openMarketPage(player, currentPageMap.get(player));
					removeListing(player);
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Create a listing")) {
					player.sendMessage(prefix()
							+ "§cSorry! That feature isn't implemented yet! Use /market sell <price> <amount>");
					return;
				} else if (clickedItem.getItemMeta().getLore().get(0).equals("§7Check your current listings")) {
					openMyListingsPage(player, currentPageMap.get(player));
					player.sendMessage(prefix() + "§eThis feature is a work in progress!");
					return;
				}

				for (int i = 0; i < items.size(); i++) {
					if (items.get(i).getItem().equals(clickedItem)) {
						MarketplaceItem item = (MarketplaceItem) items.get(i);
						if (item.getOwner().getUniqueId().toString().equals(player.getUniqueId().toString())) {
							openOwnerEditMenu(player, item);
						} else {
							openCreateConfirmationMenu(player, item);
						}
					}
				}

			}
		}
	}
}
