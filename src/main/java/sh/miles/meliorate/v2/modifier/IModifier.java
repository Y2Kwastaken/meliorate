package sh.miles.meliorate.v2.modifier;

import java.util.Map;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import sh.miles.meliorate.v2.material.MaterialGroup;

public interface IModifier {

    ItemStack getItem();

    String getLore();

    int getMaxLevel();

    String getModifierKey();

    String getLoreKey();

    ShapedRecipe getRecipe();

    MaterialGroup getGroup();

    Map<Class<? extends Event>, ModifierTrigger<? extends Event>> getTriggers();

}
