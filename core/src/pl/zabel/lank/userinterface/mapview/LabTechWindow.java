package pl.zabel.lank.userinterface.mapview;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.City;
import pl.zabel.lank.gameplayobjects.City.CityType;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.userinterface.MenuWindow;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.views.CityView;
import pl.zabel.lank.views.GalaxyView;

public class LabTechWindow extends MenuWindow {
	private UpgradeButton speedUpgButton, repairUpgButton, shootSpeedUpgButton, damageUpgButton, regenUpgButton,
			hpUpgButton, shellSpeedUpgButton, rangeUpgButton;
	private CityView cityView;
	private GalaxyView galaxyView;

	public LabTechWindow(CityView cityView, GalaxyView galaxyView) {
		super(LanguageMap.findString("labTexgTitle"), 535 * GameVariables.getScaleX(), 315 * GameVariables.getScaleY(),
				850 * GameVariables.getScaleX(), 500 * GameVariables.getScaleY());
		this.cityView = cityView;
		this.galaxyView = galaxyView;
		this.textDisplayed.setY(200 * GameVariables.getScaleY());
		speedUpgButton = new UpgradeButton("speed-upg", 50, 200);
		repairUpgButton = new UpgradeButton("repair-upg", 250, 200);
		shootSpeedUpgButton = new UpgradeButton("shoot-speed-upg", 450, 200);
		damageUpgButton = new UpgradeButton("damage-upg", 650, 200);
		hpUpgButton = new UpgradeButton("hp-upg", 250, 200);
		shellSpeedUpgButton = new UpgradeButton("shell-speed-upg", 450, 200);
		rangeUpgButton = new UpgradeButton("range-upg", 650, 200);
		regenUpgButton = new UpgradeButton("regen-upg", 50, 200);

		speedUpgButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabTechWindow.this.cityView.getCity().isSpeedUpg()) {
					LabTechWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("movSpeedResRequest"));
					if (GameSession.getCurrentPetroleum() < 70 || GameSession.getCurrentGold() < 30
							|| LabTechWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabTechWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabTechWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("petroleum", -70);
								GameSession.changeGalaxyEcoValue("gold", -30);
								LabTechWindow.this.cityView.getCity().setSpeedUpg(true);
								LabTechWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabTechWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabTechWindow.this.galaxyView.setHintsText(LanguageMap.findString("movSpeedResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabTechWindow.this.galaxyView.setHintsText("");
			}
		});

		repairUpgButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabTechWindow.this.cityView.getCity().isRepairUpg()) {
					LabTechWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("repSpeedResRequest"));
					if (GameSession.getCurrentPetroleum() < 60 || GameSession.getCurrentUranium() < 50
							|| LabTechWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabTechWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabTechWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("petroleum", -60);
								GameSession.changeGalaxyEcoValue("uranium", -50);
								LabTechWindow.this.cityView.getCity().setRepairUpg(true);
								LabTechWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabTechWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabTechWindow.this.galaxyView.setHintsText(LanguageMap.findString("repSpeedResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabTechWindow.this.galaxyView.setHintsText("");
			}
		});

		shootSpeedUpgButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabTechWindow.this.cityView.getCity().isShootSpeedUpg()) {
					LabTechWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("attackSpeedResRequest"));
					if (GameSession.getCurrentPetroleum() < 30 || GameSession.getCurrentGold() < 80
							|| LabTechWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabTechWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabTechWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("petroleum", -30);
								GameSession.changeGalaxyEcoValue("gold", -80);
								LabTechWindow.this.cityView.getCity().setShootSpeedUpg(true);
								LabTechWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabTechWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabTechWindow.this.galaxyView.setHintsText(LanguageMap.findString("attackSpeedResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabTechWindow.this.galaxyView.setHintsText("");
			}
		});

		damageUpgButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabTechWindow.this.cityView.getCity().isDamageUpg()) {
					LabTechWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("tankDamageResRequest"));
					if (GameSession.getCurrentTitanium() < 20 || GameSession.getCurrentUranium() < 40
							|| GameSession.getCurrentGold() < 70 || LabTechWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabTechWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabTechWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("titanium", -20);
								GameSession.changeGalaxyEcoValue("uranium", -40);
								GameSession.changeGalaxyEcoValue("gold", -70);
								LabTechWindow.this.cityView.getCity().setDamageUpg(true);
								LabTechWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabTechWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabTechWindow.this.galaxyView.setHintsText(LanguageMap.findString("tankDamageResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabTechWindow.this.galaxyView.setHintsText("");
			}
		});

		hpUpgButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabTechWindow.this.cityView.getCity().isHpUpg()) {
					LabTechWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("armorResRequest"));
					if (GameSession.getCurrentTitanium() < 30 || GameSession.getCurrentGold() < 80
							|| LabTechWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabTechWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabTechWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("titanium", -30);
								GameSession.changeGalaxyEcoValue("gold", -80);
								LabTechWindow.this.cityView.getCity().setHpUpg(true);
								LabTechWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabTechWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabTechWindow.this.galaxyView.setHintsText(LanguageMap.findString("armorResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabTechWindow.this.galaxyView.setHintsText("");
			}
		});

		shellSpeedUpgButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabTechWindow.this.cityView.getCity().isShellSpeedUpg()) {
					LabTechWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("bulletSpeedResRequest"));
					if (GameSession.getCurrentPetroleum() < 70 || LabTechWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabTechWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabTechWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("petroleum", -70);
								LabTechWindow.this.cityView.getCity().setShellSpeedUpg(true);
								LabTechWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabTechWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabTechWindow.this.galaxyView.setHintsText(LanguageMap.findString("bulletSpeedResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabTechWindow.this.galaxyView.setHintsText("");
			}
		});

		rangeUpgButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabTechWindow.this.cityView.getCity().isRangeUpg()) {
					LabTechWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("tankRangeResRequest"));
					if (GameSession.getCurrentUranium() < 80 || LabTechWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabTechWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabTechWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("uranium", -80);
								LabTechWindow.this.cityView.getCity().setRangeUpg(true);
								LabTechWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabTechWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabTechWindow.this.galaxyView.setHintsText(LanguageMap.findString("tankRangeResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabTechWindow.this.galaxyView.setHintsText("");
			}
		});

		regenUpgButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!LabTechWindow.this.cityView.getCity().isRegenUpg()) {
					LabTechWindow.this.cityView.getBuildRequestWindow().setText(LanguageMap.findString("armorRegenResRequest"));
					if (GameSession.getCurrentUranium() < 120 || LabTechWindow.this.cityView.getCity().hasResearchedInThisTurn()) {
						LabTechWindow.this.cityView.getBuildRequestWindow().lockOkButton();
					} else {
						LabTechWindow.this.cityView.getBuildRequestWindow().addListenerToOkButton(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								GameSession.changeGalaxyEcoValue("uranium", -120);
								LabTechWindow.this.cityView.getCity().setRegenUpg(true);
								LabTechWindow.this.cityView.updateCityGraphics();
							}
						});
					}
					LabTechWindow.this.cityView.getBuildRequestWindow().setVisible(true);
				}
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				LabTechWindow.this.galaxyView.setHintsText(LanguageMap.findString("armorRegenResInfo"));
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				LabTechWindow.this.galaxyView.setHintsText("");
			}
		});
		this.addActor(speedUpgButton);
		this.addActor(repairUpgButton);
		this.addActor(shootSpeedUpgButton);
		this.addActor(damageUpgButton);
		this.addActor(hpUpgButton);
		this.addActor(shellSpeedUpgButton);
		this.addActor(rangeUpgButton);
		this.addActor(regenUpgButton);
	}

	public void updateButtons() {
		if (cityView.getCity().getType() == CityType.MUTANT) {
			this.speedUpgButton.setVisible(false);
			this.repairUpgButton.setVisible(false);
			this.shootSpeedUpgButton.setVisible(false);
			this.damageUpgButton.setVisible(false);
			this.regenUpgButton.setVisible(true);
			this.hpUpgButton.setVisible(true);
			this.shellSpeedUpgButton.setVisible(true);
			this.rangeUpgButton.setVisible(true);
			this.regenUpgButton.setUpgraded(cityView.getCity().isRegenUpg());
			this.hpUpgButton.setUpgraded(cityView.getCity().isHpUpg());
			this.shellSpeedUpgButton.setUpgraded(cityView.getCity().isShellSpeedUpg());
			this.rangeUpgButton.setUpgraded(cityView.getCity().isRangeUpg());
		} else {
			this.speedUpgButton.setVisible(true);
			this.repairUpgButton.setVisible(true);
			this.shootSpeedUpgButton.setVisible(true);
			this.damageUpgButton.setVisible(true);
			this.regenUpgButton.setVisible(false);
			this.hpUpgButton.setVisible(false);
			this.shellSpeedUpgButton.setVisible(false);
			this.rangeUpgButton.setVisible(false);
			this.speedUpgButton.setUpgraded(cityView.getCity().isSpeedUpg());
			this.repairUpgButton.setUpgraded(cityView.getCity().isRepairUpg());
			this.shootSpeedUpgButton.setUpgraded(cityView.getCity().isShootSpeedUpg());
			this.damageUpgButton.setUpgraded(cityView.getCity().isDamageUpg());
		}
	}

	public class UpgradeButton extends Actor {
		private TextureRegion texture;
		private boolean upgraded;

		public UpgradeButton(String name, float x, float y) {
			this.texture = InterfaceTextureContainer.findRegion(name);
			this.setBounds(x * GameVariables.getScaleX(), y * GameVariables.getScaleY(), 150 * GameVariables.getScaleX(),
					150 * GameVariables.getScaleY());
			this.upgraded = false;
		}

		public void setUpgraded(boolean upgraded) {
			this.upgraded = upgraded;
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			if (!upgraded)
				batch.setColor(1, 1, 1, 0.5f);
			batch.draw(texture, UpgradeButton.this.getX(), UpgradeButton.this.getY(), UpgradeButton.this.getWidth(),
					UpgradeButton.this.getHeight());
			batch.setColor(1, 1, 1, 1);

		}
	}
}