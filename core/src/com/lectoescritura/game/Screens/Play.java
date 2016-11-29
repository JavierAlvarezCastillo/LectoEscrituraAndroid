package com.lectoescritura.game.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lectoescritura.game.Bars.TextProgressBar;
import com.lectoescritura.game.Const.Const;
import com.lectoescritura.game.Entities.Player;
import com.lectoescritura.game.Interfaces.AndroidUtils;
import com.lectoescritura.game.Interfaces.Conector;

import java.io.Serializable;
import java.util.ArrayList;

public class Play implements Screen {

    private AndroidUtils utils;
    private String map;
    private String minigame_id;
    private int pos_x;
    private int pos_y;

    private ArrayList intentos;
    private Conector conector = new Conector() {
        @Override
        public void setIntentos(ArrayList inten) {
            intentos = inten;
        }
    };

    private OrthographicCamera cam;
    private float currentZoom;
    private SpriteBatch spriteBatch;
    private Player player;

    public static final float PAN_RATE = (float) 0.25; // speed to move the camera
    public static final float ANIMATION_RATE = (float) 0.1;
    public static final int TILE_WIDTH = 50; //50 128
    public static final int TILE_HEIGHT = 25; //31  25// 64
    private TextureRegion currentFrame;
    private float stateTime = 0f;

    private float tileWidth = 50.0f;

    public TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    GestureDetector gesture;
    InputMultiplexer myInputMultiplexer;

    TiledMapTileLayer grid_layer;

    TextProgressBar textProgressBar;

    public Play (AndroidUtils androidUtils, String map, int pos_x, int pos_y, String minigame_id) {
        this.map = map;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        utils = androidUtils;
        this.minigame_id = minigame_id;
    }

//    @Override
//    public void create () {
//        // pick
//        GL20 gl = Gdx.graphics.getGL20();
//        gl.glEnable(GL20.GL_BLEND);
//        gl.glEnable(GL20.GL_TEXTURE_2D);
//        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        Drawable background =  new Image(new Texture((Gdx.files.internal("img/buttons/EmptyBar.png")))).getDrawable();
//        Drawable knob = new Image(new Texture((Gdx.files.internal("img/buttons/EmptyBar.png")))).getDrawable();
//
//        com.lectoescritura.game.Bars.ProgressBar.ProgressBarStyle f = new com.lectoescritura.game.Bars.ProgressBar.ProgressBarStyle(background, knob);
//        textProgressBar = new TextProgressBar("Prueba", 0f, 100f, 5f, false, f, new Label.LabelStyle(new BitmapFont(false), new Color(444444)));
//        textProgressBar.setSize(500, 500);
//
//        spriteBatch = new SpriteBatch();
//        player = new Player();
//        cam = new OrthographicCamera();
//        cam.setToOrtho(false, Const.VIEWPORT_WIDTH, Const.VIEWPORT_HEIGHT);
//
//        currentZoom = cam.zoom;
//
//        gesture = new GestureDetector(new MyGestureListener());
//        myInputMultiplexer = new InputMultiplexer();
//        float unitScale = (float) 0.4;
//        tiledMap = new TmxMapLoader().load("maps/" + map);
//        tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap, unitScale);
//        grid_layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
//
//        Gdx.input.setInputProcessor(gesture);
//
//    }

//    @Override
//    public void render () {
////        Gdx.gl.glClearColor(0, 0, 0, 0); // o 0,0,0,1;
////        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
////
////        Vector2 pos = player.getPos();
////
////        Vector3 temp_coord = new Vector3(pos.x, pos.y,0);
////        Vector3 coords = cam.project(temp_coord);
////
////        int x =(int) coords.x;
////        int y =(int) coords.y;
////
////        tiledMapRenderer.setView(cam);
////        tiledMapRenderer.render();
////
////        spriteBatch.setProjectionMatrix(cam.combined);
////        spriteBatch.begin();
////        stateTime += Gdx.graphics.getDeltaTime();
////        currentFrame = player.getKnight_ne_animation().getKeyFrame(stateTime * ANIMATION_RATE, true);
////        spriteBatch.draw(currentFrame, x + 80, y +190); // +75, +180
////        //textProgressBar.draw(spriteBatch, 3f);
////        spriteBatch.end();
//
//        cam.update();
//
//        Gdx.gl.glClearColor(0, 0, 0, 0); // o 0,0,0,1;
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        Vector2 pos = player.getPos();
//
////        Vector3 temp_coord = new Vector3(pos.x, pos.y,0);
////        Vector3 coords = cam.project(temp_coord);
////
////        int x =(int) coords.x;
////        int y =(int) coords.y;
//
//        tiledMapRenderer.setView(cam);
//        tiledMapRenderer.render();
//
//        spriteBatch.setProjectionMatrix(cam.combined);
//        spriteBatch.begin();
//        stateTime += Gdx.graphics.getDeltaTime();
//        currentFrame = player.getKnight_ne_animation().getKeyFrame(stateTime * ANIMATION_RATE, true);
//        //Sprite sprite = (Sprite) player.getKnight_ne_animation().getKeyFrame(stateTime);
//        //spriteBatch.draw(currentFrame, pos.x, pos.y); // +80, +190/ +75, +180
//        spriteBatch.draw(currentFrame, pos.x + 5, pos.y + 32, 0, 0, 100, 100, 0.45f, 0.45f, 0);
//        //textProgressBar.draw(spriteBatch, 3f);
//        spriteBatch.end();
//
//    }

