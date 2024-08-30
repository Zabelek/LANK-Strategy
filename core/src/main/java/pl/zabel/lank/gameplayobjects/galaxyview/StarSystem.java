package pl.zabel.lank.gameplayobjects.galaxyview;

import pl.zabel.lank.gameplayobjects.City;

public class StarSystem {
	
	//Capture state: 0 for not available, 1 for available, 2 for captured, 3 for city
	//Resource potential: 00 00 00 00 : gold uranium petroleum titanium
	public enum CaptureState {
		NOT_AVAILABLE,
		AVAILABLE,
		CAPTURED,
		CITY
	}
	
	private CaptureState captureState;
	private String name, regionName;
	private int xPos, yPos, resourcePotential, risk, timeNeeded;
	private City city;
	private byte starsToUnlock;
	
	public StarSystem(String name, int resourcePotential, int xPos, int yPos, int starsToUnlock, String regionName)
	{
		this.name = name;
		this.resourcePotential = resourcePotential;
		this.xPos = xPos;
		this.yPos = yPos;
		this.starsToUnlock = (byte)starsToUnlock;
		this.regionName = regionName;
	}
	
	public CaptureState getCaptureState() {
		return captureState;
	}

	public void setCaptureState(CaptureState captureState, City city) {
		if(city==null && captureState==CaptureState.CITY)
		{
			this.captureState=CaptureState.CAPTURED;
		}
		else {
			this.captureState = captureState;
		}
		if(city!=null && captureState==CaptureState.CITY)
		{
			city.setSystem(this);
		}
		this.city = city;
	}
	
	public String getName() {
		return name;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public int getResourcePotential() {
		return resourcePotential;
	}

	public int getRisk() {
		return risk;
	}

	public int getTimeNeeded() {
		return timeNeeded;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	public byte getStarsToUnlock()
	{
		return this.starsToUnlock;
	}

	public String getRegionName() {
		return regionName;
	}
}
