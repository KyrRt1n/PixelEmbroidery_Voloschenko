import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridPanel extends JPanel {
    private static final int cellSize = 20;
    private final EmbroideryGrid grid;
    private Color currentColor = Color.BLACK;

    private boolean mirrorHorizontal = false;
    private boolean mirrorVertical = false;
    private boolean mirrorFull = false;

    public GridPanel(EmbroideryGrid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(grid.getCols() * cellSize,grid.getRows() * cellSize));

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                paint(e);
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                paint(e);
            }

            private void paint(MouseEvent e) {
                int col = e.getX() / cellSize;
                int row = e.getY() / cellSize;

                if (row >= 0 && row < grid.getRows() && col >= 0 && col < grid.getCols()) {
                    grid.setCell(row, col, currentColor);
                    if (mirrorHorizontal)
                        grid.setCell(row, grid.getCols() - 1 - col, currentColor);
                    if (mirrorVertical)
                        grid.setCell(grid.getRows() - 1 - row, col, currentColor);
                    if (mirrorFull) {
                        grid.setCell(row, grid.getCols() - 1 - col, currentColor);
                        grid.setCell(grid.getRows() - 1 - row, col, currentColor);
                        grid.setCell(grid.getRows() - 1 - row, grid.getCols() - 1 - col, currentColor);
                    }
                    repaint();
                }
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public void setCurrentColor(Color c) {
        this.currentColor = c;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                g.setColor(grid.getCellColor(r, c));
                g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
                g.setColor(Color.LIGHT_GRAY);
                g.drawRect(c * cellSize, r * cellSize, cellSize, cellSize);
            }
        }
    }

    public void toggleMirrorHorizontal(boolean v) {
        mirrorHorizontal = v;
    }

    public void toggleMirrorVertical(boolean v) {
        mirrorVertical = v;
    }

    public void toggleFullMirror(boolean v) {
        mirrorFull = v;
    }
}