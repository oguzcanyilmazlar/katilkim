package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.katilkim.main.MurderGame;
import me.acablade.katilkim.objects.MurderGameData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BowFeature extends AbstractFeature {

	private final MurderGameData gameData;
	private final MurderGame game;

	public BowFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
		this.game = (MurderGame) abstractPhase.getGame();
		this.gameData = (MurderGameData) this.game.getGameData();

	}

	@EventHandler
	public void onShoot(ProjectileLaunchEvent event){
		if(!(event.getEntity().getShooter() instanceof Player)) return;

		Player player = (Player) event.getEntity().getShooter();

		Bukkit.getScheduler().runTaskLater(getAbstractPhase().getGame().getPlugin(),() -> {
			player.getInventory().addItem(new ItemStack(Material.ARROW));
		},gameData.getBowCd()*20);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event){
		if(gameData.getBowLoc()==null) return;
		Player player = event.getPlayer();
		if(gameData.getMurderer()==player.getUniqueId()) return;
		if(!canPickup(event.getTo(),gameData.getBowLoc())) return;

		gameData.setBowHolder(player.getUniqueId());

		game.sendBroadcast("");
		game.sendBroadcast("&eDETEKTİFİN YAYI ALINDI");
		game.sendBroadcast("");

		addBow(player);

		Arrays.stream(event.getTo().getChunk().getEntities()).filter(entity -> entity.getScoreboardTags().contains("bow")).forEach(Entity::remove);
	}

	private boolean canPickup(Location location1, Location location2){
		return location1.distanceSquared(location2)<=3;
	}

	public void addBow(Player player){
		if(!player.getInventory().contains(Material.BOW))
			player.getInventory().addItem(new ItemStack(Material.BOW));
		player.getInventory().addItem(new ItemStack(Material.ARROW));
	}
}
