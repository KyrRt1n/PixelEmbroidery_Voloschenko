import java.awt.Color;

public class EmbroideryGrid {
    private final Color[][] cells;
    private final int rows, cols;

    public EmbroideryGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Color[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                cells[r][c] = Color.WHITE;
    }

    public void ClearGrid(){
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                cells[r][c] = Color.WHITE;
    }

    public Color getCellColor(int row, int col) {
        return cells[row][col];
    }

    public void setCell(int row, int col, Color color) {
        cells[row][col] = color;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}