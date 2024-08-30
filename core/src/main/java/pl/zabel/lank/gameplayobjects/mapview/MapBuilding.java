package pl.zabel.lank.gameplayobjects.mapview;

public interface MapBuilding {
	
	public void setInBuild(boolean inBuild);
	public boolean isInBuild();
	public void updateBuildTick();
	public MapBuildSpectre getBuildSpectre();	
	public UnitBuilder getBuilder();
	public boolean checkTerrain();
	public float getMassTickCost();
	public float getEnergyTickCost();
	//for easier casting as Actor
	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public float getFixedPositionX();
	public float getFixedPositionY();
	public void setPosition(float x, float y);
	public int getCurrentHitPoints();
	public MapUnit getActorForStage();
	public void destroy();
}
