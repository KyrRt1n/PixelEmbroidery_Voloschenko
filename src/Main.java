import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    static Color[] lastUsedColors = {Color.BLACK, new Color(55, 155, 55), Color.RED, Color.ORANGE, Color.YELLOW, Color.WHITE};
    static JButton[] colorButtons = new JButton[lastUsedColors.length];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main() {
        setTitle("Pixel embroidery by Voloschenko Artem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        EmbroideryGrid grid = new EmbroideryGrid(45, 45);
        GridPanel gridPanel = new GridPanel(grid);

        JToolBar toolbar = new JToolBar();
        JButton colorBtn = new JButton("Color");
        colorBtn.addActionListener(e -> {
            ColorPicker picker = new ColorPicker(this, gridPanel.getCurrentColor());
            picker.setVisible(true);
            Color chosen = picker.getSelectedColor();
            if (chosen != null) gridPanel.setCurrentColor(chosen);
        });
        toolbar.add(colorBtn);
        toolbar.addSeparator();

        JToggleButton horizontalMirror = new JToggleButton("Mirror horizontal");
        JToggleButton verticalMirror = new JToggleButton("Mirror vertical");
        JToggleButton fullMirror = new JToggleButton("Mirror fully");

        horizontalMirror.addActionListener(e ->
                gridPanel.toggleMirrorHorizontal(horizontalMirror.isSelected())
        );

        verticalMirror.addActionListener(e ->
                gridPanel.toggleMirrorVertical(verticalMirror.isSelected())
        );

        fullMirror.addActionListener(e ->
                gridPanel.toggleFullMirror(fullMirror.isSelected())
        );

        toolbar.add(horizontalMirror);
        toolbar.addSeparator();
        toolbar.add(verticalMirror);
        toolbar.addSeparator();
        toolbar.add(fullMirror);
        toolbar.addSeparator();

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null,"Are you sure you want to proceed?",
                    "Confirm Action", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                grid.ClearGrid();
                repaint();
            }
        });

        toolbar.add(clearBtn);

        JButton saveBtn = new JButton("Export PNG");
        saveBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("StandardEmbroidery.png"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(grid.exportToImg(), "PNG", fc.getSelectedFile());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
                }
            }
        });

        JButton loadBtn = new JButton("Import PNG");
        loadBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage img = ImageIO.read(fc.getSelectedFile());
                    grid.importFromImg(img);
                    gridPanel.repaint();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Load failed: " + ex.getMessage());
                }
            }
        });

        toolbar.add(Box.createHorizontalGlue());
        toolbar.add(saveBtn);
        toolbar.addSeparator();
        toolbar.add(loadBtn);

        add(toolbar, BorderLayout.NORTH);
        JToolBar colorBar = new JToolBar(JToolBar.VERTICAL);
        for (int i = 0; i < lastUsedColors.length; i++) {
            Color c = lastUsedColors[i];
            JButton btn = new JButton();
            btn.setBackground(c);
            btn.setPreferredSize(new Dimension(32, 32));
            btn.setBorderPainted(true);
            btn.setFocusPainted(false);

            colorButtons[i] = btn;

            btn.addActionListener(e -> {
                gridPanel.setCurrentColor(btn.getBackground());
                colorBar.repaint();
            });
            btn.setMaximumSize(btn.getPreferredSize());
            btn.setMinimumSize(btn.getPreferredSize());
            colorBar.add(btn);
            colorBar.addSeparator(new Dimension(0, 8));
        }
        add(colorBar, BorderLayout.WEST);
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void updateLastUsedColors(Color newColor) {
        for (Color curr : lastUsedColors)
            if (curr.equals(newColor))
                return;

        for (int i = lastUsedColors.length - 1; i > 0; i--)
            lastUsedColors[i] = lastUsedColors[i-1];
        lastUsedColors[0] = newColor;

        for (int i = 0; i < colorButtons.length; i++)
            if (colorButtons[i] != null)
                colorButtons[i].setBackground(lastUsedColors[i]);
    }

}