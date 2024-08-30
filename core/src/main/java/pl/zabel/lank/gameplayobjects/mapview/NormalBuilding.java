package pl.zabel.lank.gameplayobjects.mapview;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.MapSession;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.userinterface.MapInterface;
import pl.zabel.lank.utilities.maputilities.ConstructionScheme;
import pl.zabel.lank.views.MapView;

public abstract class NormalBuilding extends MapUnit implements MapBuilding, ConstructionScheme{
	
	protected Image schemeImage;
	protected UnitBuilder builder;
	protected float buildTicksLeft, maxBuildTicks, massTickCost, energyTickCost, buildTextureAlphaModifier;
	protected TextureRegion inBuildTexture;
	protected String animationKey;
	protected int maxAnimationTick;
	private int currentAnimationTick;
	private boolean inBuild;
	protected boolean buildStarted, resourceSummaryTaken;
	protected float shadowTiltModifier, shadowX, shadowY;
	
	public NormalBuilding(String name, byte faction, MapView mapView, UnitBuilder builder, int maxAnimationTick) {
		super(name, faction, mapView);
		findRegionForSpecificUnit();
		this.builder = builder;
		buildTicksLeft = 10;
		maxBuildTicks = 10;
		buildTextureAlphaModifier = 0;
		massTickCost=1;
		energyTickCost=3;
		buildStarted = false;
		schemeImage = new Image();
		schemeImage.setBounds(400, 50, 100, 100);
		schemeImage.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				schemeActivationEvent();
				this.cancel();
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
		this.maxAnimationTick = maxAnimationTick;
		if(maxAnimationTick>1)
		{
			currentAnimationTick=0;
		}
		resourceSummaryTaken = false;
		this.shadowTiltModifier = -0.5f;
		this.shadowX = 0;
		this.shadowY = 10;
	}

	@Override
	public void setInBuild(boolean inBuild) {
		this.inBuild = inBuild;
		
	}

	@Override
	public boolean isInBuild() {
		return this.inBuild;
	}
	

	@Override
	public void updateBuildTick() {
		if (this.buildTicksLeft < (this.maxBuildTicks/2) && this.isVisible()==false)
			this.setVisible(true);
		if (this.buildTicksLeft == this.maxBuildTicks && !buildStarted) {
			MapSession.modifyCurrentMetalIncreasePerSec(-1 * this.massTickCost, this.builder.getBuildingSpeed());
			MapSession.modifyCurrentEnergyIncreasePerSec(-1 * this.energyTickCost, this.builder.getBuildingSpeed());
			resourceSummaryTaken = true;
			buildStarted = true;
		}
		if (this.buildTicksLeft > 0) {
				if (MapSession.getCurrentMetalAmount() >= this.massTickCost
						&& MapSession.getCurrentEnergyAmount() >= this.energyTickCost)
				{
					MapSession.modifyCurrentMetalAmount(-1 *massTickCost);
					MapSession.modifyCurrentEnergyAmount(-1 *energyTickCost);
					this.buildTicksLeft = this.buildTicksLeft - 1;
					if(buildTicksLeft<(maxBuildTicks/2))
						buildTextureAlphaModifier = ((buildTicksLeft/maxBuildTicks/2)*3.8f);
					else if(buildTicksLeft==maxBuildTicks/2)
						buildTextureAlphaModifier = 1;
					else
						buildTextureAlphaModifier = 1-((buildTicksLeft/maxBuildTicks)/2f);
				}
			}
			else {
				if(resourceSummaryTaken == true)
				{
					MapSession.modifyCurrentMetalIncreasePerSec(this.massTickCost, this.builder.getBuildingSpeed());
					MapSession.modifyCurrentEnergyIncreasePerSec(this.energyTickCost, this.builder.getBuildingSpeed());		
					resourceSummaryTaken = false;
				}
				this.setInBuild(false);
				this.builder.setBuildingState(false);
				this.setDestination(this.getX()+this.getWidth()/2, this.getY()-100);
			}	
	}

	@Override
	public MapBuildSpectre getBuildSpectre() {
		return new MapBuildSpectre(this, mapView);
	}

	@Override
	public Image getUnitSchemeImage() {
		// TODO Auto-generated method stub
		return schemeImage;
	}

	@Override
	public void schemeActivationEvent() {
		mapView.startPlacingMode(this);
		
	}

	@Override
	public void schemeHoverEvent() {
		MapInterface.loadBuildDetails(((MapUnit)this), this.maxBuildTicks/this.builder.getBuildingSpeed(), 
				(int)(this.maxBuildTicks*this.massTickCost), (int)(this.maxBuildTicks*this.energyTickCost));	
	}

	@Override
	public void schemeHoverEndEvent() {
		MapInterface.removeBuildDetails();	
	}

	@Override
	public UnitBuilder getBuilder() {
		// TODO Auto-generated method stub
		return builder;
	}

	@Override
	public int getCurrentHitPoints() {
		// TODO Auto-generated method stub
		return currentHitPoints;
	}

	@Override
	public MapUnit getActorForStage() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean checkTerrain() {
		if (mapView.checkMapCollisionForUnit(this)==false)
			return true;
		else return false;
	}
	public void animate()
	{
		if(this.inBuild==false)
		{
			this.animationKey="normal-";
			this.currentAnimationTick+=1;
			if(this.currentAnimationTick>=maxAnimationTick)
				this.currentAnimationTick=0;
			this.animationKey +=this.currentAnimationTick;
		}
	}
	public boolean hasAnimations()
	{
		if (this.maxAnimationTick>1)
			return true;
		else return false;
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
				batch.draw(displayTexture, this.textureWidth, this.textureHeight/1.5f, GameVariables.getShadowShear());
				batch.setColor(1, 1, 1, 1);
			}
		if(selected)
		{
			batch.draw(selectionTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		}
		if(buildTicksLeft<(maxBuildTicks/2))
			batch.draw(displayTexture, this.textureX, this.textureY, this.textureWidth, this.textureHeight);
		batch.draw(maxHpBar, this.getX()+20, this.getY()-10, this.getWidth()-40, 5);
		batch.draw(currentHpBar, this.getX()+20, this.getY()-10, (this.getWidth()-40)*((float)this.currentHitPoints/(float)this.maxHitPoints), 5);
		if(this.inBuild)
		{
			batch.setColor(1,1,1,buildTextureAlphaModifier);
			batch.draw(inBuildTexture, this.textureX, this.textureY, this.textureWidth, this.textureHeight);
			batch.setColor(1,1,1,1);
		}
		}
	}

	@Override
	public float getMassTickCost() {
		// TODO Auto-generated method stub
		return massTickCost;
	}

	@Override
	public float getEnergyTickCost() {
		// TODO Auto-generated method stub
		return energyTickCost;
	}
}
