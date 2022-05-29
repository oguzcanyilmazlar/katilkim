package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class NoChatFeature extends AbstractFeature {
	public NoChatFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		event.setCancelled(true);
	}
}
