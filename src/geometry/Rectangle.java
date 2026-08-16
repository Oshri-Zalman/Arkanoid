package geometry;

import java.util.ArrayList;
import java.util.List;

public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<>();

        double x = this.upperLeft.getX();
        double y = this.upperLeft.getY();

        Point upperRight = new Point(x + this.width, y);
        Point lowerLeft = new Point(x, y + this.height);
        Point lowerRight = new Point(x + this.width, y + this.height);


        Line up = new Line(upperLeft, upperRight);
        Line left = new Line(upperLeft, lowerLeft);
        Line right = new Line(upperRight, lowerRight);
        Line down = new Line(lowerLeft, lowerRight);

        Line[] sides = {up, left, right, down};

        for (Line side : sides) {
            Point intersection = line.intersectionWith(side);

            if (intersection != null) {
                intersectionPoints.add(intersection);
            }
        }

        return intersectionPoints;
    }


    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public Point getUpperLeft() {
        return this.upperLeft;
    }
}