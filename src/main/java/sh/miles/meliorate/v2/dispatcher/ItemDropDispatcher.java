package sh.miles.meliorate.v2.dispatcher;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import sh.miles.meliorate.v2.modifier.modifiers.Modifiers;

public class ItemDropDispatcher implements Listener{
    
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        Modifiers.HASTE_SHARD.execute(e);
    }

}
