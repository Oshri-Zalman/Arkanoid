package sprites;

import biuoop.DrawSurface;
import game.Counter;
import java.awt.Color;

public class ScoreIndicator implements Sprite {
    private Counter score;

    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 20);


        d.setColor(Color.BLACK);
        d.drawText(350, 15, "Score: " + this.score.getValue(), 15);
    }

    @Override
    public void timePassed() {
    }
}