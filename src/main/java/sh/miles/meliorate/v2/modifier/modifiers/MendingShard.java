package sh.miles.meliorate.v2.modifier.modifiers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;

import sh.miles.megumi.chat.ChatUtil;
import sh.miles.megumi.item.builder.ItemBuilder;
import sh.miles.megumi.item.material.MaterialGroup;
import sh.miles.meliorate.v2.Meliorate;
import sh.miles.meliorate.v2.modifier.IModifier;
import sh.miles.meliorate.v2.modifier.MeliorateModifier;
import sh.miles.meliorate.v2.modifier.ModifierTrigger;

public class MendingShard implements IModifier {

    private static MendingShard instance;

    private final ItemStack item;
    private final String lore;
    private final int maxLevel;
    private final String modifierKey;
    private final String loreKey;
    private final ShapedRecipe recipe;
    private final MaterialGroup group;
    private final Map<Class<? extends Event>, ModifierTrigger<? extends Event>> triggers;

    private MendingShard() {
        this.item = ItemBuilder.builder()
                .material(Material.DIAMOND)
                .name(ChatUtil.style("<gradient:24ec17:078e01>Shard of Mending</gradient>"))
                .modelData(3)
                .build().make();
        this.lore = ChatUtil.style("<color:24ec17>Mending %level%</color>");
        this.maxLevel = 3;
        this.modifierKey = "mending_shard";
        this.loreKey = this.modifierKey + "_lore";
        this.recipe = new ShapedRecipe(new NamespacedKey(Meliorate.getInstance(), this.modifierKey + "_recipe"), item);
        this.recipe.shape("CMC", "MSM", "GMG");
        this.recipe.setIngredient('C', Material.CACTUS);
        this.recipe.setIngredient('M', Material.MOSS_BLOCK);
        this.recipe.setIngredient('G', Material.GREEN_DYE);
        this.recipe.setIngredient('S', Material.NETHERITE_SCRAP);

        this.group = MaterialGroup.of(MaterialGroup.DAMAGEABLE);

        this.triggers = new HashMap<>();
        this.triggers.put(PlayerItemDamageEvent.class, new MendingShardTrigger());
    }

    @Override
    public ItemStack getItem() {
        return this.item.clone();
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

    public static MendingShard getInstance() {
        if (instance == null) {
            instance = new MendingShard();
        }
        return instance;
    }

    protected static class MendingShardTrigger implements ModifierTrigger<PlayerInteractEvent> {

        @Override
        public void trigger(PlayerInteractEvent e, MeliorateModifier mod) {
            if (e.getAction() != Action.RIGHT_CLICK_AIR) {
                return;
            }

            final ItemStack item = e.getItem();
            if (item == null) {
                return;
            }

            if (item.getType() == Material.AIR) {
                return;
            }

            if (!(item.getItemMeta() instanceof Damageable dmg)) {
                return;
            }

            final int maxdur = item.getType().getMaxDurability();
            final int dur = maxdur - dmg.getDamage();

            if (dur == maxdur) {
                return;
            }

            final int expCost = (int) Math.ceil((float) dur / (float) maxdur * 1000f);
            if (e.getPlayer().getExp() < expCost) {
                return;
            }

            e.getPlayer().setExp(e.getPlayer().getExp() - expCost);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW,
                    SoundCategory.PLAYERS, 1.0f, 1.0f);

        }

    }

}
