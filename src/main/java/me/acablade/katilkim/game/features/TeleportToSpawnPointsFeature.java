package me.acablade.katilkim.game.features;

import lombok.Getter;
import lombok.Setter;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TeleportToSpawnPointsFeature extends AbstractFeature {

	@Getter
	@Setter
	private List<Location> spawnPoints = new ArrayList<>();

	public TeleportToSpawnPointsFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
	}

	@Override
	public void onEnable() {

		for (int i = 0; i < getAbstractPhase().getGame().getGameData().getPlayerList().size(); i++) {
			Player player = Bukkit.getPlayer(getAbstractPhase().getGame().getGameData().getPlayerList().toArray(new UUID[0])[i]);
			player.teleport(spawnPoints.get(i));
		}

	}

}
