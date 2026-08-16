package listeners;

import game.Game;
import game.Counter;
import collidables.Block;
import game.Ball;


public class BlockRemover implements HitListener {

    private Game game;
    private Counter remainingBlocks;

    public BlockRemover(Game game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    // blocks that are hit should be removed from the game.
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {


        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
        this.remainingBlocks.decrease(1);
    }
}