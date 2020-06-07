package com.gg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gg.game.utils.Constants;

// меню дебага
public class About implements Screen {
    private Stage stage;//сцена
    private MyGdxGame menu;
    private Sprite MenuScreen;//cпрайты для отрисовки меню
    private Sprite backgroundsprite;//cпрайты для отрисовки меню
    private Sprite listsprite;//cпрайты для отрисовки меню
    private Sprite headersprite;//cпрайты для отрисовки меню

    //Инициализация всех переменных
    About(MyGdxGame about) {
        this.menu = about;
        stage = new Stage(MyGdxGame.viewport, MyGdxGame.batch);
        MenuScreen = new Sprite(new Texture("Menu/menu.jpg"));
        backgroundsprite = new Sprite(new Texture("about/bg.png"));
        listsprite = new Sprite(new Texture("about/table.png"));
        headersprite = new Sprite(new Texture("about/header.png"));
        headersprite.setSize(Gdx.graphics.getHeight() * 0.8f * 0.9f, Gdx.graphics.getHeight() * 0.3f);
        backgroundsprite.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 0.8f);
        listsprite.setSize(Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getHeight() * 0.8f * 0.9f);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundsprite.setPosition(Gdx.graphics.getWidth() / 2f - backgroundsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f - backgroundsprite.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        listsprite.setPosition(Gdx.graphics.getWidth() / 2f - listsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f - listsprite.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        headersprite.setPosition(Gdx.graphics.getWidth() / 2f - headersprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f + headersprite.getHeight() / 2);
        MenuScreen.setColor(0.5f, 0.5f, 0.5f, 1);
        btn();

    }

    @Override
    public void show() {
        //режим обработки косаний
        Gdx.input.setInputProcessor(stage);
    }

    //отрисовка всего и вся
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
        menu.font.draw(MyGdxGame.batch, "Версия : ", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + listsprite.getHeight() / 6, 0, 0, false);
        menu.font.draw(MyGdxGame.batch, Constants.Version, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + listsprite.getHeight() / 6, 0, (int) (-listsprite.getWidth() / 2), false);
        menu.font.draw(MyGdxGame.batch, "Debug mode : ", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - listsprite.getHeight() / 12, 0, 0, false);
        menu.font.draw(MyGdxGame.batch, "Контакт  : ", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - listsprite.getHeight() / 3, 0, 0, false);

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
        stage.dispose();
    }

    //создание разметки , сцены и кнопок
    private void btn() {
        //создание стола
        Table btn = new Table();
        btn.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 0.8f);
        btn.setPosition(Gdx.graphics.getWidth()/ 2f - btn.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - btn.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        //инициализация кнопок
        final Image exit = new Image(new Texture("about/close_2.png"));

        final Image debugmode;
        if (MyGdxGame.prefs.getBoolean("debugmode", false))
            debugmode = new Image(new Texture("settings/96.png"));
        else
            debugmode = new Image(new Texture("settings/95.png"));
        final Image vk = new Image(new Texture("about/vk.jpg"));
        debugmode.setSize(Gdx.graphics.getHeight() / 10f * 3, Gdx.graphics.getHeight() / 10f);
        //обработчик нажатий
        vk.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab2.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                Gdx.net.openURI(Constants.Vkurl);
                super.clicked(event, x, y);
            }
        });
        //обработчик нажатий
        debugmode.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab2.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                if (MyGdxGame.prefs.getBoolean("debugmode", false)) {
                    MyGdxGame.prefs.putBoolean("debugmode", false);
                    debugmode.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/95.png"))));
                } else {
                    MyGdxGame.prefs.putBoolean("debugmode", true);
                    debugmode.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/96.png"))));
                }
                MyGdxGame.prefs.flush();
                super.clicked(event, x, y);
            }
        });
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                MyGdxGame.prefs.flush();
                menu.setScreen(MyGdxGame.ScreenMenu);
                super.clicked(event, x, y);
            }
        });
        //создание разметки на сцене
        btn.defaults().expand();
        btn.add(exit).size(Gdx.graphics.getHeight() / 8f, Gdx.graphics.getHeight() / 8f).right().top().colspan(2);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight() / 6f);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight() / 6f).width(listsprite.getWidth() / 2);
        btn.add(debugmode).size(debugmode.getWidth(), debugmode.getHeight()).left();
        btn.row();
        btn.add().height(Gdx.graphics.getHeight() / 6f).width(listsprite.getWidth() / 2);
        btn.add(vk).size(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f).left();
        btn.row();
        btn.add().height(Gdx.graphics.getHeight() / 6f);
        stage.addActor(btn);


    }
}
