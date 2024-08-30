package pl.zabel.lank.gameplayobjects;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;

public class City {
	public enum CityType {
		CYBORG,
		MUTANT
	}
	private String name;
	//type: 0=mutant, 1=cyborg
	private CityType type;
	private byte hqLevel;
	private boolean hasLabWoj, hasLabMech, hasIntel, hasSpecBuild, hasTrainFac, hasWall;
	//labtech upgrades
	private boolean speedUpg, repairUpg, shootSpeedUpg, damageUpg, regenUpg, hpUpg, shellSpeedUpg, rangeUpg;
	//labwoj upgrades
	private boolean cyborgRightArm, cyborgLeftArm, cyborgBody, mutantRightArm, mutantLeftArm, mutantBody;
	private boolean alreadyBuiltInThisTurn, alreadyResearchedInThisTurn;
	
	
	private StarSystem system;
	
	public City(String name, CityType type)
	{
		this.name = name;
		this.type = type;
		this.hqLevel = 1;
		this.hasIntel = false;
		this.hasLabWoj = false;
		this.hasLabMech = false;
		this.hasSpecBuild = false;
		this.hasTrainFac = false;
		this.hasWall = false;
		
		this.damageUpg = false;
		this.hpUpg = false;
		this.regenUpg = false;
		this.rangeUpg = false;
		this.repairUpg = false;
		this.shellSpeedUpg = false;
		this.shootSpeedUpg = false;
		this.speedUpg = false;
		
		this.alreadyBuiltInThisTurn = false;
		this.alreadyResearchedInThisTurn = false;
		
		GameSession.addRegisteredCity(this);
	}
	
