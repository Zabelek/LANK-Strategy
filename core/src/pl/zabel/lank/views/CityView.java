package pl.zabel.lank.views;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.LaserowaAnihilacjaNajdzdzcowzKosmosu;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.City.CityType;
import pl.zabel.lank.texturecontainers.CityTextureContainer;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.LabWojTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.userinterface.CityFragmentImage;
import pl.zabel.lank.userinterface.MenuButton;
import pl.zabel.lank.userinterface.MenuWindow;
import pl.zabel.lank.userinterface.TranslatableMenuButton;
import pl.zabel.lank.userinterface.mapview.BuildingButton;
import pl.zabel.lank.userinterface.mapview.LabTechWindow;
import pl.zabel.lank.userinterface.mapview.LabWojWindow;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.MusicManager;
import pl.zabel.lank.utilities.SoundEffectManager;

public class CityView implements Screen {

	public static Pixmap texturesPixmap;

	private LaserowaAnihilacjaNajdzdzcowzKosmosu adapter;
	private Stage cityPartsStage, interfaceStage;
	private City city;

	private ArrayList<CityFragmentImage> registeredImages;
	private TranslatableMenuButton goBackButton;
	private BuildingsWindow buildingsWindow;
	private InputMultiplexer inpM;
	// images
	CityFragmentImage background, hq, trainFac, labTech, labMil, intel, special, wall;
	private LabTechWindow labtechWindow;
	private LabWojWindow labWojWindow;
	private MenuWindow wallInfo, intelInfo, trainFacInfo, specialInfo;
	private BuildRequestWindow buildRequestWindow;

