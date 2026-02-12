package view.Receptionist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public final class ButtonStyler {

    private ButtonStyler() {
        // Prevent instantiation
    }

    public static void styleButton(JButton button, Color background) {
        // Basic visual style
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        // Add custom painting for rounded corner
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background fill with rounded corners
                ButtonModel model = button.getModel();
                Color fill = background;

                if (model.isPressed()) {
                    fill = adjustBrightness(background, 0.9f);
                } else if (model.isRollover()) {
                    fill = adjustBrightness(background, 1.1f);
                }

                g2.setColor(fill);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20); // 20 = radius

                super.paint(g2, c);
                g2.dispose();
            }
        });

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(background.darker());
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(background);
                button.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(adjustBrightness(background, 0.9f)); // Darker
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Reset to hover or original depending on mouse position
                if (button.getBounds().contains(e.getPoint())) {
                    button.setBackground(adjustBrightness(background, 1.1f));
                } else {
                    button.setBackground(background);
                }
            }
        });
    }

    private static Color adjustBrightness(Color color, float factor) {
        int r = Math.min(255, (int)(color.getRed() * factor));
        int g = Math.min(255, (int)(color.getGreen() * factor));
        int b = Math.min(255, (int)(color.getBlue() * factor));
        return new Color(r, g, b);
    }
}

