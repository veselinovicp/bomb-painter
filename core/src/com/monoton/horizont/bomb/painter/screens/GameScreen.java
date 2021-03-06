package com.monoton.horizont.bomb.painter.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.monoton.horizont.bomb.painter.Constants;
import com.monoton.horizont.bomb.painter.communication.ExplosionCallback;
import com.monoton.horizont.bomb.painter.entities.Ball;
import com.monoton.horizont.bomb.painter.entities.Court;
import com.monoton.horizont.bomb.painter.entities.Explosion;
import com.monoton.horizont.bomb.painter.logic.BodyDescription;
import com.monoton.horizont.bomb.painter.ui.UIBuilder;

import java.util.UUID;

public class GameScreen implements Screen , InputProcessor, ExplosionCallback {

    private static final String BORDER = "BORDER";
    private static final String BALL="ball";
    private final UIBuilder uiBuilder;
    private OrthographicCamera camera;
    private FPSLogger logger;



    private static final int MIN_SOUND_SPEED = 25;

    private World world;
    private Box2DDebugRenderer renderer;
    private  float width, height, borderWidth, radius, circleDistance;

    private StretchViewport stretchViewport;

    private Stage stage;

    private Texture explosionTexture;
    private Texture ballLeaveBasketTexture;

    private Texture explosionParticleTexture;
    private TextureRegion[] explosionParticleImages;

    private TextureRegion[] explosionImages;
    private TextureRegion[] ballLeaveBasketImages;

    private Texture ballTexture;

    private static final int NUM_OF_BALL_ROWS = 4;

    private Body basketBody;

    private Array<Ball> balls = new Array<Ball>();

    private SnapshotArray<Body> hits = new SnapshotArray<Body>();




    private float basketCenterX;
    private float basketCenterY;

    private Sound explosion;
    private Sound basketBallSound;
    private Sound spin;

    private Texture court;

    private Table scoreBoard;
    private Label scoreLabel;

    public GameScreen(UIBuilder uiBuilder){
        this.uiBuilder =uiBuilder;

    }

    @Override
    public void show() {
        width = Gdx.graphics.getWidth()/ Constants.SCREEN_RATIO;
        height = Gdx.graphics.getHeight()/Constants.SCREEN_RATIO;
        borderWidth = width/100;
        radius = width/50;
        circleDistance = radius * 3;

        camera = new OrthographicCamera(width, height);
        camera.position.set(width*0.5f, height * 0.5f,0);
        camera.update();

        stretchViewport = new StretchViewport(width, height);
        stage = new Stage(stretchViewport);

        world = new World(new Vector2(0, -9.8f), false);
        renderer = new Box2DDebugRenderer();
        logger = new FPSLogger();

        //


        createSoundEffects();

        createAnimationImages();

        createCourt();


        createBasket();

        createBalls();



        createBorders();

        createScoreBoard();




        createBasketListener();




        Gdx.input.setInputProcessor(this);

    }

    private void createScoreBoard() {
        scoreBoard = new Table();
        scoreBoard.setWidth(width);
        scoreBoard.setHeight(height);
        scoreBoard.align(Align.top|Align.left);
//        scoreBoard.padTop(20);


        createScoreText();



        stage.addActor(scoreBoard);


    }

    private void createScoreText(){
        Skin skin = uiBuilder.get(UIBuilder.DEFAULT_SKIN, Skin.class);
        scoreLabel = new Label("0", skin);
        scoreBoard.add(scoreLabel).align(Align.left).padRight(0);
    }

    private void createCourt() {
        court = uiBuilder.get(UIBuilder.COURT, Texture.class);


        stage.addActor(new Court(court, stretchViewport.getScreenWidth(), stretchViewport.getScreenHeight()));

    }

    private void createSoundEffects() {
        explosion = uiBuilder.get(UIBuilder.EXPLOSION_SOUND,Sound.class);//basket_ball_drop.wav
        basketBallSound = uiBuilder.get(UIBuilder.BASKET_BALL_DROP, Sound.class);//basket_ball_drop.wav
        spin = uiBuilder.get(UIBuilder.MONEY_SPIN_SOUND, Sound.class);
    }

    private void createBasketListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body bodyA = contact.getFixtureA().getBody();
                BodyDescription descrptionA = (BodyDescription) bodyA.getUserData();
                Body bodyB = contact.getFixtureB().getBody();
                BodyDescription descrptionB = (BodyDescription) bodyB.getUserData();
                if(bodyA == basketBody ) {

                    addHit(bodyB);


                }

