package collidables;

import game.Ball;
import game.Game;
import sprites.Sprite;
import geometry.Point;
import game.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Rectangle;

import java.awt.Color;

public class Paddle implements Sprite, Collidable {

    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private Color color;
    private int speed;

    // Constructor
    public Paddle(biuoop.KeyboardSensor keyboard, Rectangle rect, Color color) {
        this.keyboard = keyboard;
        this.rect = rect;
        this.color = color;
        this.speed = 5; // Speed of the paddle
    }

    // Move the paddle to the left
    public void moveLeft() {
        double newX = this.rect.getUpperLeft().getX() - this.speed;
        // Limit: Don't go past the left border (assuming border width is 20)
        if (newX < 20) {
            newX = 20;
        }
        // Create a new rectangle in the new position
        this.rect = new Rectangle(new Point(newX, this.rect.getUpperLeft().getY()),
                this.rect.getWidth(), this.rect.getHeight());
    }

    // Move the paddle to the right
    public void moveRight() {
        double newX = this.rect.getUpperLeft().getX() + this.speed;
        // Limit: Don't go past the right border (800 width - 20 border - paddle width)
        double limit = 800 - 20 - this.rect.getWidth();
        if (newX > limit) {
            newX = limit;
        }
        this.rect = new Rectangle(new Point(newX, this.rect.getUpperLeft().getY()),
                this.rect.getWidth(), this.rect.getHeight());
    }

    // Sprite: Check keyboard and move accordingly
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    // Sprite: Draw the paddle
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int)rect.getUpperLeft().getX(), (int)rect.getUpperLeft().getY(),
                (int)rect.getWidth(), (int)rect.getHeight());

        // Black border
        d.setColor(Color.BLACK);
        d.drawRectangle((int)rect.getUpperLeft().getX(), (int)rect.getUpperLeft().getY(),
                (int)rect.getWidth(), (int)rect.getHeight());
    }

    // Collidable: Return the shape
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    // Collidable: The Fun Part - 5 Regions Logic
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        double paddleWidth = this.rect.getWidth();
        double regionWidth = paddleWidth / 5;
        double hitX = collisionPoint.getX();
        double startX = this.rect.getUpperLeft().getX();
        double paddleY = this.rect.getUpperLeft().getY();

        // Calculate current speed (Pythagoras) to keep it constant
        double currentSpeed = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));
        // if the ball come from the side of the paddle.
        if (collisionPoint.getY() > paddleY + 2) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }

        // Region 1 (Far Left): Bounce 300 degrees (-60)
        if (hitX < startX + regionWidth) {
            return Velocity.fromAngleAndSpeed(300, currentSpeed);
        }

        // Region 2 (Left): Bounce 330 degrees (-30)
        if (hitX < startX + 2 * regionWidth) {
            return Velocity.fromAngleAndSpeed(330, currentSpeed);
        }

        // Region 3 (Middle): Normal vertical bounce (like a block)
        if (hitX < startX + 3 * regionWidth) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }

        // Region 4 (Right): Bounce 30 degrees
        if (hitX < startX + 4 * regionWidth) {
            return Velocity.fromAngleAndSpeed(30, currentSpeed);
        }

        // Region 5 (Far Right): Bounce 60 degrees
        return Velocity.fromAngleAndSpeed(60, currentSpeed);
    }

    // Add this paddle to the game
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
