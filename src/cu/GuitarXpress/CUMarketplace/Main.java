package cu.GuitarXpress.CUMarketplace;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.chaoticunited.CUItemSpawn.CUItemSpawn;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	public static MarketplaceManager marketplace;
	public static CUItemSpawn cui = (CUItemSpawn) Bukkit.getPluginManager().getPlugin("CUItemSpawn");
	
	private static Economy econ = null;
	
	public static boolean isDisabling = false;
	
	static {
	    ConfigurationSerialization.registerClass(MarketplaceItem.class);
	}
	
	@Override
	public void onEnable() {
		isDisabling = false;
		if (!setupEconomy() ) {
            System.out.println(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		
		marketplace = new MarketplaceManager(this);
		ItemManager.init();
		
		getServer().getPluginManager().registerEvents(new MarketplaceManager(this), this);
		getCommand("market").setExecutor(new Commands(this));
		getCommand("market").setTabCompleter(new TabCompletion(this));
		
		if (this.getConfig().get("Marketplace.Items") != null) {
//			MarketplaceManager.items = (ArrayList<MarketplaceItem>) this.getConfig().getList("Marketplace.Items");
			loadMarketItems();
			System.out.println("§8[§3C§fU§9Marketplace§8] §aLoaded Marketplace Listings.");
		}
		
		startSaveRunnable();
		 
		System.out.println("§8[§3C§fU§9Marketplace§8] §aEnabled.");
	}
	
	@Override
	public void onDisable() {
		isDisabling = true;
		
//		if (!MarketplaceManager.items.isEmpty()) {
//			this.getConfig().set("Marketplace.Items", MarketplaceManager.items);
//			System.out.println("§8[§3C§fU§9Marketplace§8] §aSaved Marketplace Listings.");
//		}
		
		saveMarketItems(MarketplaceManager.items);
		
		System.out.println("§8[§3C§fU§9Marketplace§8] §cDisabled.");
	}
	
	public void saveMarketItems(List<MarketplaceItem> items) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			MarketplaceManager.refreshInventories(player);
		}
		getConfig().set("Marketplace.Items", items);
		this.saveConfig();
		System.out.println("§8[§3C§fU§9Marketplace§8] §aSaved Marketplace Listings.");
	}
	
	@SuppressWarnings("unchecked")
	public List<MarketplaceItem> loadMarketItems() {
		return getConfig().getMapList("Marketplace.Items").stream().map(serializedItem -> MarketplaceItem.deserialize((Map<String, Object>) serializedItem)).collect(Collectors.toList());
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public static Economy getEconomy() {
        return econ;
    }
	
	private void startSaveRunnable() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			saveMarketItems(MarketplaceManager.items);
		}, 60 * 20, 10 * 60 * 20); // Start 1 minute after function is called and runs every 10 minutes;
		
	}
	
}
