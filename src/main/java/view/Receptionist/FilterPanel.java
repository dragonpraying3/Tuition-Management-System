package view.Receptionist;

import javax.swing.*;
import java.awt.*;

public class FilterPanel extends JPanel {
    JComboBox<String> levelBox;
    JComboBox<String> monthBox;
    JComboBox<String> paymentMethodBox;
    JComboBox<String> paymentStatusBox;
    JButton resetButton;

    public FilterPanel(String[] levelList, String[] monthList, String[] methodList, String[] statusList) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createTitledBorder("Advanced Filters"));
        setBackground(new Color(245, 245, 245));

        levelBox = new JComboBox<>(levelList);
        monthBox = new JComboBox<>(monthList);
        paymentMethodBox = new JComboBox<>(methodList);
        paymentStatusBox = new JComboBox<>(statusList);
        resetButton = new JButton("Reset Filter");

        add(new JLabel("Level: "));
        add(levelBox);
        add(Box.createHorizontalStrut(10));
        add(new JLabel("Month: "));
        add(monthBox);
        add(Box.createHorizontalStrut(10));
        add(new JLabel("Method: "));
        add(paymentMethodBox);
        add(Box.createHorizontalStrut(10));
        add(new JLabel("Status: "));
        add(paymentStatusBox);
        add(Box.createHorizontalStrut(10));
        add(resetButton);
    }
}


