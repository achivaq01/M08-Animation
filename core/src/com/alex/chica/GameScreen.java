package com.alex.chica;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameScreen implements Screen {

    //WebSocket socket;
    String address = "localhost";
    int port = 8888;

    final Main game;
    Texture spriteSheet;
    Dialog endDialog;
    Texture background;
    TextureRegion backgroundRegion;
    OrthographicCamera camera;
    Skin skin;
    Player player;
    Stage stage;
    float stateTime;
    Rectangle up, down, left, right, fire;
    final int IDLE=0, UP=1, DOWN=2, LEFT=3, RIGHT=4, UP_RIGHT=5, UP_LEFT=6, DOWN_RIGHT=7, DOWN_LEFT=8;
    int posX;
    int posY;

    public GameScreen(Main game) {

        if ( Gdx.app.getType() == Application.ApplicationType.Android ) {
            address = "10.0.2.2";
            //socket = WebSockets.newSocket(WebSockets.toWebSocketUrl(address, port));
        }
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.game = game;
        background = new Texture(Gdx.files.internal("background.jpg"));
        background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        backgroundRegion = new TextureRegion(background);
        spriteSheet = new Texture(Gdx.files.internal("spritesheet.png"));

        player = Player.fromTexture(spriteSheet);
        stateTime = 0f;
        stage = new Stage(new ScreenViewport());
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);

        up = new Rectangle(0, game.SCR_HEIGHT*2/3, game.SCR_WIDTH, game.SCR_HEIGHT/3);
        down = new Rectangle(0, 0, game.SCR_WIDTH, game.SCR_HEIGHT/3);
        left = new Rectangle(0, 0, game.SCR_WIDTH/3, game.SCR_HEIGHT);
        right = new Rectangle(game.SCR_WIDTH*2/3, 0, game.SCR_WIDTH/3, game.SCR_HEIGHT);
        posX = 0;
        posY = 0;
        Gdx.input.setInputProcessor(stage);
    }

    protected int virtual_joystick_control() {
        for(int i=0;i<10;i++)
            if (Gdx.input.isTouched(i)) {
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                camera.unproject(touchPos);

                if (up.contains(touchPos.x, touchPos.y) && right.contains(touchPos.x, touchPos.y)) {
                    posX += 10;
                    posY += -10;
                    return UP_RIGHT;
                } else if (up.contains(touchPos.x, touchPos.y) && left.contains(touchPos.x, touchPos.y)) {
                    posX += -10;
                    posY += -10;
                    return  UP_LEFT;
                }else if (down.contains(touchPos.x, touchPos.y) && right.contains(touchPos.x, touchPos.y)) {
                    posX += 10;
                    posY += 10;
                    return  DOWN_RIGHT;
                }else if (down.contains(touchPos.x, touchPos.y) && left.contains(touchPos.x, touchPos.y)) {
                    posX += -10;
                    posY += 10;
                    return  DOWN_LEFT;
                } else if (up.contains(touchPos.x, touchPos.y)) {
                    posY += -14;
                    return UP;
                } else if (down.contains(touchPos.x, touchPos.y)) {
                    posY += 14;
                    return DOWN;
                } else if (left.contains(touchPos.x, touchPos.y)) {
                    posX += -14;
                    return LEFT;
                } else if (right.contains(touchPos.x, touchPos.y)) {
                    posX += 14;
                    return RIGHT;
                } else {
                    return IDLE;
                }
            }
        return IDLE;
    }

    @Override
    public void show() {
        TextButton button = new TextButton("Http", skin);
        button.setPosition(300, 200);
        button.setHeight(200);;
        button.setWidth(200);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
                Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("https://pokeapi.co/api/v2/pokemon/ditto").build();
                Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse(Net.HttpResponse httpResponse) {
                        endDialog = new Dialog("HTTP Response", skin);
                        endDialog.text(httpResponse.getResultAsString());

                        TextButton closeButton = new TextButton("Close", skin);
                        closeButton.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                endDialog.hide();
                            }
                        });
                        endDialog.button(closeButton);
                        endDialog.show(stage);
                    }

                    @Override
                    public void failed(Throwable t) {
                        System.out.println("The http request failed.");
                    }

                    @Override
                    public void cancelled() {
                        System.out.println("the http request was cancelled.");
                    }
                });
            }
        });

        stage.addActor(button);
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
            default:
                keyFrame = player.getIdleFrames().getKeyFrame(stateTime, true);
                break;
        }
        backgroundRegion.setRegion(posX, posY, 800, 480);

        game.batch.begin();
        game.batch.draw(backgroundRegion, 0, 0);
        game.batch.draw(keyFrame, 350, 130);

        game.batch.end();
        stage.act(delta);
        stage.draw();
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