	public CityView(LaserowaAnihilacjaNajdzdzcowzKosmosu adapter) {
		this.adapter = adapter;
		this.cityPartsStage = new Stage(new ScreenViewport());
		this.interfaceStage = new Stage(new ScreenViewport());
		registeredImages = new ArrayList<CityFragmentImage>();
		this.goBackButton = new TranslatableMenuButton(LanguageMap.findString("backToGalaxyView"),
				50 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY());
		this.goBackButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				{
					fadeOut(0.5f);
					Timer.schedule(new Timer.Task() {
						@Override
						public void run() {
							for (Actor comp : CityView.this.adapter.getGalaxyView().sharedInterfaceComponents) {
								comp.remove();
							}
							CityView.this.adapter.getGalaxyRegionView().addSharedComponents();
							CityView.this.adapter.getGalaxyRegionView().fadeIn(0.5f);
							CityView.this.adapter.getGalaxyView().setInterfaceStartPosition();
							CityView.this.adapter.getGalaxyView().animateInterfaceOpening();
							CityView.this.adapter.setScreen(CityView.this.adapter.getGalaxyRegionView());
							Gdx.input.setInputProcessor(CityView.this.adapter.getGalaxyRegionView().inpM);
							CityView.this.adapter.getBlack().remove();
							CityTextureContainer.dispose();
							LabWojTextureContainer.dispose();
							texturesPixmap.dispose();
							texturesPixmap = null;
							MusicManager.playGalaxyViewMusic();
							this.cancel();
						}
					}, 0.5f, 1, 1);
					event.stop();
				}
			}
		});
		this.interfaceStage.addActor(goBackButton);
		this.interfaceStage.addActor(goBackButton.getContainer());
		this.inpM = new InputMultiplexer();
		this.inpM.addProcessor(cityPartsStage);
		this.inpM.addProcessor(interfaceStage);
		this.buildingsWindow = new BuildingsWindow();
		this.buildingsWindow.setVisible(false);
		this.interfaceStage.addActor(this.buildingsWindow);
		this.labtechWindow = new LabTechWindow(this, adapter.getGalaxyView());
		this.labtechWindow.setVisible(false);
		this.interfaceStage.addActor(this.labtechWindow);
		LabWojTextureContainer.load();
		this.labWojWindow = new LabWojWindow(this, adapter.getGalaxyView());
		this.labWojWindow.setVisible(false);
		this.interfaceStage.addActor(this.labWojWindow);
		this.wallInfo = new MenuWindow(LanguageMap.findString("wallInfoText"), 500 * GameVariables.getScaleX(),
				340 * GameVariables.getScaleY(), 920 * GameVariables.getScaleX(), 400 * GameVariables.getScaleY());
		this.wallInfo.setVisible(false);
		this.interfaceStage.addActor(this.wallInfo);
		this.intelInfo = new MenuWindow(LanguageMap.findString("intelInfoText"), 500 * GameVariables.getScaleX(),
				340 * GameVariables.getScaleY(), 920 * GameVariables.getScaleX(), 400 * GameVariables.getScaleY());
		this.intelInfo.setVisible(false);
		this.interfaceStage.addActor(this.intelInfo);
		this.trainFacInfo = new MenuWindow(LanguageMap.findString("trainFacInfoText"), 500 * GameVariables.getScaleX(),
				340 * GameVariables.getScaleY(), 920 * GameVariables.getScaleX(), 400 * GameVariables.getScaleY());
		this.trainFacInfo.setVisible(false);
		this.interfaceStage.addActor(this.trainFacInfo);
		this.specialInfo = new MenuWindow(LanguageMap.findString("specialPlaceInfoText"),
				500 * GameVariables.getScaleX(), 340 * GameVariables.getScaleY(), 920 * GameVariables.getScaleX(),
				400 * GameVariables.getScaleY());
		this.specialInfo.setVisible(false);
		this.interfaceStage.addActor(this.specialInfo);
		this.buildRequestWindow = new BuildRequestWindow();
		this.buildRequestWindow.setVisible(false);
		this.interfaceStage.addActor(this.buildRequestWindow.getLightFade());
		this.interfaceStage.addActor(this.buildRequestWindow);

	}

	public void setCity(City city) {
		this.city = city;
		TextureRegion tempRegion = CityTextureContainer.findRegion("cyborg_hq_1lvl");
		tempRegion.getTexture().getTextureData().prepare();
		texturesPixmap = tempRegion.getTexture().getTextureData().consumePixmap();
		updateCityGraphics();
	}

	public void updateCityGraphics() {
		registeredImages.clear();
		cityPartsStage.clear();
		// background and hq
		if (this.city.getType() == CityType.MUTANT) {
			if (city.getHqLevel() == 1) {
				background = new CityFragmentImage("mutant_background_1lvl", false, 0, 0, 1920, 1080);
				hq = new CityFragmentImage("mutant_hq_1lvl", true, 410, 540, 440, 360);
				registeredImages.add(background);
				registeredImages.add(hq);
			} else if (city.getHqLevel() == 2) {
				background = new CityFragmentImage("mutant_background_2lvl", false, 0, 0, 1920, 1080);
				hq = new CityFragmentImage("mutant_hq_2lvl", true, 410, 540, 440, 360);
				registeredImages.add(background);
				registeredImages.add(hq);
			} else {
				background = new CityFragmentImage("mutant_background_3lvl", false, 0, 0, 1920, 1080);
				hq = new CityFragmentImage("mutant_hq_3lvl", true, 410, 540, 440, 360);
				registeredImages.add(background);
				registeredImages.add(hq);
			}
		} else {
			if (city.getHqLevel() == 1) {
				background = new CityFragmentImage("cyborg_background_1lvl", false, 0, 0, 1920, 1080);
				hq = new CityFragmentImage("cyborg_hq_1lvl", true, 1270, 470, 390, 400);
				registeredImages.add(background);
				registeredImages.add(hq);
			} else if (city.getHqLevel() == 2) {
				background = new CityFragmentImage("cyborg_background_2lvl", false, 0, 0, 1920, 1080);
				hq = new CityFragmentImage("cyborg_hq_2lvl", true, 1270, 470, 390, 400);
				registeredImages.add(background);
				registeredImages.add(hq);
			} else {
				background = new CityFragmentImage("cyborg_background_3lvl", false, 0, 0, 1920, 1080);
				hq = new CityFragmentImage("cyborg_hq_3lvl", true, 1270, 470, 390, 400);
				registeredImages.add(background);
				registeredImages.add(hq);
			}
		}
		// train fac
		if (city.getType() == CityType.CYBORG && city.isHasTrainFac() == true) {
			trainFac = new CityFragmentImage("cyborg_trainfac", true, 340, 380, 300, 400);
			registeredImages.add(trainFac);
		} else if (city.getType() == CityType.MUTANT && city.isHasTrainFac() == true) {
			if (city.getHqLevel() == 1)
				trainFac = new CityFragmentImage("mutant_trainfac", true, 1200, 440, 300, 480);
			else
				trainFac = new CityFragmentImage("mutant_trainfac_b", true, 1200, 440, 300, 480);
			registeredImages.add(trainFac);
		}
		// special
		if (city.getType() == CityType.CYBORG && city.isHasSpecBuild() == true) {
			special = new CityFragmentImage("cyborg_special", true, 1310, 180, 430, 350);
			registeredImages.add(special);
		} else if (city.getType() == CityType.MUTANT && city.isHasSpecBuild() == true) {
			if (city.getHqLevel() == 1)
				special = new CityFragmentImage("mutant_special_b", true, 830, 380, 350, 600);
			else
				special = new CityFragmentImage("mutant_special", true, 830, 380, 350, 600);
			registeredImages.add(special);
		}
		// labtech
		if (city.getType() == CityType.CYBORG && city.isHasLabMech() == true) {
			labTech = new CityFragmentImage("cyborg_labtech", true, 1620, 230, 300, 400);
			registeredImages.add(labTech);
		} else if (city.getType() == CityType.MUTANT && city.isHasLabMech() == true) {
			labTech = new CityFragmentImage("mutant_labtech", true, 0, 240, 450, 420);
			registeredImages.add(labTech);
		}
		// labwoj
		if (city.getType() == CityType.CYBORG && city.isHasLabWoj() == true) {
			labMil = new CityFragmentImage("cyborg_labwoj", true, 500, 220, 500, 300);
			registeredImages.add(labMil);
		} else if (city.getType() == CityType.MUTANT && city.isHasLabWoj() == true) {
			labMil = new CityFragmentImage("mutant_labwoj", true, 1350, 150, 570, 500);
			registeredImages.add(labMil);
		}
		// intel
		if (city.getType() == CityType.CYBORG && city.isHasIntel() == true) {
			intel = new CityFragmentImage("cyborg_intel", true, 0, 340, 400, 520);
			registeredImages.add(intel);
		} else if (city.getType() == CityType.MUTANT && city.isHasIntel() == true) {
			intel = new CityFragmentImage("mutant_intel", true, 800, 520, 140, 250);
			registeredImages.add(intel);
		}
		// mur
		if (city.getType() == CityType.CYBORG && city.isHasWall() == true) {
			wall = new CityFragmentImage("cyborg_wall", true, 680, 70, 1240, 400);
			registeredImages.add(wall);
		} else if (city.getType() == CityType.MUTANT && city.isHasWall() == true) {
			if (city.isHasLabWoj())
				wall = new CityFragmentImage("mutant_wall", true, 0, 100, 1920, 450);
			else
				wall = new CityFragmentImage("mutant_wall_b", true, 0, 100, 1920, 450);
			registeredImages.add(wall);
		}
		// addToStage
		cityPartsStage.clear();
		for (CityFragmentImage img : this.registeredImages) {
			cityPartsStage.addActor(img);
		}
		this.cityPartsStage.addActor(this.buildingsWindow.getLightFade());
		this.cityPartsStage.addActor(this.labtechWindow.getLightFade());
		this.cityPartsStage.addActor(this.labWojWindow.getLightFade());
		this.cityPartsStage.addActor(this.intelInfo.getLightFade());
		this.cityPartsStage.addActor(this.wallInfo.getLightFade());
		this.cityPartsStage.addActor(this.specialInfo.getLightFade());
		this.cityPartsStage.addActor(this.trainFacInfo.getLightFade());
		setUpListeners();
		this.buildingsWindow.updateByCity(city);
		this.labtechWindow.updateButtons();
		this.labWojWindow.updateUpgradeParts();
		for (Actor actor : this.interfaceStage.getActors()) {
			if (MenuWindow.class.isAssignableFrom(actor.getClass())) {
				((MenuWindow) actor).getLightFade().remove();
				this.cityPartsStage.addActor(((MenuWindow) actor).getLightFade());
			}
		}
	}

	public void setUpListeners() {

		// hq
		hq.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				buildingsWindow.setVisible(true);
			}
		});
		// labtech
		if (labTech != null) {
			labTech.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					labtechWindow.setVisible(true);
				}
			});
		}
		// labwoj
		if (labMil != null) {
			labMil.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					labWojWindow.setVisible(true);
				}
			});
		}
		// intel
		if (intel != null) {
			intel.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					intelInfo.setVisible(true);
				}
			});
		}
		// trump
		if (wall != null) {
			wall.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					wallInfo.setVisible(true);
				}
			});
		}
		// trainfac
		if (trainFac != null) {
			trainFac.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					trainFacInfo.setVisible(true);
				}
			});
		}
		// special
		if (special != null) {
			special.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					specialInfo.setVisible(true);
					if (city.getType() == CityType.MUTANT) {
						specialInfo.setText(LanguageMap.findString("specialPlaceMutantInfoText"));
					} else {
						specialInfo.setText(LanguageMap.findString("specialPlaceCyborgInfoText"));
					}
				}
			});
		}

	}

	public void addSharedComponents() {
		for (Actor actor : this.adapter.getGalaxyView().getSharedInterfaceComponents()) {
			this.interfaceStage.addActor(actor);
			if (MenuWindow.class.isAssignableFrom(actor.getClass())) {
				this.cityPartsStage.addActor(((MenuWindow) actor).getLightFade());
			}
		}
	}

	public void fadeOut(float time) {
		this.adapter.getGalaxyView().mainGame.getBlack().remove();
		this.interfaceStage.addActor(CityView.this.adapter.getBlack());
		this.adapter.getGalaxyView().mainGame.getBlack().fadeIn(time);
	}

	public void fadeIn(float time) {
		this.interfaceStage.addActor(CityView.this.adapter.getBlack());
		this.adapter.getGalaxyView().mainGame.getBlack().fadeOut(time);
		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				CityView.this.adapter.getBlack().remove();
				this.cancel();

			}
		}, time, 1, 1);

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		cityPartsStage.act(Gdx.graphics.getDeltaTime());
		cityPartsStage.draw();
		interfaceStage.act(Gdx.graphics.getDeltaTime());
		interfaceStage.draw();

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

	public class BuildingsWindow extends MenuWindow {
		private BuildingButton hqButton, wallButton, intelButton, labTechButton, labMilButton, trainFacButton,
				specialButton;

		public BuildingsWindow() {
			super(LanguageMap.findString("build"), 210 * GameVariables.getScaleX(), 140 * GameVariables.getScaleY(),
					1500 * GameVariables.getScaleX(), 800 * GameVariables.getScaleY());
			this.textDisplayed.setY(420);
		}

		public void updateByCity(final City city) {
			if (hqButton != null) {
				hqButton.remove();
				wallButton.remove();
				intelButton.remove();
				labTechButton.remove();
				labMilButton.remove();
				trainFacButton.remove();
				specialButton.remove();
			}
			if (city.getType() == CityType.MUTANT) {
				if (city.getHqLevel() == (byte) 1)
					hqButton = new BuildingButton(LanguageMap.findString("hQ1Title"), "mutant_hq_2lvl_button", 50, 450);
				else
					hqButton = new BuildingButton(LanguageMap.findString("hQ2Title"), "mutant_hq_3lvl_button", 50, 450);
				wallButton = new BuildingButton(LanguageMap.findString("wallTitle"), "mutant_wall_button", 410, 450);
				intelButton = new BuildingButton(LanguageMap.findString("intelTitle"), "mutant_intel_button", 770, 450);
				labTechButton = new BuildingButton(LanguageMap.findString("labTexgTitle"), "mutant_labtech_button",
						1130, 450);
				labMilButton = new BuildingButton(LanguageMap.findString("labWojTitle"), "mutant_labwoj_button", 280,
						150);
				trainFacButton = new BuildingButton(LanguageMap.findString("trainFacTitle"), "mutant_trainfac_button",
						640, 150);
				specialButton = new BuildingButton(LanguageMap.findString("modGenTitle"), "mutant_special_button", 1000,
						150);

			} else {
				if (city.getHqLevel() == (byte) 1)
					hqButton = new BuildingButton(LanguageMap.findString("hQ1Title"), "cyborg_hq_2lvl_button", 50, 450);
				else
					hqButton = new BuildingButton(LanguageMap.findString("hQ2Title"), "cyborg_hq_3lvl_button", 50, 450);
				wallButton = new BuildingButton(LanguageMap.findString("wallTitle"), "cyborg_wall_button", 410, 450);
				intelButton = new BuildingButton(LanguageMap.findString("intelTitle"), "cyborg_intel_button", 770, 450);
				labTechButton = new BuildingButton(LanguageMap.findString("labTexgTitle"), "cyborg_labtech_button",
						1130, 450);
				labMilButton = new BuildingButton(LanguageMap.findString("labWojTitle"), "cyborg_labwoj_button", 280,
						150);
				trainFacButton = new BuildingButton(LanguageMap.findString("trainFacTitle"), "cyborg_trainfac_button",
						640, 150);
				specialButton = new BuildingButton(LanguageMap.findString("modCybTitle"), "cyborg_special_button", 1000,
						150);

			}
			if (city.isHasIntel())
				intelButton.setBuilt(true);
			if (city.isHasWall())
				wallButton.setBuilt(true);
			if (city.isHasIntel())
				intelButton.setBuilt(true);
			if (city.isHasLabMech())
				labTechButton.setBuilt(true);
			if (city.isHasLabWoj())
				labMilButton.setBuilt(true);
			if (city.isHasTrainFac())
				trainFacButton.setBuilt(true);
			if (city.isHasSpecBuild())
				specialButton.setBuilt(true);
			if (city.getHqLevel() == (byte) 3)
				hqButton.setBuilt(true);
			hqButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (city.getHqLevel() == (byte) 1) {
						buildRequestWindow.setText(LanguageMap.findString("cityUpgradeRequest1"));
						if (GameSession.getCurrentTitanium() < 120 || GameSession.getCurrentPetroleum() < 40
								|| GameSession.getCurrentUranium() < 10 || GameSession.getCurrentGold() < 100
								|| city.hasBuiltInThisTurn()) {
							buildRequestWindow.lockOkButton();
						} else {
							buildRequestWindow.addListenerToOkButton(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									GameSession.changeGalaxyEcoValue("titanium", -120);
									GameSession.changeGalaxyEcoValue("petroleum", -40);
									GameSession.changeGalaxyEcoValue("uranium", -10);
									GameSession.changeGalaxyEcoValue("gold", -100);
									city.setHqLevel((byte) 2);
									updateCityGraphics();
								}
							});
						}
					} else if (city.getHqLevel() == (byte) 2) {
						buildRequestWindow.setText(LanguageMap.findString("cityUpgradeRequest2"));
						if (GameSession.getCurrentTitanium() < 150 || GameSession.getCurrentPetroleum() < 60
								|| GameSession.getCurrentUranium() < 20 || GameSession.getCurrentGold() < 150
								|| city.hasBuiltInThisTurn()) {
							buildRequestWindow.lockOkButton();
						} else {
							buildRequestWindow.addListenerToOkButton(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									GameSession.changeGalaxyEcoValue("titanium", -150);
									GameSession.changeGalaxyEcoValue("petroleum", -60);
									GameSession.changeGalaxyEcoValue("uranium", -20);
									GameSession.changeGalaxyEcoValue("gold", -150);
									city.setHqLevel((byte) 3);
									updateCityGraphics();
								}
							});
						}
					}
					buildRequestWindow.setVisible(true);
				}

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					adapter.getGalaxyView().setHintsText(LanguageMap.findString("cityUpgradeInfo"));
				}

				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					adapter.getGalaxyView().setHintsText("");
				}
			});
			if (city.isHasWall() == false) {
				wallButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						buildRequestWindow.setText(LanguageMap.findString("wallBuildRequest"));
						if (GameSession.getCurrentTitanium() < 70 || GameSession.getCurrentGold() < 20
								|| city.hasBuiltInThisTurn()) {
							buildRequestWindow.lockOkButton();
						} else {
							buildRequestWindow.addListenerToOkButton(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									GameSession.changeGalaxyEcoValue("titanium", -70);
									GameSession.changeGalaxyEcoValue("gold", -20);
									city.setHasWall(true);
									updateCityGraphics();
								}
							});
						}
						buildRequestWindow.setVisible(true);
					}

					@Override
					public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
						adapter.getGalaxyView().setHintsText(LanguageMap.findString("wallBuildInfo"));
					}

					@Override
					public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
						adapter.getGalaxyView().setHintsText("");
					}
				});

			}
			if (city.isHasIntel() == false) {
				intelButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						buildRequestWindow.setText(LanguageMap.findString("intelBuildRequest"));
						if (GameSession.getCurrentTitanium() < 30 || GameSession.getCurrentUranium() < 40
								|| GameSession.getCurrentGold() < 70 || city.hasBuiltInThisTurn()) {
							buildRequestWindow.lockOkButton();
						} else {
							buildRequestWindow.addListenerToOkButton(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									GameSession.changeGalaxyEcoValue("titanium", -30);
									GameSession.changeGalaxyEcoValue("uranium", -40);
									GameSession.changeGalaxyEcoValue("gold", -70);
									city.setHasIntel(true);
									updateCityGraphics();
								}
							});
						}
						buildRequestWindow.setVisible(true);
					}

					@Override
					public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
						adapter.getGalaxyView().setHintsText(LanguageMap.findString("intelBuildInfo"));
					}

					@Override
					public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
						adapter.getGalaxyView().setHintsText("");
					}
				});
			}
			if (city.isHasLabMech() == false) {
				labTechButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						buildRequestWindow.setText(LanguageMap.findString("labTechBuildRequest"));
						if (GameSession.getCurrentTitanium() < 30 || GameSession.getCurrentUranium() < 50
								|| GameSession.getCurrentPetroleum() < 50 || city.hasBuiltInThisTurn()) {
							buildRequestWindow.lockOkButton();
						} else {
							buildRequestWindow.addListenerToOkButton(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									GameSession.changeGalaxyEcoValue("titanium", -30);
									GameSession.changeGalaxyEcoValue("petroleum", -50);
									GameSession.changeGalaxyEcoValue("uranium", -50);
									city.setHasLabMech(true);
									updateCityGraphics();
								}
							});
						}
						buildRequestWindow.setVisible(true);
					}

					@Override
					public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
						adapter.getGalaxyView().setHintsText(LanguageMap.findString("labTechBuildInfo"));
					}

					@Override
					public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
						adapter.getGalaxyView().setHintsText("");
					}
				});
			}
			if (city.isHasLabWoj() == false) {
				labMilButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						buildRequestWindow.setText(LanguageMap.findString("labWojBuildRequest"));
						if (GameSession.getCurrentTitanium() < 20 || GameSession.getCurrentUranium() < 50
								|| GameSession.getCurrentPetroleum() < 60 || city.hasBuiltInThisTurn()) {
							buildRequestWindow.lockOkButton();
						} else {
							buildRequestWindow.addListenerToOkButton(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									GameSession.changeGalaxyEcoValue("titanium", -20);
									GameSession.changeGalaxyEcoValue("petroleum", -60);
									GameSession.changeGalaxyEcoValue("uranium", -50);
									city.setHasLabWoj(true);
									updateCityGraphics();
								}
							});
						}
						buildRequestWindow.setVisible(true);
					}

					@Override
					public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
						adapter.getGalaxyView().setHintsText(LanguageMap.findString("labWojBuildInfo"));
					}

					@Override
					public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
						adapter.getGalaxyView().setHintsText("");
					}
				});
			}
			if (city.isHasTrainFac() == false) {
				trainFacButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						buildRequestWindow.setText(LanguageMap.findString("trainFacBuildRequest"));
						if (GameSession.getCurrentTitanium() < 50 || GameSession.getCurrentUranium() < 60
								|| GameSession.getCurrentGold() < 20 || city.hasBuiltInThisTurn()) {
							buildRequestWindow.lockOkButton();
						} else {
							buildRequestWindow.addListenerToOkButton(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									GameSession.changeGalaxyEcoValue("titanium", -50);
									GameSession.changeGalaxyEcoValue("uranium", -60);
									GameSession.changeGalaxyEcoValue("gold", -20);
									city.setHasTrainFac(true);
									updateCityGraphics();
								}
							});
						}
						buildRequestWindow.setVisible(true);
					}

					@Override
					public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
						adapter.getGalaxyView().setHintsText(LanguageMap.findString("trainFacBuildInfo"));
					}

					@Override
					public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
						adapter.getGalaxyView().setHintsText("");
					}
				});
			}
			if (city.isHasSpecBuild() == false) {
				specialButton.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if (city.getType() == CityType.MUTANT)
							buildRequestWindow.setText(LanguageMap.findString("modGenBuildRequest"));
						else if (city.getType() == CityType.CYBORG)
							buildRequestWindow.setText(LanguageMap.findString("modCyberBuildRequest"));

						if (GameSession.getCurrentUranium() < 50 || GameSession.getCurrentPetroleum() < 80
								|| city.hasBuiltInThisTurn()) {
							buildRequestWindow.lockOkButton();
						} else {
							buildRequestWindow.addListenerToOkButton(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									GameSession.changeGalaxyEcoValue("uranium", -50);
									GameSession.changeGalaxyEcoValue("petroleum", -80);
									city.setHasSpecBuild(true);
									updateCityGraphics();
								}
							});
						}
						buildRequestWindow.setVisible(true);
					}

					@Override
					public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
						adapter.getGalaxyView().setHintsText(LanguageMap.findString("specialBuildInfo"));
					}

					@Override
					public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
						adapter.getGalaxyView().setHintsText("");
					}
				});
			}
			this.addActor(hqButton);
			this.addActor(wallButton);
			this.addActor(intelButton);
			this.addActor(labTechButton);
			this.addActor(labMilButton);
			this.addActor(trainFacButton);
			this.addActor(specialButton);

		}
	}

	public class BuildRequestWindow extends MenuWindow {

		public BuildRequestWindow() {
			super(LanguageMap.findString("areYouSure"), 660 * GameVariables.getScaleX(),
					340 * GameVariables.getScaleY(), 600 * GameVariables.getScaleX(), 400 * GameVariables.getScaleY());
			this.okButton.setPosition(50, okButton.getY());
			this.okButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SoundEffectManager.playBuildingPlacedSound();
					event.stop();
				}
			});
			this.okButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					event.stop();
				}
			});
			MenuButton cancelButton = new MenuButton(LanguageMap.findString("backToGalaxyView"),
					350 * GameVariables.getScaleX(), okButton.getY());
			cancelButton.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					setVisible(false);
					event.stop();
					return true;
				}
			});
			this.addActor(cancelButton);
		}

		@Override
		public void addListenerToOkButton(ClickListener listener) {
			this.okButton.getListeners().pop();
			this.okButton.addListener(listener);
			unlockOkButton();
		}

		public void lockOkButton() {
			this.okButton.lock();
		}

		public void unlockOkButton() {
			this.okButton.unlock();
		}

		@Override
		public void setVisible(boolean value) {
			super.setVisible(value);
			if (value) {
				this.getLightFade().remove();
				interfaceStage.addActor(this.getLightFade());
				this.remove();
				interfaceStage.addActor(this);
			}
		}
	}

	public BuildRequestWindow getBuildRequestWindow() {
		return buildRequestWindow;
	}

	public void setBuildRequestWindow(BuildRequestWindow buildRequestWindow) {
		this.buildRequestWindow = buildRequestWindow;
	}

	public City getCity() {
		return city;
	}
	public InputProcessor getInputMultiplexer() {
		return this.inpM;
	}
}