	public CityType getType() {
		return type;
	}
	public void setType(CityType type) {
		this.type = type;
	}
	public byte getHqLevel() {
		return hqLevel;
	}
	public void setHqLevel(byte hqLevel) {
		this.hqLevel = hqLevel;
		this.alreadyBuiltInThisTurn = true;
	}
	public boolean isHasLabWoj() {
		return hasLabWoj;
	}
	public void setHasLabWoj(boolean hasLabWoj) {
		this.hasLabWoj = hasLabWoj;
		this.alreadyBuiltInThisTurn = true;
	}
	public boolean isHasLabMech() {
		return hasLabMech;
	}
	public void setHasLabMech(boolean hasLabMech) {
		this.hasLabMech = hasLabMech;
		this.alreadyBuiltInThisTurn = true;
	}
	public boolean isHasIntel() {
		return hasIntel;
	}
	public void setHasIntel(boolean hasIntel) {
		this.hasIntel = hasIntel;
		this.alreadyBuiltInThisTurn = true;
	}
	public boolean isHasSpecBuild() {
		return hasSpecBuild;
	}
	public void setHasSpecBuild(boolean hasSpecBuild) {
		this.hasSpecBuild = hasSpecBuild;
		this.alreadyBuiltInThisTurn = true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StarSystem getSystem() {
		return system;
	}

	public void setSystem(StarSystem system) {
		this.system = system;
	}

	public boolean isHasTrainFac() {
		return hasTrainFac;
	}

	public void setHasTrainFac(boolean hasTrainFac) {
		this.hasTrainFac = hasTrainFac;
		this.alreadyBuiltInThisTurn = true;
	}

	public boolean isHasWall() {
		return hasWall;
	}

	public void setHasWall(boolean hasWall) {
		this.hasWall = hasWall;
		this.alreadyBuiltInThisTurn = true;
	}
	//upgrades get/set
	public boolean isShellSpeedUpg() {
		return shellSpeedUpg;
	}

	public void setShellSpeedUpg(boolean shellSpeedUpg) {
		if (this.getType()==CityType.MUTANT)
		{
			this.shellSpeedUpg = shellSpeedUpg;
			this.alreadyResearchedInThisTurn = true;
		}
		else
			this.shellSpeedUpg = false;
	}

	public boolean isSpeedUpg() {
		return speedUpg;
	}

	public void setSpeedUpg(boolean speedUpg) {
		if (this.getType()==CityType.CYBORG)
		{
			this.speedUpg = speedUpg;
			this.alreadyResearchedInThisTurn = true;
		}
		else
			this.speedUpg = false;
	}

	public boolean isRepairUpg() {
		return repairUpg;
	}

	public void setRepairUpg(boolean repairUpg) {
		if (this.getType()==CityType.CYBORG)
		{
			this.repairUpg = repairUpg;
			this.alreadyResearchedInThisTurn = true;
		}
		else
			this.repairUpg = false;
	}

	public boolean isShootSpeedUpg() {
		return shootSpeedUpg;
	}

	public void setShootSpeedUpg(boolean shootSpeedUpg) {
		if (this.getType()==CityType.CYBORG)
		{
			this.shootSpeedUpg = shootSpeedUpg;
			this.alreadyResearchedInThisTurn = true;
		}
		else
			this.shootSpeedUpg = false;
	}

	public boolean isDamageUpg() {
		return damageUpg;
	}

	public void setDamageUpg(boolean damageUpg) {
		if (this.getType()==CityType.CYBORG)
		{
			this.damageUpg = damageUpg;
			this.alreadyResearchedInThisTurn = true;
		}
		else
			this.damageUpg = false;
	}

	public boolean isRegenUpg() {
		return regenUpg;
	}

	public void setRegenUpg(boolean regenUpg) {
		if (this.getType()==CityType.MUTANT)
		{
			this.regenUpg = regenUpg;
			this.alreadyResearchedInThisTurn = true;
		}
		else
			this.regenUpg = false;
	}

	public boolean isHpUpg() {
		return hpUpg;
	}

	public void setHpUpg(boolean hpUpg) {
		if (this.getType()==CityType.MUTANT)
		{
			this.hpUpg = hpUpg;
			this.alreadyResearchedInThisTurn = true;
		}
		else
			this.hpUpg = false;
	}

	public boolean isRangeUpg() {
		return rangeUpg;
	}

	public void setRangeUpg(boolean rangeUpg) {
		if (this.getType()==CityType.MUTANT)
		{
			this.rangeUpg = hpUpg;
			this.alreadyResearchedInThisTurn = true;
		}
		else
			this.rangeUpg = false;
	}

	public boolean isCyborgRightArm() {
		return cyborgRightArm;
	}

	public void setCyborgRightArm(boolean cyborgRightArm) {
		if (this.getType()==CityType.CYBORG)
		{
			this.alreadyResearchedInThisTurn = true;
			this.cyborgRightArm = cyborgRightArm;
		}
		else
			this.cyborgRightArm = false;
	}

	public boolean isCyborgLeftArm() {
		return cyborgLeftArm;
	}

	public void setCyborgLeftArm(boolean cyborgLeftArm) {
		if (this.getType()==CityType.CYBORG)
		{
			this.alreadyResearchedInThisTurn = true;
			this.cyborgLeftArm = cyborgLeftArm;
		}
		else
			this.cyborgLeftArm = false;
	}

	public boolean isCyborgBody() {
		return cyborgBody;
	}

	public void setCyborgBody(boolean cyborgBody) {
		if (this.getType()==CityType.CYBORG)
		{
			this.alreadyResearchedInThisTurn = true;
			this.cyborgBody = cyborgBody;
		}
		else
			this.cyborgBody = false;
	}

	public boolean isMutantRightArm() {
		return mutantRightArm;
	}

	public void setMutantRightArm(boolean mutantRightArm) {
		if (this.getType()==CityType.MUTANT)
		{
			this.alreadyResearchedInThisTurn = true;
			this.mutantRightArm = mutantRightArm;
		}
		else
			this.mutantRightArm = false;
	}

	public boolean isMutantLeftArm() {
		return mutantLeftArm;
	}

	public void setMutantLeftArm(boolean mutantLeftArm) {
		if (this.getType()==CityType.MUTANT)
		{
			this.alreadyResearchedInThisTurn = true;
			this.mutantLeftArm = mutantLeftArm;
		}
		else
		this.mutantLeftArm = false;
	}

	public boolean isMutantBody() {
		return mutantBody;
	}

	public void setMutantBody(boolean mutantBody) {
		if (this.getType()==CityType.MUTANT)
		{
			this.alreadyResearchedInThisTurn = true;
			this.mutantBody = mutantBody;
		}
		else
			this.mutantBody = false;
	}
	public void resetBuildAndResearchCapability()
	{
		this.alreadyBuiltInThisTurn = false;
		this.alreadyResearchedInThisTurn = false;
	}
	public void setBuildCapability(boolean value)
	{
		this.alreadyBuiltInThisTurn = value;
	}
	public void setresearchCapability(boolean value)
	{
		this.alreadyResearchedInThisTurn = value;
	}
	public boolean hasBuiltInThisTurn()
	{
		return this.alreadyBuiltInThisTurn;
	}
	public boolean hasResearchedInThisTurn()
	{
		return this.alreadyResearchedInThisTurn;
	}
}