                if(bodyB == basketBody) {



                    addHit(bodyA);


                }

                if(bodiesCollide(descrptionA, descrptionB,BORDER, BALL)){
                    float lenA = bodyA.getLinearVelocity().len();
                    float lenB = bodyB.getLinearVelocity().len();
//					System.out.println("len a: "+lenA+", len b: "+lenB);
                    if(lenA>MIN_SOUND_SPEED || lenB>MIN_SOUND_SPEED){
                        basketBallSound.play();
                    }

                }






            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    private boolean bodiesCollide(BodyDescription descA, BodyDescription descB, String label1, String label2){
        if(descA.getLabel().equals(label1) && descB.getLabel().equals(label2)){
            return true;
        }
        if(descA.getLabel().equals(label2) && descB.getLabel().equals(label1)){
            return true;
        }
        return false;

    }

    private void addHit(Body body) {

        BodyDescription userData = (BodyDescription)body.getUserData();
        String label = userData.getLabel();

        if(userData!=null && label.equals(BALL)) {
            System.out.println("hit");
            increaseScore();
            spin.play();
            hits.begin();
            hits.add(body);
            hits.end();
            userData.setLinearVelocityAngleBeforeColision(body.getLinearVelocity().angleRad());
        }





    }

    private void increaseScore(){
        Integer currentScore = Integer.valueOf(scoreLabel.getText().toString());
        int newScore = currentScore+100;
        scoreLabel.setText(""+newScore);
    }

    private void createBasket(){
        basketCenterX = 0.505f * width;
        basketCenterY = 0.75f * height;
        int size = 3;
        basketBody = createCircleBody(basketCenterX, basketCenterY, BodyDef.BodyType.StaticBody, size *radius);
        basketBody.setUserData(new BodyDescription(UUID.randomUUID().toString(),"basket"));




//		stage.addActor(new Basket(basketTexture,2*(size+2)*radius,basketCenterX,basketCenterY,stretchViewport.getScreenWidth(), stretchViewport.getScreenHeight()));


    }

    private void createAnimationImages(){

        createExplosionAnimation();
        createBallLeaveBasketAnimation();
        createExplosionParticleAnimation();



    }


    private void createBallLeaveBasketAnimation() {
        ballLeaveBasketTexture = uiBuilder.get(UIBuilder.COIN_SPIN,Texture.class);
        ballLeaveBasketImages = createAnimationImages(ballLeaveBasketTexture,5,1);
    }

    private void createExplosionAnimation() {
//        explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
        explosionTexture = uiBuilder.get(UIBuilder.EXPLOSION, Texture.class);
        explosionImages = createAnimationImages(explosionTexture,8,8);

    }

    private void createExplosionParticleAnimation() {
        explosionParticleTexture = uiBuilder.get(UIBuilder.FLAME,Texture.class);
        explosionParticleImages = createAnimationImages(explosionParticleTexture,8,4);
    }

