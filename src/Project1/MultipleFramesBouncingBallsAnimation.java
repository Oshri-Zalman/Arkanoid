package Project1;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.Ball;
import game.Velocity;
import geometry.Point;

import java.awt.Color;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {

    // for the grey frame:
    private static final int F1_X1 = 50;
    private static final int F1_Y1 = 50;
    private static final int F1_X2 = 500;
    private static final int F1_Y2 = 500;
    private static final int F1_WIDTH = F1_X2 - F1_X1;
    private static final int F1_HEIGHT = F1_Y2 - F1_Y1;

    // for the yellow frame:
    private static final int F2_X1 = 450;
    private static final int F2_Y1 = 450;
    private static final int F2_X2 = 600;
    private static final int F2_Y2 = 600;
    private static final int F2_WIDTH = F2_X2 - F2_X1;
    private static final int F2_HEIGHT = F2_Y2 - F2_Y1;

    // the screen:
    private static final int SCREEN_WIDTH = 700;
    private static final int SCREEN_HEIGHT = 700;

    public static void main(String[] args) {
        // generate balls array.
        Ball[] balls = new Ball[args.length];
        Random rand = new Random();

        // for loop with little changes from the MultipleBouncingBalls class:
        for (int i = 0; i < args.length; i++) {
            int size = Integer.parseInt(args[i]);
            // same logical calculating
            int cappedSize = Math.min(size, 50);
            double speed = 8 - (cappedSize / 10.0);
            if (speed <= 0) {
                speed = 1;
            }
            double angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            // random color for each ball
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

            Point startPoint;

            // start point considering the frame.
            if (i < args.length / 2) {
                // the first half is for the grey frame.
                // formula for calculate the location of the point.
                int x = rand.nextInt(F1_WIDTH - 2 * size) + (F1_X1 + size);
                int y = rand.nextInt(F1_HEIGHT - 2 * size) + (F1_Y1 + size);
                startPoint = new Point(x, y);
            } else {
                // the "second half" is for the yellow frame.
                int x = rand.nextInt(F2_WIDTH - 2 * size) + (F2_X1 + size);
                int y = rand.nextInt(F2_HEIGHT - 2 * size) + (F2_Y1 + size);
                startPoint = new Point(x, y);
            }

            Ball ball = new Ball(startPoint, size, color);
            ball.setVelocity(v);
            balls[i] = ball;
        }
        // putting the balls array of the "animation" method.
        runAnimation(balls);
    }

    private static void runAnimation(Ball[] balls) {
        GUI gui = new GUI("Multiple Frames Bouncing Balls", SCREEN_WIDTH, SCREEN_HEIGHT);
        Sleeper sleeper = new Sleeper();
        while (true) {
            DrawSurface d = gui.getDrawSurface();

           // drawing the gray frame.
            d.setColor(Color.GRAY);
            d.drawRectangle(F1_X1, F1_Y1, F1_WIDTH, F1_HEIGHT);

            //drawing the yellow frame.
            d.setColor(Color.YELLOW);
            d.drawRectangle(F2_X1, F2_Y1, F2_WIDTH, F2_HEIGHT);

            for (int i = 0; i < balls.length; i++) {
                Ball ball = balls[i];

                if (i < balls.length / 2) {
                    ball.moveOneStep();
                } else {
                    ball.moveOneStep();
                }

                ball.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
