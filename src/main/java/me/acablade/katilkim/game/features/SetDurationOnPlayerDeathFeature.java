package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.katilkim.events.GamePlayerDeathEvent;
import me.acablade.katilkim.game.phase.InGamePhase;
import org.bukkit.event.EventHandler;

import java.time.Duration;

public class SetDurationOnPlayerDeathFeature extends AbstractFeature {

	private final InGamePhase phase;

	public SetDurationOnPlayerDeathFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
		this.phase = (InGamePhase) abstractPhase;
	}


	@EventHandler
	public void onDeath(GamePlayerDeathEvent event){
		if(phase.getGame().getGameData().getPlayerList().size()==5)
			phase.setDuration(Duration.ofMinutes(3));
	}
}
