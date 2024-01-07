package com.mygdx.ost;

import static com.mygdx.ost.OstGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;
import java.util.Comparator;

public class ScreenGame implements Screen {
    OstGame game;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font;
    long timeShot;
    int timeReload = 200; // индивидуально для каждого снаряда надо

    Texture imgBG;
    Texture imgTankSov, imgTankGer;

    MyButton btnMenu;
    Tank[] tanks = new Tank[3];

    int score;

    public ScreenGame(OstGame game){
        this.game = game;
        batch = game.batch;
        camera = game.camera;
        touch = game.touch;
        font = game.font;

        imgBG = new Texture("bg_game.png");
        imgTankSov = new Texture("t34e.png");
        imgTankGer = new Texture("pz4j.png");

        btnMenu = new MyButton("Menu", font, SCR_WIDTH*(1-19/20f), SCR_HEIGHT*69/70);


        for (int i = 0; i < tanks.length; i++) {
            tanks[i] = new Tank();
        }
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

            if (btnMenu.hit(touch.x, touch.y)) {
                game.setScreen(game.screenMenu);
            }

            for (int i = 0; i < tanks.length; i++) {
                if (tanks[i].hit(touch.x, touch.y)) {
                    if (tanks[i].isEnemy()) score++;
                    else score--;
                    System.out.println(score);
                    tanks[i].respawn();
                    touch.set(-1024, -720, 0); // coordinates "reset"
                }
            }
        }
        touch.set(-1024, -720, 0);

        // события
        for (int i = 0; i < tanks.length; i++) {
            tanks[i].move();
        }

        // отрисовка
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(imgBG, 0, 0, SCR_WIDTH, SCR_HEIGHT);

        for (int i = 0; i < tanks.length; i++) {
            if(tanks[i].isEnemy()) {
                batch.draw(imgTankGer, tanks[i].getX(), tanks[i].getY(), 16*tanks[i].getSize(), 9*tanks[i].getSize());
            }
            else {
                batch.draw(imgTankSov, tanks[i].getX(), tanks[i].getY(), 16*tanks[i].getSize(), 9*tanks[i].getSize());
            }
        }

        glyphLayout.setText(font, "Charged");
        font.draw(batch, "Charged", SCR_WIDTH*12/13-glyphLayout.width/2, SCR_HEIGHT*69/70);
        btnMenu.font.draw(batch, btnMenu.text, btnMenu.x, btnMenu.y);
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
        imgTankGer.dispose();
        imgTankSov.dispose();
    }
}
