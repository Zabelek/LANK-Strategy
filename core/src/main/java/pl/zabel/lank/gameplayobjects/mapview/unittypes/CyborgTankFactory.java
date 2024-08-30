package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.UnitFactory;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgTankFactory extends UnitFactory{

public CyborgTankFactory(byte faction, MapView mapView, UnitBuilder builder) {
		super("Fabryka Czolgow", faction, mapView, builder);
		this.setSize(340, 140);
		this.imageName="cyborg-tank-factory-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 1200;
		this.currentHitPoints = 1200;
		
		this.buildTicksLeft = 500;
		this.maxBuildTicks = 500;
		this.massTickCost=3;
		this.energyTickCost=15;
		this.schemesList.add(new CyborgLightTank(this.getFaction(), mapView, this));
		this.schemesList.add(new CyborgMediumTank(this.getFaction(), mapView, this));
		this.schemesList.add(new CyborgHeavyTank(this.getFaction(), mapView, this));
		this.buildSpeed = 20;
		
		this.heightModifier = 1.7f;
		this.widthModifier = 1f;
		updateTextureBounds();
		this.shadowY = 25;
	}
@Override
public void findRegionForSpecificUnit()
{
	this.displayTexture = MapUnitTextureContainer.findRegionCyborgTankFactory("normal");
	this.inBuildTexture = MapUnitTextureContainer.findRegionCyborgTankFactory("in-build");
}
@Override
public void schemeActivationEvent() {
	SoundEffectManager.playSmallClickSound();
	mapView.startPlacingMode(new CyborgTankFactory(this.getFaction(), mapView, builder));
	
}
@Override
protected void playSelectSound()
{
	SoundEffectManager.playBuildingSelectSound(1);
}
}
