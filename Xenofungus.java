import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Xenofungus extends Cell{
    private static final int max_age = 20;
    public Xenofungus(Field field, Location location, Color color) {
        super(field, location, color);
    }

    private void breed(Location location) {
        List<Location> adjacentLocations = getField().adjacentLocations(location);
        List<Location> availableLocation = new ArrayList<>();
        for (Location locations: adjacentLocations) {
            Cell cell = getField().getObjectAt(locations);
            if (cell == null || !cell.isAlive()) {
                availableLocation.add(locations);
            }
        }
        if (availableLocation.size() > 0) {
            Random random = new Random();
            Location randomAvailableLocation = availableLocation.get(random.nextInt(availableLocation.size()));
            Xenofungus xeno = new Xenofungus(getField(), randomAvailableLocation,Color.ORANGE);
        }
    }

    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        List<Cell> neighboursXeno = new ArrayList<>();
        for (Cell  life: neighbours) {
            if (life instanceof Xenofungus) {
                neighboursXeno.add(life);
            }
        }
        if (isAlive()) {
            if (neighboursXeno.size() >= 2) {
                breed(getLocation());
            }
        }
    }
}
