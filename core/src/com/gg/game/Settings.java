package com.gg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class Settings implements Screen {

    private static MyGdxGame menu;
    private Stage stage;
    private Sprite MenuScreen;
    private Sprite backgroundsprite;
    private Sprite listsprite;
    private Sprite headersprite;
    private Texture backgroundmenu;
    private Texture background;
    private Texture list;
    private Texture header;

    public Settings(final MyGdxGame settings) {
        menu = settings;
        stage = new Stage(MyGdxGame.viewport, MyGdxGame.batch);
        backgroundmenu = new Texture("Menu/menu.jpg");
        background = new Texture("settings/bg.png");
        list = new Texture("settings/table.png");
        header = new Texture("settings/92.png");


        buttons();

        MenuScreen = new Sprite(backgroundmenu);
        backgroundsprite = new Sprite(background);
        listsprite = new Sprite(list);
        headersprite = new Sprite(header);


        headersprite.setSize(Gdx.graphics.getHeight() * 0.8f * 0.9f, Gdx.graphics.getHeight() * 0.3f);
        backgroundsprite.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 0.8f);
        listsprite.setSize(Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getHeight() * 0.8f * 0.9f);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundsprite.setPosition(Gdx.graphics.getWidth() / 2f - backgroundsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f - backgroundsprite.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        listsprite.setPosition(Gdx.graphics.getWidth() / 2f - listsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f - listsprite.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        headersprite.setPosition(Gdx.graphics.getWidth() / 2f - headersprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f + headersprite.getHeight() / 2);
        MenuScreen.setColor(0.5f, 0.5f, 0.5f, 1);


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
        backgroundsprite.draw(MyGdxGame.batch);
        listsprite.draw(MyGdxGame.batch);
        headersprite.draw(MyGdxGame.batch);
        menu.font.draw(MyGdxGame.batch, "Музыка : ", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - listsprite.getHeight() / 10 * 5, 0, 0, false);
        menu.font.draw(MyGdxGame.batch, "Звуки : ", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - listsprite.getHeight() / 11 * 7, 0, 0, false);
        menu.font.draw(MyGdxGame.batch, "Вибрация : ", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - listsprite.getHeight() / 9 * 7, 0, 0, false);
        menu.font.draw(MyGdxGame.batch, "Уведомления : ", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - listsprite.getHeight() / 10 * 9, 0, 0, false);
        menu.font.draw(MyGdxGame.batch, "Громкость", Gdx.graphics.getWidth() / 2f - listsprite.getWidth() / 2, listsprite.getHeight() / 20 * 7, listsprite.getHeight() / 10 * 8, 0, false);
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
        menu.dispose();
        backgroundmenu.dispose();
        background.dispose();
        list.dispose();
        header.dispose();
        stage.dispose();

    }

    private void buttons() {

        Table btn = new Table();
        btn.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 0.8f);
        btn.setPosition(Gdx.graphics.getWidth() / 2f - btn.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - btn.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);

        int pad = 5;
        int elements = 7;

        float height = ((btn.getHeight() - 50) / elements) - pad * 2;
        int secondCollumnWidth = (int) (btn.getWidth() / 4);

        final Image music;

        if (MyGdxGame.prefs.getBoolean("music", true))
            music = new Image(new Texture("settings/96.png"));
        else
            music = new Image(new Texture("settings/95.png"));

        final Image sound;
        if (MyGdxGame.prefs.getBoolean("sound", true))
            sound = new Image(new Texture("settings/96.png"));
        else
            sound = new Image(new Texture("settings/95.png"));

        final Image vibration;
        if (MyGdxGame.prefs.getBoolean("vibration", true))
            vibration = new Image(new Texture("settings/96.png"));
        else
            vibration = new Image(new Texture("settings/95.png"));

        final Image notification;
        if (MyGdxGame.prefs.getBoolean("notification", true))
            notification = new Image(new Texture("settings/96.png"));
        else
            notification = new Image(new Texture("settings/95.png"));

        final Image exit;
        exit = new Image(new Texture("settings/close_2.png"));

        Slider.SliderStyle style;
        style = new Slider.SliderStyle(new TextureRegionDrawable(new TextureRegion(new Texture("settings/93.png"))), new TextureRegionDrawable(new TextureRegion(new Texture("settings/dot.png"))));
        final Slider musicSlider;

        style.background.setMinHeight(20);
        style.knob.setMinHeight(Gdx.graphics.getHeight() / 10f);
        style.knob.setMinWidth(Gdx.graphics.getHeight() / 10f);

        musicSlider = new Slider(0, 10, 1, false, style);
        musicSlider.setSize(150, 1);
        musicSlider.setValue(MyGdxGame.prefs.getInteger("volume", 10));
        music.setSize(Gdx.graphics.getHeight() / 10f * 3, Gdx.graphics.getHeight() / 10f);
        sound.setSize(Gdx.graphics.getHeight() / 10f * 3, Gdx.graphics.getHeight() / 10f);
        vibration.setSize(Gdx.graphics.getHeight() / 10f * 3, Gdx.graphics.getHeight() / 10f);
        notification.setSize(Gdx.graphics.getHeight() / 10f * 3, Gdx.graphics.getHeight() / 10f);
        exit.setSize(Gdx.graphics.getHeight() / 10f, Gdx.graphics.getHeight() / 10f);

        music.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab2.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                if (MyGdxGame.prefs.getBoolean("music", true)) {
                    MyGdxGame.prefs.putBoolean("music", false);
                    music.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/95.png"))));
                    menu.music.pause();
                } else {
                    MyGdxGame.prefs.putBoolean("music", true);
                    music.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/96.png"))));
                    menu.music.play();
                }

                MyGdxGame.prefs.flush();
                super.clicked(event, x, y);

            }
        });
        sound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab2.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                if (MyGdxGame.prefs.getBoolean("sound", true)) {
                    MyGdxGame.prefs.putBoolean("sound", false);
                    sound.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/95.png"))));

                } else {
                    MyGdxGame.prefs.putBoolean("sound", true);
                    sound.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/96.png"))));

                }
                MyGdxGame.prefs.flush();
                super.clicked(event, x, y);

            }
        });
