package com.lectoescritura.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lectoescritura.game.Interfaces.AndroidUtils;

/**
 * Created by Javier on 28/11/2016.
 */
public class MainMenu implements Screen {
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;

    private AndroidUtils utils;
    private String map;
    private String minigame_id;
    private int pos_x;
    private int pos_y;
    private int puntos, energia, estrellas;

    public MainMenu(AndroidUtils androidUtils, String map, int pos_x, int pos_y, String minigame_id, int puntos, int energia, int estrellas)
    {
        atlas = new TextureAtlas("atlas.txt");
        skin = new Skin(Gdx.files.internal("skin.json"), atlas);

        this.utils = androidUtils;
        this.map = map;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.puntos = puntos;
        this.energia = energia;
        this.estrellas = estrellas;
        utils = androidUtils;
        this.minigame_id = minigame_id;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 480, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {
        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.center();
        Drawable background =  new Image(new Texture(Gdx.files.internal("fondo.png"))).getDrawable();
        mainTable.setBackground(background);

        //Create buttons
        TextButton playButton = new TextButton("Inicio", skin, "button_up");
        playButton.getLabel().setFontScale(2.0f);
        TextButton minijuegosButton = new TextButton("Minijuegos", skin, "button_up");
        minijuegosButton.getLabel().setFontScale(2.0f);
        TextButton exitButton = new TextButton("Salir", skin, "button_up");
        exitButton.getLabel().setFontScale(2.0f);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new Play(utils, map, pos_x, pos_y, minigame_id, puntos, energia, estrellas));
            }
        });
        minijuegosButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new Levels(utils, map, pos_x, pos_y, minigame_id, puntos, energia, estrellas));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Add buttons to table
        mainTable.add(playButton).width(playButton.getWidth() * 1.15f).height(playButton.getHeight() * 1.5f);
        mainTable.row();
        mainTable.add(minijuegosButton).width(minijuegosButton.getWidth() * 1.15f).height(minijuegosButton.getHeight() * 1.5f);
        mainTable.row();
        mainTable.add(exitButton).width(exitButton.getWidth() * 1.15f).height(exitButton.getHeight() * 1.5f);

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        skin.dispose();
        atlas.dispose();
    }
}
