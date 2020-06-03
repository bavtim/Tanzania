package com.gg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Top implements Screen {
    private Stage stage;
    private MyGdxGame menu;
    private Sprite MenuScreen;
    private Sprite backgroundsprite;
    private Sprite listsprite;
    private Sprite headersprite;
    public Top( MyGdxGame  top) {
        this.menu = top;
        stage = new Stage(MyGdxGame.viewport, MyGdxGame.batch);


        btn();
        MenuScreen = new Sprite(new Texture("Menu/menu.jpg"));
        backgroundsprite = new Sprite(new Texture("rating/bg.png"));
        listsprite = new Sprite(new Texture("rating/table.png"));
        headersprite = new Sprite(new Texture("rating/header.png"));
        headersprite.setSize(Gdx.graphics.getHeight()*0.8f*0.9f,Gdx.graphics.getHeight()*0.3f);
        backgroundsprite.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight()*0.8f);
        listsprite.setSize(Gdx.graphics.getHeight()*0.9f, Gdx.graphics.getHeight()*0.8f*0.9f);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundsprite.setPosition(Gdx.graphics.getWidth()/2f-backgroundsprite.getWidth()/2,Gdx.graphics.getHeight()/2f-backgroundsprite.getHeight()/2-Gdx.graphics.getHeight()/10f);
        listsprite.setPosition(Gdx.graphics.getWidth()/2f-listsprite.getWidth()/2,Gdx.graphics.getHeight()/2f-listsprite.getHeight()/2-Gdx.graphics.getHeight()/10f);
        headersprite.setPosition(Gdx.graphics.getWidth()/2f-headersprite.getWidth()/2,Gdx.graphics.getHeight()/2f+headersprite.getHeight()/2);
        MenuScreen.setColor(0.5f,0.5f,0.5f,1);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(MyGdxGame.prefs.getBoolean("debugmode", false)){
            stage.setDebugAll(true);
        }else {
            stage.setDebugAll(false);
        }
        MyGdxGame.batch.begin();

        MenuScreen.draw(MyGdxGame.batch);
        backgroundsprite.draw(MyGdxGame.batch);
        listsprite.draw(MyGdxGame.batch);
        headersprite.draw(MyGdxGame.batch);
        MyGdxGame.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        MyGdxGame.camera.setToOrtho(false,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        MyGdxGame.viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

   private void  btn(){
        Table btn = new Table();
        btn.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight()*0.8f);
        btn.setPosition(Gdx.graphics.getWidth()/2f-btn.getWidth()/2,
                Gdx.graphics.getHeight()/2f-btn.getHeight()/2-Gdx.graphics.getHeight()/10f);
        final Image exit = new Image(new Texture("rating/close_2.png"));
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume",10)/10f);
                menu.setScreen(MyGdxGame.ScreenMenu);
                super.clicked(event, x, y);
            }
        });
        btn.defaults().expand();
        btn.add(exit).size(Gdx.graphics.getHeight()/8f,Gdx.graphics.getHeight()/8f).right().top().colspan(2);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/6f);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/6f);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/6f);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/6f);
        stage.addActor(btn);


    }
}
