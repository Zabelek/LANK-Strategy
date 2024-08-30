package pl.zabel.lank.gameplayobjects.mapview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import pl.zabel.lank.views.MapView;

public class MapBuildSpectre extends Actor{

	private TextureRegion texture;
	private MapBuilding unit;
	private MapView mapView;
	public MapBuildSpectre(MapBuilding unit, MapView mapView)
	{
		this.unit = unit;
		this.texture = ((MapUnit)unit).getDisplayTexture();
		this.mapView = mapView;
		this.setBounds(0, 0, ((MapUnit)unit).textureWidth, ((MapUnit)unit).textureHeight);
	}
	public void chaeckColor()
	{
		mapView.setPositionForPlacedBuilding(unit, Gdx.input.getX(), Gdx.input.getY());
		if(!unit.checkTerrain())
		{
			this.setColor(1, 0, 0, 0.7f);
		}
		else
		{
			this.setColor(1, 1, 0, 0.7f);
		}
	}
	 public void draw(Batch batch, float alpha){
		batch.setColor(this.getColor());
		batch.draw(texture, (Gdx.input.getX())-this.getWidth()/2, (Gdx.graphics.getHeight()-Gdx.input.getY())-unit.getHeight()/2, this.getWidth(), this.getHeight());
		batch.setColor(1, 1, 1, 1);
	 }
}
