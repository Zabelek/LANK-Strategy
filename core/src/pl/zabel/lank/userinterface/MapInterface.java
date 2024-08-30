package pl.zabel.lank.userinterface;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import pl.zabel.lank.GameSession;
import pl.zabel.lank.GameVariables;
import pl.zabel.lank.MapSession;
import pl.zabel.lank.gameplayobjects.mapview.MapUnit;
import pl.zabel.lank.gameplayobjects.mapview.MobileUnit;
import pl.zabel.lank.gameplayobjects.mapview.MovingUnit;
import pl.zabel.lank.gameplayobjects.mapview.UnitBuilder;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.MapUnitTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.maputilities.ConstructionScheme;
import pl.zabel.lank.views.MapView;

public class MapInterface extends WidgetGroup {

	private MenuWindow leaveRequest;
	private MapView mapView;
	private UnitParametersInfoWindow unitInfoWindow;
	private MenuButton showUnitParametersButton;
	private Label unitHpLabel, unitNameLabel, currentMetalIncrease, currentEnergyIncrease;
	private static Label buildDetails;
	private Image unitImage, currentMetalBar, currentEnergyBar, metalBar, energyBar;
	private MenuImageButton stopProductionButton, settingsButton;
	private static ArrayList<Image> unitSchemes;
	private SettingsWindow settingsWindow;

