package pl.zabel.lank.gameplayobjects.mapview;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.Observer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.utilities.maputilities.ExplosionEffect;
import pl.zabel.lank.views.MapView;

public class MapUnit extends Actor{
	
	private byte faction;
	protected String name, imageName; //used mostly for finding region in texture atlas
	protected MapView mapView;
	//only for render
	protected TextureRegion displayTexture;
	protected static TextureRegion selectionTexture, maxHpBar, currentHpBar;
	//for gameMechanics
	protected Float textureX, textureY, textureWidth, textureHeight;
	protected boolean selected;
	protected int maxHitPoints, currentHitPoints;
	protected float destinationX, destinationY, widthModifier, heightModifier, xOffset, yOffset;
	private Timer.Task regenTask;
	ArrayList<Observer> observers;
	protected float currentDistanceToCamera;
	
	public MapUnit(String name, byte faction, MapView mapView)
	{
		this.name = name;
		this.faction = faction;
		this.mapView = mapView;
		this.setDisplayTexture("");
		this.unselect();
		this.heightModifier = 1f;
		this.widthModifier = 1f;
		this.xOffset = 0;
		this.yOffset = 0;
		this.maxHitPoints = 100;
		this.currentHitPoints = 100;
		this.setBounds(100, 200, 100, 200);
		this.updateTextureBounds();
		observers = new ArrayList<Observer>();
		this.currentDistanceToCamera = 0;
	}
	public MapUnit()
	{
		this.heightModifier = 1f;
		this.widthModifier = 1f;
	}
	public static void loadBasicTextures()
	{
		selectionTexture = MapUnitTextureContainer.findRegionBasic("selection");
		maxHpBar = InterfaceTextureContainer.findRegion("blackFade");
		currentHpBar = InterfaceTextureContainer.findRegion("white");
	}
	public void setUpRegen(float regenMod)
	{
		regenTask = new Timer.Task() {
			
			@Override
			public void run() {
				MapUnit.this.setCurrentHitPoints(MapUnit.this.getCurrentHitPoints()+1);
			}
		};
		Timer.schedule(regenTask, 1, 1/regenMod);
	}
	public void select()
	{
		playSelectSound();
		this.selected = true;
	}
	protected void playSelectSound()
	{
	}
	public void setDisplayTexture(String suffix)
	{
		this.displayTexture = MapUnitTextureContainer.findRegionBasic(this.name+suffix);
	}
	public void unselect()
	{
		this.selected = false;
	}
	public void updateTextureBounds()
	{
		this.textureHeight = this.getHeight()*this.heightModifier;
		this.textureWidth = this.getWidth()*this.widthModifier;
		this.textureX = this.getX()-(this.textureWidth-this.getWidth())/2f + xOffset;
		this.textureY = this.getY() + yOffset;
	}
	public void setDestination(float x, float y)
	{
		this.destinationX = x;
		this.destinationY = y;
	}
	
