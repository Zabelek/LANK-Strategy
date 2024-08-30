package pl.zabel.lank.utilities;

import java.util.ArrayList;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.City.CityType;

public class GameplayDataReport {
	
	public int citiesAmount,
	titaniumGain, petroleumGain, uraniumGain, goldGain,
	speedUpgCount, repairUpgCount, attackSpeedUpgCount, tankDamageUpgCount, regenUpgCount, armorUpgcount,
	shellSpeedUpgCount, tankRangeUpgCount,
	cunitBuildSpeedUpgCount, cunitRepairUpgCount, cunitStorageUpgCount, cunitAttackSpeedUpgCount,
	cunitSpeedUpgCount, cunitRegenUpgCount, cunitDamageUpgCount, cunitRangeUpgCount, cunitHpUpgCount,
	soAttackUpgCount, soDeffenceUpgCount, soCyborgFlairUpgCount, soMutantFlairUpgCount;
	
	public GameplayDataReport(ArrayList<City> cities)
	{
		//resources
				citiesAmount = cities.size();
				titaniumGain = 0;
				petroleumGain = 0;
				uraniumGain = 0;
				goldGain = 0;
				for(City city : cities)
				{
					int count = 0;
					if(city.getSystem().getResourcePotential()%4>0)
					{
						count+=1;
					}
					if(city.getSystem().getResourcePotential()%4>1)
					{
						count+=1;
					}
					if(city.getSystem().getResourcePotential()%4>2)
					{
						count+=1;
					}
					count = count*(int)city.getHqLevel();
					titaniumGain+=count;
					count=0;
					if(city.getSystem().getResourcePotential()%16>3)
					{
						count+=1;
					}
					if(city.getSystem().getResourcePotential()%16>7)
					{
						count+=1;
					}
					if(city.getSystem().getResourcePotential()%16>11)
					{
						count+=1;
					}
					count = count*(int)city.getHqLevel();
					petroleumGain+=count;
					count=0;
					if(city.getSystem().getResourcePotential()%64>15)
					{
						count+=1;
					}
					if(city.getSystem().getResourcePotential()%64>31)
					{
						count+=1;
					}
					if(city.getSystem().getResourcePotential()%64>47)
					{
						count+=1;
					}
					count = count*(int)city.getHqLevel();
					uraniumGain+=count;
					count=0;
					if(city.getSystem().getResourcePotential()%256>63)
					{
						count+=1;
					}
					if(city.getSystem().getResourcePotential()%256>127)
					{
						count+=1;
					}
					if(city.getSystem().getResourcePotential()%256>191)
					{
						count+=1;
					}
					count = count*(int)city.getHqLevel();
					goldGain+=count;
				}				
		//normal units
				int count = 0;
				for(City city : cities)
				{
					if(city.isDamageUpg())
					{
						count++;
					}
				}
				this.tankDamageUpgCount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isHpUpg())
					{
						count++;
					}
				}
				this.armorUpgcount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isRangeUpg())
					{
						count++;
					}
				}
				this.tankRangeUpgCount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isRegenUpg())
					{
						count++;
					}
				}
				this.regenUpgCount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isRepairUpg())
					{
						count++;
					}
				}
				this.repairUpgCount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isShellSpeedUpg())
					{
						count++;
					}
				}
				this.shellSpeedUpgCount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isShootSpeedUpg())
					{
						count++;
					}
				}
				this.attackSpeedUpgCount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isSpeedUpg())
					{
						count++;
					}
				}
				this.speedUpgCount = count;
				count = 0;		
				//command unit labels
				for(City city : cities)
				{
					if(city.isCyborgRightArm() || city.isMutantRightArm())
					{
						count++;
					}
				}
				this.cunitBuildSpeedUpgCount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isCyborgRightArm())
					{
						count++;
					}
				}
				this.cunitRepairUpgCount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isMutantRightArm())
					{
						count++;
					}
				}
				this.cunitRegenUpgCount  = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isCyborgBody() || city.isMutantBody())
					{
						count++;
					}
				}
				this.cunitStorageUpgCount  = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isCyborgLeftArm())
					{
						count++;
					}
				}
				this.cunitAttackSpeedUpgCount  = count;
				this.cunitSpeedUpgCount  = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isMutantLeftArm())
					{
						count++;
					}
				}
				this.cunitDamageUpgCount  = count;
				this.cunitRangeUpgCount  = count;
				count = 0;
				//support officers units
				for(City city : cities)
				{
					if(city.isHasTrainFac())
					{
						count++;
					}
				}
				this.soAttackUpgCount  = count;
				this.soDeffenceUpgCount  = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isHasSpecBuild() && city.getType()==CityType.CYBORG)
					{
						count++;
					}
				}
				this.soCyborgFlairUpgCount  = count;
				if(GameSession.getIsPlayerCyborg())
					this.cunitHpUpgCount = count;
				count = 0;
				for(City city : cities)
				{
					if(city.isHasSpecBuild() && city.getType()==CityType.MUTANT)
					{
						count++;
					}
				}
				this.soMutantFlairUpgCount  = count;
				if(!GameSession.getIsPlayerCyborg())
					this.cunitHpUpgCount = count;
	}

}
