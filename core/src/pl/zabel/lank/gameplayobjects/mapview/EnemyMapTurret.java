package pl.zabel.lank.gameplayobjects.mapview;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.views.MapView;

public class EnemyMapTurret extends MapTurret{

	protected MapEnemy enemy;
	
	public EnemyMapTurret(String name, byte faction, MapView mapView, UnitBuilder builder,  MapEnemy enemy) {
		super(name, faction, mapView, builder);
		this.enemy = enemy;
	}
	public void updateBuildTick() {
		if (this.buildTicksLeft < (this.maxBuildTicks/2) && this.isVisible()==false)
			this.setVisible(true);
			if (this.buildTicksLeft > 0) {
				if (enemy.getCurrentMetalAmount() >= this.massTickCost
						&& enemy.getCurrentEnergyAmount() >= this.energyTickCost)
				{
					enemy.modifyCurrentMetalAmount(-1 *massTickCost);
					enemy.modifyCurrentEnergyAmount(-1 *energyTickCost);
					this.buildTicksLeft = this.buildTicksLeft - 1;
					if(buildTicksLeft<(maxBuildTicks/2))
						buildTextureAlphaModifier = ((buildTicksLeft/maxBuildTicks/2)*3.8f);
					else if(buildTicksLeft==maxBuildTicks/2)
						buildTextureAlphaModifier = 1;
					else
						buildTextureAlphaModifier = 1-((buildTicksLeft/maxBuildTicks)/2f);
				}
			}
			else {
				this.setInBuild(false);
				this.builder.setBuildingState(false);
				this.setDestination(this.getX()+this.getWidth()/2, this.getY()-100);
			}	
	}
}
