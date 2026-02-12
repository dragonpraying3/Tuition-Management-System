package view.Admin;

import controller.Registration;
import controller.ValidUser;

import javax.swing.*;
import java.awt.*;

public abstract class   RegisterFormTemplate extends JPanel {
    protected JTextField usernameField,fullNameField,ageField,contactField,emailField,icField, passwordField;
    protected JComboBox<String> dateBox,monthBox,yearBox,genderBox,raceBox;
    protected JTextArea addressArea;
    JPanel dobPanel;

    public RegisterFormTemplate() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createField("Username: ", usernameField = new JTextField(17));
        createField("Full Name: ", fullNameField = new JTextField(17));
        createField("NRIC/Passport: ", icField = new JTextField(17));
        createField("Age: ", ageField = new JTextField(17));
        createField("Contact: ", contactField = new JTextField(17));
        createField("Email: ", emailField = new JTextField(17));

        String[] dates = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        dateBox = new JComboBox<>(dates);
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthBox = new JComboBox<>(months);
        String[] years = {"1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006",
                "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"
        };
        yearBox = new JComboBox<>(years);
        dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        dobPanel.add(dateBox);
        dobPanel.add(monthBox);
        dobPanel.add(yearBox);
        createField("Date Of Birth: ", dobPanel);

        String[] genders = {"Male", "Female"};
        genderBox = new JComboBox<>(genders);
        createField("Gender: ", genderBox);

        String[] races = {"Malay", "Chinese Malaysian", "Indian Malaysian", "Indigenous Group"};
        raceBox = new JComboBox<>(races);
        createField("Race: ", raceBox);

        String autoPassword = new String(Registration.PasswordGenerator());
        createField("Auto Password: ", passwordField = new JTextField(autoPassword, 17));
        passwordField.setEditable(false);
        }

        //because left panel is the same, so this all at left panel
    public JPanel getLeftPanel() {
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        left.add(createField("Username: ", usernameField));
        left.add(createField("Full Name: ", fullNameField));
        left.add(createField("NRIC/Passport: ", icField));
        left.add(createField("Gender: ", genderBox));
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        dobPanel.add(dateBox);
        dobPanel.add(monthBox);
        dobPanel.add(yearBox);
        left.add(createField("Date Of Birth: ", dobPanel));
        left.add(createField("Age: ", ageField));
        left.add(createField("Contact: ", contactField));
        left.add(createField("Email: ", emailField));
        return left;
    }

    protected StringBuilder validateCommonField(String username, String fullName, String age,
                                                String contact, String email, String ic, String address) {
        StringBuilder errorMessage = new StringBuilder();

        if (ValidUser.isEmpty(username)) errorMessage.append("- Username is required.\n");
        if (ValidUser.isEmpty(fullName)) errorMessage.append("- Full Name is required.\n");
        if (ValidUser.isEmpty(age)) errorMessage.append("- Age is required.\n");
        if (ValidUser.isEmpty(contact)) errorMessage.append("- Contact is required.\n");
        if (ValidUser.isEmpty(email)) errorMessage.append("- Email is required.\n");
        if (ValidUser.isEmpty(ic)) errorMessage.append("- IC/Passport is required.\n");
        if (ValidUser.isEmpty(address)) errorMessage.append("- Address is required.\n");

        if (!ValidUser.isValidName(fullName)) errorMessage.append("- Full Name format is invalid.\n");
        if (!ValidUser.isValidIcOrPassport(ic)) errorMessage.append("- IC format is invalid.\n");
        if (!ValidUser.isValidEmail(email)) errorMessage.append("- Email format is invalid.\n");
        if (!ValidUser.isValidContact(contact)) errorMessage.append("- Contact number must be 9-11 digits.\n");

        return errorMessage;
    }
    protected JPanel createField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 1));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial",Font.BOLD,14)); //adjust the size of font
        label.setPreferredSize(new Dimension(120, 30)); //adjust size of label

        field.setFont(new Font("Arial",Font.BOLD,14)); //adjust the size of font
        //only adjust the width and height of text field but not combo box
        if (field instanceof JTextField){
            Dimension fieldSize=new Dimension(130,26);
            field.setPreferredSize(fieldSize);
            field.setMaximumSize(fieldSize);
            field.setMinimumSize(fieldSize);
        }

        panel.add(label);
        panel.add(field);
        //adjust the vertical length between column
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        return panel;
    }

    //no open object and because have two child class inherit this class
    protected void clearFields() {
        usernameField.setText("");
        fullNameField.setText("");
        passwordField.setText(new String(Registration.PasswordGenerator()));
        dateBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
        genderBox.setSelectedIndex(0);
        raceBox.setSelectedIndex(0);
        ageField.setText("");
        contactField.setText("");
        emailField.setText("");
        icField.setText("");
        addressArea.setText("");
    }
}
