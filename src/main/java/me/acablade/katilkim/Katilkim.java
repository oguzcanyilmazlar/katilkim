package me.acablade.katilkim;

import lombok.Getter;
import me.acablade.bladecommandframework.classes.BaseCommandHandler;
import me.acablade.katilkim.commands.AdminCommands;
import me.acablade.katilkim.game.phase.EndPhase;
import me.acablade.katilkim.game.phase.InGamePhase;
import me.acablade.katilkim.game.phase.LobbyPhase;
import me.acablade.katilkim.main.MurderGame;
import org.bukkit.plugin.java.JavaPlugin;

public final class Katilkim extends JavaPlugin {

	@Getter
	private MurderGame game;

	@Override
	public void onEnable() {
		// Plugin startup logic

		BaseCommandHandler commandHandler = new BaseCommandHandler();
		commandHandler.registerCommand(new AdminCommands(this));

		game = new MurderGame("katil_kim",this);

		game.addPhase(LobbyPhase.class);
		game.addPhase(InGamePhase.class);
		game.addPhase(EndPhase.class);

		game.enable(0,1);

	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
}
