package game;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import collidables.Block;
import collidables.Collidable;
import collidables.Paddle;
import geometry.Point;
import geometry.Rectangle;
import listeners.BallAdder;
import listeners.BallRemover;
import listeners.ScoreTrackingListener;
import sprites.ScoreIndicator;
import sprites.Sprite;
import sprites.SpriteCollection;
import listeners.BlockRemover;

import java.awt.Color;

public class Game {

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter scoreCounter;

    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }

    // Add a collidable object to the game environment
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    // Add a sprite object to the sprite collection
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }


    // Initialize a new game: create the Blocks, Ball, and GUI
    public void initialize() {
        this.gui = new GUI("Arkanoid", 800, 600);
        this.sleeper = new Sleeper();
        this.environment = new GameEnvironment();
        this.blocksCounter = new Counter();
        this.ballsCounter = new Counter();
        this.scoreCounter = new Counter();


        //Create background:
        Sprite background = new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                // color:
                d.setColor(new Color(0, 0, 40));
                d.fillRectangle(0, 0, 800, 600);

                // more to the background "like stars":
                d.setColor(Color.WHITE);
                d.fillCircle(100, 100, 2);
                d.fillCircle(700, 500, 2);
                d.fillCircle(400, 50, 2);
                d.fillCircle(150, 300, 2);
                d.fillCircle(400, 400, 2);
                d.fillCircle(300, 350, 2);
                d.fillCircle(80, 400, 2);
                d.fillCircle(750, 200, 2);
            }
            @Override
            public void timePassed() {
            }
        };
        this.addSprite(background);

        BlockRemover blockRemover = new BlockRemover(this, this.blocksCounter);
        BallRemover ballRemover = new BallRemover(this, this.ballsCounter);
        BallAdder bonusListener = new BallAdder(this, this.ballsCounter);

        // borders:
        Color wallColor = Color.GRAY;

        // Top Border
        Block topBlock = new Block(new Rectangle(new Point(0, 0), 800, 20), wallColor);
        this.addCollidable(topBlock);
        this.addSprite(topBlock);

        // deathRegion:
        Block deathRegion = new Block(new Rectangle(new Point(0, 599), 800, 1), wallColor);
        deathRegion.addHitListener(ballRemover);
        this.addSprite(deathRegion);
        this.addCollidable(deathRegion);

        // Left Border:
        Block leftBlock = new Block(new Rectangle(new Point(0, 20), 20, 580), wallColor);
        this.addCollidable(leftBlock);
        this.addSprite(leftBlock);

        // Right Border:
        Block rightBlock = new Block(new Rectangle(new Point(780, 20), 20, 580), wallColor);
        this.addCollidable(rightBlock);
        this.addSprite(rightBlock);

        // the score tracking:
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(this.scoreCounter);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.scoreCounter);
        this.addSprite(scoreIndicator);

        // balls:
        int ballsCount = 3;
        this.ballsCounter.increase(ballsCount);
        for (int i = 0; i < ballsCount; i++) {
            Ball ball = new Ball(new Point(400, 500), 5, Color.WHITE);
            ball.setVelocity(Velocity.fromAngleAndSpeed(330 + (i * 15), 5));
            ball.setGameEnvironment(this.environment);
            this.addSprite(ball);

        }


        // paddle:
        Color paddleColor = new Color(255, 200, 0);
        Rectangle paddleRect = new Rectangle(new Point(330, 560), 140, 15);
        Paddle paddle = new Paddle(this.gui.getKeyboardSensor(), paddleRect, paddleColor);
        paddle.addToGame(this);

        // the game blocks:
        Color[] colors = {
                new Color(0, 255, 255), // Cyan
                new Color(255, 100, 100), // Pink
                new Color(255, 255, 100), // Yellow
                new Color(100, 255, 100), // Green
                new Color(200, 200, 255), // Light Blue
                new Color(255, 150, 255)  // Purple
        };
        // the blocks size:
        int blockWidth = 50;
        int blockHeight = 25;

        // Loop for rows:
        for (int i = 0; i < 6; i++) {
            Color rowColor = colors[i];
            int y = 100 + i * blockHeight;

            // Loop for blocks in row:
            for (int j = 0; j < 12 - i; j++) {
                int x = 730 - j * blockWidth;

                Block block = new Block(new Rectangle(new Point(x, y), blockWidth, blockHeight), rowColor);
                this.addCollidable(block);
                this.addSprite(block);

                block.addHitListener(blockRemover);
                blocksCounter.increase(1);
                block.addHitListener(scoreListener);
                // the speical block.
                if (i == 2 && j == 4) {
                    block.setColor(Color.MAGENTA);
                    block.addHitListener(bonusListener);
                }
            }
        }
    }

    // Run the game animation loop
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = this.gui.getDrawSurface();

            // Draw all sprites
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            // Notify all sprites that time passed
            this.sprites.notifyAllTimePassed();
            // finish the game if all the blocks brake or the balls are finished.
            if (this.blocksCounter.getValue() == 0) {
                this.scoreCounter.increase(100);
                this.gui.close();
                return;
            }

            if (this.ballsCounter.getValue() == 0) {
                this.gui.close();
                return;
            }
            // Timing logic to maintain 60 fps
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
    // Remove a collidable from the game
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    // Remove a sprite from the game
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

}