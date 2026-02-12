//package view;
//
//import controller.Registration;
//import controller.ValidUser;
//import model.Tutor;
//import view.Admin.AdminView;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class RegisterTutorForm extends JPanel{
//    private final JTextField usernameField,fullNameField,ageField,contactField,emailField,icField, passwordField;
//    JComboBox<String> dateBox,monthBox,yearBox,genderBox,raceBox,studyBox,subjectBox1,subjectBox2,subjectBox3;
//    JPanel titlePanel,formPanel,leftPanel,rightPanel,dobPanel,formWrapper,ButtonPanel,panel;
//    JButton registerButton,cancelButton,resetButton;
//    JLabel titleLabel,label;
//    JTextArea addressArea;
//    JScrollPane scrollPane;
//
//    public RegisterTutorForm(AdminView parent) {
//        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
//
//        //title
//        titlePanel=new JPanel();
//        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
//        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
//        titleLabel=new JLabel("Tutor Registration Form",SwingConstants.CENTER);
//        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
//        titlePanel.add(titleLabel);
//        this.add(titlePanel);
//
//        //main form panel with two columns
//        formPanel=new JPanel();
//        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.X_AXIS));
//
//        //left and right
//        leftPanel=new JPanel();
//        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
//        rightPanel=new JPanel();
//        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
//
//        //left panel field
//        leftPanel.add(createField("Username: ",usernameField=new JTextField(17)));
//        leftPanel.add(createField("Full Name: ",fullNameField=new JTextField(17)));
//
//        //date combo box
//        String[] dates = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
//                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
//        dateBox=new JComboBox<>(dates);
//        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sup", "Oct", "Nov", "Dec"};
//        monthBox=new JComboBox<>(months);
//        String[] years = {"1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006",
//                "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"
//        };
//        yearBox=new JComboBox<>(years);
//        dobPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
//        dobPanel.add(dateBox);
//        dobPanel.add(monthBox);
//        dobPanel.add(yearBox);
//        leftPanel.add(createField("Date Of Birth: ",dobPanel));
//
//        leftPanel.add(createField("NRIC/Passport: ",icField=new JTextField(17)));
//        leftPanel.add(createField("Age: ",ageField=new JTextField(17)));
//
//        //gender combo box
//        String[] genders = {"Male", "Female"};
//        genderBox=new JComboBox<>(genders);
//        leftPanel.add(createField("Gender: ",genderBox));
//        leftPanel.add(createField("Contact: ",contactField=new JTextField(17)));
//        leftPanel.add(createField("Email: ",emailField=new JTextField(17)));
//
//        //right panel field
//        String autoPassword=new String(Registration.PasswordGenerator());
//        rightPanel.add(createField("Auto Password: ",passwordField=new JTextField(autoPassword,17)));
//        passwordField.setEditable(false);
//
//        //race combo box
//        String[] races = {"Malay", "Chinese Malaysian", "Indian Malaysian", "Indigenous Group"};
//        raceBox=new JComboBox<>(races);
//        rightPanel.add(createField("Race: ",raceBox));
//
//        //address area box
//        addressArea =new JTextArea(3,20);
//        addressArea.setLineWrap(true);//ensure text wrap to next line in vision view area
//        addressArea.setWrapStyleWord(true); //not split become add-ress even in different line
//        addressArea.setFont(new Font("Arial", Font.PLAIN, 14));
//        scrollPane =new JScrollPane(addressArea);//can scroll the area
//        scrollPane.setPreferredSize(new Dimension(200, 60)); //set the visible size of the scroll pane
//        rightPanel.add(createField("Address: ",scrollPane));
//
//        //qualification combo box
//        String[] studies ={"Diploma in Education", "Bachelor's in Education", "Bachelor's in Science/Arts",
//                "Master's Degree", "PhD", "Teaching Certification ", "TESL/TEFL Certification"};
//        studyBox=new JComboBox<>(studies);
//        rightPanel.add(createField("Qualification: ",studyBox));
//
//        //subject combo box
//        String[] subjects={"No","Bahasa Melayu", "English", "Mathematics", "Additional Mathematics", "Science",
//                "Physics", "Chemistry", "Biology", "History", "Geography", "Moral Education", "Pendidikan Islam",
//                "Economics", "Business Studies", "Accounting"};
//        subjectBox1=new JComboBox<>(subjects);
//        subjectBox2=new JComboBox<>(subjects);
//        subjectBox3=new JComboBox<>(subjects);
//        rightPanel.add(createField("Subject 1: ",subjectBox1));
//        rightPanel.add(createField("Subject 2: ",subjectBox2));
//        rightPanel.add(createField("Subject 3: ",subjectBox3));
//
//        formPanel.add(leftPanel);
//        formPanel.add(Box.createRigidArea(new Dimension(40, 0))); //gap between left and right
//        formPanel.add(rightPanel);
//
//        formWrapper = new JPanel(new GridBagLayout());
//        //the gap outside the border
//        formWrapper.add(formPanel);
//        this.add(formWrapper);
//
//        ButtonPanel=new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
//        resetButton=new JButton("Reset");
//        registerButton=new JButton("Register");
//        cancelButton=new JButton("Cancel");
//        resetButton.setPreferredSize(new Dimension(90,40));
//        registerButton.setPreferredSize(new Dimension(90,40));
//        cancelButton.setPreferredSize(new Dimension(90,40));
//        ButtonPanel.add(resetButton);
//        ButtonPanel.add(registerButton);
//        ButtonPanel.add(cancelButton);
//        this.add(ButtonPanel);
//
//        registerButton.addActionListener(e-> {
//            String username = usernameField.getText();
//            String fullName = fullNameField.getText();
//            String dob =  dateBox.getSelectedItem() +"-"+monthBox.getSelectedItem()+"-"+yearBox.getSelectedItem();
//            String age = ageField.getText();
//            String gender = (String) genderBox.getSelectedItem();
//            String contact = contactField.getText();
//            String email = emailField.getText();
//            String race = (String) raceBox.getSelectedItem();
//            String ic = icField.getText();
//            String address = addressArea.getText();
//            String qualification = (String) studyBox.getSelectedItem();
//            String subject1 = (String) subjectBox1.getSelectedItem();
//            String subject2 = (String) subjectBox2.getSelectedItem();
//            String subject3 = (String) subjectBox3.getSelectedItem();
//
//            Tutor tutor = new Tutor(username, autoPassword, fullName, dob, age, gender, contact,
//                    email, race, ic, address, qualification, subject1,subject2,subject3);
//
//            StringBuilder errorMessage=new StringBuilder();
//            //not only show the first message
//            if (ValidUser.isEmpty(username)) errorMessage.append("- Username is required.\n");
//            if (ValidUser.isEmpty(fullName)) errorMessage.append("- Full Name is required.\n");
//            if (ValidUser.isEmpty(age)) errorMessage.append("- Age is required.\n");
//            if (ValidUser.isEmpty(contact)) errorMessage.append("- Contact is required.\n");
//            if (ValidUser.isEmpty(email)) errorMessage.append("- Email is required.\n");
//            if (ValidUser.isEmpty(ic)) errorMessage.append("- IC/Passport is required.\n");
//            if (ValidUser.isEmpty(address)) errorMessage.append("- Address is required.\n");
//            if (subject1.equals("No")) {
//                errorMessage.append("- Subject 1 cannot be 'NO' \n"); //check
//            }
//            if (!ValidUser.isValidName(fullName)) errorMessage.append("- Full Name format is invalid.\n");
//            if (!ValidUser.isValidIcOrPassport(ic)) errorMessage.append("- IC format is invalid.\n");
//            if (!ValidUser.isValidEmail(email)) errorMessage.append("- Email format is invalid.\n");
//            if (!ValidUser.isValidContact(contact)) errorMessage.append("- Contact number must be 9-11 digits.\n");
//
//            if (!errorMessage.isEmpty()) {
//                JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Errors", JOptionPane.ERROR_MESSAGE);
//            } else {
//                String result = Registration.RegisterTutor(tutor);
//                if (result.equals("Success")) {
//                    JOptionPane.showMessageDialog(this, "Tutor "+fullName+" registered successfully.");
//                    parent.showCard("Home");
//                } else {
//                    JOptionPane.showMessageDialog(this, result, "Registration Error", JOptionPane.ERROR_MESSAGE);
//                }
//                clearFields();
//            }
//        });
//
//        cancelButton.addActionListener(e -> {
//            parent.showCard("Home");
//            clearFields();
//        });
//
//        resetButton.addActionListener(e->{
//            clearFields();
//        });
//    }
//    private JPanel createField(String labelText, JComponent field) {
//        panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 1));
//        label = new JLabel(labelText);
//        label.setFont(new Font("Arial",Font.BOLD,14)); //adjust the size of font
//        label.setPreferredSize(new Dimension(120, 30)); //adjust size of label
//
//        field.setFont(new Font("Arial",Font.BOLD,14)); //adjust the size of font
//        //only adjust the width and height of textfield but not combo box
//        if (field instanceof JTextField){
//            Dimension fieldSize=new Dimension(130,26);
//            field.setPreferredSize(fieldSize);
//            field.setMaximumSize(fieldSize);
//            field.setMinimumSize(fieldSize);
//        }
//
//        panel.add(label);
//        panel.add(field);
//        //adjust the vertical length between column
//        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
//        return panel;
//    }
//    private void clearFields() {
//        usernameField.setText("");
//        fullNameField.setText("");
//        passwordField.setText(new String(Registration.PasswordGenerator()));
//        ageField.setText("");
//        contactField.setText("");
//        emailField.setText("");
//        icField.setText("");
//        addressArea.setText("");
//    }
//}
