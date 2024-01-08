package com.mygdx.ost;

import static com.mygdx.ost.OstGame.SCR_HEIGHT;
import static com.mygdx.ost.OstGame.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class Tank {
    private float x, y;
    private float vx, vy;
    private float size;

    public boolean isEnemy() {
        return isEnemy;
    }

    private boolean isEnemy;


    public Tank() {
        respawn();
    }

    void move() {
        x += vx;
        y += vy;
        size = (SCR_HEIGHT-y*2)/15;
        if (x < 0-16*size) respawn();
    }

    void respawn() {
        isEnemy = MathUtils.randomBoolean();
        x = SCR_WIDTH*MathUtils.random(1f, 2f);
        y = MathUtils.random(SCR_HEIGHT*3/13, SCR_HEIGHT*5.5f/13);
        vy = MathUtils.random(-0.15f, 0f);
        vx = MathUtils.random(-4f, -2f);
    }

    boolean hit(float touchX, float touchY) {
        return ((x<touchX & touchX<x+16*size)&(y<touchY & touchY<y+9*size));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }
}
