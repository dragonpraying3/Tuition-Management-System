package view.Receptionist;

import controller.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterStudentPanel extends JPanel implements ActionListener {

    JTextField userNameField, passwordField, nameField, icField, emailField, contactField, addressField, ageField;
    JCheckBox raceBoxChinese, raceBoxMalay, raceBoxIndian, genderBoxMale, genderBoxFemale;
    JComboBox<String> dateBox, MonthBox, yearBox, subjectBox1, subjectBox2, subjectBox3, levelBox, monthBox;
    final String[] MONTHS = {" ", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    final String[] LEVELS = {" ", "Form 1", "Form 2", "Form 3", "Form 4", "Form 5"};
    final String[] DATES = {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    final String[] YEARS = {" ", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006",
            "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"};
    final String[] SUBJECT = {" ", "Bahasa Melayu", "English","Chinese Language", "Mathematics", "Additional Mathematics", "Science",
            "Physics", "Chemistry", "Biology", "History", "Geography", "Moral Education", "Pendidikan Islam",
            "Economics", "Business Studies", "Accounting"};
    JLabel titleLabel;
    ReceptionistDashboard home;
    BottomContainer bottomContainer;
    private static final Color FORM_BG_COLOR = new Color(245, 248, 255);

    public RegisterStudentPanel(ReceptionistDashboard home) throws IOException {
        //put the admin homePanel inside
        //use 'this' keyword to fix conflict
        this.home = home;
        setBackground(FORM_BG_COLOR);

        //title label
        titleLabel = new JLabel("<html><u>STUDENT REGISTER FORM</u></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        titleLabel.setForeground(new Color(0, 102, 204));

        //form panel

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(FORM_BG_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        setLayout(new BorderLayout());
        nameField = new JTextField();
        icField = new JTextField();
        emailField = new JTextField();
        contactField = new JTextField();
        addressField = new JTextField();
        userNameField = new JTextField();
        ageField = new JTextField();
        passwordField = new JTextField(new String(Registration.PasswordGenerator()));
        genderBoxMale = new JCheckBox("Male");
        genderBoxFemale = new JCheckBox("Female");
        raceBoxChinese = new JCheckBox("Chinese");
        raceBoxMalay = new JCheckBox("Malay");
        raceBoxIndian = new JCheckBox("Indian");
        dateBox = new JComboBox<>(DATES);
        MonthBox = new JComboBox<>(MONTHS);
        yearBox = new JComboBox<>(YEARS);
        levelBox = new JComboBox<>(LEVELS);
        monthBox = new JComboBox<>(MONTHS);
        subjectBox1 = new JComboBox<>(SUBJECT);
        subjectBox2 = new JComboBox<>(SUBJECT);
        subjectBox3 = new JComboBox<>(SUBJECT);
        setComboBoxBg(dateBox, MonthBox, yearBox, levelBox, monthBox, subjectBox1, subjectBox2, subjectBox3);
        setCheckBoxBg(genderBoxMale, genderBoxFemale, raceBoxChinese, raceBoxMalay, raceBoxIndian);
        styleTextFields(userNameField, passwordField, nameField, icField, emailField, contactField, addressField, ageField);

        passwordField.setEditable(false);

        //bottom
        bottomContainer = new BottomContainer(
                "Home", new Color(58, 91, 204), this,
                "Reset", new Color(211, 47, 47), this,
                "Enroll", new Color(102, 187, 106), this
        );

        int row = 0;
        addLabelAndComponent(formPanel, gbc, row++, "Username:", userNameField);
        addLabelAndComponent(formPanel, gbc, row++, "Password:", passwordField);
        addLabelAndComponent(formPanel, gbc, row++, "Full Name:", nameField);
        addLabelAndComponent(formPanel, gbc, row++, "IC/Passport:", icField);
        addLabelAndComponent(formPanel, gbc, row++, "Age:", ageField);

        JPanel DoBPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        DoBPanel.add(dateBox);
        DoBPanel.add(MonthBox);
        DoBPanel.add(yearBox);
        DoBPanel.setBackground(FORM_BG_COLOR);
        addLabelAndComponent(formPanel, gbc, row++, "Date Of Birth:", DoBPanel);

        addLabelAndComponent(formPanel, gbc, row++, "Email:", emailField);
        addLabelAndComponent(formPanel, gbc, row++, "Contact:", contactField);
        addLabelAndComponent(formPanel, gbc, row++, "Address:", addressField);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.add(genderBoxMale);
        genderPanel.add(genderBoxFemale);
        genderPanel.setBackground(FORM_BG_COLOR);
        addLabelAndComponent(formPanel, gbc, row++, "Gender:", genderPanel);

        JPanel racePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        racePanel.add(raceBoxChinese);
        racePanel.add(raceBoxMalay);
        racePanel.add(raceBoxIndian);
        racePanel.setBackground(FORM_BG_COLOR);
        addLabelAndComponent(formPanel, gbc, row++, "Race:", racePanel);


        addLabelAndComponent(formPanel, gbc, row++, "Level:", levelBox);
        addLabelAndComponent(formPanel, gbc, row++, "Month of Enrolment:", monthBox);
        addLabelAndComponent(formPanel, gbc, row++, "Select Subject 1:", subjectBox1);
        addLabelAndComponent(formPanel, gbc, row++, "Select Subject 2:", subjectBox2);
        addLabelAndComponent(formPanel, gbc, row++, "Select Subject 3:", subjectBox3);

        //too many component , adding scroll pane
        JScrollPane scrollPane = new JScrollPane(formPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 248, 255));
        scrollPane.setBackground(new Color(245, 248, 255));

        // add all panel to main
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);
    }

    private void addLabelAndComponent(JPanel panel, GridBagConstraints gbc, int row, String label, Component comp) {
        gbc.gridx = 0;
        gbc.gridy = row;

        JLabel labelComponent = new JLabel(label);
        labelComponent.setForeground(new Color(70, 70, 70)); // Unified label color
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Optional: consistent font
        panel.add(labelComponent, gbc);

        gbc.gridx = 1;
        panel.add(comp, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bottomContainer.leftButton) {
            home.showCard("home");
        } else if (e.getSource() == bottomContainer.rightButton1) {
            resetRegister();
        } else if (e.getSource() == bottomContainer.rightButton2) {
            String userName = userNameField.getText().trim();
            String passWord = passwordField.getText().trim();
            String name = nameField.getText().trim();
            String ic = icField.getText().trim();
            String age = ageField.getText().trim();
            String email = emailField.getText().trim();
            String contact = contactField.getText().trim();
            String address = addressField.getText().trim();
            String gender = getSelectedGender().trim();
            String race = getSelectedRace().trim();


            // if a > b ? a : b
            // first statement is true execute first else second
            String day = (dateBox.getSelectedIndex() == 0) ? null : (String) dateBox.getSelectedItem();
            String Month = (MonthBox.getSelectedIndex() == 0) ? null : (String) MonthBox.getSelectedItem();
            String year = (yearBox.getSelectedIndex() == 0) ? null : (String) yearBox.getSelectedItem();
            String level = (levelBox.getSelectedIndex() == 0) ? null : (String) levelBox.getSelectedItem();
            String month = (monthBox.getSelectedIndex() == 0) ? null : (String) monthBox.getSelectedItem();
            String sub1 = (subjectBox1.getSelectedIndex() == 0) ? null : (String) subjectBox1.getSelectedItem();
            String sub2 = (subjectBox2.getSelectedIndex() == 0) ? null : (String) subjectBox2.getSelectedItem();
            String sub3 = (subjectBox3.getSelectedIndex() == 0) ? null : (String) subjectBox3.getSelectedItem();
            String[] sub = {sub1, sub2, sub3};

            // assign random tutor
            String subject1Tutor = TutorController.assignTutor(sub1, level);
            String subject2Tutor = TutorController.assignTutor(sub2, level);
            String subject3Tutor = TutorController.assignTutor(sub3, level);

            String dob = (day != null && Month != null && year != null) ? day + "-" + Month + "-" + year : null;

            boolean validUsername = ValidUser.isEmpty(userName);
            boolean validName = ValidUser.isValidName(name);
            boolean validIc = ValidUser.isValidIcOrPassport(ic);
            boolean validAge = ValidUser.isValidAge(age);
            boolean validEmail = ValidUser.isValidEmail(email);
            boolean validContact = ValidUser.isValidContact(contact);
            boolean validAddress = ValidUser.isEmpty(address);
            boolean validRace = ValidUser.isEmpty(race);
            boolean validLevel = ValidUser.isEmpty(level);
            boolean validDoB = ValidUser.isEmpty(dob);
            boolean validSub1 = ValidUser.isEmpty(sub1);
            boolean validSub2 = ValidUser.isEmpty(sub2);
            boolean validSub3 = ValidUser.isEmpty(sub3);
            boolean subContain = isContain(sub);


            // write into file
            if (!validUsername && validName && validIc && validAge && validEmail && validContact &&
                    !validAddress && !validRace && !validLevel && !validDoB && !subContain) {
                Student stuInfo = new Student(userName, passWord, name, ic, age, dob, email, contact, address, gender,
                        race, level, "Active");
                String result = Registration.RegisterStudent(stuInfo);

                if (result.equals("Success")) {
                    JOptionPane.showMessageDialog(this,
                            "Student " + name + " has been successfully enrolled!",
                            "Enrollment Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                    //write into enrolment.txt
                    Enrolment stuEnrolment = new Enrolment(userName, month, sub1, subject1Tutor, sub2, subject2Tutor, sub3, subject3Tutor);

                    boolean save = Registration.saveEnrolment(stuEnrolment);
                    if (!save) {
                        JOptionPane.showMessageDialog(this,
                                "Failed to save enrolment data.",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    ((ReceptionistDashboard) SwingUtilities.getWindowAncestor(this)).refreshAllPanels();
                } else {
                    JOptionPane.showMessageDialog(this,
                            result,
                            "Registration Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                resetRegister();
                return;
            }

            StringBuilder errorBuilder = new StringBuilder();
            int raceCount = 0;
            int emptySubjectCount = 0;
            if (validUsername) errorBuilder.append("- UserName cannot be empty\n");
            if (!validName) errorBuilder.append("- Name is invalid\n");
            if (!validIc) errorBuilder.append("- IC or Passport is invalid\n");
            if (!validAge) errorBuilder.append("- Age must be number / cannot be empty\n");
            if (!validEmail) errorBuilder.append("- Email format is invalid\n");
            if (!validContact) errorBuilder.append("- Contact number is invalid\n");
            if (validAddress) errorBuilder.append("- Address cannot be empty\n");
            if (validRace) errorBuilder.append("- Race cannot be empty\n");
            if (validLevel) errorBuilder.append("- Level cannot be empty\n");
            if (validDoB) errorBuilder.append("- Date of Birth cannot be empty\n");
            if (genderBoxMale.isSelected() && genderBoxFemale.isSelected())
                errorBuilder.append("- Please select only one gender.\n");
            if (raceBoxChinese.isSelected()) raceCount++;
            if (raceBoxMalay.isSelected()) raceCount++;
            if (raceBoxIndian.isSelected()) raceCount++;
            if (raceCount > 1) errorBuilder.append("- Please select only one race.\n");
            if (validSub1) emptySubjectCount++;
            if (validSub2) emptySubjectCount++;
            if (validSub3) emptySubjectCount++;
            if (emptySubjectCount >= 3) errorBuilder.append("- Please enrol at least 1 subject.\n");
            if (subContain) errorBuilder.append("- Student cannot have same subject.\n");


            JOptionPane.showMessageDialog(
                    this,
                    errorBuilder.toString(),
                    "Form Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void resetRegister() {
        userNameField.setText("");
        passwordField.setText(new String(Registration.PasswordGenerator()));
        nameField.setText("");
        icField.setText("");
        ageField.setText("");
        dateBox.setSelectedIndex(0);
        MonthBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
        emailField.setText("");
        contactField.setText("");
        addressField.setText("");
        levelBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        subjectBox1.setSelectedIndex(0);
        subjectBox2.setSelectedIndex(0);
        subjectBox3.setSelectedIndex(0);
        raceBoxChinese.setSelected(false);
        raceBoxMalay.setSelected(false);
        raceBoxIndian.setSelected(false);
        genderBoxMale.setSelected(false);
        genderBoxFemale.setSelected(false);
        subjectBox1.setSelectedIndex(0);
        subjectBox2.setSelectedIndex(0);
        subjectBox3.setSelectedIndex(0);
    }

    private String getSelectedGender() {
        if (genderBoxMale.isSelected()) return genderBoxMale.getText();
        if (genderBoxFemale.isSelected()) return genderBoxFemale.getText();
        return "";
    }

    private String getSelectedRace() {
        if (raceBoxChinese.isSelected()) return raceBoxChinese.getText();
        if (raceBoxMalay.isSelected()) return raceBoxMalay.getText();
        if (raceBoxIndian.isSelected()) return raceBoxIndian.getText();
        return "";
    }

    private boolean isContain(String[] chooseSubject) {
        List<String> subjectList = new ArrayList<>();
        for (String s : chooseSubject) {
            if (s == null) continue;
            if (subjectList.contains(s)) {
                return true;
            }
            subjectList.add(s);
        }
        return false;
    }

    /**
     * Sets the background color of multiple JComboBox components
     * to match the form's background color for consistent styling.
     *
     * @param boxes boxes One or more JComboBox components to style
     */
    private void setComboBoxBg(JComboBox<?>... boxes) {
        for (JComboBox<?> box : boxes) {
            box.setBackground(FORM_BG_COLOR);
        }
    }

    //same with setComboBoxBg
    private void setCheckBoxBg(JCheckBox... boxes) {
        for (JCheckBox box : boxes) {
            box.setBackground(FORM_BG_COLOR);
        }
    }

    private void styleTextFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setBackground(Color.WHITE);
            field.setForeground(new Color(40, 40, 40));
            field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
        }
    }
}
