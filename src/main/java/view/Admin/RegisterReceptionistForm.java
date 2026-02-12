package view.Admin;

import controller.Registration;
import model.Receptionist;

import javax.swing.*;
import java.awt.*;

public class RegisterReceptionistForm extends RegisterFormTemplate {
    private final JComboBox<String> jobBox;
    private final JPanel titlePanel,rightPanel,formWrapper,ButtonPanel,workPanel,leftButton,rightButton,formRow;
    JTextField salaryField;
    JButton registerButton,cancelButton,resetButton;
    private final String autoPassword;
    JLabel titleLabel;
    JScrollPane scrollPane;
    JRadioButton FWork,PWork;
    ButtonGroup group;
    Font buttonFont;

    public RegisterReceptionistForm(AdminView parent) {
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        buttonFont=new Font("Rockwell",Font.BOLD,15);

        //title
        titlePanel=new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel=new JLabel("Receptionist Registration Form",SwingConstants.CENTER);
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

        //salary field
        salaryField=new JTextField(17);
        rightPanel.add(createField("Salary (RM): ", salaryField));

        //address field
        addressArea = new JTextArea(4, 20);
        addressArea.setLineWrap(true);//ensure text wrap to next line in vision view area
        addressArea.setWrapStyleWord(true); //not split become add-ress even in different line
        addressArea.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane = new JScrollPane(addressArea);//can scroll the area
        scrollPane.setPreferredSize(new Dimension(230, 60)); //set the visible size of the scroll pane;
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10,Integer.MAX_VALUE));
        rightPanel.add(createField("Address: ", scrollPane));

        //work status check box
        FWork=new JRadioButton("Full-Time");
        PWork=new JRadioButton("Part-Time");
        group=new ButtonGroup(); //create a button group so user only can select one
        group.add(FWork);
        group.add(PWork);
        workPanel=new JPanel(); //add into panel to fill the two button
        workPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        workPanel.add(FWork);
        workPanel.add(PWork);
        rightPanel.add(createField("Working Status:", workPanel));

        //work shift combo box
        String[] works ={"Day Shift","Afternoon Shift","Night Shift"};
        jobBox=new JComboBox<>(works);
        rightPanel.add(createField("Work Shift  : ",jobBox));

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
            String salary= salaryField.getText();
            String workShift = (String) jobBox.getSelectedItem();
            String workStatus;
            if (FWork.isSelected()){
                workStatus="Full-Time";
            }else if (PWork.isSelected()){
                workStatus="Part-Time";
            }else{
                workStatus="";
            }

            Receptionist receptionist = new Receptionist(username, autoPassword, fullName, dob, age, gender, contact,
                    email, race, ic, address, salary, workShift, workStatus);

            StringBuilder errorMessage = validateCommonField(username, fullName, age, contact, email, ic, address);
            if (workStatus.isEmpty()) {
                errorMessage.append("- Work Status cannot be empty\n");
            }
            if (salary.isEmpty()) {
                errorMessage.append("- Salary cannot be empty\n");
            }
            /**
             * d+: check at least one number
             * ( : catch decimal
             * \\. : check the . between salary
             * \\d{1,2} : the two decimal point
             * )? : can have decimal / no have
              */

            if (!salary.matches("\\d+(\\.\\d{1,2})?")) {
                errorMessage.append("- Salary must be a valid number\n");
            }

            if (!errorMessage.isEmpty()) {
                JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Errors", JOptionPane.ERROR_MESSAGE);
            } else {
                String result = Registration.RegisterReceptionist(receptionist); //write into the file
                if (result.equals("Success")) {
                    ReceptionistView receptionistPage=parent.getReceptionistView(); //refresh table
                    if (receptionistPage !=null){
                        receptionistPage.refreshTable();
                    }
                    parent.refreshCount(); //refresh the counting number
                    JOptionPane.showMessageDialog(this, "Receptionist "+fullName+" registered successfully.");
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
        salaryField.setText("");
        jobBox.setSelectedIndex(0);
        group.clearSelection();
    }
}
