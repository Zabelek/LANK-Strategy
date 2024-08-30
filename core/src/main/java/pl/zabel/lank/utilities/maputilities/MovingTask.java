package pl.zabel.lank.utilities.maputilities;

import java.util.Random;
import java.util.TimerTask;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import pl.zabel.lank.gameplayobjects.mapview.MapUnit;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit;
import pl.zabel.lank.views.MapView;

public class MovingTask extends TimerTask{
	
	private boolean started, blocked, finished;
	private float destX, destY, unitX, unitY;
	private MobileUnit unit;
	private MapView mapView;
	private MapUnit collisionScout;
	private MoveToAction resultAction;
	private int angle;
	
	@Override
	public void run() {
		if(unit.getFixedPositionX()==destX && unit.getFixedPositionY()==destY)
		{
			unit.setMoving(false);
		}
		if(unit.isMoving() && !unit.hasActions() && this.finished==false && !this.isBlocked())
		{
			unit.addAction(resultAction);
			calculateNewAction();
		}
		else if(!unit.isMoving())
		{
			this.finished=true;
			this.cancel();
		}
	}
	public MovingTask(MobileUnit unit, float destX, float destY, MapView mapView)
	{
		this.unit = unit;
		this.unitX = unit.getX();
		this.unitY = unit.getY();
		this.destX = destX;
		this.destY = destY;
		this.mapView = mapView;
		collisionScout = new MapUnit();
		collisionScout.setBounds(unit.getX(), unit.getY(), unit.getWidth(), unit.getHeight());
		collisionScout.setWidthheightModifiers(unit.getWidthModifier(), unit.getHeightModifier());
		collisionScout.updateTextureBounds();
		calculateNewAction();
		MapView.movementTimer.scheduleMovementTask(this);
	}
	public void calculateNewAction()
	{
			this.blocked=true;
			angle = 0;
			if (resultAction == null) {
				setNewPartAction();
			} else {
				if (this.unitX != this.destX && this.unitY != this.destY) {
					angle = 0;
					boolean pathFlag = false;
					int angleAmount = 0;
					while (pathFlag == false) {
						if (angle < 0)
							angle = angle + angleAmount;
						else
							angle = angle - angleAmount;
						angleAmount += 10;
						if (!checkCollision()) {
							pathFlag = true;
							setNewPartAction();
							angle = 0;
						}
						if (angleAmount == 360) {
							pathFlag = true;
							angle = 0;
							this.finished=true;
						}
					}
					pathFlag = false;
				}
				else if (this.unit.getX() == destX && this.unit.getY() == destY &&
						mapView.checkMapCollisionForUnit(this.unit)) {
						Random generator = new Random();
						this.destX += generator.nextInt(200) - 100;
						this.destY += generator.nextInt(200) - 100;
						setNewPartAction();
					}
				else
				{
					unit.setMoving(false);
				}
				
				this.blocked=false;
		}
	}
	private void setNewPartAction() {
		resultAction = new MoveToAction();
		Vector2 vec2 = new Vector2(destX, destY);
		vec2.add(unitX * -1, unitY * -1);
		vec2.setAngle(vec2.angle() + angle);
		float xlen = (unitX - destX);
		float ylen = (unitY - destY);
		if (xlen * xlen + ylen * ylen > 2500) {
			vec2.setLength(50f);
			resultAction.setDuration(50 * 1/unit.getSpeed());
		} else {
			resultAction.setDuration((float) Math.sqrt(xlen * xlen + ylen * ylen) * 1/unit.getSpeed());
		}
		vec2.add(unitX, unitY);
		resultAction.setPosition(vec2.x, vec2.y);
		this.unitX = vec2.x;
		this.unitY = vec2.y;
		this.blocked=false;
	}
	private boolean checkCollision() {
		Vector2 vec2 = new Vector2(destX, destY);
		vec2.add(unit.getX() * -1, unit.getY() * -1);
		vec2.setLength(this.unit.getWidth() + 10);
		vec2.setAngle(vec2.angle() + angle);
		vec2.add(unit.getX(), unit.getY());
		collisionScout.setPosition(vec2.x, vec2.y);

		return mapView.checkMapCollisionForUnit(collisionScout);
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public boolean isBlocked() {
		return blocked;
	}
	public MoveToAction getResultAction() {
		return resultAction;
	}
	
}
