package pl.zabel.lank.userinterface.mapview;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.texturecontainers.CityTextureContainer;
import pl.zabel.lank.texturecontainers.InterfaceTextureContainer;
import pl.zabel.lank.texturecontainers.TextStylesContainer;

public class BuildingButton extends WidgetGroup {
	private TextureRegion backgroundGreen, backgroundOrange, shadow;
	private Label nameLabel;
	private boolean built;


	public BuildingButton(String name, String imageName, float x, float y) {
		this.setBounds(x * GameVariables.getScaleX(), y * GameVariables.getScaleY(), 340 * GameVariables.getScaleX(),
				280 * GameVariables.getScaleY());
		nameLabel = new Label(name, TextStylesContainer.smallTextStyle);
		nameLabel.setBounds(BuildingButton.this.getX() + (10 * GameVariables.getScaleX()),
				BuildingButton.this.getY() + (10 * GameVariables.getScaleY()), 320 * GameVariables.getScaleX(),
				40 * GameVariables.getScaleY());
		nameLabel.setAlignment(Align.center);
		this.backgroundGreen = CityTextureContainer.findRegion("buildGreen");
		this.backgroundOrange = CityTextureContainer.findRegion("buildOrange");
		this.shadow = CityTextureContainer.findRegion("buildshadow");
		Image background = new Image(new TextureRegionDrawable(InterfaceTextureContainer.findRegion("white")));
		background.setBounds(0 * GameVariables.getScaleX(), 0 * GameVariables.getScaleY(), 340 * GameVariables.getScaleX(),
				280 * GameVariables.getScaleY());
		Image content = new Image(new TextureRegionDrawable(CityTextureContainer.findRegion(imageName)));
		content.setBounds(10 * GameVariables.getScaleX(), 60 * GameVariables.getScaleY(), 320 * GameVariables.getScaleX(),
				200 * GameVariables.getScaleY());
		this.addActor(background);
		this.addActor(content);
	}

	public boolean isBuilt() {
		return built;
	}

	public void setBuilt(boolean built) {
		this.built = built;
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (built) {
			batch.draw(backgroundGreen, nameLabel.getX(), nameLabel.getY(), nameLabel.getWidth(),
					nameLabel.getHeight());
		} else {
			batch.draw(backgroundOrange, nameLabel.getX(), nameLabel.getY(), nameLabel.getWidth(),
					nameLabel.getHeight());
		}
		batch.draw(shadow, nameLabel.getX(), nameLabel.getY(), nameLabel.getWidth(), nameLabel.getHeight());
		nameLabel.draw(batch, parentAlpha);
	}
}