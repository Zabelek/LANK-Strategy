package pl.zabel.lank.gameplayobjects.mapview;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienHeavyUnit1;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienHeavyUnit2;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienHeavyUnitFactory;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienLightUnit;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienLightUnitFactory;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienMediumUnit;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienMetalMine;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienPowerGenerator;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienTurret;
import pl.zabel.lank.utilities.Observer;
import pl.zabel.lank.views.MapView;

public class MapEnemy {
	
	private int difficulty, aiStage, minesToPlaceAt1stStage, tasksDone;
	private ArrayList<MobileUnit> group1, group2, group3;
	private ArrayList<AlienLightUnitFactory> lightFactories;
	private ArrayList<AlienHeavyUnitFactory> heavyFactories;
	private CommandUnit commandUnit;
	private MapView mapView;
	private Timer.Task aiActionTask;
	private Random random;
	
	private float maxMetalAmount, maxEnergyAmount, currentMetalAmount, currentEnergyAmount;
	
	public MapEnemy(int difficulty, CommandUnit commandUnit, MapView mapView)
	{
		this.mapView = mapView;
		this.difficulty = difficulty;
		this.group1 = new ArrayList<MobileUnit>();
		this.group2 = new ArrayList<MobileUnit>();
		this.group3 = new ArrayList<MobileUnit>();
		this.lightFactories = new  ArrayList<AlienLightUnitFactory>();
		this.heavyFactories = new ArrayList<AlienHeavyUnitFactory>();
		this.commandUnit = commandUnit;
		this.aiStage = 0;
		minesToPlaceAt1stStage = 0;
		tasksDone = 0;
		aiActionTask = new Timer.Task() {
			
			@Override
			public void run() {
				takeAction();
				tasksDone++;
			}
		};
		Timer.schedule(aiActionTask, 0, 100f/(float)difficulty);
		this.maxMetalAmount = 2000;
		this.maxEnergyAmount = 2000;
		this.currentMetalAmount = 2000;
		this.currentEnergyAmount = 2000;
		random = new Random();
	}
	public void modifyCurrentMetalAmount(float value)
	{
		currentMetalAmount+=value;
		if(currentMetalAmount>maxMetalAmount)
			currentMetalAmount = maxMetalAmount;
		if(currentMetalAmount<0)
			currentMetalAmount=0;
	}
	public void modifyCurrentEnergyAmount(float value)
	{
		currentEnergyAmount+=value;
		if(currentEnergyAmount>maxEnergyAmount)
			currentEnergyAmount = maxEnergyAmount;
		if(currentEnergyAmount<0)
			currentEnergyAmount=0;
	}
	public float getMaxMetalAmount() {
		return maxMetalAmount;
	}
	public float getMaxEnergyAmount() {
		return maxEnergyAmount;
	}
	public float getCurrentMetalAmount() {
		return currentMetalAmount;
	}
	public float getCurrentEnergyAmount() {
		return currentEnergyAmount;
	}
	
