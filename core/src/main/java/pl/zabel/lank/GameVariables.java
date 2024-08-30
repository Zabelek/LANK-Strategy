package pl.zabel.lank;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Affine2;

public class GameVariables {

	private static float scaleX, scaleY; // do slalowania interfejsu wraz ze zmian¹ rozdzielczoœci
	private static float stageFadingAlpha; // fade in i out sceny z regionami w galaxy view
	private static float musicVolume, effectsVolume; // wartosci od 0 do 1
	private static float renderDistance; // okresla na ile pikseli od srodka kamery jednostki beda renderowane
	private static boolean shadows;
	private static Affine2 shadowShear;

	public static float getScaleX() {
		return scaleX;
	}

	public static void setScaleX(float scaleX) {
		GameVariables.scaleX = scaleX;
	}

	public static float getScaleY() {
		return scaleY;
	}

	public static void setScaleY(float scaleY) {
		GameVariables.scaleY = scaleY;
	}

	public static float getStageFadingAlpha() {
		return stageFadingAlpha;
	}

	public static void setStageFadingAlpha(float stageFadingAlpha) {
		GameVariables.stageFadingAlpha = stageFadingAlpha;
	}

	public static float getMusicVolume() {
		return musicVolume;
	}

	public static void setMusicVolume(float musicVolume) {
		GameVariables.musicVolume = musicVolume;
	}

	public static float getEffectsVolume() {
		return effectsVolume;
	}

	public static void setEffectsVolume(float effectsVolume) {
		GameVariables.effectsVolume = effectsVolume;
	}

	public static float getRenderDistance() {
		return renderDistance;
	}

	public static void setRenderDistance(float renderDistance) {
		GameVariables.renderDistance = renderDistance;
	}

	public static boolean isShadows() {
		return shadows;
	}

	public static void setShadows(boolean shadows) {
		GameVariables.shadows = shadows;
	}

	public static Affine2 getShadowShear() {
		return shadowShear;
	}

	public static void setShadowShear(Affine2 shadowShear) {
		GameVariables.shadowShear = shadowShear;
	}

	public static void initializeGameVariables() {
		scaleX = Gdx.graphics.getWidth() / 1920f;
		scaleY = Gdx.graphics.getHeight() / 1080f;
		stageFadingAlpha = 1;
		musicVolume = 1;
		effectsVolume = 1;
		shadows = true;
		renderDistance = 1000;
		shadowShear = new Affine2();
	}
}
