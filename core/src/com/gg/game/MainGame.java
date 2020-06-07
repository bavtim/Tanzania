package com.gg.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
    private static final float STEP_TIME = 1f / 60f;//шаг времени
    //флаги
    public static boolean menumodeflag = false;
    private static float positionx = 10f, positiony = 10f;//дефолтное расположение точки спавна персонажа
    private static float positioncheckx = 10f, positionchecky = 10f;//дефолтное расположение точки чекпоинта
    private static boolean incheak = false;//нахождение в чеке
    private static boolean blockanimwater = false;
    private float positionenemyx = 0, positionenemyy = 0;//точка спавна врага
    private Body enemy;//физ тело врага
    private float animation_pers_x_shoot; //стартовое значение номера спрайта
    private Stage stagebg;//сцена для параллакса(задний фон)
    private Image bg1;
    private Image f2;
    private Image f1;
    private Stage stagef;//сцена для параллакса(передний фон)
    private Body rectfoot;
    // картинки для параллакса
    private Image bg2;
    //составные части ГГ
    private Body rect;
    //прослушка контактирующих объектов
    private B2dContactListener cl;
    //массив тел коробок
    private Array<Body> box;
    private Table table1;
    private static MyGdxGame game;
    //камера обзора
    private OrthographicCamera camera;
    //стол для параллакс эффекта
    private Table table0;
    //тайлмапа
    private TiledMap map;
    //рендер тайлмапы
    private TiledMapRenderer renderer;
    // мир тел
    private World world;
    //дебаг рендер
    private Box2DDebugRenderer rend;
    private float animation_pers_x_right = 0;
    private float animation_pers_x_stand = 0;
    private float animation_pers_y = 0;
    //cпрайт ГГ
    private Sprite sprite;
    //стартовый анимации
    private float animation_pers_x_left = 0;
    // переменная инициализации
    private boolean init = false;
    //направление движения по х
    private byte direction_x = 0;
    //текстура коробки
    private Texture boxsprite;
    //cглаживание просадок фпс
    private float accumulator = 0;
    //флаг анимации
    private boolean flag = true;
    private int[] cloud;
    private int[] check;
    private int[] flower;
    //позиция цветка
    private float positionflowerx = 0, positionflowery = 0;
    //массивы слоев для отрисовки
    private int[] earth;
    //количество прыжков
    private byte jump = 0;
    //появление портала
    private boolean portal;
    private Viewport viewport;
    private float timeportal = 0;
    private Texture portaltexture;
    public static boolean blockcontrol;
    private boolean temp = true;
    //отрисовка  персонажа
    private boolean pers;
    private float timershot = 2;
    private boolean unlockdoublejump = false;
    //контроллер управления+ отрисовка сцен
    private Controller controller;
    private float timerenemy = 0;
    //таймеры анимации
    private float timer = 0;
    private boolean temperflag = true;
    //текстура пули
    private Texture bullet;
    //флаги
    private boolean flag1 = true;
    private float animation_enemy_x_right = 0;
    private float animation_enemy_x_left = 0;
    //спрайт врага
    private Sprite enemysprite;
    //стартовые анимации ГГ
    private float animation_enemy_x_stand = 0;
    private boolean exit = false;


    //инициализация переменных
    public MainGame(MyGdxGame game) {
        MainGame.game = game;
        game.music.pause();
        game.maingame.setVolume(MyGdxGame.prefs.getInteger("volume", 10) / 100f);
        game.maingame.setLooping(true);
        game.maingame.play();


        if (!MyGdxGame.prefs.getBoolean("music"))
            game.maingame.pause();

        bullet = new Texture("Tilemap/bullet.png");

        box = new Array<>();
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
        sprite = new Sprite(Levelboot.Animation_pers_run[12], 300, 150, 525, 660);
        sprite.setPosition(Gdx.graphics.getWidth() / 2f - sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f);
        controller = new Controller();

        boxsprite = new Texture("Tilemap/box.png");
        createBox();

        earth = new int[]{0, 1};
        cloud = new int[]{2};
        check = new int[]{3};
        flower = new int[]{4};

        parallax();
        animation_pers_x_shoot = Levelboot.Animation_pers_blaster_shoot.length;

        enemysprite = new Sprite();
        enemysprite.setSize(8, 8);

    }


    @Override
    public void show() {

    }

    //переход в главное меню
    public static void backtomenu() {

        game.setScreen(MyGdxGame.ScreenMenu);

    }

    //перезапуск уровня
    public static void restart() {
        game.setScreen(new Levelboot(game));
    }

    //отрисовка всего и вся
    @Override
    public void render(float delta) {
        if (exit) {
            game.setScreen(new Win_screen(game, (byte) 1));
        } else {
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
            MyGdxGame.batch.setProjectionMatrix(camera.combined);
            stagebg.draw();
            renderer.setView(camera);
            renderer.render(earth);

            if (incheak)
                renderer.render(check);
            flower();
            stagef.draw();
            if (MyGdxGame.prefs.getBoolean("debugmode", true))
                rend.render(world, camera.combined);
            Draw();
            renderer.render(cloud);


            if (controller.isEscPressed())
                menumodeflag = true;

            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                if (Controller.escPressed) {
                    Controller.escPressed = false;
                    controller.createControll();
                } else {
                    Controller.escPressed = true;
                    controller.createmenu();
                }


            }

            menumode();


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

    //отрисовка портала
    private void finsih() {

        if (flag) {
            timeportal += Gdx.graphics.getDeltaTime() * 3f;

        } else {
            timeportal -= Gdx.graphics.getDeltaTime() * 3f;

        }

        if (timeportal / 4 < 1) {
            MyGdxGame.batch.draw(
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

        if (timeportal / 4 > 1) {

            MyGdxGame.batch.draw(
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
        if (rect.getPosition().x >= positionenemyx + 24) {
            //     game.setScreen(new Win_screen(game, (byte) 0));
            //  game.setScreen(new Shop(game));
//            size += Gdx.graphics.getDeltaTime() * 7;
            // blockcontrol = true;
            System.out.println("12121212");
            exit = true;
//            MyGdxGame.batch.draw(
//                    portaltexture,
//                    positionenemyx + 26,
//                    positionenemyy - 2,
//                    3,
//                    3,
//                    384 / Constants.PPM,
//                    384 / Constants.PPM,
//                    size,
//                    size,
//                    (float) Math.toDegrees(Math.abs(timeportal)),
//                    0, 0, 384, 384,
//                    false, false
//
//            );
        }
    }

    @Override
    public void hide() {
        dispose();
    }

    //отрисовка цветка
    private void flower() {

        if (rect.getPosition().x < positionflowerx && !unlockdoublejump) {

            renderer.render(flower);
        } else {
            unlockdoublejump = true;
        }
    }

    //анимация и отрисовка портала
    private void portal() {

        if (flag) {
            timeportal += Gdx.graphics.getDeltaTime() * 3f;
            blockcontrol = true;
        } else {
            timeportal -= Gdx.graphics.getDeltaTime() * 3f;

        }


        if (timeportal / 4 < 1) {
            MyGdxGame.batch.draw(
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
            MyGdxGame.batch.draw(
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
    //создания тела персонажа из связки 2 тел

    private void createRect(float x, float y) {

        PolygonShape shape;
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

    @Override
    public void dispose() {


        controller.dispose();
        map.dispose();
        world.dispose();
        rend.dispose();
        stagebg.dispose();
        stagef.dispose();
        boxsprite.dispose();
        portaltexture.dispose();
        bullet.dispose();

        for (int i = 0; i < Levelboot.Animation_pers_blaster_shoot.length; i++)
            Levelboot.Animation_pers_blaster_shoot[i].getTexture().dispose();
        for (int i = 0; i < Levelboot.Animation_pers_hurt.length; i++)
            Levelboot.Animation_pers_hurt[i].getTexture().dispose();
        for (int i = 0; i < Levelboot.Animation_pers_idle.length; i++)
            Levelboot.Animation_pers_idle[i].getTexture().dispose();
        for (int i = 0; i < Levelboot.Animation_pers_jump.length; i++)
            Levelboot.Animation_pers_jump[i].getTexture().dispose();
        for (int i = 0; i < Levelboot.Animation_pers_run.length; i++)
            Levelboot.Animation_pers_run[i].getTexture().dispose();
        for (int i = 0; i < Levelboot.Animation_enemyork1_walk.length; i++)
            Levelboot.Animation_enemyork1_walk[i].getTexture().dispose();
        for (int i = 0; i < Levelboot.Animation_enemyork1_hurt.length; i++)
            Levelboot.Animation_enemyork1_hurt[i].getTexture().dispose();
        for (int i = 0; i < Levelboot.Animation_enemyork1_idle.length; i++)
            Levelboot.Animation_enemyork1_idle[i].getTexture().dispose();
        game.maingame.pause();
        game.music.setVolume(MyGdxGame.prefs.getInteger("volume", 10) / 100f);
        game.music.setLooping(true);
        game.music.play();


        if (!MyGdxGame.prefs.getBoolean("music"))
            game.music.pause();
    }

    //прыжки ГГ
    private void playerJump() {

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

    //отрисовка анимации ГГ
    private void Draw() {
        if (init) {
            if (!cl.playerCanJump()) {
                animation_pers_y += Constants.Speed_animation_y * Gdx.graphics.getDeltaTime();
                if (animation_pers_y > Levelboot.Animation_pers_jump.length - 1)
                    animation_pers_y = Levelboot.Animation_pers_jump.length - 1;

                sprite.setRegion(Levelboot.Animation_pers_jump[(int) animation_pers_y], Constants.Animation_jump_correction_x[(int) animation_pers_y], Constants.Animation_jump_correction_y[(int) animation_pers_y], 145, 160);

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
        if (animation_pers_x_shoot > Levelboot.Animation_pers_blaster_shoot.length)
            animation_pers_x_shoot = Levelboot.Animation_pers_blaster_shoot.length;
        if (cl.playerCanJump()) {

            if (animation_pers_x_shoot < Levelboot.Animation_pers_blaster_shoot.length - 1) {
                sprite.setRegion(Levelboot.Animation_pers_blaster_shoot[(int) animation_pers_x_shoot], 75, 37, 131, 165);
                if (direction_x > 0) {
                    sprite.setFlip(false, false);
                } else if (direction_x < 0) {
                    sprite.setFlip(true, false);
                }

            }
        }

        MyGdxGame.batch.begin();
        drawBox();
        if (portal)
            portal();
        if (pers)
            sprite.draw(MyGdxGame.batch);
        if (flag1)
            enemydraw();
        if (!flag1)
            finsih();

        shotdraw();
        MyGdxGame.batch.end();
    }

    //смерть ГГ
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

    //спавн предметов
    private void spawn() {
        Array<Body> bodies = new Array<>();

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

    //парсер объектов
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

    //шаг времени мира
    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            world.step(STEP_TIME, 6, 2);
        }
    }

    //создание тел коробок
    private void createBox() {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for (int i = 0; i < world.getBodyCount(); i++) {

            if (bodies.get(i).getUserData() != null)
                if (bodies.get(i).getUserData().equals(Constants.TM_box)) {
                    box.add(bodies.get(i));


                }


        }
    }

    //отрисовка коробок
    private void drawBox() {
        for (Body bod : box) {

            MyGdxGame.batch.draw(boxsprite,
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

    //создание параллакс эффекта
    private void parallax() {

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stagef = new Stage(viewport, MyGdxGame.batch);


        stagebg = new Stage(viewport, MyGdxGame.batch);


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

    //работа параллакс эффекта
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
            if (!blockanimwater)
            a.setX(a.getX() + 0.075f);

            if (a.getX() > rect.getPosition().x + 20 - 1 / Constants.PPM / 2) {
                a.setX(rect.getPosition().x - 60 - 1 / Constants.PPM / 2);
            }
        }
    }

    //работа камеры наблюдения
    private void cameraposition() {
        if (rect.getPosition().x > camera.viewportWidth / 2 && rect.getPosition().x < 200 - camera.viewportWidth / 2)
            camera.position.set(rect.getPosition().x, camera.position.y, 0);
        if (rect.getPosition().y > camera.viewportHeight / 2 && rect.getPosition().y < 26 - camera.viewportHeight / 2)
            camera.position.set(camera.position.x, rect.getPosition().y, 0);

    }

    //чекпоинт
    private void heckpoint() {
        if (rect.getPosition().x > positioncheckx && !incheak) {
            positionx = positioncheckx;
            positiony = positionchecky;
            incheak = true;
        }

    }

    //отрисовка тел пуль
    private void shotdraw() {

        Array<Body> bodies = new Array<>();

        world.getBodies(bodies);
        for (int i = 0; i < world.getBodyCount(); i++) {


            if (bodies.get(i).getUserData() != null) {

                if (bodies.get(i).getUserData().equals(Constants.TM_bullet)) {

                    temperflag = false;
                    if (cl.isBulletdestroy()) {

                        world.destroyBody(bodies.get(i));
                        temperflag = true;
                    }
                    if (bodies.get(i).getLinearVelocity().x > 0)
                        MyGdxGame.batch.draw(bullet, bodies.get(i).getPosition().x - 2f, bodies.get(i).getPosition().y - 0.75f, 3, 1.5f, 0, 0, 256, 128, true, false);
                    else if (bodies.get(i).getLinearVelocity().x < 0)
                        MyGdxGame.batch.draw(bullet, bodies.get(i).getPosition().x - 1, bodies.get(i).getPosition().y - 0.75f, 3, 1.5f, 0, 0, 256, 128, false, false);


                }


            }


        }
    }

    //создание тел пуль
    private void shot() {
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
        fDef.density = Constants.TM_bullet_density;
        fDef.friction = Constants.TM_bullet_friction;
        fDef.restitution = Constants.TM_bullet_restitution;

        body.createFixture(fDef).setUserData(Constants.TM_bullet);
        body.setBullet(true);
        if (direction_x >= 0)
            body.applyForceToCenter(1000, 0, false);
        if (direction_x < 0)
            body.applyForceToCenter(-1000, 0, false);
        body.setGravityScale(0);
        if (MyGdxGame.prefs.getBoolean("vibration", true))
            Gdx.input.vibrate(750);
    }

    //создание врагов
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

    //передвижение врагов
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

    //управление ГГ
    private void gamepad_delta_xy() {
        rect.setGravityScale(1);
        if (!blockcontrol) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || controller.isBulletPressed()) {

                animation_pers_x_left = 0;
                animation_pers_x_right = 0;


                if (timershot > 1) {


                    animation_pers_x_shoot = 0;
                    timershot = 0;

                    if (temperflag) {

                        shot();
                    }

                }


            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || controller.isLeftPressed()) {
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || controller.isUpPressed()) {
                    playerJump();

                }

                animation_pers_x_stand = 0;
                animation_pers_x_right = 0;
                animation_pers_x_left += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime();
                if (animation_pers_x_left > Levelboot.Animation_pers_run.length - 1)
                    animation_pers_x_left = 0f;

                sprite.setRegion(Levelboot.Animation_pers_run[(int) animation_pers_x_left], 75, 37, 131, 165);


                if (direction_x > -1)
                    direction_x--;
                sprite.setFlip(true, false);
                if (cl.playerCanJump()) {
                    if (rect.getLinearVelocity().x > -Constants.Max_speed)
                        rect.applyForceToCenter(-Constants.Force_x, 0, true);
                } else {
                    if (rect.getLinearVelocity().x > -Constants.Max_speed) {
                        rect.applyForceToCenter(-Constants.Force_x / 2f, 0, true);
                    }
                }


            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controller.isRightPressed()) {
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || controller.isUpPressed()) {
                    playerJump();

                }
                animation_pers_x_stand = 0;
                animation_pers_x_left = 0;
                animation_pers_x_right += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime();
                if (animation_pers_x_right > Levelboot.Animation_pers_run.length - 1)
                    animation_pers_x_right = 0f;
                sprite.setRegion(Levelboot.Animation_pers_run[(int) animation_pers_x_right], 75, 37, 131, 165);


                if (direction_x < 1)
                    direction_x++;
                sprite.setFlip(false, false);
                if (cl.playerCanJump()) {
                    if (rect.getLinearVelocity().x < Constants.Max_speed)
                        rect.applyForceToCenter(Constants.Force_x, 0, true);
                } else {

                    if (rect.getLinearVelocity().x < Constants.Max_speed)
                        rect.applyForceToCenter(Constants.Force_x / 2f, 0, true);
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

    //режим меню
    private void menumode() {
        if (menumodeflag) {
            blockcontrol = true;

            menumodeflag = false;
            blockanimwater = true;
        } else {
            blockanimwater = false;
        }

    }

    //отрисовка бездействия персонажа
    private void idle() {
        animation_pers_x_left = 0;
        animation_pers_x_right = 0;
        animation_pers_x_stand += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime();
        if (animation_pers_x_stand > Levelboot.Animation_pers_idle.length - 1)
            animation_pers_x_stand = 0f;
        sprite.setRegion(Levelboot.Animation_pers_idle[(int) animation_pers_x_stand], 75, 37, 131, 165);
        if (direction_x == 1)
            sprite.setFlip(false, false);
        if (direction_x == -1)
            sprite.setFlip(true, false);
    }

    //отрисовка врага
    private void enemydraw() {


        if (enemy.getLinearVelocity().x > 0) {

            enemysprite.setPosition(enemy.getPosition().x - 4, enemy.getPosition().y - 4 - 0.5f);
            animation_enemy_x_stand = 0;
            animation_enemy_x_right = 0;
            animation_enemy_x_left += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime() / 2;
            if (animation_enemy_x_left > Levelboot.Animation_enemyork1_walk.length - 1)
                animation_enemy_x_left = 0f;

            enemysprite.setRegion(Levelboot.Animation_enemyork1_walk[(int) animation_enemy_x_left], 0, 0, 324, 240);
            enemysprite.setFlip(false, false);

            enemysprite.draw(MyGdxGame.batch);
        }
        if (enemy.getLinearVelocity().x < 0) {
            enemysprite.setPosition(enemy.getPosition().x - 4, enemy.getPosition().y - 4 - 0.5f);
            animation_enemy_x_stand = 0;
            animation_enemy_x_left = 0;
            animation_enemy_x_right += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime() / 2;
            if (animation_enemy_x_right > Levelboot.Animation_enemyork1_walk.length - 1)
                animation_enemy_x_right = 0f;

            enemysprite.setRegion(Levelboot.Animation_enemyork1_walk[(int) animation_enemy_x_right], 0, 0, 324, 240);
            enemysprite.setFlip(true, false);
            enemysprite.draw(MyGdxGame.batch);
        }
        if (enemy.getLinearVelocity().x == 0) {
            enemysprite.setPosition(enemy.getPosition().x - 4, enemy.getPosition().y - 4 - 0.5f);
            animation_enemy_x_right = 0;
            animation_enemy_x_left = 0;
            animation_enemy_x_stand += Constants.Speed_animation_x * Gdx.graphics.getDeltaTime() / 2;
            if (animation_enemy_x_stand > Levelboot.Animation_enemyork1_idle.length - 1)
                animation_enemy_x_stand = 0f;

            enemysprite.setRegion(Levelboot.Animation_enemyork1_idle[(int) animation_enemy_x_stand], 0, 0, Levelboot.Animation_enemyork1_idle[(int) animation_enemy_x_stand].getRegionWidth(), Levelboot.Animation_enemyork1_idle[(int) animation_enemy_x_stand].getRegionHeight());
            enemysprite.setFlip(true, false);
            enemysprite.draw(MyGdxGame.batch);

        }

    }
}
