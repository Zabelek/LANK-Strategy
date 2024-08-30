package pl.zabel.lank.gameplayobjects.mapview;

import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.views.MapView;

public class ResourceGenerator extends NormalBuilding{
	
	protected Timer.Task generateResourceTask;
	protected float generationSpeed;
	protected boolean type, destroyed;

	public ResourceGenerator(String name, byte faction, MapView mapView, UnitBuilder builder, int maxAnimationKeys) {
		super(name, faction, mapView, builder, maxAnimationKeys);
		generateResourceTask = new Timer.Task() {
			
			@Override
			public void run() {
				generateResource();				
			}
		};
		destroyed=false;
	}
	public void generateResource()
	{
		if(type ==true)
			MapSession.modifyCurrentEnergyAmount(1);
		else
			MapSession.modifyCurrentMetalAmount(1);
	}
	public void startGenerating()
	{
		if(type ==true)
			MapSession.modifyCurrentEnergyIncreasePerSec(1, generationSpeed);
		else
			MapSession.modifyCurrentMetalIncreasePerSec(1, generationSpeed);
		Timer.schedule(generateResourceTask, 0, 1/generationSpeed);
	}
	public void stopGenerating()
	{
		if(type ==true)
			MapSession.modifyCurrentEnergyIncreasePerSec(-1, generationSpeed);
		else
			MapSession.modifyCurrentMetalIncreasePerSec(-1, generationSpeed);
		generateResourceTask.cancel();
	}
	@Override
	public void destroy()
	{
		if(this.buildTicksLeft==0  && !this.isInBuild())
			stopGenerating();
		super.destroy();
		this.destroyed=true;
	}
	@Override
	public void setInBuild(boolean inBuild) {
		super.setInBuild(inBuild);
		if(inBuild==false && !generateResourceTask.isScheduled() && !destroyed)
		{
			startGenerating();
		}
	}
	public void stopChildTimers()
	{
		if(this.buildTicksLeft==0  && !this.isInBuild())
			stopGenerating();
	}
}
