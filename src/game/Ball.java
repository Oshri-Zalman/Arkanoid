package game;

import collidables.CollisionInfo;
import sprites.Sprite;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;

public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity v; // the "animation"
    private GameEnvironment gameEnvironment;

    // constructors
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.v = new Velocity(0, 0);
    }

    public Ball(int x, int y, int r, java.awt.Color color) {
        this(new Point(x, y), r, color);
    }

        // methods:
    public int getX() {
        return (int) this.center.getX();
    }
    public int getY() {
        return (int) this.center.getY();
    }
    public int getSize() {
        return this.r;
    }
    public java.awt.Color getColor() {
        return this.color;
    }
    // method for the velocity ("animation").
    public void setVelocity(Velocity v){
        this.v =v;
    }
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx,dy);
    }

    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    public GameEnvironment getGameEnvironment() {
        return this.gameEnvironment;
    }

    public Velocity getVelocity(){
        return this.v;
    }

    public void moveOneStep() {
        //Compute the trajectory.
        Point start = this.center;
        Point end = this.getVelocity().applyToPoint(start);
        Line trajectory = new Line(start, end);

        // Check if moving on this trajectory will hit anything
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);

        //No collision detected move to the destination
        if (info == null) {
            this.center = end;
        } else {
            // Collision detected
            Point p = info.collisionPoint();


            double newX = p.getX();
            double newY = p.getY();
            double epsilon = 0.00001;

            if (this.v.getDx() > 0) {
                newX = newX - epsilon;
            } else {
                newX = newX + epsilon;
            }

            if (this.v.getDy() > 0) {
                newY = newY - epsilon;
            } else {
                newY = newY + epsilon;
            }

            this.center = new Point(newX, newY);

            // Notify the object we hit and update the velocity
            Velocity newV = info.collisionObject().hit(this, info.collisionPoint(), this.v);
            this.setVelocity(newV);
        }
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface d){
        d.setColor(this.color);
        d.fillCircle(this.getX(), this.getY(), this.r);
    }

    // Remove this ball from the game
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }
}


