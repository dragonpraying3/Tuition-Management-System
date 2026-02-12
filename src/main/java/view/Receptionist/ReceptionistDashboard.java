package view.Receptionist;

import model.User;
import view.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReceptionistDashboard extends JFrame {

    CardLayout cardLayout;
    JPanel cardPanel;
    UpdateStudentPanel updatePanel;
    DeleteStudentPanel deletePanel;
    RegisterStudentPanel registerPanel;
    PaymentPanel paymentPanel;
    ReceiptPanel receiptPanel;
    ProfilePanel profilePanel;
    HomePanel homePanel;
    private boolean isMenuCollapsed = false;
    private JPanel selectedMenu = null;
    private final Map<JPanel, JLabel> menuMap = new HashMap<>();
    //color
    private static final Color MENU_BG_COLOR = new Color(235, 242, 252);
    private static final Color HOVER_COLOR = new Color(224, 224, 224);
    private static final Color TEXT_COLOR = new Color(50, 50, 50);
    private static final Color SELECTED_COLOR = new Color(33, 150, 243);


    public ReceptionistDashboard(User receptionist) throws IOException {
        initJFrame();

        //student register menu
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        // add page
        homePanel = new HomePanel();
        updatePanel = new UpdateStudentPanel(this);
        deletePanel = new DeleteStudentPanel(this);
        registerPanel = new RegisterStudentPanel(this);
        paymentPanel = new PaymentPanel(this);
        receiptPanel = new ReceiptPanel(this);
        profilePanel = new ProfilePanel(this, receptionist);

        showCard("home");
        cardPanel.add(homePanel, "home");
        cardPanel.add(deletePanel, "delete");
        cardPanel.add(registerPanel, "register");
        cardPanel.add(updatePanel, "updateEnroll");
        cardPanel.add(paymentPanel, "payment");
        cardPanel.add(receiptPanel, "receipt");
        cardPanel.add(profilePanel, "profile");

        getContentPane().add(createMenuPanel(), BorderLayout.WEST);
        getContentPane().add(cardPanel, BorderLayout.CENTER);
    }

    private void initJFrame() {
        setSize(1000, 750);
        setTitle("Receptionist Dashboard");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/app-icons.png"));
        setIconImage(icon.getImage());
    }

    public void showCard(String name) {
        cardLayout.show(cardPanel, name);
    }

    public void refreshAllPanels() {
        updatePanel.refreshStudentList();
        deletePanel.refreshStudentTable();
        paymentPanel.refreshStudentTable();
        homePanel.refreshStats();
        receiptPanel.refreshStudentComboBox();
    }

    private JPanel createMenuPanel() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(235, 242, 252));
        menu.setPreferredSize(new Dimension(190, getHeight()));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // --- Header with Toggle Button ---
        JPanel header = new JPanel(new BorderLayout());
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS)); // Horizontal layout
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("Menu");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(100, 100, 100));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        ImageIcon rawToggle = new ImageIcon(getClass().getResource("/icons/menu.png"));
        ImageIcon iconToggle = new ImageIcon(rawToggle.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        JButton toggleButton = new JButton(iconToggle);
        toggleButton.setPreferredSize(new Dimension(40, 40)); // Match menu item height
        toggleButton.setFocusPainted(false);
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggleButton.setOpaque(false);
        toggleButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); // Left padding for icon alignment
        toggleButton.addActionListener(e -> toggleMenu(menu, toggleButton, titleLabel));


        header.add(toggleButton);
        header.add(titleLabel);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Top separator
        JSeparator topSeparator = createMenuSeparator();
        menu.add(header);
        menu.add(Box.createVerticalStrut(4));
        menu.add(topSeparator);
        menu.add(Box.createVerticalStrut(10));

        // --- Menu Items ---
        JPanel reg = makeMenuItem("Register Student", "/icons/addStudent.png", "register");
        JPanel upd = makeMenuItem("Update Enrolment", "/icons/enrolment.png", "updateEnroll");
        JPanel del = makeMenuItem("Remove Student", "/icons/removeStudent.png", "delete");
        JPanel pay = makeMenuItem("Accept Payment", "/icons/payment.png", "payment");
        JPanel rec = makeMenuItem("View Receipts", "/icons/receipt.png", "receipt");
        JPanel prof = makeMenuItem("Update Profile", "/icons/manageProfile.png", "profile");
        JPanel logout = makeMenuItem("Logout", "/icons/userLogout.png", "logout");

        // Student-related
        menu.add(reg);
        menu.add(Box.createVerticalStrut(12));
        menu.add(upd);
        menu.add(Box.createVerticalStrut(12));
        menu.add(del);
        menu.add(Box.createVerticalStrut(12));

        // Separator between sections
        menu.add(createMenuSeparator());
        menu.add(Box.createVerticalStrut(12));

        // Finance/Profile
        menu.add(pay);
        menu.add(Box.createVerticalStrut(12));
        menu.add(rec);
        menu.add(Box.createVerticalStrut(12));
        menu.add(prof);
        menu.add(Box.createVerticalStrut(12));

        // Bottom logout
        menu.add(Box.createVerticalGlue());
        menu.add(createMenuSeparator());
        menu.add(Box.createVerticalStrut(10));
        menu.add(logout);

        return menu;
    }

    private JSeparator createMenuSeparator() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(200, 200, 200));
        sep.setBackground(new Color(200, 200, 200));
        return sep;
    }

    private void toggleMenu(JPanel menu, JButton toggleBtn, JLabel titleLabel) {
        boolean targetCollapsed = !isMenuCollapsed;
        isMenuCollapsed = targetCollapsed;

        // Tooltip updates
        toggleBtn.setToolTipText(targetCollapsed ? "Expand Menu" : "Collapse Menu");

        // Update text visibility (at start or end of animation)
        if (targetCollapsed) {
            titleLabel.setText("");
            for (Map.Entry<JPanel, JLabel> entry : menuMap.entrySet()) {
                entry.getValue().setText("");
            }
        }

        // Animate width change
        int startWidth = menu.getWidth();
        int endWidth = targetCollapsed ? 70 : 190;
        int steps = 15;
        int delay = 10; // milliseconds
        int delta = (endWidth - startWidth) / steps;

        Timer timer = new Timer(delay, null);
        final int[] currentStep = {0};

        timer.addActionListener(e -> {
            currentStep[0]++;
            int newWidth = startWidth + delta * currentStep[0];
            menu.setPreferredSize(new Dimension(newWidth, menu.getHeight()));
            menu.revalidate();
            menu.repaint();

            if (currentStep[0] >= steps) {
                ((Timer) e.getSource()).stop();

                // Ensure final width is exact
                menu.setPreferredSize(new Dimension(endWidth, menu.getHeight()));
                menu.revalidate();
                menu.repaint();

                // Show texts after expanding
                if (!targetCollapsed) {
                    titleLabel.setText("Menu");
                    for (Map.Entry<JPanel, JLabel> entry : menuMap.entrySet()) {
                        JLabel label = entry.getValue();
                        label.setText((String) label.getClientProperty("text"));
                    }
                }
            }
        });
        timer.start();
    }

    private JPanel makeMenuItem(String text, String iconPath, String cardName) {

        ImageIcon raw = new ImageIcon(getClass().getResource(iconPath));
        ImageIcon icon = new ImageIcon(raw.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        JLabel label = new JLabel(text, icon, JLabel.LEFT);
        label.setForeground(TEXT_COLOR);
        label.setIconTextGap(12);
        label.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean isSelected = Boolean.TRUE.equals(getClientProperty("selected"));
                Color bg = isSelected ? SELECTED_COLOR : getBackground();
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            }
        };
        panel.add(label, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(200, 40));
        panel.setOpaque(false); // Must be false to allow custom background drawing
        panel.setMaximumSize(new Dimension(200, 40));
        panel.setBackground(MENU_BG_COLOR);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Custom property to track animation
        panel.putClientProperty("hoverTimer", null);
        label.putClientProperty("text", text);

        // Hover effect
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (panel != selectedMenu) {
                    animateHover(panel, panel.getBackground(), HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (panel != selectedMenu) {
                    animateHover(panel, panel.getBackground(), MENU_BG_COLOR);
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // Do nothing, avoid interfering with selected state
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                // Do nothing, animation handled by hover
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if ("logout".equals(cardName)) {
                    dispose();
                    new LoginPage().setVisible(true);
                } else {
                    cardLayout.show(cardPanel, cardName);

                    // 1. Clear old selection background and label color
                    if (selectedMenu != null) {
                        selectedMenu.putClientProperty("selected", false);
                        stopHoverAnimation(selectedMenu);
                        selectedMenu.setBackground(MENU_BG_COLOR);
                        selectedMenu.repaint();
                        JLabel oldLabel = menuMap.get(selectedMenu);
                        if (oldLabel != null) oldLabel.setForeground(TEXT_COLOR);
                    }

                    // 2. Set current panel as selected
                    stopHoverAnimation(panel);
                    panel.putClientProperty("selected", true);
                    panel.setBackground(SELECTED_COLOR);
                    label.setForeground(Color.white);
                    panel.repaint();

                    selectedMenu = panel;
                }
            }
        });
        menuMap.put(panel, label);
        return panel;
    }

    /**
     * Creates a smooth background color transition animation for a JPanel.
     *
     * @param panel      The target JPanel to apply the animation on.
     * @param startColor The initial background color before animation.
     * @param endColor   The final background color after animation.
     */
    private void animateHover(JPanel panel, Color startColor, Color endColor) {
        // if is current selection , return no do any animation
        if (Boolean.TRUE.equals(panel.getClientProperty("selected"))) return;
        // Cancel any previous animation
        stopHoverAnimation(panel);
        // Runs every 10 milliseconds
        Timer timer = new Timer(10, null);
        panel.putClientProperty("hoverTimer", timer);
        // Current animation step
        final int[] step = {0};
        // Total steps for the animation
        final int steps = 15;

        timer.addActionListener(e -> {
            // Progress ratio (0.0 to 1.0)
            float ratio = (float) step[0] / steps;
            // Linear interpolation for each RGB component
            int r = (int) (startColor.getRed() * (1 - ratio) + endColor.getRed() * ratio);
            int g = (int) (startColor.getGreen() * (1 - ratio) + endColor.getGreen() * ratio);
            int b = (int) (startColor.getBlue() * (1 - ratio) + endColor.getBlue() * ratio);

            panel.setBackground(new Color(r, g, b));
            step[0]++;

            if (step[0] > steps) {
                ((Timer) e.getSource()).stop();
                panel.putClientProperty("hoverTimer", null);
            }
        });

        timer.start();
    }

    private void stopHoverAnimation(JPanel panel) {
        Timer oldTimer = (Timer) panel.getClientProperty("hoverTimer");
        if (oldTimer != null) {
            oldTimer.stop();
            panel.putClientProperty("hoverTimer", null);
        }
    }
}
