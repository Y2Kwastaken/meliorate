package sh.miles.meliorate.v2.modifier.modifiers.trigggers;

import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lombok.NonNull;
import sh.miles.meliorate.v2.modifier.MeliorateModifier;
import sh.miles.meliorate.v2.modifier.ModifierTrigger;

public class EffectHeldTrigger implements ModifierTrigger<PlayerItemHeldEvent> {

    private final PotionEffectType effect;

    public EffectHeldTrigger(@NonNull final PotionEffectType effect) {
        this.effect = effect;
    }

    @Override
    public void trigger(PlayerItemHeldEvent e, MeliorateModifier mod) {

        final ItemStack item = e.getPlayer().getInventory().getItem(e.getNewSlot());

        if (item == null || item.getType().isAir()) {
            e.getPlayer().removePotionEffect(effect);
            return;
        }

        if (mod.hasModifier(item)) {
            final PotionEffect potionEffect = effect.createEffect(Integer.MAX_VALUE, mod.getModifierLevel(item) - 1);
            e.getPlayer().addPotionEffect(potionEffect);
        } else {
            e.getPlayer().removePotionEffect(effect);
        }
    }

}
