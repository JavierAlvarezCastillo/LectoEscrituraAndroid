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
import com.lectoescritura.game.Const.Const;
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

    public Levels(AndroidUtils androidUtils, String map, int pos_x, int pos_y, String minigame_id)
    {
        atlas = new TextureAtlas("atlas.txt");
        skin = new Skin(Gdx.files.internal("skin.json"), atlas);

        this.utils = androidUtils;
        this.map = map;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        utils = androidUtils;
        this.minigame_id = minigame_id;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Const.V_WIDTH, Const.V_HEIGHT, camera);
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
        mainTable.right();
        Drawable background =  new Image(new Texture((Gdx.files.internal("fondo_niveles.png")))).getDrawable();
        mainTable.setBackground(background);

        //Create buttons
        TextButton a = new TextButton("1", skin, "button_up");
        TextButton b = new TextButton("2", skin, "button_up");
        TextButton c = new TextButton("3", skin, "button_up");
        TextButton d = new TextButton("4", skin, "button_up");
        TextButton e = new TextButton("5", skin, "button_up");
        TextButton volver = new TextButton("Volver", skin, "button_up");

        //Add listeners to buttons
        a.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new Play(utils, map, 0, 0, minigame_id));
            }
        });
        volver.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen( new MainMenu(utils, map, pos_x, pos_y, minigame_id));
            }
        });

        //Add buttons to table
        mainTable.add(a);
        mainTable.add(b);
        mainTable.row();
        mainTable.add(c);
        mainTable.add(d);
        mainTable.row();
        mainTable.add(e);

        mainTable.padRight(20.f);

        //Add table to stage
        stage.addActor(mainTable);
        volver.setPosition(390.0f, 70.0f);
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
