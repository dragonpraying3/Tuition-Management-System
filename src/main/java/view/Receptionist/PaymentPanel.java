package view.Receptionist;

import controller.Financial;
import controller.PageRefresh;
import controller.Registration;

import controller.ValidUser;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class PaymentPanel extends JPanel implements ActionListener {
    JComboBox<String> nameBox, paymentMethodBox, levelBox, monthBox, subjectBox, paymentStatusBox;
    JButton searchButton, filterButton;
    JPanel namePanel;
    ReceptionistDashboard home;
    JTextField amountField;
    DefaultTableModel model;
    JTable studentPaymentTable;
    final String[] LEVEL = {"", "Form 1", "Form 2", "Form 3", "Form 4", "Form 5"};
    final String[] MONTH = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    final String[] SUBJECT = {"", "Bahasa Melayu", "English", "Chinese Language", "Mathematics",
            "Additional Mathematics", "Science", "Physics", "Chemistry", "Biology", "History"};
    final String[] PAYMENT_METHOD = {"", "Online Banking", "Cash"};
    final String[] PAYMENT_STATUS = {"", "pending", "paid"};
    final String[] COLUMN = {"Username", "Student Name", "Subject", "Date", "Payment Method", "Payment Status"};
    FilterPanel filterPanel;
    BottomContainer bottomContainer;
    private static final Color FORM_BG_COLOR = new Color(245, 248, 255); // same as other panels
    private static final Color LABEL_COLOR   = new Color(33, 37, 41);

    public PaymentPanel(ReceptionistDashboard home) {
        this.home = home;
        this.setLayout(new BorderLayout());
        this.setBackground(FORM_BG_COLOR);
        filterPanel = new FilterPanel(LEVEL, MONTH, PAYMENT_METHOD, PAYMENT_STATUS);
        filterPanel.setVisible(false);

        //read student file
        List<Student> studentFromFile = Registration.loadStudentFromFile();
        List<String> studentName = new ArrayList<>();
        studentName.add(0, "");
        for (Student s : studentFromFile) {
            studentName.add(s.getName());
        }

        //create all ui
        nameBox = new JComboBox<>(studentName.toArray(new String[0]));
        searchButton = new JButton("Search");
        monthBox = new JComboBox<>(MONTH);
        levelBox = new JComboBox<>(LEVEL);
        subjectBox = new JComboBox<>(SUBJECT);
        paymentStatusBox = new JComboBox<>(PAYMENT_STATUS);
        amountField = new JTextField();
        paymentMethodBox = new JComboBox<>(PAYMENT_METHOD);
        filterButton = new JButton("Advanced Filter ▼");
        ButtonStyler.styleButton(filterButton, new Color(120, 120, 120));

        setComboBoxBg(nameBox, levelBox, monthBox, subjectBox, paymentMethodBox, paymentStatusBox,
                filterPanel.levelBox, filterPanel.monthBox, filterPanel.paymentMethodBox, filterPanel.paymentStatusBox);



        // First title
        JLabel titleLabel = new JLabel("STUDENT PAYMENT RECORD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(LABEL_COLOR);

        // TOP
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.add(new JLabel("Student Name:"){{
            setForeground(LABEL_COLOR);
        }});

        nameBox.setEditable(true);
        nameBox.setPreferredSize(new Dimension(250, 25));
        namePanel.add(nameBox);
        namePanel.add(searchButton);
        namePanel.add(filterButton);

        //center
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());

        topContainer.add(titleLabel);
        topContainer.add(namePanel);
        topContainer.add(filterPanel);


        model = new DefaultTableModel(COLUMN, 0) { //made the table in non-editable mode
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentPaymentTable = new JTable(model);
        studentPaymentTable.getColumnModel().getColumn(0).setMinWidth(0);
        studentPaymentTable.getColumnModel().getColumn(0).setMaxWidth(0);
        studentPaymentTable.getColumnModel().getColumn(0).setWidth(0);
        studentPaymentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //support horizontal scroll
        //can only allow to select row but not column
        studentPaymentTable.setRowSelectionAllowed(true);
        studentPaymentTable.setColumnSelectionAllowed(false);
        studentPaymentTable.setRowHeight(25);
        studentPaymentTable.getTableHeader().setReorderingAllowed(false); //cannot modify the title
        studentPaymentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //table design
        studentPaymentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentPaymentTable.setBackground(Color.WHITE);
        studentPaymentTable.setForeground(Color.DARK_GRAY);
        studentPaymentTable.setGridColor(new Color(200, 200, 200));
        studentPaymentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        studentPaymentTable.getTableHeader().setBackground(new Color(220, 230, 240));


        // putting object to table
        List<Student> students = Registration.loadStudentFromFile();

        Map<String, Student> studentMap = new HashMap<>();
        for (Student s : students) {
            studentMap.put(s.getUsername(), s);
        }
        for (Payment p : Financial.getAllPayment()) {
            Student s = studentMap.get(p.getUsername());
            //if (p == null) continue;
            String[] row = {
                    s.getUsername(),
                    s.getName(),
                    p.getSubjects(),
                    p.getDate(),
                    p.getPaymentMethod(),
                    p.getStatus()
            };
            model.addRow(row);
        }


        JScrollPane scrollPane = new JScrollPane(
                studentPaymentTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );


        //bottom
        bottomContainer = new BottomContainer(
                "Home",   new Color(58, 91, 204), this,
                "Cancel", new Color(158, 158, 158), this,
                "Accept Payment", new Color(76, 175, 80), this
        );


        // binding listener
        bottomContainer.rightButton1.setEnabled(false);
        studentPaymentTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = studentPaymentTable.getSelectedRow() != -1;
            bottomContainer.rightButton1.setEnabled(rowSelected);
        });
        searchButton.addActionListener(this);
        filterButton.addActionListener(this);
        filterPanel.resetButton.addActionListener(this);
        filterPanel.levelBox.addActionListener(e -> filterStudentList());
        filterPanel.monthBox.addActionListener(e -> filterStudentList());
        filterPanel.paymentMethodBox.addActionListener(e -> filterStudentList());
        filterPanel.paymentStatusBox.addActionListener(e -> filterStudentList());

        topContainer.setBackground(FORM_BG_COLOR);
        namePanel.setOpaque(false);
        filterPanel.setBackground(FORM_BG_COLOR);
        scrollPane.getViewport().setBackground(Color.WHITE);      // 表格白底

        add(topContainer, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomContainer, BorderLayout.SOUTH);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bottomContainer.leftButton) {
            home.showCard("home");
        } else if (e.getSource() == bottomContainer.rightButton1) {
            studentPaymentTable.clearSelection();
        } else if (e.getSource() == filterButton) {
            filterPanel.setVisible(!filterPanel.isVisible());
            filterPanel.revalidate();
            filterPanel.repaint();
            filterButton.setText(filterPanel.isVisible() ? "Advanced Filter ▲" : "Advanced Filter ▼");
        } else if (e.getSource() == filterPanel.resetButton) {
            filterPanel.levelBox.setSelectedIndex(0);
            filterPanel.monthBox.setSelectedIndex(0);
            filterPanel.paymentMethodBox.setSelectedIndex(0);
            filterPanel.paymentStatusBox.setSelectedIndex(0);
        } else if (e.getSource() == bottomContainer.rightButton2) {
            UpdatePayment();
            PageRefresh.refreshIncomeReport();
        } else if (e.getSource() == searchButton) {
            String searchName = (String) nameBox.getSelectedItem();
            if (ValidUser.isEmpty(searchName)) {
                JOptionPane.showMessageDialog(this,
                        "Name cannot be empty!",
                        "Form error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Student> students = Registration.loadStudentFromFile();
            List<Payment> payments = Financial.getAllPayment();
            List<Enrolment> enrolments = Registration.loadEnrolment();

            Map<String, List<Payment>> paymentMap = new HashMap<>();
            Map<String, Enrolment> enrolmentMap = new HashMap<>();

            for (Enrolment en : enrolments) {
                enrolmentMap.put(en.getUsername(), en);
            }
            for (Payment p : payments) {
                paymentMap.computeIfAbsent(p.getUsername(), k -> new ArrayList<>()).add(p);
            }

            model.setRowCount(0);
            //putting match student in table
            for (Student s : students) {
                if (s.getName().equalsIgnoreCase(searchName)){
                    List<Payment> paylist = paymentMap.get(s.getUsername());

                    if (paylist == null) {
                        JOptionPane.showMessageDialog(this,
                                "This student has no payment records.",
                                "No Payment Found",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    for (Payment p : paylist) {
                        String[] row = {
                                s.getUsername(),
                                s.getName(),
                                p.getSubjects(),
                                p.getDate(),
                                p.getPaymentMethod(),
                                p.getStatus()
                        };
                        model.addRow(row);
                    }
                }
            }
        }
    }
    
    private void filterStudentList() {
        String selectedLevel = (String) filterPanel.levelBox.getSelectedItem();
        String selectedMonth = (String) filterPanel.monthBox.getSelectedItem();
        String selectedMethod = (String) filterPanel.paymentMethodBox.getSelectedItem();
        String selectedStatus = (String) filterPanel.paymentStatusBox.getSelectedItem();

        if (selectedLevel.equals("")) selectedLevel = null;
        if (selectedMonth.equals("")) selectedMonth = null;
        if (selectedMethod.equals("")) selectedMethod = null;
        if (selectedStatus.equals("")) selectedStatus = null;

        List<Student> students = Registration.loadStudentFromFile();
        List<Payment> payments = Financial.getAllPayment();
        List<Enrolment> enrolments = Registration.loadEnrolment();
        List<String> filterNames = new ArrayList<>();

        //this map allow store multiple payment
        Map<String, List<Payment>> paymentMap = new HashMap<>();
        Map<String, Enrolment> enrolmentMap = new HashMap<>();
        for (Enrolment e : enrolments) {
            enrolmentMap.put(e.getUsername(), e);
        }
        for (Payment p : payments) {
            paymentMap.computeIfAbsent(p.getUsername(), e -> new ArrayList<>()).add(p);
        }

        model.setRowCount(0);
        for (Student s : students) {

            List<Payment> paylist = paymentMap.get(s.getUsername());
            Enrolment e = enrolmentMap.get(s.getUsername());
            if (paylist == null) continue;

            boolean matchLevel = (selectedLevel == null) || selectedLevel.equalsIgnoreCase(s.getLevel());
            boolean matchMonth = (selectedMonth == null) || selectedMonth.equalsIgnoreCase(e.getMoE());

            for (Payment p : paylist) {
                boolean matchMethod = (selectedMethod == null) || selectedMethod.equalsIgnoreCase(p.getPaymentMethod());
                boolean matchStatus = (selectedStatus == null) || selectedStatus.equalsIgnoreCase(p.getStatus());
                if (matchLevel && matchMonth && matchMethod && matchStatus) {
                    //add student box
                    filterNames.add(s.getName());
                    String[] row = {
                            s.getUsername(),
                            s.getName(),
                            p.getSubjects(),
                            p.getDate(),
                            p.getPaymentMethod(),
                            p.getStatus()
                    };
                    model.addRow(row);
                }
            }


        }
        if (filterNames.isEmpty()) {
            nameBox.setModel(new DefaultComboBoxModel<>(new String[]{"No matching students"}));
        } else {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            model.addElement("");
            //using set to prevent same name appear on nameBox
            //1.set will calculate the hashcode is it same
            //2. set will use equals to check if its true equals
            Set<String> addedNames = new HashSet<>();
            for (String name : filterNames) {
                if (addedNames.contains(name)) continue;
                model.addElement(name);
                addedNames.add(name);
            }
            nameBox.setModel(model);
        }
    }

    private void UpdatePayment() {
        int SelectedRow = studentPaymentTable.getSelectedRow();
        if (SelectedRow == -1) return;

        String status = studentPaymentTable.getValueAt(SelectedRow, 5).toString();
        if (status.equals("paid")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cannot update payment. Status: paid.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String username = studentPaymentTable.getValueAt(SelectedRow, 0).toString();
        String subject = studentPaymentTable.getValueAt(SelectedRow, 2).toString();

        Map<String, Student> studentMap = new HashMap<>();
        for (Student s : Registration.loadStudentFromFile()) {
            studentMap.put(s.getUsername(), s);
        }
        Student matchedStudent = studentMap.get(username);

        // Load all payments and find exact match (by username + subject)
        Payment matchedPayment = null;
        for (Payment p : Financial.getAllPayment()) {
            if (p.getUsername().trim().equalsIgnoreCase(username.trim()) &&
                    p.getSubjects().trim().equalsIgnoreCase(subject.trim())) {
                matchedPayment = p;
                break;
            }
        }

        if (matchedPayment == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Payment record not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to update payment status of student \"" + matchedStudent.getName() + "\"?\nThis action cannot be undone.",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            matchedPayment.setStatus("paid");
            Financial.updatePaymentStatus(matchedPayment);
            JOptionPane.showMessageDialog(this, "Student " + matchedStudent.getName() + " update payment successfully.");
            ((ReceptionistDashboard) SwingUtilities.getWindowAncestor(this)).refreshAllPanels();
        }
    }

    public void refreshStudentTable() {
        model.setRowCount(0);

        List<Student> students = Registration.loadStudentFromFile();
        List<Payment> payments = Financial.getAllPayment();

        Map<String, Student> studentMap = new HashMap<>();
        for (Student s : students) {
            studentMap.put(s.getUsername(), s);
        }

        for (Payment p : payments) {
            Student s = studentMap.get(p.getUsername());
            if (s == null) continue;

            String[] row = {
                    s.getUsername(),
                    s.getName(),
                    p.getSubjects(),
                    p.getDate(),
                    p.getPaymentMethod(),
                    p.getStatus()
            };
            model.addRow(row);
        }
    }
    private void setComboBoxBg(JComboBox<?>... boxes) {
        for (JComboBox<?> box : boxes) {
            box.setBackground(FORM_BG_COLOR);
        }
    }

}
