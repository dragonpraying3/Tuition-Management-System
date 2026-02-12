package view.Student;

import controller.StudentController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class SubjectChange extends JPanel {

        private JComboBox<String> subjectList, preferredList;
        private String Username;
        private JTable requestTable;
        private StudentView parent;
        private String[] subject = {"Bahasa Melayu", "English","Chinese Language", "Mathematics", "Additional Mathematics", "Science",
                "Physics", "Chemistry", "Biology", "History", "Geography", "Moral Education", "Pendidikan Islam",
                "Economics", "Business Studies", "Accounting"};

        public SubjectChange(StudentView parent, String Username) {
            this.parent = parent;
            this.Username = Username;

            setLayout(new BorderLayout());

            ArrayList<String> enrolledSubjects = StudentController.getEnrolledSubjects(Username);

            if (enrolledSubjects.isEmpty()) {
                JOptionPane.showMessageDialog(this, "You are not enrolled into any subjects");
                setEnabled(false);
                return;
            }

            subjectList = new JComboBox<>(enrolledSubjects.toArray(new String[0]));
            preferredList = new JComboBox<>(subject);

            JComboBox<String> receptionistComboBox = new JComboBox<>();
            Set<String> addedNames = new HashSet<>();

            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/receptionist.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length >= 2) {
                        String name = parts[1].trim();
                        if (!addedNames.contains(name)) {
                            receptionistComboBox.addItem(name);
                            addedNames.add(name);
                        }
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading receptionist list.");
            }

            JPanel topPanel = new JPanel(new GridLayout(4, 2, 5, 5));
            topPanel.setBorder(BorderFactory.createTitledBorder("Submit Request"));

            topPanel.add(new JLabel("Current Subject: "));
            topPanel.add(subjectList);
            topPanel.add(new JLabel("Preferred Subject: "));
            topPanel.add(preferredList);
            topPanel.add(new JLabel("Receptionist Name: "));
            topPanel.add(receptionistComboBox);

            JButton sendButton = new JButton("Send Request");
            JButton viewButton = new JButton("Refresh Table");
            JButton deleteButton = new JButton("Delete Pending Request");

            topPanel.add(sendButton);
            topPanel.add(viewButton);

            add(topPanel, BorderLayout.NORTH);

            String[] columnNames = {"Request ID", "Current Subject", "Preferred Subject", "Receptionist", "Status"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int rows, int column) {
                    return false;
                }
            };
            requestTable = new JTable(model);
            requestTable.getTableHeader().setReorderingAllowed(false);
            requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            requestTable.getTableHeader().setResizingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(requestTable);
            scrollPane.setBorder(BorderFactory.createTitledBorder("My Requests"));
            add(scrollPane, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            bottomPanel.add(deleteButton);

            JButton backButton = new JButton("Go Back");
            backButton.setPreferredSize(new Dimension(120, 25));
            backButton.addActionListener(e -> {
                parent.returnToMainMenu();
            });

            bottomPanel.add(backButton);
            add(bottomPanel, BorderLayout.SOUTH);

            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String subject = subjectList.getSelectedItem().toString();
                    String preferred = preferredList.getSelectedItem().toString();
                    String receptionist = receptionistComboBox.getSelectedItem().toString().trim();

                    if (receptionistComboBox.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, "Please select a receptionist.");
                        return;
                    }

                    if (enrolledSubjects.contains(preferred)) {
                        JOptionPane.showMessageDialog(null, "You are already enrolled in " + preferred + ". Please select a different subject.");
                        return;
                    }

                    if (subject.equals(preferred)) {
                        JOptionPane.showMessageDialog(null, "Same Subject cannot be chosen again, Choose Another Subject");
                        return;
                    }

                    String requestId = StudentController.generateRequestId();
                    boolean result = StudentController.sendChangeRequest(Username, requestId, subject, preferred, receptionist);
                    if (result) JOptionPane.showMessageDialog(null, "Request sent.");
                    receptionistComboBox.setSelectedIndex(0);
                    refreshRequestTable();
                }
            });

            viewButton.addActionListener(e -> refreshRequestTable());

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = requestTable.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(null, "Please select a request to update");
                        return;
                    }

                    String status = requestTable.getValueAt(selectedRow, 4).toString().trim();
                    if (!status.equalsIgnoreCase("pending")) {
                        JOptionPane.showMessageDialog(null, "Only pending requests can be deleted.");
                        return;
                    }

                    String requestId = requestTable.getValueAt(selectedRow, 0).toString().trim();
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Are You Sure You Want To Delete This Request?");

                    if (confirm == JOptionPane.YES_OPTION) {
                        StudentController.deletePendingRequest(Username, requestId);
                        refreshRequestTable();
                    }
                }
            });

            refreshRequestTable();
        }

        private void refreshRequestTable() {
            ArrayList<String[]> requests = StudentController.getUserRequests(Username);
            DefaultTableModel model = (DefaultTableModel) requestTable.getModel();
            model.setRowCount(0);

            for (String[] r : requests) {
                if(r.length == 6) {
                    model.addRow(new Object[]{r[1], r[2], r[3], r[4], r[5]});
                }
            }
        }
    }



