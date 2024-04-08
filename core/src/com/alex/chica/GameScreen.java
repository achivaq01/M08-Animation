package com.alex.chica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    final Main game;
    Texture spriteSheet;
    Texture background;
    TextureRegion backgroundRegion;
    OrthographicCamera camera;
    Player player;
    float stateTime;
    Rectangle up, down, left, right, fire;
    final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4, UP_RIGHT=5, UP_LEFT=6, DOWN_RIGHT=7, DOWN_LEFT=8;

    public GameScreen(Main game) {
        this.game = game;

        background = new Texture(Gdx.files.internal("background.png"));
        background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        backgroundRegion = new TextureRegion(background);
        spriteSheet = new Texture(Gdx.files.internal("spritesheet.png"));

        player = Player.fromTexture(spriteSheet);
        stateTime = 0f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);

        up = new Rectangle(0, game.SCR_HEIGHT*2/3, game.SCR_WIDTH, game.SCR_HEIGHT/3);
        down = new Rectangle(0, 0, game.SCR_WIDTH, game.SCR_HEIGHT/3);
        left = new Rectangle(0, 0, game.SCR_WIDTH/3, game.SCR_HEIGHT);
        right = new Rectangle(game.SCR_WIDTH*2/3, 0, game.SCR_WIDTH/3, game.SCR_HEIGHT);
    }

    protected int virtual_joystick_control() {
        for(int i=0;i<10;i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                camera.unproject(touchPos);

                if (up.contains(touchPos.x, touchPos.y) && right.contains(touchPos.x, touchPos.y)) {
                    return UP_RIGHT;
                } else if (up.contains(touchPos.x, touchPos.y) && left.contains(touchPos.x, touchPos.y)) {
                    return  UP_LEFT;
                }else if (down.contains(touchPos.x, touchPos.y) && right.contains(touchPos.x, touchPos.y)) {
                    return  DOWN_RIGHT;
                }else if (down.contains(touchPos.x, touchPos.y) && left.contains(touchPos.x, touchPos.y)) {
                    return  DOWN_LEFT;
                } else if (up.contains(touchPos.x, touchPos.y)) {
                    return UP;
                } else if (down.contains(touchPos.x, touchPos.y)) {
                    return DOWN;
                } else if (left.contains(touchPos.x, touchPos.y)) {
                    return LEFT;
                } else if (right.contains(touchPos.x, touchPos.y)) {
                    return RIGHT;
                }
            }
        return IDLE;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stateTime += delta;

        TextureRegion keyFrame = player.getDownFrames().getKeyFrame(stateTime, true);
        switch (virtual_joystick_control()) {
            case 1:
                keyFrame = player.getUpFrames().getKeyFrame(stateTime, true);
                break;
            case 2:
                keyFrame = player.getDownFrames().getKeyFrame(stateTime, true);
                break;
            case 3:
                keyFrame = player.getLeftFrames().getKeyFrame(stateTime, true);
                break;
            case 4:
                keyFrame = player.getRightFrames().getKeyFrame(stateTime, true);
                break;
            case 5:
                keyFrame = player.getUpRightFrames().getKeyFrame(stateTime, true);
                break;
            case 6:
                keyFrame = player.getUpLeftFrames().getKeyFrame(stateTime, true);
                break;
            case 7:
                keyFrame = player.getDownRightFrames().getKeyFrame(stateTime, true);
                break;
            case 8:
                keyFrame = player.getDownLeftFrames().getKeyFrame(stateTime, true);
                break;
        }
        backgroundRegion.setRegion(0, 0, 800, 480);

        game.batch.begin();
        game.batch.draw(backgroundRegion, 0, 0);
        game.batch.draw(keyFrame, 350, 130);

        game.batch.end();
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

    }
}
