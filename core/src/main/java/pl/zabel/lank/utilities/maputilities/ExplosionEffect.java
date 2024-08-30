package pl.zabel.lank.utilities.maputilities;

import java.util.Random;

import pl.zabel.lank.views.MapView;

public class ExplosionEffect extends SpecialEffect{

	public ExplosionEffect(float posX, float posY, float width, float height, MapView mapView) {
		super("explosion-1-ver", posX, posY, width, height, 0, 19, false, mapView);
		Random random = new Random();
		int type = random.nextInt(3) + 1;
		if(type==2)
			this.key = "explosion-2-ver";
		if(type==3)
			this.key = "explosion-3-ver";
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
		int angle = random.nextInt(360);
		this.setRotation(angle);
	}
}
