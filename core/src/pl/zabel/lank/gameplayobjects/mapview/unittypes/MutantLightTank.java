package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class MutantLightTank extends MobileUnit{

	public MutantLightTank(byte faction, MapView mapView, UnitBuilder builder) {
		super("Lekki Czolg", faction, mapView, builder);
		this.setSize(130, 80);
		findRegionForSpecificUnit();
		this.imageName="mutant-light-tank-image";
		this.movementSoundName = "tank-engine";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.buildTicks = 150;
		this.massTickCost=7;
		this.energyTickCost=9;
		this.maxHitPoints = 730;
		this.currentHitPoints = 730;
		this.setSpeed(105);
		
		this.widthModifier=1.4f;
		this.heightModifier=1.9f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.setSpeed(this.getSpeed()*MapSession.getUnitMovementSpeedMod());
			setUpGunParameters(30, 30, 14*MapSession.getTankDamageMod(), 2.1f,
					950, 600*MapSession.getTankRangeMod(), "lightTank");
			setUpRegen(1f*MapSession.getCuRegenMod());
		}
		else
		{
			setUpGunParameters(30, 30, 14, 2.1f, 950, 600, "lightTank");
			setUpRegen(1);
		}
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
				this.gunPositionY =-15;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = -5;
				this.gunPositionY = 0;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 105;
				this.gunPositionY = 0;
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
				this.gunPositionX = 105;
				this.gunPositionY = 85;
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
		this.displayTexture = MapUnitTextureContainer.findRegionMutantLightTank(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionMutantLightTank(turretAtlasKey);
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		this.builder.addToQueue(new MutantLightTank(this.getFaction(), mapView, builder));		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playTankSelectSound(1);
	}
}
