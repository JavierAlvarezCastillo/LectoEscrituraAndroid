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
public class Levels implements Screen {
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

    public Levels(AndroidUtils androidUtils, String map, int pos_x, int pos_y, String minigame_id, int puntos, int energia, int estrellas)
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
        mainTable.center(); //right
        Drawable background =  new Image(new Texture((Gdx.files.internal("fondo_niveles.png")))).getDrawable();
        mainTable.setBackground(background);

        //Create buttons
        TextButton a = new TextButton("1", skin, "button_up");
        a.getLabel().setFontScale(2.0f);
        TextButton b = new TextButton("2", skin, "button_up");
        b.getLabel().setFontScale(2.0f);
        TextButton c = new TextButton("3", skin, "button_up");
        c.getLabel().setFontScale(2.0f);
        TextButton d = new TextButton("4", skin, "button_up");
        d.getLabel().setFontScale(2.0f);
        TextButton e = new TextButton("5", skin, "button_up");
        e.getLabel().setFontScale(2.0f);
        TextButton f = new TextButton("6", skin, "button_up");
        f.getLabel().setFontScale(2.0f);
        TextButton volver = new TextButton("Volver", skin, "button_up");
        volver.getLabel().setFontScale(2.0f);

        //Add listeners to buttons
        a.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new Play(utils, "1.tmx", 0, 0, "1", 0, 10, 0));
            }
        });

        b.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new Play(utils, "2.tmx", 0, 0, "2", 0, 10, 0));
            }
        });

        c.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new Play(utils, "3.tmx", 0, 0, "3", 0, 10, 0));
            }
        });

        d.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new Play(utils, "4.tmx", 0, 0, "4", 0, 10, 0));
            }
        });

        f.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new Play(utils, "6.tmx", 0, 0, "6", 0, 10, 0));
            }
        });

        e.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new Play(utils, "5.tmx", 0, 0, "5", 0, 10, 0));
            }
        });

        volver.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new MainMenu(utils, map, pos_x, pos_y, minigame_id, puntos, energia, estrellas));
            }
        });

        //Add buttons to table
        mainTable.add(a).width(a.getWidth() * 1.15f).height(a.getHeight() * 1.5f);
        mainTable.add(b).width(b.getWidth() * 1.15f).height(b.getHeight() * 1.5f);
        mainTable.row();
        mainTable.add(c).width(c.getWidth() * 1.15f).height(c.getHeight() * 1.5f);
        mainTable.add(d).width(d.getWidth() * 1.15f).height(d.getHeight() * 1.5f);
        mainTable.row();
        mainTable.add(e).width(e.getWidth() * 1.15f).height(e.getHeight() * 1.5f);
        mainTable.add(f).width(e.getWidth() * 1.15f).height(f.getHeight() * 1.5f);

        //mainTable.padRight(20.f);

        //Add table to stage
        stage.addActor(mainTable);
        volver.setPosition(240.0f, 0.0f);
        volver.setWidth(volver.getWidth() * 1.15f);
        volver.setHeight(volver.getHeight() * 1.5f);
        stage.addActor(volver);
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
