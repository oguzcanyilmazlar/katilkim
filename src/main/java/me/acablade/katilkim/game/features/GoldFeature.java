package me.acablade.katilkim.game.features;

import lombok.Getter;
import lombok.Setter;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GoldFeature extends AbstractFeature {

	private long lastSpawnTick;

	private int goldAmount;


	@Getter
	private final List<Location> spawnLocations;

	public GoldFeature(AbstractPhase abstractPhase) {
		this(abstractPhase,new ArrayList<>());
	}

	public GoldFeature(AbstractPhase phase, List<Location> spawnLocations){
		super(phase);
		this.spawnLocations = spawnLocations;
	}

	@Override
	public void onTick() {
		if(goldAmount>=15) return;
		if(lastSpawnTick>=15*20){
			lastSpawnTick=0;
			Location randomLoc = spawnLocations.get(ThreadLocalRandom.current().nextInt(spawnLocations.size()-1));
			randomLoc.getWorld().dropItem(randomLoc, new ItemStack(Material.GOLD_INGOT));
			goldAmount++;
		}
		lastSpawnTick++;
	}

	@EventHandler
	public void onPickup(EntityPickupItemEvent event){
		if(!(event.getEntity() instanceof Player)){
			event.setCancelled(true);
			return;
		}
		if(event.getItem().getItemStack().getType() != Material.GOLD_INGOT){
			event.setCancelled(true);
			return;
		}

		Player player = (Player) event.getEntity();

		if(player.getInventory().contains(Material.GOLD_INGOT, 9)){
			removeItems(player.getInventory(),Material.GOLD_INGOT,10);
			getAbstractPhase().getFeature(BowFeature.class).addBow(player);
			player.updateInventory();
		}

		goldAmount--;

	}

	public void removeItems(Inventory inventory, Material type, int amount) {
		if (amount <= 0) return;
		int size = inventory.getSize();
		for (int slot = 0; slot < size; slot++) {
			ItemStack is = inventory.getItem(slot);
			if (is == null) continue;
			if (type == is.getType()) {
				int newAmount = is.getAmount() - amount;
				if (newAmount > 0) {
					is.setAmount(newAmount);
					break;
				} else {
					inventory.clear(slot);
					amount = -newAmount;
					if (amount == 0) break;
				}
			}
		}
	}
}