	public MapInterface(MapView mapView) {
		this.mapView = mapView;
		this.leaveRequest = new MenuWindow(LanguageMap.findString("secondLeaveRequest"), 600*GameVariables.getScaleX(), 400*GameVariables.getScaleY(), 720*GameVariables.getScaleX(), 280*GameVariables.getScaleY());
		this.leaveRequest.okButton.setPosition(50*GameVariables.getScaleX(), this.leaveRequest.okButton.getY());
		MenuButton cancel = new MenuButton(LanguageMap.findString("backToGalaxyView"), 500*GameVariables.getScaleX(), 20*GameVariables.getScaleY());
		cancel.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				leaveRequest.setVisible(false);
				event.cancel();
			}
		});
		this.leaveRequest.addListenerToOkButton(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MapInterface.this.mapView.getPlayersUnit().destroy();
				event.cancel();
			}
		});
		this.leaveRequest.addActor(cancel);
		leaveRequest.setVisible(false);
		this.addActor(leaveRequest);
		// set up screen borders
		ScreenBorder top = new ScreenBorder();
		top.setBounds(20*GameVariables.getScaleX(), 1060*GameVariables.getScaleY(), 1880*GameVariables.getScaleX(), 20*GameVariables.getScaleY());
		top.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.startCameraMoving(0, 10);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.stopCameraMoving();
			}
		});
		ScreenBorder bot = new ScreenBorder();
		bot.setBounds(20*GameVariables.getScaleX(), 0, 1880*GameVariables.getScaleX(), 20*GameVariables.getScaleY());
		bot.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.startCameraMoving(0, -10);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.stopCameraMoving();
			}
		});
		ScreenBorder left = new ScreenBorder();
		left.setBounds(0, 20*GameVariables.getScaleY(), 20*GameVariables.getScaleX(), 1040*GameVariables.getScaleY());
		left.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.startCameraMoving(-10, 0);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.stopCameraMoving();
			}
		});
		ScreenBorder right = new ScreenBorder();
		right.setBounds(1900*GameVariables.getScaleX(), 20*GameVariables.getScaleY(), 20*GameVariables.getScaleX(), 1040*GameVariables.getScaleY());
		right.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.startCameraMoving(10, 0);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.stopCameraMoving();
			}
		});
		ScreenBorder topright = new ScreenBorder();
		topright.setBounds(1900*GameVariables.getScaleX(), 1060*GameVariables.getScaleY(), 20*GameVariables.getScaleX(), 20*GameVariables.getScaleY());
		topright.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.startCameraMoving(10, 10);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.stopCameraMoving();
			}
		});
		ScreenBorder botright = new ScreenBorder();
		botright.setBounds(1900*GameVariables.getScaleX(), 0, 20*GameVariables.getScaleX(), 20*GameVariables.getScaleY());
		botright.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.startCameraMoving(10, -10);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.stopCameraMoving();
			}
		});
		ScreenBorder botleft = new ScreenBorder();
		botleft.setBounds(0, 0, 20*GameVariables.getScaleX(), 20*GameVariables.getScaleY());
		botleft.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.startCameraMoving(-10, -10);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.stopCameraMoving();
			}
		});
		ScreenBorder topleft = new ScreenBorder();
		topleft.setBounds(0, 1060*GameVariables.getScaleY(), 20*GameVariables.getScaleX(), 20*GameVariables.getScaleY());
		topleft.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.startCameraMoving(-10, 10);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				MapInterface.this.mapView.stopCameraMoving();
			}
		});
		this.addActor(top);
		this.addActor(bot);
		this.addActor(left);
		this.addActor(right);
		this.addActor(topright);
		this.addActor(botright);
		this.addActor(topleft);
		this.addActor(botleft);

		Image resourcesBackground = new Image(
				new TextureRegionDrawable(InterfaceTextureContainer.findRegion("map-interface-resources")));
		Image selectionBackground = new Image(
				new TextureRegionDrawable(InterfaceTextureContainer.findRegion("map-interface-selection-info")));
		unitInfoWindow = new UnitParametersInfoWindow();
		resourcesBackground.setBounds(20*GameVariables.getScaleX(), 900*GameVariables.getScaleY(), 775*GameVariables.getScaleX(), 150*GameVariables.getScaleY());
		selectionBackground.setBounds(20*GameVariables.getScaleX(), 20*GameVariables.getScaleY(), 1300*GameVariables.getScaleX(), 190*GameVariables.getScaleY());
		showUnitParametersButton = new MenuButton(LanguageMap.findString("showParametersRequest"), 1540*GameVariables.getScaleX(), 20*GameVariables.getScaleY());
		showUnitParametersButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				unitInfoWindow.setVisible(!unitInfoWindow.isVisible());
				event.cancel();
			}
		});
		unitInfoWindow.setVisible(false);
		showUnitParametersButton.setVisible(false);
		this.addActor(resourcesBackground);
		this.addActor(selectionBackground);
		this.addActor(unitInfoWindow);
		this.addActor(showUnitParametersButton);
		
		unitImage = new Image();
		unitHpLabel = new Label("", TextStylesContainer.tinyTextStyle);
		unitNameLabel = new Label("", TextStylesContainer.tinyTextStyle);
		unitImage.setBounds(77*GameVariables.getScaleX(), 64*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 100*GameVariables.getScaleY());
		unitHpLabel.setBounds(192*GameVariables.getScaleX(), 111*GameVariables.getScaleY(), 80*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
		unitNameLabel.setBounds(95*GameVariables.getScaleX(), 108*GameVariables.getScaleY(), 90*GameVariables.getScaleX(), 160*GameVariables.getScaleY());
		this.addActor(unitImage);
		this.addActor(unitHpLabel);
		this.addActor(unitNameLabel);
		
		unitSchemes = new ArrayList<Image>();
		
		Label metalTitleLabel = new Label(LanguageMap.findString("metalTitle"), TextStylesContainer.smallTextStyle);
		metalTitleLabel.setBounds(60*GameVariables.getScaleX(), 970*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
		Label energyTitleLabel = new Label(LanguageMap.findString("energyTitle"), TextStylesContainer.smallTextStyle);
		energyTitleLabel.setBounds(60*GameVariables.getScaleX(), 920*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
		this.addActor(metalTitleLabel);
		this.addActor(energyTitleLabel);
		
		metalBar = new Image(InterfaceTextureContainer.findRegion("blackFade"));
		energyBar = new Image(InterfaceTextureContainer.findRegion("blackFade"));
		metalBar.setBounds(190*GameVariables.getScaleX(), 977*GameVariables.getScaleY(), 300*GameVariables.getScaleX(), 50*GameVariables.getScaleY());
		energyBar.setBounds(190*GameVariables.getScaleX(), 917*GameVariables.getScaleY(), 300*GameVariables.getScaleX(), 50*GameVariables.getScaleY());
		this.addActor(metalBar);
		this.addActor(energyBar);
		
		currentMetalBar = new Image(InterfaceTextureContainer.findRegion("white"));
		currentEnergyBar = new Image(InterfaceTextureContainer.findRegion("white"));
		currentMetalBar.setBounds(190*GameVariables.getScaleX(), 977*GameVariables.getScaleY(), 300*GameVariables.getScaleX(), 50*GameVariables.getScaleY());
		currentEnergyBar.setBounds(190*GameVariables.getScaleX(), 917*GameVariables.getScaleY(), 300*GameVariables.getScaleX(), 50*GameVariables.getScaleY());
		this.addActor(currentMetalBar);
		this.addActor(currentEnergyBar);
		
		currentMetalIncrease = new Label("0", TextStylesContainer.smallTextStyle);
		currentEnergyIncrease = new Label("0", TextStylesContainer.smallTextStyle);
		currentMetalIncrease.setBounds(500*GameVariables.getScaleX(), 970*GameVariables.getScaleY(), 130*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
		currentEnergyIncrease.setBounds(500*GameVariables.getScaleX(), 920*GameVariables.getScaleY(), 130*GameVariables.getScaleX(), 60*GameVariables.getScaleY());
		this.addActor(currentMetalIncrease);
		this.addActor(currentEnergyIncrease);
		
		buildDetails = new Label("", TextStylesContainer.smallTextStyle);
		buildDetails.setBounds(1060*GameVariables.getScaleX(), 10*GameVariables.getScaleY(), 250*GameVariables.getScaleX(), 180*GameVariables.getScaleY());
		this.addActor(buildDetails);

		stopProductionButton = new MenuImageButton("turn-off", 220*GameVariables.getScaleX(), 50*GameVariables.getScaleY(), 50*GameVariables.getScaleX(), 50*GameVariables.getScaleY());
		stopProductionButton.setVisible(false);
		this.addActor(stopProductionButton);
		
		this.settingsWindow = new SettingsWindow();
		this.settingsWindow.setVisible(false);
		this.settingsButton = new MenuImageButton("settings-button",810*GameVariables.getScaleX(), 990*GameVariables.getScaleY(), 60*GameVariables.getScaleX(), 60*GameVariables.getScaleY());		
		this.settingsButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y)
			{
				settingsWindow.setVisible(true);
			}
		});
		this.addActor(this.settingsWindow);
		this.addActor(this.settingsButton);
	}

	public MenuWindow getLeaveRequest() {
		return this.leaveRequest;
	}

	public class ScreenBorder extends Actor {
		public ScreenBorder() {
			super();
		}

		@Override
		public void draw(Batch batch, float alpha) {

		}
	}

	public void clearUnitParameters() {
		this.unitInfoWindow.setVisible(false);
		this.showUnitParametersButton.setVisible(false);
		unitNameLabel.setText("");
		unitHpLabel.setText("");
		unitImage.setDrawable(null);
		removeBuilderData();

	}
	public void setUnitParameters(final MapUnit unit) {
		this.showUnitParametersButton.setVisible(true);
		this.unitInfoWindow.setUnitParameters(unit);
		unitNameLabel.setText(unit.getUnitName());
		unitHpLabel.setText(LanguageMap.findString("armorTitle")+unit.getCurrentHitPoints()+"/"+unit.getMaxHitPoints());
		unitImage.setDrawable(new TextureRegionDrawable(MapUnitTextureContainer.findRegionMenuImage(unit.getMenuImageName())));
		if(UnitBuilder.class.isAssignableFrom(unit.getClass()))
		{
			ArrayList<ConstructionScheme> list = ((UnitBuilder)unit).getSchemesList();
			unitSchemes.clear();
			for(int i = 0; i<list.size(); i++)
			{
				Image a = list.get(i).getUnitSchemeImage();
				a.setBounds(((a.getX())+(i*110))*GameVariables.getScaleX(), a.getY()*GameVariables.getScaleY(), a.getWidth()*GameVariables.getScaleX(), a.getHeight()*GameVariables.getScaleY());
				unitSchemes.add(a);
				stopProductionButton.setVisible(true);
				if(stopProductionButton.getListeners().size>1)
					stopProductionButton.getListeners().removeIndex(stopProductionButton.getListeners().size-1);
				stopProductionButton.addListener(new ClickListener() {
					 @Override
					 public void clicked(InputEvent event, float x, float y)
					 {
						((UnitBuilder)unit).stopBuilding();
					 }
				});
			}
			addBuilderData();
		}
	}
	public void updateUnitHp(MapUnit unit)
	{
		unitHpLabel.setText(LanguageMap.findString("armorTitle")+unit.getCurrentHitPoints()+"/"+unit.getMaxHitPoints());
	}
	public void removeBuilderData()
	{
		for(Image img : unitSchemes)
		{
			img.remove();
			img.setBounds(400, 50, 100, 100);
		}
		unitSchemes.clear();
		stopProductionButton.setVisible(false);
	}
	public void addBuilderData()
	{
		for(Image img : unitSchemes)
		{
			this.addActor(img);
		}
	}
	public void updateMetalBar()
	{
		currentMetalBar.setWidth(metalBar.getWidth()*MapSession.getCurrentMetalAmount()/MapSession.getMaxMetalAmount());
		currentMetalIncrease.setText((int)MapSession.getCurrentMetalAmount()+"/"+(int)MapSession.getMaxMetalAmount()+"     "+(int)MapSession.getCurrentMetalIncreasePerSec());
	}
	public void updateEnergyBar()
	{
		currentEnergyBar.setWidth(energyBar.getWidth()*MapSession.getCurrentEnergyAmount()/MapSession.getMaxEnergyAmount());
		currentEnergyIncrease.setText((int)MapSession.getCurrentEnergyAmount()+"/"+(int)MapSession.getMaxEnergyAmount()+"     "+(int)MapSession.getCurrentEnergyIncreasePerSec());
	}
	
	public static void loadBuildDetails(MapUnit unit, float buildTime, int metalCost, int energyCost)
	{
		buildDetails.setText(unit.getUnitName()+LanguageMap.findString("armorparameterTitle")+unit.getMaxHitPoints()+LanguageMap.findString("buildTimeParameterTitle")+(int)buildTime+LanguageMap.findString("metalCostParameterTitle")+metalCost+LanguageMap.findString("energyCostParameterTitle")+energyCost);
	}
	public static void removeBuildDetails()
	{
		buildDetails.setText("");
	}
	public class UnitParametersInfoWindow extends WidgetGroup {
		
		private Label hpLabel, damageLabel, rangeLabel, atspeedLabel, bulspeedLabel, regenLabel,
		repairLabel, speedLabel;
		
		public UnitParametersInfoWindow() {
			this.setBounds(1460*GameVariables.getScaleX(), 100*GameVariables.getScaleY(), 440*GameVariables.getScaleX(), 450*GameVariables.getScaleY());
			Image background = new Image(
					new TextureRegionDrawable(InterfaceTextureContainer.findRegion("map-interface-unit-stats")));
			background.setBounds(0, 0, this.getWidth(), this.getHeight());
			this.addActor(background);
			Label hpSign = new Label(LanguageMap.findString("armorSpecTitle"), TextStylesContainer.smallTextStyle);
			hpSign.setBounds(30*GameVariables.getScaleX(), 360*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(hpSign);
			Label damageSign = new Label(LanguageMap.findString("damageSpecTitle"), TextStylesContainer.smallTextStyle);
			damageSign.setBounds(30*GameVariables.getScaleX(), 320*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(damageSign);			
			Label rangeSign = new Label(LanguageMap.findString("rangeSpecTitle"), TextStylesContainer.smallTextStyle);
			rangeSign.setBounds(30*GameVariables.getScaleX(), 280*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(rangeSign);
			Label atspeedSign = new Label(LanguageMap.findString("shootSpeedSpecTitle"), TextStylesContainer.smallTextStyle);
			atspeedSign.setBounds(30*GameVariables.getScaleX(), 240*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(atspeedSign);		
			Label bulspeedSign = new Label(LanguageMap.findString("bulletSpeedSpecTitle"), TextStylesContainer.smallTextStyle);
			bulspeedSign.setBounds(30*GameVariables.getScaleX(), 200*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(bulspeedSign);
			Label regenSign = new Label(LanguageMap.findString("armorRegenSpecTitle"), TextStylesContainer.smallTextStyle);
			regenSign.setBounds(30*GameVariables.getScaleX(), 160*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(regenSign);
			Label repairSign = new Label(LanguageMap.findString("repairSpeedSpecTitle"), TextStylesContainer.smallTextStyle);
			repairSign.setBounds(30*GameVariables.getScaleX(), 120*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(repairSign);
			Label speedSign = new Label(LanguageMap.findString("movSpeedSpecTitle"), TextStylesContainer.smallTextStyle);
			speedSign.setBounds(30*GameVariables.getScaleX(), 80*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(speedSign);
			
			hpLabel = new Label("0", TextStylesContainer.smallTextStyle);
			hpLabel.setBounds(320*GameVariables.getScaleX(), 360*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(hpLabel);
			damageLabel = new Label("0", TextStylesContainer.smallTextStyle);
			damageLabel.setBounds(320*GameVariables.getScaleX(), 320*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(damageLabel);			
			rangeLabel = new Label("0", TextStylesContainer.smallTextStyle);
			rangeLabel.setBounds(320*GameVariables.getScaleX(), 280*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(rangeLabel);
			atspeedLabel = new Label("0", TextStylesContainer.smallTextStyle);
			atspeedLabel.setBounds(320*GameVariables.getScaleX(), 240*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(atspeedLabel);		
			bulspeedLabel = new Label("0", TextStylesContainer.smallTextStyle);
			bulspeedLabel.setBounds(320*GameVariables.getScaleX(), 200*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(bulspeedLabel);
			regenLabel = new Label("0", TextStylesContainer.smallTextStyle);
			regenLabel.setBounds(320*GameVariables.getScaleX(), 160*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(regenLabel);
			repairLabel = new Label("0", TextStylesContainer.smallTextStyle);
			repairLabel.setBounds(320*GameVariables.getScaleX(), 120*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(repairLabel);
			speedLabel = new Label("0", TextStylesContainer.smallTextStyle);
			speedLabel.setBounds(320*GameVariables.getScaleX(), 80*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 40*GameVariables.getScaleY());
			this.addActor(speedLabel);
		}
		public void setUnitParameters(MapUnit unit)
		{
			hpLabel.setText(unit.getMaxHitPoints());
			damageLabel.setText("- -");
			rangeLabel.setText("- -");
			atspeedLabel.setText("- -");
			bulspeedLabel.setText("- -");
			regenLabel.setText("- -");
			repairLabel.setText("- -");
			speedLabel.setText("- -");
			if(MovingUnit.class.isAssignableFrom(unit.getClass()))
			{
				damageLabel.setText((int) ((MovingUnit)unit).getDamage());
				rangeLabel.setText(String.valueOf(((MovingUnit)unit).getRange()));
				atspeedLabel.setText(String.valueOf(((MovingUnit)unit).getShootSpeed()));
				bulspeedLabel.setText(String.valueOf(((MovingUnit)unit).getShellSpeed()));
			}
			if(MobileUnit.class.isAssignableFrom(unit.getClass()))
			{
				speedLabel.setText(String.valueOf(((MobileUnit)unit).getSpeed()));
			}
		}
	}
}
