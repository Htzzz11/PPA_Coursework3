import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Yeast extends Cell{
    public Yeast(Field field, Location location, Color color) {
            super(field, location, color);
    }
    public void act() {
        setNextState(true);
        ArrayList<Location> availableParasitizeLocation = getAdjXenoLocation();
        if(isAlive())
            if(availableParasitizeLocation.isEmpty()){
                setNextState(false);
            }else {
                Random rand = new Random();
                Xenofungus x = (Xenofungus) getField().getObjectAt(availableParasitizeLocation.get(rand.nextInt(availableParasitizeLocation.size())));
                x.beParasitized();
                getField().clear(getLocation());
            }

    }
    // 如果周围有Xeno 的话当前位置设为空 随机寄生周围的Xeno 被寄生的Xeno设为被寄生状态
    public void parasitize(){


        }
        // return Adjecent location of Xenofungus
    private ArrayList<Location> getAdjXenoLocation(){
        List<Location> adjacentLocations = getField().adjacentLocations(getLocation());
        List<Location> availableParasitizeLocation = new ArrayList<>();
        for (Location locations: adjacentLocations) {
            Cell cell = getField().getObjectAt(locations);
            if (cell instanceof Xenofungus) {
                availableParasitizeLocation.add(locations);
            }
        }
            return (ArrayList<Location>) availableParasitizeLocation;
        }
        // 如果宿主死了的话 宿主所在的位置变成Yeast 并且在周围找一个空位繁殖一个 繁殖的为棕色

}

