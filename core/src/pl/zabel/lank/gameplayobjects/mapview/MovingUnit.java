package pl.zabel.lank.gameplayobjects.mapview;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.views.MapView;

public abstract class MovingUnit extends MapUnit{
	
	public static enum rotation{TOP, TOPRIGHT, RIGHT, BOTTOMRIGHT, BOTTOM, BOTTOMLEFT, LEFT, TOPLEFT, STAYS};

	public rotation currentRotation, currentTurretRotation;
	protected String atlasKey, turretAtlasKey;
	protected boolean moving;
	protected int movingTick;
	protected TextureRegion displayTextureTurret;
	//stats for shooting
	public boolean isShooting;
	private float shootSpeed;
	protected MapUnit target;
	protected float gunPositionX;
	protected float gunPositionY;
	private float shellSpeed, damage;
	private String bulletType;
	protected float range;
	private Timer.Task loadTask;
	protected float shadowTiltModifier, shadowX, shadowY, shadowHeight;
	
	public MovingUnit(String name, byte faction, MapView mapView) {
		super(name, faction, mapView);
		checkRotation();
		this.currentRotation=rotation.BOTTOM;
		this.currentTurretRotation=rotation.BOTTOM;
		this.movingTick=0;
		this.atlasKey = "podw-bot-";
		this.turretAtlasKey = "tower-bot";
		animateMovement();
		loadTask = new Timer.Task() {		
			@Override
			public void run() {
				if(MovingUnit.this.isShooting)
				{
					shoot(target);
				}
			}
		};
		this.shadowTiltModifier = -1;
		this.shadowX = 0;
		this.shadowY = 10;
		this.shadowHeight = 2;
	}
	public void setUpGunParameters(float gunPositionX, float gunPositionY, float damage, float shootSpeed, 
									float shellSpeed, float range, String bulletType)
	{
		this.gunPositionX = gunPositionX;
		this.gunPositionY = gunPositionY;
		this.damage = damage;
		this.shootSpeed = shootSpeed;
		this.shellSpeed = shellSpeed;
		this.range = range;
		this.bulletType = bulletType;
		Timer.schedule(loadTask, 0, 1f/shootSpeed);
	}
	public void shoot(float targetX, float targetY)
	{
			Bullet bullet = new Bullet(targetX, targetY, this.getX()+gunPositionX, this.getY()+gunPositionY,
					(int)damage, shellSpeed, bulletType, mapView, this);
	}
	public void shoot(MapUnit target)
	{
		float tempRange = this.checkRange();
		if(tempRange<this.range && this.target.currentHitPoints>0)
		{
			this.shoot(target.getX()+target.getWidth()/2, target.getY()+20);
		}
		else if (this.checkRangeForDestinationPoint()>=this.range && this.target.currentHitPoints>0)
		{
			stopShooting();
		}
	}
	public float checkRange()
	{
		if(this.target==null)
		{
			return 0;
		}
		float xlen = this.getFixedPositionX() - this.target.getFixedPositionX();
		float ylen = this.getFixedPositionY() - this.target.getFixedPositionY();
		return (float) Math.sqrt((xlen*xlen)+(ylen*ylen));
		
	}	public float checkRangeForDestinationPoint()
	{
		float xlen = this.destinationX - this.target.getFixedPositionX();
		float ylen = this.destinationY - this.target.getFixedPositionY();
		return (float) Math.sqrt((xlen*xlen)+(ylen*ylen));
	}
	public void startShooting(MapUnit target)
	{
		this.target = target;
		this.isShooting=true;
	}
	public void stopShooting()
	{
		this.target = null;
		this.isShooting=false;
	}
	public void getMovingRotation()
	{
		
		if (this.hasActions())
		{
			MoveToAction action = (MoveToAction)this.getActions().get(0);
			float x = -1 *(this.getX()-action.getX());
			float y = -1 *(this.getY()-action.getY());
			float xwb = (float)Math.sqrt(x*x);
			float ywb = (float)Math.sqrt(y*y);
			if ((2*xwb < ywb && y>0 && x<0) || (2*xwb < ywb && y>0 && x>0) || (y>0 && x==0))
			{
				currentRotation = rotation.TOP;
				currentTurretRotation= rotation.TOP;
			}
			else if ((2*xwb > ywb && y>0 && x>0 && ywb > xwb) || (2*ywb > xwb && y>0 && x>0 && xwb > ywb) || (ywb==xwb && y!=0))
			{
				currentRotation = rotation.TOPRIGHT;
				currentTurretRotation= rotation.TOPRIGHT;
			}
			else if ((2*ywb < xwb && y>0 && x>0) || (2*ywb < xwb && y<0 && x>0 ) || (y==0 && x>0))
			{
				currentRotation = rotation.RIGHT;
				currentTurretRotation= rotation.RIGHT;
			}
			else if ((2*xwb > ywb && y<0 && x>0 && ywb > xwb) || (2*ywb > xwb && y<0 && x>0 && ywb < xwb) || (ywb==xwb && x>0))
			{
				currentRotation = rotation.BOTTOMRIGHT;
				currentTurretRotation= rotation.BOTTOMRIGHT;
			}
			else if ((2*xwb < ywb && y<0 && x<0) || (2*xwb < ywb && y<0 && x>0) || (y<0 && x==0))
			{
				currentRotation = rotation.BOTTOM;
				currentTurretRotation= rotation.BOTTOM;
			}
			else if ((2*ywb > xwb && y<0 && x<0 && ywb < xwb) || (2*xwb > ywb && y<0 && x<0 && xwb < ywb) || (ywb==xwb && x!=0))
			{
				currentRotation = rotation.BOTTOMLEFT;
				currentTurretRotation= rotation.BOTTOMLEFT;
			}
			else if ((2*ywb < xwb && y>0 && x<0 ) || (2*ywb < xwb && y<0 && x<0) || (y==0 && x>0))
			{
				currentRotation = rotation.LEFT;
				currentTurretRotation= rotation.LEFT;
			}
			else if ((2*xwb > ywb && y>0 && x<0) || (2*xwb > ywb && y>0 && x<0) || (ywb==xwb && y!=0))
			{
				currentRotation = rotation.TOPLEFT;
				currentTurretRotation= rotation.TOPLEFT;
			}
		}
		this.getTurretRotationToTarget();
	}
	public void getTurretRotationToTarget()
	{
		
		if (target!=null)
		{
			float x = -1 *(this.getFixedPositionX()-target.getFixedPositionX());
			float y = -1 *(this.getFixedPositionY()-target.getFixedPositionY());
			float xwb = (float)Math.sqrt(x*x);
			float ywb = (float)Math.sqrt(y*y);
			if ((2*xwb < ywb && y>0 && x<0) || (2*xwb < ywb && y>0 && x>0) || (y>0 && x==0))
			{
				currentTurretRotation= rotation.TOP;
			}
			else if ((2*xwb > ywb && y>0 && x>0 && ywb > xwb) || (2*ywb > xwb && y>0 && x>0 && xwb > ywb) || (ywb==xwb && y!=0))
			{
				currentTurretRotation= rotation.TOPRIGHT;
			}
			else if ((2*ywb < xwb && y>0 && x>0) || (2*ywb < xwb && y<0 && x>0 ) || (y==0 && x>0))
			{
				currentTurretRotation= rotation.RIGHT;
			}
			else if ((2*xwb > ywb && y<0 && x>0 && ywb > xwb) || (2*ywb > xwb && y<0 && x>0 && ywb < xwb) || (ywb==xwb && x>0))
			{
				currentTurretRotation= rotation.BOTTOMRIGHT;
			}
			else if ((2*xwb < ywb && y<0 && x<0) || (2*xwb < ywb && y<0 && x>0) || (y<0 && x==0))
			{
				currentTurretRotation= rotation.BOTTOM;
			}
			else if ((2*ywb > xwb && y<0 && x<0 && ywb < xwb) || (2*xwb > ywb && y<0 && x<0 && xwb < ywb) || (ywb==xwb && x!=0))
			{
				currentTurretRotation= rotation.BOTTOMLEFT;
			}
			else if ((2*ywb < xwb && y>0 && x<0 ) || (2*ywb < xwb && y<0 && x<0) || (y==0 && x>0))
			{
				currentTurretRotation= rotation.LEFT;
			}
			else if ((2*xwb > ywb && y>0 && x<0) || (2*xwb > ywb && y>0 && x<0) || (ywb==xwb && y!=0))
			{
				currentTurretRotation= rotation.TOPLEFT;
			}
		}
	}
	public void checkRotation()
	{
		this.getMovingRotation();
		if(this.currentRotation==rotation.BOTTOM)
			this.atlasKey =  "podw-bot-";
		else if(this.currentRotation==rotation.BOTTOMLEFT)
			this.atlasKey =  "podw-botleft-";
		else if(this.currentRotation==rotation.BOTTOMRIGHT)
			this.atlasKey =  "podw-botright-";
		else if(this.currentRotation==rotation.LEFT)
			this.atlasKey =  "podw-left-";
		else if(this.currentRotation==rotation.RIGHT)
			this.atlasKey =  "podw-right-";
		else if(this.currentRotation==rotation.TOP)
			this.atlasKey =  "podw-top-";
		else if(this.currentRotation==rotation.TOPLEFT)
			this.atlasKey =  "podw-topleft-";
		else if(this.currentRotation==rotation.TOPRIGHT)
			this.atlasKey =  "podw-topright-";

			if(this.currentTurretRotation==rotation.BOTTOM)
				this.turretAtlasKey =  "tower-bot";
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
				this.turretAtlasKey =  "tower-botleft";
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
				this.turretAtlasKey =  "tower-botright";
			else if(this.currentTurretRotation==rotation.LEFT)
				this.turretAtlasKey =  "tower-left";
			else if(this.currentTurretRotation==rotation.RIGHT)
				this.turretAtlasKey =  "tower-right";
			else if(this.currentTurretRotation==rotation.TOP)
				this.turretAtlasKey =  "tower-top";
			else if(this.currentTurretRotation==rotation.TOPLEFT)
				this.turretAtlasKey =  "tower-topleft";
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
				this.turretAtlasKey =  "tower-topright";
	}
	public void animateMovement()
	{
		this.checkRotation();
		if(this.hasActions())
		{
			this.movingTick+=1;
			if(this.movingTick==5)
				this.movingTick=1;
			this.atlasKey +=this.movingTick;
		}
		else {
			this.atlasKey+="0";
		}		
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionBasic(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionBasic(turretAtlasKey);
	}
	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	@Override
	public void dispose()
	{
		super.dispose();
		this.loadTask.cancel();
	}
	public float getShootSpeed() {
		return shootSpeed;
	}
	public float getDamage() {
		return damage;
	}
	public float getShellSpeed() {
		return shellSpeed;
	}
	public float getRange() {
		return range;
	}
	@Override
	public void draw(Batch batch, float alpha)
	{
		if(this.currentDistanceToCamera<GameVariables.getRenderDistance())
		{
		if(GameVariables.isShadows()==true)
		{
			batch.setColor(0, 0, 0, 0.7f);
			GameVariables.getShadowShear().setToTranslation(getX()+this.shadowX, getY()+this.shadowY);
			GameVariables.getShadowShear().shear(this.shadowTiltModifier, 0);
			batch.draw(displayTexture, this.textureWidth, this.textureHeight/this.shadowHeight, GameVariables.getShadowShear());
			batch.draw(displayTextureTurret, this.textureWidth, this.textureHeight/this.shadowHeight, GameVariables.getShadowShear());
			batch.setColor(1, 1, 1, 1);
		}
		if(selected)
		{
			batch.draw(selectionTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		}
		batch.draw(displayTexture, this.textureX, this.textureY, this.textureWidth, this.textureHeight);
		batch.draw(displayTextureTurret, this.textureX, this.textureY, this.textureWidth, this.textureHeight);
		batch.draw(maxHpBar, this.getX()+20, this.getY()-10, this.getWidth()-40, 5);
		batch.draw(currentHpBar, this.getX()+20, this.getY()-10, (this.getWidth()-40)*((float)this.currentHitPoints/(float)this.maxHitPoints), 5);
		}
	}
	@Override
	public void stopChildTimers()
	{
		if(loadTask.isScheduled())
		{
			loadTask.cancel();
		}
	}
}
