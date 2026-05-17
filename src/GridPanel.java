import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GridPanel extends JPanel {
    private static final int cellSize = 20;
    private final EmbroideryGrid grid;
    private Color currentColor = Color.BLACK;

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
                    repaint();
                }
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
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
}