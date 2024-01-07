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

public class ScreenSettings implements Screen {
    OstGame game;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font;

    Texture imgBG;
    Texture img;

    MyButton btnSound;
    MyButton btnBack;

    public ScreenSettings(OstGame game){
        this.game = game;
        batch = game.batch;
        camera = game.camera;
        touch = game.touch;
        font = game.font;

        GlyphLayout glyphLayout = new GlyphLayout();
        imgBG = new Texture("bg_settings.png");
        img = new Texture("badlogic.jpg");

        btnBack = new MyButton("Back to menu", font,SCR_WIDTH/2, SCR_HEIGHT*2/10);
        btnSound = new MyButton("Sound", font, SCR_WIDTH/2, SCR_HEIGHT*3/10);
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
            if (btnBack.hit(touch.x, touch.y)) {
                game.setScreen(game.screenMenu);
            }
            if (btnSound.hit(touch.x, touch.y)) {
                System.out.println("sound touched");
            }
        }
        // события


        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        glyphLayout.setText(font, "S E T T I N G S");
        font.draw(batch, "S E T T I N G S", SCR_WIDTH/2-glyphLayout.width/2, SCR_HEIGHT*9/10);
        batch.draw(img, 1000, 0);
        btnBack.font.draw(batch, btnBack.text, btnBack.x, btnBack.y);
        btnSound.font.draw(batch, btnSound.text, btnSound.x, btnSound.y);
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
