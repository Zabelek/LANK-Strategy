package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit.rotation;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class AlienLightUnit extends MobileUnit{
	
	public AlienLightUnit(byte faction, MapView mapView, UnitBuilder builder) {
		super("Jednostka Obcych", faction, mapView, builder);
		this.setSize(100, 60);
		findRegionForSpecificUnit();
		setUpGunParameters(30, 30, 12, 1.6f, 400, 500, "test");
		this.imageName="alien-light-unit-image";
		this.movementSoundName = "light-bot-walk";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.buildTicks = 120;
		this.massTickCost=3;
		this.energyTickCost=7;
		this.setSpeed(67);
		this.maxHitPoints = 300;
		this.currentHitPoints = 300;
		
		this.heightModifier = 1.6f;
		this.widthModifier = 1.3f;
		updateTextureBounds();
	}
	@Override
	public void checkRotation()
	{
		this.getMovingRotation();
		if(this.currentRotation==rotation.BOTTOM)
			this.atlasKey =  "podw-bot";
		else if(this.currentRotation==rotation.BOTTOMLEFT)
			this.atlasKey =  "podw-botleft";
		else if(this.currentRotation==rotation.BOTTOMRIGHT)
			this.atlasKey =  "podw-botright";
		else if(this.currentRotation==rotation.LEFT)
			this.atlasKey =  "podw-left";
		else if(this.currentRotation==rotation.RIGHT)
			this.atlasKey =  "podw-right";
		else if(this.currentRotation==rotation.TOP)
			this.atlasKey =  "podw-top";
		else if(this.currentRotation==rotation.TOPLEFT)
			this.atlasKey =  "podw-topleft";
		else if(this.currentRotation==rotation.TOPRIGHT)
			this.atlasKey =  "podw-topright";

			if(this.currentTurretRotation==rotation.BOTTOM)
			{
				this.atlasKey =  "podw-bot";
				this.gunPositionX = 35;
				this.gunPositionY = 5;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.atlasKey =  "podw-botleft";
				this.gunPositionX = 0;
				this.gunPositionY = 20;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.atlasKey =  "podw-botright";
				this.gunPositionX = 70;
				this.gunPositionY = 15;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.atlasKey =  "podw-left";
				this.gunPositionX = -20;
				this.gunPositionY = 45;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.atlasKey =  "podw-right";
				this.gunPositionX = 95;
				this.gunPositionY = 40;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.atlasKey =  "podw-top";
				this.gunPositionX = 35;
				this.gunPositionY = 80;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.atlasKey =  "podw-topleft";
				this.gunPositionX = -10;
				this.gunPositionY = 75;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.atlasKey =  "podw-topright";
				this.gunPositionX = 80;
				this.gunPositionY = 65;
			}
	}
	@Override
	public void animateMovement()
	{
		this.checkRotation();
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionAlienLightUnit(atlasKey);
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		this.builder.addToQueue(new AlienLightUnit(this.getFaction(), mapView, builder));		
	}
	@Override
	public void draw(Batch batch, float alpha) {
		if(this.currentDistanceToCamera<GameVariables.getRenderDistance())
		{
			if(GameVariables.isShadows()==true)
			{
				batch.setColor(0, 0, 0, 0.7f);
				GameVariables.getShadowShear().setToTranslation(getX(), getY()+10);
				GameVariables.getShadowShear().shear(-1f, 0);
				batch.draw(displayTexture, this.textureWidth, this.textureHeight/2, GameVariables.getShadowShear());
				batch.setColor(1, 1, 1, 1);
			}
		if(selected)
		{
			batch.draw(selectionTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		}
		batch.draw(displayTexture, this.textureX, this.textureY, this.textureWidth, this.textureHeight);
		batch.draw(maxHpBar, this.getX()+20, this.getY()-10, this.getWidth()-40, 5);
		batch.draw(currentHpBar, this.getX()+20, this.getY()-10, (this.getWidth()-40)*((float)this.currentHitPoints/(float)this.maxHitPoints), 5);
	}}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBotSelectSound(1);
	}
}
