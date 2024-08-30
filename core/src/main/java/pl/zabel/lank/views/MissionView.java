package pl.zabel.lank.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
import pl.zabel.lank.gameplayobjects.missionview.Mission;
import pl.zabel.lank.texturecontainers.CharacterTextureContainer;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.MissionTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.userinterface.MenuButton;
import pl.zabel.lank.userinterface.MenuWindow;
import pl.zabel.lank.userinterface.RegionIcon;
import pl.zabel.lank.userinterface.StarSystemView;
import pl.zabel.lank.utilities.FadeableActor;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.MusicManager;
import pl.zabel.lank.utilities.SoundEffectManager;

public class MissionView implements Screen {

	private LaserowaAnihilacjaNajdzdzcowzKosmosu adapter;
	private Mission mission;
	private Stage mainStage;

	private Image background, nextButton, previousButton, startButton, backButton, characterImage;
	private int currentDialogueSequence;
	private FadeableActor black;
	private Label textLabel;
	private byte sequence;

	private CharacterChoiceWindow choiceWindow;

	public MissionView(LaserowaAnihilacjaNajdzdzcowzKosmosu adapter) {
		this.adapter = adapter;
		this.mainStage = new Stage(new ScreenViewport());
		this.background = new Image();
		this.background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.nextButton = new Image();
		this.nextButton.setBounds(1770 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY(), 100 * GameVariables.getScaleX(),
				100 * GameVariables.getScaleY());
		this.nextButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundEffectManager.playSmallClickSound();
				nextDialogue();
				event.stop();
			}

			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				nextButton.setPosition(nextButton.getX() + 2f, nextButton.getY() + 2f);
				event.stop();
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				nextButton.setPosition(nextButton.getX() - 2f, nextButton.getY() - 2f);
				event.stop();
			}
		});
		this.previousButton = new Image();
		this.previousButton.setBounds(50 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY(), 100 * GameVariables.getScaleX(),
				100 * GameVariables.getScaleY());
		this.previousButton.setOrigin(50 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY());
		this.previousButton.rotateBy(180);
		this.previousButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundEffectManager.playSmallClickSound();
				previousDialogue();
				event.stop();
			}

			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				previousButton.setPosition(previousButton.getX() + 2f, previousButton.getY() + 2f);
				event.stop();
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				previousButton.setPosition(previousButton.getX() - 2f, previousButton.getY() - 2f);
				event.stop();
			}
		});
		this.startButton = new Image();
		this.startButton.setBounds(1700 * GameVariables.getScaleX(), 350 * GameVariables.getScaleY(), 200 * GameVariables.getScaleX(),
				100 * GameVariables.getScaleY());
		this.startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundEffectManager.playSmallClickSound();
				startMission();
				event.stop();
			}

			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				startButton.setPosition(startButton.getX() + 2f, startButton.getY() + 2f);
				event.stop();
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				startButton.setPosition(startButton.getX() - 2f, startButton.getY() - 2f);
				event.stop();
			}
		});
		this.backButton = new Image();
		this.backButton.setBounds(50 * GameVariables.getScaleX(), 350 * GameVariables.getScaleY(), 200 * GameVariables.getScaleX(),
				100 * GameVariables.getScaleY());
		this.backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundEffectManager.playSmallClickSound();
				MusicManager.playGalaxyViewMusic();
				backToGalaxyRegionView();
				if (sequence == (byte) 1 || sequence == (byte) 2) {
					MissionView.this.adapter.getGalaxyRegionView().startGoToGalaxyViewProcedure();
					MissionView.this.adapter.getGalaxyView().showSiegeInfo();
					Timer.schedule(new Timer.Task() {

						@Override
						public void run() {
							Gdx.input.setInputProcessor(MissionView.this.adapter.getGalaxyView().getInputMultiplexer());

						}
					}, 2.0f);
				}
				event.stop();
			}

			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				backButton.setPosition(backButton.getX() + 2f, backButton.getY() + 2f);
				event.stop();
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				backButton.setPosition(backButton.getX() - 2f, backButton.getY() - 2f);
				event.stop();
			}

		});

		this.textLabel = new Label("tekst misji", TextStylesContainer.smallTextStyle);
		this.textLabel.setBounds(300 * GameVariables.getScaleX(), 50 * GameVariables.getScaleY(), 1320 * GameVariables.getScaleX(),
				200 * GameVariables.getScaleY());
		this.textLabel.setAlignment(Align.center);
		this.characterImage = new Image();
		this.characterImage.setBounds(300 * GameVariables.getScaleX(), 250 * GameVariables.getScaleY(),
				1320 * GameVariables.getScaleX(), 800 * GameVariables.getScaleY());
		black = new FadeableActor(InterfaceTextureContainer.findRegion("blackFade"), 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		this.mainStage.addActor(characterImage);
		this.mainStage.addActor(background);
		this.mainStage.addActor(textLabel);
		this.mainStage.addActor(nextButton);
		this.mainStage.addActor(previousButton);
		this.mainStage.addActor(startButton);
		this.mainStage.addActor(backButton);
	}

	public void AssignMission(Mission mission, byte sequence) {
		MissionTextureContainer.load();
		this.mission = mission;
		this.sequence = sequence;
		if (GameSession.getIsPlayerCyborg() == null) {
			this.choiceWindow = new CharacterChoiceWindow();
			this.choiceWindow.setVisible(true);
			this.mainStage.addActor(choiceWindow);
			Timer.schedule(new Timer.Task() {

				@Override
				public void run() {
					MissionView.this.fadeIn(2);
					this.cancel();
				}
			}, 1f, 1, 1);
		} else {
			this.currentDialogueSequence = 0;

			if (GameSession.getIsPlayerCyborg() == true) {
				this.background
						.setDrawable(new TextureRegionDrawable(MissionTextureContainer.findRegion("cyborg-panel")));
				this.nextButton.setDrawable(
						new TextureRegionDrawable(MissionTextureContainer.findRegion("next-button-cyborg")));
				this.previousButton.setDrawable(
						new TextureRegionDrawable(MissionTextureContainer.findRegion("next-button-cyborg")));
				this.startButton.setDrawable(
						new TextureRegionDrawable(MissionTextureContainer.findRegion("start-button-cyborg")));
				this.backButton.setDrawable(
						new TextureRegionDrawable(MissionTextureContainer.findRegion("back-button-cyborg")));
			} else {
				this.background
						.setDrawable(new TextureRegionDrawable(MissionTextureContainer.findRegion("mutant-panel")));
				this.nextButton.setDrawable(
						new TextureRegionDrawable(MissionTextureContainer.findRegion("next-button-mutant")));
				this.previousButton.setDrawable(
						new TextureRegionDrawable(MissionTextureContainer.findRegion("next-button-mutant")));
				this.startButton.setDrawable(
						new TextureRegionDrawable(MissionTextureContainer.findRegion("start-button-mutant")));
				this.backButton.setDrawable(
						new TextureRegionDrawable(MissionTextureContainer.findRegion("back-button-mutant")));
			}
			if (sequence == 0x00) {
				this.startButton.setVisible(true);
				this.textLabel.setText(mission.getDialogueSequences().get(currentDialogueSequence).getText());
				this.characterImage.setDrawable(new TextureRegionDrawable(CharacterTextureContainer.findRegion(
						mission.getDialogueSequences().get(currentDialogueSequence).getCharacter() + "-mission")));
			} else if (sequence == 0x01) {
				this.startButton.setVisible(false);
				this.textLabel.setText(mission.getWinDialogueSequences().get(currentDialogueSequence).getText());
				this.characterImage.setDrawable(new TextureRegionDrawable(CharacterTextureContainer.findRegion(
						mission.getWinDialogueSequences().get(currentDialogueSequence).getCharacter() + "-mission")));
			} else {
				this.startButton.setVisible(false);
				this.textLabel.setText(mission.getLoseDialogueSequences().get(currentDialogueSequence).getText());
				this.characterImage.setDrawable(new TextureRegionDrawable(CharacterTextureContainer.findRegion(
						mission.getLoseDialogueSequences().get(currentDialogueSequence).getCharacter() + "-mission")));
			}
			Timer.schedule(new Timer.Task() {

				@Override
				public void run() {
					MissionView.this.fadeIn(1);
					this.cancel();
				}
			}, 2f, 1, 1);
		}

	}

	public void nextDialogue() {
		if (sequence == 0x00) {
			if (currentDialogueSequence < mission.getDialogueSequences().size() - 1) {
				currentDialogueSequence++;
				this.textLabel.setText(mission.getDialogueSequences().get(currentDialogueSequence).getText());
				this.characterImage.setDrawable(new TextureRegionDrawable(CharacterTextureContainer.findRegion(
						mission.getDialogueSequences().get(currentDialogueSequence).getCharacter() + "-mission")));
			}
		} else if (sequence == 0x01) {
			if (currentDialogueSequence < mission.getWinDialogueSequences().size() - 1) {
				currentDialogueSequence++;
				this.textLabel.setText(mission.getWinDialogueSequences().get(currentDialogueSequence).getText());
				this.characterImage.setDrawable(new TextureRegionDrawable(CharacterTextureContainer.findRegion(
						mission.getWinDialogueSequences().get(currentDialogueSequence).getCharacter() + "-mission")));
			}
		} else {
			if (currentDialogueSequence < mission.getLoseDialogueSequences().size() - 1) {
				currentDialogueSequence++;
				this.textLabel.setText(mission.getLoseDialogueSequences().get(currentDialogueSequence).getText());
				this.characterImage.setDrawable(new TextureRegionDrawable(CharacterTextureContainer.findRegion(
						mission.getLoseDialogueSequences().get(currentDialogueSequence).getCharacter() + "-mission")));
			}
		}
	}

	public void previousDialogue() {
		if (currentDialogueSequence > 0) {
			currentDialogueSequence--;
			if (sequence == 0x00) {
				this.textLabel.setText(mission.getDialogueSequences().get(currentDialogueSequence).getText());
				this.characterImage.setDrawable(new TextureRegionDrawable(CharacterTextureContainer.findRegion(
						mission.getDialogueSequences().get(currentDialogueSequence).getCharacter() + "-mission")));
			} else if (sequence == 0x01) {
				this.textLabel.setText(mission.getWinDialogueSequences().get(currentDialogueSequence).getText());
				this.characterImage.setDrawable(new TextureRegionDrawable(CharacterTextureContainer.findRegion(
						mission.getWinDialogueSequences().get(currentDialogueSequence).getCharacter() + "-mission")));
			} else {
				this.textLabel.setText(mission.getLoseDialogueSequences().get(currentDialogueSequence).getText());
				this.characterImage.setDrawable(new TextureRegionDrawable(CharacterTextureContainer.findRegion(
						mission.getLoseDialogueSequences().get(currentDialogueSequence).getCharacter() + "-mission")));
			}
		}
	}

	public void startMission() {
		this.adapter.goToMapView(mission);
	}

	public void backToGalaxyRegionView() {

		this.fadeOut(0.5f);
		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				MissionView.this.adapter.getGalaxyRegionView().addSharedComponents();
				MissionView.this.adapter.getGalaxyRegionView().fadeIn(1f);
				MissionView.this.adapter.getGalaxyView().setInterfaceStartPosition();
				MissionView.this.adapter.getGalaxyView().animateInterfaceOpening();
				MissionView.this.adapter.setScreen(MissionView.this.adapter.getGalaxyRegionView());
			}
		}, 0.5f, 0, 0);
		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				MissionView.this.adapter.getGalaxyView().mainGame.getBlack().remove();
				Gdx.input.setInputProcessor(MissionView.this.adapter.getGalaxyRegionView().inpM);
				MissionTextureContainer.dispose();
			}
		}, 1.5f, 0, 0);
	}

	public void showCharacterChoiceWindow() {

	}

	public class CharacterChoiceWindow extends WidgetGroup {
		public MenuWindow infoWindow;

		public CharacterChoiceWindow() {
			super();
			this.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Image background = new Image(
					new TextureRegionDrawable(MissionTextureContainer.findRegion("destiny-choice-background")));
			background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			final MenuButton readyButton = new MenuButton("GOTOWE", 870 * GameVariables.getScaleX(),
					50 * GameVariables.getScaleY());

			readyButton.lock();
			this.infoWindow = new MenuWindow("", 650 * GameVariables.getScaleX(), 120 * GameVariables.getScaleY(),
					620 * GameVariables.getScaleX(), 750 * GameVariables.getScaleY());
			this.infoWindow.hideOkButton();
			this.infoWindow.setTextDispayedY(60);
			Image zackChoice = new Image(
					new TextureRegionDrawable(MissionTextureContainer.findRegion("destiny-choice-zack")));
			Image kaiteChoice = new Image(
					new TextureRegionDrawable(MissionTextureContainer.findRegion("destiny-choice-kaite")));
			final Image zackChoiceSelect = new Image(
					new TextureRegionDrawable(MissionTextureContainer.findRegion("destiny-choice-zack-selected")));
			final Image kaiteChoiceSelect = new Image(
					new TextureRegionDrawable(MissionTextureContainer.findRegion("destiny-choice-kaite-selected")));
			zackChoiceSelect.setVisible(false);
			kaiteChoiceSelect.setVisible(false);
			zackChoice.setBounds(0, 0, 600 * GameVariables.getScaleX(), 800 * GameVariables.getScaleY());
			zackChoice.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					zackChoiceSelect.setVisible(true);
					kaiteChoiceSelect.setVisible(false);
					infoWindow.setText(LanguageMap.findString("zackInfoText"));
					readyButton.unlock();
					event.stop();
				}
			});
			zackChoiceSelect.setBounds(0, 0, 600 * GameVariables.getScaleX(), 800 * GameVariables.getScaleY());
			kaiteChoice.setBounds(1320 * GameVariables.getScaleX(), 0, 600 * GameVariables.getScaleX(),
					800 * GameVariables.getScaleY());
			kaiteChoice.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					zackChoiceSelect.setVisible(false);
					kaiteChoiceSelect.setVisible(true);
					infoWindow.setText(LanguageMap.findString("kaiteInfoText"));
					readyButton.unlock();
					event.stop();
				}
			});

			kaiteChoiceSelect.setBounds(1320 * GameVariables.getScaleX(), 0, 600 * GameVariables.getScaleX(),
					800 * GameVariables.getScaleY());

			Label zackLabel = new Label("Zack Acinron", TextStylesContainer.genericButtonStyle);
			Label kaiteLabel = new Label("Kaite Akswokrat", TextStylesContainer.genericButtonStyle);
			zackLabel.setBounds(100 * GameVariables.getScaleX(), 70 * GameVariables.getScaleY(), 300 * GameVariables.getScaleX(),
					70 * GameVariables.getScaleY());
			kaiteLabel.setBounds(1500 * GameVariables.getScaleX(), 70 * GameVariables.getScaleY(), 300 * GameVariables.getScaleX(),
					70 * GameVariables.getScaleY());
			zackLabel.setTouchable(Touchable.disabled);
			kaiteLabel.setTouchable(Touchable.disabled);

			readyButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (zackChoiceSelect.isVisible())
						GameSession.setIsPlayerCyborg(true);
					else
						GameSession.setIsPlayerCyborg(false);
					MissionView.this.AssignMission(MissionView.this.mission, MissionView.this.sequence);
					MissionView.this.fadeOut(2);
					Timer.schedule(new Timer.Task() {

						@Override
						public void run() {
							CharacterChoiceWindow.this.setVisible(false);
							MissionView.this.fadeIn(1);
							this.cancel();
						}
					}, 2f, 1, 0);
					event.stop();
				}
			});

			this.addActor(background);
			this.addActor(infoWindow);
			this.addActor(kaiteChoiceSelect);
			this.addActor(zackChoiceSelect);
			this.addActor(kaiteChoice);
			this.addActor(zackChoice);
			this.addActor(readyButton);
			this.addActor(zackLabel);
			this.addActor(kaiteLabel);
		}
	}

	public void fadeIn(float time) {
		black.remove();
		mainStage.addActor(black);
		black.fadeOut(time);
		Timer.schedule(new Timer.Task() {

			@Override
			public void run() {
				black.remove();
				this.cancel();

			}
		}, time, 1, 1);
	}

	public void fadeOut(float time) {
		black.remove();
		mainStage.addActor(black);
		black.fadeIn(time);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		mainStage.act(Gdx.graphics.getDeltaTime());
		mainStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public Stage getMainStage() {
		return mainStage;
	}

	public void checkIfLast() {
		if (sequence == 0x01 && this.mission.getMissionName().toLowerCase().equals("f-1-nal")) {
			MusicManager.playOutroMusic();
		}
	}
}
