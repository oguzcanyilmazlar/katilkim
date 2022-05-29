package me.acablade.katilkim.game.phase;

import lombok.Setter;
import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.impl.MapFeature;
import me.acablade.bladeapi.features.impl.NoBlockBreakFeature;
import me.acablade.bladeapi.features.impl.NoBlockPlaceFeature;
import me.acablade.bladeapi.features.impl.SpawnOnSpawnLocationFeature;
import me.acablade.katilkim.game.features.*;
import me.acablade.katilkim.objects.MurderGameData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.time.Duration;
import java.util.List;

public class InGamePhase extends AbstractPhase {

	private MurderGameData gameData;

	@Setter
	private Duration duration = Duration.ofMinutes(10);

	public InGamePhase(AbstractGame game) {
		super(game);

		this.gameData = (MurderGameData) game.getGameData();

		addFeature(new NoItemDropFeature(this));
		addFeature(new NoBlockBreakFeature(this));
		addFeature(new NoBlockPlaceFeature(this));
		addFeature(new KillPlayerOnHitFeature(this));
		addFeature(new SwordThrowFeature(this));
		addFeature(new BowFeature(this));
		addFeature(new NoChatFeature(this));
		addFeature(new GoldFeature(this));
		addFeature(new TeleportToSpawnPointsFeature(this));
		addFeature(new GiveItemFeature(this));
		addFeature(new KillPlayerOnLeaveFeature(this));
		addFeature(new SetDurationOnPlayerDeathFeature(this));
		addFeature(new AddPlayerOnJoinFeature(this));
		addFeature(new ScoreboardFeature(this));



		World world = Bukkit.getWorld(gameData.getWorldUID());

		List<Location> spawnpointList = getFeature(TeleportToSpawnPointsFeature.class).getSpawnPoints();

		spawnpointList.add(world.getHighestBlockAt(30,30).getLocation().clone().add(0,1,0));
		spawnpointList.add(world.getHighestBlockAt(70,30).getLocation().clone().add(0,1,0));
		spawnpointList.add(world.getHighestBlockAt(30,70).getLocation().clone().add(0,1,0));
		spawnpointList.add(world.getHighestBlockAt(70,70).getLocation().clone().add(0,1,0));

		getFeature(TeleportToSpawnPointsFeature.class).setSpawnPoints(spawnpointList);

		for (int i = -20; i < 20; i++) {
			for (int j = -20; j < 20; j++) {
				getFeature(GoldFeature.class).getSpawnLocations().add(world.getHighestBlockAt(50+i,50+j).getLocation().clone().add(0,1,0));
			}
		}


	}

	@Override
	public Duration duration() {
		return duration;
	}
}
