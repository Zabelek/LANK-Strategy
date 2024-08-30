package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.gameplayobjects.mapview.EnemyResourceGenerator;
import pl.zabel.lank.gameplayobjects.mapview.MapEnemy;
import pl.zabel.lank.gameplayobjects.mapview.ResourceGenerator;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class AlienMetalMine extends EnemyResourceGenerator{

	public AlienMetalMine(byte faction, MapView mapView, UnitBuilder builder, MapEnemy enemy) {
		super("Kopalnia Metalu", faction, mapView, builder, 1, enemy);
		this.generationSpeed=8;
		this.type = false;
		this.setSize(130, 80);
		this.imageName="alien-metal-mine-image";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.maxHitPoints = 300;
		this.currentHitPoints = 300;
		
		this.buildTicksLeft = 100;
		this.maxBuildTicks = 100;
		this.massTickCost=2;
		this.energyTickCost=5;
		findRegionForSpecificUnit();
		
		this.heightModifier = 2.1f;
		this.widthModifier = 1.4f;
		this.yOffset=-20;
		updateTextureBounds();
	}
	@Override
	public boolean checkTerrain()
	{
		if(super.checkTerrain())
		{
			float mapCoordX = (this.getX() + this.getWidth() / 2);
			float mapCoordY = (this.getY() + this.getHeight() / 2);
			if (mapView.checkTileValue((mapCoordX),(mapCoordY)) == "metal") {
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
			this.displayTexture = MapUnitTextureContainer.findRegionAlienMetalMine(this.animationKey);
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionAlienMetalMine("normal");
		this.inBuildTexture = MapUnitTextureContainer.findRegionAlienMetalMine("in-build");
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		mapView.startPlacingMode(new AlienMetalMine(this.getFaction(), mapView, builder, enemy));
		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBuildingSelectSound(1);
	}
}
