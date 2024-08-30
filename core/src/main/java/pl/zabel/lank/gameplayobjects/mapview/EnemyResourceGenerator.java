package pl.zabel.lank.gameplayobjects.mapview;

import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.MapSession;
import pl.zabel.lank.views.MapView;

public class EnemyResourceGenerator extends ResourceGenerator{

	protected MapEnemy enemy;
	
	public EnemyResourceGenerator(String name, byte faction, MapView mapView, UnitBuilder builder,
			int maxAnimationKeys, MapEnemy enemy) {
		super(name, faction, mapView, builder, maxAnimationKeys);
		this.enemy = enemy;
	}
	@Override
	public void updateBuildTick() {
		if (this.buildTicksLeft < (this.maxBuildTicks/2) && this.isVisible()==false)
			this.setVisible(true);
		if (this.buildTicksLeft == this.maxBuildTicks && !buildStarted) {
			buildStarted = true;
		}
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
	@Override
	public void generateResource()
	{
		if(type ==true)
			enemy.modifyCurrentEnergyAmount(1);
		else
			enemy.modifyCurrentMetalAmount(1);
	}
	@Override
	public void startGenerating()
	{
		Timer.schedule(generateResourceTask, 0, 1/generationSpeed);
	}
	@Override
	public void stopGenerating()
	{
		generateResourceTask.cancel();
	}
}
