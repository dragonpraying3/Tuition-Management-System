package view.Student;

import controller.StudentController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PaymentView extends JPanel {

    private String username;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> subjectBox;
    private JComboBox<String> methodBox;
    private JButton payButton;
    private JLabel statusLabel;
    private JButton backButton;
    private StudentView parentView;
    private JLabel balanceLabel;

    public PaymentView(StudentView parentView, String Username) {
        this.username = Username;
        this.parentView = parentView;

        makePaymentPanel();

    }

    public void makePaymentPanel() {
        removeAll();
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Make Payment", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Subject", "Charge (RM)", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        subjectBox = new JComboBox<>();
        methodBox = new JComboBox<>(new String[]{"Cash", "Card", "Online"});
        payButton = new JButton("Make Payment");
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GREEN);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(new JLabel("Select Subject:"));
        form.add(subjectBox);
        form.add(new JLabel("Payment Method:"));
        form.add(methodBox);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(form, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(payButton);

        backButton = new JButton("Go Back");
        buttonPanel.add(backButton);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadPaymentDetails();
        loadPendingSubjects();

        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String subject = (String) subjectBox.getSelectedItem();
                String method = (String) methodBox.getSelectedItem();

                if (subject == null || method == null) {
                    statusLabel.setText("Please select subject and method.");
                    return;
                }

                StudentController.makePayment(username, subject, method);
                statusLabel.setText("Payment made for " + subject);

                loadPaymentDetails();
                loadPendingSubjects();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentView.returnToMainMenu();
            }
        });

        revalidate();
        repaint();
    }

    public void viewTotalBalancePanel() {
        removeAll();
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Total Balance", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Subject", "Charge (RM)", "Date", "Payment Method", "Status"};
        DefaultTableModel balanceTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable balanceTable = new JTable(balanceTableModel);
        balanceTable.setDefaultEditor(Object.class, null);
        balanceTable.getTableHeader().setReorderingAllowed(false);
        balanceTable.getTableHeader().setResizingAllowed(false);
        add(new JScrollPane(balanceTable), BorderLayout.CENTER);

        balanceLabel = new JLabel("", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(balanceLabel, BorderLayout.SOUTH);

        backButton = new JButton("Go Back");
        balanceLabel = new JLabel("", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(balanceLabel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(backButton);
        bottomPanel.add(rightPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentView.returnToMainMenu();
            }
        });

        loadTotalBalance(balanceTableModel);

        revalidate();
        repaint();
    }

    private void loadPaymentDetails() {
        tableModel.setRowCount(0); // Clear old rows

        ArrayList<String> subjects = StudentController.getUserSubjects(username);
        ArrayList<String[]> payments = StudentController.getUserPayments(username);

        for (String subject : subjects) {
            double charge = StudentController.getChargeForSubject(subject);
            String status = "";

            for (String[] p : payments) {
                if (p[0].equalsIgnoreCase(subject)) {
                    status = p[4]; // This will be "" or "Pending"
                    break;
                }
            }

            tableModel.addRow(new Object[]{subject, charge, status});
        }
    }



    private void loadPendingSubjects() {
        subjectBox.removeAllItems();
        statusLabel.setText(""); // Clear old message

        ArrayList<String> subjects = StudentController.getUserSubjects(username);
        ArrayList<String[]> payments = StudentController.getUserPayments(username);

        for (String subject : subjects) {
            boolean alreadyPaid = false;

            for (String[] p : payments) {
                if (p[0].equalsIgnoreCase(subject)) {
                    String status = p[4];
                    if (!status.isEmpty()) {
                        alreadyPaid = true;
                    }
                    break;
                }
            }

            if (!alreadyPaid) {
                subjectBox.addItem(subject);
            }
        }

        if (subjectBox.getItemCount() == 0) {
        }
    }


    private void loadTotalBalance(DefaultTableModel model) {
        model.setRowCount(0);

        ArrayList<String> subjects = StudentController.getUserSubjects(username);
        ArrayList<String[]> payments = StudentController.getUserPayments(username);

        double totalPaid = 0;
        double totalDue = 0;

        for (String subject : subjects) {
            double charge = StudentController.getChargeForSubject(subject);
            String date = "";
            String method = "";
            String status = "";

            for (String[] p : payments) {
                if (p[0].equalsIgnoreCase(subject)) {
                    date = p[2];
                    method = p[3];
                    status = p[4];
                    break;
                }
            }

            model.addRow(new Object[]{subject, charge, date, method, status});

            if (!status.isEmpty()) {
                totalPaid += charge;
            } else {
                totalDue += charge;
            }
        }

        balanceLabel.setText(String.format("Total Paid: RM %.2f   |   Total Due: RM %.2f", totalPaid, totalDue));
    }

}
