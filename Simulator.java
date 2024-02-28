import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.Random;


/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2024.02.03
 */

public class Simulator {

    private static final double Mycoplasma_Creation_Prob = 0.5;
    private static final double MYCOPLASMA_ALIVE_PROB = 0.3;
    private static final double Xenofungus_Creation_Prob = 0.75;
    private static final double Xenofungus_Alive_Prob =0.3;
    private static final double Yeast_Creation_Prob = 0.5;
    private static final double Yeast_Alive_Prob = 0.7;
    private static final double Chromafire_Creation_Prob = 0.5;
    private static final double Chromafire_Alive_Prob = 0.7;
    private Cell[][] cells;
    private Field field;
    private int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        field = new Field(depth, width);
        cells = field.getAllCell();
        reset();
    }

    /**
     * Run the simulation fdrom its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        for (Cell[] cell : cells) {
            for(Cell cell1 : cell){
                if(cell1 != null)
                    cell1.act();
            }
        }

        for (Cell[] cell : cells) {
            for(Cell cell1 : cell){
                if(cell1 != null)
                    cell1.updateState();
            }
        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        for (Cell[] cell : cells) {
            for(Cell cell1 : cell){
                cell1 = null;
            }
        }
        populate();
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                if (rand.nextDouble() <= Mycoplasma_Creation_Prob) {
                    Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
                    cells[row][col] = myco;
                    if (rand.nextDouble() >= MYCOPLASMA_ALIVE_PROB) {
                        myco.setDead();
                    }
                } else if (rand.nextDouble() <= Xenofungus_Creation_Prob) {
                    Xenofungus xeno = new Xenofungus(field, location, Color.LIGHTGREEN);
                    cells[row][col] = xeno;
                    if (rand.nextDouble() >= Xenofungus_Alive_Prob) {
                        xeno.setDead();
                    }
                } else if (rand.nextDouble() <= Yeast_Creation_Prob) {
                    Yeast yeast = new Yeast(field, location, Color.BLACK);
                    cells[row][col] = yeast;
                    if (rand.nextDouble() >= Yeast_Alive_Prob) {
                        yeast.setDead();
                    }
                } else if (rand.nextDouble() <= Chromafire_Creation_Prob) {
                    Chromafire chromafire = new Chromafire(field, location, Color.BLUE);
                    cells[row][col] = chromafire;
                    if (rand.nextDouble() >= Chromafire_Alive_Prob) {
                        chromafire.setDead();
                    }
                }
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    public Field getField() {
        return field;
    }

    public int getGeneration() {
        return generation;
    }
}
