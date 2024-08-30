package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.gameplayobjects.mapview.ResourceGenerator;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgPowerGenerator extends ResourceGenerator{

	public CyborgPowerGenerator(byte faction, MapView mapView, UnitBuilder builder) {
		super("Elektrownia", faction, mapView, builder, 8);
		this.generationSpeed=10;
		this.type = true;
		this.setSize(230, 120);
		this.imageName="cyborg-power-generator-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 500;
		this.currentHitPoints = 500;
		
		this.buildTicksLeft = 150;
		this.maxBuildTicks = 150;
		this.massTickCost=2;
		this.energyTickCost=4;
		findRegionForSpecificUnit();
		
		this.heightModifier = 1.8f;
		this.widthModifier = 1f;
		updateTextureBounds();
	}
	@Override
	public boolean checkTerrain()
	{
		if(super.checkTerrain())
		{
			float mapCoordX = (this.getX() + this.getWidth() / 2);
			float mapCoordY = (this.getY() + this.getHeight() / 2);
			if (mapView.checkTileValue((mapCoordX - 100),(mapCoordY + 100)) == "ground" && mapView.checkTileValue((mapCoordX),(mapCoordY + 100)) == "ground" && mapView.checkTileValue((mapCoordX + 100),(mapCoordY + 100)) == "ground" && 
					mapView.checkTileValue((mapCoordX - 100),(mapCoordY)) == "ground" && mapView.checkTileValue((mapCoordX),(mapCoordY)) == "ground" && mapView.checkTileValue((mapCoordX + 100),(mapCoordY)) == "ground" && 
					mapView.checkTileValue((mapCoordX - 100),(mapCoordY - 100)) == "ground" && mapView.checkTileValue((mapCoordX),(mapCoordY - 100)) == "ground" && mapView.checkTileValue((mapCoordX + 100),(mapCoordY - 100)) == "ground") {
				return true;
			}
			else return false;
		}
		else return false;
	}
	@Override
	public void animate()
	{
		super.animate();
		if(!this.isInBuild())
			this.displayTexture = MapUnitTextureContainer.findRegionCyborgPowerGenerator(this.animationKey);
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionCyborgPowerGenerator("normal");
		this.inBuildTexture = MapUnitTextureContainer.findRegionCyborgPowerGenerator("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new CyborgPowerGenerator(this.getFaction(), mapView, builder));		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}
}
