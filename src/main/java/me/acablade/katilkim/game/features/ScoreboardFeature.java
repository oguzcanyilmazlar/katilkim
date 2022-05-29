package me.acablade.katilkim.game.features;

import fr.mrmicky.fastboard.FastBoard;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.katilkim.game.phase.InGamePhase;
import me.acablade.katilkim.objects.MurderGameData;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreboardFeature extends AbstractFeature {

	private static final Map<UUID, FastBoard> boards = new HashMap<>();

	private final MurderGameData gameData;

	public ScoreboardFeature(AbstractPhase abstractPhase) {
		super(abstractPhase);
		this.gameData = (MurderGameData) abstractPhase.getGame().getGameData();
	}

	@Override
	public void onTick() {
		super.onTick();
		for (FastBoard board : boards.values()) {
			updateBoard(board);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event){

		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();

		if(boards.containsKey(uuid)) return;

		FastBoard fastBoard = new FastBoard(player);
		fastBoard.updateTitle("§cLav Yükseliyor");

		boards.put(uuid,fastBoard);

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		FastBoard board = boards.remove(player.getUniqueId());

		if (board != null) {
			board.delete();
		}
	}

	private void updateBoard(FastBoard board) {

		List<String> lines;
		String title = color("&e&lKATIL KIM");;

		if(getAbstractPhase() instanceof InGamePhase){
			String detLine = "&cYay düştü!";
			if(gameData.getBowHolder()!=null)
				detLine = gameData.getBowHolder()==gameData.getSupposedDetective() ? "&aHayatta" : "&aYay alındı";
			lines = Arrays.asList(
					"",
					color("&eOyuncu: " + getAbstractPhase().getGame().getGameData().getPlayerList().size()),
					"",
					color("&eDetektif: " + detLine),
					"",
					color("&eKalan Zaman: " + DurationFormatUtils.formatDuration(getAbstractPhase().timeLeft().toMillis(), "mm:ss", true)),
					""
			);

		}else{
			lines = Arrays.asList(
					"",
					color("&eLOBI"),
					""
			);
		}

		board.updateTitle(title);
		board.updateLines(lines);

	}

	private String color(String msg){
		return ChatColor.translateAlternateColorCodes('&',msg);
	}

}
