package view.Admin;

import controller.Registration;
import model.Tutor;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class RegisterTutorForm  extends RegisterFormTemplate{
    private final JComboBox<String> studyBox,subjectBox1,subjectBox2,subjectBox3;
    private final JPanel titlePanel,rightPanel,formWrapper,ButtonPanel,formRow,leftButton,rightButton;
    private final String autoPassword;
    JButton registerButton,cancelButton,resetButton;
    JLabel titleLabel;
    JScrollPane scrollPane;
    Font buttonFont;

    public RegisterTutorForm(AdminView parent) {
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        buttonFont=new Font("Rockwell",Font.BOLD,15);

        //title
        titlePanel=new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel=new JLabel("Tutor Registration Form",SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titlePanel.add(titleLabel);
        this.add(titlePanel);

        //right panel
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        autoPassword=new String(Registration.PasswordGenerator());
        rightPanel.add(createField("Auto Password: ",passwordField=new JTextField(autoPassword,17)));
        passwordField.setEditable(false);

        //address text area
        addressArea = new JTextArea(4, 20);
        addressArea.setLineWrap(true);//ensure text wrap to next line in vision view area
        addressArea.setWrapStyleWord(true); //not split become address even in different line
        addressArea.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane = new JScrollPane(addressArea);//can scroll the area
        scrollPane.setPreferredSize(new Dimension(230, 60)); //set the visible size of the scroll pane;
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10,Integer.MAX_VALUE));
        rightPanel.add(createField("Address: ", scrollPane));

        //qualification combo box
        String[] studies ={"SPM/STPM Level","Postgraduate Diploma in Education", "Bachelor's in Education",
                "Bachelor's in Science/Arts", "Master's Degree", "PhD", "Teaching Certification ", "TESL/TEFL Certification"};
        studyBox=new JComboBox<>(studies);
        studyBox.setPreferredSize(new Dimension(230,30));
        rightPanel.add(createField("Qualification: ",studyBox));

        //subject combo box
        String[] subjects={"No","Bahasa Melayu", "English","Chinese Language", "Mathematics", "Additional Mathematics", "Science",
                "Physics", "Chemistry", "Biology", "History", "Geography", "Moral Education", "Pendidikan Islam",
                "Economics", "Business Studies", "Accounting"};
        subjectBox1=new JComboBox<>(subjects);
        subjectBox2=new JComboBox<>(subjects);
        subjectBox3=new JComboBox<>(subjects);
        rightPanel.add(createField("Subject 1: ",subjectBox1));
        rightPanel.add(createField("Subject 2: ",subjectBox2));
        rightPanel.add(createField("Subject 3: ",subjectBox3));

        //combine panel
        formRow = new JPanel();
        formRow.setLayout(new BoxLayout(formRow, BoxLayout.X_AXIS));
        formRow.add(getLeftPanel()); // add inherited fields (this RegisterFormTemplate itself is a panel)
        formRow.add(Box.createRigidArea(new Dimension(40, 0))); //gap between left and right panel
        formRow.add(rightPanel);

        formWrapper = new JPanel(new GridBagLayout());
        formWrapper.add(formRow);
        this.add(formWrapper);

        //button panel
        ButtonPanel=new JPanel(new BorderLayout());
        leftButton=new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        rightButton=new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

        resetButton=new JButton("Reset");
        registerButton=new JButton("Register");
        cancelButton=new JButton("Back");

        //design button
        resetButton.setPreferredSize(new Dimension(90,40));
        resetButton.setBackground(new Color(255, 140, 0));
        resetButton.setForeground(new Color(255,255,255));
        formatButton(resetButton);

        registerButton.setPreferredSize(new Dimension(100,40));
        registerButton.setBackground(new Color(26, 162, 96));
        registerButton.setForeground(new Color(255,255,255));
        formatButton(registerButton);

        cancelButton.setPreferredSize(new Dimension(90,40));
        cancelButton.setBackground(new Color(168, 169, 173));
        cancelButton.setForeground(new Color(255,255,255));
        formatButton(cancelButton);

        leftButton.add(cancelButton);
        rightButton.add(registerButton);
        rightButton.add(resetButton);

        ButtonPanel.add(leftButton,BorderLayout.WEST);
        ButtonPanel.add(rightButton,BorderLayout.EAST);
        this.add(ButtonPanel,BorderLayout.SOUTH);

        AdminView.changeColour(this);

        registerButton.addActionListener(e-> {
            String username = usernameField.getText();
            String fullName = fullNameField.getText();
            String dob =  dateBox.getSelectedItem() +"-"+monthBox.getSelectedItem()+"-"+yearBox.getSelectedItem();
            String age = ageField.getText();
            String gender = (String) genderBox.getSelectedItem();
            String contact = contactField.getText();
            String email = emailField.getText();
            String race = (String) raceBox.getSelectedItem();
            String ic = icField.getText();
            String address = addressArea.getText();
            String qualification = (String) studyBox.getSelectedItem();
            String subject1 = (String) subjectBox1.getSelectedItem();
            String subject2 = (String) subjectBox2.getSelectedItem();
            String subject3 = (String) subjectBox3.getSelectedItem();

            Tutor tutor = new Tutor(username,autoPassword, fullName, dob, age, gender, contact,
                    email, race, ic, address, qualification, subject1,subject2,subject3);

            StringBuilder errorMessage = validateCommonField(username, fullName, age, contact, email, ic, address);
            if (subject1.equals("No")) {
                errorMessage.append("- Subject 1 cannot be 'No'\n");
            }

            Set<String> checkDuplicate=new HashSet<>();
            String[] subject={
                    ((String)subjectBox1.getSelectedItem()),
                    ((String)subjectBox2.getSelectedItem()),
                    ((String)subjectBox3.getSelectedItem())};
            for (String sub:subject){
                if (!sub.equals("No")){
                    if (!checkDuplicate.add(sub)){
                        JOptionPane.showMessageDialog(this,"Subjects cannot be the same!","Duplicate Subject",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }

            if (!errorMessage.isEmpty()) {
                JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Errors", JOptionPane.ERROR_MESSAGE);
            } else {
                String result = Registration.RegisterTutor(tutor);
                String result2=Registration.CopyTutorUsername(username);
                if (result.equals("Success") && result2.equals("Success")) {
                    JOptionPane.showMessageDialog(this, "Tutor "+fullName+" registered successfully.");
                    TutorView tutorView=parent.getTutorView();; //refresh the table
                    AssignTutorPage assignPage=parent.getAssignTutorPage(); //refresh the comboBox
                    ViewAssignment viewAssignment=parent.getViewAssignment(); //refresh table
                    if (tutorView!=null){
                        tutorView.refreshTable();
                    }
                    if (assignPage!=null){
                        assignPage.refreshTutorBox();
                    }
                    if (viewAssignment != null){
                        viewAssignment.refreshTable();
                    }
                    parent.refreshCount(); //refresh the counting number
                    parent.showCard("Home");
                } else {
                    JOptionPane.showMessageDialog(this, result, "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
                clear();
            }
        });

        cancelButton.addActionListener(e -> {
            parent.showCard("Home");
            clearFields();
            clear();
        });

        resetButton.addActionListener(e->{
            clearFields();
            clear();
        });
    }
    private void formatButton(JButton button){
        button.setFont(buttonFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    protected void clear() {
        studyBox.setSelectedIndex(0);
        subjectBox1.setSelectedIndex(0);
        subjectBox2.setSelectedIndex(0);
        subjectBox3.setSelectedIndex(0);
    }
}
