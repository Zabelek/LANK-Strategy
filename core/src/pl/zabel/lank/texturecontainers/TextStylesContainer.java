package pl.zabel.lank.texturecontainers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TextStylesContainer {
	
	public static Label.LabelStyle genericButtonStyle, smallTextStyle, tinyTextStyle;
	public static TextField.TextFieldStyle textFieldStyle;
	public static Slider.SliderStyle sliderStyle;
	public static ScrollPane.ScrollPaneStyle scrollPaneStyle;
	public static TextButton.TextButtonStyle textButtonStyle;
	
	public static void load()
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/azoft-sans-bold.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		FreeTypeFontParameter parameter1 = new FreeTypeFontParameter();
		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter.characters ="ĘęĄąĆćŃńÓóŻżŹźŁłŚś\0";
		
		if(Gdx.graphics.getWidth()>=1920)
		{
			parameter.size = 32;
			parameter1.size = 22;
			parameter2.size = 16;
		}else if(Gdx.graphics.getWidth()>=1366)
		{
			parameter.size = 22;
			parameter1.size = 16;
			parameter2.size = 10;
		}
		else
		{
			parameter.size = 15;
			parameter1.size = 10;
			parameter2.size = 8;
		}
		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS+"ĘęĄąĆćŃńÓóŻżŹźŁłŚś";
		parameter1.characters = FreeTypeFontGenerator.DEFAULT_CHARS+"ĘęĄąĆćŃńÓóŻżŹźŁłŚś";
		parameter2.characters = FreeTypeFontGenerator.DEFAULT_CHARS+"ĘęĄąĆćŃńÓóŻżŹźŁłŚś";
		BitmapFont myFont = generator.generateFont(parameter);		
		genericButtonStyle = new Label.LabelStyle();
		genericButtonStyle.font = myFont;
		genericButtonStyle.fontColor = Color.WHITE;
		myFont = generator.generateFont(parameter1);		
		smallTextStyle = new Label.LabelStyle();
		smallTextStyle.font = myFont;
		smallTextStyle.fontColor = Color.WHITE;
		myFont = generator.generateFont(parameter2);
		tinyTextStyle = new Label.LabelStyle();
		tinyTextStyle.font = myFont;
		tinyTextStyle.fontColor = Color.WHITE;
		myFont = generator.generateFont(parameter1);
		textFieldStyle = new TextField.TextFieldStyle();
		textFieldStyle.background = new TextureRegionDrawable(InterfaceTextureContainer.findRegion("blackFade"));
		textFieldStyle.font = myFont;
		textFieldStyle.fontColor = Color.WHITE;
		generator.dispose();
		sliderStyle = new Slider.SliderStyle(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("slider")), 
				new TextureRegionDrawable(InterfaceTextureContainer.findRegion("slider-knob")));
		scrollPaneStyle = new ScrollPane.ScrollPaneStyle(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("lightBlack")),
				new TextureRegionDrawable(InterfaceTextureContainer.findRegion("text-button-background-normal")),
				new TextureRegionDrawable(InterfaceTextureContainer.findRegion("text-button-background-normal")),
				new TextureRegionDrawable(InterfaceTextureContainer.findRegion("text-button-background-normal")),
				new TextureRegionDrawable(InterfaceTextureContainer.findRegion("text-button-background-normal")));
		textButtonStyle = new TextButton.TextButtonStyle(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("lightBlack")),
				new TextureRegionDrawable(InterfaceTextureContainer.findRegion("slider-knob")),
				new TextureRegionDrawable(InterfaceTextureContainer.findRegion("slider-knob")), myFont);
	}

}
