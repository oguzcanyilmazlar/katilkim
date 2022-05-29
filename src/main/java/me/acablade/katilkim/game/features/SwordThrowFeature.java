package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.katilkim.main.MurderGame;
import me.acablade.katilkim.objects.MurderGameData;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class SwordThrowFeature extends AbstractFeature {

	private MurderGameData gameData;
	private MurderGame game;

	private long throwCd = System.currentTimeMillis();

	public SwordThrowFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
		this.gameData = (MurderGameData) getAbstractPhase().getGame().getGameData();
		this.game = (MurderGame) getAbstractPhase().getGame();
	}


	@EventHandler
	public void onInteract(PlayerInteractEvent event){
		if(throwCd>System.currentTimeMillis()) return;
		if(!this.gameData.getMurderer().equals(event.getPlayer().getUniqueId())) return;
		if(event.getHand() != EquipmentSlot.HAND) return;
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(event.getItem() == null) return;
		if(event.getItem().getType() != Material.IRON_SWORD) return;
		event.setCancelled(true);

		Player player = event.getPlayer();

		ArmorStand armorStand = player.getWorld().spawn(player.getLocation(),ArmorStand.class);
		armorStand.getLocation().setDirection(player.getLocation().getDirection());
		armorStand.setGravity(false);
		armorStand.setArms(true);
		armorStand.setVisible(false);
		armorStand.setMarker(true);

		armorStand.setRightArmPose(new EulerAngle(Math.toRadians(325), 0, Math.toRadians(270)));
		armorStand.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
		armorStand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
		armorStand.addScoreboardTag("thrown");

		throwCd = System.currentTimeMillis()+gameData.getSwordCd()*1000;

		(new BukkitRunnable(){

			int i = 0;

			final Location clonedLoc = armorStand.getLocation().clone();

			@Override
			public void run() {

				i++;
				if(i>=40){
					cancel();
					armorStand.remove();
					return;
				}

				armorStand.teleport(clonedLoc.add(clonedLoc.getDirection().multiply(i*.1)));

				for(Entity entity : armorStand.getNearbyEntities(.5,.5,.5)){
					if(!(entity instanceof Player)) continue;
					Player target = (Player) entity;
					if(target==player) continue;
					game.processDeath(target);
				}

			}
		}).runTaskTimer(game.getPlugin(),0,1);


	}


}
