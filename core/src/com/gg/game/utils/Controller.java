package com.gg.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gg.game.MainGame;
import com.gg.game.MyGdxGame;

public class Controller {
    public static boolean escPressed;
    private SpriteBatch batch;
    private Sprite backgroundsprite;
    private Sprite listsprite;
    private Sprite headersprite;
    private Viewport viewport;
    private Sprite text;
    private Stage stage;
    private boolean upPressed, leftPressed, rightPressed, bulletPressed;
    public Controller() {
        batch = new SpriteBatch();
        text = new Sprite(new Texture("pause/text.png"));
        backgroundsprite = new Sprite(new Texture("pause/bg.png"));
        listsprite = new Sprite(new Texture("pause/table.png"));
        headersprite = new Sprite(new Texture("pause/header.png"));

        text.setSize(Gdx.graphics.getHeight() * 1f * 0.8f, Gdx.graphics.getHeight() * 0.5f * 0.8f);
        headersprite.setSize(Gdx.graphics.getHeight() * 0.8f * 0.9f, Gdx.graphics.getHeight() * 0.3f);
        backgroundsprite.setSize(Gdx.graphics.getHeight() * 1.3f, Gdx.graphics.getHeight() * 0.8f);
        listsprite.setSize(Gdx.graphics.getHeight() * 0.9f * 1.3f, Gdx.graphics.getHeight() * 0.8f * 0.9f);

        text.setPosition(Gdx.graphics.getWidth() / 2f - text.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - text.getWidth() / 4f);
        backgroundsprite.setPosition(Gdx.graphics.getWidth() / 2f - backgroundsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f - backgroundsprite.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        listsprite.setPosition(Gdx.graphics.getWidth() / 2f - listsprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f - listsprite.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);
        headersprite.setPosition(Gdx.graphics.getWidth() / 2f - headersprite.getWidth() / 2, Gdx.graphics.getHeight() / 2f + headersprite.getHeight() / 2);

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport, MyGdxGame.batch);
        if (MyGdxGame.prefs.getBoolean("debugmode", false)) {
            stage.setDebugAll(true);
        } else {
            stage.setDebugAll(false);
        }

        Gdx.input.setInputProcessor(stage);
        createControll();



    }

    public void draw() {
        if (escPressed) {
            menurender();

        }

        stage.draw();

    }

    public boolean isUpPressed() {

        return upPressed;
    }


    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isEscPressed() {
        return escPressed;
    }
    public boolean isBulletPressed() {
        return bulletPressed;
    }
    public boolean isRightPressed() {

        return rightPressed;
    }


    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    public void dispose(){
        batch.dispose();
        stage.dispose();
    }

    public void createControll() {

        escPressed = false;
        gamemod();
        stage.clear();
        Table gamepad = new Table();
        gamepad.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gamepad.setPosition(Gdx.graphics.getWidth() / 2f - gamepad.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - gamepad.getHeight() / 2);
        final Image upImg = new Image(new Texture("buttons/arrowup.png"));
        final Image leftImg = new Image(new Texture("buttons/arrowleft.png"));
        final Image rightImg = new Image(new Texture("buttons/arrowright.png"));
        final Image ecs = new Image(new Texture("btn/menu.png"));
        final Image bullet = new Image(new Texture("buttons/bulletico.png"));
        upImg.setSize(150, 150);
        leftImg.setSize(150, 150);
        rightImg.setSize(150, 150);
        bullet.setSize(150, 150);
        ecs.setSize(Gdx.graphics.getWidth() / 8f, Gdx.graphics.getWidth() / 8f);
        ecs.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createmenu();
                super.clicked(event, x, y);
            }
        });
        bullet.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                bulletPressed = !(x > bullet.getWidth()) && !(x < 0) && !(y > bullet.getHeight()) && !(y < 0);


            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bulletPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bulletPressed = false;

            }
        });


        upImg.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                upPressed = !(x > upImg.getWidth()) && !(x < 0) && !(y > upImg.getHeight()) && !(y < 0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });
        leftImg.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                leftPressed = !(x > leftImg.getWidth()) && !(x < 0) && !(y > leftImg.getHeight()) && !(y < 0);


            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;

            }
        });
        rightImg.addListener(new ClickListener() {

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                rightPressed = !(x > rightImg.getWidth()) && !(x < 0) && !(y > rightImg.getHeight()) && !(y < 0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;

            }
        });

        gamepad.defaults().expand();
        gamepad.add(ecs).size(ecs.getWidth(), ecs.getHeight());
        gamepad.add().width(Gdx.graphics.getWidth() / 4f);
        gamepad.add().width(Gdx.graphics.getWidth() / 4f);
        gamepad.add().width(Gdx.graphics.getWidth() / 4f);
        gamepad.row();
        gamepad.add().colspan(2);
        gamepad.add(upImg).colspan(2).size(upImg.getWidth(), upImg.getHeight()).center().bottom();
        gamepad.row();
        gamepad.add(bullet).size(bullet.getWidth(), bullet.getHeight()).colspan(2);


        gamepad.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight()).center();
        gamepad.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).center();
        gamepad.row();


        stage.addActor(gamepad);
    }

    public void createmenu() {
        escPressed = true;

        stage.clear();
        Table gamepad = new Table();
        gamepad.setSize(Gdx.graphics.getHeight() * 1.3f, Gdx.graphics.getHeight() * 0.8f);
        gamepad.setPosition(Gdx.graphics.getWidth() / 2f - gamepad.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - gamepad.getHeight() / 2 - Gdx.graphics.getHeight() / 10f);

        final Image exit = new Image(new Texture("rating/close_2.png"));
        final Image menu = new Image(new Texture("btn/menu.png"));
        final Image restart = new Image(new Texture("btn/restart.png"));
        menu.setSize(Gdx.graphics.getHeight() / 5f, Gdx.graphics.getHeight() / 5f);
        restart.setSize(Gdx.graphics.getHeight() / 5f, Gdx.graphics.getHeight() / 5f);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createControll();
                super.clicked(event, x, y);
            }
        });
        restart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.restart();
                super.clicked(event, x, y);
            }
        });
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                MainGame.backtomenu();
                super.clicked(event, x, y);
            }
        });
        gamepad.defaults().expand();
        gamepad.add(exit).size(Gdx.graphics.getHeight() / 8f, Gdx.graphics.getHeight() / 8f).right().top().colspan(2);
        gamepad.row();
        gamepad.row();
        gamepad.add().height(Gdx.graphics.getHeight() / 5f);
        gamepad.row();

        gamepad.add(menu).size(menu.getWidth(), menu.getHeight()).height(Gdx.graphics.getHeight() / 5f).center();
        gamepad.add(restart).size(menu.getWidth(), menu.getHeight()).height(Gdx.graphics.getHeight() / 5f).center();

        gamepad.row();
        gamepad.add().height(Gdx.graphics.getHeight() / 5f);
        stage.addActor(gamepad);

    }

    private void gamemod() {
        MainGame.blockcontrol = false;
        MainGame.menumodeflag = false;

    }

    private void menurender() {
        batch.begin();
        backgroundsprite.draw(batch);
        listsprite.draw(batch);
        headersprite.draw(batch);
        text.draw(batch);
        batch.end();
    }
}

