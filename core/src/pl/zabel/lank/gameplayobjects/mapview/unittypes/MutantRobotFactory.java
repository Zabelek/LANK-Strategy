package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.UnitFactory;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class MutantRobotFactory extends UnitFactory{

	public MutantRobotFactory(byte faction, MapView mapView, UnitBuilder builder) {
		super("Fabryka Robotow", faction, mapView, builder);
		this.setSize(300, 140);
		this.imageName="mutant-robot-factory-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 800;
		this.currentHitPoints = 800;
		
		this.buildTicksLeft = 200;
		this.maxBuildTicks = 200;
		this.massTickCost=2;
		this.energyTickCost=7;
		this.schemesList.add(new MutantLightBot(this.getFaction(), mapView, this));
		this.schemesList.add(new MutantMediumBot(this.getFaction(), mapView, this));
		this.schemesList.add(new MutantHeavyBot(this.getFaction(), mapView, this));
		this.buildSpeed = 10;
		
		this.heightModifier = 1.7f;
		this.widthModifier = 1f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
			setUpRegen(1f*MapSession.getCuRegenMod());
		else
			setUpRegen(1);
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionMutantRobotFactory("normal");
		this.inBuildTexture = MapUnitTextureContainer.findRegionMutantRobotFactory("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new MutantRobotFactory(this.getFaction(), mapView, builder));
		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}

}
