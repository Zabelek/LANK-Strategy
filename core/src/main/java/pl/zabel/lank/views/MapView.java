package pl.zabel.lank.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.LaserowaAnihilacjaNajdzdzcowzKosmosu;
import pl.zabel.lank.MapSession;
import pl.zabel.lank.ScheduledAnimationTimer;
import pl.zabel.lank.ScheduledBulletTimer;
import pl.zabel.lank.ScheduledMovementTimer;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem.CaptureState;
import pl.zabel.lank.gameplayobjects.mapview.Bullet;
import pl.zabel.lank.gameplayobjects.mapview.CommandUnit;
import pl.zabel.lank.gameplayobjects.mapview.MapBuildSpectre;
import pl.zabel.lank.gameplayobjects.mapview.MapBuilding;
import pl.zabel.lank.gameplayobjects.mapview.MapEnemy;
import pl.zabel.lank.gameplayobjects.mapview.MapTurret;
import pl.zabel.lank.gameplayobjects.mapview.MapUnit;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit;
import pl.zabel.lank.gameplayobjects.mapview.NormalBuilding;
import pl.zabel.lank.gameplayobjects.mapview.RepairingUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienCommandUnit;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienHeavyUnit1;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienHeavyUnit2;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienLightUnit;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.AlienMediumUnit;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.CyborgCommandUnit;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.CyborgHeavyBot;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.CyborgHeavyTank;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.CyborgLightBot;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.CyborgLightTank;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.CyborgMediumBot;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.CyborgMediumTank;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.MutantCommandUnit;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.MutantHeavyBot;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.MutantHeavyTank;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.MutantLightBot;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.MutantLightTank;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.MutantMediumBot;
import pl.zabel.lank.gameplayobjects.mapview.unittypes.MutantMediumTank;
import pl.zabel.lank.gameplayobjects.missionview.Mission;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.texturecontainers.SpecialEffectTextureContainer;
import pl.zabel.lank.userinterface.MapInterface;
import pl.zabel.lank.userinterface.TutorialPopUp;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.Observer;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.utilities.maputilities.OrderEffect;
import pl.zabel.lank.utilities.maputilities.SpecialEffect;

public class MapView implements Screen, InputProcessor {
	
	private LaserowaAnihilacjaNajdzdzcowzKosmosu adapter;
	private Mission mission;
	private OrthographicCamera camera;
	private TiledMapRenderer tiledMapRenderer;
	private Stage unitsStage, interfaceStage, effectsStage;
	private MapInterface mapInterface;
	private InputMultiplexer inpM;

	// for camera movement by cursor
	private Timer.Task cameraMovementTask, unitSortTask, ecoUpdateTask, autoTargetTask;
	private float cameraMovementXModifier, cameraMovementYModifier;
	private int tiledMapWidth, tiledMapHeight;

	// tasks
	public static ScheduledMovementTimer movementTimer;
	public static ScheduledAnimationTimer animationTimer;
	public static ScheduledBulletTimer bulletTimer;
	public static TimerTask animationTask, bulletTask;

	// list for registering
	private ArrayList<MapUnit> registeredUnits, selectedUnits;
	private ArrayList<Bullet> registeredBullets;
	private ArrayList<SpecialEffect> registeredSpecialEffects;

	// for building
	private boolean placingMode;
	private MapBuilding buildingToPlace;
	private MapBuildSpectre buildSpectre;

	// commandUnits
	private CommandUnit playersUnit, enemysUnit;
	private int result; // 0 for not sloved, 1 for win, 2 for lose;
	private MapEnemy enemy;

	// battle music
	public static boolean battleStarted;

	// tutorial flags
	private boolean firstMessagePopUp;
	private TutorialPopUp firstPopUp;

