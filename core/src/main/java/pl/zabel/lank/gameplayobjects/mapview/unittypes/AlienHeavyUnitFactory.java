package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.gameplayobjects.mapview.EnemyNormalBuilding;
import pl.zabel.lank.gameplayobjects.mapview.MapEnemy;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.UnitFactory;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class AlienHeavyUnitFactory extends EnemyNormalBuilding{

	public AlienHeavyUnitFactory(byte faction, MapView mapView, UnitBuilder builder, MapEnemy enemy) {
		super("Fabryka Obcych", faction, mapView, builder, enemy);
		this.setSize(300, 140);
		this.imageName="alien-heavy-unit-factory-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 1800;
		this.currentHitPoints = 1800;
		
		this.buildTicksLeft = 600;
		this.maxBuildTicks = 600;
		this.massTickCost=4;
		this.energyTickCost=10;
		this.schemesList.add(new AlienHeavyUnit1(this.getFaction(), mapView, this));
		this.schemesList.add(new AlienHeavyUnit2(this.getFaction(), mapView, this));
		this.buildSpeed = 15;
		
		this.heightModifier = 1.7f;
		this.widthModifier = 1.5f;
		this.yOffset=-30;
		updateTextureBounds();
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionAlienHeavyUnitFactory("normal");
		this.inBuildTexture = MapUnitTextureContainer.findRegionAlienHeavyUnitFactory("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new AlienHeavyUnitFactory(this.getFaction(), mapView, builder, enemy));	
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}
}
