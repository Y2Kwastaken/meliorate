package sh.miles.meliorate.v2.modifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import lombok.Getter;
import lombok.NonNull;
import sh.miles.meliorate.v2.Meliorate;
import sh.miles.meliorate.v2.material.MaterialGroup;

public class MeliorateModifier {

    private final ItemStack item;
    private final String lore;
    @Getter
    private final int maxLevel;
    @Getter
    private final NamespacedKey modifierKey;
    @Getter
    private final NamespacedKey loreKey;
    private final ShapedRecipe recipe;
    private final MaterialGroup group;
    private final Map<Class<? extends Event>, ModifierTrigger<? extends Event>> triggers;

    @NonNull
    public MeliorateModifier(final IModifier modifier) {
        this.item = modifier.getItem();
        this.lore = modifier.getLore();
        this.maxLevel = modifier.getMaxLevel();
        this.modifierKey = new NamespacedKey(Meliorate.getInstance(), modifier.getModifierKey());
        this.loreKey = new NamespacedKey(Meliorate.getInstance(), modifier.getLoreKey());
        this.recipe = modifier.getRecipe();
        this.group = modifier.getGroup();
        this.triggers = modifier.getTriggers();
    }

    public boolean apply(final ItemStack stack) {

        if (!this.group.contains(stack.getType())) {
            return false;
        }

        if (!this.hasModifier(stack)) {
            this.applyLore(stack, 1);
            this.setModifierLevel(stack, 1);
            return true;
        }

        final int level = this.getModifierLevel(stack);
        if (level + 1 > this.maxLevel) {
            return false;
        }

        this.applyLore(stack, level + 1);
        this.setModifierLevel(stack, level + 1);
        return true;
    }

    protected void applyLore(final ItemStack stack, final int level) {
        final ItemMeta meta = stack.getItemMeta();
        List<String> lores = meta.getLore();
        if (lores == null) {
            lores = new ArrayList<>();
        }

        if (!this.hasModifier(stack)) {
            lores.add(lore.replace("%level%", String.valueOf(level)));
            meta.getPersistentDataContainer().set(loreKey, PersistentDataType.INTEGER, lores.size() - 1);
            meta.setLore(lores);
            stack.setItemMeta(meta);
        }

        final int oldLine = meta.getPersistentDataContainer().get(loreKey, PersistentDataType.INTEGER);
        lores.set(oldLine, lore.replace("%level%", String.valueOf(level)));
        meta.setLore(lores);
        stack.setItemMeta(meta);
    }

    public boolean hasModifier(final ItemStack stack) {
        return stack.getItemMeta().getPersistentDataContainer().has(modifierKey, PersistentDataType.INTEGER);
    }

    public int getModifierLevel(final ItemStack stack) {
        return stack.getItemMeta().getPersistentDataContainer().get(modifierKey, PersistentDataType.INTEGER);
    }

    protected void setModifierLevel(final ItemStack stack, final int level) {
        if (level > maxLevel) {
            throw new IllegalArgumentException("Level for modifier cannot be greater than max level");
        }
        final ItemMeta meta = stack.getItemMeta();
        final PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(modifierKey, PersistentDataType.INTEGER, level);
        stack.setItemMeta(meta);
    }

    @SuppressWarnings("unchecked")
    public void execute(final Event event) {
        final Class<Event> eventClass = (Class<Event>) event.getClass();
        if (triggers.containsKey(eventClass)) {
            // this cast will cause error I think
            final ModifierTrigger<Event> trigger = (ModifierTrigger<Event>) triggers.get(eventClass);
            trigger.trigger(event, this);
        } else {
            throw new IllegalArgumentException("Event " + eventClass.getName() + " is not supported by this modifier");
        }
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public void registerRecipe() {
        Meliorate.getInstance().getServer().addRecipe(recipe);
    }

    public NamespacedKey getRecipeKey() {
        return recipe.getKey();
    }

}
