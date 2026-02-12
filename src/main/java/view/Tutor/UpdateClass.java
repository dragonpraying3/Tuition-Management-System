package view.Tutor;

import controller.AddClassInformation;
import model.ClassInfo;
import controller.ValidUser;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UpdateClass extends JPanel {
    JTextField tfCharge;
    private JComboBox<String> cbSubject;
    private JComboBox<String> cbDay;
    private JComboBox<String> cbStartTime;
    private JComboBox<String> cbEndTime;
    private JComboBox<String> cbRoom;
    private JButton updateButton;

    public UpdateClass() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 20);
        Dimension fieldSize = new Dimension(300, 40);

        AddClassInformation info = new AddClassInformation();

        // Title
        JLabel titleLabel = new JLabel(" Update Class ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        // Subject
        JLabel subjectLabel = new JLabel("Select Subject:");
        subjectLabel.setFont(labelFont);
        formPanel.add(subjectLabel);

        cbSubject = new JComboBox<>();
        cbSubject.setFont(fieldFont);
        cbSubject.setPreferredSize(fieldSize);
        List<String> subjectList = info.loadSubjectsFromClassInfo();
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
        JLabel timeLabel = new JLabel("Start Time - End Time:");
        timeLabel.setFont(labelFont);
        formPanel.add(timeLabel);

        String[] timeSlots = {
                "10:00am", "11:00am", "12:00pm", "1:00pm",
                "2:00pm", "3:00pm", "4:00pm", "5:00pm",
                "6:00pm", "7:00pm", "8:00pm"
        };
        cbStartTime = new JComboBox<>(timeSlots);
        cbStartTime.setFont(fieldFont);
        cbEndTime = new JComboBox<>(timeSlots);
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
        formPanel.add(tfCharge);

        // Room
        JLabel roomLabel = new JLabel("Room:");
        roomLabel.setFont(labelFont);
        formPanel.add(roomLabel);

        String[] roomOptions = {"B-07-03", "B-05-05", "E-09-03", "B-07-07", "room Bestari", "room Estar", "room alam"};
        cbRoom = new JComboBox<>(roomOptions);
        cbRoom.setFont(fieldFont);
        cbRoom.setPreferredSize(fieldSize);
        formPanel.add(cbRoom);

        // Update Button
        formPanel.add(new JLabel()); // empty filler
        updateButton = new JButton("Update Class");
        updateButton.setFont(new Font("Arial", Font.BOLD, 18));
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Go Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            TutorView tutorView = (TutorView) SwingUtilities.getWindowAncestor(this);
            tutorView.showHomePanel();
        });

        // Subject Selection: Auto-fill from file
        cbSubject.addActionListener(e -> {
            String selectedSubject = (String) cbSubject.getSelectedItem();
            String currentUser = ValidUser.getCurrentUser();

            if (selectedSubject == null || selectedSubject.trim().isEmpty()) return;

            List<String> allClasses = AddClassInformation.loadClassesFromFile();

            for (String line : allClasses) {
                String[] parts = line.split(";");
                if (parts.length != 6) continue;

                if (parts[0].equalsIgnoreCase(currentUser) &&
                        parts[1].equalsIgnoreCase(selectedSubject)) {

                    String fileTime = parts[2].trim();
                    String fileDay = parts[3].trim();
                    String fileCharge = parts[4].trim();
                    String fileRoom = parts[5].trim();

                    // Set day
                    for (int i = 0; i < cbDay.getItemCount(); i++) {
                        if (cbDay.getItemAt(i).trim().equalsIgnoreCase(fileDay)) {
                            cbDay.setSelectedIndex(i);
                            break;
                        }
                    }

                    // Set start and end time
                    String[] times = fileTime.split("-");
                    if (times.length == 2) {
                        String start = times[0].trim();
                        String end = times[1].trim();

                        for (int i = 0; i < cbStartTime.getItemCount(); i++) {
                            if (cbStartTime.getItemAt(i).trim().equalsIgnoreCase(start)) {
                                cbStartTime.setSelectedIndex(i);
                                break;
                            }
                        }

                        for (int i = 0; i < cbEndTime.getItemCount(); i++) {
                            if (cbEndTime.getItemAt(i).trim().equalsIgnoreCase(end)) {
                                cbEndTime.setSelectedIndex(i);
                                break;
                            }
                        }
                    }

                    tfCharge.setText(fileCharge);
                    cbRoom.setSelectedItem(fileRoom);
                    break;
                }

            }
        });
        // Update button logic
        updateButton.addActionListener(e -> {
            String username = ValidUser.getCurrentUser();
            String subject = (String) cbSubject.getSelectedItem();
            String day = (String) cbDay.getSelectedItem();
            String startTime = (String) cbStartTime.getSelectedItem();
            String endTime = (String) cbEndTime.getSelectedItem();
            String time = startTime + " - " + endTime;
            String charge = tfCharge.getText().trim();
            String room = (String) cbRoom.getSelectedItem();

            if (subject == null || subject.trim().isEmpty() ||
                    charge == null || charge.trim().isEmpty() || !charge.matches("\\d+") ||
                    room == null || room.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete all fields correctly.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ClassInfo updatedClass = new ClassInfo(username, subject, time,day, charge, room);
            String updatedLine = updatedClass.toFileString();

            List<String> allClasses = AddClassInformation.loadClassesFromFile();
            String originalLine = null;
            for (String line : allClasses) {
                String[] parts = line.split(";");
                if (parts.length >= 6 &&
                        parts[0].equalsIgnoreCase(username) &&
                        parts[1].equalsIgnoreCase(subject) &&
                        parts[2].equalsIgnoreCase(time)&&
                        parts[3].equalsIgnoreCase(day)) {
                    originalLine = line;
                    break;
                }
            }

            if (originalLine != null && originalLine.equalsIgnoreCase(updatedLine)) {
                JOptionPane.showMessageDialog(this, "No changes made. Class already exists.",
                        "Duplicate", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!info.ValidTimeRange(time)) {
                JOptionPane.showMessageDialog(this, "Please choose a valid time range (2hour)", "Time Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!info.validClass(updatedClass)){
                JOptionPane.showMessageDialog(this,
                        "Another class already exists at this time and room.",
                        "Double Booking", JOptionPane.ERROR_MESSAGE);
                return;
            }

            info.updateClass(updatedClass);

            JOptionPane.showMessageDialog(this, "Class updated successfully.");

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof TutorView tutorView) {
                tutorView.showHomePanel();
            }
        });
    }
}