	public void takeAction()
	{
		if(aiStage ==0)
		{
			countMinePlaces();
			aiStage = 1;
		}
		else if(aiStage==1 && minesToPlaceAt1stStage>0)
		{
			AlienMetalMine mineToPlace = new AlienMetalMine((byte)1, mapView, commandUnit, this);
			if(findGoodSpace(mineToPlace))
			{
				commandUnit.addToQueue(mineToPlace);
			}
			AlienPowerGenerator generatorToPlace = new AlienPowerGenerator((byte)1, mapView, commandUnit, this);
			if(findGoodSpace(generatorToPlace))
			{
				commandUnit.addToQueue(generatorToPlace);
			}
			minesToPlaceAt1stStage--;
		}
		else if(aiStage==1 && minesToPlaceAt1stStage==0)
		{
			aiStage=2;
		}
		else if(aiStage==2)
		{
			if (this.lightFactories.isEmpty())
			{
				final AlienLightUnitFactory factoryToBuild = new AlienLightUnitFactory((byte)1, mapView, commandUnit, this);
				if(findGoodSpaceForFactory(factoryToBuild))
				{
					factoryToBuild.attach(new Observer() {
						@Override
						public void update()
						{
							lightFactories.remove(factoryToBuild);
						}
					});
					commandUnit.addToQueue(factoryToBuild);
					this.lightFactories.add(factoryToBuild);
				}
			}
			else
			{
				for(AlienLightUnitFactory factory : this.lightFactories)
				{
					if(factory.canProduceUnits())
					{
						AlienLightUnit unit = new AlienLightUnit((byte)1, mapView, factory);
						factory.addToQueue(unit);
						factory.setDestination(mapView.getPlayersXPosition(), mapView.getPlayersYPosition());
					}
				}
			}
		}
		else if(aiStage==3)
		{
			if (this.lightFactories.size()<2)
			{
				final AlienLightUnitFactory factoryToBuild = new AlienLightUnitFactory((byte)1, mapView, commandUnit, this);
				if(findGoodSpaceForFactory(factoryToBuild))
				{
					factoryToBuild.attach(new Observer() {
						@Override
						public void update()
						{
							lightFactories.remove(factoryToBuild);
						}
					});
					commandUnit.addToQueue(factoryToBuild);
					this.lightFactories.add(factoryToBuild);
				}
			}
			if (this.heavyFactories.isEmpty())
			{
				final AlienHeavyUnitFactory factoryToBuild = new AlienHeavyUnitFactory((byte)1, mapView, commandUnit, this);
				if(findGoodSpaceForFactory(factoryToBuild))
				{
					factoryToBuild.attach(new Observer() {
						@Override
						public void update()
						{
							heavyFactories.remove(factoryToBuild);
						}
					});
					commandUnit.addToQueue(factoryToBuild);
					this.heavyFactories.add(factoryToBuild);
				}
			}
			if(tasksDone%20==0)
			{
				AlienMetalMine mineToPlace = new AlienMetalMine((byte)1, mapView, commandUnit, this);
				if(findGoodSpaceForNextMine(mineToPlace))
				{
					commandUnit.addToQueue(mineToPlace);
				}
				AlienPowerGenerator generatorToPlace = new AlienPowerGenerator((byte)1, mapView, commandUnit, this);
				if(findGoodSpaceForFactory(generatorToPlace))
				{
					commandUnit.addToQueue(generatorToPlace);
				}
				AlienTurret turretToPlace = new AlienTurret((byte)1, mapView, commandUnit, this);
				if(findGoodSpaceForFactory(turretToPlace))
				{
					commandUnit.addToQueue(turretToPlace);
				}
				
			}
			else if(tasksDone%23==0)
			{
				for(AlienHeavyUnitFactory factory : this.heavyFactories)
				{
					if(factory.canProduceUnits())
					{
						AlienHeavyUnit1 unit = new AlienHeavyUnit1((byte)1, mapView, factory);
						factory.addToQueue(unit);
						factory.setDestination(mapView.getPlayersXPosition(), mapView.getPlayersYPosition());
					}
				}
			}
			else if(tasksDone%33==0)
			{
				for(AlienHeavyUnitFactory factory : this.heavyFactories)
				{
					if(factory.canProduceUnits())
					{
						AlienHeavyUnit2 unit = new AlienHeavyUnit2((byte)1, mapView, factory);
						factory.addToQueue(unit);
						factory.setDestination(mapView.getPlayersXPosition(), mapView.getPlayersYPosition());
					}
				}
			}
			if(tasksDone%5==0)
			{
				for(AlienLightUnitFactory factory : this.lightFactories)
				{
					if(factory.canProduceUnits())
					{
						AlienMediumUnit unit = new AlienMediumUnit((byte)1, mapView, factory);
						factory.addToQueue(unit);
						factory.setDestination(mapView.getPlayersXPosition(), mapView.getPlayersYPosition());
					}
				}
			}
		}
		if(tasksDone>50)
		{
			aiStage=3;
		}
	}
	private void countMinePlaces() {
			for(int i=(int) (commandUnit.getFixedPositionX()-1000); i<commandUnit.getFixedPositionX()+1000; i+=100)
			{
				for(int j=(int) (commandUnit.getFixedPositionY()-1000); j<commandUnit.getFixedPositionY()+1000; j+=100)
				{
					if(mapView.checkTileValue(i, j)=="metal")
					{
						minesToPlaceAt1stStage++;
					}

				}
			}
	}
	private boolean findGoodSpace(MapBuilding building) {
		for(int i=(int) (commandUnit.getFixedPositionX()-1000); i<commandUnit.getFixedPositionX()+1000; i+=100)
		{
			for(int j=(int) (commandUnit.getFixedPositionY()-1000); j<commandUnit.getFixedPositionY()+1000; j+=100)
			{
				building.setPosition(i, j);
				if(building.checkTerrain())
				{
					return true;
				}
			}
		}
		return false;
	}
	private boolean findGoodSpaceForFactory(MapBuilding building) {
		int flag = 0;
		while(flag<20)
		{
			float x = commandUnit.getFixedPositionX()+random.nextInt(1000)-500;
			float y = commandUnit.getFixedPositionX()+random.nextInt(1000)-500;
			building.setPosition(x, y);
			if(building.checkTerrain())
			{
				return true;
			}
		}
		return false;
	}
	private boolean findGoodSpaceForNextMine(MapBuilding building) {
		for(int i=(int) (commandUnit.getFixedPositionX()-2000); i<commandUnit.getFixedPositionX()+2000; i+=100)
		{
			for(int j=(int) (commandUnit.getFixedPositionY()-2000); j<commandUnit.getFixedPositionY()+2000; j+=100)
			{
				building.setPosition(i, j);
				if(building.checkTerrain())
				{
					return true;
				}
			}
		}
		return false;
	}
	public void finnishAi()
	{
		if(this.aiActionTask!=null && this.aiActionTask.isScheduled())
			this.aiActionTask.cancel();
	}
}
