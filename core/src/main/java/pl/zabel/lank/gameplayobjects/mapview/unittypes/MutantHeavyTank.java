package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class MutantHeavyTank extends MobileUnit{

	public MutantHeavyTank(byte faction, MapView mapView, UnitBuilder builder) {
		super("Ciezki Czolg", faction, mapView, builder);
		this.setSize(170, 110);
		findRegionForSpecificUnit();
		this.imageName="mutant-heavy-tank-image";
		this.movementSoundName = "tank-engine";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.buildTicks = 1060;
		this.massTickCost=7;
		this.energyTickCost=8;
		this.maxHitPoints = 3600;
		this.currentHitPoints = 3600;
		this.setSpeed(55);
		
		this.widthModifier=2.4f;
		this.heightModifier=2.6f;
		this.xOffset=-10;
		this.yOffset=-50;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.setSpeed(this.getSpeed()*MapSession.getUnitMovementSpeedMod());
			setUpGunParameters(30, 30, 160*MapSession.getTankDamageMod(), 0.4f,
					1150, 950*MapSession.getTankRangeMod(), "heavyTank");
			setUpRegen(1f*MapSession.getCuRegenMod());
		}
		else
		{
			setUpGunParameters(30, 30, 160, 0.4f, 1150, 950, "heavyTank");
			setUpRegen(1);
		}
		this.shadowTiltModifier = 0;
		this.shadowX = -145;
		this.shadowY = -70;
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
				this.gunPositionX = 70;
				this.gunPositionY =-65;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = -85;
				this.gunPositionY = -30;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 220;
				this.gunPositionY = -25;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -130;
				this.gunPositionY = 65;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 265;
				this.gunPositionY = 70;
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
				this.gunPositionX = -65;
				this.gunPositionY = 145;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 205;
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
		this.displayTexture = MapUnitTextureContainer.findRegionMutantHeavyTank(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionMutantHeavyTank(turretAtlasKey);
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		this.builder.addToQueue(new MutantHeavyTank(this.getFaction(), mapView, builder));		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playTankSelectSound(1);
	}
}
