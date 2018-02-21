package game.Character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Enemy extends Sprite {

    protected World world;
    public Body body;
    public Vector2 velocity;

    public Enemy(World world, float x, float y){
        this.world = world;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1, 0);
    }

    protected abstract void defineEnemy();
    public abstract void hitOnHead();

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;

        if(y)
            velocity.y = -velocity.y;
    }
}
