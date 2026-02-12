package view.Receptionist;

import controller.Financial;
import controller.Registration;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomePanel extends JPanel {

    private JLabel clockLabel;
    private JPanel statsPanel;
    StatBox statBox1, statBox2;
    //color
    private static final Color BACKGROUND_COLOR = new Color(245, 248, 255);
    private static final Color STAT_TEXT_COLOR = new Color(50, 50, 50);
    private static final Color SUBTEXT_COLOR = new Color(100, 110, 130);

    public HomePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));

        JLabel welcomeLabel = new JLabel("Welcome, Receptionist!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setForeground(STAT_TEXT_COLOR);

        clockLabel = new JLabel();
        clockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        clockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        clockLabel.setForeground(SUBTEXT_COLOR);
        updateClock();

        JLabel description = new JLabel("Please use the menu on the left to navigate.");
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        description.setForeground(SUBTEXT_COLOR);

        //Add statistics panel
        statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        statsPanel.setBackground(BACKGROUND_COLOR);
        statsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        int studentCount = Registration.loadStudentFromFile().size();
        long pendingPayments = Financial.getAllPayment().stream()
                .filter(p -> p.getStatus().equalsIgnoreCase("Pending"))
                .count();

        statBox1 = new StatBox("/icons/studentCount.png", "Total Registered Students", String.valueOf(studentCount), Color.WHITE);
        statBox2 = new StatBox("/icons/pendingCount.png", "Pending Payments", String.valueOf(pendingPayments), Color.WHITE);

        statsPanel.add(Box.createHorizontalGlue());
        statsPanel.add(statBox1);
        statsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        statsPanel.add(statBox2);
        statsPanel.add(Box.createHorizontalGlue());

        //add to main
        add(welcomeLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(clockLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(description);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(statsPanel);

        // start clock timer
        new Timer(1000, e -> updateClock()).start();
    }

    private void updateClock() {
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        clockLabel.setText(time);
    }

    public void refreshStats() {
        int studentCount = Registration.loadStudentFromFile().size();
        long pendingPayments = Financial.getAllPayment().stream()
                .filter(p -> p.getStatus().equalsIgnoreCase("Pending"))
                .count();

        statBox1.setCount(String.valueOf(studentCount));
        statBox2.setCount(String.valueOf(pendingPayments));
    }
}

