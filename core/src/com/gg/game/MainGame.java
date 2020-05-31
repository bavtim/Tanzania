package com.gg.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gg.game.utils.B2dContactListener;
import com.gg.game.utils.Constants;
import com.gg.game.utils.Controller;
import com.gg.game.utils.TiledObjectUtil;


public class MainGame implements Screen {
    float size = 1;
    public static TextureRegion[] Animation_enemyork1_walk;
    public static TextureRegion[] Animation_enemyork1_hurt;
    public static TextureRegion[] Animation_enemyork1_idle;
    float positionenemyx = 0, positionenemyy = 0;
    Body enemy;
    float animation_pers_x_shoot = 0;
    Stage stagebg;
    Stage stagef;
    Image bg2;
    Image bg1;
    Image f2;
    Image f1;
    public Body rect;
    public Body rectfoot;
    private B2dContactListener cl;
    Array<Body> box;
    private OrthographicCamera camera;
    Table table0;
    Table table1;
    private MyGdxGame game;
    private TiledMap map;
    private TiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer rend;
    private Sprite sprite;
    private float animation_pers_x_left = 0;
    private float animation_pers_x_right = 0;
    private float animation_pers_x_stand = 0;
    private float animation_pers_y = 0;
    private boolean init = false;
    private byte direction_x = 0;
    public static float positionx = 10f, positiony = 10f;
    public static float positioncheckx = 10f, positionchecky = 10f;
    private Texture boxsprite;
    static final float STEP_TIME = 1f / 60f;
    float accumulator = 0;
    boolean flag = true;
    float positionflowerx = 0, positionflowery = 0;
    private static boolean incheak = false;
    int[] earth;
    int[] cloud;
    int[] check;
    byte jump = 0;
    int[] flower;
    boolean portal;
    boolean pers;
    Controller controller;
    Viewport viewport;
    float timeportal = 0;
    Texture portaltexture;
    boolean blockcontrol;
    boolean temp = true;
    float timer = 0;
    float timershot = 2;
    boolean unlockdoublejump = false;
    Texture bullet;
    Texture dim;
    float timerenemy = 0;
    boolean flag1 = true;
    boolean temperflag = true;
    Sprite enemysprite;
    float animation_enemy_x_stand = 0;
    float animation_enemy_x_right = 0;
    float animation_enemy_x_left = 0;
    public MainGame(MyGdxGame game) {
        this.game = game;
        bullet = new Texture("Tilemap/bullet.png");
        dim = new Texture("Tilemap/dim.png");
        box = new Array<Body>();
        world = new World(new Vector2(0, -20), true);
        cl = new B2dContactListener();
        world.setContactListener(cl);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / Constants.PPM * 2, Gdx.graphics.getHeight() / Constants.PPM * 2);
        portaltexture = new Texture("Tilemap/portal.png");
        rend = new Box2DDebugRenderer();
        map = new TmxMapLoader().load("Tilemap/tin.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f / Constants.PPM);
        ParseTileMap();
        spawn();
        sprite = new Sprite(MyGdxGame.Animation_pers_run[12], 300, 150, 525, 660);
        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2);
        controller = new Controller();

        boxsprite = new Texture("Tilemap/box.png");
        createBox();

        earth = new int[]{0, 1};
        cloud = new int[]{2};
        check = new int[]{3};
        flower = new int[]{4};

        parallax();
        animation_pers_x_shoot = MyGdxGame.Animation_pers_blaster_shoot.length;
        init_enemy();
        enemysprite = new Sprite();
        enemysprite.setSize(8, 8);
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        System.out.println(rect.getPosition());
        timerenemy += Gdx.graphics.getDeltaTime() * 2;
        if (timer <= 1)
            timer += Gdx.graphics.getDeltaTime();
        if (timershot <= 1)
            timershot += Gdx.graphics.getDeltaTime();
        heckpoint();
        playerDead();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stepWorld();
        camera.update();

        gamepad_delta_xy();
        bgparallaxdraw();
        game.batch.setProjectionMatrix(camera.combined);
        stagebg.draw();
        renderer.setView(camera);
        renderer.render(earth);

        if (incheak)
            renderer.render(check);
        flower();
        stagef.draw();
        if (game.prefs.getBoolean("debugmode", true))
            rend.render(world, camera.combined);
        Draw();
        renderer.render(cloud);
        if (Gdx.app.getType() == Application.ApplicationType.Android)
        controller.draw();

