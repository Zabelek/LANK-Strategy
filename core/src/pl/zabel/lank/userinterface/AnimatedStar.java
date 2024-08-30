package pl.zabel.lank.userinterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import pl.zabel.lank.GameVariables;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem;
import pl.zabel.lank.gameplayobjects.galaxyview.StarSystem.CaptureState;
import pl.zabel.lank.texturecontainers.GalaxyTextureContainer;
import pl.zabel.lank.utilities.LanguageMap;
import pl.zabel.lank.utilities.SoundEffectManager;
import pl.zabel.lank.views.GalaxyRegionView;

public class AnimatedStar extends Actor {
	
	public static Timer.Task sparklingTask;
	
    TextureRegion tex;
    float posX, posY, drawWidth, drawPosX, drawPosY;
    int cycle=0;
    public boolean hover = false;
    private StarSystem starSystem;

    public AnimatedStar(final GalaxyRegionView regionView, final StarSystem starSystem){
    	
    	this.starSystem = starSystem;
    	this.tex = GalaxyTextureContainer.findStarRegion("star_1");
    	this.posX = (float)(starSystem.getxPos())*GameVariables.getScaleX();
    	this.posY = (float)(starSystem.getyPos())*GameVariables.getScaleY();
    	this.drawWidth = 80*GameVariables.getScaleX();
        this.drawPosX = posX-(40*GameVariables.getScaleX());
        this.drawPosY = posY-(40*GameVariables.getScaleY());
        this.setBounds(drawPosX,drawPosY,drawWidth,drawWidth);
        
        
        this.addListener(new ClickListener(){
        	@Override
            public void enter (InputEvent event, float x, float y, int pointer, Actor button) {
        		if(starSystem.getCaptureState()!=CaptureState.NOT_AVAILABLE)
        		{
        			hover = true;
        			cycle=0;
        			regionView.displayOnHintsLabel(starSystem.getName());
        		}
        		else {
        			regionView.displayOnHintsLabel(starSystem.getName()+LanguageMap.findString("accessBlockedText"));
        		}
                event.stop();
            }
        	@Override
            public void exit (InputEvent event, float x, float y, int pointer, Actor button) {
                hover = false;
                cycle=0;
                regionView.displayOnHintsLabel("");
                event.stop();
            }
        	@Override
			public void clicked (InputEvent event, float x, float y) {
				if(starSystem.getCaptureState()==CaptureState.NOT_AVAILABLE)
					SoundEffectManager.playClickLockedSound();
				event.stop();
			}
        });
        addListener(new InputListener() {
        	 public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                 return true;
        	 }
        });
    }
    
    public StarSystem getStarSystem() {
		return starSystem;
	}

	public void animate()
    {
    	if (hover)
    	{
    		if(cycle==0)
    		{
    			tex = GalaxyTextureContainer.findStarRegion("star_hover1");
    			cycle++;
    		}
    		else if(cycle==1)
    		{
    			tex = GalaxyTextureContainer.findStarRegion("star_hover2");
    			cycle++;
    		}
    		else
    		{
    			tex = GalaxyTextureContainer.findStarRegion("star_hover3");
    		}
    	}
    	else {
    		if(cycle==1)
    		{
    			tex = GalaxyTextureContainer.findStarRegion("star_2");
    			cycle--;
    		}
    		else
    		{
    			tex = GalaxyTextureContainer.findStarRegion("star_1");
    			cycle++;
    		}
    	}
    }
    
    @Override
    public void draw(Batch batch, float alpha){
    	
        	batch.draw(tex, drawPosX, drawPosY, drawWidth, drawWidth);
    }
    
    @Override
    public void act(float delta){
    }
    public static void animateStars()
    {
    	
    }
}
