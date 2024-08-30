package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgMediumTank extends MobileUnit{

	public CyborgMediumTank(byte faction, MapView mapView, UnitBuilder builder) {
		super("Sredni Czolg", faction, mapView, builder);
		this.setSize(150, 95);
		findRegionForSpecificUnit();
		this.imageName="cyborg-medium-tank-image";
		this.movementSoundName = "tank-engine";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.buildTicks = 350;
		this.massTickCost=7;
		this.energyTickCost=9;
		this.maxHitPoints = 1750;
		this.currentHitPoints = 1750;
		this.setSpeed(70);
		
		this.widthModifier=1.6f;
		this.heightModifier=2.1f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.setSpeed(this.getSpeed()*MapSession.getUnitMovementSpeedMod());
			setUpGunParameters(30, 30, 30*MapSession.getTankDamageMod(), 1.0f,
					1100, 700*MapSession.getTankRangeMod(), "mediumTank");
		}
		else
			setUpGunParameters(30, 30, 30, 1.0f, 1100, 700, "mediumTank");
		this.shadowTiltModifier = 0;
		this.shadowX = -55;
		this.shadowY = -22;
		this.shadowHeight = 1;
	}
	@Override
	public void updateTextureBounds()
	{
		super.updateTextureBounds();
		this.textureY-=30;
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
				this.turretAtlasKey =  "tower-bot";
				this.gunPositionX = 55;
				this.gunPositionY =-10;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = -30;
				this.gunPositionY = 10;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 135;
				this.gunPositionY = 20;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -55;
				this.gunPositionY = 75;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 165;
				this.gunPositionY = 70;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 50;
				this.gunPositionY = 145;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = -20;
				this.gunPositionY = 125;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 130;
				this.gunPositionY = 125;
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
		this.displayTexture = MapUnitTextureContainer.findRegionCyborgMediumTank(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionCyborgMediumTank(turretAtlasKey);
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		this.builder.addToQueue(new CyborgMediumTank(this.getFaction(), mapView, builder));		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playTankSelectSound(1);
	}
}
