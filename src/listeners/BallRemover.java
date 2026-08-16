package listeners;

import game.Game;
import game.Counter;
import collidables.Block;
import game.Ball;

public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);

        this.remainingBalls.decrease(1);

    }
}
