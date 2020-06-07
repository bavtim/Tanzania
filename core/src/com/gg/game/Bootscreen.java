package com.gg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

//экран загрузки игры
public class Bootscreen implements Screen {
    private Sprite sprite;//спрайт  картинки загрузки
    private float timer = 0f, timer1 = 0f;//таймеры для анимаций
    private boolean flag = false;//булевое значение для переключения блока анимаций
    private Texture Bootscreen_IMG;//текстура загрузки
    private float deltatime;//переменная для получения времени кадров
    MyGdxGame boot;

    Bootscreen(MyGdxGame boot) {
        this.boot = boot;
        Bootscreen_IMG = new Texture("Bootscreen/joystick.png");
        sprite = new Sprite(Bootscreen_IMG, 0, 0, 512, 512);
        sprite.setPosition(Gdx.graphics.getWidth() / 2f - 256, Gdx.graphics.getHeight() / 2f - 256);

        sprite.setColor(0, 0, 0, 0);
    }

    @Override
    public void show() {

    }

    // отрисовка всего и вся
    @Override
    public void render(float delta) {
        //очистка экрана от прошлого кадра
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        deltatime = Gdx.graphics.getDeltaTime() * 1.25f;
        //анимация мигания картинки
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
                timer = 0;
                boot.setScreen(MyGdxGame.ScreenMenu);
            }

        }
//отрисовка

        sprite.setColor(0, 0, 0, timer);

        MyGdxGame.camera.update();
        MyGdxGame.batch.setProjectionMatrix(MyGdxGame.camera.combined);

        MyGdxGame.batch.begin();
        sprite.draw(MyGdxGame.batch);
        MyGdxGame.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        MyGdxGame.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.setPosition(Gdx.graphics.getWidth() / 2f - 256, Gdx.graphics.getHeight() / 2f - 256);

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

        Bootscreen_IMG.dispose();

    }

}
