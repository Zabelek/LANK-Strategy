package pl.zabel.lank.utilities.maputilities;

import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.gameplayobjects.mapview.MapBuilding;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.views.MapView;


public class MapBuildTask{
	
	private boolean started;
	private boolean finished;
	private boolean firstTick;
	
	private UnitBuilder assignedUnit;
	private MapBuilding target;
	private float buildSpeed;
	private MapView mapView;
	
	public MapBuildTask(UnitBuilder assignedUnit, MapBuilding target, float buildSpeed, MapView mapView)
	{
		this.assignedUnit = assignedUnit;
		this.target = target;
		this.buildSpeed = buildSpeed;
		this.mapView = mapView;
		firstTick = true;
		finished = false;
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {

				if(!MapBuildTask.this.isFinished())
				{
					if(firstTick)
					{
						 start();
					}
					if(MapBuildTask.this.target.isInBuild())
					{
						MapBuildTask.this.target.updateBuildTick();
					}
					else
					{
						MapBuildTask.this.finish();
					}
					if(MapBuildTask.this.target.getCurrentHitPoints()<=0)
					{
						MapBuildTask.this.finish();
					}
				}
				else if(MapBuildTask.this.isFinished())
				{
					this.cancel();
				}
			}
		}, 0, 1f / buildSpeed);
	}
	public void start() {
		MapBuildTask.this.target.setInBuild(true);
		MapBuildTask.this.mapView.registerUnit(MapBuildTask.this.target.getActorForStage());
		MapBuildTask.this.assignedUnit.setBuildingState(true);
		((MobileUnit)(MapBuildTask.this.assignedUnit)).lockMoving();
		MapBuildTask.this.assignedUnit.setUpVisualBuildEffects(MapBuildTask.this.target.getActorForStage());	
		firstTick = false;
		this.started = true;		
	}
	public void finish() {
		this.finished = true;
		MapBuildTask.this.target.setInBuild(false);
		((MobileUnit)(MapBuildTask.this.assignedUnit)).unlockMoving();
		MapBuildTask.this.assignedUnit.setBuildingState(false);
	}
	public boolean isStarted() {
		return this.started;
	}
	public boolean isFinished() {
		return this.finished;
	}
}
