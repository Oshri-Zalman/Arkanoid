package Project1;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.Ball;
import geometry.Point;

public class BouncingBallAnimation {

    public static void main(String[] args) {
        String xString = args[0];
        String yString = args[1];
        String dxString = args[2];
        String dyString = args[3];

        int x = Integer.parseInt(xString);
        int y = Integer.parseInt(yString);
        int dx = Integer.parseInt(dxString);
        int dy = Integer.parseInt(dyString);

        Point startPoint = new Point(x,y);
        drawAnimation(startPoint, dx, dy);
    }

    static private void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("Bouncing game.Ball", 500, 800);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start, 20, java.awt.Color.BLACK);
        ball.setVelocity(dx, dy);
        //set the limits of the frame:
        int xMin = 0;
        int yMin = 0;
        int xMax = 500;
        int yMax = 800;

        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
