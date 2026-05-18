import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main() {
        setTitle("Pixel embroidery by Voloschenko Artem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        EmbroideryGrid grid = new EmbroideryGrid(40, 50);
        GridPanel gridPanel = new GridPanel(grid);

        JToolBar toolbar = new JToolBar();
        JButton colorBtn = new JButton("Color");
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

        toolbar.add(clearBtn);

        add(toolbar, BorderLayout.NORTH);
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}