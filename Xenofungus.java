import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Xenofungus extends Cell{
    private static final int max_age = 10;
    private int age;
    private boolean beParasitized;
    public Xenofungus(Field field, Location location, Color color) {
        super(field, location, color);
        age = 0;
        beParasitized = false;
    }

    /**
     * Takes a random adjacent dead Xenofungus and brings it back to life with age = 0.
     * @param location Xenofungus's location
     */
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
            xeno.setNextState(true);
            xeno.setAge(0);
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
    public void beParasitized(){
        beParasitized = true;
        age *= 2;
    }
    public boolean parasiticState(){
        return beParasitized;
    }



    public void act() {
        List<Cell> neighbourXeno = getNeighbourLivingXeno();
        if (isAlive()) {
            age++;
            if (neighbourXeno.size() <= 2) {
                revive(getLocation());
            }
            if (age >= 5) {
                setColor(Color.GREEN);
            }
        }
        if (age > max_age) {
            setNextState(false);
            }
        }
    }
