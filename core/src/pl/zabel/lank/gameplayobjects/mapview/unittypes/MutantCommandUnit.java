package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.CommandUnit;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.texturecontainers.SpecialEffectTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class MutantCommandUnit extends CommandUnit{

	public MutantCommandUnit(byte faction, MapView mapView) {
		super(faction, mapView);
		
		this.imageName="mutant-command-unit-image";
		this.movementSoundName = "command-unit-walk";
		this.maxHitPoints = 3100;
		this.currentHitPoints = 3100;
		buildLaser = SpecialEffectTextureContainer.findRegion("mutant-build-laser");
		schemesList.add(new MutantPowerGenerator(this.getFaction(), mapView, this));
		schemesList.add(new MutantMetalMine(this.getFaction(), mapView, this));
		schemesList.add(new MutantRobotFactory(this.getFaction(), mapView, this));
		schemesList.add(new MutantTankFactory(this.getFaction(), mapView, this));
		schemesList.add(new MutantTurret(this.getFaction(), mapView, this));
		this.heightModifier = 2.8f;
		this.widthModifier = 1.2f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.maxHitPoints *= MapSession.getCuHpMod();
			this.currentHitPoints *= MapSession.getCuHpMod();
			setUpGunParameters(30, 100, 50*MapSession.getCuDamageMod(), 0.6f*MapSession.getCuShootSpeedMod(),
					600, 450*MapSession.getCuRangeMod(), "commandUnit");
			this.repairSpeed *=MapSession.getCuRepairSpeedMod();
		}
		else
			setUpGunParameters(30, 100, 50, 0.6f, 600, 450, "commandUnit");
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
				this.gunPositionX = 60;
				this.gunPositionY = 35;
				this.laserPositionX = 20;
				this.laserPositionY = 50;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = 20;
				this.gunPositionY = 30;
				this.laserPositionX = 5;
				this.laserPositionY = 65;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 95;
				this.gunPositionY = 55;
				this.laserPositionX = 50;
				this.laserPositionY = 45;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -15;
				this.gunPositionY = 50;
				this.laserPositionX = 5;
				this.laserPositionY = 85;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 90;
				this.gunPositionY = 85;
				this.laserPositionX = 85;
				this.laserPositionY = 55;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 15;
				this.gunPositionY = 105;
				this.laserPositionX = 60;
				this.laserPositionY = 110;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = -15;
				this.gunPositionY = 80;
				this.laserPositionX = 30;
				this.laserPositionY = 115;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 55;
				this.gunPositionY = 105;
				this.laserPositionX = 90;
				this.laserPositionY = 80;
			}
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionMutantCommandUnit(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionMutantCommandUnit(turretAtlasKey);
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playCommandUnitSelectSound(1);
	}
}
