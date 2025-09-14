
package com.mygdx.ost;

import static com.mygdx.ost.OstGame.SCR_HEIGHT;
import static com.mygdx.ost.OstGame.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class Tank {
    private float x, y;
    private float vx, vy;
    private float size;
    private int hp;
    private boolean isEnemy;


    public Tank() {
        respawn();
    }

    void move() {
        x += vx;
        y += vy;
        size = (SCR_HEIGHT-y*2)/15;
        if (isEnemy) {
            if (x < 0 - 16 * size) {
                respawn();
            }
        }
        else {
            if(x > SCR_WIDTH + 16 * size) {
                respawn();
            }
        }
    }

    void respawn() {
        hp = 2;
        isEnemy = MathUtils.random(3) != 1;
        if (isEnemy) {
            x = SCR_WIDTH * MathUtils.random(1f, 2f);
            y = MathUtils.random(SCR_HEIGHT * 3 / 13, SCR_HEIGHT * 5.5f / 13);
            vy = MathUtils.random(-0.15f, 0f);
            vx = MathUtils.random(-4f, -2f);
        }
        else {
            x = 0 - MathUtils.random(1f, 2f);
            y = MathUtils.random(SCR_HEIGHT * 3 / 13, SCR_HEIGHT * 5.5f / 13);
            vy = MathUtils.random(-0.15f, 0f);
            vx = MathUtils.random(2f, 4f);
        }
    }

    boolean hit(float touchX, float touchY, int damage) {
        if (isEnemy) {
            if ((x<touchX & touchX<x+16*size)&(y<touchY & touchY<y+9*size)) {
                hp-=damage;
                return true;
            }
        }
        else {
            if ((x>touchX & touchX>x-16*size)&(y<touchY & touchY<y+9*size)) {
                hp-=damage;
                return true;
            }
        }
        return false;
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

    public int getHp() {
        return hp;
    }
    public boolean isEnemy() {
        return isEnemy;
    }
}