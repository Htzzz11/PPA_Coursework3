import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Yeast extends Cell {
    public Yeast(Field field, Location location, Color color) {
        super(field, location, color);
    }

    /* If there is Chromafire nearby. it will paratize to it, so that null is putting at current location  
       else it will dead.
    */
    public void act() {
        List<Location> availableParasitizeLocation = getAdjChromaLocation();
        if (isAlive()) {
            if (availableParasitizeLocation.isEmpty()) {
                setNextState(false);
            } else {
                Random rand = new Random();
                int randomInt = rand.nextInt(availableParasitizeLocation.size());
                Chromafire c = (Chromafire) getField().getObjectAt(availableParasitizeLocation.get(randomInt));
                c.isParasitized();
                getField().clear(getLocation());
            }
        }
    }

    // Returns a List of adjacent Chromafire
    private List<Location> getAdjChromaLocation() {
        List<Location> adjacentLocations = getField().adjacentLocations(getLocation());
        List<Location> availableParasitizeLocation = new ArrayList<>();
        for (Location locations : adjacentLocations) {
            Cell cell = getField().getObjectAt(locations);
            if (cell instanceof Chromafire) {
                availableParasitizeLocation.add(locations);
            }
        }
        return availableParasitizeLocation;
    }
}


