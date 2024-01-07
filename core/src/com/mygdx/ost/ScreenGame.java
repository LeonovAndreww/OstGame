package com.mygdx.ost;

import static com.mygdx.ost.OstGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
public class ScreenGame implements Screen {
    OstGame game;

    SpriteBatch batch;
    OrthographicCamera camera;
    Vector3 touch;
    BitmapFont font;
    BitmapFont fontUiR, fontUiG;

    Texture imgBG;
    Texture imgTankSov, imgTankGer;


    MyButton btnMenu;
    Tank[] tanks = new Tank[3];

    long timeShot;
    int timeReload = 3000; // индивидуально для каждого снаряда надо бы!
    boolean isReloading = false;
    String strUiReload = "Ready!";

    int score;

    public ScreenGame(OstGame game){
        this.game = game;
        batch = game.batch;
        camera = game.camera;
        touch = game.touch;
        font = game.font;
        fontUiR = game.fontUiR;
        fontUiG = game.fontUiG;

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

            if(!isReloading) {
                for (int i = 0; i < tanks.length; i++) {
                    if (tanks[i].hit(touch.x, touch.y)) {
                        if (tanks[i].isEnemy()) score++;
                        else score--;
                        System.out.println(score);
                        tanks[i].respawn();
                        touch.set(-1024, -720, 0); // "resets" coordinates
                    }
                }
                timeShot = TimeUtils.millis();
                isReloading = true;
            }
            else {
                System.out.println("Reloading!");
            }
        }

        // события
        for (int i = 0; i < tanks.length; i++) {
            tanks[i].move();
        }

        if (isReloading) {
            if (TimeUtils.millis() - timeShot > timeReload) {
                isReloading = false;
            }
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

        if (isReloading) {
            strUiReload = "Reloading";
            glyphLayout.setText(font, strUiReload);
            fontUiR.draw(batch, strUiReload, SCR_WIDTH * 27 / 28 - glyphLayout.width / 2, SCR_HEIGHT * 69 / 70);
        }
        else {
            strUiReload = "Charged";
            glyphLayout.setText(font, strUiReload);
            fontUiG.draw(batch, strUiReload, SCR_WIDTH * 27 / 28 - glyphLayout.width / 2, SCR_HEIGHT * 69 / 70);
        }
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
