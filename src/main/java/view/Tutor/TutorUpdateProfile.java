package view.Tutor;

import controller.Profile;
import controller.Registration;
import controller.ValidUser;
import model.Tutor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TutorUpdateProfile extends JPanel {

    public TutorUpdateProfile() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        String currentUsername = ValidUser.getCurrentUser().trim();

        List<Tutor> allTutors = Registration.getAllTutor();
        Tutor currentTutor = allTutors.stream()
                .filter(t -> t.getUsername().equalsIgnoreCase(currentUsername))
                .findFirst()
                .orElse(null);

        if (currentTutor == null) {
            add(new JLabel("Error: Profile data not found."), BorderLayout.CENTER);
            return;
        }

        // Store original values to restore on Reset
        final String originalName = currentTutor.getFullName();
        final String originalPassword = currentTutor.getPassword();
        final String originalPhone = currentTutor.getContactNumber();
        final String originalEmail = currentTutor.getEmail();
        final String originalDob = currentTutor.getDateOfBirth();
        final String originalAge = currentTutor.getAge();
        final String originalGender = currentTutor.getGender();
        final String originalRace = currentTutor.getRace();
        final String originalIC = currentTutor.getIC();
        final String originalQualification = currentTutor.getQualification();
        final String originalAddress = currentTutor.getAddress();

        JLabel title = new JLabel("Update Profile", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JTextField nameField = new JTextField(originalName);
        JTextField passwordField = new JTextField(originalPassword);
        JTextField phoneField = new JTextField(originalPhone);
        JTextField emailField = new JTextField(originalEmail);

        JTextField dobField = new JTextField(originalDob);
        dobField.setEditable(false);

        JTextField ageField = new JTextField(originalAge);
        ageField.setEditable(false);

        JTextField genderField = new JTextField(originalGender);
        genderField.setEditable(false);

        JTextField raceField = new JTextField(originalRace);
        raceField.setEditable(false);

        JTextField icField = new JTextField(originalIC);
        icField.setEditable(false);

        JTextField qualificationField = new JTextField(originalQualification);
        qualificationField.setEditable(false);

        JTextField addressField = new JTextField(originalAddress);

        // Tutor Teach concatenated from subject1, subject2, subject3
        JLabel teachLabel = new JLabel("Tutor Teach:");
        String tutorTeach = String.join("; ",
                currentTutor.getSubject1(),
                currentTutor.getSubject2(),
                currentTutor.getSubject3());
        JTextField teachField = new JTextField(tutorTeach);
        teachField.setEditable(false);

        JButton updateButton = new JButton("Update");
        JButton resetButton = new JButton("Reset");
        JButton backButton = new JButton("Go Back");

        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            TutorView tutorView = (TutorView) SwingUtilities.getWindowAncestor(this);
            tutorView.showHomePanel();
        });

        JPanel formPanel = new JPanel(new GridLayout(14, 2, 10, 10));

        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Date Of Birth:"));
        formPanel.add(dobField);


        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);

        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderField);

        formPanel.add(new JLabel("Race:"));
        formPanel.add(raceField);

        formPanel.add(new JLabel("IC Number:"));
        formPanel.add(icField);

        formPanel.add(new JLabel("Academic Qualification:"));
        formPanel.add(qualificationField);

        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);

        formPanel.add(teachLabel);
        formPanel.add(teachField);

        // Panel to hold Reset and Update buttons side by side
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.add(resetButton);
        buttonPanel.add(updateButton);

        formPanel.add(new JLabel()); // spacer for alignment
        formPanel.add(buttonPanel);

        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Reset button functionality: revert fields to original values
        resetButton.addActionListener(e -> {
            nameField.setText(originalName);
            passwordField.setText(originalPassword);
            phoneField.setText(originalPhone);
            emailField.setText(originalEmail);
            dobField.setText(originalDob);
            ageField.setText(originalAge);
            genderField.setText(originalGender);
            raceField.setText(originalRace);
            icField.setText(originalIC);
            qualificationField.setText(originalQualification);
            addressField.setText(originalAddress);
        });

        // Update button with validation for email and contact number
        updateButton.addActionListener(e -> {
            String emailInput = emailField.getText().trim();
            String contactInput = phoneField.getText().trim();
            String passwordInput = passwordField.getText().trim();

            if (!ValidUser.isValidEmail(emailInput)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.",
                        "Invalid Email", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!ValidUser.isValidContact(contactInput)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid contact number (9 to 11 digits).",
                        "Invalid Contact", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!ValidUser.isValidPassword(passwordInput)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid password (4+ digits).",
                        "Invalid IC/Passport", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to update your profile?",
                    "Confirm Update",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                String result = Profile.updateTutorProfile(
                        currentUsername,
                        nameField.getText().trim(),
                        passwordField.getText().trim(),
                        contactInput,
                        emailInput,
                        dobField.getText().trim(),
                        ageField.getText().trim(),
                        genderField.getText().trim(),
                        raceField.getText().trim(),
                        icField.getText().trim(),
                        qualificationField.getText().trim(),
                        addressField.getText().trim()
                );

                if ("Success".equals(result)) {
                    JOptionPane.showMessageDialog(this, "Profile updated successfully.");
                    TutorView tutorView = (TutorView) SwingUtilities.getWindowAncestor(this);
                    tutorView.showHomePanel();
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed: " + result);
                }
            }
        });
    }
}

