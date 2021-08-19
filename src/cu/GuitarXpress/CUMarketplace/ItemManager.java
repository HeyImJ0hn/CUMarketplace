package cu.GuitarXpress.CUMarketplace;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class ItemManager {
	public static ItemStack nextPageItem;
	public static ItemStack prevPageItem;

	public static ItemStack browseItem;
	public static ItemStack inventoryFillerItem;
	public static ItemStack myListingsItem;
	public static ItemStack createListingItem;
	public static ItemStack refreshListingsItem;
	public static ItemStack backItem;

	public static ItemStack confirmPurchaseItem;
	public static ItemStack cancelPurchaseItem;

	public static ItemStack applyChangesItem;
	public static ItemStack discardChangesItem;
	public static ItemStack removeListingItem;
	public static ItemStack backFromEditItem;
	
	public static ItemStack setPriceItem;
	
	public static void init() {
		createNextPageItem();
		createPreviousPageItem();

		createInventoryFillerItem();
		createBrowseItem();
		createMyListingsItem();
		createCreateListingItem();
		createRefreshListingsItem();
		createBackItem();

		createConfirmPurchaseItem();
		createCancelPuchaseItem();
		
		createApplyChangesItem();
		createDiscardChangesItem();
		createRemoveListingItem();
		createBackFromEditItem();
		
		
		createSetPriceItem();
	}

	private static void createBackFromEditItem() {
		ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§cBack");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Back to listings");
		meta.setLore(lore);
		item.setItemMeta(meta);
		backFromEditItem = item;
	}

	private static void createSetPriceItem() {
		ItemStack item = new ItemStack(Material.NAME_TAG, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§eSet Price");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Press to set item price");
		meta.setLore(lore);
		item.setItemMeta(meta);
		setPriceItem = item;
	}

	private static void createRemoveListingItem() {
		ItemStack item = new ItemStack(Material.BARRIER, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§cRemove Listing");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Remove listing from marketplace");
		meta.setLore(lore);
		item.setItemMeta(meta);
		removeListingItem = item;
	}

	private static void createDiscardChangesItem() {
		ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§cDiscard Changes");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Discard listing changes");
		meta.setLore(lore);
		item.setItemMeta(meta);
		discardChangesItem = item;
	}

	private static void createApplyChangesItem() {
		ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§aApply Changes");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Apply changes to listing");
		meta.setLore(lore);
		item.setItemMeta(meta);
		applyChangesItem = item;
	}

	private static void createCancelPuchaseItem() {
		ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§cCancel");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Cancel Purchase");
		meta.setLore(lore);
		item.setItemMeta(meta);
		cancelPurchaseItem = item;
	}

	private static void createConfirmPurchaseItem() {
		ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§aConfirm");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Confirm Purchase");
		meta.setLore(lore);
		item.setItemMeta(meta);
		confirmPurchaseItem = item;
	}

	private static void createBackItem() {
		ItemStack item = new ItemStack(Material.BARRIER, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§eBack");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Back to main menu");
		meta.setLore(lore);
		item.setItemMeta(meta);
		backItem = item;
	}

	private static void createRefreshListingsItem() {
		ItemStack item = new ItemStack(Material.SUNFLOWER, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§eRefresh Listings");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Refresh current page");
		meta.setLore(lore);
		item.setItemMeta(meta);
		refreshListingsItem = item;
	}

	private static void createInventoryFillerItem() {
		ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName(" ");
		ArrayList<String> lore = new ArrayList<>();
		meta.setLore(lore);
		item.setItemMeta(meta);
		inventoryFillerItem = item;
	}

	private static void createBrowseItem() {
		ItemStack item = new ItemStack(Material.MAP, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§eBrowse Listings");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Browse current listings");
		meta.setLore(lore);
		item.setItemMeta(meta);
		browseItem = item;
	}

	private static void createMyListingsItem() {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§eMy Listings");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Check your current listings");
		meta.setLore(lore);
		item.setItemMeta(meta);
		myListingsItem = item;
	}

	private static void createCreateListingItem() {
		ItemStack item = new ItemStack(Material.EMERALD, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName("§eCreate Listing");
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Create a listing");
		meta.setLore(lore);
		item.setItemMeta(meta);
		createListingItem = item;
	}

	private static void createNextPageItem() {
		ItemStack item = getSkull(
				"http://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf");
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Go to Next Page");
		meta.setLore(lore);
		meta.setDisplayName("§eNext Page");
		item.setItemMeta(meta);
		nextPageItem = item;
	}

	private static void createPreviousPageItem() {
		ItemStack item = getSkull(
				"http://textures.minecraft.net/texture/bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9");
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§7Go to Previous Page");
		meta.setLore(lore);
		meta.setDisplayName("§ePrevious Page");
		item.setItemMeta(meta);
		prevPageItem = item;
	}

	public static ItemStack getSkull(String url) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
		if (url == null || url.isEmpty())
			return skull;
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;
		try {
			profileField = skullMeta.getClass().getDeclaredField("profile");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		profileField.setAccessible(true);
		try {
			profileField.set(skullMeta, profile);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		skull.setItemMeta(skullMeta);
		return skull;
	}
}
