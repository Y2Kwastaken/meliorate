package sh.miles.meliorate.v2.modifier.modifiers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import sh.miles.megumi.chat.ChatUtil;
import sh.miles.megumi.item.material.MaterialGroup;
import sh.miles.meliorate.ItemBuilder;
import sh.miles.meliorate.v2.Meliorate;
import sh.miles.meliorate.v2.modifier.IModifier;
import sh.miles.meliorate.v2.modifier.MeliorateModifier;
import sh.miles.meliorate.v2.modifier.ModifierTrigger;

public class LavaCrystal implements IModifier {

    private static LavaCrystal instance;

    private final ItemStack item;
    private final String lore;
    private final int maxLevel;
    private final String modifierKey;
    private final String loreKey;
    private final ShapedRecipe recipe;
    private final MaterialGroup group;
    private final Map<Class<? extends Event>, ModifierTrigger<? extends Event>> triggers;

    private LavaCrystal() {
        this.item = ItemBuilder.builder()
                .material(Material.DIAMOND)
                .modelData(1)
                .name(ChatUtil.style("<gradient:ff0000:ff7f00>Crystal of Lava</gradient>"))
                .build().make();
        this.lore = ChatUtil.style("<color:FF9300>Auto-Smelt %level%");
        this.maxLevel = 1;
        this.modifierKey = "lava_crystal";
        this.loreKey = this.modifierKey + "_lore";
        this.recipe = new ShapedRecipe(new NamespacedKey(Meliorate.getInstance(), this.modifierKey + "_recipe"), item);
        this.recipe.shape("OLO", "LSL", "OLO");
        this.recipe.setIngredient('O', Material.OBSIDIAN);
        this.recipe.setIngredient('L', Material.LAVA_BUCKET);
        this.recipe.setIngredient('S', Material.NETHERITE_SCRAP);

        this.group = MaterialGroup.of(MaterialGroup.AXES, MaterialGroup.PICKAXES);

        this.triggers = new HashMap<>();
        this.triggers.put(BlockBreakEvent.class, new LavaCrystalBreakTrigger());
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
        return this.triggers;
    }

    static LavaCrystal getInstance() {
        if (instance == null) {
            instance = new LavaCrystal();
        }
        return instance;
    }

    private static class LavaCrystalBreakTrigger implements ModifierTrigger<BlockBreakEvent> {

        @Override
        public void trigger(BlockBreakEvent e, MeliorateModifier mod) {
            final ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();
            Collection<ItemStack> drops = e.getBlock().getDrops(tool);
            drops.forEach(drop -> {
                if (Meliorate.SMELT_TRANSLATOR.hasKey(drop.getType())) {
                    drop.setType(Meliorate.SMELT_TRANSLATOR.getKey(drop.getType()));
                }
            });
            e.setDropItems(false);
            drops.forEach(drop -> e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop));
        }

    }

}