        cameraposition();
        sprite.setPosition(rect.getPosition().x - 2, rect.getPosition().y - 2);

        if (cl.isEnemydamage() && flag1) {

            world.destroyBody(enemy);
            flag1 = false;

        }
        if (flag1)
            moveenemy();


    }

    private void finsih() {

        if (flag) {
            timeportal += Gdx.graphics.getDeltaTime() * 3f;

        } else {
            timeportal -= Gdx.graphics.getDeltaTime() * 3f;

        }

        if (timeportal / 4 < 1) {
            game.batch.draw(
                    portaltexture,
                    positionenemyx + 26,
                    positionenemyy - 2,
                    3,
                    3,
                    384 / Constants.PPM,
                    384 / Constants.PPM,
                    timeportal / 4,
                    timeportal / 4,
                    (float) Math.toDegrees(Math.abs(timeportal)),
                    0, 0, 384, 384,
                    false, false
            );
        }
        if (rect.getPosition().x >= positionenemyx + 24) {
            game.setScreen(game.Screenw);


            size += Gdx.graphics.getDeltaTime() * 7;
            blockcontrol = true;
            System.out.println(size * 3);
            if (size * 3 > Gdx.graphics.getWidth() / Constants.PPM) {


            }
            game.batch.draw(
                    portaltexture,
                    positionenemyx + 26,
                    positionenemyy - 2,
                    3,
                    3,
                    384 / Constants.PPM,
                    384 / Constants.PPM,
                    size,
                    size,
                    (float) Math.toDegrees(Math.abs(timeportal)),
                    0, 0, 384, 384,
                    false, false

            );
        } else if (timeportal / 4 > 1) {
            game.batch.draw(
                    portaltexture,
                    positionenemyx + 26,
                    positionenemyy - 2,
                    3,
                    3,
                    384 / Constants.PPM,
                    384 / Constants.PPM,
                    1,
                    1,
                    (float) Math.toDegrees(Math.abs(timeportal)),
                    0, 0, 384, 384,
                    false, false

            );


        }


//        if (timeportal / 4 > 1.5) {
//            flag = false;
//
//        }
//        if (timeportal / 4 < 0) {
//            flag = true;
//            portal = false;
//            temp = true;
//
//        }
    }

    private void flower() {

        if (rect.getPosition().x < positionflowerx && !unlockdoublejump) {

            renderer.render(flower);
        } else {
            unlockdoublejump = true;
        }
    }


    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, Gdx.graphics.getWidth() / Constants.PPM * 2, Gdx.graphics.getHeight() / Constants.PPM * 2);
        sprite.setSize(128 / Constants.PPM * 2, 128 / Constants.PPM * 2);
        controller.resize(width, height);
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

        map.dispose();

        game.batch.dispose();
        world.dispose();
        rend.dispose();

    }

    private void Draw() {
        if (init) {

            if (!cl.playerCanJump()) {


                animation_pers_y += Constants.Speed_animation_y * Gdx.graphics.getDeltaTime();
                if (animation_pers_y > MyGdxGame.Animation_pers_jump.length - 1)
                    animation_pers_y = MyGdxGame.Animation_pers_jump.length - 1;

                sprite.setRegion(MyGdxGame.Animation_pers_jump[(int) animation_pers_y], Constants.Animation_jump_correction_x[(int) animation_pers_y], Constants.Animation_jump_correction_y[(int) animation_pers_y], 580, 640);

                if (direction_x > 0) {
                    sprite.setFlip(false, false);
                } else if (direction_x < 0) {
                    sprite.setFlip(true, false);
                }
            } else {
                animation_pers_y = 0;

            }
        } else {

            init = true;
        }
        animation_pers_x_shoot += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime();
        if (animation_pers_x_shoot > MyGdxGame.Animation_pers_blaster_shoot.length)
            animation_pers_x_shoot = MyGdxGame.Animation_pers_blaster_shoot.length;
        if (cl.playerCanJump()) {

            if (animation_pers_x_shoot < MyGdxGame.Animation_pers_blaster_shoot.length - 1) {
                sprite.setRegion(MyGdxGame.Animation_pers_blaster_shoot[(int) animation_pers_x_shoot], 300, 150, 525, 660);
                if (direction_x > 0) {
                    sprite.setFlip(false, false);
                } else if (direction_x < 0) {
                    sprite.setFlip(true, false);
                }

            }
        }

        game.batch.begin();
        drawBox();
        if (portal)
            portal();
        if (pers)
            sprite.draw(game.batch);
        if (flag1)
            enemydraw();
        if (!flag1)
            finsih();

        shotdraw();
        game.batch.end();
    }

    private void portal() {

        if (flag) {
            timeportal += Gdx.graphics.getDeltaTime() * 3f;
            blockcontrol = true;
        } else {
            timeportal -= Gdx.graphics.getDeltaTime() * 3f;

        }


        if (timeportal / 4 < 1) {
            game.batch.draw(
                    portaltexture,
                    positionx - 3,
                    positiony - 2,
                    3,
                    3,
                    384 / Constants.PPM,
                    384 / Constants.PPM,
                    timeportal / 4,
                    timeportal / 4,
                    (float) Math.toDegrees(Math.abs(timeportal)),
                    0, 0, 384, 384,
                    false, false
            );
        }
        if (timeportal / 4 > 1 && timeportal / 4 < 1.5f) {
            game.batch.draw(
                    portaltexture,
                    positionx - 3,
                    positiony - 2,
                    3,
                    3,
                    384 / Constants.PPM,
                    384 / Constants.PPM,
                    1,
                    1,
                    (float) Math.toDegrees(Math.abs(timeportal)),
                    0, 0, 384, 384,
                    false, false

            );
            if (temp) {
                animation_pers_x_stand = 0;

                init = true;

            }

            temp = false;
            pers = true;
            idle();
        }
        if (timeportal / 4 > 1.5) {
            flag = false;

        }
        if (timeportal / 4 < 0) {
            flag = true;
            portal = false;
            temp = true;
            blockcontrol = false;
        }
    }

    private void createRect(float x, float y) {

        PolygonShape shape;
//        ChainShape shape1;
////        shape1= new ChainShape();
////        shape1.createChain(new Vector2[]{new Vector2(-1.9f,-1.99f),new Vector2(1.9f,-1.99f)});
        PolygonShape shape1;
        shape1 = new PolygonShape();
        shape1.setAsBox(100 / Constants.PPM, 127 / Constants.PPM);
        shape = new PolygonShape();
        shape.setAsBox(127 / Constants.PPM, 127 / Constants.PPM);
        BodyDef bDeffoot = new BodyDef();
        bDeffoot.type = BodyDef.BodyType.DynamicBody;
        bDeffoot.position.set(x, y);
        BodyDef bDefbody = new BodyDef();

        bDefbody.type = BodyDef.BodyType.DynamicBody;
        bDefbody.position.set(x, y);
        rect = world.createBody(bDefbody);
        rect.setUserData(Constants.TM_player_body);
        rectfoot = world.createBody(bDeffoot);
        rectfoot.setUserData(Constants.TM_player_foot);

        FixtureDef fDeffoot = new FixtureDef();
        fDeffoot.shape = shape1;
        fDeffoot.density = Constants.TM_player_foot_density;
        fDeffoot.friction = Constants.TM_player_foot_friction;
        fDeffoot.restitution = Constants.TM_player_foot_density;

        FixtureDef fDefbody = new FixtureDef();
        fDefbody.shape = shape;
        fDefbody.density = Constants.TM_player_body_density;
        fDefbody.friction = Constants.TM_player_body_friction;
        fDefbody.restitution = Constants.TM_player_body_restitution;

        rect.setFixedRotation(true);
        rect.setBullet(true);

        rect.createFixture(fDefbody).setUserData(Constants.TM_player_body);
        rectfoot.setFixedRotation(true);
        rectfoot.createFixture(fDeffoot).setUserData(Constants.TM_player_foot);
        portal = true;
        pers = false;
        RevoluteJointDef rDef = new RevoluteJointDef();
        rDef.bodyA = rect;
        rDef.bodyB = rectfoot;

        world.createJoint(rDef);

    }

    private void gamepad_delta_xy() {
        rect.setGravityScale(1);
        if (!blockcontrol) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                animation_pers_x_stand = 0;
                animation_pers_x_left = 0;
                animation_pers_x_right = 0;


//System.out.println(timershot);
                if (timershot > 1) {


                    animation_pers_x_shoot = 0;
                    timershot = 0;
                    System.out.println(temperflag);
                    if (temperflag) {
                        System.out.println(123123);
                        shot();
                    }

                }


            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || controller.isLeftPressed()) {
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || controller.isUpPressed()) {
                    playerJump();

                }
                System.out.println(123213);
                animation_pers_x_stand = 0;
                animation_pers_x_right = 0;
                animation_pers_x_left += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime();
                if (animation_pers_x_left > MyGdxGame.Animation_pers_run.length - 1)
                    animation_pers_x_left = 0f;

                sprite.setRegion(MyGdxGame.Animation_pers_run[(int) animation_pers_x_left], 300, 150, 525, 660);


                if (direction_x > -1)
                    direction_x--;
                sprite.setFlip(true, false);
                if (cl.playerCanJump()) {
                    if (rect.getLinearVelocity().x > -Constants.Max_speed)
                        rect.applyForceToCenter(-Constants.Force_x, 0, true);
                } else {
                    if (rect.getLinearVelocity().x > -Constants.Max_speed)
                        rect.applyForceToCenter(-Constants.Force_x / 2, 0, true);
                }


            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controller.isRightPressed()) {
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || controller.isUpPressed()) {
                    playerJump();

                }
                animation_pers_x_stand = 0;
                animation_pers_x_left = 0;
                animation_pers_x_right += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime();
                if (animation_pers_x_right > MyGdxGame.Animation_pers_run.length - 1)
                    animation_pers_x_right = 0f;
                sprite.setRegion(MyGdxGame.Animation_pers_run[(int) animation_pers_x_right], 300, 150, 525, 660);


                if (direction_x < 1)
                    direction_x++;
                sprite.setFlip(false, false);
                if (cl.playerCanJump()) {
                    if (rect.getLinearVelocity().x < Constants.Max_speed)
                        rect.applyForceToCenter(Constants.Force_x, 0, true);
                } else {

                    if (rect.getLinearVelocity().x < Constants.Max_speed)
                        rect.applyForceToCenter(Constants.Force_x / 2, 0, true);
                }


            } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || controller.isUpPressed()) {
                playerJump();

            } else {

                if (cl.playerCanJump()) {
                    rect.setGravityScale(50);
                } else {
                    rect.setGravityScale(1);
                }
                idle();
            }

        }


    }

    private void playerJump() {
//cl.playerCanJump()

        if (cl.playerCanJump()) {
            timer = 0;
            jump = 1;
            animation_pers_x_left = 0;
            animation_pers_x_right = 0;
            rect.setLinearVelocity(rect.getLinearVelocity().x, 0);
            rect.applyForceToCenter(0, Constants.Force_y, true);


        } else if (unlockdoublejump && jump < 3 && timer > 1) {
            animation_pers_y = 0;
            animation_pers_x_left = 0;
            animation_pers_x_right = 0;
            rect.setLinearVelocity(rect.getLinearVelocity().x, 0);
            rect.applyForceToCenter(0, Constants.Force_y, true);
            jump++;

        }
    }

    private void idle() {
        animation_pers_x_left = 0;
        animation_pers_x_right = 0;
        animation_pers_x_stand += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime();
        if (animation_pers_x_stand > MyGdxGame.Animation_pers_idle.length - 1)
            animation_pers_x_stand = 0f;
        sprite.setRegion(MyGdxGame.Animation_pers_idle[(int) animation_pers_x_stand], 300, 150, 525, 660);
        if (direction_x == 1)
            sprite.setFlip(false, false);
        if (direction_x == -1)
            sprite.setFlip(true, false);
    }

    private void playerDead() {
        if (cl.isPlayerDead()) {
            init = false;
            f1.setBounds(positionx - 12800 / Constants.PPM - 12800 / Constants.PPM / 2, 0, 12800 / Constants.PPM, 384 / Constants.PPM);
            f2.setBounds(positionx - 12800 / Constants.PPM / 2, 0, 12800 / Constants.PPM, 384 / Constants.PPM);
            bg1.setBounds(positionx - 12800 / Constants.PPM - 12800 / Constants.PPM / 2, 4, 12800 / Constants.PPM, 1408 / Constants.PPM);
            bg2.setBounds(positionx - 12800 / Constants.PPM / 2, 4, 12800 / Constants.PPM, 1408 / Constants.PPM);
            B2dContactListener.playerDead = false;
            world.destroyBody(rect);
            world.destroyBody(rectfoot);
            createRect(positionx, positiony);
            camera.position.set(rect.getPosition().x, rect.getPosition().y, 0);
            if (rect.getPosition().x < camera.viewportWidth / 2)
                camera.position.set(camera.viewportWidth / 2, camera.position.y, 0);
            if (rect.getPosition().x > 200 - camera.viewportWidth / 2)
                camera.position.set(200 - camera.viewportWidth / 2, camera.position.y, 0);
            if (rect.getPosition().y > 26 - camera.viewportHeight / 2)
                camera.position.set(camera.position.x, 26 - camera.viewportHeight / 2, 0);
            if (rect.getPosition().y < camera.viewportHeight / 2)
                camera.position.set(camera.position.x, camera.viewportHeight / 2, 0);


        }
    }

    private void spawn() {
        Array<Body> bodies = new Array<Body>();

        world.getBodies(bodies);
        for (int i = 0; i < world.getBodyCount(); i++) {

            if (bodies.get(i).getUserData() != null) {

                if (bodies.get(i).getUserData().equals(Constants.TM_flower)) {

                    positionflowerx = bodies.get(i).getPosition().x;
                    positionflowery = bodies.get(i).getPosition().y;
                    world.destroyBody(bodies.get(i));


                }


            }


        }
        bodies.clear();
        world.getBodies(bodies);
        for (int i = 0; i < world.getBodyCount(); i++) {
            if (bodies.get(i).getUserData() != null) {
                if (bodies.get(i).getUserData().equals(Constants.TM_spawn)) {
                    positionx = bodies.get(i).getPosition().x;
                    positiony = bodies.get(i).getPosition().y;
                    world.destroyBody(bodies.get(i));
                    break;

                }


            }


        }
        bodies.clear();
        world.getBodies(bodies);
        for (int i = 0; i < world.getBodyCount(); i++) {
            if (bodies.get(i).getUserData() != null) {
                if (bodies.get(i).getUserData().equals(Constants.TM_checkpoint)) {
                    positioncheckx = bodies.get(i).getPosition().x;
                    positionchecky = bodies.get(i).getPosition().y;
                    world.destroyBody(bodies.get(i));


                }


            }


        }

        bodies.clear();
        world.getBodies(bodies);
        for (int i = 0; i < world.getBodyCount(); i++) {
            if (bodies.get(i).getUserData() != null) {
                if (bodies.get(i).getUserData().equals(Constants.TM_enemy_point)) {
                    positionenemyx = bodies.get(i).getPosition().x;
                    positionenemyy = bodies.get(i).getPosition().y;
                    world.destroyBody(bodies.get(i));


                }


            }


        }
        createRect(positionx, positiony);
        createenemy();
    }

    private void ParseTileMap() {
        TiledObjectUtil.parseTiledObjectlayer(world, map.getLayers().get(Constants.TM_earth_collision).getObjects(), Constants.TM_earth_collision);
        TiledObjectUtil.parseTiledObjectlayer(world, map.getLayers().get(Constants.TM_wall_collision).getObjects(), Constants.TM_wall_collision);
        TiledObjectUtil.parseTiledObjectlayer(world, map.getLayers().get(Constants.TM_kill_zone).getObjects(), Constants.TM_kill_zone);
        TiledObjectUtil.parseTiledObjectlayer(world, map.getLayers().get(Constants.TM_roof_collision).getObjects(), Constants.TM_roof_collision);
        TiledObjectUtil.parseTiledObjectlayer(world, map.getLayers().get(Constants.TM_box).getObjects(), Constants.TM_box);
        TiledObjectUtil.parseTiledObjectlayer(world, map.getLayers().get(Constants.TM_spawn).getObjects(), Constants.TM_spawn);
        TiledObjectUtil.parseTiledObjectlayer(world, map.getLayers().get(Constants.TM_checkpoint).getObjects(), Constants.TM_checkpoint);
        TiledObjectUtil.parseTiledObjectlayer(world, map.getLayers().get(Constants.TM_flower).getObjects(), Constants.TM_flower);
        TiledObjectUtil.parseTiledObjectlayer(world, map.getLayers().get(Constants.TM_enemy_point).getObjects(), Constants.TM_enemy_point);
    }

    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            world.step(STEP_TIME, 6, 2);
        }
    }

    private void createBox() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (int i = 0; i < world.getBodyCount(); i++) {

            if (bodies.get(i).getUserData() != null)
                if (bodies.get(i).getUserData().equals(Constants.TM_box)) {
                    box.add(bodies.get(i));


                }


        }
    }

    private void drawBox() {
        for (Body bod : box) {

            game.batch.draw(boxsprite,
                    bod.getPosition().x - 1,
                    bod.getPosition().y - 1,
                    1,
                    1,
                    128 / Constants.PPM,
                    128 / Constants.PPM,
                    1f,
                    1f,
                    (float) Math.toDegrees(bod.getTransform().getRotation()),
                    0, 0, boxsprite.getWidth(), boxsprite.getHeight(),
                    false, false
            );
        }

    }

    private void parallax() {

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stagef = new Stage(viewport, game.batch);


        stagebg = new Stage(viewport, game.batch);


        bg1 = new Image(new Texture("Tilemap/bg_jungle.png"));
        bg1.setBounds(positionx - 12800 / Constants.PPM, 2 * 2, 12800 / Constants.PPM, 1408 / Constants.PPM);
        bg2 = new Image(new Texture("Tilemap/bg_jungle.png"));
        bg2.setBounds(positionx - 1 / Constants.PPM / 2, 2 * 2, 12800 / Constants.PPM, 1408 / Constants.PPM);

        f1 = new Image(new Texture("Tilemap/wave.png"));
        f1.setBounds(positionx - 12800 / Constants.PPM, 0, 12800 / Constants.PPM, 384 / Constants.PPM);
        f2 = new Image(new Texture("Tilemap/wave.png"));
        f2.setBounds(positionx - 1 / Constants.PPM / 2, 0, 12800 / Constants.PPM, 384 / Constants.PPM);

        table0 = new Table();
        table1 = new Table();

        table1.addActor(f1);
        table1.addActor(f2);

        table0.addActor(bg1);
        table0.addActor(bg2);

        stagef.addActor(table1);


        stagebg.addActor(table0);


    }

    private void bgparallaxdraw() {
        for (Actor a : table0.getChildren()) {
            if (rect.getLinearVelocity().x != 0) {
                a.setX(a.getX() + rect.getLinearVelocity().x / 2 / Constants.PPM);
            }
            if (a.getX() - table0.getX() < rect.getPosition().x - 300) {
                a.setX(rect.getPosition().x + 100 + table0.getX());
            }
        }
        for (Actor a : table1.getChildren()) {

            a.setX(a.getX() + 0.075f);

            if (a.getX() > rect.getPosition().x + 20 - 1 / Constants.PPM / 2) {
                a.setX(rect.getPosition().x - 60 - 1 / Constants.PPM / 2);
            }
        }
    }

    private void cameraposition() {
        if (rect.getPosition().x > camera.viewportWidth / 2 && rect.getPosition().x < 200 - camera.viewportWidth / 2)
            camera.position.set(rect.getPosition().x, camera.position.y, 0);
        if (rect.getPosition().y > camera.viewportHeight / 2 && rect.getPosition().y < 26 - camera.viewportHeight / 2)
            camera.position.set(camera.position.x, rect.getPosition().y, 0);

    }

    private void heckpoint() {
        if (rect.getPosition().x > positioncheckx && incheak != true) {
            positionx = positioncheckx;
            positiony = positionchecky;
            incheak = true;
        }

    }

    private void shotdraw() {

        Array<Body> bodies = new Array<Body>();

        world.getBodies(bodies);
        for (int i = 0; i < world.getBodyCount(); i++) {


            if (bodies.get(i).getUserData() != null) {

                if (bodies.get(i).getUserData().equals(Constants.TM_bullet)) {
                    System.out.println(bodies.get(i).getPosition());
                    temperflag = false;
                    if (cl.isBulletdestroy()) {

                        world.destroyBody(bodies.get(i));
                        temperflag = true;
                    }
                    if (bodies.get(i).getLinearVelocity().x > 0)
                        game.batch.draw(bullet, bodies.get(i).getPosition().x - 2f, bodies.get(i).getPosition().y - 0.75f, 3, 1.5f, 0, 0, 256, 128, true, false);
                    else if (bodies.get(i).getLinearVelocity().x < 0)
                        game.batch.draw(bullet, bodies.get(i).getPosition().x - 1, bodies.get(i).getPosition().y - 0.75f, 3, 1.5f, 0, 0, 256, 128, false, false);


                }


            }


        }
    }

    private void shot() {
        System.out.println(123321);
        PolygonShape shape;
        shape = new PolygonShape();
        shape.setAsBox(0.75f, 0.75f);

        BodyDef bdef = new BodyDef();


        Body body;


        bdef.type = BodyDef.BodyType.DynamicBody;

        if (direction_x >= 0)
            bdef.position.set(rect.getPosition().x + 4, rect.getPosition().y);
        if (direction_x < 0)
            bdef.position.set(rect.getPosition().x - 4, rect.getPosition().y);
        body = world.createBody(bdef);

        body.setUserData(Constants.TM_bullet);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 0;
        fDef.friction = 0;
        fDef.restitution = 1;

        body.createFixture(fDef).setUserData(Constants.TM_bullet);
        body.setBullet(true);
        if (direction_x >= 0)
            body.applyForceToCenter(1000, 0, false);
        if (direction_x < 0)
            body.applyForceToCenter(-1000, 0, false);
        body.setGravityScale(0);
    }

    private void init_enemy() {
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

    private void createenemy() {


        PolygonShape shape1;
        shape1 = new PolygonShape();
        shape1.setAsBox(4, 4);
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(positionenemyx, positionenemyy);
        enemy = world.createBody(bDef);
        enemy.setUserData(Constants.TM_enemy_point);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape1;
        fDef.density = Constants.TM_enemy_density;
        fDef.friction = Constants.TM_enemy_friction;
        fDef.restitution = Constants.TM_enemy_restitution;

        enemy.setFixedRotation(true);
        enemy.createFixture(fDef).setUserData(Constants.TM_enemy_point);
        enemy.applyForceToCenter(2000, 0, false);


    }

    private void moveenemy() {
        if (enemy.getLinearVelocity().x > 0) {
            enemy.setLinearVelocity(4, 0);
            timerenemy = 0;
        } else if (enemy.getLinearVelocity().x < 0) {
            enemy.setLinearVelocity(-4, 0);
            timerenemy = 0;
        }
        if (enemy.getPosition().x > positionenemyx + 15) {
            enemy.setLinearVelocity(0, 0);
            if (timerenemy > 10)
                enemy.setLinearVelocity(-4, 0);
        } else if (enemy.getPosition().x < positionenemyx - 15) {
            enemy.setLinearVelocity(0, 0);
            if (timerenemy > 10)
                enemy.setLinearVelocity(4, 0);
        }

    }

    private void enemydraw() {


        if (enemy.getLinearVelocity().x > 0) {
            System.out.println(12);
            enemysprite.setPosition(enemy.getPosition().x - 4, enemy.getPosition().y - 4 - 0.5f);
            animation_enemy_x_stand = 0;
            animation_enemy_x_right = 0;
            animation_enemy_x_left += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime() / 2;
            if (animation_enemy_x_left > Animation_enemyork1_walk.length - 1)
                animation_enemy_x_left = 0f;

            enemysprite.setRegion(Animation_enemyork1_walk[(int) animation_enemy_x_left], 0, 0, 1619, 1197);
            enemysprite.setFlip(false, false);

            enemysprite.draw(game.batch);
        }
        if (enemy.getLinearVelocity().x < 0) {
            enemysprite.setPosition(enemy.getPosition().x - 4, enemy.getPosition().y - 4 - 0.5f);
            animation_enemy_x_stand = 0;
            animation_enemy_x_left = 0;
            animation_enemy_x_right += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime() / 2;
            if (animation_enemy_x_right > Animation_enemyork1_walk.length - 1)
                animation_enemy_x_right = 0f;
            System.out.println(animation_enemy_x_right);
            enemysprite.setRegion(Animation_enemyork1_walk[(int) animation_enemy_x_right], 0, 0, 1619, 1197);
            enemysprite.setFlip(true, false);
            enemysprite.draw(game.batch);
        }
        if (enemy.getLinearVelocity().x == 0) {
            enemysprite.setPosition(enemy.getPosition().x - 4, enemy.getPosition().y - 4 - 0.5f);
            animation_enemy_x_right = 0;
            animation_enemy_x_left = 0;
            animation_enemy_x_stand += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime() / 2;
            if (animation_enemy_x_stand > Animation_enemyork1_idle.length - 1)
                animation_enemy_x_stand = 0f;

            enemysprite.setRegion(Animation_enemyork1_idle[(int) animation_enemy_x_stand], 0, 0, 1619, 1197);
            enemysprite.setFlip(true, false);
            enemysprite.draw(game.batch);
        }

    }

}
