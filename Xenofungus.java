import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implementation of life Xenofungus.
 * Xenofungus' two major behaviours are revive adjacent cells and have a chance to develop disease and spread it.
 */
public class Xenofungus extends Cell{
    private static final int max_age = 10;
    private int age;
    public Xenofungus(Field field, Location location, Color color) {
        super(field, location, color);
        age = 0;
    }

    /**
     * Revives all adjacent Xenofungus and sets their age to 0.
     * @param location Xenofungus's location
     */
    private void revive(Location location) {
        List<Location> adjacentLocations = getField().adjacentLocations(location);
        List<Location> reviveLocation = new ArrayList<>();
        for (Location locations: adjacentLocations) {
            Cell cell = getField().getObjectAt(locations);
            if (cell != null && !cell.isAlive()) {
                reviveLocation.add(locations);
            }
        }
        if (!reviveLocation.isEmpty()) {
            for (Location location1 : reviveLocation) {
                Cell cell = getField().getObjectAt(location1);
                cell.setNextState(true);
                cell.setAge(0);
            }
        }
    }

    // Returns a list of adjacent living Xenofungus
    private List<Cell> getNeighbourLivingXeno() {
        neighbours = getLivingNeighbours();
        List<Cell> neighboursXeno = new ArrayList<>();
        for (Cell cell: neighbours) {
            if (cell instanceof Xenofungus) {
                neighboursXeno.add(cell);
            }
        }
        return neighboursXeno;
    }

    public void act() {
        List<Cell> neighbourXeno = getNeighbourLivingXeno();
        if (isAlive()) {
            age++;
            if (age < max_age) setNextState(true);
            if (age <= 5) {
                if (neighbourXeno.size() <= 3) revive(getLocation());
            } else {
                setColor(Color.DARKGREEN);
                Random random = new Random();
                int randomInt = random.nextInt(4);
                switch (randomInt) {
                    case 0:
                        //Disease
                        break;
                    case 1:
                        setNextState(false);
                        break;
                    default:
                        revive(getLocation());
                        break;
                }
            }
            if (age > 10) setNextState(false);
        }
    }
}
