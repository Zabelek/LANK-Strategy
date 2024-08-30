package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.UnitFactory;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgRobotFactory extends UnitFactory{

	public CyborgRobotFactory(byte faction, MapView mapView, UnitBuilder builder) {
		super("Fabryka Robotow", faction, mapView, builder);
		this.setSize(300, 140);
		this.imageName="cyborg-robot-factory-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 800;
		this.currentHitPoints = 800;
		
		this.buildTicksLeft = 200;
		this.maxBuildTicks = 200;
		this.massTickCost=2;
		this.energyTickCost=7;
		this.schemesList.add(new CyborgLightBot(this.getFaction(), mapView, this));
		this.schemesList.add(new CyborgMediumBot(this.getFaction(), mapView, this));
		this.schemesList.add(new CyborgHeavyBot(this.getFaction(), mapView, this));
		this.buildSpeed = 10;
		
		this.heightModifier = 1.7f;
		this.widthModifier = 1f;
		updateTextureBounds();
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionCyborgRobotFactory("normal");
		this.inBuildTexture = MapUnitTextureContainer.findRegionCyborgRobotFactory("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new CyborgRobotFactory(this.getFaction(), mapView, builder));
		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}
}
