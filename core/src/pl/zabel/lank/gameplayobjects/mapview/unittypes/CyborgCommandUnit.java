package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.CommandUnit;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.texturecontainers.SpecialEffectTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgCommandUnit extends CommandUnit{

	public CyborgCommandUnit(byte faction, MapView mapView) {
		super(faction, mapView);
		this.imageName="cyborg-command-unit-image";
		this.movementSoundName = "command-unit-walk";
		this.maxHitPoints = 3000;
		this.currentHitPoints = 3000;
		buildLaser = SpecialEffectTextureContainer.findRegion("cyborg-build-laser");
		schemesList.add(new CyborgPowerGenerator(this.getFaction(), mapView, this));
		schemesList.add(new CyborgMetalMine(this.getFaction(), mapView, this));
		schemesList.add(new CyborgRobotFactory(this.getFaction(), mapView, this));
		schemesList.add(new CyborgTankFactory(this.getFaction(), mapView, this));
		schemesList.add(new CyborgTurret(this.getFaction(), mapView, this));
		
		this.heightModifier = 2.8f;
		this.widthModifier = 1.2f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.maxHitPoints *= MapSession.getCuHpMod();
			this.currentHitPoints *= MapSession.getCuHpMod();
			setUpGunParameters(30, 100, 50*MapSession.getCuDamageMod(), 0.5f*MapSession.getCuShootSpeedMod(),
					600, 500*MapSession.getCuRangeMod(), "commandUnit");
			this.repairSpeed *=MapSession.getCuRepairSpeedMod();
		}
		else
			setUpGunParameters(30, 100, 50, 0.5f, 600, 500, "commandUnit");
		
	}
	@Override
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
			{
				this.turretAtlasKey =  "tower-bot";
				this.gunPositionX = 65;
				this.gunPositionY = 40;
				this.laserPositionX = 10;
				this.laserPositionY = 50;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = 20;
				this.gunPositionY = 40;
				this.laserPositionX = -10;
				this.laserPositionY = 70;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 90;
				this.gunPositionY = 65;
				this.laserPositionX = 50;
				this.laserPositionY = 45;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -20;
				this.gunPositionY = 55;
				this.laserPositionX = -10;
				this.laserPositionY = 95;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 90;
				this.gunPositionY = 85;
				this.laserPositionX = 85;
				this.laserPositionY = 60;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 0;
				this.gunPositionY = 100;
				this.laserPositionX = 70;
				this.laserPositionY = 105;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = -30;
				this.gunPositionY = 90;
				this.laserPositionX = 30;
				this.laserPositionY = 115;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 55;
				this.gunPositionY = 95;
				this.laserPositionX = 95;
				this.laserPositionY = 85;
			}
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionCyborgCommandUnit(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionCyborgCommandUnit(turretAtlasKey);
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playCommandUnitSelectSound(1);
	}
}
