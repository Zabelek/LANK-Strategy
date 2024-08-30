package pl.zabel.lank.gameplayobjects.mapview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.userinterface.MapInterface;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.utilities.maputilities.ConstructionScheme;
import pl.zabel.lank.utilities.maputilities.MovingTask;
import pl.zabel.lank.views.MapView;

public class MobileUnit extends MovingUnit implements ConstructionScheme{

	protected Image schemeImage;
	protected MovingTask movingTask;
	private float speed;
	private boolean movementLocked;
	protected UnitBuilder builder;
	protected float massTickCost, energyTickCost, buildTicks;
	private Sound movementSound;
	protected String movementSoundName;
	private Long movementSoundId;

	
	public MobileUnit(String name, byte faction, MapView mapView, UnitBuilder builder) {
		super(name, faction, mapView);
		this.builder = builder;
		speed = 0.01f;
		movementLocked=false;
		schemeImage = new Image();
		schemeImage.setBounds(400, 50, 100, 100);
		schemeImage.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				schemeActivationEvent();
				event.cancel();
			}
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
				schemeHoverEvent();
				event.cancel();
			}
			@Override
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor) {
				schemeHoverEndEvent();
				event.cancel();
			}
		});
	}
	@Override
	public void shoot(MapUnit target)
	{
		if(this.target!=null)
		{
		float tempRange = this.checkRange();
			if(tempRange<this.range && this.target.currentHitPoints>0)
			{
				this.shoot(target.getX()+target.getWidth()/2, target.getY()+20);
			}
			else if(tempRange<this.range+300 && !this.isMoving() && this.target.currentHitPoints>0 && !isMovementLocked())
			{
				Vector2 vec = new Vector2(this.target.getX(), this.target.getY());
				vec.x = vec.x-this.getX();
				vec.y = vec.y-this.getY();
				vec.setLength(vec.len()-this.range+50);
				vec.add(this.getX(), this.getY());
				this.scheduleMovingTask(vec.x, vec.y);
			}
			else if(this.checkRangeForDestinationPoint()>=this.range || this.target.currentHitPoints<=0 || isMovementLocked())
			{
				stopShooting();
			}
		}
	}
	public void comeCloserToShoot()
	{
		if(this.isShooting && !isMovementLocked())
		{
			if(this.checkRange()>this.range)
			{
				Vector2 vec = new Vector2(this.target.getX(), this.target.getY());
				vec.x = vec.x-this.getX();
				vec.y = vec.y-this.getY();
				vec.setLength(vec.len()-this.range+50);
				vec.add(this.getX(), this.getY());
				this.scheduleMovingTask(vec.x, vec.y);
			}
		}
	}
	protected void playMovementSound()
	{
		movementSound = Gdx.audio.newSound(Gdx.files.internal("sounds/"+movementSoundName+".ogg"));
		movementSoundId = SoundEffectManager.playSoundLoop(this.calculateEffectVolume(), movementSound, movementSoundName);
	}
	protected void stopMovementSound()
	{
		SoundEffectManager.stopSoundLoop(movementSound, movementSoundId);
		movementSound = null;
	}
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public MovingTask getMovingTask() {
		return movingTask;
	}
	public void scheduleMovingTask(float x, float y)
	{
		this.setDestination(x, y);
		this.setMoving(true);
		stopMoving();
		movingTask = new MovingTask(this, x, y, this.mapView);
		playMovementSound();
	}
	@Override
	public void setMoving(boolean moving) {
		if(!moving)
			stopMovementSound();
		this.moving = moving;
	}
	public void stopMoving()
	{
		stopMovementSound();
		if(movingTask!=null)
		{
			this.getActions().clear();
			movingTask.cancel();
		}
	}
	public void lockMoving()
	{
		this.movementLocked = true;
	}
	public void unlockMoving()
	{
		this.movementLocked = false;
	}
	public boolean isMovementLocked()
	{
		return this.movementLocked;
	}
	@Override
	public void dispose()
	{
		this.stopMoving();
		stopMovementSound();
		super.dispose();
		if(this.movingTask!=null && this.movingTask.isStarted())
			this.movingTask.cancel();
	}
	@Override
	public Image getUnitSchemeImage() {
		// TODO Auto-generated method stub
		return schemeImage;
	}
	@Override
	public void schemeActivationEvent() {	
	}
	@Override
	public void schemeHoverEvent() {
		MapInterface.loadBuildDetails(((MapUnit)this), this.buildTicks/this.builder.getBuildingSpeed(), 
				(int)(this.buildTicks*this.massTickCost), (int)(this.buildTicks*this.energyTickCost));
	}
	@Override
	public void schemeHoverEndEvent() {
		
		MapInterface.removeBuildDetails();
	}
	public float getMassTickCost() {
		return massTickCost;
	}
	public float getEnergyTickCost() {
		return energyTickCost;
	}
	public float getBuildTicks() {
		// TODO Auto-generated method stub
		return buildTicks;
	}
	@Override
	public void stopChildTimers()
	{
		this.stopMoving();
		stopShooting();
	}			
	public void updateEffectVolume() {
		if (this.movementSound!=null)
		{
			this.movementSound.setVolume(this.movementSoundId, this.calculateEffectVolume());
		}
		
	}
}
