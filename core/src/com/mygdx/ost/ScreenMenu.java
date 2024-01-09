package com.mygdx.ost;

import static com.mygdx.ost.OstGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ScreenMenu implements Screen {
    OstGame game;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font, fontUi;

    Texture imgBG;

    MyButton btnPlay;
    MyButton btnSave;
    MyButton btnLoad;
    MyButton btnExit;
    MyButton btnSettings;

    String preferenceStatus = "Welcome";

    public ScreenMenu(OstGame game){
        this.game = game;
        batch = game.batch;
        camera = game.camera;
        touch = game.touch;
        font = game.font;
        fontUi = game.fontUi;

        glyphLayout = new GlyphLayout();

        imgBG = new Texture("bg_menu.png");

        btnPlay = new MyButton("Play", font,SCR_WIDTH/2, SCR_HEIGHT*7/10);
        btnSave = new MyButton("Save", font,SCR_WIDTH/2, SCR_HEIGHT*6/10);
        btnLoad = new MyButton("Load", font,SCR_WIDTH/2, SCR_HEIGHT*5/10);
        btnSettings = new MyButton("Settings", font,SCR_WIDTH/2, SCR_HEIGHT*4/10);
        btnExit = new MyButton("Exit", font,SCR_WIDTH/2, SCR_HEIGHT*3/10);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // касания
        if (Gdx.input.justTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (btnPlay.hit(touch.x, touch.y)) {
                game.setScreen(game.screenGame);
            }
            if (btnExit.hit(touch.x, touch.y)) {
                Gdx.app.exit();
            }
            if (btnSettings.hit(touch.x, touch.y)) {
                game.setScreen(game.screenSettings);
            }
            if (btnSave.hit(touch.x, touch.y)) {
                savePlayer();
                preferenceStatus = "Saved";
            }
            if (btnLoad.hit(touch.x, touch.y)) {
                loadPlayer();
                preferenceStatus = "Loaded";
            }
        }
        // события

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);

        glyphLayout.setText(font, "M E N U");
        font.draw(batch, "M E N U", SCR_WIDTH/2-glyphLayout.width/2, SCR_HEIGHT*9/10);
        btnPlay.font.draw(batch, btnPlay.text, btnPlay.x, btnPlay.y);
        btnSave.font.draw(batch, btnSave.text, btnSave.x, btnSave.y);
        btnLoad.font.draw(batch, btnLoad.text, btnLoad.x, btnLoad.y);
        btnSettings.font.draw(batch, btnSettings.text, btnSettings.x, btnSettings.y);
        btnExit.font.draw(batch, btnExit.text, btnExit.x, btnExit.y);
        fontUi.draw(batch, preferenceStatus, 0 + SCR_WIDTH / 27 / 28, SCR_HEIGHT * 69 / 70);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        imgBG.dispose();
    }

    private void savePlayer() {
        Preferences preferences = Gdx.app.getPreferences("playerOst");
        preferences.putInteger("Score", game.score);
        preferences.flush();
    }

    private void loadPlayer() {
        Preferences preferences = Gdx.app.getPreferences("playerOst");
        game.score = preferences.getInteger("Score");
    }
}
