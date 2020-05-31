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
public class About implements Screen {
    Stage stage;
    MyGdxGame menu;
    private Sprite MenuScreen;
    private Sprite backgroundsprite;
    private Sprite listsprite;
    private Sprite headersprite;


    public About( MyGdxGame  about) {
        this.menu = about;
        stage = new Stage(menu.viewport, menu.batch);
        MenuScreen = new Sprite(new Texture("Menu/menu.jpg"));
        backgroundsprite = new Sprite(new Texture("about/bg.png"));
        listsprite = new Sprite(new Texture("about/table.png"));
        headersprite = new Sprite(new Texture("about/header.png"));
        headersprite.setSize(Gdx.graphics.getHeight()*0.8f*0.9f,Gdx.graphics.getHeight()*0.3f);
        backgroundsprite.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight()*0.8f);
        listsprite.setSize(Gdx.graphics.getHeight()*0.9f, Gdx.graphics.getHeight()*0.8f*0.9f);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundsprite.setPosition(Gdx.graphics.getWidth()/2-backgroundsprite.getWidth()/2,Gdx.graphics.getHeight()/2-backgroundsprite.getHeight()/2-Gdx.graphics.getHeight()/10);
        listsprite.setPosition(Gdx.graphics.getWidth()/2-listsprite.getWidth()/2,Gdx.graphics.getHeight()/2-listsprite.getHeight()/2-Gdx.graphics.getHeight()/10);
        headersprite.setPosition(Gdx.graphics.getWidth()/2-headersprite.getWidth()/2,Gdx.graphics.getHeight()/2+headersprite.getHeight()/2);
        MenuScreen.setColor(0.5f,0.5f,0.5f,1);
        btn();

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
        menu.font.draw(menu.batch,"Версия : ",Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2+listsprite.getHeight()/6,0, 0,false);
        menu.font.draw(menu.batch, Constants.Version,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2+listsprite.getHeight()/6,0, (int) (-listsprite.getWidth()/2),false);
        menu.font.draw(menu.batch,"Debug mode : ",Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2-listsprite.getHeight()/12,0,0,false);
        menu.font.draw(menu.batch,"Контакт  : ",Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2-listsprite.getHeight()/3,0, 0,false);

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
    }

    public void  btn(){
        Table btn = new Table();
        btn.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight()*0.8f);
        btn.setPosition(Gdx.graphics.getWidth()/2-btn.getWidth()/2,
                Gdx.graphics.getHeight()/2-btn.getHeight()/2-Gdx.graphics.getHeight()/10);
        final Image exit = new Image(new Texture("about/close_2.png"));

        final Image debugmode;
        if(menu.prefs.getBoolean("debugmode", false))
            debugmode = new Image(new Texture("settings/96.png"));
        else
            debugmode = new Image(new Texture("settings/95.png"));
            final Image vk = new Image(new Texture("about/vk.jpg"));
        debugmode.setSize(Gdx.graphics.getHeight()/10*3,Gdx.graphics.getHeight()/10);

        vk.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.tab2.play(menu.prefs.getInteger("volume",10)/10f);
                Gdx.net.openURI(Constants.Vkurl);
                super.clicked(event, x, y);
            }
        });
        debugmode.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.tab2.play(menu.prefs.getInteger("volume",10)/10f);
                if(menu.prefs.getBoolean("debugmode", false)){
                    menu.prefs.putBoolean("debugmode", false);
                   debugmode.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/95.png"))));
                }else {
                    menu.prefs.putBoolean("debugmode", true);
                    debugmode.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("settings/96.png"))));
                }
                menu.prefs.flush();
                super.clicked(event, x, y);
            }
        });
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(menu.prefs.getBoolean("sound"))
                    menu.tab1.play(menu.prefs.getInteger("volume",10)/10f);
                menu.prefs.flush();
                menu.setScreen(MyGdxGame.ScreenMenu);
                super.clicked(event, x, y);
            }
        });
        btn.defaults().expand();
        btn.add(exit).size(Gdx.graphics.getHeight()/8,Gdx.graphics.getHeight()/8).right().top().colspan(2);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/6);
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/6).width(listsprite.getWidth()/2);
        btn.add(debugmode).size(debugmode.getWidth(),debugmode.getHeight()).left();
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/6).width(listsprite.getWidth()/2);
        btn.add(vk).size(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6).left();
        btn.row();
        btn.add().height(Gdx.graphics.getHeight()/6);
        stage.addActor(btn);


    }
}
