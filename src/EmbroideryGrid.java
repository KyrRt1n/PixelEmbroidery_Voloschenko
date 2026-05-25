import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class EmbroideryGrid {
    private final Color[][] cells;
    private final int rows, cols;

    public BufferedImage exportToImg() {
        BufferedImage img = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                img.setRGB(c, r, cells[r][c].getRGB());
        return img;
    }

    public void importFromImg(BufferedImage img){
        int maxRows = Math.min(rows, img.getHeight());
        int maxCols = Math.min(cols, img.getWidth());

        for(int r = 0; r < maxRows; r++){
            for(int c = 0; c < maxCols; c++) {
                cells[r][c] = new Color(img.getRGB(c, r));
            }
        }
    }

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