	public byte getFaction() {
		return faction;
	}
	public float getFixedPositionY()
	{
		return this.getY()+(this.getWidth()/4);
	}
	public float getFixedPositionX()
	{
		return this.getX()+(this.getWidth()/2);
	}
	public boolean colidesWithUnit(MapUnit unit)
	{
		if(this.getX()>=unit.getX() && this.getX() < unit.getX()+unit.getWidth() && this.getY()>=unit.getY() && this.getY() < unit.getY()+unit.getHeight())//1
			return true;
		if(this.getX()<unit.getX() && this.getX() +this.getWidth() >= unit.getX() && this.getY()>=unit.getY() && this.getY() < unit.getY()+unit.getHeight())//2
			return true;
		if(this.getX()>=unit.getX() && this.getX() < unit.getX()+unit.getWidth() && this.getY()<unit.getY() && this.getY()+this.getHeight() >= unit.getY())//3
			return true;
		if(this.getX()<unit.getX() && this.getX() + this.getWidth() >= unit.getX() && this.getY()<unit.getY() && this.getY()+this.getHeight() >= unit.getY())//4
			return true;
		
		return false;
	}
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionBasic(name);
	}
	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
		updateTextureBounds();
	}
	@Override
	public void setPosition(float x, float y, int alignment)
	{
		super.setPosition(x, y, alignment);
		updateTextureBounds();
	}
	public void takeDamage(int damage)
	{
		if(this.getFaction()==(byte)0)
		{
			MapView.battleStarted=true;
		}
		this.currentHitPoints = this.currentHitPoints - damage;
		if(this.currentHitPoints<=0)
		{
			this.destroy();
		}
		if(this.selected)
			this.mapView.getMapInterface().updateUnitHp(this);
	}
	public void destroy()
	{
		unselect();
		this.currentHitPoints=0;
		SoundEffectManager.playBigExplosionSound(this.calculateEffectVolume());
		ExplosionEffect exp = new ExplosionEffect(this.textureX, this.textureY, this.textureWidth, this.textureWidth, mapView);
		//if(selected)
		//	mapView.getMapInterface().clearUnitParameters();
		this.dispose();
		notifyObservers();
	}
	private void notifyObservers() {
		for(Observer observer : this.observers)
		{
			observer.update();
		}	
	}
	public void dispose()
	{
		if(this.regenTask!=null && this.regenTask.isScheduled())
			regenTask.cancel();
		stopChildTimers();
		this.getActions().clear();
		this.mapView.removeUnit(this);
		this.remove();
		
	}
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}
	public void setCurrentHitPoints(int currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
		if (this.currentHitPoints>this.maxHitPoints)
		{
			this.currentHitPoints = this.maxHitPoints;
		}
		if(this.selected)
			this.mapView.getMapInterface().updateUnitHp(this);
	}
	public int getMaxHitPoints() {
		return maxHitPoints;
	}
	public String getUnitName()
	{
		return this.name;
	}
	public float getWidthModifier()
	{
		return this.widthModifier;
	}
	public float getHeightModifier()
	{
		return this.heightModifier;
	}
	public void setWidthheightModifiers(float w, float h)
	{
		this.widthModifier = w;
		this.heightModifier = h;
	}
	@Override
	public void draw(Batch batch, float alpha)
	{
		if(this.currentDistanceToCamera<GameVariables.getRenderDistance())
		{
			if(selected)
			{
				batch.draw(selectionTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
			}
				batch.draw(displayTexture, this.textureX, this.textureY, this.textureWidth, this.textureHeight);
				batch.draw(maxHpBar, this.getX()+20, this.getY()-10, this.getWidth()-40, 5);
				batch.draw(currentHpBar, this.getX()+20, this.getY()-10, (this.getWidth()-40)*((float)this.currentHitPoints/(float)this.maxHitPoints), 5);
		}
	}
	public String getMenuImageName() {
		return this.imageName;
	}
	public TextureRegion getDisplayTexture() {
		return displayTexture;
	}
	public void stopChildTimers()
	{
		
	}
	public void attach(Observer observer)
	{
		this.observers.add(observer);
	}
	public float checkDistanceToCamera()
	{
		float xlen = this.getFixedPositionX() - this.mapView.getCamera().position.x;
		float ylen = this.getFixedPositionY() - this.mapView.getCamera().position.y;
		this.currentDistanceToCamera = (float) Math.sqrt((xlen*xlen)+(ylen*ylen));
		return currentDistanceToCamera;
		
	} public float calculateEffectVolume()
	{
		float rawDistance = this.currentDistanceToCamera;
		if(rawDistance<500f)
			return 1f*GameVariables.getEffectsVolume();
		else if(rawDistance<1000f)
			return (1-(rawDistance-500f)/500f)*GameVariables.getEffectsVolume();
		else
			return 0f;
	}
	public void setMapView(MapView mapView)
	{
		this.mapView = mapView;
	}
}
