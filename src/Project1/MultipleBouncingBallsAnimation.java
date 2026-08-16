package Project1;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.Ball;
import game.Velocity;
import geometry.Point;

import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {
    // finals of the size frame:
    private static final int SCREEN_WIDTH = 500;
    private static final int SCREEN_HEIGHT = 800;

    public static void main(String[] args) {
        // generate the balls array.
        Ball[] balls = new Ball[args.length];
        Random rand = new Random();
        // for loop that generate the balls:
        for (int i = 0; i < args.length; i++) {
            int size = Integer.parseInt(args[i]);

            // formula that calculate the speed depends of the size of the ball (50 is the max size of this formula):
            int cappedSize = Math.min(size, 50);
            double speed = 8 - (cappedSize / 10.0);
            if (speed <= 0) {
                speed = 1;
            }

            // random angel of the ball and the velocity is angle and the speed from above.
            double angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);

            // calculate safe starting point.
            int x = rand.nextInt(SCREEN_WIDTH - 2 * size) + size;
            int y = rand.nextInt(SCREEN_HEIGHT - 2 * size) + size;
            Point startPoint = new Point(x, y);

            // random color and generate the ball:
             Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
             Ball ball = new Ball(startPoint, size, color);

            ball.setVelocity(v); // sett the velocity.
            balls[i] = ball; // add the ball to the array.
        }

        // in the end the balls array is ready, and we can run the animation with the method.
        runAnimation(balls);
    }

    private static void runAnimation(Ball[] balls) {
        GUI gui = new GUI("Multiple Bouncing Balls", SCREEN_WIDTH, SCREEN_HEIGHT);
        Sleeper sleeper = new Sleeper();

        while (true) {
            DrawSurface d = gui.getDrawSurface();
            // we pass through all the balls.
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}