//        vibration.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if(menu.prefs.getBoolean("sound"))
//                    menu.tab2.play(menu.prefs.getInteger("volume",10)/10f);
//                if(menu.prefs.getBoolean("vibration", true)){
//                    menu.prefs.putBoolean("vibration", false);
//                    vibration.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/95.png"))));
//                }else {
//                    menu.prefs.putBoolean("vibration", true);
//                    vibration.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/96.png"))));
//                }
//                menu.prefs.flush();
//                super.clicked(event, x, y);
//
//            }
//        });
//        notification.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if(menu.prefs.getBoolean("sound"))
//                    menu.tab2.play(menu.prefs.getInteger("volume",10)/10f);
//                if(menu.prefs.getBoolean("notification", true)){
//                    menu.prefs.putBoolean("notification", false);
//                    notification.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/95.png"))));
//                }else {
//                    menu.prefs.putBoolean("notification", true);
//                    notification.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/96.png"))));
//                }
//                super.clicked(event, x, y);
//                menu.prefs.flush();
//            }
//        });
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                menu.setScreen(MyGdxGame.ScreenMenu);
                super.clicked(event, x, y);
            }
        });
        musicSlider.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume", 10) / 100f);
                MyGdxGame.prefs.putInteger("volume", (int) musicSlider.getValue());
                menu.music.setVolume(MyGdxGame.prefs.getInteger("volume", 10) / 100f);
                MyGdxGame.prefs.flush();
            }
        });
        btn.defaults().expand();

        btn.add(exit).size(Gdx.graphics.getHeight() / 8f, Gdx.graphics.getHeight() / 8f).right().colspan(2);
        btn.row();
        btn.add().width(secondCollumnWidth).height(height);
        btn.add(music).size(music.getWidth(), music.getHeight()).right().padRight(Gdx.graphics.getWidth() / 16f);
        btn.row();
        btn.add().width(secondCollumnWidth).height(height);
        btn.add(sound).size(music.getWidth(), music.getHeight()).right().padRight(Gdx.graphics.getWidth() / 16f);
        btn.row();
        btn.add().width(secondCollumnWidth).height(height);
        btn.add(vibration).size(music.getWidth(), music.getHeight()).right().padRight(Gdx.graphics.getWidth() / 16f);
        btn.row();
        btn.add().width(secondCollumnWidth).height(height);
        btn.add(notification).size(music.getWidth(), music.getHeight()).right().padRight(Gdx.graphics.getWidth() / 16f);
        btn.row();
        btn.add().height(height);
        btn.row();
        btn.add(musicSlider).size(btn.getWidth() / 4 * 3, height).pad(pad).center().colspan(2).height(height);
        btn.row();
        btn.add().height(height);
        stage.addActor(btn);

    }
}