	public MapView(LaserowaAnihilacjaNajdzdzcowzKosmosu adapter) {
		this.adapter = adapter;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		ScreenViewport viewport = new ScreenViewport();
		viewport.setCamera(camera);
		unitsStage = new Stage(viewport);
		interfaceStage = new Stage(new ScreenViewport());
		effectsStage = new Stage(viewport);
		this.inpM = new InputMultiplexer();
		this.inpM.addProcessor(interfaceStage);
		this.inpM.addProcessor(unitsStage);
		this.inpM.addProcessor(this);
		this.mapInterface = new MapInterface(this);
		interfaceStage.addActor(mapInterface);

		this.registeredUnits = new ArrayList<MapUnit>();
		this.selectedUnits = new ArrayList<MapUnit>();
		this.registeredBullets = new ArrayList<Bullet>();
		this.registeredSpecialEffects = new ArrayList<SpecialEffect>();
		placingMode = false;
		firstMessagePopUp = false;
	}

	public void loadMap(Mission mission) {
		result = 0;
		MapSession.resetMapSession();
		this.mission = mission;
		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(mission.getGameplayMap().getTiledMap());
		this.tiledMapWidth = mission.getGameplayMap().getTiledMap().getProperties().get("width", Integer.class);
		this.tiledMapHeight = mission.getGameplayMap().getTiledMap().getProperties().get("height", Integer.class);
		MapUnitTextureContainer.load();
		MapUnit.loadBasicTextures();
		SpecialEffectTextureContainer.load();
		setUpTasks();
		movementTimer = new ScheduledMovementTimer();
		movementTimer.run();
		animationTimer = new ScheduledAnimationTimer();
		animationTimer.run();
		bulletTimer = new ScheduledBulletTimer();
		bulletTimer.run();
		Gdx.input.setInputProcessor(inpM);
		animationTimer.scheduleAnimationTask(animationTask);
		bulletTimer.scheduleBulletTask(bulletTask);
		Timer.schedule(unitSortTask, 0, 1f);
		Timer.schedule(ecoUpdateTask, 0, 0.033f);
		Timer.schedule(autoTargetTask, 0, 1f);
		battleStarted = false;
		spawnCommandUnits();

		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if (!firstMessagePopUp) {
					firstPopUp = new TutorialPopUp(LanguageMap.findString("mapFirsTutorialPopup"), 1400, 900, 520, 120,
							1000, -90);
					interfaceStage.addActor(firstPopUp);
					firstMessagePopUp = true;
				}
			}
		}, 3f);
	}

	public void goBackToMissionView(byte state) {
		SoundEffectManager.stopAllSounds();
		this.adapter.goToMissionView(mission, state);
		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				for (int i = MapView.this.registeredUnits.size() - 1; i >= 0; i--) {
					MapView.this.registeredUnits.get(i).dispose();
				}
				for (int i = MapView.this.registeredSpecialEffects.size() - 1; i >= 0; i--) {
					MapView.this.registeredSpecialEffects.get(i).dispose();
				}
				for (int i = MapView.this.registeredBullets.size() - 1; i >= 0; i--) {
					MapView.this.registeredBullets.get(i).dispose();
				}
				SpecialEffectTextureContainer.dispose();
				MapUnitTextureContainer.dispose();
				MapView.this.emptySelectedUnits();
				animationTask.cancel();
				bulletTask.cancel();
				animationTimer.cancelTimer();
				movementTimer.cancelTimer();
				bulletTimer.cancelTimer();
				unitSortTask.cancel();
				ecoUpdateTask.cancel();
				autoTargetTask.cancel();
				camera.translate(-(camera.position.x - Gdx.graphics.getWidth() / 2),
						-(camera.position.y - Gdx.graphics.getHeight() / 2));

			}
		}, 1.1f, 1, 0);
	}

	public void setUpTasks() {
		cameraMovementTask = new Timer.Task() {
			@Override
			public void run() {
				if (cameraMovementXModifier > 0 && checkCameraPositionRight())
					camera.translate(cameraMovementXModifier, 0);
				else if (cameraMovementXModifier < 0 && checkCameraPositionLeft())
					camera.translate(cameraMovementXModifier, 0);
				if (cameraMovementYModifier > 0 && checkCameraPositionTop())
					camera.translate(0, cameraMovementYModifier);
				else if (cameraMovementYModifier < 0 && checkCameraPositionBot())
					camera.translate(0, cameraMovementYModifier);
			}
		};
		animationTask = new TimerTask() {
			@Override
			public void run() {
				for (MapUnit unit : registeredUnits) {
					if (unit.checkDistanceToCamera() < GameVariables.getRenderDistance()) {
						if (MobileUnit.class.isAssignableFrom(unit.getClass())) {
							((MobileUnit) unit).animateMovement();
							unit.findRegionForSpecificUnit();
						} else if (MapTurret.class.isAssignableFrom(unit.getClass())) {
							((MapTurret) unit).animateMovement();
							unit.findRegionForSpecificUnit();
						} else if (NormalBuilding.class.isAssignableFrom(unit.getClass())
								&& ((NormalBuilding) unit).hasAnimations()) {
							((NormalBuilding) unit).animate();
						}
					}
					if (MobileUnit.class.isAssignableFrom(unit.getClass())) {
						((MobileUnit) unit).updateEffectVolume();
					}
				}
				for (int i = registeredSpecialEffects.size() - 1; i >= 0; i--) {
					registeredSpecialEffects.get(i).nextFrame();
				}
				if (MapView.this.placingMode) {
					MapView.this.buildSpectre.chaeckColor();
				}
			}
		};
		bulletTask = new TimerTask() {
			@Override
			public void run() {
				for (int i = registeredBullets.size() - 1; i >= 0; i--) {
					Bullet bullet = registeredBullets.get(i);
					if (bullet.isFinished()) {
						bullet.dispose();
					} else if (!mission.getGameplayMap().isEnterableArea(bullet.getX(), bullet.getY())) {
						bullet.block();
					} else {
						for (int j = registeredUnits.size() - 1; j >= 0; j--) {
							if (bullet.colidesWithUnit(registeredUnits.get(j))) {
								bullet.hit(registeredUnits.get(j));
							}
						}
					}
				}
			}
		};
		unitSortTask = new Timer.Task() {
			@Override
			public void run() {
				sortUnitsInStage();
			}
		};
		ecoUpdateTask = new Timer.Task() {
			@Override
			public void run() {
				mapInterface.updateEnergyBar();
				mapInterface.updateMetalBar();
				if (result == 1) {
					goBackToMissionView((byte) 1);
					GameSession.nextTurn();
					result = 0;
					this.cancel();
				} else if (result == 2) {
					goBackToMissionView((byte) 2);
					GameSession.nextTurn();
					result = 0;
					this.cancel();
				}
			}
		};
		autoTargetTask = new Timer.Task() {
			@Override
			public void run() {
				for (MapUnit unit : MapView.this.registeredUnits) {
					if (MovingUnit.class.isAssignableFrom(unit.getClass())) {
						if (RepairingUnit.class.isAssignableFrom(unit.getClass())
								&& ((RepairingUnit) unit).isRepairing()) {
						} else if (UnitBuilder.class.isAssignableFrom(unit.getClass())
								&& ((UnitBuilder) unit).isBuilding()) {
						} else {
							for (MapUnit unit1 : MapView.this.registeredUnits) {
								if (unit.getFaction() != unit1.getFaction() && !((MovingUnit) unit).isShooting) {
									float xlen = unit.getX() - unit1.getX();
									float ylen = unit.getY() - unit1.getY();
									if ((float) Math.sqrt((xlen * xlen) + (ylen * ylen)) < ((MovingUnit) unit)
											.getRange()) {
										((MovingUnit) unit).startShooting(unit1);
									}
								}
							}
						}
					}

				}
			}
		};
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		this.unitsStage.act(Gdx.graphics.getDeltaTime());
		this.unitsStage.draw();
		this.effectsStage.act(Gdx.graphics.getDeltaTime());
		this.effectsStage.draw();
		this.interfaceStage.act(Gdx.graphics.getDeltaTime());
		this.interfaceStage.draw();

	}

	public boolean checkMapCollisionForPoint(float x, float y) {
		MapUnit test = new MapUnit();
		test.setBounds(x, y, 100, 100);
		test.updateTextureBounds();
		return checkMapCollisionForUnit(test);
	}

	public boolean checkMapCollisionForUnit(MapUnit unit)// false for no colision
	{
		float xTile = unit.getX() + unit.getWidth() / 2;
		float yTile = unit.getY() + unit.getHeight() / 2;
		if (this.mission.getGameplayMap().isEnterableArea(xTile, yTile)) {
			for (MapUnit unit1 : this.registeredUnits) {
				if (Math.abs(unit1.getX() - unit.getX()) < 600 && Math.abs(unit1.getY() - unit.getY()) < 600
						&& unit != unit1) {
					if (unit.colidesWithUnit(unit1)) {
						return true;
					}
				}
			}
			return false;
		} else
			return true;
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.LEFT)
			camera.translate(-50, 0);
		if (keycode == Input.Keys.RIGHT)
			camera.translate(50, 0);
		if (keycode == Input.Keys.UP)
			camera.translate(0, 50);
		if (keycode == Input.Keys.DOWN)
			camera.translate(0, -50);
		if (keycode == Input.Keys.ESCAPE)
			mapInterface.getLeaveRequest().setVisible(true);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.RIGHT) {
			if (this.placingMode) {
				this.placingMode = false;
				this.buildSpectre.remove();
				this.buildSpectre = null;
			} else if (!this.selectedUnits.isEmpty()) {
				Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
				Vector3 position = camera.unproject(clickCoordinates);
				if (!checkMapCollisionForPoint(clickCoordinates.x, clickCoordinates.y)) {
					for (MapUnit unit : this.selectedUnits) {

						if (RepairingUnit.class.isAssignableFrom(unit.getClass())) {
							((RepairingUnit) unit).stopRepairing();
						}
						if (MobileUnit.class.isAssignableFrom(unit.getClass())
								&& !((MobileUnit) unit).isMovementLocked()) {
							((MobileUnit) unit).scheduleMovingTask((clickCoordinates.x - unit.getWidth() / 2),
									clickCoordinates.y - unit.getHeight() / 4);
						}
						unit.setDestination(clickCoordinates.x, clickCoordinates.y);
						this.registeredSpecialEffects
								.add(new OrderEffect(clickCoordinates.x - 25, clickCoordinates.y - 25, this));
					}
				}
			}
		} else if (button == Input.Buttons.LEFT) {
			if (this.placingMode) {
				setPositionForPlacedBuilding(buildingToPlace, screenX, screenY);
				buildingToPlace.getBuilder().addToQueue(buildingToPlace);
				this.placingMode = false;
				this.buildSpectre.remove();
				this.buildSpectre = null;
				buildingToPlace = null;
			} else
				emptySelectedUnits();
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	public OrthographicCamera getCamera() {
		return this.camera;
	}

	public void startCameraMoving(int x, int y) {
		if (!cameraMovementTask.isScheduled()) {
			this.cameraMovementXModifier = x;
			this.cameraMovementYModifier = y;
			Timer.schedule(cameraMovementTask, 0, 0.01f);
		}
	}

	public void stopCameraMoving() {
		cameraMovementTask.cancel();
	}

	public boolean checkCameraPositionTop() {
		if (this.camera.position.y + Gdx.graphics.getHeight() / 2 < tiledMapHeight * 100) {
			return true;
		} else
			return false;
	}

	public boolean checkCameraPositionBot() {
		if (this.camera.position.y - Gdx.graphics.getHeight() / 2 > 0) {
			return true;
		} else
			return false;
	}

	public boolean checkCameraPositionLeft() {
		if (this.camera.position.x - Gdx.graphics.getWidth() / 2 > 0) {
			return true;
		} else
			return false;
	}

	public boolean checkCameraPositionRight() {
		if (this.camera.position.x + Gdx.graphics.getWidth() / 2 < tiledMapWidth * 100) {
			return true;
		} else
			return false;
	}

	public void emptySelectedUnits() {
		for (MapUnit unit : this.selectedUnits) {
			unit.unselect();
		}
		this.selectedUnits.clear();
		mapInterface.clearUnitParameters();
	}

	private void sortUnitsInStage() {// Sprawdzic sortowanie przy duzej liczbie(ok.100) jednostek i porownac czasowo
										// z sortowaniem tylko tych na ekranie.
		List<Actor> list = Arrays.asList(unitsStage.getActors().toArray());
		Collections.sort(list, new ActorComparator());
		unitsStage.clear();
		for (Actor actor : list) {
			unitsStage.addActor(actor);
		}
	}

	public void registerUnit(final MapUnit unit) {
		this.registeredUnits.add(unit);
		this.unitsStage.addActor(unit);
		unit.addListener(new ClickListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (button == Input.Buttons.LEFT && unit.getFaction() == (byte) 0)

				{
					if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
						emptySelectedUnits();
					}
					unit.select();
					mapInterface.setUnitParameters(unit);
					MapView.this.selectedUnits.add(unit);
				} else if (button == Input.Buttons.RIGHT && unit.getFaction() == (byte) 1) {
					if (RepairingUnit.class.isAssignableFrom(unit.getClass())) {
						((RepairingUnit) unit).stopRepairing();
					}
					for (MapUnit unit1 : MapView.this.selectedUnits) {
						if (MovingUnit.class.isAssignableFrom(unit1.getClass())) {
							((MovingUnit) unit1).startShooting(unit);
							if (MobileUnit.class.isAssignableFrom(unit1.getClass())
									&& !((MobileUnit) unit1).isMovementLocked())
								((MobileUnit) unit1).comeCloserToShoot();
						}
					}
				} else if (button == Input.Buttons.RIGHT && unit.getFaction() == (byte) 0) {
					if (RepairingUnit.class.isAssignableFrom(unit.getClass())) {
						((RepairingUnit) unit).stopRepairing();
					}
					for (MapUnit unit1 : MapView.this.selectedUnits) {
						if (RepairingUnit.class.isAssignableFrom(unit1.getClass()) && unit1 != unit) {
							((RepairingUnit) unit1).startRepairing(unit);
						}
					}
				}
				return true;
			}
		});
	}

	public void removeUnit(MapUnit unit) {
		unit.remove();
		this.registeredUnits.remove(unit);
	}

	public void registerBullet(Bullet bullet) {
		this.unitsStage.addActor(bullet);
		this.registeredBullets.add(bullet);
	}

	public void removeBullet(Bullet bullet) {
		this.registeredBullets.remove(bullet);
		bullet.remove();
	}

	public void registerSpecialEffect(SpecialEffect effect) {
		this.registeredSpecialEffects.add(effect);
		this.effectsStage.addActor(effect);
	}

	public void removeSpecialEffect(SpecialEffect effect) {
		effect.remove();
		this.registeredSpecialEffects.remove(effect);
	}

	private class ActorComparator implements Comparator<Actor> {
		@Override
		public int compare(Actor arg0, Actor arg1) {
			return (int) (arg1.getY()-arg0.getY());
		}
	}

	public MapInterface getMapInterface() {
		return mapInterface;
	}

	public boolean isPlacingMode() {
		return placingMode;
	}

	public void startPlacingMode(MapBuilding building) {
		if (this.placingMode == true) {
			this.placingMode = false;
			this.buildSpectre.remove();
			this.buildSpectre = null;
		}
		this.placingMode = true;
		this.buildingToPlace = building;
		this.buildSpectre = building.getBuildSpectre();
		this.interfaceStage.addActor(buildSpectre);
	}

	public String checkTileValue(float x, float y) {
		int tileValue = this.mission.getGameplayMap().getTileValue(x, y);
		if (tileValue % 10 == 1) {
			return "ground";
		} else if (tileValue == 4) {
			return "metal";
		}
		return "unable";
	}

	private void spawnCommandUnits() {
		if (this.mission.getMissionName() != "training") {
			enemysUnit = new AlienCommandUnit((byte) 1, this, getEnemy());
			enemy = new MapEnemy(2, enemysUnit, this);
			enemysUnit.attach(new Observer() {
				@Override
				public void update() {
					result = 1;
					MapView.this.mission.getStarSystem().setCaptureState(CaptureState.CAPTURED, null);
					getEnemy().finnishAi();
					String missionName = MapView.this.mission.getMissionName();
					if (missionName.equals("Bevra")) {
						GameSession.getAnna().setLocked(false);
					} else if (missionName.equals("Rabler-11")) {
						GameSession.getSimon().setLocked(false);
						GameSession.getEnemy().setAgressionLevel(60);
						GameSession.getEnemy().setPower(10);
					} else if (missionName.equals("ibomar")) {
						if (GameSession.getIsPlayerCyborg() == true)
							GameSession.getKaite().setLocked(false);
						else
							GameSession.getZack().setLocked(false);
						GameSession.getEnemy().setAgressionLevel(100);
						GameSession.getEnemy().setPower(15);
					} else if (missionName.equals("Wobler")) {
						GameSession.changeGalaxyEcoValue("titanium", 300);
						GameSession.changeGalaxyEcoValue("petroleum", 200);
						GameSession.changeGalaxyEcoValue("uranium", 200);
						GameSession.changeGalaxyEcoValue("gold", 200);
					} else if (missionName.equals("Samira")) {
						GameSession.changeGalaxyEcoValue("titanium", 50);
						GameSession.changeGalaxyEcoValue("gold", 100);
					} else if (missionName.equals("Rabler-11")) {
						GameSession.changeGalaxyEcoValue("titanium", 50);
						GameSession.changeGalaxyEcoValue("petroleum", 100);
					} else if (missionName.equals("SEM-PRAT448-0")) {
						GameSession.changeGalaxyEcoValue("titanium", 100);
						GameSession.changeGalaxyEcoValue("petroleum", 100);
						GameSession.changeGalaxyEcoValue("uranium", 100);
						GameSession.changeGalaxyEcoValue("gold", 100);
					} else if (missionName.equals("Garm-74")) {
						GameSession.changeGalaxyEcoValue("titanium", 100);
						GameSession.changeGalaxyEcoValue("petroleum", 100);
					} else if (missionName.equals("Samira")) {
						GameSession.changeGalaxyEcoValue("titanium", 100);
						GameSession.changeGalaxyEcoValue("gold", 100);
						GameSession.changeGalaxyEcoValue("uranium", 100);
					}
				}
			});
			enemysUnit.setPosition(mission.getGameplayMap().getEnemyStartX() * 100,
					mission.getGameplayMap().getEnemyStartY() * 100);
			registerUnit(enemysUnit);
		}
		if (GameSession.getIsPlayerCyborg() == true) {
			playersUnit = new CyborgCommandUnit((byte) 0, this);
			playersUnit.setPosition(mission.getGameplayMap().getPlayerStartX() * 100,
					mission.getGameplayMap().getPlayerStartY() * 100);
			registerUnit(playersUnit);
		} else {
			playersUnit = new MutantCommandUnit((byte) 0, this);
			playersUnit.setPosition(mission.getGameplayMap().getPlayerStartX() * 100,
					mission.getGameplayMap().getPlayerStartY() * 100);
			registerUnit(playersUnit);
		}
		playersUnit.attach(new Observer() {
			@Override
			public void update() {
				result = 2;
				if (getEnemy() != null)
					getEnemy().finnishAi();
			}
		});
		camera.translate(0, 0);
		camera.translate(playersUnit.getFixedPositionX() - Gdx.graphics.getWidth() / 2,
				playersUnit.getFixedPositionY() - Gdx.graphics.getHeight() / 2);
	}

	public float getPlayersXPosition() {
		return playersUnit.getFixedPositionX();
	}

	public float getPlayersYPosition() {
		return playersUnit.getFixedPositionY();
	}

	public CommandUnit getPlayersUnit() {
		return playersUnit;
	}

	public MapEnemy getEnemy() {
		return enemy;
	}

	public void setPositionForPlacedBuilding(MapBuilding building, float screenX, float screenY) {
		Vector3 clickCoordinates = new Vector3(screenX, screenY, 0);
		Vector3 position = camera.unproject(clickCoordinates);
		building.setPosition(position.x - building.getWidth() / 2, position.y - building.getHeight() / 2);
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
}
