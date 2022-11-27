package sh.miles.meliorate.v2.dispatcher;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import sh.miles.meliorate.v2.modifier.modifiers.Modifiers;

public class ItemHeldDispatcher implements Listener{
    
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent e){
        Modifiers.HASTE_SHARD.execute(e);
    }
    
}
