package com.mygdx.ost;

import static com.mygdx.ost.OstGame.*;

import com.badlogic.gdx.Gdx;
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
    BitmapFont font;

    Texture imgBG;
    Texture img;

    MyButton btnPlay;
    MyButton btnExit;
    MyButton btnSettings;

    public ScreenMenu(OstGame game){
        this.game = game;
        batch = game.batch;
        camera = game.camera;
        touch = game.touch;
        font = game.font;

        glyphLayout = new GlyphLayout();

        imgBG = new Texture("bg_menu.png");
        img = new Texture("badlogic.jpg");

        btnPlay = new MyButton("Play", font,SCR_WIDTH/2, SCR_HEIGHT*7/10);
        btnSettings = new MyButton("Settings", font,SCR_WIDTH/2, SCR_HEIGHT*6/10);
        btnExit = new MyButton("Exit", font,SCR_WIDTH/2, SCR_HEIGHT*5/10);
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
        }
        // события


        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        glyphLayout.setText(font, "M E N U");
        font.draw(batch, "M E N U", SCR_WIDTH/2-glyphLayout.width/2, SCR_HEIGHT*9/10);
        batch.draw(img, 1000, 0);
        btnPlay.font.draw(batch, btnPlay.text, btnPlay.x, btnPlay.y);
        btnSettings.font.draw(batch, btnSettings.text, btnSettings.x, btnSettings.y);
        btnExit.font.draw(batch, btnExit.text, btnExit.x, btnExit.y);
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
        img.dispose();
        imgBG.dispose();
    }
}
