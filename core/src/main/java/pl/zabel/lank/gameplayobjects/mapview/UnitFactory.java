package pl.zabel.lank.gameplayobjects.mapview;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.MapSession;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.maputilities.ConstructionScheme;
import pl.zabel.lank.utilities.maputilities.MapBuildTask;
import pl.zabel.lank.views.MapView;

public abstract class UnitFactory extends NormalBuilding implements UnitBuilder{

	protected ArrayList<MobileUnit> unitProductionQueue;
	protected ArrayList<ConstructionScheme> schemesList;
	protected MobileUnit currentlyProducedUnit;
	protected boolean building;
	protected int currentProductionTick;
	protected Timer.Task buildingTask;
	protected float buildSpeed;
	
	public UnitFactory(String name, byte faction, MapView mapView, UnitBuilder builder) {
		super(name, faction, mapView, builder, 0);		
		findRegionForSpecificUnit();			
		this.unitProductionQueue = new ArrayList<MobileUnit>();
		currentProductionTick = 0;
		
		schemesList = new ArrayList<ConstructionScheme>();
		buildingTask = new Timer.Task() {
			
			@Override
			public void run() {
				if(UnitFactory.this.getCurrentHitPoints()>0)
					checkProductionTick();
				else
					this.cancel();
			}
		};
		this.destinationX = this.getFixedPositionX();
		this.destinationY = this.getY()-200;
	}
	
	@Override
	public Image getUnitSchemeImage() {
		// TODO Auto-generated method stub
		return schemeImage;
	}
	@Override
	public boolean checkTerrain()
	{
		if(super.checkTerrain())
		{
			float mapCoordX = (this.getX() + this.getWidth() / 2);
			float mapCoordY = (this.getY() + this.getHeight() / 2);
			if (mapView.checkTileValue((mapCoordX - 100),(mapCoordY + 100)) == "ground" && mapView.checkTileValue((mapCoordX),(mapCoordY + 100)) == "ground" && mapView.checkTileValue((mapCoordX + 100),(mapCoordY + 100)) == "ground" && 
					mapView.checkTileValue((mapCoordX - 100),(mapCoordY)) == "ground" && mapView.checkTileValue((mapCoordX),(mapCoordY)) == "ground" && mapView.checkTileValue((mapCoordX + 100),(mapCoordY)) == "ground" && 
					mapView.checkTileValue((mapCoordX - 100),(mapCoordY - 100)) == "ground" && mapView.checkTileValue((mapCoordX),(mapCoordY - 100)) == "ground" && mapView.checkTileValue((mapCoordX + 100),(mapCoordY - 100)) == "ground") {
				return true;
			}
			else return false;
		}
		else return false;
	}
	public void checkProductionTick()
	{
		if(!this.unitProductionQueue.isEmpty() && !this.isInBuild() && !this.isBuilding())
		{			
			this.setBuildingState(true);
			this.currentlyProducedUnit = this.unitProductionQueue.get(0);
			this.unitProductionQueue.remove(this.currentlyProducedUnit);
			MapSession.modifyCurrentMetalIncreasePerSec(-1 * currentlyProducedUnit.getMassTickCost(), this.getBuildingSpeed());
			MapSession.modifyCurrentEnergyIncreasePerSec(-1 * currentlyProducedUnit.getEnergyTickCost(), this.getBuildingSpeed());
		}
		else if(this.isBuilding())
		{
			if(MapSession.getCurrentEnergyAmount()>=currentlyProducedUnit.getEnergyTickCost() 
					&& MapSession.getCurrentMetalAmount()>=currentlyProducedUnit.getMassTickCost())
			{
				if(this.currentProductionTick<this.currentlyProducedUnit.getBuildTicks())
				{
					this.currentProductionTick++;
					MapSession.modifyCurrentMetalAmount(-1 *currentlyProducedUnit.getMassTickCost());
					MapSession.modifyCurrentEnergyAmount(-1 *currentlyProducedUnit.getEnergyTickCost());
					}
				else
				{
					this.currentlyProducedUnit.setPosition(this.getX()+this.getWidth()/2, this.getY()-50);
					this.currentlyProducedUnit.scheduleMovingTask(this.destinationX, this.destinationY);
					Random generator = new Random();
					this.setDestination(this.destinationX+generator.nextInt(4)-2, this.destinationY+generator.nextInt(4)-2);
					mapView.registerUnit(this.currentlyProducedUnit);
					MapSession.modifyCurrentMetalIncreasePerSec(currentlyProducedUnit.getMassTickCost(), this.getBuildingSpeed());
					MapSession.modifyCurrentEnergyIncreasePerSec(currentlyProducedUnit.getEnergyTickCost(), this.getBuildingSpeed());
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
			MapSession.modifyCurrentMetalIncreasePerSec(currentlyProducedUnit.getMassTickCost(), this.getBuildingSpeed());
			MapSession.modifyCurrentEnergyIncreasePerSec(currentlyProducedUnit.getEnergyTickCost(), this.getBuildingSpeed());
			this.currentlyProducedUnit = null;
			this.currentProductionTick=0;
			buildingTask.cancel();
		}
	}
	@Override
	public void destroy()
	{
		super.destroy();
		this.stopBuilding();
	}
	@Override
	public void dispose()
	{
		super.dispose();
		this.stopBuilding();
	}
	@Override
	public ArrayList<ConstructionScheme> getSchemesList() {
		// TODO Auto-generated method stub
		return schemesList;
	}
	@Override
	public boolean isBuilding() {
		// TODO Auto-generated method stub
		return building;
	}
	@Override
	public void setBuildingState(boolean value) {
		this.building = value;		
	}
	@Override
	public void startBuilding(MapBuilding building, float x, float y) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public float getBuildingSpeed() {
		// TODO Auto-generated method stub
		return buildSpeed;
	}
	@Override
	public void addToQueue(MobileUnit unit) {
		unitProductionQueue.add(unit);
		if(!this.buildingTask.isScheduled())
			Timer.schedule(buildingTask, 0, 1/buildSpeed);
		
	}
	@Override
	public void addToQueue(MapBuilding unit) {
		// TODO Auto-generated method stub
		
	}
	@Override
    public void draw(Batch batch, float alpha){
		if(this.currentDistanceToCamera<GameVariables.getRenderDistance())
		{
		super.draw(batch, alpha);
		if(building)
		{
			batch.draw(maxHpBar, this.getX()+20, this.getY()-20, this.getWidth()-40, 5);
			batch.draw(currentHpBar, this.getX()+20, this.getY()-20, (this.getWidth()-40)*((float)this.currentProductionTick/(float)this.currentlyProducedUnit.getBuildTicks()), 5);
		}
		}
	}

}
