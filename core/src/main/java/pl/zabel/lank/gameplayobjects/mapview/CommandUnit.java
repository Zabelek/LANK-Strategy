package pl.zabel.lank.gameplayobjects.mapview;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit.rotation;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.texturecontainers.SpecialEffectTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.utilities.maputilities.ConstructionScheme;
import pl.zabel.lank.utilities.maputilities.MapBuildTask;
import pl.zabel.lank.views.MapView;

public abstract class CommandUnit extends RepairingUnit implements UnitBuilder {

	private boolean building, onTheWayToNextBuilding;
	protected ArrayList<ConstructionScheme> schemesList;
	private ArrayList<MapBuilding> unitProductionQueue;
	private Timer.Task buildingTask, buildQueueTask;
	private MapBuilding unitBuilding;
	protected TextureRegion buildLaser;
	protected float buildLaserAngle, laserLenght, laserPositionX, laserPositionY, buildSpeed;
	MapBuildTask continueBuildingTask;

	public CommandUnit(byte faction, MapView mapView) {
		super("Jednostka Dowodzaca", faction, mapView, null);
		this.setSize(100, 50);
		findRegionForSpecificUnit();
		buildSpeed = 10;
		building = false;
		onTheWayToNextBuilding = false;
		buildingTask = new Timer.Task() {

			@Override
			public void run() {
				if (CommandUnit.this.checkRangeToBuild(unitBuilding) < CommandUnit.this.range
						) {
					if(continueBuildingTask!=null && continueBuildingTask.isStarted())
					{
						continueBuildingTask.finish();
					}
					continueBuildingTask = new MapBuildTask(CommandUnit.this, CommandUnit.this.unitBuilding, buildSpeed,
							CommandUnit.this.mapView);
					SoundEffectManager.playBuildLaserSound(calculateEffectVolume());
					stopMoving();
					onTheWayToNextBuilding = false;
					this.cancel();
				}
				//else if(!unitBuilding.checkTerrain())
				//{
				//	this.cancel();
				//}

			}
		};
		buildQueueTask = new Timer.Task() {

			@Override
			public void run() {
				if (!CommandUnit.this.building && !CommandUnit.this.unitProductionQueue.isEmpty() &&
						!CommandUnit.this.hasBuildingInBuild() && !onTheWayToNextBuilding) {
					startBuilding(unitProductionQueue.get(0), unitProductionQueue.get(0).getX(),
							unitProductionQueue.get(0).getY());
					unitProductionQueue.remove(0);
				}

			}
		};
		Timer.schedule(buildQueueTask, 1, 1);
		this.setSpeed(60);
		schemesList = new ArrayList<ConstructionScheme>();
		unitProductionQueue = new ArrayList<MapBuilding>();
		if(this.getFaction()==(byte)0)
		{
			buildSpeed = 10*MapSession.getCuBuildSpeedMod();
			this.setSpeed(this.getSpeed()*MapSession.getCuMovementSpeedMod());
			setUpRegen(1f*MapSession.getCuRegenMod());
		}
		else
			setUpRegen(1);
	}

	private boolean hasBuildingInBuild() {
		if(this.unitBuilding==null)
			return false;
		else if(this.unitBuilding.isInBuild())
		{
			this.building=true;
			this.lockMoving();
			return true;
		}
		else
			return false;
	}

	public ArrayList<ConstructionScheme> getSchemesList() {
		return schemesList;
	}

	public boolean isBuilding() {
		return building;
	}

	public void setBuildingState(boolean value) {
		this.building = value;
		this.target=null;
	}

	public void startBuilding(MapBuilding unitBuilding, float x, float y) {
		if (!this.building) {
			this.unitBuilding = unitBuilding;
			unitBuilding.setPosition(x, y);
			stopMoving();
			stopRepairing();
			stopShooting();
			this.target=(MapUnit) unitBuilding;
			if (this.checkRangeToBuild(unitBuilding) < this.range ) {
				if (unitBuilding.checkTerrain()) {
					MapBuildTask task = new MapBuildTask(CommandUnit.this, CommandUnit.this.unitBuilding, buildSpeed,
							CommandUnit.this.mapView);
					stopMoving();
					this.building = true;
					SoundEffectManager.playBuildLaserSound(this.calculateEffectVolume());
					onTheWayToNextBuilding = false;
				}
			} else if(!buildingTask.isScheduled()){
				onTheWayToNextBuilding = true;
				Vector2 vec = new Vector2(unitBuilding.getX() + unitBuilding.getWidth() / 2,
						unitBuilding.getY() + unitBuilding.getWidth() / 4);
				vec.x = vec.x - (this.getX() + this.getWidth() / 2);
				vec.y = vec.y - this.getY() + this.getWidth() / 4;
				vec.setLength(vec.len() - (this.range / 2) + 50);
				vec.add(this.getX(), this.getY());
				this.scheduleMovingTask(vec.x, vec.y);
				Timer.schedule(buildingTask, 0, 1);
			}
		}
	}

