package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit.rotation;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class MutantLightBot extends MobileUnit{

	public MutantLightBot(byte faction, MapView mapView, UnitBuilder builder) {
		super("Lekki Bot", faction, mapView, builder);
		this.setSize(80, 40);
		findRegionForSpecificUnit();

		this.imageName="mutant-light-bot-image";
		this.movementSoundName = "light-bot-walk";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.buildTicks = 50;
		this.massTickCost=3;
		this.energyTickCost=8;
		this.setSpeed(70);
		this.maxHitPoints = 90;
		this.currentHitPoints = 90;
		
		this.heightModifier = 2.6f;
		this.widthModifier = 1.2f;
		this.xOffset=7;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.setSpeed(this.getSpeed()*MapSession.getUnitMovementSpeedMod());
			setUpGunParameters(30, 30, 15, 1f*MapSession.getUnitShootSpeedMod(), 
					500*MapSession.getBotBulletSpeedMod(), 450, "lightBot");
			setUpRegen(1f*MapSession.getCuRegenMod());
		}
		else
		{
			setUpGunParameters(30, 30, 15, 1f, 500, 450, "lightBot");
			setUpRegen(1);
		}
		}
	@Override
	public void checkRotation()
	{
		this.getMovingRotation();
		if(this.currentRotation==rotation.BOTTOM)
			this.atlasKey =  "podw-bot-";
		else if(this.currentRotation==rotation.BOTTOMLEFT)
			this.atlasKey =  "podw-botleft-";
		else if(this.currentRotation==rotation.BOTTOMRIGHT)
			this.atlasKey =  "podw-botright-";
		else if(this.currentRotation==rotation.LEFT)
			this.atlasKey =  "podw-left-";
		else if(this.currentRotation==rotation.RIGHT)
			this.atlasKey =  "podw-right-";
		else if(this.currentRotation==rotation.TOP)
			this.atlasKey =  "podw-top-";
		else if(this.currentRotation==rotation.TOPLEFT)
			this.atlasKey =  "podw-topleft-";
		else if(this.currentRotation==rotation.TOPRIGHT)
			this.atlasKey =  "podw-topright-";

			if(this.currentTurretRotation==rotation.BOTTOM)
			{
				this.turretAtlasKey =  "tower-bot";
				this.gunPositionX = 5;
				this.gunPositionY = 20;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = -10;
				this.gunPositionY = 40;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 35;
				this.gunPositionY = 25;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = -10;
				this.gunPositionY = 60;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 60;
				this.gunPositionY = 35;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 50;
				this.gunPositionY = 70;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = 15;
				this.gunPositionY = 70;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 65;
				this.gunPositionY = 50;
			}
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionMutantLightBot(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionMutantLightBot(turretAtlasKey);
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		this.builder.addToQueue(new MutantLightBot(this.getFaction(), mapView, builder));		
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBotSelectSound(1);
	}
}
