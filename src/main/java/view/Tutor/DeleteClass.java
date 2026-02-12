package view.Tutor;

import controller.AddClassInformation;
import controller.ValidUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DeleteClass extends JPanel {
    private JTable classTable;
    private DefaultTableModel tableModel;
    private JButton deleteButton;
    private JButton goBackButton;

    public DeleteClass() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Delete Title
        JLabel titleLabel = new JLabel("Delete Class");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        //
        Font tableFont = new Font("Arial", Font.PLAIN, 16);
        Font headerFont = new Font("Arial", Font.BOLD, 18);

        // Table info
        String[] columns = {"Subject", "Time", "Day", "Charge", "Room"};
        tableModel = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        classTable = new JTable(tableModel);
        classTable.setFont(tableFont);
        classTable.setRowHeight(30);
        classTable.getTableHeader().setFont(headerFont);
        classTable.setShowGrid(true);
        classTable.setGridColor(Color.BLACK);
        classTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(classTable);
        add(scrollPane, BorderLayout.CENTER);

        //  Bottom Buttons
        goBackButton = new JButton("Go Back");
        goBackButton.setFont(new Font("Arial", Font.PLAIN, 16));

        deleteButton = new JButton("Delete Selected Class");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(goBackButton, BorderLayout.WEST);
        bottomPanel.add(deleteButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load Tutor Classes
        loadTutorClassesIntoTable();

        // Delete Handler
        deleteButton.addActionListener(e -> {
            int selectedRow = classTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a class to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete the selected class?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            String subject = (String) tableModel.getValueAt(selectedRow, 0);
            String time = (String) tableModel.getValueAt(selectedRow, 1);
            String day = (String) tableModel.getValueAt(selectedRow, 2);

            AddClassInformation info = new AddClassInformation();
            String currentUser = ValidUser.getCurrentUser();
            String toDelete = null;

            List<String> allClasses = AddClassInformation.loadClassesFromFile();
            for (String line : allClasses) {
                String[] parts = line.split(";");
                if (parts.length >= 6 &&
                        parts[0].equals(currentUser) &&
                        parts[1].equalsIgnoreCase(subject) &&
                        parts[2].equalsIgnoreCase(day) &&
                        parts[3].equalsIgnoreCase(time)) {
                    toDelete = line;
                    break;
                }
            }

            if (toDelete != null) {
                info.deleteClass(toDelete);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Class deleted successfully!");
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (topFrame instanceof TutorView tutorView) {
                    tutorView.showHomePanel();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Class not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //  Go Back
        goBackButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame instanceof TutorView tutorView) {
                tutorView.showHomePanel();
            }
        });
    }

    // Load Tutor Classes Into Table
    private void loadTutorClassesIntoTable() {
        String currentUser = ValidUser.getCurrentUser();
        List<String> allClasses = AddClassInformation.loadClassesFromFile();

        for (String line : allClasses) {
            String[] parts = line.split(";");
            if (parts.length >= 6 && parts[0].equals(currentUser)) {
                String[] row = {parts[1], parts[3], parts[2], parts[4], parts[5]};
                tableModel.addRow(row);
            }
        }
    }
}
