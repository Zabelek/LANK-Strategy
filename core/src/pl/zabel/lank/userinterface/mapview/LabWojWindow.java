package pl.zabel.lank.userinterface.mapview;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.City.CityType;
import pl.zabel.lank.texturecontainers.LabWojTextureContainer;
import pl.zabel.lank.userinterface.MenuWindow;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.views.CityView;
import pl.zabel.lank.views.GalaxyView;

public class LabWojWindow extends MenuWindow {
	private UpgradePart cyborgRightArm, cyborgLeftArm, cyborgBody, mutantRightArm, mutantLeftArm, mutantBody;
	private Image background;
	private CityView cityView;
	private GalaxyView galaxyView;

	public LabWojWindow(CityView cityView, GalaxyView galaxyView) {
		super(LanguageMap.findString("labWojTitle"), 660 * GameVariables.getScaleX(), 140 * GameVariables.getScaleY(),
				600 * GameVariables.getScaleX(), 800 * GameVariables.getScaleY());
		this.cityView = cityView;
		this.galaxyView = galaxyView;
		this.textDisplayed.setY(400 * GameVariables.getScaleY());
		background = new Image(
				new TextureRegionDrawable(LabWojTextureContainer.findRegion("labwoj-window-background")));
		background.setBounds(50 * GameVariables.getScaleX(), 150 * GameVariables.getScaleY(), 500 * GameVariables.getScaleX(),
				570 * GameVariables.getScaleY());
		this.addActor(background);
		cyborgRightArm = new UpgradePart("labwoj-window-cyborg-right-arm", 50, 350, 300, 270);
		cyborgLeftArm = new UpgradePart("labwoj-window-cyborg-left-arm", 350, 350, 300, 270);
		cyborgBody = new UpgradePart("labwoj-window-cyborg-body", 200, 200, 180, 420);
		mutantRightArm = new UpgradePart("labwoj-window-mutant-right-arm", 50, 350, 300, 270);
		mutantLeftArm = new UpgradePart("labwoj-window-mutant-left-arm", 350, 350, 300, 270);
		mutantBody = new UpgradePart("labwoj-window-mutant-body", 200, 200, 180, 420);

		cyborgRightArm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabWojWindow.this.cityView.getCity().isCyborgRightArm()) {
					LabWojWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("jdBuildArmResRequest"));
					if (GameSession.getCurrentUranium() < 100 || GameSession.getCurrentGold() < 50
							|| LabWojWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabWojWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabWojWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("uranium", -100);
								GameSession.changeGalaxyEcoValue("gold", -50);
								LabWojWindow.this.cityView.getCity().setCyborgRightArm(true);
								LabWojWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabWojWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabWojWindow.this.galaxyView.setHintsText(LanguageMap.findString("jdBuildArmResCyborgInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabWojWindow.this.galaxyView.setHintsText("");
			}
		});

