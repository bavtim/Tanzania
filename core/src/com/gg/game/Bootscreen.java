package com.gg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Bootscreen implements Screen {
    private Sprite sprite;
    private float timer = 0f, timer1 = 0f;
    private boolean flag = false, flag1 = false;
    private BitmapFont font;
    private Texture Bootscreen_IMG;
    private float deltatime;
    Bootscreen() {
    }

    @Override
    public void show() {
        Bootscreen_IMG = new Texture("Bootscreen/joystick.png");
        sprite = new Sprite(Bootscreen_IMG, 0, 0, 512, 512);
        sprite.setPosition(Gdx.graphics.getWidth() / 2f - 256, Gdx.graphics.getHeight() / 2f - 256);
        font = new BitmapFont();
        font.setColor(0, 0, 0, 0);
        font.getData().setScale(Gdx.graphics.getWidth() / 100f);
        sprite.setColor(0, 0, 0, 0);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        deltatime = Gdx.graphics.getDeltaTime();
        if (deltatime < 1) {
            if (flag) {
                if (timer > 0)
                    timer -= deltatime;
            } else {
                timer += deltatime;
            }
            if (timer > 2)
                flag = true;
            if (timer < 0) {
                if (flag1) {
                    timer1 -= deltatime;
                }
                if (!flag1) {
                    timer1 += deltatime;
                    if (timer1 > 2)
                        flag1 = true;
                }

                if (timer1 < 0)
                    timer1 = 0;
            }

        }


        sprite.setColor(0, 0, 0, timer);

        MyGdxGame.camera.update();
        MyGdxGame.batch.setProjectionMatrix(MyGdxGame.camera.combined);
        font.setColor(0, 0, 0, timer1);
        MyGdxGame.batch.begin();
        font.draw(MyGdxGame.batch, "GGgame", Gdx.graphics.getWidth() / 2f - Gdx.graphics.getWidth() / 3.5f, Gdx.graphics.getHeight() / 2f+ Gdx.graphics.getWidth() / 10f, 0, 40, false);
        sprite.draw(MyGdxGame.batch);
        MyGdxGame.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        MyGdxGame.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setPosition(Gdx.graphics.getWidth() / 2f - 256, Gdx.graphics.getHeight() / 2f - 256);
        font.getData().setScale(Gdx.graphics.getWidth() / 100f);
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
