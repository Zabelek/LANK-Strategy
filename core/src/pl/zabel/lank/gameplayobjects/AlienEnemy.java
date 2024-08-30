package pl.zabel.lank.gameplayobjects;

import com.badlogic.gdx.math.MathUtils;

import pl.zabel.lank.GameSession;

public class AlienEnemy {

	private int agressionLevel, power;

	public AlienEnemy() {
		this.setAgressionLevel(50);
		this.setPower(10);
	}

	public void takeTurnAction() {
		int random = MathUtils.random(1, 255);
		{
			if (random < agressionLevel) {
				this.performAttack();
			}
		}
	}

	private void performAttack() {
		if (!GameSession.getRegisteredCities().isEmpty()) {
			int cityPick = MathUtils.random(0, GameSession.getRegisteredCities().size() - 1);
			if (chechIfAlreadySieged(GameSession.getRegisteredCities().get(cityPick)) == false) {
				if (GameSession.getRegisteredCities().get(cityPick).isHasIntel()) {
					if (MathUtils.random(1, 2) == 1) {
						GameSession.addNewSiege(GameSession.getRegisteredCities().get(cityPick), power);
					}
				} else {
					GameSession.addNewSiege(GameSession.getRegisteredCities().get(cityPick), power);
				}
			}
		}
	}

	private boolean chechIfAlreadySieged(City givenCity) {
		for (Siege siege : GameSession.getActiveSieges()) {
			if (siege.getCity() == givenCity)
				return true;
		}
		return false;
	}

	public int getAgressionLevel() {
		return agressionLevel;
	}

	public void setAgressionLevel(int agressionLevel) {
		this.agressionLevel = agressionLevel;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

}
