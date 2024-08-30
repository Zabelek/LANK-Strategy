package pl.zabel.lank.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.Siege;
import pl.zabel.lank.gameplayobjects.City.CityType;
import pl.zabel.lank.gameplayobjects.galaxyview.GalaxyRegion;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem.CaptureState;

public class SaveGameHandler {
	
	private static FileHandle files;
	
	public static void saveGameToFile(String filename)
	{
		FileHandle file = Gdx.files.external("Documents/LANKsaves/"+filename+".lanks");
		file.writeString("", false, "UTF-8");
		//segment 0 - player type
		if(GameSession.getIsPlayerCyborg()==null)
		{
			file.writeString("2", true, "UTF-8");
		}
		else if(GameSession.getIsPlayerCyborg()==true){
			file.writeString("1", true, "UTF-8");
		}
		else
		{
			file.writeString("0", true, "UTF-8");
		}
		file.writeString(","+GameSession.getTurnNumber(), true, "UTF-8");
		//segment1 - regions and stars
		for(GalaxyRegion region: GameSession.getGalaxyRegions())
		{
			file.writeString("!", true, "UTF-8");
			for(StarSystem system : region.getStarSystems())
			{
				file.writeString(String.valueOf(system.getCaptureState()), true, "UTF-8");
			}
		}
		//segment2 - cities
		file.writeString("%", true, "UTF-8");
		for(City city : GameSession.getRegisteredCities())
		{
			char[] chars = city.getName().toCharArray();
			for(char c : chars)
			{
				file.writeString(String.valueOf((int)c)+".", true, "UTF-8");
			}
			file.writeString(",", true, "UTF-8");
			
			if(city.getType() == CityType.MUTANT)
			{
				file.writeString("0,", true, "UTF-8");
			}
			else
			{
				file.writeString("1,", true, "UTF-8");
			}
			file.writeString(city.getHqLevel()+",", true, "UTF-8");
			file.writeString((city.isHasLabWoj()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isHasLabMech()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isHasIntel()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isHasSpecBuild()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isHasTrainFac()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isHasWall()?"1":"0")+",", true, "UTF-8");
			
			file.writeString((city.isSpeedUpg()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isRepairUpg()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isShootSpeedUpg()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isDamageUpg()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isRegenUpg()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isShellSpeedUpg()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isRangeUpg()?"1":"0")+",", true, "UTF-8");
			
			file.writeString((city.isCyborgRightArm()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isCyborgLeftArm()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isCyborgBody()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isMutantRightArm()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isMutantLeftArm()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.isMutantBody()?"1":"0")+",", true, "UTF-8");

			file.writeString((city.hasBuiltInThisTurn()?"1":"0")+",", true, "UTF-8");
			file.writeString((city.hasResearchedInThisTurn()?"1":"0")+",", true, "UTF-8");
			char[] syschars = city.getSystem().getName().toCharArray();
			for(char c : syschars)
			{
				file.writeString(String.valueOf((int)c)+".", true, "UTF-8");
			}
			file.writeString(",", true, "UTF-8");
			
			file.writeString("%", true, "UTF-8");
		}
		//segment3 - officers
		file.writeString("@", true, "UTF-8");		
		file.writeString(GameSession.getAnna().getRawAttack()+",", true, "UTF-8");
		file.writeString(GameSession.getAnna().getAvailablePoints()+",", true, "UTF-8");
		file.writeString(GameSession.getAnna().getRawDeffence()+",", true, "UTF-8");
		file.writeString(GameSession.getAnna().getExp()+",", true, "UTF-8");
		file.writeString(GameSession.getAnna().getRawFlair()+",", true, "UTF-8");
		file.writeString(GameSession.getAnna().getLevel()+",", true, "UTF-8");
		file.writeString((GameSession.getAnna().isLocked()?"1":"0")+",", true, "UTF-8");
		
		file.writeString("@", true, "UTF-8");
		file.writeString(GameSession.getKaite().getRawAttack()+",", true, "UTF-8");
		file.writeString(GameSession.getKaite().getAvailablePoints()+",", true, "UTF-8");
		file.writeString(GameSession.getKaite().getRawDeffence()+",", true, "UTF-8");
		file.writeString(GameSession.getKaite().getExp()+",", true, "UTF-8");
		file.writeString(GameSession.getKaite().getRawFlair()+",", true, "UTF-8");
		file.writeString(GameSession.getKaite().getLevel()+",", true, "UTF-8");
		file.writeString((GameSession.getKaite().isLocked()?"1":"0")+",", true, "UTF-8");

		file.writeString("@", true, "UTF-8");
		file.writeString(GameSession.getZack().getRawAttack()+",", true, "UTF-8");
		file.writeString(GameSession.getZack().getAvailablePoints()+",", true, "UTF-8");
		file.writeString(GameSession.getZack().getRawDeffence()+",", true, "UTF-8");
		file.writeString(GameSession.getZack().getExp()+",", true, "UTF-8");
		file.writeString(GameSession.getZack().getRawFlair()+",", true, "UTF-8");
		file.writeString(GameSession.getZack().getLevel()+",", true, "UTF-8");
		file.writeString((GameSession.getZack().isLocked()?"1":"0")+",", true, "UTF-8");
		
		file.writeString("@", true, "UTF-8");
		file.writeString(GameSession.getSimon().getRawAttack()+",", true, "UTF-8");
		file.writeString(GameSession.getSimon().getAvailablePoints()+",", true, "UTF-8");
		file.writeString(GameSession.getSimon().getRawDeffence()+",", true, "UTF-8");
		file.writeString(GameSession.getSimon().getExp()+",", true, "UTF-8");
		file.writeString(GameSession.getSimon().getRawFlair()+",", true, "UTF-8");
		file.writeString(GameSession.getSimon().getLevel()+",", true, "UTF-8");
		file.writeString((GameSession.getSimon().isLocked()?"1":"0")+",", true, "UTF-8");
		
		//segment4 - resources
		file.writeString("^", true, "UTF-8");
		file.writeString(GameSession.getCurrentTitanium()+",", true, "UTF-8");
		file.writeString(GameSession.getCurrentPetroleum()+",", true, "UTF-8");
		file.writeString(GameSession.getCurrentUranium()+",", true, "UTF-8");
		file.writeString(GameSession.getCurrentGold()+",", true, "UTF-8");
		
		//segment5 - sieges
		for(Siege siege : GameSession.getActiveSieges())
		{
			file.writeString("$", true, "UTF-8");
			char[] chars = siege.getCity().getSystem().getName().toCharArray();
			for(char c : chars)
			{
				file.writeString(String.valueOf((int)c)+".", true, "UTF-8");
			}
			file.writeString(",", true, "UTF-8");
			file.writeString(siege.getAttackerAttack()+",", true, "UTF-8");
			file.writeString(siege.getAttackerDeffence()+",", true, "UTF-8");
			file.writeString(siege.getTurnsToStart()+",", true, "UTF-8");
			file.writeString(siege.getTurnsToEnd()+",", true, "UTF-8");
			file.writeString(siege.getOfficersBonus()+",", true, "UTF-8");
			if(siege.getOfficer()!=null)
			{
				if(siege.getOfficer()==GameSession.getAnna())
					file.writeString("a,", true, "UTF-8");
				if(siege.getOfficer()==GameSession.getKaite())
					file.writeString("k,", true, "UTF-8");
				if(siege.getOfficer()==GameSession.getZack())
					file.writeString("z,", true, "UTF-8");
				if(siege.getOfficer()==GameSession.getSimon())
					file.writeString("s,", true, "UTF-8");
			}
			else
				file.writeString("n,", true, "UTF-8");
		}
		file.writeString("#", true, "UTF-8");
		System.out.print(file.read().toString());
	}
	public static void loadGameFromFile(String filename)
	{
		FileHandle file = Gdx.files.external("Documents/LANKsaves/"+filename+".lanks");
		char[] fileChars = file.readString().toCharArray();
		int index = 0;
		//segment0
		if(fileChars[index] == '1')
		{
			GameSession.setIsPlayerCyborg(true);
		}
		else if(fileChars[index] == '0')
		{
			GameSession.setIsPlayerCyborg(false);
		}
		else
		{
			GameSession.setIsPlayerCyborg(null);
		}
		index+=2;
		int turnNumber = 0;
		String turnNumberTemp = "";
		while(fileChars[index]!='!')
		{
			turnNumberTemp+=fileChars[index];
			index++;
		}
		GameSession.setTurnNumber(Integer.valueOf(turnNumberTemp));
		//segment1
		GameSession.resetGalaxyRegions();
		boolean flag=false;
		for(GalaxyRegion region: GameSession.getGalaxyRegions())
		{
			index++;
			for(StarSystem system : region.getStarSystems())
			{
				String value = "";
				value+=fileChars[index++];
				if(Integer.valueOf(value)==0)
					system.setCaptureState(CaptureState.NOT_AVAILABLE, null);
				if(Integer.valueOf(value)==1)
					system.setCaptureState(CaptureState.AVAILABLE, null);
				if(Integer.valueOf(value)==2)
					system.setCaptureState(CaptureState.CAPTURED, null);
				if(Integer.valueOf(value)==3)
					system.setCaptureState(CaptureState.CITY, null);
			}
		}
		GameSession.updateCaptureStates();
		//segment2
		GameSession.getRegisteredCities().clear();
		index++;
		while(fileChars[index]!='@')
		{
			String chars = "";
			while(fileChars[index]!=',')
			{
				chars+=fileChars[index];
				index++;
			}
			String name = asciiToString(chars);
			index++;
			CityType type;
			if(fileChars[index]=='0')
			{
				type=CityType.MUTANT;
			}
			else
			{
				type=CityType.CYBORG;
			}
			index+=2;
			City city = new City(name, type);
			city.setHqLevel(Byte.valueOf(String.valueOf(fileChars[index++])));
			index++;
			if(fileChars[index++] =='0')
				city.setHasLabWoj(false);
			else
				city.setHasLabWoj(true);
			index++;
			if(fileChars[index++] =='0')
				city.setHasLabMech(false);
			else
				city.setHasLabMech(true);
			index++;
			if(fileChars[index++] =='0')
				city.setHasIntel(false);
			else
				city.setHasIntel(true);
			index++;
			if(fileChars[index++] =='0')
				city.setHasSpecBuild(false);
			else
				city.setHasSpecBuild(true);
			index++;
			if(fileChars[index++] =='0')
				city.setHasTrainFac(false);
			else
				city.setHasTrainFac(true);
			index++;
			if(fileChars[index++] =='0')
				city.setHasWall(false);
			else
				city.setHasWall(true);
			index++;
			
			if(fileChars[index++] =='0')
				city.setSpeedUpg(false);
			else
				city.setSpeedUpg(true);
			index++;
			if(fileChars[index++] =='0')
				city.setRepairUpg(false);
			else
				city.setRepairUpg(true);
			index++;
			if(fileChars[index++] =='0')
				city.setShootSpeedUpg(false);
			else
				city.setShootSpeedUpg(true);
			index++;
			if(fileChars[index++] =='0')
				city.setDamageUpg(false);
			else
				city.setDamageUpg(true);
			index++;
			if(fileChars[index++] =='0')
				city.setRegenUpg(false);
			else
				city.setRegenUpg(true);
			index++;
			if(fileChars[index++] =='0')
				city.setShellSpeedUpg(false);
			else
				city.setShellSpeedUpg(true);
			index++;
			if(fileChars[index++] =='0')
				city.setRangeUpg(false);
			else
				city.setRangeUpg(true);
			index++;
			
			if(fileChars[index++] =='0')
				city.setCyborgRightArm(false);
			else
				city.setCyborgRightArm(true);
			index++;
			if(fileChars[index++] =='0')
				city.setCyborgLeftArm(false);
			else
				city.setCyborgLeftArm(true);
			index++;
			if(fileChars[index++] =='0')
				city.setCyborgBody(false);
			else
				city.setCyborgBody(true);
			index++;
			if(fileChars[index++] =='0')
				city.setMutantRightArm(false);
			else
				city.setMutantRightArm(true);
			index++;
			if(fileChars[index++] =='0')
				city.setMutantLeftArm(false);
			else
				city.setMutantLeftArm(true);
			index++;
			if(fileChars[index++] =='0')
				city.setMutantBody(false);
			else
				city.setMutantBody(true);
			index++;
			
			if(fileChars[index++] =='0')
				city.setBuildCapability(false);
			else
				city.setBuildCapability(true);
			index++;
			if(fileChars[index++] =='0')
				city.setresearchCapability(false);
			else
				city.setresearchCapability(true);
			index++;
			String syschars = "";
			while(fileChars[index]!=',')
			{
				syschars+=fileChars[index];
				index++;
			}
			String sysname = asciiToString(syschars);
			for(GalaxyRegion region : GameSession.getGalaxyRegions())
			{
				for(StarSystem system : region.getStarSystems())
				{
					if(system.getName().toLowerCase().equals(sysname.toLowerCase()))
					{
						system.setCaptureState(CaptureState.CITY, city);
						//city.setSystem(system);
						//GameSession.registeredCities.add(city);
					}
				}
			}
			index+=2;	
		}
		GameSession.updateCaptureStates();
		//segment 3
		String tempNumber="";
		GameSession.getAnna().setLocked(true);
		GameSession.getKaite().setLocked(true);
		GameSession.getZack().setLocked(true);
		GameSession.getSimon().setLocked(true);
		index++;
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getAnna().setAttack(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getAnna().setAvailablePoints(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getAnna().setDeffence(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getAnna().addExp(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getAnna().setFlair(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getAnna().setLevel(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index+=2;
		if(Integer.valueOf(tempNumber)==0)
			GameSession.getAnna().setLocked(false);
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		GameSession.getKaite().setAttack(Integer.valueOf(tempNumber));
		index++;	
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getKaite().setAvailablePoints(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getKaite().setDeffence(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getKaite().addExp(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getKaite().setFlair(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getKaite().setLevel(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index+=2;
		if(Integer.valueOf(tempNumber)==0)
			GameSession.getKaite().setLocked(false);
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		
		GameSession.getZack().setAttack(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getZack().setAvailablePoints(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getZack().setDeffence(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getZack().addExp(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getZack().setFlair(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getZack().setLevel(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index+=2;
		if(Integer.valueOf(tempNumber)==0)
			GameSession.getZack().setLocked(false);
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;		
		GameSession.getSimon().setAttack(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getSimon().setAvailablePoints(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getSimon().setDeffence(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getSimon().addExp(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getSimon().setFlair(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index++;
		GameSession.getSimon().setLevel(Integer.valueOf(tempNumber));
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}
		index+=2;
		if(Integer.valueOf(tempNumber)==0)
			GameSession.getSimon().setLocked(false);
		//segment 4
		GameSession.resetEcoValues();
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}		
		GameSession.changeGalaxyEcoValue("titanium", Integer.valueOf(tempNumber));
		index++;
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}		
		GameSession.changeGalaxyEcoValue("petroleum", Integer.valueOf(tempNumber));
		index++;
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}		
		GameSession.changeGalaxyEcoValue("uranium", Integer.valueOf(tempNumber));
		index++;
		tempNumber="";
		while(fileChars[index]!=',')
		{
			tempNumber+=fileChars[index++];
		}		
		GameSession.changeGalaxyEcoValue("gold", Integer.valueOf(tempNumber));
		index++;		
		//segment 5
		GameSession.getActiveSieges().clear();	
		while (fileChars[index] != '#') {
			index++;
			flag = false;
			String chars = "";
			while (fileChars[index] != ',') {
				chars += fileChars[index];
				index++;
			}
			flag = false;
			String name = asciiToString(chars);
			for (GalaxyRegion region : GameSession.getGalaxyRegions()) {
				for (StarSystem system : region.getStarSystems()) {
					if (system.getName().toLowerCase().equals(name.toLowerCase())) {
						GameSession.addNewSiege(system.getCity(), 10);
						Siege s = GameSession.getActiveSieges().get(GameSession.getActiveSieges().size() - 1);
						index++;
						tempNumber = "";
						while (fileChars[index] != ',') {
							tempNumber += fileChars[index++];
						}
						s.setAttackerAttack(Integer.valueOf(tempNumber));
						index++;
						tempNumber = "";
						while (fileChars[index] != ',') {
							tempNumber += fileChars[index++];
						}
						s.setAttackerDeffence(Integer.valueOf(tempNumber));
						index++;
						tempNumber = "";
						while (fileChars[index] != ',') {
							tempNumber += fileChars[index++];
						}
						s.setTurnsToStart(Integer.valueOf(tempNumber));
						index++;
						tempNumber = "";
						while (fileChars[index] != ',') {
							tempNumber += fileChars[index++];
						}
						s.setTurnsToEnd(Integer.valueOf(tempNumber));
						index++;
						tempNumber = "";
						while (fileChars[index] != ',') {
							tempNumber += fileChars[index++];
						}
						s.setOfficersBonus(Integer.valueOf(tempNumber));
						index++;
						if(fileChars[index]=='a')
						{
							GameSession.getAnna().setCurrentSystem(s.getCity().getSystem());
							s.setOfficer(GameSession.getAnna());
						}
						else if(fileChars[index]=='k')
						{
							GameSession.getKaite().setCurrentSystem(s.getCity().getSystem());
							s.setOfficer(GameSession.getKaite());
						}
						else if(fileChars[index]=='z')
						{
							GameSession.getZack().setCurrentSystem(s.getCity().getSystem());
							s.setOfficer(GameSession.getZack());
						}
						else if(fileChars[index]=='s')
						{
							GameSession.getSimon().setCurrentSystem(s.getCity().getSystem());
							s.setOfficer(GameSession.getSimon());
						}
						GameSession.notifyObservers();
						index+=2;
						System.out.print("\ndupadupa\n"+fileChars[index]+"\ndupadupa\n");
						flag=true;//to usunac jak bedzie wiecej systemow w regionach
						//to usunac jak bedzie wiecej systemow w regionach
						if(flag==true)
						{
							break;
						}
					}
					//to usunac jak bedzie wiecej systemow w regionach
					if(flag==true)
					{
						break;
					}
				}
			}
		}
		GameSession.notifyObservers();
	}

	private static String asciiToString(String ascii)
	{
		char[] chars = ascii.toCharArray();
		String singleNumber = "";
		String returnedString = "";
		for(char c : chars)
		{
			if(c!='.')
				singleNumber+=c;
			else
			{
				returnedString+=Character.toString((char) ((int)Integer.valueOf(singleNumber))).toString();
				singleNumber="";
			}
		}
		//System.out.print(returnedString);
		return returnedString;
		
	}
}
