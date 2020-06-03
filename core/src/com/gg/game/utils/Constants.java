package com.gg.game.utils;

//константные значения
public class Constants {

    public static final String Version = "1.1512v"; //версия
    public static final String Vkurl = "https://vk.com/id390172232"; //вк дебага

    public static final float PPM = 64; //разрешение тел
    public static final int Max_speed = 8; //максимальная скорость
    public static final int Force_y = 5500; //сила прилагаемая к ГГ при управлении
    public static final int Force_x = 400;//сила прилагаемая к ГГ при управлении
    public static final float Speed_animation_x = 15f;//скорость анимации по оси х
    public static final float Speed_animation_y = 13f;//скорость анимации по оси у
    //коррекция анимации прыжка по осям
    public static final short[] Animation_jump_correction_y = new short[]{165, 165, 165, 165, 165, 165, 140, 115, 80, 35, 35, 35, 35, 120, 180, 180, 180, 180, 180, 165, 165};
    public static final short[] Animation_jump_correction_x = new short[]{325, 345, 400, 425, 450, 400, 380, 290, 290, 210, 230, 285, 320, 320, 340, 400, 400, 380, 350, 320, 320};

    //характеристики тел типа "collision-earth" : имя, плотность, трение , прыгучесть
    public static final String TM_earth_collision = "collision-earth";
    public static final float TM_earth_density = 0f;
    public static final float TM_earth_friction = 1f;
    public static final float TM_earth_restitution = 0f;
    //характеристики тел типа "collision-wall" : имя, плотность, трение , прыгучесть
    public static final String TM_wall_collision = "collision-wall";
    public static final float TM_wall_density = 0f;
    public static final float TM_wall_friction = 0f;
    public static final float TM_wall_restitution = 0f;
    //характеристики тел типа "kill_zone" : имя, плотность, трение , прыгучесть
    public static final String TM_kill_zone = "kill_zone";
    public static final float TM_kill_density = 0f;
    public static final float TM_kill_friction = 0f;
    public static final float TM_kill_restitution = 0f;
    //характеристики тел типа "collision-roof" : имя, плотность, трение , прыгучесть
    public static final String TM_roof_collision = "collision-roof";
    public static final float TM_roof_density = 0f;
    public static final float TM_roof_friction = 0f;
    public static final float TM_roof_restitution = 0f;
    //характеристики тел типа "box" : имя, плотность, трение , прыгучесть
    public static final String TM_box = "box";
    public static final float TM_box_density = 0.5f;
    public static final float TM_box_friction = 0.5f;
    public static final float TM_box_restitution = 0f;
    //характеристики тел типа "spawn" : имя
    public static final String TM_spawn = "spawn";
    //характеристики тел типа "playerfoot" : имя, плотность, трение , прыгучесть
    public static final String TM_player_foot = "playerfoot";
    public static final float TM_player_foot_density = 0.3f;
    public static final float TM_player_foot_friction = 0.0f;
    public static final float TM_player_foot_restitution = 0f;
    //характеристики тел типа "playerbody" : имя, плотность, трение , прыгучесть
    public static final String TM_player_body = "playerbody";
    public static final float TM_player_body_density = 0.3f;
    public static final float TM_player_body_friction = 0.6f;
    public static final float TM_player_body_restitution = 0f;
    //характеристики тел типа "point" : имя
    public static final String TM_checkpoint = "point";
    //характеристики тел типа "flower2" : имя
    public static final String TM_flower = "flower2";
    //характеристики тел типа "bullet" : имя, плотность, трение , прыгучесть
    public static final String TM_bullet = "bullet";
    public static final float TM_bullet_density = 0.3f;
    public static final float TM_bullet_friction = 0f;
    public static final float TM_bullet_restitution = 1f;
    //характеристики тел типа "pointenemy" : имя, плотность, трение , прыгучесть
    public static final String TM_enemy_point = "pointenemy";
    public static final float TM_enemy_density = 0.3f;
    public static final float TM_enemy_friction = 0.6f;
    public static final float TM_enemy_restitution = 0f;
}
