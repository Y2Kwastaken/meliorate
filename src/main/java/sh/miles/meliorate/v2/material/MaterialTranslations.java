package sh.miles.meliorate.v2.material;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import com.google.common.collect.ImmutableMap;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MaterialTranslations {

    public static final Map<Material, Material> SMELT_MAP;

    static {
        Map<Material, Material> map = new HashMap<>();
        map.put(Material.RAW_IRON, Material.IRON_INGOT);
        map.put(Material.RAW_IRON_BLOCK, Material.IRON_BLOCK);
        map.put(Material.RAW_GOLD, Material.GOLD_INGOT);
        map.put(Material.RAW_GOLD_BLOCK, Material.GOLD_BLOCK);
        map.put(Material.COAL_ORE, Material.COAL);
        map.put(Material.DIAMOND_ORE, Material.DIAMOND);
        map.put(Material.EMERALD_ORE, Material.EMERALD);
        map.put(Material.OAK_LOG, Material.CHARCOAL);
        map.put(Material.SPRUCE_LOG, Material.CHARCOAL);
        map.put(Material.BIRCH_LOG, Material.CHARCOAL);
        map.put(Material.JUNGLE_LOG, Material.CHARCOAL);
        map.put(Material.ACACIA_LOG, Material.CHARCOAL);
        map.put(Material.DARK_OAK_LOG, Material.CHARCOAL);
        map.put(Material.CRIMSON_STEM, Material.CHARCOAL);
        map.put(Material.WARPED_STEM, Material.CHARCOAL);
        map.put(Material.STRIPPED_OAK_LOG, Material.CHARCOAL);
        map.put(Material.STRIPPED_SPRUCE_LOG, Material.CHARCOAL);
        map.put(Material.STRIPPED_BIRCH_LOG, Material.CHARCOAL);
        map.put(Material.STRIPPED_JUNGLE_LOG, Material.CHARCOAL);
        map.put(Material.STRIPPED_ACACIA_LOG, Material.CHARCOAL);
        map.put(Material.STRIPPED_DARK_OAK_LOG, Material.CHARCOAL);
        map.put(Material.STRIPPED_CRIMSON_STEM, Material.CHARCOAL);
        map.put(Material.STRIPPED_WARPED_STEM, Material.CHARCOAL);
        SMELT_MAP = ImmutableMap.copyOf(map);
    }
}