    @Override
    public void show() {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glEnable(GL20.GL_TEXTURE_2D);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Drawable background =  new Image(new Texture((Gdx.files.internal("img/buttons/EmptyBar.png")))).getDrawable();
        Drawable knob = new Image(new Texture((Gdx.files.internal("img/buttons/EmptyBar.png")))).getDrawable();

        com.lectoescritura.game.Bars.ProgressBar.ProgressBarStyle f = new com.lectoescritura.game.Bars.ProgressBar.ProgressBarStyle(background, knob);
        textProgressBar = new TextProgressBar("Prueba", 0f, 100f, 5f, false, f, new Label.LabelStyle(new BitmapFont(false), new Color(444444)));
        textProgressBar.setSize(500, 500);

        spriteBatch = new SpriteBatch();
        player = new Player();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Const.VIEWPORT_WIDTH, Const.VIEWPORT_HEIGHT);

        currentZoom = cam.zoom;

        gesture = new GestureDetector(new MyGestureListener());
        myInputMultiplexer = new InputMultiplexer();
        float unitScale = (float) 0.4;
        tiledMap = new TmxMapLoader().load("maps/" + map);
        tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap, unitScale);
        grid_layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        Gdx.input.setInputProcessor(gesture);
    }

    @Override
    public void render(float delta) {
        cam.update();

        Gdx.gl.glClearColor(0, 0, 0, 0); // o 0,0,0,1;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector2 pos = player.getPos();

//        Vector3 temp_coord = new Vector3(pos.x, pos.y,0);
//        Vector3 coords = cam.project(temp_coord);
//
//        int x =(int) coords.x;
//        int y =(int) coords.y;

        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = player.getKnight_ne_animation().getKeyFrame(stateTime * ANIMATION_RATE, true);
        //Sprite sprite = (Sprite) player.getKnight_ne_animation().getKeyFrame(stateTime);
        //spriteBatch.draw(currentFrame, pos.x, pos.y); // +80, +190/ +75, +180
        spriteBatch.draw(currentFrame, pos.x + 5, pos.y + 32, 0, 0, 100, 100, 0.45f, 0.45f, 0);
        //textProgressBar.draw(spriteBatch, 3f);
        spriteBatch.end();

    }

    @Override
    public void resize(int width, int height) {
        //the cam will show 10 tiles
        /*float camWidth = tileWidth * 10.0f;

        cam.position.set(camWidth / 2.0f, 0, 0);
        cam.update();*/
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
        GL20 gl = Gdx.graphics.getGL20();
        gl.glDisable(GL20.GL_BLEND);
        gl.glDisable(GL20.GL_TEXTURE_2D);
    }


//    public class MapManager {
//
//        private TiledMap map;
//        private IsometricTiledMapRenderer renderer;
//
//        public MapManager() {
//
//            map = new TmxMapLoader().load("maps/map.tmx");
//
//                renderer = new IsometricTiledMapRenderer(map, (float) 0.4);
//        }
//
//        public void render(){
//            renderer.render();
//            renderer.setView(cam);
//        }
//    }


    public class MyGestureListener implements GestureDetector.GestureListener {

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {
            checkTiles(x, y);
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
            x = -deltaX * currentZoom * PAN_RATE;
            y = deltaY * currentZoom * PAN_RATE;

            // TODO: Hay que cambiarlo para que funcione en todos los mapas
            if (cam.position.x + x > 120 && cam.position.y + y < 120 && cam.position.y + y > -90 && cam.position.x + x < 490) {
                cam.translate(x, y);
                cam.update();
            }

            return false;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            currentZoom = cam.zoom;
            return false;
        }

        @Override
        public boolean zoom(float initialDistance, float distance) {
            // TODO: Comprobar si funciona en todos los mapas
            if (((initialDistance / distance) * currentZoom) < 1.6 && ((initialDistance / distance) * currentZoom) > 0.85) {
                cam.zoom = (initialDistance / distance) * currentZoom;
                cam.update();
            }

            return false;
        }

        @Override
        public boolean pinch(Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
            return false;
        }

        @Override
        public void pinchStop() {

        }

    }

    void checkTiles(float x, float y) {
        Vector3 temp_coord = new Vector3(x, y, 0);
        Vector3  coords = cam.unproject(temp_coord);

        x = coords.x;
        y = coords.y - 37.f;

        int xt = (int) (x / TILE_WIDTH + y / TILE_HEIGHT);
        int yt = (int) Math.abs(y / TILE_HEIGHT - x / TILE_WIDTH);
        Tile tile = new Tile(yt, xt);
        tile.setCell(grid_layer.getCell(tile.x, tile.y));

        if (tile.getCell() != null) {
            if (tile.getCell().getTile().getProperties().containsKey("obstacle"))
                utils.toast("¡No puedes pasar!");
            else {
                if (tile.getCell().getTile().getId() == 2) { // libros
                    utils.pruebaEducativa(minigame_id, xt, yt);
                }
                else if (tile.getCell().getTile().getId() == 4) {  // lampara
                    utils.toast("¡Luz de la habitación aumentada!");
                }
                else if (tile.getCell().getTile().getId() == 5)   // final
                    utils.toast("¡Has llegado al final!");
                player.setPos(xt, yt);
            }

        }


        System.out.println("(" + xt + "," + yt + ")");
    }

    public class Tile {
        private int x, y;
        private TiledMapTileLayer.Cell cell;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public TiledMapTileLayer.Cell getCell() {
            return cell;
        }

        public void setCell(TiledMapTileLayer.Cell cell) {
            this.cell = cell;
        }
    }


}