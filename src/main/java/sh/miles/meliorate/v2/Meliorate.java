package sh.miles.meliorate.v2;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import sh.miles.megumi.menu.listener.MenuListener;
import sh.miles.meliorate.v2.dispatcher.BlockBreakDispatcher;
import sh.miles.meliorate.v2.dispatcher.InventoryClickDispathcer;
import sh.miles.meliorate.v2.dispatcher.ItemDropDispatcher;
import sh.miles.meliorate.v2.dispatcher.ItemHeldDispatcher;
import sh.miles.meliorate.v2.dispatcher.ItemPickupDispathcer;
import sh.miles.meliorate.v2.modifier.modifiers.Modifiers;
import sh.miles.meliorate.v2.table.UpgradeTable;
import sh.miles.meliorate.v2.table.listener.UpgradeTableInteractListener;
import sh.miles.meliorate.v2.table.listener.UpgradeTablePlaceListener;

public class Meliorate extends JavaPlugin {

    @Getter
    private static Meliorate instance;

    @Override
    public void onEnable() {
        instance = this;

        // Modifiers Registration
        Modifiers.LAVA_CRYSTAL.registerRecipe();
        Modifiers.HASTE_SHARD.registerRecipe();

        // Modifier Event Dispatchers
        getServer().getPluginManager().registerEvents(new BlockBreakDispatcher(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickDispathcer(), this);
        getServer().getPluginManager().registerEvents(new ItemDropDispatcher(), this);
        getServer().getPluginManager().registerEvents(new ItemHeldDispatcher(), this);
        getServer().getPluginManager().registerEvents(new ItemPickupDispathcer(), this);

        // table recipe
        UpgradeTable.registerUpgradeTableRecipe();

        // table listeners
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new UpgradeTablePlaceListener(), this);
        getServer().getPluginManager().registerEvents(new UpgradeTableInteractListener(), this);
    }

}
