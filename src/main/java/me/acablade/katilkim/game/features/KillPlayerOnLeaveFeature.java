package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.katilkim.main.MurderGame;
import me.acablade.katilkim.objects.MurderGameData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class KillPlayerOnLeaveFeature extends AbstractFeature {

	private final MurderGame game;

	public KillPlayerOnLeaveFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
		this.game = (MurderGame) getAbstractPhase().getGame();
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if(game.getGameData().getPlayerList().contains(player.getUniqueId()))
			game.processDeath(player);
	}

}
