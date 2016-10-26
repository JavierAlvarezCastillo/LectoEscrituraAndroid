package com.lectoescritura.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Muevecamara extends ApplicationAdapter {

    public TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;

    GestureDetector gesture;
    InputMultiplexer myInputMultiplexer;

    //float w = Gdx.graphics.getWidth();
    //float h = Gdx.graphics.getHeight();

    public static final float PAN_RATE = (float) 0.01;
    private static final float ZOOM_SPEED = (float) 0.009;
    int columns, rows;
    TiledMapTileLayer grid_layer;

    @Override
    public void create() {

        gesture = new GestureDetector(new MyGestureListener());
        myInputMultiplexer = new InputMultiplexer();
        float unitScale = 1 / 32f;


        camera = new OrthographicCamera();
        camera.setToOrtho(true, 33, 21);

        tiledMap = new TmxMapLoader().load("maps/map2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
        grid_layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        Stage stage = new TiledMapStage(tiledMap);
        myInputMultiplexer.addProcessor(gesture);
        myInputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(myInputMultiplexer);
    }

    @Override
    public void render() {

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        camera.update();
    }

    public class TiledMapActor extends Actor {

        private TiledMapTileLayer.Cell cell;

        public TiledMapActor(TiledMap tileMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell) {
            tiledMap = tileMap;
            grid_layer = tiledLayer;
            this.cell = cell;
        }

    }

    public class TiledMapClickListener extends ClickListener {

        private TiledMapActor actor;

        public TiledMapClickListener(TiledMapActor actor) {
            this.actor = actor;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {

            int a = (int) actor.getX();
            int b = (int) actor.getY();

            System.out.println(actor.cell + " has been clicked.");
            System.out.println("x =  " + a + "y = " + b);
        }
    }

    public class TiledMapStage extends Stage {

        private TiledMap tiledMap;

        public TiledMapStage(TiledMap tiledMap) {
            this.tiledMap = tiledMap;

            for (MapLayer layer : tiledMap.getLayers()) {

                TiledMapTileLayer tiledLayer = (TiledMapTileLayer) layer;
                createActorsForLayer(tiledLayer);
            }
        }

        private void createActorsForLayer(TiledMapTileLayer tiledLayer) {
            for (int x = 0; x <= tiledLayer.getWidth(); x++) {
                for (int y = 0; y < tiledLayer.getHeight(); y++) {
                    TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                    TiledMapActor actor = new TiledMapActor(tiledMap, tiledLayer, cell);
                    actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(),
                            tiledLayer.getTileHeight());
                    addActor(actor);
                    EventListener eventListener = new TiledMapClickListener(actor);
                    actor.addListener(eventListener);
                }
            }
        }
    }

    public void resize(float width, float height) {

    }

    public class MyGestureListener implements GestureDetector.GestureListener {

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

            float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
            float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

            camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 33 - effectiveViewportWidth / 2f);
            camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 21 - effectiveViewportHeight / 2f);
            if (camera.zoom < 1) {

                camera.translate(-deltaX * PAN_RATE, -deltaY * PAN_RATE, 0);
                camera.update();
            }
            return false;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            //Gdx.app.log("Text", "panstop");
            return false;
        }


        @Override
        public boolean zoom(float originalDistance, float currentDistance) {

            float ratio = originalDistance / currentDistance;

            camera.zoom += ZOOM_SPEED * ratio;

            if (camera.zoom < 0.3) {
                camera.zoom = (float) 0.3;
            } else if (camera.zoom > 1) {
                camera.zoom = 1;
            }
            System.out.println(camera.zoom);
            return false;
        }

        @Override
        public boolean pinch(Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
            camera.zoom -= .01;
            camera.update();
            return false;
        }

        @Override
        public void pinchStop() {

        }

    }
}