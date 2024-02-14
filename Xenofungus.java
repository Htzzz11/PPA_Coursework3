import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Xenofungus extends Cell{
    private static final int max_age = 10;
    private int age;
    public Xenofungus(Field field, Location location, Color color) {
        super(field, location, color);
    }

    private void revive(Location location) {
        List<Location> adjacentLocations = getField().adjacentLocations(location);
        List<Location> availableLocation = new ArrayList<>();
        for (Location locations: adjacentLocations) {
            Cell cell = getField().getObjectAt(locations);
            if (cell instanceof Xenofungus && !cell.isAlive()) {
                availableLocation.add(locations);
            }
        }
        if (!availableLocation.isEmpty()) {
            Random random = new Random();
            Location randomAvailableLocation = availableLocation.get(random.nextInt(availableLocation.size()));
            Cell xeno = getField().getObjectAt(randomAvailableLocation);
            xeno.setColor(Color.GREEN);
            xeno.setNextState(true);
        }
    }

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
            if (neighbourXeno.size() == 1) {
                revive(getLocation());
                age++;
            }
        }
        if (age > max_age) {
            setNextState(false);
        }
    }
}
