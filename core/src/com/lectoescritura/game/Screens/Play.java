package com.lectoescritura.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.lectoescritura.game.Const.Const;
import com.lectoescritura.game.Entities.Player;

public class Play extends Game implements GestureDetector.GestureListener {

    private OrthographicCamera cam;
    private float currentZoom;
    private SpriteBatch batch;
    private Player player;
    private MapManager mapManager;

    public static final float PAN_RATE = (float) 0.25; // speed to move the camera
    public static int TILE_WIDTH = 128;
    public static int TILE_HEIGHT = 64;



    @Override
    public void create () {
        batch = new SpriteBatch();
        player = new Player();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Const.VIEWPORT_WIDTH, Const.VIEWPORT_HEIGHT);
//        cam.near = 1;
//        cam.far = 1000;
        currentZoom = cam.zoom;
        mapManager = new MapManager();

        Gdx.input.setInputProcessor(new GestureDetector(this));
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0); // o 0,0,0,1;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector2 pos = player.getPos();

        Vector3 temp_coord = new Vector3(pos.x,pos.y,0);
        Vector3 coords = cam.project(temp_coord);

        int x =(int) coords.x;
        int y =(int) coords.y;

        mapManager.render();

        batch.begin();
        batch.draw(player.getSprite(), x, y);
        batch.end();

        cam.update();
    }

    @Override
    public void resize(int width, int height) {
//        cam.viewportHeight = height;
//        cam.viewportWidth = width;
//        cam.update();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector3 temp_coord = new Vector3(x,y,0);
        Vector3 coords = cam.unproject(temp_coord);

        int xa =(int) coords.x;
        int ya =(int) coords.y;

        System.out.println("X:" + x + " , Y:" + y + "Screen coordinates translated to world coordinates: "
                + "Xa: " + xa + " Ya: " + ya);
        // 21, 39

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

    public class MapManager {

        private TiledMap map;
        private IsometricTiledMapRenderer renderer;

        public MapManager() {

            map = new TmxMapLoader().load("maps/map.tmx");

            renderer = new IsometricTiledMapRenderer(map, (float) 0.4);
        }

        public void render(){
            renderer.render();
            renderer.setView(cam);
        }
    }
}
