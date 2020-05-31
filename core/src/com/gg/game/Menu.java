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

public class Menu implements Screen {

    public static MyGdxGame menu;
    Stage stage;
    private Sprite MenuScreen;
    private Texture backgroundmenu;

    public Menu(MyGdxGame menu) {
        Menu.menu = menu;
        buttons();

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (MyGdxGame.prefs.getBoolean("debugmode", false)) {
            stage.setDebugAll(true);
        } else {
            stage.setDebugAll(false);
        }
        MyGdxGame.batch.begin();

        MenuScreen.draw(MyGdxGame.batch);

        MyGdxGame.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        MyGdxGame.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
    }

    @Override
    public void dispose() {
        backgroundmenu.dispose();
        stage.dispose();

    }

    public void buttons() {
        stage = new Stage(MyGdxGame.viewport, MyGdxGame.batch);


        backgroundmenu = new Texture("Menu/menu.jpg");
        MenuScreen = new Sprite(backgroundmenu);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        Table btn = new Table();
        Image about = new Image(new Texture("Menu/about.png"));
        Image leader = new Image(new Texture("btn/shop.png"));
        Image prize = new Image(new Texture("Menu/prize.png"));
        final Image play = new Image(new Texture("Menu/play.png"));
        Image setting = new Image(new Texture("Menu/setting.png"));
        about.setSize(Gdx.graphics.getHeight() / 5, Gdx.graphics.getHeight() / 5);
        leader.setSize(Gdx.graphics.getHeight() / 5, Gdx.graphics.getHeight() / 5);
        prize.setSize(Gdx.graphics.getHeight() / 5, Gdx.graphics.getHeight() / 5);
        play.setSize(Gdx.graphics.getHeight() / 4, Gdx.graphics.getHeight() / 4);
        setting.setSize(Gdx.graphics.getHeight() / 5, Gdx.graphics.getHeight() / 5);

        about.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                menu.setScreen(new About(menu));
                super.clicked(event, x, y);
            }
        });
        leader.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                menu.setScreen(new Shop(menu));
                super.clicked(event, x, y);
            }
        });
        prize.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                menu.setScreen(new Top(menu));
                super.clicked(event, x, y);
            }
        });
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                if (MyGdxGame.prefs.getBoolean("firsttime", true)) ;
                menu.setScreen(MyGdxGame.ScreenMenuLevelSelect);

                super.clicked(event, x, y);
            }
        });
        setting.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);

                menu.setScreen(new Settings(menu));


                super.clicked(event, x, y);
            }
        });

        btn.setFillParent(true);
        btn.add(about).padLeft(20).padTop(20).size(about.getHeight(), about.getHeight()).left();
        btn.add(setting).padRight(20).padTop(20).size(setting.getHeight(), setting.getHeight()).right();
        btn.row();
        btn.add(play).size(play.getHeight(), play.getHeight()).colspan(2).expand();
        btn.row().width(leader.getHeight());
        btn.add(leader).padLeft(20).padBottom(20).size(leader.getHeight(), leader.getHeight()).left();
        btn.add(prize).padRight(20).padBottom(20).size(prize.getHeight(), prize.getHeight()).right();
        stage.addActor(btn);
    }

}
