package com.codelackeys.transcension.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector3;
import com.codelackeys.transcension.maputils.World;

public final class DeveloperHud {
	
	private static FreeTypeFontGenerator generator;
	private static FreeTypeFontParameter parameter;
	private static BitmapFont font12;
	private static OrthographicCamera devCam;
	private static boolean enabled = true;
	static {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("consola.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 16;
		parameter.borderWidth = 2;
		parameter.borderColor = Color.BLACK;
		font12 = generator.generateFont(parameter); // font size 12 pixels
		font12.setColor(Color.YELLOW);
		generator.dispose();
		// pixel perfect ortho cam for text rendering
		devCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		devCam.setToOrtho(false);	// center the camera
		devCam.update();
	}
	public static void toggle() {
		enabled = !enabled;
	}
	public static void draw(SpriteBatch batch, OrthographicCamera camera) {
		
		if (!enabled) return;
		
		Vector3 mouseWorldCoords = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		
		StringBuilder sb = new StringBuilder();
		sb.append("FPS: " + Gdx.graphics.getFramesPerSecond() + " | ");
		sb.append("ScrPos: (" + Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight() + ") | ");
		sb.append("CamPos: (" + camera.position.x + ", " + camera.position.y + ") | \n");
		sb.append("MousePos: (" + Gdx.input.getX() + ", " + Gdx.input.getY() + ") | ");
		sb.append("CurMap: " + World.getMapName() + " | \n");
		sb.append("MWorldCoords: (" + Math.round(mouseWorldCoords.x) + ", " + Math.round(mouseWorldCoords.y) + ") | ");
		
		batch.setProjectionMatrix(devCam.combined);
		font12.draw(batch, sb, 0, devCam.viewportHeight - font12.getCapHeight());
	}
}
