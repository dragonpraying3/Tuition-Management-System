package view.Tutor;

import controller.AddClassInformation;
import controller.ValidUser;
import model.ClassInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddClass extends JPanel {

    private JTextField tfCharge;
    private JButton submitBtn;
    private JComboBox<String> cbSubject;
    private JComboBox<String> cbDay;
    private JComboBox<String> cbStartTime;
    private JComboBox<String> cbEndTime;
    private JComboBox<String> cbRoom;

    public AddClass() {
        setLayout(new BorderLayout());

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 20);
        Dimension fieldSize = new Dimension(300, 40);

        // AddClassInformation instance
        AddClassInformation info = new AddClassInformation();

        // Title
        JLabel titleLabel = new JLabel("Add Class");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Subject
        JLabel subjectLabel = new JLabel("Select Subject:");
        subjectLabel.setFont(labelFont);
        formPanel.add(subjectLabel);

        cbSubject = new JComboBox<>();
        cbSubject.setFont(fieldFont);
        cbSubject.setPreferredSize(fieldSize);
        List<String> subjectList = info.loadTutorSubjects("src/main/resources/tutor.txt");
        for (String subject : subjectList) {
            cbSubject.addItem(subject);
        }
        formPanel.add(cbSubject);

        // Day
        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setFont(labelFont);
        formPanel.add(dayLabel);

        String[] daysWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        cbDay = new JComboBox<>(daysWeek);
        cbDay.setFont(fieldFont);
        cbDay.setPreferredSize(fieldSize);
        formPanel.add(cbDay);

        // Time
        JLabel timeLabel = new JLabel("Start Time / End Time:");
        timeLabel.setFont(labelFont);
        formPanel.add(timeLabel);

        String[] timeSlots = {
                "10:00am", "11:00am", "12:00pm", "1:00pm",
                "2:00pm", "3:00pm", "4:00pm", "5:00pm",
                "6:00pm", "7:00pm", "8:00pm", "9:00pm", "10:00pm"
        };

        cbStartTime = new JComboBox<>(timeSlots);
        cbEndTime = new JComboBox<>(timeSlots);
        cbStartTime.setFont(fieldFont);
        cbEndTime.setFont(fieldFont);

        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        timePanel.add(cbStartTime);
        timePanel.add(new JLabel(" to "));
        timePanel.add(cbEndTime);
        formPanel.add(timePanel);

        // Charge
        JLabel chargeLabel = new JLabel("Charge:");
        chargeLabel.setFont(labelFont);
        formPanel.add(chargeLabel);

        tfCharge = new JTextField();
        tfCharge.setFont(fieldFont);
        tfCharge.setPreferredSize(fieldSize);
        formPanel.add(tfCharge);

        // Room
        JLabel roomLabel = new JLabel("Room:");
        roomLabel.setFont(labelFont);
        formPanel.add(roomLabel);

        String[] roomOptions = {"B-07-03", "B-05-05", "E-09-03", "B-07-07"};
        cbRoom = new JComboBox<>(roomOptions);
        cbRoom.setFont(fieldFont);
        cbRoom.setPreferredSize(fieldSize);
        formPanel.add(cbRoom);

        // Submit button
        formPanel.add(new JLabel()); // spacer
        submitBtn = new JButton("Add Class");
        submitBtn.setFont(fieldFont);
        formPanel.add(submitBtn);


        add(formPanel, BorderLayout.CENTER);

        // Bottom panel (Go Back)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        JButton backButton = new JButton("Go Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof TutorView tutorView) {
                tutorView.showHomePanel();
            }
        });
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add Button Action
        submitBtn.addActionListener(e -> {
            String username = ValidUser.getCurrentUser();
            String subject = (String) cbSubject.getSelectedItem();
            String day = (String) cbDay.getSelectedItem();
            String startTime = (String) cbStartTime.getSelectedItem();
            String endTime = (String) cbEndTime.getSelectedItem();
            String time = startTime.trim() + " - " + endTime.trim(); // normalize
            String charge = tfCharge.getText().trim();
            String room = (String) cbRoom.getSelectedItem();

            // Validation
            if (subject == null || subject.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a subject.", "Missing Subject", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (charge.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a charge.", "Missing Charge", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!charge.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Charge must be numeric.", "Invalid Charge", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (room == null || room.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a room.", "Missing Room", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!info.ValidTimeRange(time)) {
                JOptionPane.showMessageDialog(this, "Please choose a valid time range (2hour)", "Time Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate if tutor has been assigned to any form
            if (!info.isTutorAssignedForm("src/main/resources/assignment.txt")) {
                JOptionPane.showMessageDialog(this, "This is a new tutor. Must be assigned by admin first.",
                        "Unassigned Tutor", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Create ClassInfo
            ClassInfo ci = new ClassInfo(username, subject, time,day, charge, room);

            // Validate for duplicates
            if (info.checkDupe(ci)) {
                JOptionPane.showMessageDialog(this,
                        "This class already exists in your schedule.",
                        "Duplicate Class", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save class
            info.saveToFile(ci);
            JOptionPane.showMessageDialog(this, "Class added successfully!");

            // Return to home
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof TutorView tutorView) {
                tutorView.showHomePanel();
            }
        });
    }
}
