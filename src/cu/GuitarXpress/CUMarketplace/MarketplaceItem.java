package cu.GuitarXpress.CUMarketplace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.NumberConversions;

import net.milkbowl.vault.economy.Economy;

public class MarketplaceItem implements ConfigurationSerializable {
	
	private OfflinePlayer owner;
	private String name;
	private ItemStack item;
	private float price;
	private int amount;
	List<String> itemLore = new ArrayList<String>();
	List<String> customLore = new ArrayList<String>();
	ItemMeta itemMeta;
	Economy economy = Main.getEconomy();
	
	public MarketplaceItem(OfflinePlayer owner, String name, ItemStack item, float price, int amount) {
		boolean customAmount = false;
		
		this.owner = owner;
		this.name = name;
		
		this.price = price;
		this.amount = amount;
		
		createCustomLore();
		
		ItemStack itemCopy = item.clone();
		
		if (amount != itemCopy.getAmount()) {
			customAmount = true;
		}
		
		itemCopy.setAmount(amount);
		this.item = itemCopy;
		
		
		if (itemCopy.hasItemMeta()) {
			itemMeta = itemCopy.getItemMeta();
			if (itemCopy.getItemMeta().hasLore()) {
				itemLore = itemCopy.getItemMeta().getLore();
				changeLore(true);
			} else {
				changeLore(false);
			}
		} else {
			changeLore(false);
		}
		
		MarketplaceManager.addToMarket(this);
		
		if (customAmount) {
			item.setAmount(item.getAmount() - amount);
		} else {
			item.setAmount(0);
		}
	}

	public void restoreMeta() {
		item.setItemMeta(itemMeta);
	}
	
	private void createCustomLore() {
		customLore.add(" ");
		customLore.add("§8---------------");
		customLore.add("§9Being sold by: §6" + owner.getName());
		customLore.add("§9Price: §6" + economy.format(price));
		customLore.add("§8---------------");
	}
	
	private void changeLore(boolean hasLore) {
		List<String> lore = new ArrayList<String>();
		ItemMeta meta = item.getItemMeta();
		if (hasLore) {
			int j = 0;
			for (int i = 0; i < itemLore.size() + customLore.size(); i++) {
				if (i < itemLore.size())
					lore.add(itemLore.get(i));
				else
					lore.add(customLore.get(j++));
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else {
			meta.setLore(customLore);
			item.setItemMeta(meta);
		}
	}

	public OfflinePlayer getOwner() {
		return owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ItemStack getItem() {
		return item;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
		serialized.put("owner", this.owner);
		serialized.put("name", this.name);
		serialized.put("item", this.item);
		serialized.put("price", this.price);
		serialized.put("amount", this.amount);
		if (Main.isDisabling)
			restoreMeta();
		return serialized;
	}
	
	public static MarketplaceItem deserialize(Map<String, Object> deserialize) {
		return new MarketplaceItem((OfflinePlayer) deserialize.get("owner"), String.valueOf(deserialize.get("name")), (ItemStack) deserialize.get("item"), NumberConversions.toFloat(deserialize.get("price")), NumberConversions.toInt(deserialize.get("amount")));
	}
	
}
