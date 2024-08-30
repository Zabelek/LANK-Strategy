package pl.zabel.lank.texturecontainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapUnitTextureContainer {
		public static TextureAtlas basicUnitAtlas, cyborgLightBotAtlas, menuImagesAtlas,
		cyborgCommandUnitAtlas, cyborgRobotFactoryAtlas, cyborgMetalMineAtlas, cyborgPowerGeneratorAtlas,
		cyborgTurretAtlas, cyborgTankFactoryAtlas, cyborgLightTankAtlas, cyborgMediumTankAtlas,
		cyborgHeavyTankAtlas, cyborgMediumBotAtlas, cyborgHeavyBotAtlas, mutantCommandUnitAtlas,
		mutantLightBotAtlas, mutantMetalMineAtlas, mutantPowerGeneratorAtlas, mutantRobotFactoryAtlas,
		mutantTankFactoryAtlas, mutantTurretAtlas, mutantLightTankAtlas, mutantMediumTankAtlas,
		mutantHeavyTankAtlas, mutantMediumBotAtlas, mutantHeavyBotAtlas, alienCommandUnitAtlas,
		alienPowerGeneratorAtlas, alienMetalMineAtlas, alienLightUnitFactoryAtlas, alienHeavyUnitFactoryAtlas,
		alienTurretAtlas, alienLightUnitAtlas, alienMediumUnitAtlas, alienHeavyUnit1Atlas, alienHeavyUnit2Atlas;
		public static void load()
		{
			basicUnitAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/basic.atlas"));
			menuImagesAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/menuImages.atlas"));
			
			cyborgCommandUnitAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-command-unit.atlas"));
			cyborgLightBotAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-light-bot.atlas"));
			cyborgRobotFactoryAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-robot-factory.atlas"));
			cyborgMetalMineAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-metal-mine.atlas"));
			cyborgPowerGeneratorAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-power-generator.atlas"));		
			cyborgTurretAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-turret.atlas"));		
			cyborgTankFactoryAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-tank-factory.atlas"));		
			cyborgLightTankAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-light-tank.atlas"));		
			cyborgMediumTankAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-medium-tank.atlas"));		
			cyborgHeavyTankAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-heavy-tank.atlas"));		
			cyborgMediumBotAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-medium-bot.atlas"));		
			cyborgHeavyBotAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/cyborg-heavy-bot.atlas"));		
			
			mutantCommandUnitAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-command-unit.atlas"));
			mutantLightBotAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-light-bot.atlas"));
			mutantMetalMineAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-metal-mine.atlas"));
			mutantPowerGeneratorAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-power-generator.atlas"));
			mutantRobotFactoryAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-robot-factory.atlas"));
			mutantTankFactoryAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-tank-factory.atlas"));
			mutantTurretAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-turret.atlas"));
			mutantLightTankAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-light-tank.atlas"));
			mutantMediumTankAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-medium-tank.atlas"));
			mutantHeavyTankAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-heavy-tank.atlas"));
			mutantMediumBotAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-medium-bot.atlas"));
			mutantHeavyBotAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/mutant-heavy-bot.atlas"));
			
			alienCommandUnitAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-command-unit.atlas"));
			alienPowerGeneratorAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-power-generator.atlas"));
			alienMetalMineAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-metal-mine.atlas"));
			alienLightUnitFactoryAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-light-unit-factory.atlas"));
			alienHeavyUnitFactoryAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-heavy-unit-factory.atlas"));
			alienTurretAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-turret.atlas"));
			alienLightUnitAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-light-unit.atlas"));
			alienMediumUnitAtlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-medium-unit.atlas"));
			alienHeavyUnit1Atlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-heavy-unit1.atlas"));
			alienHeavyUnit2Atlas = new TextureAtlas(Gdx.files.internal("graphics/units/alien-heavy-unit2.atlas"));
		}
		public static void dispose()
		{
			basicUnitAtlas.dispose();
			basicUnitAtlas = null;
			cyborgLightBotAtlas.dispose();
			cyborgLightBotAtlas = null;
			menuImagesAtlas.dispose();
			menuImagesAtlas = null;
			cyborgCommandUnitAtlas.dispose();
			cyborgCommandUnitAtlas = null;
			cyborgRobotFactoryAtlas.dispose();
			cyborgRobotFactoryAtlas = null;
			cyborgMetalMineAtlas.dispose();
			cyborgMetalMineAtlas = null;
			cyborgPowerGeneratorAtlas.dispose();
			cyborgPowerGeneratorAtlas = null;
			cyborgTurretAtlas.dispose();
			cyborgTurretAtlas= null;
			cyborgTankFactoryAtlas.dispose();
			cyborgTankFactoryAtlas= null;
			cyborgLightTankAtlas.dispose();
			cyborgLightTankAtlas= null;
			cyborgMediumTankAtlas.dispose();
			cyborgMediumTankAtlas = null;
			cyborgHeavyTankAtlas.dispose();
			cyborgHeavyTankAtlas= null;
			cyborgMediumBotAtlas.dispose();
			cyborgMediumBotAtlas= null;
			cyborgHeavyBotAtlas.dispose();
			cyborgHeavyBotAtlas= null;
			
			mutantCommandUnitAtlas.dispose();
			mutantCommandUnitAtlas= null;
			mutantLightBotAtlas.dispose();
			mutantLightBotAtlas= null;
			mutantMetalMineAtlas.dispose();
			mutantMetalMineAtlas= null;
			mutantPowerGeneratorAtlas.dispose();
			mutantPowerGeneratorAtlas= null;
			mutantRobotFactoryAtlas.dispose();
			mutantRobotFactoryAtlas= null;		
			mutantTankFactoryAtlas.dispose();
			mutantTankFactoryAtlas= null;
			mutantTurretAtlas.dispose();
			mutantTurretAtlas= null;
			mutantLightTankAtlas.dispose();
			mutantLightTankAtlas= null;
			mutantMediumTankAtlas.dispose();
			mutantMediumTankAtlas= null;
			mutantHeavyTankAtlas.dispose();
			mutantHeavyTankAtlas= null;
			mutantMediumBotAtlas.dispose();
			mutantMediumBotAtlas= null;
			mutantHeavyBotAtlas.dispose();
			mutantHeavyBotAtlas= null;
			
			alienCommandUnitAtlas.dispose();
			alienCommandUnitAtlas= null;
			alienPowerGeneratorAtlas.dispose();
			alienPowerGeneratorAtlas= null;
			alienMetalMineAtlas.dispose();
			alienMetalMineAtlas= null;		
			alienLightUnitFactoryAtlas.dispose();
			alienLightUnitFactoryAtlas= null;
			alienHeavyUnitFactoryAtlas.dispose();
			alienHeavyUnitFactoryAtlas= null;
			alienTurretAtlas.dispose();
			alienTurretAtlas= null;
			alienLightUnitAtlas.dispose();
			alienLightUnitAtlas= null;
			alienMediumUnitAtlas.dispose();
			alienMediumUnitAtlas= null;
			alienHeavyUnit1Atlas.dispose();
			alienHeavyUnit1Atlas= null;
			alienHeavyUnit2Atlas.dispose();
			alienHeavyUnit2Atlas= null;
		}
		public static TextureRegion findRegionBasic(String name)
		{
			return basicUnitAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgLightBot(String name)
		{
			return cyborgLightBotAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgMediumBot(String name)
		{
			return cyborgMediumBotAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgHeavyBot(String name)
		{
			return cyborgHeavyBotAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMenuImage(String name)
		{
			return menuImagesAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgCommandUnit(String name)
		{
			return cyborgCommandUnitAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgRobotFactory(String name)
		{
			return cyborgRobotFactoryAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgMetalMine(String name)
		{
			return cyborgMetalMineAtlas.findRegion(name);
		}public static TextureRegion findRegionCyborgPowerGenerator(String name)
		{
			return cyborgPowerGeneratorAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgTurret(String name)
		{
			return cyborgTurretAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgTankFactory(String name)
		{
			return cyborgTankFactoryAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgLightTank(String name)
		{
			return cyborgLightTankAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgMediumTank(String name)
		{
			return cyborgMediumTankAtlas.findRegion(name);
		}
		public static TextureRegion findRegionCyborgHeavyTank(String name)
		{
			return cyborgHeavyTankAtlas.findRegion(name);
		}
		
		
		public static TextureRegion findRegionMutantTankFactory(String name)
		{
			return mutantTankFactoryAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantTurret(String name)
		{
			return mutantTurretAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantLightTank(String name)
		{
			return mutantLightTankAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantMediumTank(String name)
		{
			return mutantMediumTankAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantHeavyTank(String name)
		{
			return mutantHeavyTankAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantCommandUnit(String name)
		{
			return mutantCommandUnitAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantLightBot(String name)
		{
			return mutantLightBotAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantMetalMine(String name)
		{
			return mutantMetalMineAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantPowerGenerator(String name)
		{
			return mutantPowerGeneratorAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantRobotFactory(String name)
		{
			return mutantRobotFactoryAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantMediumBot(String name)
		{
			return mutantMediumBotAtlas.findRegion(name);
		}
		public static TextureRegion findRegionMutantHeavyBot(String name)
		{
			return mutantHeavyBotAtlas.findRegion(name);
		}
		
		
		public static TextureRegion findRegionAlienCommandUnit(String name)
		{
			return alienCommandUnitAtlas.findRegion(name);
		}
		public static TextureRegion findRegionAlienPowerGenerator(String name)
		{
			return alienPowerGeneratorAtlas.findRegion(name);
		}
		public static TextureRegion findRegionAlienMetalMine(String name)
		{
			return alienMetalMineAtlas.findRegion(name);
		}
		public static TextureRegion findRegionAlienLightUnitFactory(String name)
		{
			return alienLightUnitFactoryAtlas.findRegion(name);
		}
		public static TextureRegion findRegionAlienHeavyUnitFactory(String name)
		{
			return alienHeavyUnitFactoryAtlas.findRegion(name);
		}
		public static TextureRegion findRegionAlienTurret(String name)
		{
			return alienTurretAtlas.findRegion(name);
		}
		public static TextureRegion findRegionAlienLightUnit(String name)
		{
			return alienLightUnitAtlas.findRegion(name);
		}
		public static TextureRegion findRegionAlienMediumUnit(String name)
		{
			return alienMediumUnitAtlas.findRegion(name);
		}
		public static TextureRegion findRegionAlienHeavyUnit1(String name)
		{
			return alienHeavyUnit1Atlas.findRegion(name);
		}
		public static TextureRegion findRegionAlienHeavyUnit2(String name)
		{
			return alienHeavyUnit2Atlas.findRegion(name);
		}
}
