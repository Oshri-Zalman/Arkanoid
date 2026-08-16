package game;
import collidables.Collidable;
import collidables.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {

    List<Collidable> collidables = new ArrayList<Collidable>();

    // add the given collidable to the environment.
        public void addCollidable(Collidable c) {
            collidables.add(c);
        }

        // Assume an object moving from line.start() to line.end().
        // If this object will not collide with any of the collidables
        // in this collection, return null. Else, return the information
        // about the closest collision that is going to occur.
        public CollisionInfo getClosestCollision(Line trajectory) {
                // initialazions:
                Point closestPoint = null;
                Collidable closestObject = null;
                double minDistance = Double.MAX_VALUE;

                for (Collidable c : this.collidables) {
                    Rectangle rect = c.getCollisionRectangle();

                    // where the line intersecting the rect block.
                    Point intersection = trajectory.closestIntersectionToStartOfLine(rect);
                    if (intersection != null) {

                        double distance = trajectory.start().distance(intersection);

                        // checks if this is the closest intersection.
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestPoint = intersection;
                            closestObject = c;
                        }
                    }
                }

                if (closestPoint == null) {
                    return null;
                }

                return new CollisionInfo(closestPoint, closestObject);
            }

    // Remove a collidable from the list
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    }
