package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.gameplayobjects.mapview.EnemyMapTurret;
import pl.zabel.lank.gameplayobjects.mapview.MapEnemy;
import pl.zabel.lank.gameplayobjects.mapview.MapTurret;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit.rotation;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class AlienTurret extends EnemyMapTurret{

	public AlienTurret(byte faction, MapView mapView, UnitBuilder builder, MapEnemy enemy) {
		super("Wieza Obronna", faction, mapView, builder, enemy);
		this.setSize(140, 80);
		this.imageName="alien-turret-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 1600;
		this.currentHitPoints = 1600;
		setUpGunParameters(200, 100, 28, 1.5f, 500, 1750, "heavyBot");
		this.buildTicksLeft = 250;
		this.maxBuildTicks = 250;
		this.massTickCost=3;
		this.energyTickCost=7;
		turretAtlasKey = "tower-bot";
		findRegionForSpecificUnit();
		
		this.widthModifier=1.4f;
		this.heightModifier=3.0f;
		this.yOffset=-20;
		updateTextureBounds();
		
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionAlienTurret("normal");
		this.displayTextureTurret = MapUnitTextureContainer.findRegionAlienTurret(turretAtlasKey);
		this.inBuildTexture = MapUnitTextureContainer.findRegionAlienTurret("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new AlienTurret(this.getFaction(), mapView, builder, enemy));
		
	}
	@Override
	public void checkRotation()
	{
		this.getMovingRotation();
			if(this.currentTurretRotation==rotation.BOTTOM)
			{
				this.turretAtlasKey =  "tower-bot";
				this.gunPositionX = 60;
				this.gunPositionY = 100;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = 0;
				this.gunPositionY = 115;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 120;
				this.gunPositionY = 125;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -20;
				this.gunPositionY = 155;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 145;
				this.gunPositionY = 155;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 55;
				this.gunPositionY = 210;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = 0;
				this.gunPositionY = 190;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 120;
				this.gunPositionY = 190;
			}
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}
}
