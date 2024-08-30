package pl.zabel.lank.gameplayobjects.mapview.unittypes;

import com.badlogic.gdx.graphics.g2d.Batch;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.mapview.CommandUnit;
import pl.zabel.lank.gameplayobjects.mapview.MapEnemy;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.texturecontainers.SpecialEffectTextureContainer;
import pl.zabel.lank.views.MapView;

public class AlienCommandUnit extends CommandUnit{

	public AlienCommandUnit(byte faction, MapView mapView, MapEnemy enemy) {
		super(faction, mapView);
		setUpGunParameters(30, 100, 50, 0.4f, 600, 500, "commandUnit");
		this.imageName="alien-command-unit-image";
		this.movementSoundName = "command-unit-walk";
		this.maxHitPoints = 3200;
		this.currentHitPoints = 3200;
		buildLaser = SpecialEffectTextureContainer.findRegion("alien-build-laser");
		schemesList.add(new AlienPowerGenerator(this.getFaction(), mapView, this, enemy));
		schemesList.add(new AlienMetalMine(this.getFaction(), mapView, this, enemy));
		schemesList.add(new AlienLightUnitFactory(this.getFaction(), mapView, this, enemy));
		schemesList.add(new AlienHeavyUnitFactory(this.getFaction(), mapView, this, enemy));
		schemesList.add(new AlienTurret(this.getFaction(), mapView, this, enemy));
		this.heightModifier = 3.8f;
		this.widthModifier = 1.5f;
		updateTextureBounds();
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
				this.atlasKey =  "podw-bot-";
				this.gunPositionX = 75;
				this.gunPositionY = 65;
				this.laserPositionX = 15;
				this.laserPositionY = 80;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMLEFT)
			{
				this.atlasKey =  "podw-botleft-";
				this.gunPositionX = 30;
				this.gunPositionY = 60;
				this.laserPositionX = -5;
				this.laserPositionY = 105;
			}
			else if(this.currentTurretRotation==rotation.BOTTOMRIGHT)
			{
				this.atlasKey =  "podw-botright-";
				this.gunPositionX = 100;
				this.gunPositionY = 95;
				this.laserPositionX = 50;
				this.laserPositionY = 75;
			}
			else if(this.currentTurretRotation==rotation.LEFT)
			{
				this.atlasKey =  "podw-left-";
				this.gunPositionX = -15;
				this.gunPositionY = 75;
				this.laserPositionX = 0;
				this.laserPositionY = 125;
			}
			else if(this.currentTurretRotation==rotation.RIGHT)
			{
				this.atlasKey =  "podw-right-";
				this.gunPositionX = 90;
				this.gunPositionY = 125;
				this.laserPositionX = 85;
				this.laserPositionY = 90;
			}
			else if(this.currentTurretRotation==rotation.TOP)
			{
				this.atlasKey =  "podw-top-";
				this.gunPositionX = 5;
				this.gunPositionY = 130;
				this.laserPositionX = 70;
				this.laserPositionY = 140;
			}
			else if(this.currentTurretRotation==rotation.TOPLEFT)
			{
				this.atlasKey =  "podw-topleft-";
				this.gunPositionX = -20;
				this.gunPositionY = 105;
				this.laserPositionX = 20;
				this.laserPositionY = 135;
			}
			else if(this.currentTurretRotation==rotation.TOPRIGHT)
			{
				this.atlasKey =  "podw-topright-";
				this.gunPositionX = 40;
				this.gunPositionY = 135;
				this.laserPositionX = 95;
				this.laserPositionY = 105;
			}
	}
	@Override
	public void findRegionForSpecificUnit()
	{
		this.displayTexture = MapUnitTextureContainer.findRegionAlienCommandUnit(atlasKey);
	}
	public void animateMovement()
	{
		this.checkRotation();
		if(this.hasActions())
		{
			this.movingTick+=1;
			if(this.movingTick==5)
				this.movingTick=1;
			this.atlasKey +=this.movingTick;
		}
		else {
			this.atlasKey+="0";
		}		
	}
	@Override
	public void draw(Batch batch, float alpha) {
		if(this.currentDistanceToCamera<GameVariables.getRenderDistance())
		{
			if(GameVariables.isShadows()==true)
			{
				batch.setColor(0, 0, 0, 0.7f);
				GameVariables.getShadowShear().setToTranslation(getX(), getY()+10);
				GameVariables.getShadowShear().shear(-1f, 0);
				batch.draw(displayTexture, this.textureWidth, this.textureHeight/2, GameVariables.getShadowShear());
				batch.setColor(1, 1, 1, 1);
			}
		if(selected)
		{
			batch.draw(selectionTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		}
		batch.draw(displayTexture, this.textureX, this.textureY, this.textureWidth, this.textureHeight);
		batch.draw(maxHpBar, this.getX()+20, this.getY()-10, this.getWidth()-40, 5);
		batch.draw(currentHpBar, this.getX()+20, this.getY()-10, (this.getWidth()-40)*((float)this.currentHitPoints/(float)this.maxHitPoints), 5);
	
		if (this.isBuilding()) {
			
			batch.draw(this.buildLaser, this.getX() + laserPositionX, this.getY() + laserPositionY, 10, 10, 20f,
					laserLenght, 1, 1, this.buildLaserAngle);

		}
		}
	}
}
