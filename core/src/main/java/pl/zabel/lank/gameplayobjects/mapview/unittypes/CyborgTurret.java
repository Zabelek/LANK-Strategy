package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.gameplayobjects.mapview.MapTurret;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit.rotation;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgTurret extends MapTurret{

	public CyborgTurret(byte faction, MapView mapView, UnitBuilder builder) {
		super("Wieza Obronna", faction, mapView, builder);
		this.setSize(140, 80);
		this.imageName="cyborg-turret-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 1500;
		this.currentHitPoints = 1500;
		setUpGunParameters(200, 100, 20, 1.5f, 500, 1700, "heavyBot");
		this.buildTicksLeft = 250;
		this.maxBuildTicks = 250;
		this.massTickCost=3;
		this.energyTickCost=7;
		turretAtlasKey = "tower-bot";
		findRegionForSpecificUnit();
		
		this.widthModifier=2.3f;
		this.heightModifier=3.0f;
		updateTextureBounds();
		
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionCyborgTurret("normal");
		this.displayTextureTurret = MapUnitTextureContainer.findRegionCyborgTurret(turretAtlasKey);
		this.inBuildTexture = MapUnitTextureContainer.findRegionCyborgTurret("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new CyborgTurret(this.getFaction(), mapView, builder));
		
	}
	@Override
	public void checkRotation()
	{
		this.getMovingRotation();
			if(this.currentTurretRotation==rotation.BOTTOM)
			{
				this.turretAtlasKey =  "tower-bot";
				this.gunPositionX = 30;
				this.gunPositionY = 50;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = -70;
				this.gunPositionY = 90;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 155;
				this.gunPositionY = 65;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -110;
				this.gunPositionY = 150;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 215;
				this.gunPositionY = 120;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 70;
				this.gunPositionY = 230;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = -40;
				this.gunPositionY = 210;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 170;
				this.gunPositionY = 190;
			}
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}
}
