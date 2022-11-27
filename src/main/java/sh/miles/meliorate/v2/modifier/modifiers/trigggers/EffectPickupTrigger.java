package sh.miles.meliorate.v2.modifier.modifiers.trigggers;

import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import sh.miles.meliorate.v2.modifier.MeliorateModifier;
import sh.miles.meliorate.v2.modifier.ModifierTrigger;

public class EffectPickupTrigger implements ModifierTrigger<EntityPickupItemEvent> {

    private final PotionEffectType effect;

    public EffectPickupTrigger(PotionEffectType effect) {
        this.effect = effect;
    }

    @Override
    public void trigger(EntityPickupItemEvent e, MeliorateModifier mod) {
        final PotionEffect potionEffect = effect.createEffect(Integer.MAX_VALUE,
                mod.getModifierLevel(e.getItem().getItemStack()) - 1);
        e.getEntity().addPotionEffect(potionEffect);
    }

}
