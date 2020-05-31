package com.gg.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gg.game.MainGame;
import com.gg.game.MyGdxGame;


public class Controller {

    Viewport viewport;
    Stage stage;
    boolean upPressed,leftPressed, rightPressed;

    MyGdxGame game;
    OrthographicCamera camera;

    public Controller() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport, game.batch);
        stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);
        Table gamepad = new Table();
        gamepad.left().bottom();
        // final  Image escImg = new Image(new Texture("buttons/esc.jpeg"));

        final Image upImg = new Image(new Texture("buttons/arrowup.png"));
        final Image leftImg = new Image(new Texture("buttons/arrowleft.png"));
        final Image rightImg = new Image(new Texture("buttons/arrowright.png"));
        upImg.setSize(150, 150);
        leftImg.setSize(150, 150);
        rightImg.setSize(150, 150);

        upImg.addListener(new ClickListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (x > upImg.getWidth() || x < 0 || y > upImg.getHeight() || y < 0) {
                    upPressed = false;
                } else {
                    upPressed = true;
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                upPressed = true;
                System.out.println("f " + x + "  " + y);
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
                if (x > leftImg.getWidth() || x < 0 || y > leftImg.getHeight() || y < 0) {
                    leftPressed = false;
                } else {
                    leftPressed = true;
                }


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
                if (x > rightImg.getWidth() || x < 0 || y > rightImg.getHeight() || y < 0) {
                    rightPressed = false;
                } else {
                    rightPressed = true;
                }


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

        gamepad.add();

        gamepad.add(upImg).size(upImg.getWidth(), upImg.getHeight()).pad(5, 5, 5, 5);
        gamepad.add();
        gamepad.row();
        gamepad.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight()).pad(5, 5, 5, 5);
        gamepad.add();
        gamepad.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).pad(5, 5, 5, 5);

        gamepad.setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 8);
        stage.addActor(gamepad);

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

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
