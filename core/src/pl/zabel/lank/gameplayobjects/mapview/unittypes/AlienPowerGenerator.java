package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.gameplayobjects.mapview.EnemyResourceGenerator;
import pl.zabel.lank.gameplayobjects.mapview.MapEnemy;
import pl.zabel.lank.gameplayobjects.mapview.ResourceGenerator;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class AlienPowerGenerator extends EnemyResourceGenerator{

	public AlienPowerGenerator(byte faction, MapView mapView, UnitBuilder builder, MapEnemy enemy) {
		super("Elektrownia", faction, mapView, builder, 1, enemy);
		this.generationSpeed=20;
		this.type = true;
		this.setSize(230, 120);
		this.imageName="alien-power-generator-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 500;
		this.currentHitPoints = 500;
		
		this.buildTicksLeft = 150;
		this.maxBuildTicks = 150;
		this.massTickCost=2;
		this.energyTickCost=4;
		findRegionForSpecificUnit();
		
		this.heightModifier = 2.4f;
		this.widthModifier = 1.3f;
		this.yOffset=-50;
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
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionAlienPowerGenerator("normal");
		this.inBuildTexture = MapUnitTextureContainer.findRegionAlienPowerGenerator("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new AlienPowerGenerator(this.getFaction(), mapView, builder, enemy));		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}
}
