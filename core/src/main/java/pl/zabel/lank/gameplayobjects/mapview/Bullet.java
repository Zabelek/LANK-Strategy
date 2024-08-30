package pl.zabel.lank.gameplayobjects.mapview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.utilities.maputilities.SpecialEffect;
import pl.zabel.lank.views.MapView;

public class Bullet extends Actor{
	private TextureRegion displayTexture;
	protected MapView mapView;
	private MoveToAction action;
	private int damage;
	private MapUnit shooter;
	private float bulletRotation, bulletSpeed;
	private String bulletType;
	private float targetX, targetY;
	
	public Bullet(float targetX, float targetY, float startX, float startY, int damage, 
			float bulletSpeed, String bulletType, MapView mapView, MapUnit shooter)
	{
		this.targetX = targetX;
		this.targetY = targetY;		
		this.mapView = mapView;
		this.setTouchable(Touchable.disabled);
		this.shooter = shooter;
		this.damage = damage;
		action = new MoveToAction();
		action.setPosition(targetX, targetY);
		float dx = targetX-50 - startX;
	    float dy = targetY-20 - startY;
	    float odleglosc = (float)Math.sqrt(dx*dx + dy*dy);
	    action.setDuration(odleglosc/bulletSpeed);
	    this.setPosition(startX, startY);
	    this.bulletType = bulletType;
	    if(bulletType == "test")
	    {
	    	this.setSize(22, 22);
	    	this.displayTexture = MapUnitTextureContainer.findRegionBasic("command-unit-bullet");
	    	SoundEffectManager.playCommandUnitShootSound(shooter.calculateEffectVolume());
	    }
	    else if(bulletType == "commandUnit")
	    {
	    	this.setSize(22, 22);
	    	this.displayTexture = MapUnitTextureContainer.findRegionBasic("command-unit-bullet");
	    	SoundEffectManager.playCommandUnitShootSound(shooter.calculateEffectVolume());
	    }
	    else if(bulletType == "lightTank")
	    {
	    	this.setSize(20, 20);
	    	this.displayTexture = MapUnitTextureContainer.findRegionBasic("light-tank-bullet");
	    	SoundEffectManager.playLightTankShootSound(shooter.calculateEffectVolume());
	    }
	    else if(bulletType == "mediumTank")
	    {
	    	this.setSize(22, 22);
	    	this.displayTexture = MapUnitTextureContainer.findRegionBasic("heavy-tank-bullet");
	    	SoundEffectManager.playMediumTankShootSound(shooter.calculateEffectVolume());
	    }
	    else if(bulletType == "heavyTank")
	    {
	    	this.setSize(30, 30);
	    	this.displayTexture = MapUnitTextureContainer.findRegionBasic("heavy-tank-bullet");
	    	SoundEffectManager.playHeavyTankShootSound(shooter.calculateEffectVolume());
	    }
	    else if(bulletType == "lightBot")
	    {
	    	this.setSize(20, 20);
	    	this.displayTexture = MapUnitTextureContainer.findRegionBasic("light-bot-bullet");
	    	SoundEffectManager.playLightBotShootSound(shooter.calculateEffectVolume());
	    }
	    else if(bulletType == "heavyBot")
	    {
	    	this.setSize(25, 25);
	    	this.displayTexture = MapUnitTextureContainer.findRegionBasic("heavy-bot-bullet");
	    	SoundEffectManager.playHeavyBotShootSound(shooter.calculateEffectVolume());
	    }
		this.addAction(action);
		this.mapView.registerBullet(this);
		this.bulletRotation = (float)Math.atan2((double)(action.getX()-this.getX()), (double)(action.getY()-this.getY()));
		this.bulletRotation = (bulletRotation*180)/(float)Math.PI*-1;
		if(bulletType == "test")
	    {
			SpecialEffect shootSplash = new SpecialEffect("gun2-", startX, startY, 30, 30, bulletRotation-180, 7, false, mapView);
	    }
		if(bulletType == "lightTank")
	    {
			SpecialEffect shootSplash = new SpecialEffect("gun2-", startX-2, startY-2, 35, 35, bulletRotation-180, 7, false, mapView);
	    }
		if(bulletType == "mediumTank")
	    {
			SpecialEffect shootSplash = new SpecialEffect("gun2-", startX-7, startY-7, 45, 45, bulletRotation-180, 7, false, mapView);
	    }
		if(bulletType == "heavyTank")
	    {
			SpecialEffect shootSplash = new SpecialEffect("gun2-", startX-10, startY-10, 50, 50, bulletRotation-180, 7, false, mapView);
	    }
		if(bulletType == "lightBot")
	    {
			SpecialEffect shootSplash = new SpecialEffect("gun1-", startX+5, startY+5, 20, 20, bulletRotation-180, 7, false, mapView);
	    }
		if(bulletType == "heavyBot")
	    {
			SpecialEffect shootSplash = new SpecialEffect("gun1-", startX-5, startY-5, 40, 40, bulletRotation-180, 7, false, mapView);
	    }
		if(bulletType == "commandUnit")
	    {
			SpecialEffect shootSplash = new SpecialEffect("gun3-", startX+2, startY+2, 25, 25, bulletRotation-180, 7, false, mapView);
	    }
	    
	}
	public void dispose()
	{
		MapUnit unit = new MapUnit();
		unit.setMapView(mapView);
		unit.setBounds(targetX, targetX, 100, 100);
		if(bulletType == "heavyBot" || bulletType == "heavyTank")
	    {
			SoundEffectManager.playSmallExplosion(unit.calculateEffectVolume());
			SpecialEffect effect = new SpecialEffect("effect", this.getX()-10, this.getY()-10, 60, 60, 0, 15, false, mapView);
	    }
		else
		{
			SoundEffectManager.playSmallExplosion(unit.calculateEffectVolume());
			SpecialEffect effect = new SpecialEffect("effect", this.getX(), this.getY(), 40, 40, 0, 15, false, mapView);
		}
		this.mapView.removeBullet(this);
	}
	public void hit(MapUnit unit)
	{
		if(unit!=null)
			unit.takeDamage(this.damage);
		else
			this.block();
		this.clearActions();
		this.dispose();
	}
	public void block()
	{
		this.clearActions();
		this.dispose();
	}
	public boolean colidesWithUnit(MapUnit unit)
	{
		if(unit.getFaction()==this.shooter.getFaction())
			return false;
		if(this.getX()>unit.getX() && this.getX() < unit.getX()+unit.getWidth() && this.getY()>unit.getY() && this.getY() < unit.getY()+unit.getWidth()/2)
			return true;
		if(this.getX()<unit.getX() && this.getX()+this.getWidth() > unit.getX() && this.getY()>unit.getY() && this.getY() < unit.getY()+unit.getWidth()/2)
			return true;
		if(this.getX()>unit.getX() && this.getX() < unit.getX()+unit.getWidth() && this.getY()<unit.getY() && this.getY()+this.getWidth()/2 > unit.getY())
			return true;
		if(this.getX()<unit.getX() && this.getX()+this.getWidth() > unit.getX() && this.getY()<unit.getY() && this.getY()+this.getWidth()/2 > unit.getY())
			return true;
		
		return false;
	}
	public boolean isFinished()
	{
		return action.isComplete();
	}
	public void draw(Batch batch, float alpha){
		batch.draw(this.displayTexture, this.getX(), this.getY(), this.getWidth()/2, this.getHeight()/2,
				this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
				this.bulletRotation+45+90);
	}

}
