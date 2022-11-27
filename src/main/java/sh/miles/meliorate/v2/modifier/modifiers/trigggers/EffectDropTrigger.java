package sh.miles.meliorate.v2.modifier.modifiers.trigggers;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import lombok.NonNull;
import sh.miles.meliorate.v2.modifier.MeliorateModifier;
import sh.miles.meliorate.v2.modifier.ModifierTrigger;

public class EffectDropTrigger implements ModifierTrigger<PlayerDropItemEvent> {

    private final PotionEffectType effect;

    public EffectDropTrigger(@NonNull final PotionEffectType effect) {
        this.effect = effect;
    }

    @Override
    public void trigger(PlayerDropItemEvent e, MeliorateModifier mod) {
        final ItemStack item = e.getItemDrop().getItemStack();
        if (mod.hasModifier(item)) {
            e.getPlayer().removePotionEffect(effect);
        }
    }

}
