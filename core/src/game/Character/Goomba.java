package game.Character;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import game.Main;


public class Goomba extends Enemy {

    private float stateTime;
    private final Animation walkAnimation;
    private boolean setToDestroy;
    private boolean destroyed;
    private final TextureRegion goomba;

    public Goomba(World world, TextureAtlas atlas, MapObject object) {
        super(world, object);
        goomba = atlas.findRegion("goomba");

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(goomba, i * 16, 0, 16, 16));

        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / Main.PPM, 16 / Main.PPM);

        setToDestroy = false;
        destroyed = false;
    }

    @Override
    public void update(float delta){
        stateTime += delta;

        if(setToDestroy && !destroyed){
            world.destroyBody(body);
            destroyed = true;
            setRegion(new TextureRegion(goomba, 32, 0, 16, 16));
            stateTime = 0;
        }

        else if(!destroyed) {
            body.setLinearVelocity(velocity);
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.PPM);
        fdef.filter.categoryBits = Main.ENEMY_BIT;
        fdef.filter.maskBits = Main.BRICK_BIT | Main.COIN_BIT | Main.GROUND_BIT | Main.MARIO_BIT | Main.OBJECT_BIT | Main.ENEMY_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / Main.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / Main.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / Main.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / Main.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = Main.ENEMY_HEAD_BIT;
        body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead(Player player) {
        setToDestroy = true;
        Main.manager.get("audio/stomp.wav", Sound.class).play();
    }

    @Override
    public void onEnemyHit(Enemy enemy){
        if(enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.MOVING_SHELL)
            setToDestroy = true;

        else
            reverseVelocity(true, false);
    }
}
