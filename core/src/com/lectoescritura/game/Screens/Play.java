package com.lectoescritura.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.lectoescritura.game.Const.Const;

public class Play extends ApplicationAdapter implements GestureDetector.GestureListener {

    private OrthographicCamera cam;
    private float currentZoom;
    //private Player player;

    private TiledMap tileMap;
    private IsometricTiledMapRenderer tileMapRenderer;

    public static final float PAN_RATE = (float) 0.35; // speed to move the camera

    @Override
    public void create () {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Const.VIEWPORT_WIDTH, Const.VIEWPORT_HEIGHT);
//        cam.near = 1;
//        cam.far = 1000;
        currentZoom = cam.zoom;

        //player = new Player();

        tileMap = new TmxMapLoader().load("maps/map2.tmx");
        tileMapRenderer = new IsometricTiledMapRenderer(tileMap, (float) 0.4);

        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // updates
        //player.update(delta);

        // rendering
        tileMapRenderer.setView(cam);
        tileMapRenderer.render();

        //player.render(sb);

        cam.update();
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        // clamp para limitar la camara¿
        cam.translate(-deltaX * currentZoom * PAN_RATE,deltaY * currentZoom * PAN_RATE);
        cam.update();
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        currentZoom = cam.zoom;
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        cam.zoom = (initialDistance / distance) * currentZoom;
        cam.update();
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
