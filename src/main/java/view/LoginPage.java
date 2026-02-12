package view;

import controller.ValidUser;
import model.*;
import view.Admin.AdminView;
import view.Receptionist.ReceptionistDashboard;
import view.Student.StudentView;
import view.Tutor.TutorView;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JLabel messageLabel;

    public LoginPage() {
        setTitle("Tuition Management System");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(33, 150, 243));
        leftPanel.setPreferredSize(new Dimension(250, 400));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Load ATC logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/icons/atc_logo.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Centre Name
        JLabel centreName1 = new JLabel("Advanced Tuition Centre");
        centreName1.setFont(new Font("Segoe UI", Font.BOLD, 18));
        centreName1.setForeground(Color.WHITE);
        centreName1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel centreName2 = new JLabel("(ATC)");
        centreName2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        centreName2.setForeground(Color.WHITE);
        centreName2.setAlignmentX(Component.CENTER_ALIGNMENT);

        //welcome text
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel devLabel = new JLabel("Developed by");
        devLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        devLabel.setForeground(Color.WHITE);
        devLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel devName = new JLabel("2 Power of 2");
        devName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        devName.setForeground(Color.WHITE);
        devName.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(logoLabel); // ATC logo
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(centreName1);
        leftPanel.add(centreName2);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(welcomeLabel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(devLabel);
        leftPanel.add(devName);
        leftPanel.add(Box.createVerticalStrut(10));

        //right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        //title center
        JLabel title = new JLabel("Login Page");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        rightPanel.add(title);

        //username
        JLabel usernameText = new JLabel("Username:");
        usernameText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usernameText.setPreferredSize(new Dimension(80, 25));

        usernameField = new JTextField(15);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JButton inv = new JButton();
        inv.setPreferredSize(new Dimension(30, 25));
        inv.setFocusPainted(false);
        inv.setContentAreaFilled(false);
        inv.setBorderPainted(false);

        Box usernameBox = Box.createHorizontalBox();
        usernameBox.add(usernameText);
        usernameBox.add(Box.createHorizontalStrut(10));
        usernameBox.add(usernameField);
        usernameBox.add(Box.createHorizontalStrut(5));
        usernameBox.add(inv);
        usernameBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(usernameBox);

        //password
        JLabel passwordText = new JLabel("Password:");
        passwordText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordText.setPreferredSize(new Dimension(80, 25));

        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        JButton showPassBtn = new JButton(loadIcon("/icons/show.png", 20, 20));
        showPassBtn.setPreferredSize(new Dimension(30, 25));
        showPassBtn.setFocusPainted(false);
        showPassBtn.setContentAreaFilled(false);
        showPassBtn.setBorderPainted(false);
        showPassBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        showPassBtn.addActionListener(e -> {
            if (passwordField.getEchoChar() == '\u0000') {
                passwordField.setEchoChar('●');
                showPassBtn.setIcon(loadIcon("/icons/show.png", 20, 20));
            } else {
                passwordField.setEchoChar('\u0000');
                showPassBtn.setIcon(loadIcon("/icons/hide.png", 20, 20));
            }
        });

        Box passwordBox = Box.createHorizontalBox();
        passwordBox.add(passwordText);
        passwordBox.add(Box.createHorizontalStrut(10));
        passwordBox.add(passwordField);
        passwordBox.add(Box.createHorizontalStrut(5));
        passwordBox.add(showPassBtn);
        passwordBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(passwordBox);
        rightPanel.add(Box.createVerticalStrut(15));

        // login button
        loginButton = new JButton("LOGIN");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(loginButton);

        // for display error or success
        messageLabel = new JLabel(" ");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(Color.RED);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(messageLabel);

        //forget password
        JLabel forgotLabel = new JLabel("<HTML><U>Forgot Password?</U></HTML>");
        forgotLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        forgotLabel.setForeground(new Color(30, 100, 200));
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(Box.createVerticalStrut(6));
        rightPanel.add(forgotLabel);

        //simple popup
        forgotLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JOptionPane.showMessageDialog(LoginPage.this,
                        """
                                Please contact the administrator to reset your password
                                Email: admin@tuitioncentre.com
                                """,
                        "Password Reset", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // add to main
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> attemptLogin());
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Username or password cannot be empty.");
            shakeWindow();
            return;
        }

        if (ValidUser.isLockedOut()) {
            loginButton.setEnabled(false);
            Timer t = new Timer(1000, null);
            t.addActionListener(evt -> {
                if (ValidUser.isLockedOut()) {
                    messageLabel.setText("Locked. Try again in " + ValidUser.getRemainingLockTime() + "s");
                } else {
                    messageLabel.setText("Please retry.");
                    loginButton.setEnabled(true);
                    t.stop();
                }
            });
            t.start();
            return;
        }

        try {
            User user = ValidUser.validation(username, password);

            if (user == null) {
                shakeWindow();
                int remaining = ValidUser.getRemainingAttempts();
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Invalid username, password, or role. " + remaining + " chance(s) left.");
                return;
            }

            messageLabel.setForeground(new Color(0, 128, 0));
            messageLabel.setText("Login successful, Redirecting as " + user.getRole() + "...");
            loginButton.setEnabled(false);

            Timer successTimer = new Timer(1000, evt2 -> {
                try {
                    switch (user) {
                        case Admin admin -> {
                            new AdminView(admin).setVisible(true);
                        }
                        case Receptionist receptionist -> {
                            new ReceptionistDashboard(receptionist).setVisible(true);
                        }
                        case Tutor tutor -> {
                            ValidUser.setCurrentUser(tutor.getUsername());
                            new TutorView().setVisible(true);
                        }
                        case Student student -> {
                            ValidUser.setCurrentUser(student.getUsername());
                            new StudentView().setVisible(true);
                        }
                        default -> {
                            JOptionPane.showMessageDialog(null, "Unrecognized account type.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to open dashboard.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            successTimer.setRepeats(false);
            successTimer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("An error occurred during login.");
        }
    }

    // Shaking effect for failed login
    private void shakeWindow() {
        final int originalX = getLocationOnScreen().x;
        final int originalY = getLocationOnScreen().y;

        Timer timer = new Timer(20, null);
        final int[] count = {0};
        timer.addActionListener(e -> {
            int offset = (count[0] % 2 == 0) ? 10 : -10;
            setLocation(originalX + offset, originalY);
            if (++count[0] >= 6) {
                timer.stop();
                setLocation(originalX, originalY);
            }
        });
        timer.start();
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }


}
