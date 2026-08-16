package geometry;

public class Point {
    private double x;
    private double y;

        // constructor
        public Point(double x, double y) {
            this.x =x;
            this.y=y;
        }

        // distance -- return the distance of this point to the other point
        public double distance(Point other) {
            double x1 = this.x;
            double y1 = this.y;
            double x2 = other.x;
            double y2 = other.y;
            double dis = ((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2));

            return Math.sqrt(dis);

        }

        // equals -- return true is the points are equal, false otherwise
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Point)) {
                return false;
            }

            final double EPSILON = 0.00001;
            boolean xEquals = Math.abs(this.x - ((Point) other).x) < EPSILON;
            boolean yEquals = Math.abs(this.y - ((Point) other).y) < EPSILON;

            return xEquals && yEquals;
        }

        // Return the x and y values of this point
        public double getX() {
            return this.x;

        }
        public double getY() {
            return this.y;

        }
}
