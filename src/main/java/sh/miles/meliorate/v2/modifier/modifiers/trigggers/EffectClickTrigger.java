package sh.miles.meliorate.v2.modifier.modifiers.trigggers;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lombok.NonNull;
import sh.miles.meliorate.v2.modifier.MeliorateModifier;
import sh.miles.meliorate.v2.modifier.ModifierTrigger;

public class EffectClickTrigger implements ModifierTrigger<InventoryClickEvent> {

    private final PotionEffectType effect;

    public EffectClickTrigger(@NonNull final PotionEffectType effect) {
        this.effect = effect;
    }

    @Override
    public void trigger(InventoryClickEvent e, MeliorateModifier mod) {
        final ItemStack item = e.getWhoClicked().getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            e.getWhoClicked().removePotionEffect((effect));
            return;
        }

        if (mod.hasModifier(item)) {
            final PotionEffect potionEffect = effect.createEffect(Integer.MAX_VALUE, mod.getModifierLevel(item) - 1);
            e.getWhoClicked().addPotionEffect(potionEffect);
        } else {
            e.getWhoClicked().removePotionEffect(effect);
        }

    }

}
