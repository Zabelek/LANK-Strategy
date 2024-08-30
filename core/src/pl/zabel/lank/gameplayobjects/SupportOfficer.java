package pl.zabel.lank.gameplayobjects;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;

public class SupportOfficer {
	
	private String name;
	private int level, availablePoints, exp, attack, deffence, flair;
	private StarSystem currentSystem;
	private boolean isLocked;
	
	public SupportOfficer(String name)
	{
		this.name = name;
		this.level = 1;
		this.availablePoints = 2;
		this.attack = 2;
		this.deffence = 2;
		this.flair = 2;
		this.exp = 0;
		this.isLocked = true;
	}

	public int getAvailablePoints() {
		return availablePoints;
	}

	public void setAvailablePoints(int availablePoints) {
		this.availablePoints = availablePoints;
		GameSession.notifyObservers();
	}

	public int getExp() {
		return exp;
	}

	public void addExp(int exp) {
		this.exp += exp;
		while(this.exp>(500*this.level))
		{
			this.level++;
			this.availablePoints+=2;
			this.exp-=500*this.level;
		}
		GameSession.notifyObservers();
	}
	public void setLevel(int value)
	{
		this.level=value;
	}
	public int getAttack() {
		return attack + GameSession.generateGameplayDataReport().soAttackUpgCount;
	}
	public int getRawAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
		GameSession.notifyObservers();
	}

	public int getDeffence() {
		
		return deffence + GameSession.generateGameplayDataReport().soDeffenceUpgCount;
	}
	public int getRawDeffence() {
		return deffence;
	}
	public void setDeffence(int deffence) {
		this.deffence = deffence;
		GameSession.notifyObservers();
	}

	public int getFlair() {
		if(this.getName()=="Anna" || this.getName()=="Zack")
			return flair + GameSession.generateGameplayDataReport().soCyborgFlairUpgCount;
		else
			return flair + GameSession.generateGameplayDataReport().soMutantFlairUpgCount;
	}
	public int getRawFlair() {
		return flair;
	}

	public void setFlair(int flair) {
		this.flair = flair;
		GameSession.notifyObservers();
	}

	public StarSystem getCurrentSystem() {
		return currentSystem;
	}

	public void setCurrentSystem(StarSystem currentSystem) {
		this.currentSystem = currentSystem;
		GameSession.notifyObservers();
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
		GameSession.notifyObservers();
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}
	

}
