package pl.zabel.lank.userinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.texturecontainers.TextStylesContainer;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.SaveGameHandler;

public class LoadGameWindow extends MenuWindow{
	
	private Table filesPanel, scrolLTable;
	private ScrollPane pane;

	public LoadGameWindow() {
		super(LanguageMap.findString("loadGameButtonText"), 600*GameVariables.getScaleX(), 200*GameVariables.getScaleY(), 720*GameVariables.getScaleX(), 680*GameVariables.getScaleY());
		this.textDisplayed.setPosition(0,  360*GameVariables.getScaleY());
		this.filesPanel = new Table();
		this.filesPanel.pack();
		this.filesPanel.setTransform(false);
		this.filesPanel.setOrigin(this.filesPanel.getWidth() / 2,
				this.filesPanel.getHeight() / 2);
		this.pane = new ScrollPane(filesPanel, TextStylesContainer.scrollPaneStyle);
		this.scrolLTable = new Table();
		this.scrolLTable.setBounds(0, 0, 550*GameVariables.getScaleX(), 400*GameVariables.getScaleY());
		this.scrolLTable.add(pane).height(400*GameVariables.getScaleY());
		this.addActor(scrolLTable);
	}
	public void updateFilesPanel()
	{
		this.filesPanel.setBounds(0, 0, 550*GameVariables.getScaleX(), 400*GameVariables.getScaleY());
		this.filesPanel.clearChildren();
		FileHandle files = Gdx.files.external("Documents/LANKsaves/");
		this.filesPanel.clear();
		for(FileHandle file : files.list())
		{ 
			MenuButton button = new MenuButton(file.name().substring(0, file.name().indexOf('.')), 0, 0);
			final String buttontext = button.getText();
			button.addListener(new ClickListener() {
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				SaveGameHandler.loadGameFromFile(buttontext);
				LoadGameWindow.this.setVisible(false);
				event.stop();
				return true;
			}});
			WidgetGroup group = new WidgetGroup();
			group.addActor(button);
			this.filesPanel.add(group).padBottom(1).size(550*GameVariables.getScaleX(), 90*GameVariables.getScaleY()).row();
		}
		this.filesPanel.pack();
		this.filesPanel.setOrigin(this.filesPanel.getWidth() / 2,
		this.filesPanel.getHeight() / 2);
		pane.setScrollingDisabled(true, false);
		pane.setFadeScrollBars(false);
		this.scrolLTable.setFillParent(true);
	}
	

}
