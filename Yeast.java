import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Yeast extends Cell {
    public Yeast(Field field, Location location, Color color) {
        super(field, location, color);
    }

    // 如果周围有Xeno 的话当前位置设为空 随机寄生周围的Xeno 被寄生的Xeno设为被寄生状态
    public void act() {
        List<Location> availableParasitizeLocation = getAdjCfLocation();
        if (isAlive()) {
            if (availableParasitizeLocation.isEmpty()) {
                setNextState(false);
            } else {
                Random rand = new Random();
                Chromafire c = (Chromafire) getField().getObjectAt(availableParasitizeLocation.get(rand.nextInt(availableParasitizeLocation.size())));
                c.isParasitized();
                getField().clear(getLocation());
            }
        }
    }

    // Returns a List of adjacent Chromafire
    private List<Location> getAdjCfLocation() {
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


