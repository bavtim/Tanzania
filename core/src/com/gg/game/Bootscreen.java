package com.gg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static java.lang.Thread.sleep;


public class Bootscreen implements Screen {
    MyGdxGame menu;



    public Bootscreen(){ }



    Sprite sprite;

    float timer =0f,timer1 =0f;
    boolean flag=false,flag1=false;
    BitmapFont font;
    Texture Bootscreen_IMG;
    float deltatime=0;
    @Override
    public void show() {
        Bootscreen_IMG = new Texture("Bootscreen/joystick.png");

        sprite = new Sprite(Bootscreen_IMG,0,0,512,512);
        sprite.setPosition(Gdx.graphics.getWidth()/2-256, Gdx.graphics.getHeight()/2-256);
        font = new BitmapFont();
        font.setColor(0,0,0,0);
        font.getData().setScale(Gdx.graphics.getWidth()/100);
        sprite.setColor(0,0,0,0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        deltatime=Gdx.graphics.getDeltaTime();
        if(deltatime<1){
            if(flag){
                if(timer>0)
                timer -=deltatime;
            }else{
                timer +=deltatime;
            }
            if(timer>2)
                flag=true;
            if(timer<0){
                if(flag1){
                    timer1 -=deltatime;
                }
                if(!flag1){
                    timer1 +=deltatime;
                    if(timer1>2)
                        flag1=true;
                }

                if(timer1<0)
                   timer1=0;
            }

        }


        sprite.setColor(0,0,0, timer);

        MyGdxGame.camera.update();
        menu.batch.setProjectionMatrix(menu.camera.combined);
        font.setColor(0,0,0,timer1);
        menu.batch.begin();
        font.draw(menu.batch,"GGgame",Gdx.graphics.getWidth()/2-Gdx.graphics.getWidth()/3.5f,Gdx.graphics.getHeight()/2+Gdx.graphics.getWidth()/10f,0,40, false);
        sprite.draw(menu.batch);
        menu.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        MyGdxGame.camera.setToOrtho(false,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        sprite.setPosition(Gdx.graphics.getWidth()/2-256, Gdx.graphics.getHeight()/2-256);
        font.getData().setScale(Gdx.graphics.getWidth()/100);
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
        font.dispose();
        Bootscreen_IMG.dispose();
    }

}
