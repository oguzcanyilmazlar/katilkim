package me.acablade.katilkim.game.phase;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.katilkim.main.MurderGame;
import me.acablade.katilkim.objects.MurderGameData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class EndPhase extends AbstractPhase {

	private final MurderGameData gameData;
	private final MurderGame game;

	public EndPhase(AbstractGame game) {
		super(game);
		this.game = (MurderGame) game;
		this.gameData = (MurderGameData) game.getGameData();

	}

	@Override
	public void onEnable() {
		game.sendBroadcast("");
		switch (gameData.getWinnerRole()){
			case INNOCENT:
				game.sendBroadcast("&aMASUMLAR &eKAZANDI");
				break;
			case MURDERER:
				game.sendBroadcast("&cKATİL &eKAZANDI");
				break;
		}
		game.sendBroadcast("");
	}

	@Override
	public Duration duration() {
		return Duration.ofSeconds(5);
	}


}
