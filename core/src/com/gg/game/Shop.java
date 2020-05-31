package com.gg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Shop implements Screen {
    private Stage stage;
    private MyGdxGame menu;
    private boolean exitflag = true;
    private String strtemp;
    private GlyphLayout glyphLayout;
    private Sprite MenuScreen;
    private Sprite backgroundsprite;
    private Sprite listsprite;
    private Sprite headersprite;

    public Shop(MyGdxGame shop) {
        this.menu = shop;
        stage = new Stage(MyGdxGame.viewport, MyGdxGame.batch);
        btn();
        MenuScreen = new Sprite(new Texture("Menu/menu.jpg"));
        backgroundsprite = new Sprite(new Texture("shop/bg.png"));
        listsprite = new Sprite(new Texture("shop/table.png"));
        headersprite = new Sprite(new Texture("shop/header.png"));
        headersprite.setSize(Gdx.graphics.getHeight() * 0.8f * 0.9f, Gdx.graphics.getHeight() * 0.3f);
        backgroundsprite.setSize(Gdx.graphics.getHeight() * 1.3f, Gdx.graphics.getHeight() * 0.8f);
        listsprite.setSize(Gdx.graphics.getHeight() * 0.9f * 1.3f, Gdx.graphics.getHeight() * 0.8f * 0.9f);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundsprite.setPosition(Gdx.graphics.getWidth() / 2f - backgroundsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f - backgroundsprite.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        listsprite.setPosition(Gdx.graphics.getWidth() / 2f - listsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f - listsprite.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        headersprite.setPosition(Gdx.graphics.getWidth() / 2f - headersprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f + headersprite.getHeight() / 2);
        MenuScreen.setColor(0.5f, 0.5f, 0.5f, 1);
        glyphLayout = new GlyphLayout();

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
        menu.font.draw(MyGdxGame.batch, glyphLayout, Gdx.graphics.getWidth() / 2f - glyphLayout.width / 2, Gdx.graphics.getWidth() / 7f);
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

    public void btn() {
        Table btn = new Table();
        btn.setSize(Gdx.graphics.getHeight() * 1.3f, Gdx.graphics.getHeight() * 0.8f);
        btn.setPosition(Gdx.graphics.getWidth() / 2f- btn.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - btn.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        final Image exit = new Image(new Texture("shop/close_2.png"));
        final Image shop1 = new Image(new Texture("shop/1.png"));
        final Image shop2 = new Image(new Texture("shop/2.png"));
        final Image shop3 = new Image(new Texture("shop/3.png"));
        final Image shop1btn = new Image(new Texture("shop/btn.png"));
        final Image shop2btn = new Image(new Texture("shop/btn.png"));
        final Image shop3btn = new Image(new Texture("shop/btn.png"));
        shop1btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab2.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                shop1.setVisible(false);
                shop2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/1.png"))));
                shop3.setVisible(false);
                shop1btn.setVisible(false);
                shop2btn.setVisible(false);
                shop3btn.setVisible(false);
                exitflag = false;
                strtemp = "Вы отключили рекламу";
                glyphLayout.setText(menu.font, strtemp);
                super.clicked(event, x, y);
            }
        });
        shop2btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab2.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                shop1.setVisible(false);
                shop2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/2.png"))));
                shop3.setVisible(false);
                shop1btn.setVisible(false);
                shop2btn.setVisible(false);
                shop3btn.setVisible(false);
                exitflag = false;
                MyGdxGame.prefs.putInteger("Countstar", MyGdxGame.prefs.getInteger("Countstar", 0) + 1);

                MyGdxGame.prefs.getInteger("Countstar", 0);
                strtemp = "Вы получили 1 звезду!\nВаш счет равен:" + MyGdxGame.prefs.getInteger("Countstar", 0);
                MyGdxGame.prefs.flush();
                glyphLayout.setText(menu.font, strtemp);
                super.clicked(event, x, y);
            }
        });
        shop3btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab2.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                shop1.setVisible(false);
                shop2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/3.png"))));
                shop3.setVisible(false);
                shop1btn.setVisible(false);
                shop2btn.setVisible(false);
                shop3btn.setVisible(false);
                exitflag = false;
                MyGdxGame.prefs.putInteger("Countstar", MyGdxGame.prefs.getInteger("Countstar", 0) + 5);

                MyGdxGame.prefs.getInteger("Countstar", 0);
                strtemp = "Вы получили 5 звезд!\nВаш счет равен:" + MyGdxGame.prefs.getInteger("Countstar", 0);
                glyphLayout.setText(menu.font, strtemp);
                super.clicked(event, x, y);
            }
        });
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (MyGdxGame.prefs.getBoolean("sound"))
                    menu.tab1.play(MyGdxGame.prefs.getInteger("volume", 10) / 10f);
                if (exitflag) {
                    menu.setScreen(MyGdxGame.ScreenMenu);

                } else {
                    exitflag = true;
                    shop1.setVisible(true);
                    shop3.setVisible(true);
                    shop1.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/1.png"))));
                    shop2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/2.png"))));
                    shop3.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/3.png"))));
                    shop1btn.setVisible(true);
                    shop2btn.setVisible(true);
                    shop3btn.setVisible(true);
                    strtemp = "";
                    glyphLayout.setText(menu.font, strtemp);
                }

                super.clicked(event, x, y);
            }
        });
        btn.defaults().expand();
        btn.add(exit).size(Gdx.graphics.getHeight() / 8f, Gdx.graphics.getHeight() / 8f).right().top().colspan(3);
        btn.row();
        btn.add(shop1).height(Gdx.graphics.getHeight() / 3f).pad(15).padLeft(100);
        btn.add(shop2).height(Gdx.graphics.getHeight() / 3f).pad(15);
        btn.add(shop3).height(Gdx.graphics.getHeight() / 3f).pad(15).padRight(100);
        btn.row();
        btn.add(shop1btn).height(Gdx.graphics.getHeight() / 11f).pad(15).padLeft(100);
        btn.add(shop2btn).height(Gdx.graphics.getHeight() / 11f).pad(15);
        btn.add(shop3btn).height(Gdx.graphics.getHeight() / 11f).pad(15).padRight(100);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight() / 10f);
        stage.addActor(btn);
    }


}
