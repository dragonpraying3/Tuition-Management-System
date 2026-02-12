package view.Receptionist;

import controller.Profile;
import controller.Registration;
import controller.ValidUser;
import model.Receptionist;
import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static controller.Registration.AccountFile;


public class ProfilePanel extends JPanel implements ActionListener {
    private final User Receptionist;
    ReceptionistDashboard home;
    JTextField nameField, roleField, fullNameField, dobField, ageField,
            genderField, contactField, emailField, raceField, icField,
            salaryField, workingShiftField, passwordField;
    JTextArea addressArea;
    JLabel iconLabel;
    JComboBox<String> workStatusBox;
    final String[] WORK = {"Full-Time","Part-Time"};
    BottomContainer bottomContainer;
    private static final Color FORM_BG_COLOR = new Color(245, 248, 255);
    private static final Color LABEL_COLOR = new Color(33, 37, 41);
    private static final String AVATAR_DIR = "src/main/resources/avatars/";


    public ProfilePanel(ReceptionistDashboard home, User receptionist) {
        this.home = home;
        this.Receptionist = receptionist;
        this.setBackground(FORM_BG_COLOR);
        this.setLayout(new BorderLayout());

        Receptionist current = null;
        for (Receptionist r : Registration.getAllReceptionist()) {
            if (r.getUsername().equalsIgnoreCase(Receptionist.getUsername())) {
                current = r;
                break;
            }
        }

        if (current == null) {
            this.add(new JLabel("Receptionist not found."));
            return;
        }

        //create ui
        nameField = new JTextField(current.getUsername());
        nameField.setEditable(false);
        roleField = new JTextField("Receptionist");
        roleField.setEditable(false);
        fullNameField = new JTextField(current.getFullName());
        passwordField = new JTextField(current.getPassword());
        dobField = new JTextField(current.getDateOfBirth());
        dobField.setEditable(false);
        ageField = new JTextField(current.getAge());
        ageField.setEditable(false);
        genderField = new JTextField(current.getGender());
        genderField.setEditable(false);
        contactField = new JTextField(current.getContactNumber());
        emailField = new JTextField(current.getEmail());
        raceField = new JTextField(current.getRace());
        raceField.setEditable(false);
        icField = new JTextField(current.getIC());
        icField.setEditable(false);
        salaryField = new JTextField("RM " + current.getSalary());
        salaryField.setEditable(false);
        workingShiftField = new JTextField(current.getWorkingShift());
        workingShiftField.setEditable(false);
        workStatusBox = new JComboBox<>(WORK);
        workStatusBox.setSelectedItem(current.getWorkStatus());

        styleTextFields(fullNameField, passwordField, contactField, emailField);
        setComboBoxBg(workStatusBox);

        // === Top Panel containing icon (left) and title (right) ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Icon on the left
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.Y_AXIS));
        iconPanel.setPreferredSize(new Dimension(130, 140));
        File avatarFile = new File(AVATAR_DIR + Receptionist.getUsername() + ".png");
        ImageIcon avatarIcon;
        if (avatarFile.exists()) {
            avatarIcon = new ImageIcon(avatarFile.getAbsolutePath());
        } else {
            avatarIcon = new ImageIcon("src/main/resources/icons/user.png");
        }

        Image scaledImage = avatarIcon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        ImageIcon smallIcon = new ImageIcon(scaledImage);
        iconLabel = new JLabel(smallIcon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setToolTipText("Click to upload a new avatar");
        iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                uploadAvatar();
            }
        });

        //remove image
        JButton removeButton = new JButton("Remove");
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.setFocusPainted(false);
        removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        removeButton.addActionListener(e -> {
            File avatar = new File(AVATAR_DIR, Receptionist.getUsername() + ".png");
            if (avatar.exists() && avatar.delete()) {
                loadAvatar();
                JOptionPane.showMessageDialog(this, "Avatar removed.");
            } else {
                JOptionPane.showMessageDialog(this, "No custom avatar to remove.");
            }
        });

        iconPanel.add(iconLabel);
        iconPanel.add(Box.createVerticalStrut(4));
        iconPanel.add(removeButton);
        topPanel.add(iconPanel, BorderLayout.WEST);

        //title
        JPanel titleRightPanel = new JPanel();
        titleRightPanel.setLayout(new BoxLayout(titleRightPanel, BoxLayout.Y_AXIS));
        titleRightPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Receptionist Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Username
        JLabel usernameLabel = new JLabel("Username: " + Receptionist.getUsername());
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleRightPanel.add(Box.createVerticalStrut(5));


        // Role
        JLabel roleLabel = new JLabel("Role: Receptionist");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


        titleRightPanel.add(titleLabel, BorderLayout.CENTER);
        titleRightPanel.add(usernameLabel);
        titleRightPanel.add(roleLabel);

        titleRightPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Control spacing
        topPanel.add(titleRightPanel, BorderLayout.CENTER);

        // Form grid
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        //add into form panel
        addLabelAndComponent(formPanel, gbc, row++, "Full Name:", fullNameField);
        addLabelAndComponent(formPanel, gbc, row++, "Password:", passwordField);
        addLabelAndComponent(formPanel, gbc, row++, "Date of Birth:", dobField);
        addLabelAndComponent(formPanel, gbc, row++, "Age:", ageField);
        addLabelAndComponent(formPanel, gbc, row++, "Gender:", genderField);
        addLabelAndComponent(formPanel, gbc, row++, "Contact Number:", contactField);
        addLabelAndComponent(formPanel, gbc, row++, "Email:", emailField);
        addLabelAndComponent(formPanel, gbc, row++, "Race:", raceField);
        addLabelAndComponent(formPanel, gbc, row++, "IC:", icField);
        addLabelAndComponent(formPanel, gbc, row++, "Salary:", salaryField);
        addLabelAndComponent(formPanel, gbc, row++, "Working Shift:", workingShiftField);
        addLabelAndComponent(formPanel, gbc, row++, "Work Status:", workStatusBox);

        addressArea = new JTextArea(current.getAddress(), 3, 20);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        JScrollPane addScroll = new JScrollPane(addressArea);
        addScroll.setPreferredSize(new Dimension(250, 60));
        addLabelAndComponent(formPanel, gbc, row++, "Address:", addScroll);

        // Add scroll to formPanel
        JScrollPane centerScroll = new JScrollPane(formPanel);
        centerScroll.setBorder(null);

        //bottom
        bottomContainer = new BottomContainer(
                "Home", new Color(58, 91, 204), this,
                "Reset", new Color(158, 158, 158), this,
                "Save", new Color(76, 175, 80), this
        );

        //detect those field getting change
        changeListener(fullNameField);
        changeListener(emailField);
        changeListener(passwordField);
        changeListener(contactField);
        changeListener(addressArea);
        changeListener(workStatusBox);

        bottomContainer.rightButton1.setEnabled(false);
        bottomContainer.rightButton2.setEnabled(false);

        topPanel.setBackground(FORM_BG_COLOR);
        iconPanel.setBackground(FORM_BG_COLOR);
        titleRightPanel.setBackground(FORM_BG_COLOR);
        formPanel.setBackground(FORM_BG_COLOR);
        centerScroll.getViewport().setBackground(FORM_BG_COLOR);

        //add to main panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerScroll, BorderLayout.CENTER);
        this.add(bottomContainer, BorderLayout.SOUTH);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bottomContainer.leftButton) {
            home.showCard("home");
        } else if (e.getSource() == bottomContainer.rightButton1) {
            displayInfo(Receptionist.getUsername());
            bottomContainer.rightButton1.setEnabled(false);
            bottomContainer.rightButton2.setEnabled(false);
        } else if (e.getSource() == bottomContainer.rightButton2) {
            String name = fullNameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();
            String address = addressArea.getText();
            String work = (String) workStatusBox.getSelectedItem();

            String errorMessage = validateField(name, password, contact, email, address);
            if (!errorMessage.isEmpty()) {
                JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Errors", JOptionPane.ERROR_MESSAGE);
            } else {
                String result = Profile.updateReceptionistProfile(Receptionist.getUsername(), name, password, contact, email, address, work);
                if (result.equals("Success")) {
                    JOptionPane.showMessageDialog(this, "Receptionist Profile Update Successfully");
                    displayInfo(Receptionist.getUsername());
                    bottomContainer.rightButton1.setEnabled(false);
                    bottomContainer.rightButton2.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, result, "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void addLabelAndComponent(JPanel panel, GridBagConstraints gbc, int row, String label, Component comp) {
        gbc.gridx = 0;
        gbc.gridy = row;

        JLabel lbl = new JLabel(label);
        lbl.setForeground(LABEL_COLOR);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(lbl, gbc);

        gbc.gridx = 1;
        panel.add(comp, gbc);
    }

    private void displayInfo(String username) {
        List<Receptionist> receptionistData = Registration.getAllReceptionist();
        List<String> accountData = Registration.readFile(AccountFile);

        for (Receptionist r : receptionistData) {
            if (r.getUsername().equals(username)) {
                fullNameField.setText(r.getFullName());
                dobField.setText(r.getDateOfBirth());
                ageField.setText(r.getAge());
                genderField.setText(r.getGender());
                contactField.setText(r.getContactNumber());
                emailField.setText(r.getEmail());
                raceField.setText(r.getRace());
                icField.setText(r.getIC());
                addressArea.setText(r.getAddress());
                salaryField.setText("RM " + r.getSalary());
                workStatusBox.setSelectedItem(r.getWorkStatus());
            }
            for (String row : accountData) {
                String[] field = row.split(";");
                if (field.length >= 3 && field[0].equalsIgnoreCase(username)) {
                    passwordField.setText(field[1]);
                    break;
                }
            }
        }
    }

    private String validateField(String fullName, String password,
                                 String contact, String email, String address) {
        StringBuilder errorMessage = new StringBuilder();

        if (ValidUser.isEmpty(fullName)) errorMessage.append("- Full Name is required.\n");
        if (ValidUser.isEmpty(password)) errorMessage.append("- Password is required.\n");
        if (ValidUser.isEmpty(contact)) errorMessage.append("- Contact is required.\n");
        if (ValidUser.isEmpty(email)) errorMessage.append("- Email is required.\n");
        if (ValidUser.isEmpty(address)) errorMessage.append("- Address is required.\n");

        if (!ValidUser.isValidName(fullName)) errorMessage.append("- Full Name format is invalid.\n");
        if (!ValidUser.isValidEmail(email)) errorMessage.append("- Email format is invalid.\n");
        if (!ValidUser.isValidContact(contact)) errorMessage.append("- Contact number must be 9-11 digits.\n");

        return errorMessage.toString();
    }

    /**
     * Creates a circle image
     *
     * @param source The original BufferedImage to be transformed.
     * @param size   The width and height of the output image in pixels (creates a square) (110x110)
     * @return A new BufferedImage with the input image clipped into a circle.
     */
    private BufferedImage makeRoundedImage(BufferedImage source, int size) {
        // Scale the source image to the desired size (width and height)
        Image scaled = source.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        // Create an output image with transparency (ARGB)
        BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        // Get the graphics context for drawing
        Graphics2D g2 = output.createGraphics();
        // Enable antialiasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Define a circular clipping area (a perfect circle)
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size);
        // Apply the circular clipping region to the graphics context
        g2.setClip(circle);
        // Draw the scaled image within the circular clipping area
        g2.drawImage(scaled, 0, 0, null);
        // Clean up graphics resources
        g2.dispose();
        // Return the final circular image
        return output;
    }

    //this method is use for checking the TextField have get any changed or not
    private void changeListener(JTextField field) {
        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                enabledItem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enabledItem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enabledItem();
            }
        });
    }

    private void changeListener(JTextArea field) {
        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                enabledItem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enabledItem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enabledItem();
            }
        });
    }

    private void changeListener(JComboBox<String> field) {
        field.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                enabledItem();
            }
        });

    }

    private void enabledItem() {
        bottomContainer.rightButton1.setEnabled(true);
        bottomContainer.rightButton2.setEnabled(true);
    }

    private void loadAvatar() {
        File avatarFile = new File(AVATAR_DIR, Receptionist.getUsername() + ".png");

        ImageIcon avatarIcon;
        if (avatarFile.exists()) {
            avatarIcon = new ImageIcon(avatarFile.getAbsolutePath());
        } else {
            avatarIcon = new ImageIcon(getClass().getResource("/icons/user.png"));
        }
        Image scaled = avatarIcon.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaled));
    }

    private void styleTextFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setBackground(Color.WHITE);
            field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        }
    }

    private void setComboBoxBg(JComboBox<?>... boxes) {
        for (JComboBox<?> box : boxes) {
            box.setBackground(Color.WHITE);
            box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    private void uploadAvatar() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose an Avatar");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File src = chooser.getSelectedFile();

                File dest = new File(AVATAR_DIR, Receptionist.getUsername() + ".png");

                // Read and process image as circular
                BufferedImage original;
                try {
                    original = ImageIO.read(src);
                    if (original == null) {
                        JOptionPane.showMessageDialog(this, "Invalid image file. Please select a valid JPG/PNG image.");
                        return;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to read the selected image.");
                    return;
                }
                BufferedImage circleAvatar = makeRoundedImage(original, 110);

                // Save processed avatar
                ImageIO.write(circleAvatar, "png", dest);

                // Reload icon into the label
                loadAvatar();
                JOptionPane.showMessageDialog(this, "Avatar updated!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to save avatar.");
            }
        }
    }
}
