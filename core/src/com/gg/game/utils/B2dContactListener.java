package com.gg.game.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


public class B2dContactListener implements ContactListener {
    private boolean bulletdestroy=false;
    private int numFootContacts=0;
    public static boolean playerDead;
    private int enemylive=3;
    public B2dContactListener(){}
    @Override
    public void beginContact(Contact contact) {
        bulletdestroy=false;
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        System.out.println("A "+fa.getUserData());
        System.out.println("b "+fb.getUserData());
        if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_bullet)||fb.getUserData() != null && fb.getUserData().equals(Constants.TM_bullet)) {
            if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_bullet)&&fb.getUserData() != null && fb.getUserData().equals(Constants.TM_enemy_point)){

                enemylive--;
            }
            if(fb.getUserData() != null && fb.getUserData().equals(Constants.TM_bullet)&&fa.getUserData() != null && fa.getUserData().equals(Constants.TM_enemy_point)){

                enemylive--;
            }
            bulletdestroy=true;
        }


        if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_box)||fb.getUserData() != null && fb.getUserData().equals(Constants.TM_box)){
            if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_box)&&fb.getUserData() != null && fb.getUserData().equals(Constants.TM_player_foot)){
                numFootContacts++;
            }else if(fb.getUserData() != null && fb.getUserData().equals(Constants.TM_box)&&fa.getUserData() != null && fa.getUserData().equals(Constants.TM_player_foot)){
                numFootContacts++;
            }else
                return;
        }



        if(fa == null || fb == null) return;
        if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_earth_collision)&&fb.getUserData() != null && fb.getUserData().equals(Constants.TM_player_foot)) {
            numFootContacts++;
        }
        if(fb.getUserData() != null && fb.getUserData().equals(Constants.TM_earth_collision)&&fa.getUserData() != null && fa.getUserData().equals(Constants.TM_player_foot)) {
            numFootContacts++;
        }
        if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_kill_zone)) {
            playerDead = true;
        }
        if(fb.getUserData() != null && fb.getUserData().equals(Constants.TM_kill_zone)) {
            playerDead = true;
        }



    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_bullet)||fb.getUserData() != null && fb.getUserData().equals(Constants.TM_bullet)) {

            bulletdestroy=false;
        }
        if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_box)||fb.getUserData() != null && fb.getUserData().equals(Constants.TM_box)){
            if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_box)&&fb.getUserData() != null && fb.getUserData().equals(Constants.TM_player_foot)){
                numFootContacts--;
            }else if(fb.getUserData() != null && fb.getUserData().equals(Constants.TM_box)&&fa.getUserData() != null && fa.getUserData().equals(Constants.TM_player_foot)){
                numFootContacts--;
            }else
                return;
        }
        if(fa == null || fb == null) return;

        if(fa.getUserData() != null && fa.getUserData().equals(Constants.TM_earth_collision)&&fb.getUserData() != null && fb.getUserData().equals(Constants.TM_player_foot)) {
            numFootContacts--;
        }
        if(fb.getUserData() != null && fb.getUserData().equals(Constants.TM_earth_collision)&&fa.getUserData() != null && fa.getUserData().equals(Constants.TM_player_foot)) {
            numFootContacts--;
        }

    }
    public boolean playerCanJump() { return numFootContacts > 0; }
    public boolean isPlayerDead() {return playerDead; }
    public boolean isBulletdestroy() {return bulletdestroy; }
    public boolean isEnemydamage(){
        return enemylive <= 0;
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
