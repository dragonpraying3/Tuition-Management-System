package view.Student;

import controller.ValidUser;
import model.User;
import view.Admin.AdminView;
import view.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class StudentView extends JFrame {

    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel menuPanel;
    private JButton toggleMenuButton;
    private String Username;
    private PaymentView paymentView;

    public StudentView() throws IOException {
        this.Username = ValidUser.getCurrentUser();

        paymentView = new PaymentView(this, Username);

        setTitle("Student Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with BorderLayout: WEST = menu, CENTER = content
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        add(mainPanel);

        // Header panel at top
        headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Welcome, " + Username, JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(70, 130, 180));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Left vertical menu panel
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(30, 144, 255)); // Dodger Blue
        menuPanel.setPreferredSize(new Dimension(220, 0));

        // Create section labels and buttons
        JLabel studentLabel = createSectionLabel("Student");
        JButton btnViewSchedule = createMenuButton("View Schedule");
        JButton btnSubjectChange = createMenuButton("Change Subject");

        JLabel paymentLabel = createSectionLabel("Payment");
        JButton btnMakePayment = createMenuButton("Make Payment");
        JButton btnViewBalance = createMenuButton("View Total Balance");

        JLabel accountLabel = createSectionLabel("Account");
        JButton btnUpdateProfile = createMenuButton("Update Profile");
        JButton btnLogout = createMenuButton("Logout");

        // Add components to menu panel with spacing
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(studentLabel);
        menuPanel.add(btnViewSchedule);
        menuPanel.add(btnSubjectChange);

        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(paymentLabel);
        menuPanel.add(btnMakePayment);
        menuPanel.add(btnViewBalance);

        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(accountLabel);
        menuPanel.add(btnUpdateProfile);
        menuPanel.add(btnLogout);

        menuPanel.add(Box.createVerticalGlue());

        mainPanel.add(menuPanel, BorderLayout.WEST);

        // Content panel on right
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(200, 230, 255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        ImageIcon bookIcon = new ImageIcon("src/main/resources/icons/book.png");
        Image scaled = bookIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);

        JLabel bookLabel = new JLabel(scaledIcon);
        bookLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(bookLabel);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Toggle menu button
        toggleMenuButton = new JButton("☰ Menu");
        toggleMenuButton.setFocusPainted(false);
        toggleMenuButton.setVisible(false);  // hide initially
        toggleMenuButton.setBackground(new Color(125,167,202));
        toggleMenuButton.setForeground(new Color(255,255,255));
        toggleMenuButton.setFont(new Font("Sanserif",Font.BOLD,18));
        toggleMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        headerPanel.add(toggleMenuButton, BorderLayout.WEST);

        // Button actions for menu items
        btnViewSchedule.addActionListener(e -> {
            switchContent(new ScheduleWindow(this));
            hideMenu();
        });

        btnSubjectChange.addActionListener(e -> {
            switchContent(new SubjectChange(this, Username));
            hideMenu();
        });

        btnMakePayment.addActionListener(e -> {
            paymentView.makePaymentPanel();
            switchContent(paymentView);
            hideMenu();
        });

        btnViewBalance.addActionListener(e -> {
            paymentView.viewTotalBalancePanel();
            switchContent(paymentView);
            hideMenu();
        });

        btnUpdateProfile.addActionListener(e -> {
            switchContent(new StudentProfilePanel(this, Username));  // pass 'this' as parent
            hideMenu();
        });

        btnLogout.addActionListener(e -> logout());

        // Toggle menu button shows menu again
        toggleMenuButton.addActionListener(e -> showMenu());

        setVisible(true);
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        label.setOpaque(true); // make background visible
        label.setBackground(new Color(25, 25, 112)); // dark blue (Midnight Blue)
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // full width, fixed height
        return label;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setBackground(new Color(135, 206, 250));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(135, 206, 250));
            }
        });

        return button;
    }

    private void switchContent(JPanel newView) {
        contentPanel.removeAll();
        contentPanel.add(newView, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void hideMenu() {
        menuPanel.setVisible(false);
        toggleMenuButton.setVisible(true);
    }

    private void showMenu() {
        menuPanel.setVisible(true);
        toggleMenuButton.setVisible(false);
    }

    private void logout() {
        dispose(); // Close the current JFrame (e.g. StudentView, ReceptionistView)
        new LoginPage().setVisible(true);
    }

    public void returnToMainMenu() {
        // Clear only content panel inside mainPanel
        contentPanel.removeAll();

        ImageIcon bookIcon = new ImageIcon("src/main/resources/icons/book.png");
        Image scaled = bookIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);

        JLabel bookLabel = new JLabel(scaledIcon);
        bookLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(bookLabel);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Refresh content panel UI
        contentPanel.revalidate();
        contentPanel.repaint();

        // Show the menu panel again (if hidden)
        showMenu();
    }

}
