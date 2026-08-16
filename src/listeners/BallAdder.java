package listeners;

import game.Game;
import game.Counter;
import collidables.Block;
import game.Ball;
import game.Velocity;
import geometry.Point;
import java.awt.Color;

public class BallAdder implements HitListener {
    private Game game;
    private Counter ballsCounter;

    public BallAdder(Game game, Counter ballsCounter) {
        this.game = game;
        this.ballsCounter = ballsCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) { // add 2 red balls from the "special" block :) :)

        double x = beingHit.getCollisionRectangle().getUpperLeft().getX() + 25;
        double y = beingHit.getCollisionRectangle().getUpperLeft().getY() + 10;

        for (int i = 0; i < 2; i++) {
            Ball extraBall = new Ball(new Point(x, y), 5, Color.RED);

            if (i == 0) {
                extraBall.setVelocity(Velocity.fromAngleAndSpeed(150, 5));
            } else {
                extraBall.setVelocity(Velocity.fromAngleAndSpeed(210, 5));
            }

            extraBall.setGameEnvironment(hitter.getGameEnvironment());


            this.game.addSprite(extraBall);
        }

        this.ballsCounter.increase(2);
    }
}