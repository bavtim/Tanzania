package com.gg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;


public class Levelboot implements Screen {
    //регионы для анимации персонажей
    static TextureRegion[] Animation_pers_blaster_shoot;
    static TextureRegion[] Animation_pers_hurt;
    static TextureRegion[] Animation_pers_idle;
    static TextureRegion[] Animation_pers_jump;
    static TextureRegion[] Animation_pers_run;
    static TextureRegion[] Animation_enemyork1_walk;
    static TextureRegion[] Animation_enemyork1_hurt;
    static TextureRegion[] Animation_enemyork1_idle;
    private static long SPLASH_MINIMUM_MILLIS = 3000L;//минимальное время для бутскрина
    private BitmapFont font;//шрифт
    private boolean flag;//флаг анимации
    private float timer;//таймер анимации
    private MyGdxGame menu;
    private Sprite MenuScreen;//спрайт для фона
    private GlyphLayout glyphLayout;//лайаут для текста
    private float deltatime = 0;//время кадра

    Levelboot(MyGdxGame levelboot) {
        this.menu = levelboot;
        glyphLayout = new GlyphLayout();

        MenuScreen = new Sprite(new Texture("level_select/levelboot.png"));
        //генератор шрифта
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ttf/segoeprb.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        parameter.size = Gdx.graphics.getHeight() / 6;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(0f, 0f, 0f, 0f);
        MenuScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        glyphLayout.setText(font, "Загрузка...");
        bootassert();

    }

    @Override
    public void show() {

    }

    //отрисовка всего и вся
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        deltatime = Gdx.graphics.getDeltaTime() * 1.25f;
        MyGdxGame.batch.begin();

        MenuScreen.draw(MyGdxGame.batch);
//анимация
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
                flag = false;

            }
            System.out.println(timer);
        }
        font.setColor(0f, 0f, 0f, timer);
        glyphLayout.setText(font, "Загрузка...");
        font.draw(MyGdxGame.batch, glyphLayout, Gdx.graphics.getWidth() / 2f - glyphLayout.width / 2, Gdx.graphics.getHeight() / 2f);
        MyGdxGame.batch.end();

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
        dispose();
    }

    @Override
    public void dispose() {
        font.dispose();
    }

    //открытие второго потока для подгрузки текстур, пока в первом идет показ анимации
    private void bootassert() {
        final long splash_start_time = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        TextureAtlas();

                        long splash_elapsed_time = System.currentTimeMillis() - splash_start_time;
                        if (splash_elapsed_time < SPLASH_MINIMUM_MILLIS) {
                            Timer.schedule(
                                    new Timer.Task() {
                                        @Override
                                        public void run() {
                                            menu.setScreen(new MainGame(menu));
                                        }
                                    }, (float) (SPLASH_MINIMUM_MILLIS - splash_elapsed_time) / 1000f);
                        } else {
                            menu.setScreen(new MainGame(menu));

                        }
                    }
                });
            }
        }).start();

    }

    //парсер для текстур из файлов
    private void TextureAtlas() {
        Animation_pers_blaster_shoot = new TextureRegion[8];
        Animation_pers_hurt = new TextureRegion[10];
        Animation_pers_idle = new TextureRegion[14];
        Animation_pers_jump = new TextureRegion[21];
        Animation_pers_run = new TextureRegion[14];
        for (int i = 0; i < Animation_pers_blaster_shoot.length; i++) {

            Animation_pers_blaster_shoot[i] = new TextureRegion(new Texture("foxy/animation/blaster shoot/foxy-blaster shoot_" + i + ".png"));

        }
        for (int i = 0; i < Animation_pers_hurt.length; i++) {
            if (i < 10) {

                Animation_pers_hurt[i] = new TextureRegion(new Texture("foxy/animation/hurt/foxy-hurt_0" + i + ".png"));

            } else {

                Animation_pers_hurt[i] = new TextureRegion(new Texture("foxy/animation/hurt/foxy-hurt_" + i + ".png"));


            }
        }
        for (int i = 0; i < Animation_pers_idle.length; i++) {
            if (i < 10) {

                Animation_pers_idle[i] = new TextureRegion(new Texture("foxy/animation/idle/foxy-idle_0" + i + ".png"));

            } else {

                Animation_pers_idle[i] = new TextureRegion(new Texture("foxy/animation/idle/foxy-idle_" + i + ".png"));


            }
        }
        for (int i = 0; i < Animation_pers_jump.length; i++) {
            if (i < 10) {

                Animation_pers_jump[i] = new TextureRegion(new Texture("foxy/animation/jump/foxy-jump_0" + i + ".png"));

            } else {

                Animation_pers_jump[i] = new TextureRegion(new Texture("foxy/animation/jump/foxy-jump_" + i + ".png"));


            }
        }
        for (int i = 0; i < Animation_pers_run.length; i++) {
            if (i < 10) {

                Animation_pers_run[i] = new TextureRegion(new Texture("foxy/animation/run/foxy-run_0" + i + ".png"));

            } else {

                Animation_pers_run[i] = new TextureRegion(new Texture("foxy/animation/run/foxy-run_" + i + ".png"));


            }
        }
        Animation_enemyork1_hurt = new TextureRegion[7];
        Animation_enemyork1_idle = new TextureRegion[7];
        Animation_enemyork1_walk = new TextureRegion[7];
        for (int i = 0; i < Animation_enemyork1_hurt.length; i++) {
            Animation_enemyork1_hurt[i] = new TextureRegion(new Texture("enemy/ork1/HURT/HURT_00" + i + ".png"));
        }
        for (int i = 0; i < Animation_enemyork1_idle.length; i++) {
            Animation_enemyork1_idle[i] = new TextureRegion(new Texture("enemy/ork1/IDLE/IDLE_00" + i + ".png"));
        }
        for (int i = 0; i < Animation_enemyork1_walk.length; i++) {
            Animation_enemyork1_walk[i] = new TextureRegion(new Texture("enemy/ork1/WALK/WALK_00" + i + ".png"));
        }

    }

}


