package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MapUnit;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit.rotation;
import pl.zabel.lank.gameplayobjects.mapview.RepairingUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.texturecontainers.SpecialEffectTextureContainer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.MapView;

public class CyborgMediumBot extends RepairingUnit{
	
	protected TextureRegion buildLaser;
	protected float buildLaserAngle, laserLenght;
	
	public CyborgMediumBot(byte faction, MapView mapView, UnitBuilder builder) {
		super("Sredni Bot", faction, mapView, builder);
		this.setSize(120, 70);
		findRegionForSpecificUnit();
		this.imageName="cyborg-medium-bot-image";
		this.movementSoundName = "light-bot-walk";
		schemeImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(this.getMenuImageName())));
		this.buildTicks = 250;
		this.massTickCost=3;
		this.energyTickCost=7;
		this.setSpeed(70);
		this.maxHitPoints = 600;
		this.currentHitPoints = 600;
		
		this.heightModifier = 1.6f;
		this.widthModifier = 1.3f;
		updateTextureBounds();
		if(this.getFaction()==(byte)0)
		{
			this.setSpeed(this.getSpeed()*MapSession.getUnitMovementSpeedMod());
			setUpGunParameters(30, 30, 7, 3.5f*MapSession.getUnitShootSpeedMod(), 
					400*MapSession.getBotBulletSpeedMod(), 500, "lightBot");
			this.repairSpeed = MapSession.getUnitRepairSpeedMod();
		}
		else
			setUpGunParameters(30, 30, 7, 3.5f, 400, 500, "lightBot");
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
				this.gunPositionX = 15;
				this.gunPositionY = 25;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.turretAtlasKey =  "tower-botleft";
				this.gunPositionX = 0;
				this.gunPositionY = 50;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.turretAtlasKey =  "tower-botright";
				this.gunPositionX = 50;
				this.gunPositionY = 25;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.turretAtlasKey =  "tower-left";
				this.gunPositionX = 0;
				this.gunPositionY = 70;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.turretAtlasKey =  "tower-right";
				this.gunPositionX = 80;
				this.gunPositionY = 35;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.turretAtlasKey =  "tower-top";
				this.gunPositionX = 70;
				this.gunPositionY = 85;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.turretAtlasKey =  "tower-topleft";
				this.gunPositionX = 45;
				this.gunPositionY = 85;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.turretAtlasKey =  "tower-topright";
				this.gunPositionX = 90;
				this.gunPositionY = 70;
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
		this.displayTexture = MapUnitTextureContainer.findRegionCyborgMediumBot(atlasKey);
		this.displayTextureTurret = MapUnitTextureContainer.findRegionCyborgMediumBot(turretAtlasKey);
	}
	@Override
	public void schemeActivationEvent() {
		SoundEffectManager.playSmallClickSound();
		this.builder.addToQueue(new CyborgMediumBot(this.getFaction(), mapView, builder));		
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
