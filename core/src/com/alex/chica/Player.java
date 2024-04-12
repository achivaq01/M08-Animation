package com.alex.chica;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    public static final int HEIGHT = 50;
    public static final int WIDTH = 50;
    public static final float ANIMATION_SPEED = .15f;

    private final Animation<TextureRegion> idleFrames;
    private final Animation<TextureRegion> upFrames;
    private final Animation<TextureRegion> downFrames;
    private final Animation<TextureRegion> rightFrames;
    private final Animation<TextureRegion> leftFrames;
    private final Animation<TextureRegion> upRightFrames;
    private final Animation<TextureRegion> upLeftFrames;
    private final Animation<TextureRegion> downRightFrames;
    private final Animation<TextureRegion> downLeftFrames;

    private Player(Texture spriteSheet) {
        idleFrames = new Animation<TextureRegion>(ANIMATION_SPEED,
                new TextureRegion(spriteSheet, 200, 50, 50, 50));

        upFrames = new Animation<TextureRegion>(ANIMATION_SPEED,
                new TextureRegion(spriteSheet, 0, 0, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 50, 0, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 100, 0, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 150, 0, WIDTH, HEIGHT));

        downFrames = new Animation<TextureRegion>(ANIMATION_SPEED,
                new TextureRegion(spriteSheet, 0, 50, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 50, 50, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 100, 50, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 150, 50, WIDTH, HEIGHT));

        downRightFrames = new Animation<TextureRegion>(ANIMATION_SPEED,
                new TextureRegion(spriteSheet, 0, 100, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 50, 100, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 100, 100, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 150, 100, WIDTH, HEIGHT));

        rightFrames = new Animation<>(ANIMATION_SPEED,
                new TextureRegion(spriteSheet, 0, 150, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 50, 150, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 100, 150, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 150, 150, WIDTH, HEIGHT));

        upRightFrames = new Animation<>(ANIMATION_SPEED,
                new TextureRegion(spriteSheet, 0, 200, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 50, 200, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 100, 200, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 150, 200, WIDTH, HEIGHT));

        downLeftFrames = new Animation<>(ANIMATION_SPEED,
                new TextureRegion(spriteSheet, 0, 250, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 50, 250, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 100, 250, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 150, 250, WIDTH, HEIGHT));

        leftFrames = new Animation<>(ANIMATION_SPEED,
                new TextureRegion(spriteSheet, 0, 300, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 50, 300, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 100, 300, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 150, 300, WIDTH, HEIGHT));

        upLeftFrames = new Animation<>(ANIMATION_SPEED,
                new TextureRegion(spriteSheet, 0, 350, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 50, 350, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 100, 350, WIDTH, HEIGHT),
                new TextureRegion(spriteSheet, 150, 350, WIDTH, HEIGHT));
    }
    public static Player fromTexture(Texture spriteSheet) {
        return new Player(spriteSheet);
    }

    public Animation<TextureRegion> getIdleFrames() { return idleFrames; }

    public Animation<TextureRegion> getUpFrames() {
        return upFrames;
    }

    public Animation<TextureRegion> getDownFrames() {
        return downFrames;
    }

    public Animation<TextureRegion> getRightFrames() {
        return rightFrames;
    }

    public Animation<TextureRegion> getLeftFrames() {
        return leftFrames;
    }

    public Animation<TextureRegion> getUpRightFrames() {
        return upRightFrames;
    }

    public Animation<TextureRegion> getUpLeftFrames() {
        return upLeftFrames;
    }

    public Animation<TextureRegion> getDownRightFrames() {
        return downRightFrames;
    }

    public Animation<TextureRegion> getDownLeftFrames() {
        return downLeftFrames;
    }
}
