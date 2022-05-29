package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.katilkim.objects.MurderGameData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RoleSelectFeature extends AbstractFeature {

	private final MurderGameData gameData;

	public RoleSelectFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
		this.gameData = (MurderGameData) abstractPhase.getGame().getGameData();
	}

	@Override
	public void onDisable() {

		Set<UUID> playerSet = new HashSet<>(gameData.getPlayerList());

		if(gameData.getMurderer() == null) {
			UUID murderer = playerSet.toArray(new UUID[0])[ThreadLocalRandom.current().nextInt(playerSet.size())];
			playerSet.remove(murderer);
			gameData.setMurderer(murderer);
			Bukkit.getPlayer(murderer).sendMessage(ChatColor.translateAlternateColorCodes('&',"&eKatil olarak atandın"));
		}

		if(gameData.getBowHolder() == null){
			UUID bowHolder = playerSet.toArray(new UUID[0])[ThreadLocalRandom.current().nextInt(playerSet.size())];
			playerSet.remove(bowHolder);
			gameData.setBowHolder(bowHolder);
			gameData.setSupposedDetective(bowHolder);
			Bukkit.getPlayer(bowHolder).sendMessage(ChatColor.translateAlternateColorCodes('&',"&eDetektif olarak atandın"));
		}







	}
}
