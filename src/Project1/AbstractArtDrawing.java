package Project1;

import biuoop.GUI;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;

import java.util.Random;
import java.awt.Color;

public class AbstractArtDrawing {


    public void run() {
        GUI gui = new GUI("Abstract Art", 400, 300);
        Random rand = new Random();
        Line[] lines = new Line[10]; // generate array of 10 lines.

        for (int i = 0; i < 10; ++i) {
            int x1 = rand.nextInt(400) + 1;
            int y1 = rand.nextInt(300) + 1;
            int x2 = rand.nextInt(400) + 1;
            int y2 = rand.nextInt(300) + 1;
            lines[i] = new Line(x1, y1, x2, y2); // after that we will have 10 randoms lines in the array.
        }

        DrawSurface d = gui.getDrawSurface(); // generate the Drawsurface.

        for (int i=0; i < 10; i++) {
            d.setColor(Color.BLACK);
            d.drawLine((int)lines[i].start().getX(), (int)lines[i].start().getY(),
                    (int)lines[i].end().getX(), (int)lines[i].end().getY()); // after each iteration draw 1 black line.
            d.setColor(Color.BLUE);
            Point mid = lines[i].middle();
            d.fillCircle((int)mid.getX(), (int)mid.getY(), 3); // after each iteration draw 1 middle blue point.
        }

        d.setColor(Color.RED);
        for (int i = 0; i < lines.length; i++) { // 2 for loop, for check if 2 line are intersecting.
            for (int j = i + 1; j < lines.length; j++) {
                Line l1 = lines[i];
                Line l2 = lines[j];

                Point intersection = l1.intersectionWith(l2); // generate the intersection point.

                if (intersection != null) {
                    d.fillCircle((int)intersection.getX(), (int)intersection.getY(), 3); // draw the point.
                }
            }
        }

        gui.show(d);
    }


    public static void main(String[] args) {
        AbstractArtDrawing art = new AbstractArtDrawing();
        art.run();
    }
}
