package com.lectoescritura.game.Entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 pos;
    private float velocity;
    private int pos_x;
    private int pos_y;

    // NE
    private Texture knight_ne;
    private TextureRegion[] knight_ne_frames;
    private Animation knight_ne_animation;
    private int FRAME_COLS = 8;
    private int FRAME_ROWS = 1;

    public Player(int pos_x, int pos_y) {

        // NE animation
        // TODO: Hay que controlar hacia donde mira el personaje para mostrar la animacion correspondiente
        knight_ne = new  Texture(Gdx.files.internal("img/character/knight_se.png"));
        knight_ne.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest); // pick test
        TextureRegion[][] tmp = TextureRegion.split(knight_ne, knight_ne.getWidth() /
                FRAME_COLS, knight_ne.getHeight() / FRAME_ROWS);
        knight_ne_frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                knight_ne_frames[index++] = tmp[i][j];
            }
        }
        knight_ne_animation = new Animation(0.025f, knight_ne_frames);

        velocity = 5f;
        pos = new Vector2((25.5f * pos_x) + (pos_y * 25.5f), (13f * pos_x) - (pos_y * 13f));
        this.pos_x = pos_x;
        this.pos_y = pos_y;
    }

    public Animation getKnight_ne_animation() {
        return knight_ne_animation;
    }

    public float getVelocity() {
        return velocity;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(int x, int y) {
        pos_x = x;
        pos_y = y;
        pos.x = (25.5f * x) + (y * 25.5f);
        pos.y = (13f * x) - (y * 13f);
    }

    public boolean esContigua(int x, int y) {
        int abs_x = Math.abs(x - pos_x);
        int abs_y = Math.abs(y - pos_y);

        return (abs_x == 1 && abs_y == 0) || (abs_y == 1 && abs_x == 0);
    }
}