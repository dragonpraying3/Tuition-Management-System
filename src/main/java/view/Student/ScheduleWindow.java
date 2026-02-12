package view.Student;

import controller.StudentController;
import controller.ValidUser;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ScheduleWindow extends JPanel {

    private StudentView parent;

    public ScheduleWindow(StudentView parent) {
        this.parent = parent;

        setSize(600, 400);
        setLayout(new BorderLayout());

        JLabel titlelabel = new JLabel("Subject Schedule");
        titlelabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlelabel.setHorizontalAlignment(JLabel.CENTER);
        add(titlelabel, BorderLayout.NORTH);

        String Username = ValidUser.getCurrentUser();
        List<String[]> scheduleData = StudentController.getScheduleData(Username);

        String[] columns = {"Subject", "Tutor", "Day", "Time", "Room"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (String[] row : scheduleData) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollPanel = new JScrollPane(table);
        add(scrollPanel, BorderLayout.CENTER);

        JPanel outerBottomPanel = new JPanel(new BorderLayout());
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JButton backButton = new JButton("Go Back");
        backButton.setPreferredSize(new Dimension(120, 25));

        backButton.addActionListener(e -> {
            parent.returnToMainMenu(); // show welcome panel again
        });

        backPanel.add(backButton);
        outerBottomPanel.add(backPanel, BorderLayout.WEST);
        add(outerBottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}