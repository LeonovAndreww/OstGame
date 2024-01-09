package com.mygdx.ost;

import static com.mygdx.ost.OstGame.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
    Texture imgBtnHe, imgBtnHeDis, imgBtnAphe, imgBtnApheDis, imgBtnApheLock;
    Sound sndShot;
    Sound sndEmpty;
    Sound sndHit0;
    Sound sndHit1;
    Sound sndHit2;
    Sound sndAmbWar;


    MyButton btnMenu;
    MyButton btnHe, btnAphe;

    Tank[] tanks = new Tank[3];

    long timeShot;
    int timeReload = 6000; // индивидуально для каждого снаряда надо бы!
    boolean isReloading = false;
    String strUiReload = "Ready!";
    int shellDamage = 1;

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
        imgBtnHe = new Texture("he.png");
        imgBtnHeDis = new Texture("he_disabled.png");
        imgBtnAphe = new Texture("aphe.png");
        imgBtnApheDis = new Texture("aphe_disabled.png");
        imgBtnApheLock = new Texture("aphe_locked.png");

        sndShot = Gdx.audio.newSound(Gdx.files.internal("shot.mp3"));
        sndEmpty = Gdx.audio.newSound(Gdx.files.internal("empty.mp3"));
        sndHit0 = Gdx.audio.newSound(Gdx.files.internal("hit_0.mp3"));
        sndHit1 = Gdx.audio.newSound(Gdx.files.internal("hit_1.mp3"));
        sndHit2 = Gdx.audio.newSound(Gdx.files.internal("hit_2.mp3"));
        sndAmbWar = Gdx.audio.newSound(Gdx.files.internal("warfare_ambient.mp3"));

        btnMenu = new MyButton("Menu", font, SCR_WIDTH*(1-19/20f), SCR_HEIGHT*69/70);
        btnHe = new MyButton(SCR_WIDTH*5/12, 0, 128);
        btnAphe = new MyButton(SCR_WIDTH*7/12, 0, 128);

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
            else if (btnHe.hit(touch.x, touch.y)) {
                shellDamage = 1;
                System.out.println("he clicked");
            }
            else if (btnAphe.hit(touch.x, touch.y) && game.score >= 20) {
                shellDamage = 2;
                System.out.println("aphe clicked");
            }

            else if(!isReloading) {
                for (int i = 0; i < tanks.length; i++) {
                    if (tanks[i].hit(touch.x, touch.y, shellDamage)) {
                        if (tanks[i].getHp()==0) {
                            if (tanks[i].isEnemy()) game.score+=5;
                            else game.score-=10;
                            tanks[i].respawn();
                            touch.set(-1024, -720, 0); // "resets" coordinates
                        }
                        else {
                            if (tanks[i].isEnemy()) game.score++;
                            else game.score--;
                        }
                        if (game.isSoundOn) {
                            Sound[] sndHits = new Sound[]{sndHit0, sndHit1, sndHit2};
                            sndHits[MathUtils.random.nextInt(sndHits.length)].play();
                        }
                    }
                }
                timeShot = TimeUtils.millis();
                if (game.isSoundOn) sndShot.play();
                isReloading = true;
            }
            else {
                if (game.isSoundOn) sndEmpty.play();
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

        if (game.isSoundOn) if (MathUtils.random.nextInt(333)==1) {
            sndAmbWar.play();
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
                batch.draw(imgTankSov, tanks[i].getX(), tanks[i].getY(), -16*tanks[i].getSize(), 9*tanks[i].getSize());
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
        font.draw(batch, "Score "+game.score, SCR_WIDTH/2-textWidth("Score: "+game.score, font)/2, SCR_HEIGHT*69/70);
        if (shellDamage == 1) {
            batch.draw(imgBtnHe, btnHe.x, btnHe.y, btnHe.width, btnHe.height);
            if (game.score >= 20) {
                batch.draw(imgBtnApheDis, btnAphe.x, btnAphe.y, btnAphe.width, btnAphe.height);
            }
            else {
                batch.draw(imgBtnApheLock, btnAphe.x, btnAphe.y, btnAphe.width, btnAphe.height);
            }
        }
        else {
            batch.draw(imgBtnHeDis, btnHe.x, btnHe.y, btnHe.width, btnHe.height);
            batch.draw(imgBtnAphe, btnAphe.x, btnAphe.y, btnAphe.width, btnAphe.height);
        }
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
        imgBtnHe.dispose();
        imgBtnHeDis.dispose();
        imgBtnAphe.dispose();
        imgBtnApheDis.dispose();
        imgBtnApheLock.dispose();
    }

    float textWidth(String text, BitmapFont font){
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return layout.width;
    }
}
