package sh.miles.meliorate.v2.modifier.modifiers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.inventory.ItemStack;

import sh.miles.meliorate.v2.modifier.MeliorateModifier;

public class Modifiers {

    public static final MeliorateModifier LAVA_CRYSTAL = new MeliorateModifier(LavaCrystal.getInstance());
    public static final MeliorateModifier HASTE_SHARD = new MeliorateModifier(HasteShard.getInstance());

    private static final Map<ItemStack, MeliorateModifier> MODIFIERS = new HashMap<>();

    static {
        MODIFIERS.put(LAVA_CRYSTAL.getItem(), LAVA_CRYSTAL);
        MODIFIERS.put(HASTE_SHARD.getItem(), HASTE_SHARD);
    }

    private Modifiers() {
    }

    public static boolean isModifier(ItemStack item) {
        final ItemStack clone = item.clone();
        clone.setAmount(1);
        return MODIFIERS.containsKey(clone);
    }

    public static MeliorateModifier getModifier(ItemStack item) {
        final ItemStack clone = item.clone();
        clone.setAmount(1);
        return MODIFIERS.get(clone);
    }

    public static Optional<MeliorateModifier> getPossibleModifier(ItemStack item) {
        return Optional.ofNullable(getModifier(item));
    }

}
