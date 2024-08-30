package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MapUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.RepairingUnit;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.texturecontainers.SpecialEffectTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgLightBot extends RepairingUnit{

	protected TextureRegion buildLaser;
	protected float buildLaserAngle, laserLenght;
	
	public CyborgLightBot(byte faction, MapView mapView, UnitBuilder builder) {
		super("Lekki Bot", faction, mapView, builder);
		this.setSize(100, 60);
		findRegionForSpecificUnit();
		this.imageName="cyborg-light-bot-image";
		this.movementSoundName = "light-bot-walk";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.buildTicks = 50;
		this.massTickCost=3;
		this.energyTickCost=8;
		this.setSpeed(90);
		this.maxHitPoints = 100;
		this.currentHitPoints = 100;
		
		this.heightModifier = 1.7f;
		this.widthModifier = 1f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.setSpeed(this.getSpeed()*MapSession.getUnitMovementSpeedMod());
			setUpGunParameters(30, 30, 10, 1.5f*MapSession.getUnitShootSpeedMod(), 
					500*MapSession.getBotBulletSpeedMod(), 400, "lightBot");
			this.repairSpeed = MapSession.getUnitRepairSpeedMod();
		}
		else
			setUpGunParameters(30, 30, 10, 1.5f, 500, 400, "lightBot");
		buildLaser = SpecialEffectTextureContainer.findRegion("cyborg-build-laser");
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
				this.gunPositionX = 25;
				this.gunPositionY = 20;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = 10;
				this.gunPositionY = 35;
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
				this.gunPositionX = 10;
				this.gunPositionY = 50;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 60;
				this.gunPositionY = 25;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 50;
				this.gunPositionY = 55;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = 35;
				this.gunPositionY = 50;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 60;
				this.gunPositionY = 40;
			}
	}
	@Override
	public void startRepairing(MapUnit unit)
	{
			SoundEffectManager.playBuildLaserSound(this.calculateEffectVolume());
			super.startRepairing(unit);
			setUpVisualBuildEffects(unit);
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionCyborgLightBot(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionCyborgLightBot(turretAtlasKey);
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		this.builder.addToQueue(new CyborgLightBot(this.getFaction(), mapView, builder));		
	}
	public void setUpVisualBuildEffects(MapUnit unit) {
		checkRotation();
		buildLaserAngle = (float) Math.atan2(
				(double) ((unit.getX() + unit.getWidth() / 2) - (this.getX() + gunPositionX)),
				(double) (unit.getY() - (this.getY() + gunPositionY)));
		buildLaserAngle = (buildLaserAngle * 180) / (float) Math.PI * -1;
		Vector2 vec = new Vector2(unit.getX() + unit.getWidth() / 2, unit.getY());
		vec.x = vec.x - (this.getX() + gunPositionX);
		vec.y = vec.y - (this.getY() + gunPositionY);
		laserLenght = vec.len();
	}
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		if (this.isRepairing()) {
			batch.draw(this.buildLaser, this.getX() + gunPositionX, this.getY() + gunPositionY, 10, 10, 20f,
					laserLenght, 1, 1, this.buildLaserAngle);
		}
	}
	@Override
	protected void playSelectSound()
	{
		SoundEffectManager.playBotSelectSound(1);
	}
}
