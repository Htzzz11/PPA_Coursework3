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
    public Xenofungus(Field field, Location location, Color color) {
        super(field, location, color);
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
                cell.getInfected(false);
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

    public void spreadDisease(Cell cell) {
        List<Cell> adjacentLivingXeno = cell.getField().getLivingNeighbours(cell.getLocation());
        for (Cell cell1 : adjacentLivingXeno) {
            cell1.getInfected(true);
        }
    }

    public void act() {
        List<Cell> neighbourXeno = getNeighbourLivingXeno();
        if (isAlive()) {
            incrementAge();
            if (!isInfected()) {
                if (getAge() < max_age) setNextState(true);
                if (getAge() <= 5) {
                    if (neighbourXeno.size() <= 3) revive(getLocation());
                } else {
                    setColor(Color.GREEN);
                    Random random = new Random();
                    int randomInt = random.nextInt(6);
                    switch (randomInt) {
                        case 0:
                            getInfected(true);
                            spreadDisease(getField().getObjectAt(getLocation()));
                            break;
                        case 1:
                            setNextState(false);
                            break;
                        default:
                            revive(getLocation());
                            break;
                    }
                }
            } else {
                setColor(Color.DARKGREEN);
            }
            if (getAge() > 10) setNextState(false);
        }
    }
}
