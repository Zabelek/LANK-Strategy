package pl.zabel.lank.gameplayobjects.mapview;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.views.MapView;

public class RepairingUnit extends MobileUnit{
	
	private MapUnit currentlyRepairedUnit;
	private boolean repairing;
	private Timer.Task repairingTask;
	protected float repairSpeed;

	public RepairingUnit(String name, byte faction, MapView mapView, UnitBuilder builder) {
		super(name, faction, mapView, builder);
		this.repairing=false;
		this.repairSpeed = 1;
		this.repairingTask = new Timer.Task() {
			
			@Override
			public void run() {
				if(RepairingUnit.this.currentlyRepairedUnit!=null)
					doRepairTick();
				else
					this.cancel();		
			}
		};
	}
	private void doRepairTick()
	{
		if(checkRangeForRepair()>this.range)
		{
			Vector2 vec = new Vector2(this.currentlyRepairedUnit.getX(), this.currentlyRepairedUnit.getY());
			vec.x = vec.x-this.getX();
			vec.y = vec.y-this.getY();
			vec.setLength(vec.len()-this.range+50);
			vec.add(this.getX(), this.getY());
			this.scheduleMovingTask(vec.x, vec.y);
		}
		else if(this.repairing==false)
		{
			this.stopMoving();
			this.repairing=true;
			MapSession.modifyCurrentMetalIncreasePerSec(-1, this.repairSpeed);
		}
		else if(MapSession.getCurrentMetalAmount()>1 && currentlyRepairedUnit.getCurrentHitPoints()<currentlyRepairedUnit.getMaxHitPoints()&&
				currentlyRepairedUnit.getCurrentHitPoints()>0)
		{
			MapSession.modifyCurrentMetalAmount(-1);
			currentlyRepairedUnit.setCurrentHitPoints(currentlyRepairedUnit.getCurrentHitPoints()+3);
		}
		else if (currentlyRepairedUnit.getCurrentHitPoints()==currentlyRepairedUnit.getMaxHitPoints() ||
				currentlyRepairedUnit.getCurrentHitPoints()==0)
		{
			stopRepairing();

		}
	}
	public void startRepairing(MapUnit unit)
	{
		if(this.isShooting)
		{
			this.stopShooting();
		}
		this.currentlyRepairedUnit = unit;
		this.target=unit;
		if(MobileUnit.class.isAssignableFrom(unit.getClass()))
		{
			((MobileUnit)unit).stopMoving();
			((MobileUnit)unit).lockMoving();
		}
		if(!repairingTask.isScheduled())
		{
			Timer.schedule(repairingTask, 0, 1f/this.repairSpeed);
		}
	}
	public void stopRepairing()
	{
		if(this.repairing)
		{
			MapSession.modifyCurrentMetalIncreasePerSec(1, this.repairSpeed);
			if(MobileUnit.class.isAssignableFrom(currentlyRepairedUnit.getClass()))
			{
				((MobileUnit)currentlyRepairedUnit).unlockMoving();
			}
			this.target=null;
		}		
		this.currentlyRepairedUnit = null;
		this.repairing=false;
	}
	public float checkRangeForRepair()
	{
		float xlen = this.getFixedPositionX() - this.currentlyRepairedUnit.getFixedPositionX();
		float ylen = this.getFixedPositionY() - this.currentlyRepairedUnit.getFixedPositionY();
		return (float) Math.sqrt((xlen*xlen)+(ylen*ylen));
		
	}
	@Override
	public void dispose()
	{
		super.dispose();
		stopRepairing();
		if(repairingTask.isScheduled())
		{
			repairingTask.cancel();
		}
		this.unlockMoving();
	}
	public boolean isRepairing()
	{
		return this.repairing;
	}
}
