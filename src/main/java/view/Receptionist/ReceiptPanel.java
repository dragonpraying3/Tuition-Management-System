package view.Receptionist;

import controller.Financial;
import controller.PDFReceiptGenerator;
import controller.Registration;
import controller.TutorController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *search student to generate receipt
 *pending status at payment.txt won't be added into pdf
 *system auto calculate the total amount paid
 *date is the day generate receipt , not the payment date
 */
public class ReceiptPanel extends JPanel implements ActionListener {

    private JComboBox<String> nameBox;
    private JButton searchButton;
    private JPanel receiptDisplayPanel;
    private JLabel receiptInfoLabel;
    private ReceptionistDashboard home;
    private Receipt currentReceipt;
    BottomContainer bottomContainer;
    private static final Color FORM_BG_COLOR = new Color(245, 248, 255);
    private static final Color LABEL_COLOR   = new Color(33, 37, 41);


    public ReceiptPanel(ReceptionistDashboard home) {
        this.home = home;
        this.setBackground(FORM_BG_COLOR);
        this.setLayout(new BorderLayout());

        List<String> studentName = new ArrayList<>();
        studentName.add(0, "");
        for (Student student : Registration.loadStudentFromFile()) {
            studentName.add(student.getName());
        }

        //create UI
        searchButton = new JButton("Search");
        nameBox = new JComboBox<>(studentName.toArray(new String[0]));

        // Top: Search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(FORM_BG_COLOR);
        searchButton.setFocusable(false);
        nameBox.setBackground(FORM_BG_COLOR);
        topPanel.add(new JLabel("Select Student:"){{
            setForeground(LABEL_COLOR);
        }});
        nameBox.setEditable(true);
        nameBox.setPreferredSize(new Dimension(150, 25));

        searchButton.setFocusable(false);
        nameBox.setBackground(FORM_BG_COLOR);
        setComboBoxBg(nameBox);

        topPanel.add(nameBox);
        topPanel.add(searchButton);

        // Center: Receipt display area
        receiptDisplayPanel = new JPanel();
        receiptDisplayPanel.setBackground(FORM_BG_COLOR);
        receiptDisplayPanel.setLayout(new BorderLayout());
        receiptDisplayPanel.setBorder(BorderFactory.createTitledBorder("Receipt Preview"));

        receiptInfoLabel = new JLabel("<html><i>Please search for a student to preview receipt.</i></html>");
        receiptInfoLabel.setForeground(LABEL_COLOR);
        receiptInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        receiptInfoLabel.setVerticalAlignment(SwingConstants.TOP);
        receiptInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        receiptDisplayPanel.add(receiptInfoLabel, BorderLayout.CENTER);

        //bottom
        bottomContainer = new BottomContainer(
                "Home",   new Color(58, 91, 204), this,
                "Reset",  new Color(158, 158, 158), this,
                "Generate PDF", new Color(76, 175, 80), e -> {
            JOptionPane.showMessageDialog(this, "PDF Receipt Generated!");
            PDFReceiptGenerator.generateReceipt(currentReceipt);
        }
        );

        // binding listener
        bottomContainer.rightButton1.setEnabled(false);
        bottomContainer.rightButton2.setEnabled(false);
        searchButton.addActionListener(this);

        add(receiptDisplayPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(bottomContainer, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bottomContainer.leftButton) {
            home.showCard("home");
        } else if (e.getSource() == bottomContainer.rightButton1) {
            nameBox.setSelectedIndex(0);
            bottomContainer.rightButton1.setEnabled(false);
            bottomContainer.rightButton2.setEnabled(false);
            receiptInfoLabel.setText("<html><i>Please search for a student to preview receipt.</i></html>");
        } else if (e.getSource() == searchButton) {
            String selectedStudent = (String) nameBox.getSelectedItem();

            if (selectedStudent == null || selectedStudent.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please select a student, before pressing search button",
                        "Selection Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Map<String, Payment> paymentMap = new HashMap<>();
            Map<String, Student> studentMap = new HashMap<>();
            Map<String, Enrolment> enrolmentMap = new HashMap<>();
            Map<String, Tutor> tutorMap = new HashMap<>();

            for (Payment payment : Financial.getAllPayment()) {
                paymentMap.put(payment.getUsername(), payment);
            }
            for (Student student : Registration.loadStudentFromFile()) {
                studentMap.put(student.getUsername(), student);
            }
            for (Enrolment enrolment : Registration.loadEnrolment()) {
                enrolmentMap.put(enrolment.getUsername(), enrolment);
            }
            for (Tutor tutor : Registration.getAllTutor()) {
                tutorMap.put(tutor.getUsername(), tutor);
            }

            Payment matchPayment = null;
            Student matchStudent = null;
            Enrolment matchEnrolment = null;

            for (Student student : Registration.loadStudentFromFile()) {
                if (selectedStudent.equals(student.getName())) {
                    matchStudent = studentMap.get(student.getUsername());
                    matchPayment = paymentMap.get(student.getUsername());
                    matchEnrolment = enrolmentMap.get(student.getUsername());
                    break;
                }
            }
            String username = matchStudent.getUsername();

            String subject1TutorUsername = matchEnrolment.getSubject1Tutor();
            String subject2TutorUsername = matchEnrolment.getSubject2Tutor();
            String subject3TutorUsername = matchEnrolment.getSubject3Tutor();

            Tutor t1 = tutorMap.get(subject1TutorUsername);
            Tutor t2 = tutorMap.get(subject2TutorUsername);
            Tutor t3 = tutorMap.get(subject3TutorUsername);


            if (matchPayment == null) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ This student has not made any payment yet.\nCannot generate receipt.",
                        "No Payment Found",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            LocalDate date = LocalDate.now();
            currentReceipt = new Receipt();
            currentReceipt.setReceiptNo(PDFReceiptGenerator.nextReceiptNo());
            currentReceipt.setDate(String.valueOf(date));
            currentReceipt.setPaymentMethod(matchPayment.getPaymentMethod());
            currentReceipt.setStudentName(matchStudent.getName());
            currentReceipt.setStudentLevel(matchStudent.getLevel());
            currentReceipt.setEnrolmentMonth(matchEnrolment.getMoE());
            currentReceipt.setSubject1(matchEnrolment.getSubject1());
            currentReceipt.setSubject2(matchEnrolment.getSubject2());
            currentReceipt.setSubject3(matchEnrolment.getSubject3());
            currentReceipt.setTutor1((t1 != null) ? t1.getFullName() : "-");
            currentReceipt.setTutor2((t2 != null) ? t2.getFullName() : "-");
            currentReceipt.setTutor3((t3 != null) ? t3.getFullName() : "-");

            int amount1 = (t1 != null) ? TutorController.getSubjectFee(t1.getUsername(), currentReceipt.getSubject1()) : 0;
            int amount2 = (t2 != null) ? TutorController.getSubjectFee(t2.getUsername(), currentReceipt.getSubject2()) : 0;
            int amount3 = (t3 != null) ? TutorController.getSubjectFee(t3.getUsername(), currentReceipt.getSubject3()) : 0;

            currentReceipt.setAmount1(Integer.toString(amount1));
            currentReceipt.setAmount2(Integer.toString(amount2));
            currentReceipt.setAmount3(Integer.toString(amount3));

            currentReceipt.setTotalAmount(Integer.toString(amount1 + amount2 + amount3));

            List<Payment> allPayment = Financial.getAllPayment();
            List<Payment> paidPayment = allPayment.stream()
                    .filter(payment -> payment.getUsername().equals(username))
                    .filter(payment -> payment.getStatus().equals("paid"))
                    .toList();

            String receipt = buildReceipt(currentReceipt, paidPayment, matchEnrolment);
            bottomContainer.rightButton1.setEnabled(true);
            bottomContainer.rightButton2.setEnabled(true);
            receiptInfoLabel.setText(receipt);
        }
    }


    private String buildReceipt(Receipt receipt, List<Payment> paidPayments, Enrolment enrolment) {
        StringBuilder sb = new StringBuilder();
        /*
          Begin the HTML document and apply general font and text color.
          - font-family: Segoe UI for modern appearance
          - color: #212529 is a dark gray (almost black) for body text
         */
        sb.append("<html><body style='font-family:Segoe UI; color:#212529;'>");

        /*
          Header section of the receipt
          - div: block-level container
          - text-align: center the title horizontally
          - color: blue-purple (#3f51b5) for the title
          - font-size: 20px to make the header stand out
          - font-weight: bold text
          - margin-bottom: spacing below the title
         */

        sb.append("<div style='text-align:center; color:#3f51b5; font-size:20px; font-weight:bold; margin-bottom:10px;'>");
        sb.append("TUITION CENTRE PAYMENT RECEIPT");
        sb.append("</div>");

        /*
          Table for general receipt details (metadata)
          - font-size: 12px for compact info
          - line-height: 1.6 for spacing between rows
         */

        sb.append("<table style='font-size:12px; line-height:1.6;'>");
        sb.append(String.format("<tr><td><b>Receipt No.</b></td><td>: %s</td></tr>", receipt.getReceiptNo()));
        sb.append(String.format("<tr><td><b>Date</b></td><td>: %s</td></tr>", receipt.getDate()));
        sb.append(String.format("<tr><td><b>Payment Method</b></td><td>: %s</td></tr>", receipt.getPaymentMethod()));
        sb.append(String.format("<tr><td><b>Student Name</b></td><td>: %s</td></tr>", receipt.getStudentName()));
        sb.append(String.format("<tr><td><b>Level</b></td><td>: %s</td></tr>", receipt.getStudentLevel()));
        sb.append(String.format("<tr><td><b>Enrolment Month</b></td><td>: %s</td></tr>", receipt.getDate()));
        sb.append("</table><br>");

        /*
          Section title for subjects list
          - font-size: 12px for label text
          - font-weight: bold to highlight the section
          - margin-top/bottom: space above and below
          - border-bottom: thin grey line separator
         */

        sb.append("<div style='font-size:12px; font-weight:bold; margin-top:10px; margin-bottom:5px; border-bottom:1px solid #ccc;'>Subjects Enrolled:</div>");

        // fullName -> username map
        Map<String, String> tutorNameToUsername = new HashMap<>();
        for (Tutor tutor : Registration.getAllTutor()) {
            tutorNameToUsername.put(tutor.getUsername(), tutor.getFullName());
        }

        int total = 0;

        /*
          Unordered list of enrolled subjects and fees
          - margin-left: indent the list
          - font-size: 16px to make subjects clearly readable
          - each <li>: one subject entry
            - tutor name in grey (#555)
            - fee bolded (RM xxx)
         */
        sb.append("<ul style='margin-left:20px; font-size:16px;'>");
        for (Payment p : paidPayments) {
            String subject = p.getSubjects();
            String tutorUsername = enrolment.getTutorBySubject(subject);
            String tutorFullName = tutorNameToUsername.get(tutorUsername);
            int fee = TutorController.getSubjectFee(tutorUsername, subject);
            total += fee;

            sb.append(String.format("<li>%s <span style='color:#555;'>(%s)</span>: <b>RM %d</b></li>",
                    subject, tutorFullName, fee));
        }
        sb.append("</ul>");

        /*
          Display total amount and payment status
          - total amount: highlighted in green, slightly larger
          - payment status: also green, indicating success
         */
        sb.append(String.format("<br><b>Total Amount Paid:</b> <span style='color:green; font-size:17px;'>RM %d</span><br>", total));
        sb.append("<b>Payment Status:</b> <span style='color:green; font-size:17px;'>PAID</span><br>");

        //close the html tags
        sb.append("</body></html>");
        return sb.toString();
    }

    private void setComboBoxBg(JComboBox<?>... boxes) {
        for (JComboBox<?> box : boxes) {
            box.setBackground(FORM_BG_COLOR);
        }
    }

    public void refreshStudentComboBox() {
        nameBox.removeAllItems();
        nameBox.addItem("");
        for (Student student : Registration.loadStudentFromFile()) {
            nameBox.addItem(student.getName());
        }
    }

}
