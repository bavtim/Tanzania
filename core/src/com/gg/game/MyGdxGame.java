package com.gg.game;
//импорты библиотек

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gg.game.utils.NotificationHandler;

public class MyGdxGame extends Game {
	public NotificationHandler notificationHandler;
	public static Screen ScreenMenu;//экран загрузки
	public static Screen ScreenMenuLevelSelect;//экран выбора уровня
	public static SpriteBatch batch;//холст
	public static Preferences prefs;//сохранненые настройки
	FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	public static OrthographicCamera camera;//карера
	public static FitViewport viewport;//переменная обрезания камеры
	//массивы текстур для анимации персонажа

	FreeTypeFontGenerator generator;//генератор шрифта
	BitmapFont font;//шрифт
	Music music;//музыка меню
	Sound tab1;//звуки закрытия окон
	Sound tab2;//звуки переключения и щелчков
	Sound denied;//звуки отказа доступа
	private Texture texture;//временная текстура для заполнения массива текстур для анимации персонажей

	public void create() {


		//параметры запуска
		Gdx.graphics.setResizable(false);
		Gdx.graphics.setTitle("Танзания");
		//подгрузка файлов
		prefs = Gdx.app.getPreferences("Data");
		denied= Gdx.audio.newSound(Gdx.files.internal("music/denied.mp3"));
		tab1= Gdx.audio.newSound(Gdx.files.internal("music/Tab1.mp3"));
		tab2= Gdx.audio.newSound(Gdx.files.internal("music/Tab2.mp3"));
		music= Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
		//инициализация шрифта
		generator = new FreeTypeFontGenerator(Gdx.files.internal("ttf/segoeprb.ttf"));
		batch = new SpriteBatch();
		parameter.characters ="абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
		parameter.size = Gdx.graphics.getHeight()/18;
		font = generator.generateFont(parameter);
		generator.dispose();
		font.setColor(0f, 0f, 0f, 1f);
		//инициализация камеры
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		//создание скринов активностей
		screen();
		//переход на экран загрузки игры
        setScreen(new Bootscreen(this));



	}

	public void render() {
		super.render();

	}
	public void dispose() {
		batch.dispose();
		generator.dispose();
		font.dispose();
	}

    //cоздание экранов
	public void screen(){

	ScreenMenu = new Menu(this);
	ScreenMenuLevelSelect= new MenuLevelSelect(this);

	}

	public void setNotificationHandler(NotificationHandler handler) {
		this.notificationHandler = handler;
	}


}