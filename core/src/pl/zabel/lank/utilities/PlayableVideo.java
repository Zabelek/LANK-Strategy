package pl.zabel.lank.utilities;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayer.CompletionListener;
import com.badlogic.gdx.video.VideoPlayerCreator;

public class PlayableVideo extends Actor{	

	private boolean loop, isFinished, isPaused, isLoaded;
	private VideoPlayer videoPlayer;
	
	public PlayableVideo(String name, float posx, float posy, float width, float height)
	{
		this.loop = false;
		this.isFinished = false;
		this.isPaused = false;
		this.setBounds(posx, posy, width, height);
		this.isLoaded = false;

		videoPlayer = VideoPlayerCreator.createVideoPlayer();
		if(Gdx.graphics.getWidth()>=1920)
			name = name+"_r3";
		else if(Gdx.graphics.getWidth()>=1366)
			name = name+"_r2";
		else
			name = name+"_r1";
	    try {
	        videoPlayer.play(Gdx.files.internal("graphics/video/"+name+".ogv"));
			videoPlayer.resize((int)width, (int)height);
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	public PlayableVideo(final String name, float posx, float posy, float heigth, float width, boolean loop)
	{
		this(name, posx, posy, heigth, width);
		this.loop = loop;
		videoPlayer.setOnCompletionListener(new CompletionListener() {

			@Override
			public void onCompletionListener(FileHandle file) {
				 try {
					videoPlayer.play(Gdx.files.internal("graphics/video/"+name+".ogv"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	public void stop()
	{
		this.isFinished = true;
		if (videoPlayer!=null)
		{
		videoPlayer.stop();
		videoPlayer.dispose();
		videoPlayer = null;
		}
		this.remove();
	}
	public void pause()
	{
		this.isPaused = true;
		videoPlayer.pause();
	}
	public void play()
	{
		this.isPaused = false;
		videoPlayer.resume();
	}
	
	@Override
	public void draw(Batch batch, float alpha)
	{
		videoPlayer.render();
	}
}
