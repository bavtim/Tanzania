package com.gg.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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


    private Viewport viewport;
    private Stage stage, stagebg;
    private boolean upPressed, leftPressed, rightPressed, escPressed, bulletPressed;

    public Controller() {
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport, MyGdxGame.batch);
        stage.setDebugAll(true);
        stagebg = new Stage(viewport, MyGdxGame.batch);
        stagebg.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);
        Table table1 = new Table();
        table1.setSize(Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getHeight() * 0.7f);
        table1.setPosition(Gdx.graphics.getWidth() / 2.1f - table1.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - table1.getHeight() / 2 + Gdx.graphics.getHeight() / 10f);
        Image BG = new Image(new Texture("btn/restart.png"));
        Image LIST = new Image(new Texture("btn/restart.png"));
        Image HEADER = new Image(new Texture("btn/restart.png"));

        createControll();



    }

    public void draw() {
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
         stage.dispose();
    }

    private void createControll() {

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
        final Image ecs = new Image(new Texture("buttons/menu.png"));
        final Image bullet = new Image(new Texture("buttons/bulletico.png"));
        upImg.setSize(150, 150);
        leftImg.setSize(150, 150);
        rightImg.setSize(150, 150);
        bullet.setSize(150, 150);
        ecs.setSize(100, 100);
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
        gamepad.add(bullet).size(bullet.getWidth(), bullet.getHeight()).colspan(1);

        gamepad.add();
        gamepad.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight()).center();
        gamepad.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).center();
        gamepad.row();


        stage.addActor(gamepad);
    }

    private void createmenu() {
        escPressed = true;

        stage.clear();
        Table gamepad = new Table();

        gamepad.setSize(Gdx.graphics.getHeight() * 0.9f, Gdx.graphics.getHeight() * 0.7f);
        gamepad.setPosition(Gdx.graphics.getWidth() / 2.1f - gamepad.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - gamepad.getHeight() / 2 + Gdx.graphics.getHeight() / 10f);
        final Image exit = new Image(new Texture("rating/close_2.png"));
        final Image menu = new Image(new Texture("btn/menu.png"));
        final Image restart = new Image(new Texture("btn/restart.png"));
        menu.setSize(100, 100);
        restart.setSize(100, 100);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createControll();
                super.clicked(event, x, y);
            }
        });
        menu.addListener(new ClickListener() {
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
        gamepad.add().height(Gdx.graphics.getHeight() / 5f);
        gamepad.row();
        gamepad.add(menu).size(menu.getWidth(), menu.getHeight()).height(Gdx.graphics.getHeight() / 5f).center();
        gamepad.add(restart).size(menu.getWidth(), menu.getHeight()).height(Gdx.graphics.getHeight() / 5f).center();
        gamepad.row();
        gamepad.add().height(Gdx.graphics.getHeight() / 5f);
        gamepad.row();
        gamepad.add().height(Gdx.graphics.getHeight() / 5f);
        stage.addActor(gamepad);

    }

    private void gamemod() {
        MainGame.blockcontrol = false;
        MainGame.menumodeflag = false;
        MainGame.blockanimwater = false;
    }
}

