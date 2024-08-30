package pl.zabel.lank.gameplayobjects;

import com.badlogic.gdx.math.MathUtils;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem.CaptureState;

public class Siege {
	
	private City city;
	private SupportOfficer officer;
	private int turnsToStart, turnsToEnd, attackerAttack, attackerDeffence, officersBonus;
	private boolean won;
	
	public Siege(City city, int power)
	{
		this.city = city;
		turnsToEnd = 420;
		if(this.city.isHasIntel())
			turnsToStart = 4;
		else
			turnsToStart = 0;
		this.attackerAttack = MathUtils.random(power/2, power);
		this.attackerDeffence = MathUtils.random(power/2, power);
		officersBonus = 0;
		doSingleTick();
	}
	public void doSingleTick()
	{
		if(turnsToStart<=0)
		{
			if(turnsToEnd==420)
			{
				if (this.city.isHasWall())
					turnsToEnd=3;
				else
					turnsToEnd=1;
			}
			else
			{
				if(this.officer!=null)
				{
					if(this.city.isHasWall())
						officersBonus += 20;
					endSiege();
				}
				else {
					turnsToEnd--;
					if(turnsToEnd<=0)
						endSiege();
				}
			}
		}
		else 
		{
			turnsToStart--;
			if(officer!=null)
			{
				officersBonus+=10;
			}
		}
	}
	private void endSiege() {
		if(officer==null)
		{
			GameSession.getRegisteredCities().remove(city);
			city.getSystem().setCaptureState(CaptureState.AVAILABLE, null);
			GameSession.getActiveSieges().remove(this);
			this.won=false;
		}
		else if(!getFightResult())
		{
			GameSession.getRegisteredCities().remove(city);
			city.getSystem().setCaptureState(CaptureState.AVAILABLE, null);
			officer.setCurrentSystem(null);
			GameSession.getActiveSieges().remove(this);
			this.won=false;
		}
		else
		{
			officer.addExp(20*attackerAttack+20*attackerDeffence);
			officer.setCurrentSystem(null);
			GameSession.getActiveSieges().remove(this);
			this.won=true;
		}
		GameSession.getEndedSieges().add(this);
		
	}
	public City getCity() {
		return city;
	}
	public boolean alreadyStarted()
	{
		if(this.turnsToEnd!=420) return true;
		else return false;
	}
	public SupportOfficer getOfficer() {
		return officer;
	}
	public void setOfficer(SupportOfficer officer) {
		this.officer = officer;
	}
	private boolean getFightResult()
	{
		int enemyhp = attackerDeffence;
		int defenderhp = officer.getDeffence()+officersBonus;
		int dodgeChance = officer.getFlair()-attackerAttack/2;
		while(enemyhp>0 && defenderhp>0)
		{
			if(MathUtils.random(0,100)>dodgeChance)
			{
				defenderhp-=this.attackerAttack;
			}
			enemyhp-=officer.getAttack();
		}
		if(enemyhp<=0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean isWon() {
		return won;
	}
	public int getTurnsToStart()
	{
		return this.turnsToStart;
	}
	public int getTurnsToEnd()
	{
		return this.turnsToEnd;
	}
	public int getAttackerAttack()
	{
		return this.attackerAttack;
	}
	public int getAttackerDeffence()
	{
		return this.attackerDeffence;
	}
	public int getOfficersBonus()
	{
		return this.officersBonus;
	}
	public void setTurnsToStart(int turnsToStart) {
		this.turnsToStart = turnsToStart;
	}
	public void setTurnsToEnd(int turnsToEnd) {
		this.turnsToEnd = turnsToEnd;
	}
	public void setAttackerAttack(int attackerAttack) {
		this.attackerAttack = attackerAttack;
	}
	public void setAttackerDeffence(int attackerDeffence) {
		this.attackerDeffence = attackerDeffence;
	}
	public void setOfficersBonus(int officersBonus) {
		this.officersBonus = officersBonus;
	}
}
