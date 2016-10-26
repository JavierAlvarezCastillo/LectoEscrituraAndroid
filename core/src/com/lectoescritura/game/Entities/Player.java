package com.lectoescritura.game.Entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 pos;
    private float velocity;
    private Sprite sprite;


    public Player() {
        sprite = new Sprite(new Texture(Gdx.files.internal("img/character/knight_ne.png")));
        velocity = 5f;
        pos = new Vector2(1,1);
    }

    public float getVelocity() {
        return velocity;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPos() {
        return pos;
    }
}