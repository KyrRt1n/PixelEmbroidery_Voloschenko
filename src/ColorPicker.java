import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ColorPicker extends JDialog {

    private Color selectedColor;
    private final BufferedImage wheelImage;
    private final JPanel preview;
    private final int SIZE = 250;

    public ColorPicker(JFrame parent, Color initial) {
        super(parent, "Pick a color", true);
        this.selectedColor = initial;
        BufferedImage loaded;
        try {
            loaded = ImageIO.read(new File("src/wheel.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.wheelImage = loaded;

        JPanel wheelPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(wheelImage, 0, 0, null);
            }
        };
        wheelPanel.setPreferredSize(new Dimension(SIZE, SIZE));

        MouseAdapter ma = new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) { pick(e.getX(), e.getY()); }
            @Override public void mouseDragged(MouseEvent e) { pick(e.getX(), e.getY()); }
        };
        wheelPanel.addMouseListener(ma);
        wheelPanel.addMouseMotionListener(ma);

        preview = new JPanel();
        preview.setBackground(initial);
        preview.setPreferredSize(new Dimension(SIZE, 30));

        Color[] palette = {
                Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE,
                Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA,
                new Color(0x8B4513), new Color(0xFFD700), new Color(0x800080),
                new Color(0x006400), new Color(0x00008B), new Color(0x8B0000)
        };

        JPanel palettePanel = new JPanel(new GridLayout(2, 8, 2, 2));
        for (Color c : palette) {
            JButton btn = new JButton();
            btn.setBackground(c);
            btn.setPreferredSize(new Dimension(24, 24));
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.addActionListener(e -> {
                selectedColor = c;
                preview.setBackground(c);
                preview.repaint();
            });
            palettePanel.add(btn);
        }

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> dispose());
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> { selectedColor = null; dispose(); });

        JPanel btnPanel = new JPanel();
        btnPanel.add(ok);
        btnPanel.add(cancel);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(palettePanel, BorderLayout.SOUTH);
        southPanel.add(btnPanel, BorderLayout.NORTH);

        setLayout(new BorderLayout(5, 5));
        add(wheelPanel, BorderLayout.CENTER);
        add(preview, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private void pick(int x, int y) {
        int cx = SIZE / 2, cy = SIZE / 2;
        double dist = Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
        if (dist > SIZE / 2) return;
        int rgb = wheelImage.getRGB(x, y);
        selectedColor = new Color(rgb);
        preview.setBackground(selectedColor);
        preview.repaint();
    }

    public Color getSelectedColor() { return selectedColor; }
}