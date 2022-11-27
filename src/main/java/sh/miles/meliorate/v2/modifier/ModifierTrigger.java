package sh.miles.meliorate.v2.modifier;

import org.bukkit.event.Event;

@FunctionalInterface
public interface ModifierTrigger<T extends Event> {
    
    void trigger(T event, MeliorateModifier modifier);

}
