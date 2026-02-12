package view.Receptionist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BottomContainer extends JPanel {

    public JButton leftButton;
    public JButton rightButton1;
    public JButton rightButton2;
    private static final Color BG_COLOR = new Color(245, 248, 255);


    public BottomContainer(String leftText, Color leftColor, ActionListener leftAction,
                           String midText, Color midColor, ActionListener midAction,
                           String rightText, Color rightColor, ActionListener rightAction) {
        this.setLayout(new BorderLayout());
        this.setBackground(BG_COLOR);

        leftButton = new JButton(leftText);
        rightButton1 = new JButton(midText);
        rightButton2 = new JButton(rightText);


        // === Left panel (Home)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false); // Make transparent to inherit parent bg
        leftButton = new JButton(leftText);
        ButtonStyler.styleButton(leftButton, leftColor);
        leftButton.addActionListener(leftAction);
        leftPanel.add(leftButton);

        // === Right panel (Reset + Main)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false); //transparent

        rightButton1 = new JButton(midText);
        ButtonStyler.styleButton(rightButton1, midColor);
        rightButton1.addActionListener(midAction);

        rightButton2 = new JButton(rightText);
        ButtonStyler.styleButton(rightButton2, rightColor);
        rightButton2.addActionListener(rightAction);

        rightPanel.add(rightButton1);
        rightPanel.add(rightButton2);

        // Add to main container
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }
}

