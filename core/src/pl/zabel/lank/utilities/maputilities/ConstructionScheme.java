package pl.zabel.lank.utilities.maputilities;

import com.badlogic.gdx.scenes.scene2d.ui.Image;


public interface ConstructionScheme{
	
	public Image getUnitSchemeImage();
	public void schemeActivationEvent();
	public void schemeHoverEvent();
	public void schemeHoverEndEvent();

}
