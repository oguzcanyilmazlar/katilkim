package me.acablade.katilkim.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.PlayerJoinGameEvent;
import me.acablade.bladeapi.features.AbstractFeature;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * @author Acablade/oz
 */
public class AddPlayerOnJoinFeature extends AbstractFeature {

    public AddPlayerOnJoinFeature(AbstractPhase abstractPhase) {
        super(abstractPhase);
    }

    @Override
    public void onEnable() {
        (new BukkitRunnable(){

            @Override
            public void run() {
                for(Player player:Bukkit.getOnlinePlayers()){
                    getAbstractPhase().getGame().getGameData().getPlayerList().add(player.getUniqueId());
                    Bukkit.getPluginManager().callEvent(new PlayerJoinGameEvent(player, getAbstractPhase().getGame()));

                    ArmorStand snowball = player.getWorld().spawn(player.getLocation().clone().add(0,10,0),ArmorStand.class);
                    snowball.setGravity(false);
                    snowball.setVisible(false);
                    snowball.setMarker(true);

                    player.addPassenger(snowball);

                    player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    player.setGameMode(GameMode.ADVENTURE);
                    player.setFoodLevel(20);
                }
            }
        }).runTaskLater(getAbstractPhase().getGame().getPlugin(),3);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        getAbstractPhase().getGame().getGameData().getPlayerList().add(uuid);
        Bukkit.getPluginManager().callEvent(new PlayerJoinGameEvent(player, getAbstractPhase().getGame()));

        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);


    }


}
