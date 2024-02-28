import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromafire extends Cell{
    private boolean isParasitized;
    private static final int Max_Age = 5;
    public Chromafire(Field field, Location location, Color color) {
        super(field, location, color);
        isParasitized = false;
    }
    @Override
    //如果大于三个的话繁殖 如果小于两个的话直接死
    public void act() {
        neighbours = getLivingNeighbours();
        if(isAlive()){
            incrementAge();
            if (getAge() < Max_Age) {
                setNextState(true);
                if(neighbours.size() < 2) {
                    setNextState(false);
                }
                if(neighbours.size() > 3) {
                    reproduce();
                }
            }
            else {
                setNextState(false);
            }
        }
        else {
            checkYeastBreed();
        }
    }
    public void checkYeastBreed() {
        Random random = new Random();
        List<Location> adjCfoLocation = getAdjChromaLocation();
        for (Location location : adjCfoLocation) {
            if (isParasitized) {
                getField().place(new Yeast(getField(), getLocation(), Color.RED), location);
                ArrayList<Location> availableLocation = getAvailableLocation();
                if (!availableLocation.isEmpty()) {
                    getField().place(new Yeast(getField(), getLocation(), Color.RED), availableLocation.get(random.nextInt(availableLocation.size())));
                }
                if (!availableLocation.isEmpty()) {
                    getField().place(new Yeast(getField(), getLocation(), Color.RED), availableLocation.get(random.nextInt(availableLocation.size())));
                }
            }
        }
    }
    private List<Location> getAdjChromaLocation() {
        List<Location> adjacentLocations = getField().adjacentLocations(getLocation());
        List<Location> availableParasitizeLocation = new ArrayList<>();
        for (Location locations: adjacentLocations) {
            Cell cell = getField().getObjectAt(locations);
            if (cell instanceof Chromafire) {
                availableParasitizeLocation.add(locations);
            }
        }
        return availableParasitizeLocation;
    }
    private void reproduce() {
        Random random = new Random();
        ArrayList<Location> availableLocation = getAvailableLocation();
        if(!availableLocation.isEmpty()) {
            Chromafire chroma = (new Chromafire(getField(), getLocation(), Color.LIGHTGREEN));
            int randomInt = random.nextInt(availableLocation.size());
            getField().place(chroma,availableLocation.get(randomInt));
        }
    }
    public void isParasitized() {
        isParasitized = true;
        setAge(getAge()*2);
        getField().getObjectAt(getLocation()).setColor(Color.DARKBLUE);
    }
}