	@Override
	public void stopBuilding() {
		if (this.building) {
			this.building = false;
			this.target=null;
			MapSession.modifyCurrentMetalIncreasePerSec(this.unitBuilding.getMassTickCost(), this.getBuildingSpeed());
			MapSession.modifyCurrentEnergyIncreasePerSec(this.unitBuilding.getEnergyTickCost(),
					this.getBuildingSpeed());
			mapView.removeUnit(this.unitBuilding.getActorForStage());
			this.unitBuilding.destroy();
			this.unitBuilding = null;
			buildingTask.cancel();
			this.stopMoving();
			this.unitProductionQueue.clear();
		}
		else
		{
			this.building = false;
			this.target=null;
			if(this.unitBuilding!=null)
			{
				mapView.removeUnit(this.unitBuilding.getActorForStage());
				this.unitBuilding.destroy();
				this.unitBuilding = null;
			}
			this.stopMoving();
			this.unitProductionQueue.clear();
			if(buildingTask.isScheduled())
				buildingTask.cancel();
			onTheWayToNextBuilding = false;
		}
	}
	@Override
	public void setUpVisualBuildEffects(MapUnit unit) {
		checkRotation();
		buildLaserAngle = (float) Math.atan2(
				(double) ((unit.getX() + unit.getWidth() / 2) - (this.getX() + laserPositionX)),
				(double) (unit.getY() - (this.getY() + laserPositionY)));
		buildLaserAngle = (buildLaserAngle * 180) / (float) Math.PI * -1;
		Vector2 vec = new Vector2(unit.getX() + unit.getWidth() / 2, unit.getY());
		vec.x = vec.x - (this.getX() + laserPositionX);
		vec.y = vec.y - (this.getY() + laserPositionY);
		laserLenght = vec.len();
	}
	@Override
	public void startRepairing(MapUnit unit)
	{
		if(!this.isBuilding())
		{
			SoundEffectManager.playBuildLaserSound(this.calculateEffectVolume());
			super.startRepairing(unit);
			setUpVisualBuildEffects(unit);
		}
	}
	public float checkRangeToBuild(MapBuilding unitBuilding) {
		float xlen = this.getFixedPositionX() - unitBuilding.getFixedPositionX();
		float ylen = this.getFixedPositionY() - unitBuilding.getFixedPositionY();
		return (float) Math.sqrt((xlen * xlen) + (ylen * ylen));
	}

