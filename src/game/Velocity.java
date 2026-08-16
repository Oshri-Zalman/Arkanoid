package game;

import geometry.Point;

public class Velocity {
    private double dx;
    private double dy;

    // constructor
        public Velocity(double dx, double dy) {
            this.dx = dx;
            this.dy = dy;
        }
    //for the applypoint function we need the getters.
    public double getDx() {
        return this.dx;
    }

    public double getDy() {
        return this.dy;
    }

        // Take a point with position (x,y) and return a new point
        // with position (x+dx, y+dy)
        public Point applyToPoint(Point p){
            return new Point(p.getX() + this.dx, p.getY() +this.dy); // return the change its effect of animation.
        }

    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // to radians:
        double angleInRadians = Math.toRadians(angle);

        // horizontal:
        double dx = speed * Math.sin(angleInRadians);

        // vertical :
        double dy = -speed * Math.cos(angleInRadians);

        return new Velocity(dx, dy);
    }
}
