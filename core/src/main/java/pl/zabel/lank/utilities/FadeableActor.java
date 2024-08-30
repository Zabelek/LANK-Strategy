package pl.zabel.lank.utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

public class FadeableActor extends Image{
	
	private float r = this.getColor().r;
	private float g = this.getColor().g;
	private float b = this.getColor().b;
	private float a=1;
	protected TextureRegion tex;
	
	public FadeableActor(TextureRegion tex, float posx, float posy, float width, float height)
	{
		this.setBounds(posx, posy, width, height);
		this.tex = tex;
	}
	
	public void fadeIn(final float seconds)
	{
		this.a = 0;
		this.setColor(r, g, b, a);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if(FadeableActor.this.getColor().a<1)
				{
					a += 1f/(30f*seconds);
					FadeableActor.this.getColor().a = a;
				}
				else
				{
					FadeableActor.this.getColor().a = 1;
					this.cancel();
				}
			}
		},0, 1f/30f);
	}
	public void fadeOut(final float seconds)
	{
		this.a = 1;
		this.setColor(r, g, b, a);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if(FadeableActor.this.getColor().a>0)
				{
					a -= 1f/(30f*seconds);
					FadeableActor.this.getColor().a = a;
				}
				else
				{
					FadeableActor.this.getColor().a = 0;
					this.cancel();
				}
			}
		},0, 1f/30f);
	}
	public void fadeOut(final float seconds, final float delay)
	{
		this.a = 1;
		this.setColor(r, g, b, a);
		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				if(FadeableActor.this.getColor().a>0)
				{
					a -= 1f/(30f*seconds);
					FadeableActor.this.getColor().a = a;
				}
				else
				{
					FadeableActor.this.getColor().a = 0;
					this.cancel();
				}
			}
		},delay, 1f/30f);
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		Color color = getColor();
	    batch.setColor(color.r, color.g, color.b, color.a * alpha);
		batch.draw(this.tex, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		batch.setColor(color.r, color.g, color.b, 1);
	}
}
