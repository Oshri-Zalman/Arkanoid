package sprites;

import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

    public class SpriteCollection {

        private List<Sprite> sprites;

        public SpriteCollection() {
            this.sprites = new ArrayList<>();
        }

        public void addSprite(Sprite s) {
            this.sprites.add(s);
        }

        // call timePassed() on all sprites.
        public void notifyAllTimePassed() {
            List<Sprite> copiedSprites = new ArrayList<>(this.sprites);

            // we run in the copy of the list.
            for (Sprite s : copiedSprites) {
                s.timePassed();
            }
        }

        // call drawOn(d) on all sprites.
        public void drawAllOn(DrawSurface d) {
            List<Sprite> copiedSprites = new ArrayList<>(this.sprites);

            for (Sprite s : copiedSprites) {
                s.drawOn(d);
            }
        }
        // Remove a sprite from the list
        public void removeSprite(Sprite s) {
            this.sprites.remove(s);
        }
    }
