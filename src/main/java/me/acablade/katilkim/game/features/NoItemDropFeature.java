package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class NoItemDropFeature extends AbstractFeature {
	public NoItemDropFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
	}


	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		event.setCancelled(true);
	}
}
