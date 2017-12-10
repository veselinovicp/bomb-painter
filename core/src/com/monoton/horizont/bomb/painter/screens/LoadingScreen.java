package com.monoton.horizont.bomb.painter.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.monoton.horizont.bomb.painter.BombPainter;
import com.monoton.horizont.bomb.painter.ui.UIBuilder;


/**
 * Created by monoton on 2.10.2017.
 */
public class LoadingScreen implements Screen {//
    private ShapeRenderer shapeRenderer;
    private float progress;


    private BombPainter bombPainter;
    private UIBuilder uiBuilder;


    public LoadingScreen(BombPainter bombPainter) {

        this.bombPainter = bombPainter;
        this.shapeRenderer = new ShapeRenderer();
        uiBuilder = bombPainter.getUiBuilder();

    }


    private void queueAssets() {

        uiBuilder.startLoading();
    }

    @Override
    public void show() {
        System.out.println("LOADING");

        this.progress = 0f;
        queueAssets();
    }

    private void update(float delta) {
        progress = MathUtils.lerp(progress, uiBuilder.getProgress(), .1f);
        if (uiBuilder.update() && progress >= uiBuilder.getProgress() - .001f) {
            bombPainter.setScreen(new GameScreen(bombPainter.getUiBuilder()));
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, Gdx.graphics.getWidth() - 64, 16);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(32, Gdx.graphics.getHeight() / 2 - 8, progress * (Gdx.graphics.getWidth() - 64), 16);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

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
        shapeRenderer.dispose();
    }
}
