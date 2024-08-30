package pl.zabel.lank.gameplayobjects.galaxyview;

import java.util.ArrayList;

import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem.CaptureState;
import pl.zabel.lank.utilities.JsonHandler;

public class GalaxyRegion {
	
	public enum RegionCaptureState {
		NOT_AVAILABLE,
		AVAILABLE,
		CAPTURED
	}
	
	private RegionCaptureState captureState; //0 for not available, 1 for available, 2 for captured
	private byte capturedRegionsToUnlock;
	private String name;
	ArrayList<StarSystem> starSystems;
	
	public GalaxyRegion(String name, RegionCaptureState captureState, byte capturedRegionsToUnlock)
	{
		this.name = name;
		this.captureState = captureState;
		this.capturedRegionsToUnlock = capturedRegionsToUnlock;
		starSystems = JsonHandler.loadStarSystemsForRegion(name);
		if(starSystems!=null)
		{
			for(StarSystem sys : starSystems)
			{
				sys.setCaptureState(CaptureState.NOT_AVAILABLE, null);
			}
		}
	}
	
	public RegionCaptureState getCaptureState() {
		return captureState;
	}
	public void setCaptureState(RegionCaptureState captureState) {
		this.captureState = captureState;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<StarSystem> getStarSystems() {
		return starSystems;
	}

	public byte getCapturedRegionsToUnlock() {
		return capturedRegionsToUnlock;
	}
}
