package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.katilkim.objects.MurderGameData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItemFeature extends AbstractFeature {

	private final MurderGameData gameData;

	public GiveItemFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
		this.gameData = (MurderGameData) abstractPhase.getGame().getGameData();
	}

	@Override
	public void onEnable() {

		Player bowHolder = Bukkit.getPlayer(gameData.getBowHolder());
		getAbstractPhase().getFeature(BowFeature.class).addBow(bowHolder);

		Bukkit.getScheduler().runTaskLater(getAbstractPhase().getGame().getPlugin(),() -> {
			Player player = Bukkit.getPlayer(gameData.getMurderer());
			player.getInventory().setItem(1,new ItemStack(Material.IRON_SWORD));
		}, gameData.getSwordCdStart()*20);
	}
}
