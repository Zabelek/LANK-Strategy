package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MapTurret;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit.rotation;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class MutantTurret extends MapTurret{

	public MutantTurret(byte faction, MapView mapView, UnitBuilder builder) {
		super("Wieza Obronna", faction, mapView, builder);
		this.setSize(140, 80);
		this.imageName="mutant-turret-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 1400;
		this.currentHitPoints = 1400;
		setUpGunParameters(200, 100, 22, 1.5f, 500, 1700, "heavyBot");
		this.buildTicksLeft = 250;
		this.maxBuildTicks = 250;
		this.massTickCost=3;
		this.energyTickCost=7;
		turretAtlasKey = "tower-bot";
		findRegionForSpecificUnit();
		
		this.widthModifier=2.1f;
		this.heightModifier=3.2f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
			setUpRegen(1f*MapSession.getCuRegenMod());
		else
			setUpRegen(1);
		
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionMutantTurret("normal");
		this.displayTextureTurret = MapUnitTextureContainer.findRegionMutantTurret(turretAtlasKey);
		this.inBuildTexture = MapUnitTextureContainer.findRegionMutantTurret("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new MutantTurret(this.getFaction(), mapView, builder));
		
	}
	@Override
	public void checkRotation()
	{
		this.getMovingRotation();
			if(this.currentTurretRotation==rotation.BOTTOM)
			{
				this.turretAtlasKey =  "tower-bot";
				this.gunPositionX = 55;
				this.gunPositionY = 90;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = -50;
				this.gunPositionY = 115;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 160;
				this.gunPositionY = 115;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -90;
				this.gunPositionY = 170;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 205;
				this.gunPositionY = 170;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 55;
				this.gunPositionY = 240;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = -40;
				this.gunPositionY = 220;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 160;
				this.gunPositionY = 220;
			}
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}
}
