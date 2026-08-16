package collidables;

import game.Ball;
import game.Game;
import listeners.HitListener;
import listeners.HitNotifier;
import sprites.Sprite;
import geometry.Point;
import game.Velocity;
import biuoop.DrawSurface;
import geometry.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;


public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private java.awt.Color color;
    private List<HitListener> hitListeners;

    public Block (Rectangle rect, java.awt.Color color) {
        this.rect = rect;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //Get current velocity components
            double dx = currentVelocity.getDx();
            double dy = currentVelocity.getDy();
            // Get collision point coordinates
            double x = collisionPoint.getX();
            double y = collisionPoint.getY();
            //Calculate rectangle borders
            double minX = this.rect.getUpperLeft().getX();
            double maxX = minX + this.rect.getWidth();
            double minY = this.rect.getUpperLeft().getY();
            double maxY = minY + this.rect.getHeight();

            final double EPSILON = 0.00001;

            // Check horizontal collision.
            if (Math.abs(minX - x) < EPSILON || Math.abs(maxX - x) < EPSILON) {
                dx = -dx;
            }

            //Check vertical collision (Top or Bottom edge)
            if (Math.abs(minY - y) < EPSILON || Math.abs(maxY - y) < EPSILON) {
                dy = -dy;
            }
            this.notifyHit(hitter);
            return new Velocity(dx, dy);
        }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int)rect.getUpperLeft().getX(),(int)rect.getUpperLeft().getY(),(int)rect.getWidth(), (int)rect.getHeight());
        d.setColor(java.awt.Color.BLACK);
        d.drawRectangle((int)rect.getUpperLeft().getX(), (int)rect.getUpperLeft().getY(),
                (int)rect.getWidth(), (int)rect.getHeight());
    }

    @Override
    public void timePassed() {

    }

    public void setColor(Color rowColor) {
    this.color = rowColor;
    }

    // Remove this block from the game (both as a sprite and as a collidable)
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

}

