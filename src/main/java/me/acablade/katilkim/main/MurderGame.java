package me.acablade.katilkim.main;

import lombok.Getter;
import lombok.Setter;
import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.events.GamePlayerEvent;
import me.acablade.katilkim.events.GamePlayerDeathEvent;
import me.acablade.katilkim.game.phase.EndPhase;
import me.acablade.katilkim.game.phase.InGamePhase;
import me.acablade.katilkim.game.phase.LobbyPhase;
import me.acablade.katilkim.objects.MurderGameData;
import me.acablade.katilkim.objects.PlayerRole;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MurderGame extends AbstractGame {

	private MurderGameData gameData;

	public MurderGame(String name, JavaPlugin plugin) {
		super(name, plugin);
		this.gameData = new MurderGameData();
		this.gameData.setWorldUID(Bukkit.getWorlds().get(0).getUID());
		setGameData(gameData);
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public void onTick() {
		super.onTick();
	}

	public void processDeath(Player player){
		gameData.getPlayerList().remove(player.getUniqueId());
		gameData.getSpectatorList().add(player.getUniqueId());
		if(this.gameData.getMurderer()==player.getUniqueId()){
			getGameData().setWinnerRole(PlayerRole.INNOCENT);
			endPhase();
			Bukkit.broadcastMessage(getCurrentPhase().getClass().getSimpleName());
			return;
		} else if(this.gameData.getPlayerList().size()==1){
			getGameData().setWinnerRole(PlayerRole.MURDERER);
			endPhase();
			return;
		}

		player.setAllowFlight(true);
		player.setFlying(true);

		if(this.gameData.getBowHolder()==player.getUniqueId()){
			dropBow(player.getLocation());
		}

		gameData.getPlayerList().stream().map(Bukkit::getPlayer).forEach(active -> active.hidePlayer(player));
		gameData.getSpectatorList().stream().map(Bukkit::getPlayer).forEach(passive -> player.showPlayer(passive));

		GamePlayerDeathEvent gamePlayerDeathEvent = new GamePlayerDeathEvent(player, this);
		Bukkit.getPluginManager().callEvent(gamePlayerDeathEvent);

	}

	@Getter
	private BukkitTask bowTask;

	private void dropBow(Location loc){

		ArmorStand armorStand = loc.getWorld().spawn(loc,ArmorStand.class);
		armorStand.getLocation().setDirection(loc.getDirection());
		armorStand.setGravity(false);
		armorStand.setArms(true);
		armorStand.setVisible(false);
		armorStand.setMarker(true);

		armorStand.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
		armorStand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
		armorStand.setCustomNameVisible(true);
		armorStand.setCustomName(ChatColor.translateAlternateColorCodes('&', "&bDETEKTİFİN YAYI"));
		armorStand.addScoreboardTag("bow");


		armorStand.setRightArmPose(new EulerAngle(0,0,Math.toRadians(270)));

		bowTask = (new BukkitRunnable(){
			int i = 0;
			@Override
			public void run() {
				armorStand.setRotation(i%360,0);
				i++;
			}
		}).runTaskTimer(getPlugin(),0,1);
	}

	public void sendBroadcast(String message){
		Set<UUID> allPlayers = new HashSet<>(gameData.getPlayerList());
		allPlayers.addAll(gameData.getSpectatorList());

		allPlayers.stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(player -> {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
		});
	}

	@Override
	public MurderGameData getGameData() {
		return gameData;
	}
}
