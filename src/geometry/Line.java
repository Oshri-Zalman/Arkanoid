package geometry;

import java.util.List;

public class Line {
    private Point start;
    private Point end;

    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    // Return the length of the line
    public double length() {
        return start.distance(end);
    }

    // Returns the middle point of the line
    public Point middle() {
        double x = (start.getX() + end.getX()) / 2;
        double y = (start.getY() + end.getY()) / 2;
        return new Point(x, y);
    }

    // Returns the start point of the line
    public Point start() {
        return this.start;
    }

    // Returns the end point of the line
    public Point end() {
        return this.end;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {

        Point p1 = this.start;
        Point q1 = this.end;
        Point p2 = other.start;
        Point q2 = other.end;
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        if (o4 == 0 && onSegment(p2, q1, q2)) {
            return true;
        }

        return false;
    }

    private static int orientation(Point p, Point q, Point r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) -
                (q.getX() - p.getX()) * (r.getY() - q.getY());

        final double EPSILON = 0.00001;

        if (Math.abs(val) < EPSILON) {
            return 0;
        }

        return (val > 0) ? 1 : 2;
    }

    private boolean onSegment(Point p, Point q, Point r) {
        boolean xOn = q.getX() <= Math.max(p.getX(), r.getX()) &&
                q.getX() >= Math.min(p.getX(), r.getX());

        boolean yOn = q.getY() <= Math.max(p.getY(), r.getY()) &&
                q.getY() >= Math.min(p.getY(), r.getY());

        return xOn && yOn;
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {

        if (!this.isIntersecting(other)) {
            return null;
        }

        Point p1 = this.start;
        Point p2 = this.end;
        Point p3 = other.start;
        Point p4 = other.end;

        double x1 = p1.getX(), y1 = p1.getY();
        double x2 = p2.getX(), y2 = p2.getY();
        double x3 = p3.getX(), y3 = p3.getY();
        double x4 = p4.getX(), y4 = p4.getY();

        double den = (x1 - x2) * (y3 - y4) -
                (y1 - y2) * (x3 - x4);

        final double EPSILON = 0.00001;

        if (Math.abs(den) < EPSILON) {
            return null;
        }

        double tNum = (x1 - x3) * (y3 - y4) -
                (y1 - y3) * (x3 - x4);

        double t = tNum / den;
        double ix = x1 + t * (x2 - x1);
        double iy = y1 + t * (y2 - y1);

        return new Point(ix, iy);
    }


    // equals -- return true is the lines are equal, false otherwise
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof Line)) {
            return false;
        }

        Line otherLine = (Line) other;

        boolean sameDirection = this.start.equals(otherLine.start) &&
                this.end.equals(otherLine.end);


        boolean reverseDirection = this.start.equals(otherLine.end) &&
                this.end.equals(otherLine.start);

        return sameDirection || reverseDirection;
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> arr = rect.intersectionPoints(this);
        if (arr.size() == 0) return null;
        Point closest = arr.get(0);
        double mindis = this.start().distance(closest);
        for (int i = 1; i <arr.size(); i++){
            double dis = this.start().distance(arr.get(i));
            if (dis <mindis){
                mindis = dis;
                closest = arr.get(i);
            }
        }
        return closest;
    }
}
