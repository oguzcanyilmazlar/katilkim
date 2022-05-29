package me.acablade.katilkim.game.phase;

import lombok.Setter;
import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.impl.MapFeature;
import me.acablade.bladeapi.features.impl.NoBlockBreakFeature;
import me.acablade.bladeapi.features.impl.NoBlockPlaceFeature;
import me.acablade.bladeapi.features.impl.NoHitFeature;
import me.acablade.katilkim.game.features.AddPlayerOnJoinFeature;
import me.acablade.katilkim.game.features.RoleSelectFeature;
import me.acablade.katilkim.game.features.ScoreboardFeature;

import java.time.Duration;

public class LobbyPhase extends AbstractPhase {

	@Setter
	private Duration duration = Duration.ofMinutes(1);

	public LobbyPhase(AbstractGame game) {

		super(game);

		addFeature(new NoHitFeature(this));
		addFeature(new NoBlockPlaceFeature(this));
		addFeature(new NoBlockBreakFeature(this));
		addFeature(new MapFeature(this));
		addFeature(new AddPlayerOnJoinFeature(this));
		addFeature(new RoleSelectFeature(this));
		addFeature(new ScoreboardFeature(this));


	}

	@Override
	public void onDisable() {
		/*if(getGame().getGameData().getPlayerList().size()<=2){
			getGame().addPhaseNext(LobbyPhase.class);
			return;
		}*/


	}

	@Override
	public Duration duration() {
		return duration;
	}
}
