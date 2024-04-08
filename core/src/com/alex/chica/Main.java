package com.alex.chica;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends Game {
	final int SCR_HEIGHT = 480;
	final int SCR_WIDTH = 800;

	Screen currentScreen;
	SpriteBatch batch;
	BitmapFont font;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		img = new Texture("badlogic.jpg");

		currentScreen = new GameScreen(this);
		this.setScreen(currentScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
