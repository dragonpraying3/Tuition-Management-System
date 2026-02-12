package view.Student;

import controller.Profile;
import controller.ValidUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StudentProfilePanel extends JPanel implements ActionListener {

    private JTextField usernameField, fullNameField, dobField, contactField, emailField, nricField, genderField, raceField, ageField;
    private JTextArea addressArea;
    private JPasswordField passwordField;
    private JButton saveButton, resetButton;
    private String Username;
    private String originalUsername;
    private StudentView parent;
    private String originalFullName;
    private String originalContact;
    private String originalEmail;
    private String originalAddress;
    private boolean isPasswordVisible = false;
    private JButton togglePasswordBtn;
    private JTextField levelField;
    private JTextField statusField;
    private JTextArea subjectsArea;
    private String originalPassword;

    private static final Color DARK_BLUE = new Color(36, 64, 138);
    private static final Color LIGHT_BLUE = new Color(173, 204, 255);
    private static final Color BG_COLOR = new Color(240, 245, 255);

    public StudentProfilePanel(StudentView parent, String studentUsername) {
        this.parent = parent;
        this.Username = studentUsername;
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);

        // Top title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(DARK_BLUE);
        JLabel titleLabel = new JLabel("Student Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Center panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Default values (will override below if found)
        String fullName = "", dob = "", contact = "", email = "", address = "", nric = "", gender = "", race = "", age = "";

        levelField = createTextField("", false);
        statusField = createTextField("", false);

        // Create fields
        int row = 0;
        usernameField = createTextField(studentUsername, false);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        togglePasswordBtn = new JButton("👁");
        togglePasswordBtn.setFocusPainted(false);
        togglePasswordBtn.setBorderPainted(false);
        togglePasswordBtn.addActionListener(ev -> {
            isPasswordVisible = !isPasswordVisible;
            passwordField.setEchoChar(isPasswordVisible ? (char) 0 : '•');
            togglePasswordBtn.setText(isPasswordVisible ? "\uD83D\uDCD6" : "\uD83D\uDCD5");
        });
        fullNameField = createTextField(fullName, true);
        ageField = createTextField(age, false);
        dobField = createTextField(dob, false);
        contactField = createTextField(contact, true);
        emailField = createTextField(email, true);
        nricField = createTextField(nric, false);
        genderField = createTextField(gender, false);
        raceField = createTextField(race, false);

        addressArea = new JTextArea(address, 3, 20);
        addressArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addressArea.setWrapStyleWord(true);
        addressArea.setLineWrap(true);
        JScrollPane addressScroll = new JScrollPane(addressArea);

        subjectsArea = new JTextArea(3, 20);
        subjectsArea.setEditable(false);
        subjectsArea.setLineWrap(true);
        subjectsArea.setWrapStyleWord(true);
        subjectsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        String[] data = Profile.getStudentProfile(Username);
        if (data != null) {
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/users.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length >= 2 && parts[0].equals(Username)) {
                        originalPassword = parts[1];
                        passwordField.setText(originalPassword);
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            originalUsername = data[0];
            usernameField.setText(data[0]);

            originalFullName = data[1];
            fullNameField.setText(originalFullName);

            nricField.setText(data[2]);
            dobField.setText(data[4]);
            ageField.setText(data[3]);

            originalContact = data[6];
            contactField.setText(originalContact);

            originalEmail = data[5];
            emailField.setText(originalEmail);

            originalAddress = data[7];
            addressArea.setText(originalAddress);

            genderField.setText(data[8]);
            raceField.setText(data[9]);

            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/student.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts[0].equals(Username)) {
                        levelField.setText(parts[10]);
                        statusField.setText(parts[11]);
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/enrolment.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts[0].equals(Username)) {
                        StringBuilder subjects = new StringBuilder();
                        if (parts.length > 2 && !parts[2].equalsIgnoreCase("null") && !parts[2].isEmpty()) {
                            subjects.append(parts[2]).append("\n");
                        }
                        if (parts.length > 4 && !parts[4].equalsIgnoreCase("null") && !parts[4].isEmpty()) {
                            subjects.append(parts[4]).append("\n");
                        }
                        if (parts.length > 6 && !parts[6].equalsIgnoreCase("null") && !parts[6].isEmpty()) {
                            subjects.append(parts[6]);
                        }
                        subjectsArea.setText(subjects.toString().trim());
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        addField(formPanel, gbc, row++, "Username:", usernameField);
        addField(formPanel, gbc, row++, "Full Name:", fullNameField);
        addField(formPanel, gbc, row++, "Age:", ageField);
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(togglePasswordBtn, BorderLayout.EAST);
        addField(formPanel, gbc, row++, "New Password:", passwordPanel);
        addField(formPanel, gbc, row++, "Gender:", genderField);
        addField(formPanel, gbc, row++, "Race:", raceField);
        addField(formPanel, gbc, row++, "NRIC:", nricField);
        addField(formPanel, gbc, row++, "Date of Birth:", dobField);
        addField(formPanel, gbc, row++, "Contact No:", contactField);
        addField(formPanel, gbc, row++, "Email:", emailField);
        addField(formPanel, gbc, row++, "Address:", addressScroll);
        addField(formPanel, gbc, row++, "Level:", levelField);
        addField(formPanel, gbc, row++, "Status:", statusField);

        JScrollPane subjectScroll = new JScrollPane(subjectsArea);
        addField(formPanel, gbc, row++, "Current Subjects:", subjectScroll);

        // Bottom buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BG_COLOR);
        saveButton = createButton("Save", DARK_BLUE, Color.WHITE);
        resetButton = createButton("Reset", LIGHT_BLUE, Color.BLACK);

        JButton backButton = new JButton("Go Back");
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(200, 200, 200));  // Light gray
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(120, 25));
        backButton.addActionListener(e -> {
            parent.returnToMainMenu();
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(backButton);

        // Add all panels
        add(titlePanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); //
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTextField createTextField(String text, boolean editable) {
        JTextField field = new JTextField(text);
        field.setEditable(editable);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        return field;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, Component input) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(input, gbc);
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String newFullName = fullNameField.getText().trim();
            String newPassword = new String(passwordField.getPassword()).trim();
            String newContact = contactField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newAddress = addressArea.getText().trim();

            if (!newFullName.isEmpty() && !ValidUser.isValidName(newFullName)) {
                JOptionPane.showMessageDialog(this, "Please enter alphabets only.");
                return;
            }

            if (!newPassword.isEmpty() && !ValidUser.isValidPassword(newPassword)) {
                JOptionPane.showMessageDialog(this, "Password must have at least 4 digits.");
                return;
            }

            if (!newContact.isEmpty() && !ValidUser.isValidContact(newContact)) {
                JOptionPane.showMessageDialog(this, "Invalid contact number format.");
                return;
            }

            if (!newEmail.isEmpty() && !ValidUser.isValidEmail(newEmail)) {
                JOptionPane.showMessageDialog(this, "Invalid email format.");
                return;
            }

            // Call update method
            String result = Profile.updateStudentProfile(
                    originalUsername, newPassword, newFullName, newContact, newEmail, newAddress
            );

            JOptionPane.showMessageDialog(this, result);
        } else if (e.getSource() == resetButton) {
            fullNameField.setText(originalFullName);
            contactField.setText(originalContact);
            emailField.setText(originalEmail);
            addressArea.setText(originalAddress);
            passwordField.setText(originalPassword);
        }
    }
}