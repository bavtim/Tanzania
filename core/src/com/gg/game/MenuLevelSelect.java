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


public class MenuLevelSelect extends MyGdxGame implements Screen {
    //cоздание переменных
    public static MyGdxGame menu;
    private Stage stage;
    private Sprite MenuScreen;
    private Sprite backgroundsprite;
    private Sprite listsprite;
    private Sprite headersprite;

    //инициализация переменных
    public MenuLevelSelect(MyGdxGame menuenuLevelSelect) {

        menu = menuenuLevelSelect;
        stage = new Stage(MyGdxGame.viewport, MyGdxGame.batch);
        btn();
        MenuScreen = new Sprite(new Texture("Menu/menu.jpg"));
        backgroundsprite = new Sprite(new Texture("level_select/bg.png"));
        listsprite = new Sprite(new Texture("level_select/table2.png"));
        headersprite = new Sprite(new Texture("level_select/header.png"));
        headersprite.setSize(Gdx.graphics.getHeight() * 0.8f * 0.9f, Gdx.graphics.getHeight() * 0.3f);
        backgroundsprite.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 0.8f);
        listsprite.setSize(Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getHeight() * 0.8f * 0.9f);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundsprite.setPosition(Gdx.graphics.getWidth() / 2f - backgroundsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f - backgroundsprite.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        listsprite.setPosition(Gdx.graphics.getWidth() / 2f - listsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f- listsprite.getHeight() / 2f - Gdx.graphics.getHeight() / 10f);
        headersprite.setPosition(Gdx.graphics.getWidth() / 2f - headersprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f + headersprite.getHeight() / 2f);
        MenuScreen.setColor(0.5f, 0.5f, 0.5f, 1);

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    //отрисовка всего и вся
    @Override
    public void render(float delta) {


        super.render();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (prefs.getBoolean("debugmode", false)) {
            stage.setDebugAll(true);
        } else {
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
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

    //создание сцены с разметкой и кнопками
    private void btn() {
        Table btn = new Table();
        btn.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 0.8f);
        btn.setPosition(Gdx.graphics.getWidth() / 2f - btn.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - btn.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        //создание кнопок
        final Image exit = new Image(new Texture("rating/close_2.png"));
        final Image btn1 = new Image(new Texture("level_select/table1.png"));
        final Image btn2 = new Image(new Texture("level_select/table.png"));
        final Image btn3 = new Image(new Texture("level_select/table.png"));
        final Image btn4 = new Image(new Texture("level_select/table.png"));
        final Image btn5 = new Image(new Texture("level_select/table.png"));
        final Image btn6 = new Image(new Texture("level_select/table.png"));
        final Image btn7 = new Image(new Texture("level_select/table.png"));
        final Image btn8 = new Image(new Texture("level_select/table.png"));
        final Image btn9 = new Image(new Texture("level_select/table.png"));

        btn1.setSize(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f);
        btn2.setSize(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f);
        btn3.setSize(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f);
        btn4.setSize(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f);
        btn5.setSize(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f);
        btn6.setSize(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f);
        btn7.setSize(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f);
        btn8.setSize(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f);
        btn9.setSize(Gdx.graphics.getHeight() / 6f, Gdx.graphics.getHeight() / 6f);
        //прослушка кнопок
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.tab2.play(prefs.getInteger("volume", 10) / 10f);
                menu.setScreen(new Levelboot(menu));

                super.clicked(event, x, y);
            }
        });
        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.denied.play(prefs.getInteger("volume", 10) / 10f);

                super.clicked(event, x, y);
            }
        });
        btn3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.denied.play(prefs.getInteger("volume", 10) / 10f);

                super.clicked(event, x, y);
            }
        });
        btn4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.denied.play(prefs.getInteger("volume", 10) / 10f);

                super.clicked(event, x, y);
            }
        });
        btn5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.denied.play(prefs.getInteger("volume", 10) / 10f);

                super.clicked(event, x, y);
            }
        });
        btn6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.denied.play(prefs.getInteger("volume", 10) / 10f);

                super.clicked(event, x, y);
            }
        });
        btn7.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.denied.play(prefs.getInteger("volume", 10) / 10f);

                super.clicked(event, x, y);
            }
        });
        btn8.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.denied.play(prefs.getInteger("volume", 10) / 10f);

                super.clicked(event, x, y);
            }
        });
        btn9.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.denied.play(prefs.getInteger("volume", 10) / 10f);

                super.clicked(event, x, y);
            }
        });
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (prefs.getBoolean("sound"))
                    menu.tab1.play(prefs.getInteger("volume", 10) / 10f);
                menu.setScreen(ScreenMenu);
                super.clicked(event, x, y);
            }
        });
        //создание разметки
        btn.defaults().expand();
        btn.add(exit).size(Gdx.graphics.getHeight() / 8f, Gdx.graphics.getHeight() / 8f).right().top().colspan(3);
        btn.row();
        btn.add(btn1).size(btn1.getWidth(), btn2.getHeight()).height(Gdx.graphics.getHeight() / 6f).pad(10).right();
        btn.add(btn2).size(btn1.getWidth(), btn2.getHeight()).height(Gdx.graphics.getHeight() / 6f).pad(10);
        btn.add(btn3).size(btn1.getWidth(), btn2.getHeight()).height(Gdx.graphics.getHeight() / 6f).pad(10).left();
        btn.row();
        btn.add(btn4).size(btn1.getWidth(), btn2.getHeight()).height(Gdx.graphics.getHeight() / 6f).pad(10).right();
        btn.add(btn5).size(btn1.getWidth(), btn2.getHeight()).height(Gdx.graphics.getHeight() / 6f).pad(10);
        btn.add(btn6).size(btn1.getWidth(), btn2.getHeight()).height(Gdx.graphics.getHeight() / 6f).pad(10).left();
        btn.row();
        btn.add(btn7).size(btn1.getWidth(), btn2.getHeight()).height(Gdx.graphics.getHeight() / 6f).pad(10).right();
        btn.add(btn8).size(btn1.getWidth(), btn2.getHeight()).height(Gdx.graphics.getHeight() / 6f).pad(10);
        btn.add(btn9).size(btn1.getWidth(), btn2.getHeight()).height(Gdx.graphics.getHeight() / 6f).pad(10).left();
        btn.row();
        btn.add().height(Gdx.graphics.getHeight() / 6f);
        btn.add();
        stage.addActor(btn);


    }


}
