package sh.miles.meliorate.v2;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import sh.miles.megumi.item.material.MaterialTranslator;
import sh.miles.megumi.menu.listener.MenuListener;
import sh.miles.meliorate.v2.dispatcher.BlockBreakDispatcher;
import sh.miles.meliorate.v2.dispatcher.InventoryClickDispathcer;
import sh.miles.meliorate.v2.dispatcher.ItemDropDispatcher;
import sh.miles.meliorate.v2.dispatcher.ItemHeldDispatcher;
import sh.miles.meliorate.v2.dispatcher.ItemPickupDispathcer;
import sh.miles.meliorate.v2.modifier.modifiers.Modifiers;
import sh.miles.meliorate.v2.table.UpgradeTable;
import sh.miles.meliorate.v2.table.listener.UpgradeTableGravityListener;
import sh.miles.meliorate.v2.table.listener.UpgradeTableInteractListener;
import sh.miles.meliorate.v2.table.listener.UpgradeTablePlaceListener;

public class Meliorate extends JavaPlugin {

    @Getter
    private static Meliorate instance;
    public static final MaterialTranslator SMELT_TRANSLATOR;
    static {
        SMELT_TRANSLATOR = MaterialTranslator.fromArray(new Material[] {
                Material.OAK_LOG, Material.CHARCOAL,
                Material.ACACIA_LOG, Material.CHARCOAL,
                Material.BIRCH_LOG, Material.CHARCOAL,
                Material.DARK_OAK_LOG, Material.CHARCOAL,
                Material.JUNGLE_LOG, Material.CHARCOAL,
                Material.SPRUCE_LOG, Material.CHARCOAL,
                Material.CRIMSON_STEM, Material.CHARCOAL,
                Material.WARPED_STEM, Material.CHARCOAL,
                Material.STRIPPED_OAK_LOG, Material.CHARCOAL,
                Material.STRIPPED_ACACIA_LOG, Material.CHARCOAL,
                Material.STRIPPED_BIRCH_LOG, Material.CHARCOAL,
                Material.STRIPPED_DARK_OAK_LOG, Material.CHARCOAL,
                Material.STRIPPED_JUNGLE_LOG, Material.CHARCOAL,
                Material.STRIPPED_SPRUCE_LOG, Material.CHARCOAL,
                Material.STRIPPED_CRIMSON_STEM, Material.CHARCOAL,
                Material.STRIPPED_WARPED_STEM, Material.CHARCOAL,
                Material.RAW_IRON, Material.IRON_INGOT,
                Material.RAW_GOLD, Material.GOLD_INGOT,
                Material.RAW_COPPER, Material.COPPER_INGOT,
                Material.RAW_IRON_BLOCK, Material.IRON_BLOCK,
                Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK,
                Material.RAW_COPPER_BLOCK, Material.COPPER_BLOCK
        });
        SMELT_TRANSLATOR.lock();
    }

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
        getServer().getPluginManager().registerEvents(new UpgradeTableGravityListener(), this);
        getServer().getPluginManager().registerEvents(new UpgradeTablePlaceListener(), this);
        getServer().getPluginManager().registerEvents(new UpgradeTableInteractListener(), this);
    }

}
