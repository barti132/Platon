package game.Character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import game.Main;

public abstract class Enemy extends Sprite {

    protected World world;
    public Body body;
    public Vector2 velocity;

    public Enemy(World world, MapObject object){
        this.world = world;
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        setPosition(rect.getX() / Main.PPM, rect.getY() / Main.PPM);
        defineEnemy();
        velocity = new Vector2(0.6f, -0.5f);
    }

    protected abstract void defineEnemy();
    public abstract void hitOnHead();
    public abstract void update(float delta);

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;

        if(y)
            velocity.y = -velocity.y;
    }
}
