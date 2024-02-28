import java.util.List;
public class Disease {
    /**
     * Infect the cell with disease
     * @param cell
     */
    public void infect(Cell cell) {
        cell.getInfected();
    }

    /**
     * Spread disease to adjacent cells
     * @param cell
     */
    public void spreadDisease(Cell cell) {
        List<Cell> adjacentLivingCells = cell.getField().getLivingNeighbours(cell.getLocation());
        for (Cell cell1 : adjacentLivingCells) {
            cell1.getInfected();
        }
    }
}
