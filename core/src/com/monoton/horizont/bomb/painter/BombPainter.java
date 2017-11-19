package com.monoton.horizont.bomb.painter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BombPainter extends ApplicationAdapter {
	private OrthographicCamera camera;
	private FPSLogger logger;

	private World world;
	private Box2DDebugRenderer renderer;
	private float width, height;

	private Body circleBody;
	
	@Override
	public void create () {
		width = Gdx.graphics.getWidth()/5;
		height = Gdx.graphics.getHeight()/5;

		camera = new OrthographicCamera(width, height);
		camera.position.set(width*0.5f, height * 0.5f,0);
		camera.update();

		world = new World(new Vector2(0, -9.8f), false);
		renderer = new Box2DDebugRenderer();
		logger = new FPSLogger();

		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyDef.BodyType.DynamicBody;
		circleDef.position.set(width/2, height/2);

		circleBody = world.createBody(circleDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(3f);

		FixtureDef circleFixture = new FixtureDef();
		circleFixture.shape = circleShape;
		circleFixture.density = 0.4f;
		circleFixture.friction = 0.2f;
		circleFixture.restitution = 0.8f;

		circleBody.createFixture(circleFixture);

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0,3);

		Body groundBody = world.createBody(groundBodyDef);

		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(camera.viewportWidth*2,3);

		groundBody.createFixture(groundBox,0);




	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render(world, camera.combined);
		world.step(1/60f, 6, 2);
		logger.log();

	}
	
	@Override
	public void dispose () {
		world.dispose();

	}
}
