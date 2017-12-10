package com.monoton.horizont.bomb.painter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.monoton.horizont.bomb.painter.screens.LoadingScreen;
import com.monoton.horizont.bomb.painter.ui.UIBuilder;

public class BombPainter extends Game {

	private UIBuilder uiBuilder = new UIBuilder();





	@Override
	public void create () {


		System.out.println("start creating CrowndPatternCommand");


		setScreen(new LoadingScreen(this));

		System.out.println("end creating CrowndPatternCommand");


	}



	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}


	@Override
	public void render () {
		super.render();


	}

	@Override
	public void dispose () {
		super.dispose();
		uiBuilder.dispose();
		getScreen().dispose();


	}

	public UIBuilder getUiBuilder() {
		return uiBuilder;
	}

}
