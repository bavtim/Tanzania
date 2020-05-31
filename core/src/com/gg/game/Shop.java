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
import com.gg.game.utils.Temp;

public class Shop implements Screen {
    Stage stage;
    MyGdxGame menu;
    private Sprite MenuScreen;
    private Sprite backgroundsprite;
    private Sprite listsprite;
    private Sprite headersprite;
    boolean exitflag=true;
    String strtemp;
    GlyphLayout glyphLayout;
    public Shop( MyGdxGame  shop) {
        this.menu = shop;
        stage = new Stage(menu.viewport, menu.batch);

        btn();
        MenuScreen = new Sprite(new Texture("Menu/menu.jpg"));
        backgroundsprite = new Sprite(new Texture("shop/bg.png"));
        listsprite = new Sprite(new Texture("shop/table.png"));
        headersprite = new Sprite(new Texture("shop/header.png"));
        headersprite.setSize(Gdx.graphics.getHeight()*0.8f*0.9f,Gdx.graphics.getHeight()*0.3f);
        backgroundsprite.setSize(Gdx.graphics.getHeight()*1.3f, Gdx.graphics.getHeight()*0.8f);
        listsprite.setSize(Gdx.graphics.getHeight()*0.9f*1.3f, Gdx.graphics.getHeight()*0.8f*0.9f);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundsprite.setPosition(Gdx.graphics.getWidth()/2-backgroundsprite.getWidth()/2,Gdx.graphics.getHeight()/2-backgroundsprite.getHeight()/2-Gdx.graphics.getHeight()/10);
        listsprite.setPosition(Gdx.graphics.getWidth()/2-listsprite.getWidth()/2,Gdx.graphics.getHeight()/2-listsprite.getHeight()/2-Gdx.graphics.getHeight()/10);
        headersprite.setPosition(Gdx.graphics.getWidth()/2-headersprite.getWidth()/2,Gdx.graphics.getHeight()/2+headersprite.getHeight()/2);
        MenuScreen.setColor(0.5f,0.5f,0.5f,1);
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
        if(menu.prefs.getBoolean("debugmode", false)){
            stage.setDebugAll(true);
        }else {
            stage.setDebugAll(false);
        }
        menu.batch.begin();

        MenuScreen.draw( menu.batch);
        backgroundsprite.draw( menu.batch);
        listsprite.draw( menu.batch);
        headersprite.draw( menu.batch);
        menu.font.draw(menu.batch,glyphLayout,Gdx.graphics.getWidth()/2-glyphLayout.width/2,Gdx.graphics.getWidth()/7);
        menu.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        menu.camera.setToOrtho(false,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        menu.viewport.update(width, height);
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
        menu.dispose();
    }

    public void  btn(){
        Table btn = new Table();
        btn.setSize(Gdx.graphics.getHeight()*1.3f, Gdx.graphics.getHeight()*0.8f);
        btn.setPosition(Gdx.graphics.getWidth()/2-btn.getWidth()/2,
                Gdx.graphics.getHeight()/2-btn.getHeight()/2-Gdx.graphics.getHeight()/10);
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
                if(menu.prefs.getBoolean("sound"))
                    menu.tab2.play(menu.prefs.getInteger("volume",10)/10f);
                shop1.setVisible(false);
                shop2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/1.png"))));
                shop3.setVisible(false);
                shop1btn.setVisible(false);
                shop2btn.setVisible(false);
                shop3btn.setVisible(false);
                exitflag= false;
                strtemp="Вы отключили рекламу";
                glyphLayout.setText(menu.font,strtemp);
                super.clicked(event, x, y);
            }
        });
        shop2btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.tab2.play(menu.prefs.getInteger("volume",10)/10f);
                shop1.setVisible(false);
                shop2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/2.png"))));
                shop3.setVisible(false);
                shop1btn.setVisible(false);
                shop2btn.setVisible(false);
                shop3btn.setVisible(false);
                exitflag= false;
                strtemp="Вы получили 1 звезду!\nВаш счет равен:"+ ++Temp.Countstar;
                glyphLayout.setText(menu.font,strtemp);
                super.clicked(event, x, y);
            }
        });
        shop3btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.tab2.play(menu.prefs.getInteger("volume",10)/10f);
                shop1.setVisible(false);
                shop2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/3.png"))));
                shop3.setVisible(false);
                shop1btn.setVisible(false);
                shop2btn.setVisible(false);
                shop3btn.setVisible(false);
                exitflag= false;
                Temp.Countstar+=5;
                strtemp="Вы получили 5 звезд!\nВаш счет равен:"+ Temp.Countstar;
                glyphLayout.setText(menu.font,strtemp);
                super.clicked(event, x, y);
            }
        });
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.tab1.play(menu.prefs.getInteger("volume",10)/10f);
                if(exitflag){
                    menu.setScreen(MyGdxGame.ScreenMenu);

                }else {
                    exitflag=true;
                    shop1.setVisible(true);
                    shop3.setVisible(true);
                    shop1.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/1.png"))));
                    shop2.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/2.png"))));
                    shop3.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("shop/3.png"))));
                    shop1btn.setVisible(true);
                    shop2btn.setVisible(true);
                    shop3btn.setVisible(true);
                    strtemp="";
                    glyphLayout.setText(menu.font,strtemp);
                }

                super.clicked(event, x, y);
            }
        });
        btn.defaults().expand();
        btn.add(exit).size(Gdx.graphics.getHeight()/8,Gdx.graphics.getHeight()/8).right().top().colspan(3);
        btn.row();
        btn.add(shop1).height(Gdx.graphics.getHeight()/3).pad(15).padLeft(100);
        btn.add(shop2).height(Gdx.graphics.getHeight()/3).pad(15);
        btn.add(shop3).height(Gdx.graphics.getHeight()/3).pad(15).padRight(100);
        btn.row();
        btn.add(shop1btn).height(Gdx.graphics.getHeight()/11).pad(15).padLeft(100);
        btn.add(shop2btn).height(Gdx.graphics.getHeight()/11).pad(15);
        btn.add(shop3btn).height(Gdx.graphics.getHeight()/11).pad(15).padRight(100);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/10);
        stage.addActor(btn);
    }


}
