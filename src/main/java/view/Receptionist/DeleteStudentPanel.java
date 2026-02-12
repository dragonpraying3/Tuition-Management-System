package view.Receptionist;

import controller.Registration;
import model.Enrolment;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteStudentPanel extends JPanel implements ActionListener {

    JPanel titlePanel, tablePanel, filterPanel;
    JLabel titleLabel, filterLabel;
    final String[] COLUMN = {"Username", "Student Name", "Study Status", "IC", "Age", "Date Of Birth", "Email", "Contact", "Address",
            "Gender", "Race", "Level", "Month of Enrolment", "Subject1", "Subject2", "Subject3"};
    DefaultTableModel model;
    JTable table;
    JScrollPane sp;
    ReceptionistDashboard home;
    BottomContainer bottomContainer;
    final String[] STATUS = {"Both", "Active", "Completed"};
    JComboBox<String> studyStatusBox;
    private static final Color FORM_BG_COLOR = new Color(245, 248, 255);
    private static final Color LABEL_COLOR = new Color(33, 37, 41);

    public DeleteStudentPanel(ReceptionistDashboard home) {
        //make sure panel is updated
        this.home = home;
        this.setBackground(FORM_BG_COLOR);
        this.setLayout(new BorderLayout());

        //create ui
        studyStatusBox = new JComboBox<>(STATUS);

        studyStatusBox.setAlignmentX(Component.CENTER_ALIGNMENT);


        //top panel
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        titleLabel = new JLabel("Remove Student Record", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));

        // filter panel
        filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        filterLabel = new JLabel("Filter by Study Status:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studyStatusBox.setPreferredSize(new Dimension(150, 25));
        studyStatusBox.setFocusable(false);
        filterPanel.add(filterLabel);
        filterPanel.add(studyStatusBox);

        //styling
        styleLabel(titleLabel, filterLabel);

        //add to top
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10)); //add gap
        titlePanel.add(filterPanel);

        //center
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        model = new DefaultTableModel(COLUMN, 0) { //made the table in non-editable mode
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //support horizontal scroll
        //can only allow to select row but not column
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false); //cannot modify the title
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(200);
        }

        //table design
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.DARK_GRAY);
        table.setGridColor(new Color(200, 200, 200));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(220, 230, 240));

        List<Student> students = Registration.loadStudentFromFile();
        List<Enrolment> enrolments = Registration.loadEnrolment();
        // create map key = username, value = enrolment
        Map<String, Enrolment> enrolmentMap = new HashMap<>();
        for (Enrolment e : enrolments) {
            enrolmentMap.put(e.getUsername(), e);
        }
        for (Student student : students) {
            //return value of enrolment map
            Enrolment e = enrolmentMap.get(student.getUsername());
            if (e == null) continue;
            String[] row = {
                    student.getUsername(),
                    student.getName(),
                    student.getStudyStatus(),
                    student.getIc(),
                    student.getAge(),
                    student.getDateOfBirth(),
                    student.getEmail(),
                    student.getContact(),
                    student.getAddress(),
                    student.getGender(),
                    student.getRace(),
                    student.getLevel(),
                    e.getMoE(),
                    e.getSubject1(),
                    e.getSubject2(),
                    e.getSubject3()
            };
            model.addRow(row);
        }

        sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tablePanel.add(sp, BorderLayout.CENTER);

        //bottom
        bottomContainer = new BottomContainer(
                "Home", new Color(58, 91, 204), this,
                "Cancel", new Color(158, 158, 158), this,
                "Remove", new Color(211, 47, 47), this
        );

        bottomContainer.rightButton1.setEnabled(false);
        bottomContainer.rightButton2.setEnabled(false);

        //binding ActionListener
        table.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = table.getSelectedRow() != -1;
            bottomContainer.rightButton1.setEnabled(rowSelected);
            bottomContainer.rightButton2.setEnabled(rowSelected);
        });

        studyStatusBox.addActionListener(this);

        //colour
        studyStatusBox.setBackground(FORM_BG_COLOR);
        titlePanel.setBackground(FORM_BG_COLOR);
        filterPanel.setBackground(FORM_BG_COLOR);
        tablePanel.setBackground(FORM_BG_COLOR);
        titleLabel.setForeground(LABEL_COLOR);
        filterLabel.setForeground(LABEL_COLOR);

        add(titlePanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bottomContainer.rightButton1) {
            table.clearSelection();
        } else if (e.getSource() == bottomContainer.leftButton) {
            home.showCard("home");
        } else if (e.getSource() == bottomContainer.rightButton2) {
            int SelectedRow = table.getSelectedRow();
            if (SelectedRow == -1) return;
            //check study status
            String status = table.getValueAt(SelectedRow, 2).toString();
            if (status.equals("Active")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cannot delete student. Status: Active.\nOnly completed students can be removed from the system.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Map<String, Student> studentMap = new HashMap<>();
            for (Student s : Registration.loadStudentFromFile()) {
                studentMap.put(s.getUsername(), s);
            }

            String username = (String) table.getValueAt(SelectedRow, 0);
            // if username key equal studentMap key , return the student information
            Student matchedStudent = studentMap.get(username);

            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to permanently delete the student \"" + matchedStudent.getName() + "\"?\nThis action cannot be undone.",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                // delete user
                String result = Registration.DeleteStudent(matchedStudent.getUsername());
                if (result.equals("Success")) {
                    JOptionPane.showMessageDialog(this, "Student " + matchedStudent.getName() + " delete successfully.");
                    ((DefaultTableModel) table.getModel()).removeRow(SelectedRow); //update the GUI of the table
                    ((ReceptionistDashboard) SwingUtilities.getWindowAncestor(this)).refreshAllPanels();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete Student: " + result);
                }
            }
        } else if (e.getSource() == studyStatusBox) {
            if (studyStatusBox.getSelectedIndex() == 0) {
                refreshStudentTable();
                return;
            }
            filterStudentListAndTable();
        }
    }

    private void filterStudentListAndTable() {
        String status = (String) studyStatusBox.getSelectedItem();

        model.setRowCount(0);
        Map<String, Enrolment> enrolmentMap = new HashMap<>();
        for (Enrolment e : Registration.loadEnrolment()) {
            enrolmentMap.put(e.getUsername(), e);
        }

        for (Student s : Registration.loadStudentFromFile()) {
            if (s.getStudyStatus().equalsIgnoreCase(status)) {
                Enrolment e = enrolmentMap.get(s.getUsername());
                if (e == null) continue;
                String[] row = {
                        s.getUsername(),
                        s.getName(),
                        s.getStudyStatus(),
                        s.getIc(),
                        s.getAge(),
                        s.getDateOfBirth(),
                        s.getEmail(),
                        s.getContact(),
                        s.getAddress(),
                        s.getGender(),
                        s.getRace(),
                        s.getLevel(),
                        e.getMoE(),
                        e.getSubject1(),
                        e.getSubject2(),
                        e.getSubject3()
                };
                model.addRow(row);
            }
        }

    }

    public void refreshStudentTable() {
        model.setRowCount(0);

        List<Student> students = Registration.loadStudentFromFile();
        List<Enrolment> enrolments = Registration.loadEnrolment();
        // create map key = username, value = enrolment
        Map<String, Enrolment> enrolmentMap = new HashMap<>();
        for (Enrolment e : enrolments) {
            enrolmentMap.put(e.getUsername(), e);
        }

        for (Student student : students) {
            //return value of enrolment map
            Enrolment e = enrolmentMap.get(student.getUsername());
            if (e == null) continue;

            String[] row = {
                    student.getUsername(),
                    student.getName(),
                    student.getStudyStatus(),
                    student.getIc(),
                    student.getAge(),
                    student.getDateOfBirth(),
                    student.getEmail(),
                    student.getContact(),
                    student.getAddress(),
                    student.getGender(),
                    student.getRace(),
                    student.getLevel(),
                    e.getMoE(),
                    e.getSubject1(),
                    e.getSubject2(),
                    e.getSubject3()
            };
            model.addRow(row);
        }
    }

    private void styleLabel(JLabel... labels) {
        for (JLabel label : labels) {
            label.setForeground(LABEL_COLOR);
        }
    }
}
