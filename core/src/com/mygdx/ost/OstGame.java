package com.mygdx.ost;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

public class OstGame extends Game {

	public static final float SCR_WIDTH = 1920, SCR_HEIGHT = 1080;

	public static final int HE_RELOAD = 5500, APHE_RELOAD = 4000;
	public static final int HE_DAMAGE = 1, APHE_DAMAGE = 2;

	public int score = 0;
	public boolean isSoundOn = true;

	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touch;
	BitmapFont font;
	BitmapFont fontUi, fontUiR, fontUiG;

	public static GlyphLayout glyphLayout;

	ScreenMenu screenMenu;
	ScreenGame screenGame;
	ScreenSettings screenSettings;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCR_WIDTH, SCR_HEIGHT);
		touch = new Vector3();
		fontGenerate();

		screenMenu = new ScreenMenu(this);
		screenGame = new ScreenGame(this);
		screenSettings = new ScreenSettings(this);
		setScreen(screenMenu);
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		fontUiG.dispose();
		fontUiR.dispose();
	}

	private void fontGenerate() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("amarugt.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 70;
		parameter.borderWidth = 3;
		font = generator.generateFont(parameter);

		parameter.size = 50;
		parameter.borderWidth = 2;
		fontUi = generator.generateFont(parameter);

		parameter.color = Color.GREEN;
		fontUiG = generator.generateFont(parameter);

		parameter.color = Color.RED;
		fontUiR = generator.generateFont(parameter);

		generator.dispose();
	}

	public static float textWidth(String text, BitmapFont font){
		GlyphLayout layout = new GlyphLayout();
		layout.setText(font, text);
		return layout.width;
	}
}
