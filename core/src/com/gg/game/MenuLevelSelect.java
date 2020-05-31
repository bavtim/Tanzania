package com.gg.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MenuLevelSelect extends MyGdxGame implements Screen {
    private Stage stage;
    public static MyGdxGame menu;
    public static SpriteBatch batch;
    private Sprite MenuScreen;
    private Sprite backgroundsprite;
    private Sprite listsprite;
    private Sprite headersprite;

    FreeTypeFontGenerator generator ;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    BitmapFont font;


    public FitViewport viewport;


    public MenuLevelSelect( MyGdxGame  menuenuLevelSelect) {
        this.menu = menuenuLevelSelect;
        stage = new Stage(menu.viewport, menu.batch);
        btn();
        MenuScreen = new Sprite(new Texture("Menu/menu.jpg"));
        backgroundsprite = new Sprite(new Texture("level_select/bg.png"));
        listsprite = new Sprite(new Texture("level_select/table2.png"));
        headersprite = new Sprite(new Texture("level_select/header.png"));
        headersprite.setSize(Gdx.graphics.getHeight()*0.8f*0.9f,Gdx.graphics.getHeight()*0.3f);
        backgroundsprite.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight()*0.8f);
        listsprite.setSize(Gdx.graphics.getHeight()*0.9f, Gdx.graphics.getHeight()*0.8f*0.9f);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundsprite.setPosition(Gdx.graphics.getWidth()/2-backgroundsprite.getWidth()/2,Gdx.graphics.getHeight()/2-backgroundsprite.getHeight()/2-Gdx.graphics.getHeight()/10);
        listsprite.setPosition(Gdx.graphics.getWidth()/2-listsprite.getWidth()/2,Gdx.graphics.getHeight()/2-listsprite.getHeight()/2-Gdx.graphics.getHeight()/10);
        headersprite.setPosition(Gdx.graphics.getWidth()/2-headersprite.getWidth()/2,Gdx.graphics.getHeight()/2+headersprite.getHeight()/2);
        MenuScreen.setColor(0.5f,0.5f,0.5f,1);

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
//        if(menu.prefs.getBoolean("firsttime",true));
//        menu.setScreen(menu.ScreenGame);
    }

    @Override
    public void render(float delta) {


        super.render();
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
        menu.dispose();
        stage.dispose();
    }

    public void  btn(){
        Table btn = new Table();
        btn.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight()*0.8f);
        btn.setPosition(Gdx.graphics.getWidth()/2-btn.getWidth()/2,
                Gdx.graphics.getHeight()/2-btn.getHeight()/2-Gdx.graphics.getHeight()/10);
        final Image exit = new Image(new Texture("rating/close_2.png"));
        final Image btn1= new Image(new Texture("level_select/table1.png"));
        final Image btn2= new Image(new Texture("level_select/table.png"));
        final Image btn3= new Image(new Texture("level_select/table.png"));
        final Image btn4= new Image(new Texture("level_select/table.png"));
        final Image btn5= new Image(new Texture("level_select/table.png"));
        final Image btn6= new Image(new Texture("level_select/table.png"));
        final Image btn7= new Image(new Texture("level_select/table.png"));
        final Image btn8= new Image(new Texture("level_select/table.png"));
        final Image btn9= new Image(new Texture("level_select/table.png"));

        btn1.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        btn2.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        btn3.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        btn4.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        btn5.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        btn6.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        btn7.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        btn8.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        btn9.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.tab2.play(menu.prefs.getInteger("volume",10)/10f);
                menu.setScreen(new MainGame(menu));

                super.clicked(event, x, y);
            }
        });
        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.denied.play(menu.prefs.getInteger("volume",10)/10f);

                super.clicked(event, x, y);
            }
        });
        btn3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.denied.play(menu.prefs.getInteger("volume",10)/10f);

                super.clicked(event, x, y);
            }
        });
        btn4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.denied.play(menu.prefs.getInteger("volume",10)/10f);

                super.clicked(event, x, y);
            }
        });
        btn5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.denied.play(menu.prefs.getInteger("volume",10)/10f);

                super.clicked(event, x, y);
            }
        });
        btn6.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.denied.play(menu.prefs.getInteger("volume",10)/10f);

                super.clicked(event, x, y);
            }
        });
        btn7.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.denied.play(menu.prefs.getInteger("volume",10)/10f);

                super.clicked(event, x, y);
            }
        });
        btn8.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.denied.play(menu.prefs.getInteger("volume",10)/10f);

                super.clicked(event, x, y);
            }
        });
        btn9.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.denied.play(menu.prefs.getInteger("volume",10)/10f);

                super.clicked(event, x, y);
            }
        });
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.tab1.play(menu.prefs.getInteger("volume",10)/10f);
                menu.setScreen(ScreenMenu);
                super.clicked(event, x, y);
            }
        });
        btn.defaults().expand();
        btn.add(exit).size(Gdx.graphics.getHeight()/8,Gdx.graphics.getHeight()/8).right().top().colspan(3);
        btn.row();
        btn.add(btn1).size(btn1.getWidth(),btn2.getHeight()).height(Gdx.graphics.getHeight()/6).pad(10).right();
        btn.add(btn2).size(btn1.getWidth(),btn2.getHeight()).height(Gdx.graphics.getHeight()/6).pad(10);
        btn.add(btn3).size(btn1.getWidth(),btn2.getHeight()).height(Gdx.graphics.getHeight()/6).pad(10).left();;
        btn.row();
        btn.add(btn4).size(btn1.getWidth(),btn2.getHeight()).height(Gdx.graphics.getHeight()/6).pad(10).right();
        btn.add(btn5).size(btn1.getWidth(),btn2.getHeight()).height(Gdx.graphics.getHeight()/6).pad(10);;
        btn.add(btn6).size(btn1.getWidth(),btn2.getHeight()).height(Gdx.graphics.getHeight()/6).pad(10).left();;
        btn.row();
        btn.add(btn7).size(btn1.getWidth(),btn2.getHeight()).height(Gdx.graphics.getHeight()/6).pad(10).right();
        btn.add(btn8).size(btn1.getWidth(),btn2.getHeight()).height(Gdx.graphics.getHeight()/6).pad(10);;
        btn.add(btn9).size(btn1.getWidth(),btn2.getHeight()).height(Gdx.graphics.getHeight()/6).pad(10).left();;
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/6);
        btn.add();
        stage.addActor(btn);


    }
}
