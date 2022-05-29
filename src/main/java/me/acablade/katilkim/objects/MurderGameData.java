package me.acablade.katilkim.objects;

import lombok.Getter;
import lombok.Setter;
import me.acablade.bladeapi.objects.GameData;
import org.bukkit.Location;

import java.util.UUID;

public class MurderGameData extends GameData {

	@Getter
	@Setter
	private PlayerRole winnerRole;

	@Getter
	@Setter
	private UUID worldUID;

	@Getter
	@Setter
	private UUID murderer = null;

	@Getter
	@Setter
	private UUID bowHolder = null;

	@Getter
	@Setter
	private UUID supposedDetective;

	@Getter
	@Setter
	private long bowCd = 5;

	@Getter
	@Setter
	private long swordCdStart = 15;

	@Getter
	@Setter
	private long swordCd = 10;

	@Getter
	@Setter
	private Location bowLoc;

}
