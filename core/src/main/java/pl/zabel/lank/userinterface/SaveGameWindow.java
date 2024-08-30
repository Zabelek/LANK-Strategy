package pl.zabel.lank.userinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.SaveGameHandler;

public class SaveGameWindow extends MenuWindow{

	private TextField nameTextField;
	private Table filesPanel, scrolLTable;
	private ScrollPane pane;

	public SaveGameWindow() {
		super("ZAPISZ GRE", 500*GameVariables.getScaleX(), 200*GameVariables.getScaleY(), 920*GameVariables.getScaleX(), 680*GameVariables.getScaleY());
		this.textDisplayed.setPosition(0,  360*GameVariables.getScaleY());
		Label newSavelabel = new Label(LanguageMap.findString("saveWindowNewSave"), TextStylesContainer.smallTextStyle);
		newSavelabel.setBounds(30*GameVariables.getScaleX(), 550*GameVariables.getScaleY(), 100*GameVariables.getScaleX(), 80*GameVariables.getScaleY());
		this.nameTextField = new TextField(LanguageMap.findString("saveWindowSavename"), TextStylesContainer.textFieldStyle);
		this.nameTextField.setBounds(200*GameVariables.getScaleX(), 550*GameVariables.getScaleY(), 500*GameVariables.getScaleX(), 70*GameVariables.getScaleY());
		MenuButton newSaveButton = new MenuButton(LanguageMap.findString("saveWindowCreate"), 710*GameVariables.getScaleX(), 550*GameVariables.getScaleY());
		newSaveButton.addListener(new ClickListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				SaveGameHandler.saveGameToFile(nameTextField.getText());
				updateFilesPanel();
				event.stop();
				return true;
			}});
		
		this.filesPanel = new Table();
		this.filesPanel.pack();
		this.filesPanel.setTransform(false);
		this.filesPanel.setOrigin(this.filesPanel.getWidth() / 2,
				this.filesPanel.getHeight() / 2);
		this.pane = new ScrollPane(filesPanel, TextStylesContainer.scrollPaneStyle);
		//this.pane.setBounds(0, 0, 860, 400);
		this.scrolLTable = new Table();
		this.scrolLTable.setBounds(0, 0*GameVariables.getScaleY(), 700*GameVariables.getScaleX(), 400*GameVariables.getScaleY());
		this.scrolLTable.add(pane).height(400*GameVariables.getScaleY());
		this.addActor(newSavelabel);
		this.addActor(this.nameTextField);
		this.addActor(newSaveButton);
		//this.addActor(this.filesPanel);
		this.addActor(scrolLTable);
	}
	public void updateFilesPanel()
	{
		this.filesPanel.setBounds(0, 0*GameVariables.getScaleY(), 700*GameVariables.getScaleX(), 400*GameVariables.getScaleY());
		this.filesPanel.clearChildren();
		FileHandle files = Gdx.files.external("Documents/LANKsaves/");
		int fileIndex = 0;
		this.filesPanel.clear();
		for(FileHandle file : files.list())
		{ 
			MenuButton button = new MenuButton(file.name().substring(0, file.name().indexOf('.')), 0, 0);
			final String buttontext = button.getText();
			button.addListener(new ClickListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				SaveGameHandler.saveGameToFile(buttontext);
				updateFilesPanel();
				event.stop();
				return true;
			}});
			WidgetGroup group = new WidgetGroup();
			group.addActor(button);
			this.filesPanel.add(group).padBottom(1).size(700*GameVariables.getScaleX(), 90*GameVariables.getScaleY()).row();
			fileIndex++;
		}
		this.filesPanel.pack();
		this.filesPanel.setOrigin(this.filesPanel.getWidth() / 2,
		this.filesPanel.getHeight() / 2);
		pane.setScrollingDisabled(true, false);
		pane.setFadeScrollBars(false);
		this.scrolLTable.setFillParent(true);
	}

}