		cyborgLeftArm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabWojWindow.this.cityView.getCity().isCyborgLeftArm()) {
					LabWojWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("jdShootArmResRequest"));
					if (GameSession.getCurrentPetroleum() < 60 || GameSession.getCurrentUranium() < 80
							|| LabWojWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabWojWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabWojWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("uranium", -80);
								GameSession.changeGalaxyEcoValue("petroleum", -60);
								LabWojWindow.this.cityView.getCity().setCyborgLeftArm(true);
								LabWojWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabWojWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabWojWindow.this.galaxyView.setHintsText(LanguageMap.findString("jdShootArmResCyborgInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabWojWindow.this.galaxyView.setHintsText("");
			}
		});

		cyborgBody.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabWojWindow.this.cityView.getCity().isCyborgBody()) {
					LabWojWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("jdBodyResRequest"));
					if (GameSession.getCurrentUranium() < 50 || GameSession.getCurrentTitanium() < 50
							|| GameSession.getCurrentPetroleum() < 50 || LabWojWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabWojWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabWojWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("titanium", -50);
								GameSession.changeGalaxyEcoValue("uranium", -50);
								GameSession.changeGalaxyEcoValue("petroleum", -50);
								LabWojWindow.this.cityView.getCity().setCyborgBody(true);
								LabWojWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabWojWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabWojWindow.this.galaxyView.setHintsText(LanguageMap.findString("jdBodyResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabWojWindow.this.galaxyView.setHintsText("");
			}
		});

		mutantRightArm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabWojWindow.this.cityView.getCity().isMutantRightArm()) {
					LabWojWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("jdBuildArmResRequest"));
					if (GameSession.getCurrentUranium() < 100 || GameSession.getCurrentGold() < 50
							|| LabWojWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabWojWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabWojWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("uranium", -100);
								GameSession.changeGalaxyEcoValue("gold", -50);
								LabWojWindow.this.cityView.getCity().setMutantRightArm(true);
								LabWojWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabWojWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabWojWindow.this.galaxyView.setHintsText(LanguageMap.findString("jdBuildArmResMutantInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabWojWindow.this.galaxyView.setHintsText("");
			}
		});

		mutantLeftArm.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabWojWindow.this.cityView.getCity().isMutantLeftArm()) {
					LabWojWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("jdShootArmResRequest"));
					if (GameSession.getCurrentPetroleum() < 60 || GameSession.getCurrentUranium() < 80
							|| LabWojWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabWojWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabWojWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("uranium", -80);
								GameSession.changeGalaxyEcoValue("petroleum", -60);
								LabWojWindow.this.cityView.getCity().setMutantLeftArm(true);
								LabWojWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabWojWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabWojWindow.this.galaxyView.setHintsText(LanguageMap.findString("jdShootArmResMutantInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabWojWindow.this.galaxyView.setHintsText("");
			}
		});

		mutantBody.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabWojWindow.this.cityView.getCity().isMutantBody()) {
					LabWojWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("jdBodyResRequest"));
					if (GameSession.getCurrentUranium() < 50 || GameSession.getCurrentTitanium() < 50
							|| GameSession.getCurrentPetroleum() < 50 || LabWojWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabWojWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabWojWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("titanium", -50);
								GameSession.changeGalaxyEcoValue("uranium", -50);
								GameSession.changeGalaxyEcoValue("petroleum", -50);
								LabWojWindow.this.cityView.getCity().setMutantBody(true);
								LabWojWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabWojWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabWojWindow.this.galaxyView.setHintsText(LanguageMap.findString("jdBodyResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabWojWindow.this.galaxyView.setHintsText("");
			}
		});
		this.addActor(cyborgRightArm);
		this.addActor(cyborgLeftArm);
		this.addActor(cyborgBody);
		this.addActor(mutantRightArm);
		this.addActor(mutantLeftArm);
		this.addActor(mutantBody);

	}

	public void updateUpgradeParts() {
		if (cityView.getCity().getType() == CityType.MUTANT) {
			this.cyborgBody.setVisible(false);
			this.cyborgLeftArm.setVisible(false);
			this.cyborgRightArm.setVisible(false);
			this.mutantBody.setVisible(true);
			this.mutantLeftArm.setVisible(true);
			this.mutantRightArm.setVisible(true);
			this.mutantBody.setUpgraded(cityView.getCity().isMutantBody());
			this.mutantLeftArm.setUpgraded(cityView.getCity().isMutantLeftArm());
			this.mutantRightArm.setUpgraded(cityView.getCity().isMutantRightArm());
		} else {
			this.cyborgBody.setVisible(true);
			this.cyborgLeftArm.setVisible(true);
			this.cyborgRightArm.setVisible(true);
			this.mutantBody.setVisible(false);
			this.mutantLeftArm.setVisible(false);
			this.mutantRightArm.setVisible(false);
			this.cyborgBody.setUpgraded(cityView.getCity().isCyborgBody());
			this.cyborgLeftArm.setUpgraded(cityView.getCity().isCyborgLeftArm());
			this.cyborgRightArm.setUpgraded(cityView.getCity().isCyborgRightArm());
		}
	}
	public class UpgradePart extends Actor {
		private TextureRegion texture;
		private boolean upgraded;

		public UpgradePart(String name, float x, float y, float w, float h) {
			this.texture = LabWojTextureContainer.findRegion(name);
			this.setBounds(x * GameVariables.getScaleX(), y * GameVariables.getScaleY(), w * GameVariables.getScaleX(),
					h * GameVariables.getScaleY());
			upgraded = false;
		}

		public void setUpgraded(boolean upgraded) {
			this.upgraded = upgraded;
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			if (!upgraded)
				batch.setColor(1, 1, 1, 0.5f);
			batch.draw(texture, background.getX(), background.getY(), background.getWidth(),
					background.getHeight());
			batch.setColor(1, 1, 1, 1);

		}
	}
}