	public void getTurretRotationToTarget() {

		if (target != null) {
			float x = -1 * (this.getFixedPositionX() - target.getFixedPositionX());
			float y = -1 * (this.getFixedPositionY() - target.getFixedPositionY());
			float xwb = (float) Math.sqrt(x * x);
			float ywb = (float) Math.sqrt(y * y);
			if ((2 * xwb < ywb && y > 0 && x < 0) || (2 * xwb < ywb && y > 0 && x > 0) || (y > 0 && x == 0)) {
				currentTurretRotation = rotation.TOP;
			} else if ((2 * xwb > ywb && y > 0 && x > 0 && ywb > xwb) || (2 * ywb > xwb && y > 0 && x > 0 && xwb > ywb)
					|| (ywb == xwb && y != 0)) {
				currentTurretRotation = rotation.TOPRIGHT;
			} else if ((2 * ywb < xwb && y > 0 && x > 0) || (2 * ywb < xwb && y < 0 && x > 0) || (y == 0 && x > 0)) {
				currentTurretRotation = rotation.RIGHT;
			} else if ((2 * xwb > ywb && y < 0 && x > 0 && ywb > xwb) || (2 * ywb > xwb && y < 0 && x > 0 && ywb < xwb)
					|| (ywb == xwb && x > 0)) {
				currentTurretRotation = rotation.BOTTOMRIGHT;
			} else if ((2 * xwb < ywb && y < 0 && x < 0) || (2 * xwb < ywb && y < 0 && x > 0) || (y < 0 && x == 0)) {
				currentTurretRotation = rotation.BOTTOM;
			} else if ((2 * ywb > xwb && y < 0 && x < 0 && ywb < xwb) || (2 * xwb > ywb && y < 0 && x < 0 && xwb < ywb)
					|| (ywb == xwb && x != 0)) {
				currentTurretRotation = rotation.BOTTOMLEFT;
			} else if ((2 * ywb < xwb && y > 0 && x < 0) || (2 * ywb < xwb && y < 0 && x < 0) || (y == 0 && x > 0)) {
				currentTurretRotation = rotation.LEFT;
			} else if ((2 * xwb > ywb && y > 0 && x < 0) || (2 * xwb > ywb && y > 0 && x < 0)
					|| (ywb == xwb && y != 0)) {
				currentTurretRotation = rotation.TOPLEFT;
			}
		} else if (building) {
			float x = -1 * (this.getFixedPositionX() - unitBuilding.getFixedPositionX());
			float y = -1 * (this.getFixedPositionY() - unitBuilding.getFixedPositionY());
			float xwb = (float) Math.sqrt(x * x);
			float ywb = (float) Math.sqrt(y * y);
			if ((2 * xwb < ywb && y > 0 && x < 0) || (2 * xwb < ywb && y > 0 && x > 0) || (y > 0 && x == 0)) {
				currentTurretRotation = rotation.TOP;
			} else if ((2 * xwb > ywb && y > 0 && x > 0 && ywb > xwb) || (2 * ywb > xwb && y > 0 && x > 0 && xwb > ywb)
					|| (ywb == xwb && y != 0)) {
				currentTurretRotation = rotation.TOPRIGHT;
			} else if ((2 * ywb < xwb && y > 0 && x > 0) || (2 * ywb < xwb && y < 0 && x > 0) || (y == 0 && x > 0)) {
				currentTurretRotation = rotation.RIGHT;
			} else if ((2 * xwb > ywb && y < 0 && x > 0 && ywb > xwb) || (2 * ywb > xwb && y < 0 && x > 0 && ywb < xwb)
					|| (ywb == xwb && x > 0)) {
				currentTurretRotation = rotation.BOTTOMRIGHT;
			} else if ((2 * xwb < ywb && y < 0 && x < 0) || (2 * xwb < ywb && y < 0 && x > 0) || (y < 0 && x == 0)) {
				currentTurretRotation = rotation.BOTTOM;
			} else if ((2 * ywb > xwb && y < 0 && x < 0 && ywb < xwb) || (2 * xwb > ywb && y < 0 && x < 0 && xwb < ywb)
					|| (ywb == xwb && x != 0)) {
				currentTurretRotation = rotation.BOTTOMLEFT;
			} else if ((2 * ywb < xwb && y > 0 && x < 0) || (2 * ywb < xwb && y < 0 && x < 0) || (y == 0 && x > 0)) {
				currentTurretRotation = rotation.LEFT;
			} else if ((2 * xwb > ywb && y > 0 && x < 0) || (2 * xwb > ywb && y > 0 && x < 0)
					|| (ywb == xwb && y != 0)) {
				currentTurretRotation = rotation.TOPLEFT;
			}
		}
	}

	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		if (this.isBuilding() || this.isRepairing()) {
			batch.draw(this.buildLaser, this.getX() + laserPositionX, this.getY() + laserPositionY, 10, 10, 20f,
					laserLenght, 1, 1, this.buildLaserAngle);
		}
	}

	@Override
	public float getBuildingSpeed() {
		// TODO Auto-generated method stub
		return buildSpeed;
	}

	@Override
	public void addToQueue(MobileUnit unit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addToQueue(MapBuilding unit) {
		if (unit.checkTerrain()) {
			unitProductionQueue.add(unit);
		}
		else
			SoundEffectManager.playClickLockedSound();
	}
	public void stopChildTimers()
	{
		stopBuilding();
		if(buildQueueTask.isScheduled())
			buildQueueTask.cancel();
	}
	@Override
	public Actor hit (float x, float y, boolean touchable) {
		if (touchable && this.getTouchable() != Touchable.enabled) return null;
		if (!isVisible()) return null;
		return x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight()*2.5f ? this : null;
	}
}
