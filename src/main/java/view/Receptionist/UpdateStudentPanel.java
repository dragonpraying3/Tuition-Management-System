package view.Receptionist;

import controller.*;
import model.AssignmentModel;
import model.Enrolment;
import model.Student;
import model.Tutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateStudentPanel extends JPanel implements ActionListener {

    JButton searchButton, removeButton1, removeButton2, removeButton3;
    JPanel namePanel, infoPanel;
    JComboBox<String> subjectBox1, subjectBox2, subjectBox3, levelBox, monthBox, nameBox, statusBox, subject1TutorBox,
            subject2TutorBox, subject3TutorBox, updateLevelBox;
    JLabel enrolmentTitle;
    JTextArea studentInfoLabel;
    final String[] level = {"", "Form 1", "Form 2", "Form 3", "Form 4", "Form 5"};
    final String[] updateLevel = {"Form 1", "Form 2", "Form 3", "Form 4", "Form 5"};
    final String[] month = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    final String[] status = {"Active", "Completed"};
    final String[] SUBJECT = {" ", "Bahasa Melayu", "English","Chinese Language", "Mathematics", "Additional Mathematics", "Science",
            "Physics", "Chemistry", "Biology", "History", "Geography", "Moral Education", "Pendidikan Islam",
            "Economics", "Business Studies", "Accounting"};
    ReceptionistDashboard home;
    BottomContainer bottomContainer;
    //color
    private static final Color FORM_BG_COLOR = new Color(245, 248, 255); // same as form/menu
    private static final Color LABEL_COLOR = new Color(70, 70, 70);

    public UpdateStudentPanel(ReceptionistDashboard home) throws IOException {
        //make sure all panel update
        this.home = home;
        this.setBackground(FORM_BG_COLOR);
        List<Student> studentFromFile = Registration.loadStudentFromFile();
        List<String> studentName = new ArrayList<>();
        //set first index empty
        studentName.add(0, "");
        for (Student s : studentFromFile) {
            studentName.add(s.getName());
        }
        setLayout(new BorderLayout());

        nameBox = new JComboBox<>(studentName.toArray(new String[0]));
        searchButton = new JButton("Search");
        subjectBox1 = new JComboBox<>(SUBJECT);
        subjectBox2 = new JComboBox<>(SUBJECT);
        subjectBox3 = new JComboBox<>(SUBJECT);
        subject1TutorBox = new JComboBox<>();
        subject2TutorBox = new JComboBox<>();
        subject3TutorBox = new JComboBox<>();
        removeButton1 = new JButton("Remove");
        removeButton2 = new JButton("Remove");
        removeButton3 = new JButton("Remove");
        monthBox = new JComboBox<>(month);
        levelBox = new JComboBox<>(level);
        statusBox = new JComboBox<>();
        updateLevelBox = new JComboBox<>(updateLevel);
        setComboBoxBg(subjectBox1, subjectBox2, subjectBox3, levelBox, monthBox, nameBox, statusBox, subject1TutorBox, subject2TutorBox, subject3TutorBox,updateLevelBox);

        //design
        nameBox.setEditable(true);
        nameBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameBox.setPreferredSize(new Dimension(200, 30));


        //top container
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        //top container - row 1
        namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.add(new JLabel("Student Name:"));
        namePanel.add(nameBox);
        namePanel.add(searchButton);


        //top container - row 2
        infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.add(new JLabel("Level:"));

        infoPanel.add(levelBox);
        //spacing
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(new JLabel("Enrolment Month:"));
        infoPanel.add(monthBox);

        topContainer.add(namePanel);
        topContainer.add(infoPanel);

        //center container
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 10, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        int row = 0;

        //center container - title
        enrolmentTitle = new JLabel("Update Enrolment Subjects & Status", SwingConstants.CENTER);
        enrolmentTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        enrolmentTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 3;
        formPanel.add(enrolmentTitle, gbc);
        gbc.gridwidth = 1;


        //center container - subject panel/update status/subject tutor
        subjectBox1.setEnabled(false);
        subjectBox2.setEnabled(false);
        subjectBox3.setEnabled(false);
        statusBox.setEnabled(false);
        removeButton1.setEnabled(false);
        removeButton2.setEnabled(false);
        removeButton3.setEnabled(false);
        subject1TutorBox.setEnabled(false);
        subject2TutorBox.setEnabled(false);
        subject3TutorBox.setEnabled(false);
        updateLevelBox.setEnabled(false);

        // SUBJECT 1
        gbc.gridx = 0;
        addLabelAndComponent(formPanel, gbc, row++, "Subject 1:", subjectBox1);
        gbc.gridx = 2;
        formPanel.add(removeButton1, gbc);

        gbc.gridx = 0;
        addLabelAndComponent(formPanel, gbc, row++, "Tutor:", subject1TutorBox);

        // SUBJECT 2
        gbc.gridx = 0;
        addLabelAndComponent(formPanel, gbc, row++, "Subject 2:", subjectBox2);
        gbc.gridx = 2;
        formPanel.add(removeButton2, gbc);

        gbc.gridx = 0;
        addLabelAndComponent(formPanel, gbc, row++, "Tutor:", subject2TutorBox);

        // SUBJECT 3
        gbc.gridx = 0;
        addLabelAndComponent(formPanel, gbc, row++, "Subject 3:", subjectBox3);
        gbc.gridx = 2;
        formPanel.add(removeButton3, gbc);

        gbc.gridx = 0;
        addLabelAndComponent(formPanel, gbc, row++, "Tutor:", subject3TutorBox);

        // STATUS
        gbc.gridx = 0;
        addLabelAndComponent(formPanel, gbc, row++, "Study Status:", statusBox);

        // LEVEL
        gbc.gridx = 0;
        addLabelAndComponent(formPanel, gbc, row++, "Level:", updateLevelBox);


        // center container - student information
        studentInfoLabel = new JTextArea("Student Info: -");
        studentInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentInfoLabel.setEditable(false);
        studentInfoLabel.setBackground(null);
        studentInfoLabel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Student Details", 0, 0,
                new Font("Segoe UI", Font.BOLD, 18),
                LABEL_COLOR));
        studentInfoLabel.setLineWrap(true);
        studentInfoLabel.setWrapStyleWord(true);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 3;
        formPanel.add(studentInfoLabel, gbc);
        gbc.gridwidth = 1;

        // bottom container
        bottomContainer = new BottomContainer(
                "Home", new Color(58, 91, 204), this,
                "Reset", new Color(211, 47, 47), this,
                "Update", new Color(102, 187, 106), this
        );

        // binding actionListener
        searchButton.addActionListener(this);
        levelBox.addActionListener(this);
        monthBox.addActionListener(this);
        removeButton1.addActionListener(this);
        removeButton2.addActionListener(this);
        removeButton3.addActionListener(this);

        //lambda action listener
        subjectBox1.addActionListener(e -> updateTutorBoxForSubject(subjectBox1, subject1TutorBox, "1"));
        subjectBox2.addActionListener(e -> updateTutorBoxForSubject(subjectBox2, subject2TutorBox, "2"));
        subjectBox3.addActionListener(e -> updateTutorBoxForSubject(subjectBox3, subject3TutorBox, "3"));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(formPanel, BorderLayout.NORTH);

        topContainer.setOpaque(false); //can Inheritance the parent colour
        namePanel.setOpaque(false);
        infoPanel.setOpaque(false);
        formPanel.setBackground(FORM_BG_COLOR);
        wrapperPanel.setBackground(FORM_BG_COLOR);

        add(topContainer, BorderLayout.NORTH);
        add(wrapperPanel, BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);


    }

    private void addLabelAndComponent(JPanel panel, GridBagConstraints gbc,
                                      int row, String label, Component comp) {
        gbc.gridx = 0;
        gbc.gridy = row;

        JLabel lbl = new JLabel(label);
        lbl.setForeground(LABEL_COLOR);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panel.add(lbl, gbc);
        gbc.gridx = 1;
        panel.add(comp, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bottomContainer.leftButton) {
            home.showCard("home");

        } else if (e.getSource() == searchButton) {
            // code here
            String searchName = (String) nameBox.getSelectedItem();
            if (ValidUser.isEmpty(searchName)) {
                JOptionPane.showMessageDialog(this,
                        "Name cannot be empty!",
                        "Form error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Student> studentFromFile = Registration.loadStudentFromFile();
            List<Enrolment> enrolmentFromFile = Registration.loadEnrolment();
            Map<String, Enrolment> enrolmentMap = new HashMap<>();
            for (Enrolment enrolment : enrolmentFromFile) {
                enrolmentMap.put(enrolment.getUsername(), enrolment);
            }
            Student matchedStudent = null;
            Enrolment matchedEnrol = null;

            Map<String, String> usernameToName = new HashMap<>();
            for (Tutor t : Registration.getAllTutor()) {
                usernameToName.put(t.getUsername(), t.getFullName());
            }

            for (Student s : studentFromFile) {
                if (s.getName().equalsIgnoreCase(searchName)) {
                    matchedStudent = s;
                    matchedEnrol = enrolmentMap.get(s.getUsername());

                    //prevent using filter list method
                    levelBox.removeActionListener(this);
                    monthBox.removeActionListener(this);
                    levelBox.setSelectedItem(matchedStudent.getLevel());
                    monthBox.setSelectedItem(matchedEnrol.getMoE());
                    levelBox.addActionListener(this);
                    monthBox.addActionListener(this);
                    subjectBox1.setSelectedItem(matchedEnrol.getSubject1());
                    subjectBox2.setSelectedItem(matchedEnrol.getSubject2());
                    subjectBox3.setSelectedItem(matchedEnrol.getSubject3());
                    subject1TutorBox.setSelectedItem(usernameToName.get(matchedEnrol.getSubject1Tutor()));
                    subject2TutorBox.setSelectedItem(usernameToName.get(matchedEnrol.getSubject2Tutor()));
                    subject3TutorBox.setSelectedItem(usernameToName.get(matchedEnrol.getSubject3Tutor()));
                    updateLevelBox.setSelectedItem(matchedStudent.getLevel());

                    statusBox.setModel(new DefaultComboBoxModel<>(status));


                    removeButton1.setEnabled(true);
                    removeButton2.setEnabled(true);
                    removeButton3.setEnabled(true);
                    subjectBox1.setEnabled(true);
                    subjectBox2.setEnabled(true);
                    subjectBox3.setEnabled(true);
                    statusBox.setEnabled(true);
                    bottomContainer.rightButton2.setEnabled(true);
                    subject1TutorBox.setEnabled(true);
                    subject2TutorBox.setEnabled(true);
                    subject3TutorBox.setEnabled(true);
                    updateLevelBox.setEnabled(true);
                    break;
                }
            }
            if (matchedStudent != null) {
                studentInfoLabel.setText(studentInfoBuilder(matchedStudent, matchedEnrol));
            }

            /*
             * enroll button
             * */
        } else if (e.getSource() == bottomContainer.rightButton2) {
            String name = (String) nameBox.getSelectedItem();
            if (ValidUser.isEmpty(name)) {
                JOptionPane.showMessageDialog(this,
                        "Please select a student name before updating.",
                        "Update Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //get matched student and other information
            List<Student> studentList = Registration.loadStudentFromFile();
            List<Enrolment> enrolmentFromFile = Registration.loadEnrolment();
            Map<String, Enrolment> enrolmentMap = new HashMap<>();
            for (Enrolment enrolment : enrolmentFromFile) {
                enrolmentMap.put(enrolment.getUsername(), enrolment);
            }
            Student matchedStudent = null;
            Enrolment matchedEnrol = null;

            for (Student s : studentList) {
                if (s.getName().equalsIgnoreCase(name)) {
                    matchedEnrol = enrolmentMap.get(s.getUsername());
                    matchedStudent = s;

                    break;
                }
            }

            if (matchedStudent == null & matchedEnrol == null) {
                JOptionPane.showMessageDialog(this,
                        "Student not found.",
                        "Update Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String s1 = (String) subjectBox1.getSelectedItem();
            String s2 = (String) subjectBox2.getSelectedItem();
            String s3 = (String) subjectBox3.getSelectedItem();
            String tutor1FullName = (String) subject1TutorBox.getSelectedItem();
            String tutor2FullName = (String) subject2TutorBox.getSelectedItem();
            String tutor3FullName = (String) subject3TutorBox.getSelectedItem();
            String status = (String) statusBox.getSelectedItem();
            String level = (String) updateLevelBox.getSelectedItem();

            //use fullname to find username
            Map<String, String> tutorNameToUsername = new HashMap<>();
            for (Tutor t : Registration.getAllTutor()) {
                tutorNameToUsername.put(t.getFullName(), t.getUsername());
            }
            String tutor1Username = tutorNameToUsername.get(tutor1FullName);
            String tutor2Username = tutorNameToUsername.get(tutor2FullName);
            String tutor3Username = tutorNameToUsername.get(tutor3FullName);


            matchedEnrol.setSubject1(s1);
            matchedEnrol.setSubject2(s2);
            matchedEnrol.setSubject3(s3);
            matchedEnrol.setSubject1Tutor(tutor1Username);
            matchedEnrol.setSubject2Tutor(tutor2Username);
            matchedEnrol.setSubject3Tutor(tutor3Username);
            matchedStudent.setStudyStatus(status);
            matchedStudent.setLevel(level);

            boolean result = Registration.UpdateStudentEnrollmentAndStatus(matchedStudent, matchedEnrol);
            if (result) {

                JOptionPane.showMessageDialog(this,
                        "Student updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                studentInfoLabel.setText(studentInfoBuilder(matchedStudent, matchedEnrol));
                //refresh all panel
                ((ReceptionistDashboard) SwingUtilities.getWindowAncestor(this)).refreshAllPanels();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Update Error!",
                        "Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == levelBox) {
            filterStudentList();
        } else if (e.getSource() == monthBox) {
            filterStudentList();
        } else if (e.getSource() == removeButton1) {
            subjectBox1.setSelectedIndex(0);
        } else if (e.getSource() == removeButton2) {
            subjectBox2.setSelectedIndex(0);
        } else if (e.getSource() == removeButton3) {
            subjectBox3.setSelectedIndex(0);
        } else if (e.getSource() == bottomContainer.rightButton1) {
            setResetButton();
        }
    }

    public void refreshStudentList() {
        List<Student> students = Registration.loadStudentFromFile();
        List<Enrolment> enrolments = Registration.loadEnrolment();
        Map<String, Enrolment> enrolmentMap = new HashMap<>();
        for (Enrolment enrolment : enrolments) {
            enrolmentMap.put(enrolment.getUsername(), enrolment);
        }


        ArrayList<String> studentNames = new ArrayList<>();
        for (Student s : students) {

            studentNames.add(s.getName());
        }
        nameBox.setModel(new DefaultComboBoxModel<>(studentNames.toArray(new String[0])));
    }

    private void filterStudentList() {
        String selectedLevel = (String) levelBox.getSelectedItem();
        String selectedMonth = (String) monthBox.getSelectedItem();

        // to check user using filter function
        // if equals empty return null
        if (selectedLevel.equals("")) selectedLevel = null;
        if (selectedMonth.equals("")) selectedMonth = null;

        List<Student> students = Registration.loadStudentFromFile();
        List<Enrolment> enrolments = Registration.loadEnrolment();
        Map<String, Enrolment> enrolmentMap = new HashMap<>();
        for (Enrolment enrolment : enrolments) {
            enrolmentMap.put(enrolment.getUsername(), enrolment);
        }
        ArrayList<String> filteredNames = new ArrayList<>();


        for (Student s : students) {
            Enrolment e = enrolmentMap.get(s.getUsername());
            // true = no apply filter
            // false = apply filter
            /*if first statement correct selectedXXX == null , it means doesn't apply filter ,
             * because user is not using filter function,
             * if first statement selectedXXX != null , it means apply filter,
             * it will apply filter , selectedXXX.equal(s.getXXX)
             * */

            boolean matchLevel = (selectedLevel == null) || selectedLevel.equalsIgnoreCase(s.getLevel());
            boolean matchMonth = (selectedMonth == null) || selectedMonth.equalsIgnoreCase(e.getMoE());
            // if apply filter , but student name cant found
            // this statement will not execute
            // matchLevel or matchMonth, is false , due to no same name
            if (matchLevel && matchMonth) {
                filteredNames.add(s.getName());
            }
        }

        if (filteredNames.isEmpty()) {
            nameBox.setModel(new DefaultComboBoxModel<>(new String[]{"No matching students"}));
        } else {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("");
            for (String name : filteredNames) {
                model.addElement(name);
            }
            nameBox.setModel(model);
        }
    }

    private void setResetButton() {
        // Remove subject listeners to prevent triggering updateTutorBoxForSubject
        subjectBox1.removeActionListener(subjectBox1.getActionListeners()[0]);
        subjectBox2.removeActionListener(subjectBox2.getActionListeners()[0]);
        subjectBox3.removeActionListener(subjectBox3.getActionListeners()[0]);

        levelBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        nameBox.setSelectedIndex(0);
        subjectBox1.setSelectedIndex(0);
        subjectBox2.setSelectedIndex(0);
        subjectBox3.setSelectedIndex(0);
        subject1TutorBox.setModel(new DefaultComboBoxModel<>(new String[]{""}));
        subject2TutorBox.setModel(new DefaultComboBoxModel<>(new String[]{""}));
        subject3TutorBox.setModel(new DefaultComboBoxModel<>(new String[]{""}));
        studentInfoLabel.setText("Student Info: -");
        statusBox.setModel(new DefaultComboBoxModel<>(new String[0]));

        removeButton1.setEnabled(false);
        removeButton2.setEnabled(false);
        removeButton3.setEnabled(false);
        bottomContainer.rightButton2.setEnabled(false);
        subjectBox1.setEnabled(false);
        subjectBox2.setEnabled(false);
        subjectBox3.setEnabled(false);
        subject1TutorBox.setEnabled(false);
        subject2TutorBox.setEnabled(false);
        subject3TutorBox.setEnabled(false);
        statusBox.setEnabled(false);
        updateLevelBox.setEnabled(false);

        // Re-add subject listeners
        subjectBox1.addActionListener(e -> updateTutorBoxForSubject(subjectBox1, subject1TutorBox, "1"));
        subjectBox2.addActionListener(e -> updateTutorBoxForSubject(subjectBox2, subject2TutorBox, "2"));
        subjectBox3.addActionListener(e -> updateTutorBoxForSubject(subjectBox3, subject3TutorBox, "3"));

    }

    private String studentInfoBuilder(Student matchedStudent, Enrolment matchedEnrol) {
        StringBuilder infoBuilder = new StringBuilder();
        infoBuilder.append("Name: ").append(matchedStudent.getName()).append("\n");
        infoBuilder.append("Level: ").append(matchedStudent.getLevel()).append("\n");
        infoBuilder.append("Month of Enrolment: ").append(matchedEnrol.getMoE()).append("\n");
        infoBuilder.append("Date of Birth: ").append(matchedStudent.getDateOfBirth()).append("\n");
        // Subject 1
        String subject1 = matchedEnrol.getSubject1();
        if (subject1 != null) {
            String request1 = RequestController.checkRequest(subject1, matchedStudent);
            infoBuilder.append("Subject 1: ").append(subject1)
                    .append("\t\tRequest Subject: ").append(request1 != null ? request1 : "").append("\n");
        }

        // Subject 2
        String subject2 = matchedEnrol.getSubject2();
        if (subject2 != null) {
            String request2 = RequestController.checkRequest(subject2, matchedStudent);
            infoBuilder.append("Subject 2: ").append(subject2)
                    .append("\t\tRequest Subject: ").append(request2 != null ? request2 : "").append("\n");
        }
        // Subject 3
        String subject3 = matchedEnrol.getSubject3();
        if (subject3 != null) {
            String request3 = RequestController.checkRequest(subject3, matchedStudent);
            infoBuilder.append("Subject 3: ").append(subject3)
                    .append("\t\tRequest Subject: ").append(request3 != null ? request3 : "").append("\n");
        }

        infoBuilder.append("Study Status: ").append(matchedStudent.getStudyStatus()).append("\n");


        return infoBuilder.toString();
    }

    private void updateTutorBoxForSubject(JComboBox<String> subjectBox, JComboBox<String> tutorBox, String subjectIndex) {
        String selectedSubject = (String) subjectBox.getSelectedItem();
        String name = (String) nameBox.getSelectedItem();

        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student name before selecting a subject.",
                    "Missing Student", JOptionPane.WARNING_MESSAGE);
            return;
        }

        /*if (selectedSubject == null || selectedSubject.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a subject to view available tutors.",
                    "Missing Subject", JOptionPane.WARNING_MESSAGE);
            return;
        }*/

        List<Tutor> allTutors = Registration.getAllTutor();
        // Create a mapping from tutor username to full name,
        // so we can easily retrieve the tutor's name using their username later
        Map<String, String> usernameToName = new HashMap<>();
        for (Tutor t : allTutors) {
            usernameToName.put(t.getUsername(), t.getFullName());
        }

        Map<String, AssignmentModel> assignmentModelMap = Assignment.loadAssignmentData();

        List<Enrolment> enrolmentFromFile = Registration.loadEnrolment();
        Map<String, Enrolment> enrolmentMap = new HashMap<>();
        for (Enrolment enrolment : enrolmentFromFile) {
            enrolmentMap.put(enrolment.getUsername(), enrolment);
        }

        Enrolment matchedEnrol = null;
        String studentLevel = null;
        //to get matched student
        for (Student s : Registration.loadStudentFromFile()) {
            if (s.getName().equalsIgnoreCase(name)) {
                matchedEnrol = enrolmentMap.get(s.getUsername());
                studentLevel = s.getLevel();
                break;
            }
        }

        if (matchedEnrol == null || studentLevel == null) {
            JOptionPane.showMessageDialog(this,
                    "Cannot find enrolment information or level for the selected student.",
                    "Data Not Found",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //
        // Get the current tutor's username for the selected subject box.
        // using switch can reduce if else
        String currentTutorUsername = switch (subjectIndex) {
            case "1" -> matchedEnrol.getSubject1Tutor();
            case "2" -> matchedEnrol.getSubject2Tutor();
            default -> matchedEnrol.getSubject3Tutor();
        };

        String currentTutorFullName = usernameToName.get(currentTutorUsername);
        DefaultComboBoxModel<String> tutorModel = new DefaultComboBoxModel<>();
        if (currentTutorFullName != null) {
            tutorModel.addElement(currentTutorFullName);
        }

        for (Tutor t : allTutors) {
            boolean result = TutorController.isTutorAbleToTeach(t, selectedSubject, studentLevel, assignmentModelMap);

            if (result) {
                String fullName = t.getFullName();
                if (!fullName.equals(currentTutorFullName)) {
                    tutorModel.addElement(fullName);
                }
            }
        }
        tutorBox.setModel(tutorModel);
        tutorBox.setSelectedItem(currentTutorFullName);
    }

    private void setComboBoxBg(JComboBox<?>... boxes) {
        for (JComboBox<?> box : boxes) {
            box.setBackground(FORM_BG_COLOR);
        }
    }
}

