package pl.zabel.lank.gameplayobjects.mapview;

import java.util.Random;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.views.MapView;

public class EnemyNormalBuilding extends UnitFactory{
	
	protected MapEnemy enemy;

	public EnemyNormalBuilding(String name, byte faction, MapView mapView, UnitBuilder builder, MapEnemy enemy) {
		super(name, faction, mapView, builder);
		this.enemy = enemy;
	}
	@Override
	public void updateBuildTick() {
		if (this.buildTicksLeft < (this.maxBuildTicks/2) && this.isVisible()==false)
			this.setVisible(true);
		if (this.buildTicksLeft == this.maxBuildTicks && !buildStarted) {
			buildStarted = true;
		}
		if (this.buildTicksLeft > 0) {
				if (enemy.getCurrentMetalAmount() >= this.massTickCost
						&& enemy.getCurrentEnergyAmount() >= this.energyTickCost)
				{
					enemy.modifyCurrentMetalAmount(-1 *massTickCost);
					enemy.modifyCurrentEnergyAmount(-1 *energyTickCost);
					this.buildTicksLeft = this.buildTicksLeft - 1;
					if(buildTicksLeft<(maxBuildTicks/2))
						buildTextureAlphaModifier = ((buildTicksLeft/maxBuildTicks/2)*3.8f);
					else if(buildTicksLeft==maxBuildTicks/2)
						buildTextureAlphaModifier = 1;
					else
						buildTextureAlphaModifier = 1-((buildTicksLeft/maxBuildTicks)/2f);
				}
			}
			else {
				this.setInBuild(false);
				this.builder.setBuildingState(false);
				this.setDestination(this.getX()+this.getWidth()/2, this.getY()-100);
			}	
	}
	@Override
	public void checkProductionTick()
	{
		if(!this.unitProductionQueue.isEmpty() && !this.isInBuild() && !this.isBuilding())
		{			
			this.setBuildingState(true);
			this.currentlyProducedUnit = this.unitProductionQueue.get(0);
			this.unitProductionQueue.remove(this.currentlyProducedUnit);
			}
		else if(this.isBuilding())
		{
			if(MapSession.getCurrentEnergyAmount()>=currentlyProducedUnit.getEnergyTickCost() 
					&& MapSession.getCurrentMetalAmount()>=currentlyProducedUnit.getMassTickCost())
			{
				if(this.currentProductionTick<this.currentlyProducedUnit.getBuildTicks())
				{
					this.currentProductionTick++;
					enemy.modifyCurrentMetalAmount(-1 *currentlyProducedUnit.getMassTickCost());
					enemy.modifyCurrentEnergyAmount(-1 *currentlyProducedUnit.getEnergyTickCost());
				}
				else
				{
					this.currentlyProducedUnit.setPosition(this.getX()+this.getWidth()/2, this.getY()-50);
					this.currentlyProducedUnit.scheduleMovingTask(this.destinationX, this.destinationY);
					Random generator = new Random();
					this.setDestination(this.destinationX+generator.nextInt(4)-2, this.destinationY+generator.nextInt(4)-2);
					mapView.registerUnit(this.currentlyProducedUnit);
					this.currentProductionTick = 0;
					this.setBuildingState(false);
				}
			}
		}
		else
		{
			buildingTask.cancel();
		}
	}
	@Override
	public void stopBuilding() {
		if(this.building)
		{
			this.building = false;
			this.unitProductionQueue.clear();
			this.currentlyProducedUnit = null;
			this.currentProductionTick=0;
			buildingTask.cancel();
		}
	}
	public boolean canProduceUnits()
	{
		if(this.buildTicksLeft>0)
			return false;
		else
			return true;
	}
}
