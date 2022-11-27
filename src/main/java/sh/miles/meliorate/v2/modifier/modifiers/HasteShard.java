package sh.miles.meliorate.v2.modifier.modifiers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffectType;

import sh.miles.megumi.chat.ChatUtil;
import sh.miles.meliorate.ItemBuilder;
import sh.miles.meliorate.v2.Meliorate;
import sh.miles.meliorate.v2.material.MaterialGroup;
import sh.miles.meliorate.v2.modifier.IModifier;
import sh.miles.meliorate.v2.modifier.ModifierTrigger;
import sh.miles.meliorate.v2.modifier.modifiers.trigggers.EffectClickTrigger;
import sh.miles.meliorate.v2.modifier.modifiers.trigggers.EffectDropTrigger;
import sh.miles.meliorate.v2.modifier.modifiers.trigggers.EffectHeldTrigger;
import sh.miles.meliorate.v2.modifier.modifiers.trigggers.EffectPickupTrigger;

public class HasteShard implements IModifier {

    private static HasteShard instance;

    private final ItemStack item;
    private final String lore;
    private final int maxLevel;
    private final String modifierKey;
    private final String loreKey;
    private final ShapedRecipe recipe;
    private final MaterialGroup group;
    private final Map<Class<? extends Event>, ModifierTrigger<? extends Event>> triggers;

    private HasteShard() {
        this.item = ItemBuilder.builder()
                .material(Material.DIAMOND)
                .modelData(2)
                .name(ChatUtil.style("<gradient:FFFAC3:F3EB00>Shard of Haste</gradient>"))
                .build().make();
        this.lore = ChatUtil.style("<color:FFFAC3>Haste %level%");
        this.maxLevel = 2;
        this.modifierKey = "haste_shard";
        this.loreKey = this.modifierKey + "_lore";
        this.recipe = new ShapedRecipe(new NamespacedKey(Meliorate.getInstance(), this.modifierKey + "_recipe"), item);
        this.recipe.shape("GRG", "RSR", "GRG");
        this.recipe.setIngredient('G', Material.GOLD_BLOCK);
        this.recipe.setIngredient('R', Material.REDSTONE_BLOCK);
        this.recipe.setIngredient('S', Material.NETHERITE_SCRAP);

        this.group = MaterialGroup.of(MaterialGroup.TOOLS, MaterialGroup.SWORDS);
        this.triggers = new HashMap<>();
        this.triggers.put(PlayerDropItemEvent.class, new EffectDropTrigger(PotionEffectType.FAST_DIGGING));
        this.triggers.put(PlayerItemHeldEvent.class, new EffectHeldTrigger(PotionEffectType.FAST_DIGGING));
        this.triggers.put(InventoryClickEvent.class, new EffectClickTrigger(PotionEffectType.FAST_DIGGING));
        this.triggers.put(EntityPickupItemEvent.class, new EffectPickupTrigger(PotionEffectType.FAST_DIGGING));
    }

    @Override
    public ItemStack getItem() {
        return this.item;
    }

    @Override
    public String getLore() {
        return this.lore;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public String getModifierKey() {
        return this.modifierKey;
    }

    @Override
    public String getLoreKey() {
        return this.loreKey;
    }

    @Override
    public ShapedRecipe getRecipe() {
        return this.recipe;
    }

    @Override
    public MaterialGroup getGroup() {
        return this.group;
    }

    @Override
    public Map<Class<? extends Event>, ModifierTrigger<? extends Event>> getTriggers() {
        return new HashMap<>(this.triggers);
    }

    public static HasteShard getInstance() {
        if (instance == null) {
            instance = new HasteShard();
        }
        return instance;
    }

}
