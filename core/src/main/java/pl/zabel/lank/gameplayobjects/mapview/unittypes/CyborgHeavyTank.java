package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgHeavyTank extends MobileUnit{

	public CyborgHeavyTank(byte faction, MapView mapView, UnitBuilder builder) {
		super("Ciezki Czolg", faction, mapView, builder);
		this.setSize(170, 110);
		findRegionForSpecificUnit();
		this.imageName="cyborg-heavy-tank-image";
		this.movementSoundName = "tank-engine";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.buildTicks = 1050;
		this.massTickCost=7;
		this.energyTickCost=8;
		this.maxHitPoints = 3750;
		this.currentHitPoints = 3750;
		this.setSpeed(50);
		
		this.widthModifier=1.9f;
		this.heightModifier=2.1f;
		this.yOffset = -30;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.setSpeed(this.getSpeed()*MapSession.getUnitMovementSpeedMod());
			setUpGunParameters(30, 30, 110*MapSession.getTankDamageMod(), 0.6f,
					1200, 950*MapSession.getTankRangeMod(), "heavyTank");
		}
		else
			setUpGunParameters(30, 30, 110, 0.6f, 1200, 950, "heavyTank");
		this.shadowTiltModifier = 0;
		this.shadowX = -95;
		this.shadowY = -22;
		this.shadowHeight = 1;
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
				this.gunPositionX = 75;
				this.gunPositionY =-45;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = -55;
				this.gunPositionY = -5;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 200;
				this.gunPositionY = -5;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -90;
				this.gunPositionY = 85;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 230;
				this.gunPositionY = 80;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 70;
				this.gunPositionY = 180;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = -40;
				this.gunPositionY = 160;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 175;
				this.gunPositionY = 155;
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
		this.displayTexture = MapUnitTextureContainer.findRegionCyborgHeavyTank(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionCyborgHeavyTank(turretAtlasKey);
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		this.builder.addToQueue(new CyborgHeavyTank(this.getFaction(), mapView, builder));		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playTankSelectSound(1);
	}
}
