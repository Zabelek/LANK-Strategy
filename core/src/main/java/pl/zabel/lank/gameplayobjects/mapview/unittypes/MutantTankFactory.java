package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.UnitFactory;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class MutantTankFactory extends UnitFactory{

public MutantTankFactory(byte faction, MapView mapView, UnitBuilder builder) {
		super("Fabryka Czolgow", faction, mapView, builder);
		this.setSize(340, 140);
		this.imageName="mutant-tank-factory-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 1200;
		this.currentHitPoints = 1200;
		
		this.buildTicksLeft = 500;
		this.maxBuildTicks = 500;
		this.massTickCost=3;
		this.energyTickCost=15;
		this.schemesList.add(new MutantLightTank(this.getFaction(), mapView, this));
		this.schemesList.add(new MutantMediumTank(this.getFaction(), mapView, this));
		this.schemesList.add(new MutantHeavyTank(this.getFaction(), mapView, this));
		this.buildSpeed = 20;
		
		this.heightModifier = 2f;
		this.widthModifier = 0.9f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
			setUpRegen(1f*MapSession.getCuRegenMod());
		else
			setUpRegen(1);
	}
@Override
public void findRegionForSpecificUnit()
{
	this.displayTexture = MapUnitTextureContainer.findRegionMutantTankFactory("normal");
	this.inBuildTexture = MapUnitTextureContainer.findRegionMutantTankFactory("in-build");
}
@Override
public void schemeActivationEvent() {
	SoundEffectManager.playSmallClickSound();
	mapView.startPlacingMode(new MutantTankFactory(this.getFaction(), mapView, builder));
	
}
@Override
protected void playSelectSound()
{
	SoundEffectManager.playBuildingSelectSound(1);
}
}
