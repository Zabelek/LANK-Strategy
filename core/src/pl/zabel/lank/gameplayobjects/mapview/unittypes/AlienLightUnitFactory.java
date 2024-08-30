package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.gameplayobjects.mapview.EnemyNormalBuilding;
import pl.zabel.lank.gameplayobjects.mapview.MapEnemy;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.UnitFactory;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class AlienLightUnitFactory extends EnemyNormalBuilding{

	public AlienLightUnitFactory(byte faction, MapView mapView, UnitBuilder builder, MapEnemy enemy) {
		super("Fabryka Robotow", faction, mapView, builder, enemy);
		this.setSize(300, 140);
		this.imageName="alien-light-unit-factory-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 900;
		this.currentHitPoints = 900;
		
		this.buildTicksLeft = 200;
		this.maxBuildTicks = 200;
		this.massTickCost=2;
		this.energyTickCost=7;
		this.schemesList.add(new AlienLightUnit(this.getFaction(), mapView, this));
		this.schemesList.add(new AlienMediumUnit(this.getFaction(), mapView, this));
		this.buildSpeed = 10;
		
		this.heightModifier = 1.7f;
		this.widthModifier = 1.5f;
		this.yOffset=-10;
		updateTextureBounds();
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionAlienLightUnitFactory("normal");
		this.inBuildTexture = MapUnitTextureContainer.findRegionAlienLightUnitFactory("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new AlienLightUnitFactory(this.getFaction(), mapView, builder, enemy));	
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}

}
