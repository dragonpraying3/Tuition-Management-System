package view.Tutor;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;



public class EnrolledStudents extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public EnrolledStudents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        //Title
        JLabel titleLabel = new JLabel("Enrolled Studnets");
        titleLabel.setFont(new Font("Arial", Font.BOLD,28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Table model setup
        String[] columns = {"Student Name", "Subject", "Form"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        //box for each data
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        //Go back button
        JButton backButton = new JButton("Go Back");
        backButton.setFont(new Font("Arial",Font.PLAIN,16));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            TutorView tutorView = (TutorView) SwingUtilities.getWindowAncestor(this);
            tutorView.showHomePanel();
        });

        loadEnrolledDataFromController();

    }
    private void loadEnrolledDataFromController() {
        List<String[]> data = controller.EnrolledStudents.getEnrolledData();
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }

}










