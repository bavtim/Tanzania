package com.gg.game.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.gg.game.utils.Constants.TM_box;
import static com.gg.game.utils.Constants.TM_box_density;
import static com.gg.game.utils.Constants.TM_box_friction;
import static com.gg.game.utils.Constants.TM_box_restitution;
import static com.gg.game.utils.Constants.TM_earth_collision;
import static com.gg.game.utils.Constants.TM_earth_density;
import static com.gg.game.utils.Constants.TM_earth_friction;
import static com.gg.game.utils.Constants.TM_earth_restitution;
import static com.gg.game.utils.Constants.TM_kill_density;
import static com.gg.game.utils.Constants.TM_kill_friction;
import static com.gg.game.utils.Constants.TM_kill_restitution;
import static com.gg.game.utils.Constants.TM_kill_zone;
import static com.gg.game.utils.Constants.TM_roof_collision;
import static com.gg.game.utils.Constants.TM_roof_density;
import static com.gg.game.utils.Constants.TM_roof_friction;
import static com.gg.game.utils.Constants.TM_roof_restitution;
import static com.gg.game.utils.Constants.TM_wall_collision;
import static com.gg.game.utils.Constants.TM_wall_density;
import static com.gg.game.utils.Constants.TM_wall_friction;
import static com.gg.game.utils.Constants.TM_wall_restitution;


public class TiledObjectUtil {

    private static Vector2 position;

    public static void parseTiledObjectlayer(World world, MapObjects objects, String str) {
        float density = 0f, friction = 0f, restitution = 0f;
        position = new Vector2();

        for (MapObject object : objects) {
            switch (str) {
                case TM_earth_collision:
                    density = TM_earth_density;
                    friction = TM_earth_friction;
                    restitution = TM_earth_restitution;
                    break;
                case TM_box:
                    density = TM_box_density;
                    friction = TM_box_friction;
                    restitution = TM_box_restitution;
                    break;
                case TM_wall_collision:
                    density = TM_wall_density;
                    friction = TM_wall_friction;
                    restitution = TM_wall_restitution;
                    break;
                case TM_kill_zone:
                    density = TM_kill_density;
                    friction = TM_kill_friction;
                    restitution = TM_kill_restitution;
                    break;
                case TM_roof_collision:
                    density = TM_roof_density;
                    friction = TM_roof_friction;
                    restitution = TM_roof_restitution;
                    break;


            }
            if (object instanceof RectangleMapObject) {

                PolygonShape shape;
                shape = getRectangle((RectangleMapObject) object);
                BodyDef bdef = new BodyDef();
                bdef.position.set(10, 10);

                Body body;
                if (str.equals(TM_box)) {

                    bdef.type = BodyDef.BodyType.DynamicBody;
                } else
                    bdef.type = BodyDef.BodyType.StaticBody;

                bdef.position.set(position);

                body = world.createBody(bdef);
                body.setUserData(str);
                FixtureDef fDef = new FixtureDef();
                fDef.shape = shape;
                fDef.density = density;
                fDef.friction = friction;
                fDef.restitution = restitution;

                body.createFixture(fDef).setUserData(str);


            } else if (object instanceof PolygonMapObject) {

                PolygonShape shape;
                shape = getPolygon((PolygonMapObject) object);
                BodyDef bdef = new BodyDef();

                Body body;
                if (str.equals(TM_box))
                    bdef.type = BodyDef.BodyType.DynamicBody;
                else
                    bdef.type = BodyDef.BodyType.StaticBody;

                body = world.createBody(bdef);
                body.setUserData(str);
                FixtureDef fDef = new FixtureDef();
                fDef.shape = shape;
                fDef.density = density;
                fDef.friction = friction;
                fDef.restitution = restitution;
                body.createFixture(fDef).setUserData(str);


            } else if (object instanceof PolylineMapObject) {

                ChainShape shape;
                shape = getPolyline((PolylineMapObject) object);
                BodyDef bdef = new BodyDef();
                Body body;
                if (str.equals(TM_box))
                    bdef.type = BodyDef.BodyType.DynamicBody;
                else
                    bdef.type = BodyDef.BodyType.StaticBody;
                body = world.createBody(bdef);
                body.setUserData(str);
                FixtureDef fDef = new FixtureDef();
                fDef.shape = shape;
                fDef.density = density;
                fDef.friction = friction;
                fDef.restitution = restitution;
                body.createFixture(fDef).setUserData(str);


            } else if (object instanceof CircleMapObject) {


                CircleShape shape;
                shape = getCircle((CircleMapObject) object);
                BodyDef bdef = new BodyDef();
                bdef.position.set(position);
                Body body;
                if (str.equals(TM_box))
                    bdef.type = BodyDef.BodyType.KinematicBody;
                else
                    bdef.type = BodyDef.BodyType.StaticBody;
                body = world.createBody(bdef);
                body.setUserData(str);
                FixtureDef fDef = new FixtureDef();
                fDef.shape = shape;

                fDef.density = density;
                fDef.friction = friction;
                fDef.restitution = restitution;
                body.createFixture(fDef).setUserData(str);


            }

        }
    }

    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {

        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();

        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / Constants.PPM,
                (rectangle.y + rectangle.height * 0.5f) / Constants.PPM);
        polygon.setAsBox(rectangle.width * 0.5f / Constants.PPM,
                rectangle.height * 0.5f / Constants.PPM,
                new Vector2(),
                0.0f);
        position = size;
        return polygon;
    }

    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / Constants.PPM);
        circleShape.setPosition(new Vector2(circle.x / Constants.PPM, circle.y / Constants.PPM));
        position = new Vector2(circle.x / Constants.PPM, circle.y / Constants.PPM);
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {

            worldVertices[i] = vertices[i] / Constants.PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / Constants.PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / Constants.PPM;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);

        return chain;
    }


}
