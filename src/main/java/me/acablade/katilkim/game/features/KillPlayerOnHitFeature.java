package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.katilkim.main.MurderGame;
import me.acablade.katilkim.objects.MurderGameData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class KillPlayerOnHitFeature extends AbstractFeature {

	private MurderGameData gameData;
	private MurderGame game;

	public KillPlayerOnHitFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
		this.game = (MurderGame) abstractPhase.getGame();
		this.gameData = (MurderGameData) game.getGameData();
	}


	@EventHandler(ignoreCancelled = true)
	public void onEntityDamage(EntityDamageByEntityEvent event){
		if(!(event.getEntity() instanceof Player)){
			event.setCancelled(true);
			return;
		}
		Player target = (Player) event.getEntity();
		Player damager;
		if(event.getDamager() instanceof Projectile){
			damager = (Player) ((Projectile)event.getDamager()).getShooter();
		}
		else if(event.getDamager() instanceof Player){
			damager = (Player) event.getDamager();
		}
		else{
			event.setCancelled(true);
			return;
		}
		if(damager == null) return;
		if(damager.getEquipment() == null) return;
		ItemStack itemStack = damager.getEquipment().getItemInMainHand();
		if(itemStack.getType().isAir() || itemStack.getType() == Material.GOLD_INGOT){
			event.setCancelled(true);
			return;
		}
		game.processDeath(target);
		if(gameData.getMurderer()!=target.getUniqueId()&&gameData.getMurderer()!=damager.getUniqueId())
			game.processDeath(damager);

	}

}
