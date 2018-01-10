package game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud{
    private Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private boolean timeUp;
    private float timeCount;
    private static Integer score;

    private Label countdownL;
    private static Label scoreL;
    private Label timeL;
    private Label levelL;
    private Label worldL;
    private Label marioL;

    public Hud(SpriteBatch batch){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(1280, 720, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownL = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreL = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeL = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelL = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldL = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioL = new Label("Mario", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(marioL).expandX().padTop(10);
        table.add(worldL).expandX().padTop(10);
        table.add(timeL).expandX().padTop(10);

        table.row();
        table.add(scoreL).expandX();
        table.add(levelL).expandX();
        table.add(countdownL).expandX();

        stage.addActor(table);
    }

    public void update(float delta){
        timeCount += delta;
        if(timeCount >= 1){
            worldTimer--;
            countdownL.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreL.setText(String.format("%06d", score));
    }

    public Stage getStage(){
        return stage;
    }

}
