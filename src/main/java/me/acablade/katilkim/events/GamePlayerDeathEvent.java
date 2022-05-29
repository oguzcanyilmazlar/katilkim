package me.acablade.katilkim.events;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.events.GamePlayerEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class GamePlayerDeathEvent extends GamePlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();

	public GamePlayerDeathEvent(Player player, AbstractGame abstractGame) {
		super(player, abstractGame);
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
