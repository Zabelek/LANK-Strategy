package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgLightTank extends MobileUnit{

	public CyborgLightTank(byte faction, MapView mapView, UnitBuilder builder) {
		super("Lekki Czolg", faction, mapView, builder);
		this.setSize(130, 80);
		findRegionForSpecificUnit();
		this.imageName="cyborg-light-tank-image";
		this.movementSoundName = "tank-engine";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.buildTicks = 150;
		this.massTickCost=7;
		this.energyTickCost=9;
		this.maxHitPoints = 750;
		this.currentHitPoints = 750;
		this.setSpeed(100);
		
		this.widthModifier=1.4f;
		this.heightModifier=1.9f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.setSpeed(this.getSpeed()*MapSession.getUnitMovementSpeedMod());
			setUpGunParameters(30, 30, 15*MapSession.getTankDamageMod(), 2.0f,
					1000, 600*MapSession.getTankRangeMod(), "lightTank");
		}
		else
			setUpGunParameters(30, 30, 15, 2.0f, 1000, 600, "lightTank");
		this.shadowTiltModifier = 0;
		this.shadowX = -40;
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
				this.gunPositionX = 50;
				this.gunPositionY =-5;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = -15;
				this.gunPositionY = 10;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 115;
				this.gunPositionY = 5;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -40;
				this.gunPositionY = 45;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 130;
				this.gunPositionY = 40;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 50;
				this.gunPositionY = 100;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = -15;
				this.gunPositionY = 85;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 110;
				this.gunPositionY = 80;
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
		this.displayTexture = MapUnitTextureContainer.findRegionCyborgLightTank(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionCyborgLightTank(turretAtlasKey);
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		this.builder.addToQueue(new CyborgLightTank(this.getFaction(), mapView, builder));		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playTankSelectSound(1);
	}
}
