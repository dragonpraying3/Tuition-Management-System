package view.Receptionist;

import javax.swing.*;
import java.awt.*;

/**
 * A reusable statistics display box with icon, label, and count text.
 * Designed with modern UI (rounded corners and soft colors).
 */
public class StatBox extends JPanel {
    private JLabel countLabel;
    private static final Color BOX_BG = new Color(255, 255, 255);          // Box background
    private static final Color TEXT_COLOR = new Color(60, 60, 60);         // Text color
    private static final Color BORDER_COLOR = new Color(220, 220, 220);    // Border color

    public StatBox(String iconPath, String labelText, String countText, Color bgColor) {
        setPreferredSize(new Dimension(300, 60));
        setOpaque(false); // We draw our own background
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Load and scale icon
        ImageIcon raw = new ImageIcon(getClass().getResource(iconPath));
        Image scaled = raw.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon smallIcon = new ImageIcon(scaled);

        JLabel iconLabel = new JLabel(smallIcon);

        countLabel = new JLabel(labelText + " : " + countText);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        countLabel.setForeground(TEXT_COLOR);

        add(iconLabel);
        add(countLabel);
    }

    /**
     * Updates the count part of the label.
     * @param newCount the new value to display.
     */
    public void setCount(String newCount) {
        String oldText = countLabel.getText();
        int sep = oldText.indexOf(':');
        if (sep != -1) {
            String label = oldText.substring(0, sep + 1); // include colon
            countLabel.setText(label + " " + newCount);
        }
    }

    /**
     * Custom background paint with rounded corners.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable antialiasing for smooth corners
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set background color
        g2.setColor(BOX_BG);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

        // Optional border
        g2.setColor(BORDER_COLOR);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

        g2.dispose();
        super.paintComponent(g);
    }
}