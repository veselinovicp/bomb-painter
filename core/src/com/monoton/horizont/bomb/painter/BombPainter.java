package com.monoton.horizont.bomb.painter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class BombPainter extends ApplicationAdapter implements InputProcessor{
	private OrthographicCamera camera;
	private FPSLogger logger;

	private World world;
	private Box2DDebugRenderer renderer;
	private float width, height;


	
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

		createCircles();

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0,3);

		Body groundBody = world.createBody(groundBodyDef);

		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(camera.viewportWidth*2,3);

		groundBody.createFixture(groundBox,0);

		Gdx.input.setInputProcessor(this);




	}

	private void createCircles() {

		for(int i=10; i<width;i+=10){
			createCircleBody(i, i);
		}

	}

    private float  DEGTORAD = 0.0174532925199432957f;
	private void explosion(float x, float y) {
		int numRays = 100;
		float blastPower = 100000;
		for (int i = 0; i < numRays; i++) {
			float angle = (i / (float) numRays) * 360 * DEGTORAD;

			Vector2 rayDir = new Vector2 (MathUtils.sin(angle), MathUtils.cos(angle));

			BodyDef bd = new BodyDef();
			bd.type = BodyDef.BodyType.DynamicBody;
			bd.fixedRotation = true; // rotation not necessary
			bd.bullet = true; // prevent tunneling at high speed
			bd.linearDamping = 10; // drag due to moving through air
			bd.gravityScale = 0; // ignore gravity
//			bd.position.set(width/2, height/2); // start at blast center
			bd.position.set(x, y);

			bd.linearVelocity.set(new Vector2(blastPower*rayDir.x, blastPower * rayDir.y));
			Body body = world.createBody(bd);


			CircleShape circleShape = new CircleShape();
//			circleShape.m_radius = 0.05; // very small
			circleShape.setRadius(0.25f);

			FixtureDef fd = new FixtureDef();
			fd.shape =circleShape;
			fd.density = 60 / (float) numRays; // very high - shared across all particles
			fd.friction = 0; // friction not necessary
			fd.restitution = 0.99f; // high restitution to reflect off obstacles
			fd.filter.groupIndex = -1; // particles should not collide with each other
//			body -> CreateFixture( & fd);
			body.createFixture(fd);
		}
	}


	private void createCircleBody(float x, float y) {
		BodyDef circleDef = new BodyDef();
		circleDef.type = BodyDef.BodyType.DynamicBody;
		circleDef.position.set(x, y);

		Body result = world.createBody(circleDef);

		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(3f);

		FixtureDef circleFixture = new FixtureDef();
		circleFixture.shape = circleShape;
		circleFixture.density = 0.4f;
		circleFixture.friction = 0.2f;
		circleFixture.restitution = 0.8f;

		result.createFixture(circleFixture);

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

	@Override
	public boolean keyDown(int keycode) {
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return true;

	}

	@Override
	public boolean keyTyped(char character) {
		return true;

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		explosion(touchPos.x, touchPos.y);
		return true;

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;

	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return true;

	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return true;

	}

	@Override
	public boolean scrolled(int amount) {
		return true;

	}
}
