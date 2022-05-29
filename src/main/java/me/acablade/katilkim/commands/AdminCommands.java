package me.acablade.katilkim.commands;

import me.acablade.bladecommandframework.annotations.CommandInfo;
import me.acablade.bladecommandframework.classes.CommandActor;
import me.acablade.katilkim.Katilkim;
import me.acablade.katilkim.game.features.BowFeature;
import org.bukkit.entity.Player;

@CommandInfo(commandName = "admin", permission = "katilkim.admin")
public class AdminCommands {

	private final Katilkim plugin;

	public AdminCommands(Katilkim plugin) {
		this.plugin = plugin;
	}


	@CommandInfo(commandName = "skip")
	public void skip(CommandActor actor){
		plugin.getGame().endPhase();
	}

	@CommandInfo(commandName = "freeze")
	public void freeze(CommandActor actor){
		plugin.getGame().setFrozen(true);
	}

	@CommandInfo(commandName = "setmurderer")
	public void setMurderer(CommandActor actor, Player player){
		plugin.getGame().getGameData().setMurderer(player.getUniqueId());
	}

	@CommandInfo(commandName = "givebow")
	public void giveBow(CommandActor actor, Player player){
		plugin.getGame().getCurrentPhase().getFeature(BowFeature.class).addBow(player);
	}

	@CommandInfo(commandName = "setdet")
	public void setDet(CommandActor actor, Player player){
		plugin.getGame().getGameData().setBowHolder(player.getUniqueId());
	}

}
