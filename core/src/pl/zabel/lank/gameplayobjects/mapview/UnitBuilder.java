package pl.zabel.lank.gameplayobjects.mapview;

import java.util.ArrayList;

import pl.zabel.lank.utilities.maputilities.ConstructionScheme;
import pl.zabel.lank.views.MapView;

public interface UnitBuilder{

	public ArrayList<ConstructionScheme> getSchemesList();
	public boolean isBuilding();
	public void setBuildingState(boolean value);
	public void startBuilding(MapBuilding building, float x, float y);
	public void stopBuilding();
	public float getBuildingSpeed();
	public void addToQueue(MobileUnit unit);
	public void addToQueue(MapBuilding unit);
	default void setUpVisualBuildEffects(MapUnit unit)
	{}
}