    private TextureRegion[] createAnimationImages(Texture texture, int frameCols, int frameRows) {
//		int FRAME_COLS=8,FRAME_ROWS = 8;


        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / frameCols,
                texture.getHeight() / frameRows);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] animImages = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                animImages[index++] = tmp[i][j];
            }
        }
        return animImages;
    }

    private void createBorders() {
        /**
         * floor
         */
        createBorder(0,borderWidth,camera.viewportWidth*2,borderWidth);

        /**
         * left border
         */
        createBorder(borderWidth,borderWidth,borderWidth,camera.viewportHeight*2);

        /**
         * right border
         */
        createBorder(camera.viewportWidth-borderWidth,borderWidth,borderWidth,camera.viewportHeight*2);

        /**
         * ceiling
         */
        createBorder(borderWidth,camera.viewportHeight-borderWidth,camera.viewportWidth*2,borderWidth);
    }

    private void createBorder(float x, float y, float width, float height) {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(x,y);

        Body borderBody = world.createBody(groundBodyDef);
        borderBody.setUserData(new BodyDescription(UUID.randomUUID().toString(),BORDER));

        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(width, height);

        borderBody.createFixture(groundBox,0);

        groundBox.dispose();
    }

    private void createBalls() {

        ballTexture = uiBuilder.get(UIBuilder.BASKET_BALL,Texture.class);

        float verticalStep = width / (float) (NUM_OF_BALL_ROWS+3);


        for(int j=0; j<NUM_OF_BALL_ROWS+3; j++) {
            for (int i = (int) circleDistance; i < width/2f; i += circleDistance) {
                Body circleBody = createCircleBody(i, (j+1)*verticalStep, BodyDef.BodyType.DynamicBody, radius);
                Ball ball = new Ball(circleBody, new TextureRegion(ballTexture), radius * 2, stretchViewport.getScreenWidth(), stretchViewport.getScreenHeight(), basketCenterX, basketCenterY, stage, basketBallSound, ballLeaveBasketImages);

                balls.add(ball);
                stage.addActor(ball);

//				ball.setUserObject();

                circleBody.setUserData(new BodyDescription(ball.getId(), BALL));

//				break;
            }

//			break;
        }

    }

    private float  DEGTORAD = 0.0174532925199432957f;
    private void explosion(float x, float y) {
        int numRays = 100;
        float blastPower = 10000000;

        final Array<Body> explosionsParticles = new Array<Body>();
        for (int i = 0; i < numRays; i++) {
            float angle = (i / (float) numRays) * 360 * DEGTORAD;

            Vector2 rayDir = new Vector2 (MathUtils.sin(angle), MathUtils.cos(angle));

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.fixedRotation = true; // rotation not necessary
            bodyDef.bullet = true; // prevent tunneling at high speed
            bodyDef.linearDamping = 3; // drag due to moving through air 10
            bodyDef.gravityScale = 0; // ignore gravity

            bodyDef.position.set(x, y);

            bodyDef.linearVelocity.set(new Vector2(blastPower*rayDir.x, blastPower * rayDir.y));
            Body body = world.createBody(bodyDef);
            body.setUserData(new BodyDescription(UUID.randomUUID().toString(),"explosion"));


            CircleShape circleShape = new CircleShape();

            circleShape.setRadius(radius/5);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape =circleShape;
            fixtureDef.density = 60 / (float) numRays; // very high - shared across all stage
            fixtureDef.friction = 0; // friction not necessary
            fixtureDef.restitution = 0.99f; // high restitution to reflect off obstacles
            fixtureDef.filter.groupIndex = -1; // stage should not collide with each other

            body.createFixture(fixtureDef);

            circleShape.dispose();

            explosionsParticles.add(body);
        }
        explosion.play();

        stage.addActor(new Explosion(explosionsParticles, this, explosionImages, x, y, stretchViewport.getScreenWidth(), stretchViewport.getScreenHeight(), explosionParticleImages));



    }


    private Body createCircleBody(float x, float y, BodyDef.BodyType bodyType, float _radius) {
        BodyDef circleDef = new BodyDef();
        circleDef.type = bodyType;
        circleDef.position.set(x, y);

        Body result = world.createBody(circleDef);


        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(_radius);

        FixtureDef circleFixture = new FixtureDef();
        circleFixture.shape = circleShape;
        circleFixture.density = 0.4f;
        circleFixture.friction = 0.2f;
        circleFixture.restitution = 0.8f;

        result.createFixture(circleFixture);
        circleShape.dispose();


        return result;

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        /**

         * 1. draw stage
         */
        stage.act();
        stage.draw();

        clearHitsFromBoard();


//		renderer.render(world, camera.combined);
        world.step(1/60f, 6, 2);
//		logger.log();

    }


    private void clearHitsFromBoard() {

        if(!world.isLocked()){
            Object[] bodies = hits.begin();

            for (int i = 0, n = hits.size; i < n; i++) {
                Body destroyedBall = (Body)bodies[i];
                BodyDescription userData = (BodyDescription)destroyedBall.getUserData();
                removeBall(userData.getId());
                world.destroyBody(destroyedBall);

            }
            hits.clear();

            hits.end();

        }

    }

    private void removeBall(String id){

        for(Actor actor : stage.getActors())
        {


            if(actor instanceof Ball){
                Ball ball = (Ball) actor;
                if(ball.getId().equals(id)){
                    actor.remove();
                }
            }


        }
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

    @Override
    public void explosionEnded(Explosion explosion) {
        for(Body particle : explosion.getExplosionsParticles()) {
            if(particle.isActive()) {
                world.destroyBody(particle);
            }
        }
        explosion.remove();


    }
}
