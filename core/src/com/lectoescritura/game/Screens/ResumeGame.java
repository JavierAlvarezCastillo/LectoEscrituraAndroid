package com.lectoescritura.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lectoescritura.game.Const.Const_core;
import com.lectoescritura.game.Entities.Player;
import com.lectoescritura.game.Interfaces.AndroidUtils;

//TODO: Buscar solucion a pasar todos los atributos y crear un nuevo objeto al resumir el juego
public class ResumeGame extends Game implements InputProcessor{

    private AndroidUtils utils;
    private String map;
    private String minigame_id;
    private int pos_x;
    private int pos_y;
    private int ultimax;
    private int ultimay;

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

    public TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    GestureDetector gesture;
    InputMultiplexer myInputMultiplexer;

    TiledMapTileLayer grid_layer;

    float camposx;
    float camposy;

    private TextureAtlas atlas;
    protected Skin skin;
    Label puntuacion_valor;
    Label energia_valor;
    Texture energia;
    private SpriteBatch textobatch;
    private int puntos, energ, estrel;

    public ResumeGame (AndroidUtils androidUtils, String map, int pos_x, int pos_y, String minigame_id, float camposx, float camposy, int puntos, int energia, int estrellas) {
        this.map = map;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.ultimax = pos_x;
        this.ultimay = pos_y;
        utils = androidUtils;
        this.minigame_id = minigame_id;
        this.camposx = camposx;
        this.camposy = camposy;
        this.puntos = puntos;
        this.energ = energia;
        this.estrel = estrellas;
    }

    @Override
    public void create() {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glEnable(GL20.GL_BLEND);
        gl.glEnable(GL20.GL_TEXTURE_2D);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        spriteBatch = new SpriteBatch();
        player = new Player(pos_x, pos_y);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Const_core.VIEWPORT_WIDTH, Const_core.VIEWPORT_HEIGHT);

        currentZoom = cam.zoom;
        Gdx.input.setCatchBackKey(true);

        if (camposx != 0.0f)
            cam.position.set(camposx, camposy , 0);

        gesture = new GestureDetector(new MyGestureListener());
        myInputMultiplexer = new InputMultiplexer();
        float unitScale = (float) 0.4;
        tiledMap = new TmxMapLoader().load("maps/" + map);
        tiledMapRenderer = new IsometricTiledMapRenderer(tiledMap, unitScale);
        grid_layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        myInputMultiplexer.addProcessor(gesture);
        myInputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(myInputMultiplexer);

        atlas = new TextureAtlas("atlas.txt");
        skin = new Skin(Gdx.files.internal("skin.json"), atlas);
        textobatch = new SpriteBatch();
        puntuacion_valor = new Label(String.valueOf(puntos), skin, "default");
        energia = new Texture(Gdx.files.internal("img/buttons/Bolt.png"));
        energia_valor = new Label(String.valueOf(energ), skin, "default");
    }

    @Override
    public void render() {
        cam.update();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector2 pos = player.getPos();

        tiledMapRenderer.setView(cam);
        tiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = player.getKnight_ne_animation().getKeyFrame(stateTime * ANIMATION_RATE, true);
        spriteBatch.draw(currentFrame, pos.x + 5, pos.y + 32, 0, 0, 100, 100, 0.45f, 0.45f, 0);
        spriteBatch.end();

        textobatch.begin();
        puntuacion_valor.setPosition(1600.0f, 1000.0f);
        puntuacion_valor.setFontScale(5);
        puntuacion_valor.setText(String.valueOf(puntos));
        puntuacion_valor.draw(textobatch, 1);
        textobatch.draw(energia, 50.0f, 960.0f, 80.0f, 80.0f);
        energia_valor.setPosition(140.0f, 1000.0f);
        energia_valor.setFontScale(5);
        energia_valor.setText(String.valueOf(energ));
        energia_valor.draw(textobatch, 3.f);
        textobatch.end();

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
    public void dispose() {
        GL20 gl = Gdx.graphics.getGL20();
        gl.glDisable(GL20.GL_BLEND);
        gl.glDisable(GL20.GL_TEXTURE_2D);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK)
            utils.salir(ultimax, ultimay, cam.position.x, cam.position.y, ultimax, ultimay, puntos, energ, estrel);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

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
            x = -deltaX * currentZoom * PAN_RATE;
            y = deltaY * currentZoom * PAN_RATE;

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


        if (tile.getCell() != null && player.esContigua(xt, yt)) {
            if (tile.getCell().getTile().getProperties().containsKey("obstacle"))
                utils.toast("¡No puedes pasar!");
            else if (energ == 0) {
                utils.pruebaAleatoria(minigame_id, map, ultimax, ultimay, cam.position.x, cam.position.y, ultimax, ultimay, puntos, energ, estrel);
            }
            else {
                energ --;
                puntos++;
                if (tile.getCell().getTile().getId() == 2) { // libros
                    utils.pruebaEducativa(minigame_id, map, xt, yt, cam.position.x, cam.position.y, ultimax, ultimay, puntos, energ, estrel);
                }
                else if (tile.getCell().getTile().getId() == 4) {  // lampara
                    utils.toast("¡Energía +1!");
                    energ++;
                }
                else if (tile.getCell().getTile().getId() == 5)   // final
                    utils.pruebaFinal(minigame_id, map, xt, yt, cam.position.x, cam.position.y, ultimax, ultimay, puntos, energ, estrel);
                player.setPos(xt, yt);
                ultimax = xt;
                ultimay = yt;
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