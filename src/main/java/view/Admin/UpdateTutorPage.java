package view.Admin;

import controller.Assignment;
import controller.Registration;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

import static controller.Registration.AssignmentFile;
import static controller.Registration.TutorFile;


public class UpdateTutorPage extends AssignmentTemplate {
    JLabel titleLabel, selectLabel;
    JTextField NameField, qualifyField, subject1Field, subject2Field, subject3Field;
    JPanel topPanel, topSection, selectPanel, leftPanel, rightPanel, totalPanel, titlePanel, wrapper, leftButtonPanel, rightButtonPanel, buttonPanel;
    JComboBox<String> tutorBox,subjectBox1,subjectBox2,subjectBox3;
    JButton backButton, updateButton, cancelButton, ruleButton;
    private List<JCheckBox> allCheckBox = new ArrayList<>(); //store the index of all subject's forms
    Font buttonFont;

    public UpdateTutorPage(AdminView parent) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Font titleFont = new Font("Serif", Font.BOLD, 26);
        Font labelFont = new Font("Times New Roman", Font.PLAIN, 18);
        buttonFont=new Font("Rockwell",Font.BOLD,15);

        //title
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //add rules introduction
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        titleLabel = new JLabel("Update Tutor Assignment");
        titleLabel.setFont(titleFont);

        ImageIcon icon = new ImageIcon("src/main/resources/icons/rule.png");
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon smallIcon = new ImageIcon(scaledImage);
        ruleButton = new JButton();
        ruleButton.setIcon(smallIcon);
        ruleButton.setPreferredSize(new Dimension(26, 26));
        ruleButton.setToolTipText("Click to view assignment rules");
        ruleButton.setBackground(new Color(148, 191, 233));
        formatButton(ruleButton);

        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(ruleButton);
        topPanel.add(Box.createHorizontalStrut(10));

        titlePanel.add(topPanel);
        this.add(titlePanel);
        this.add(Box.createVerticalStrut(10));

        //comboBox
        selectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        selectLabel = new JLabel("New Tutor List: ");
        selectLabel.setFont(labelFont);
        tutorBox = new JComboBox<>();
        tutorBox.setFont(labelFont);
        tutorBox.setPreferredSize(new Dimension(300, 30));
        //show the relevant tutor username
        refreshTutorBoxAvailable();
        selectPanel.add(selectLabel);
        selectPanel.add(tutorBox);

        //center form panel
        topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.setAlignmentX(Component.CENTER_ALIGNMENT);
        topSection.add(titlePanel);
        topSection.add(selectPanel);
        this.add(topSection);
        this.add(Box.createVerticalStrut(10)); //gap between the select panel and the form

        // Left Form
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(createLabelField("Full Name:", NameField = new JTextField(18)));
        leftPanel.add(Box.createRigidArea(new Dimension(0,36)));
        String[] subjects={"No","Bahasa Melayu", "English","Chinese Language", "Mathematics", "Additional Mathematics", "Science",
                "Physics", "Chemistry", "Biology", "History", "Geography", "Moral Education", "Pendidikan Islam",
                "Economics", "Business Studies", "Accounting"};
        subjectBox1=new JComboBox<>(subjects);
        subjectBox2=new JComboBox<>(subjects);
        subjectBox3=new JComboBox<>(subjects);
        leftPanel.add(createField("Subject 1: ",subjectBox1));
        leftPanel.add(Box.createRigidArea(new Dimension(0,36)));
        leftPanel.add(createField("Subject 2: ",subjectBox2));
        leftPanel.add(Box.createRigidArea(new Dimension(0,36)));
        leftPanel.add(createField("Subject 3: ",subjectBox3));
        makeReadOnly(NameField);

        // Right Form
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(createLabelField("Qualification:", qualifyField = new JTextField(18)));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 36)));
        makeReadOnly(qualifyField);
        //inside a list 'allCheckBox'
        rightPanel.add(checkBoxPanel("Teach's Levels:")); //index 0-4
        rightPanel.add(Box.createRigidArea(new Dimension(0, 36)));
        rightPanel.add(checkBoxPanel("Teach's Levels:")); //index 5-9
        rightPanel.add(Box.createRigidArea(new Dimension(0, 36)));
        rightPanel.add(checkBoxPanel("Teach's Levels:")); //index 10-14

        // Fix size to avoid over-expansion
        Dimension formSize = new Dimension(350, 300);
        leftPanel.setMaximumSize(formSize);
        rightPanel.setMaximumSize(formSize);
        rightPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.X_AXIS));
        totalPanel.add(Box.createHorizontalGlue()); // center alignment
        totalPanel.add(leftPanel);
        totalPanel.add(Box.createRigidArea(new Dimension(50, 0))); // spacing between left/right
        totalPanel.add(rightPanel);
        totalPanel.add(Box.createHorizontalGlue()); //push the form to bottom

        // Wrap totalPanel in a CENTERED container
        wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(Box.createVerticalGlue()); //push upper
        wrapper.add(totalPanel); //left and right panel is in total panel and contain in wrapper
        wrapper.add(Box.createHorizontalGlue());
        this.add(Box.createVerticalStrut(10)); //the gap between comboBox and form
        this.add(wrapper); //the form area
        this.add(Box.createVerticalGlue()); //push everything go up
        this.add(Box.createVerticalStrut(2)); //move larger gap at bottom so that it can push the form upper

        buttonPanel = new JPanel(new BorderLayout());
        leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

        backButton=new JButton("Back");
        backButton.setPreferredSize(new Dimension(90,40));
        backButton.setBackground(new Color(168, 169, 173));
        backButton.setForeground(new Color(255,255,255));
        formatButton(backButton);

        updateButton = new JButton("Save Changes");
        updateButton.setPreferredSize(new Dimension(150, 40));
        updateButton.setBackground(new Color(26, 162, 96));
        updateButton.setForeground(new Color(255,255,255));
        formatButton(updateButton);

        cancelButton = new JButton("Discard");
        cancelButton.setPreferredSize(new Dimension(110, 40));
        cancelButton.setBackground(new Color(200, 63, 73));
        cancelButton.setForeground(new Color(255,255,255));
        formatButton(cancelButton);

        leftButtonPanel.add(backButton);
        rightButtonPanel.add(updateButton);
        rightButtonPanel.add(cancelButton);
        updateButton.setEnabled(false);
        cancelButton.setEnabled(false);
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(Box.createRigidArea(new Dimension(0,50)));
        AdminView.changeColour(this);

        updateButton.addActionListener(e-> {
            String TutorName = (String) tutorBox.getSelectedItem();

            if (TutorName.equals("--- Select Current Available Tutor ---")) {
                JOptionPane.showMessageDialog(this, "Please select a tutor before updating.", "No Tutor Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Map<String, List<String>> updating = getSelectedItem();
            if (updating==null || updating.isEmpty()){
                return;
            }
            String result = Assignment.updateTutor(TutorName, updating);
            if (result.equals("Success")) {
                ViewAssignment viewAssignment=parent.getViewAssignment();
                if (viewAssignment != null){
                    viewAssignment.refreshTable();
                }
                refreshTutorBoxAvailable();
                JOptionPane.showMessageDialog(this, "Tutor " + TutorName + " update successfully.");
                clearAllSelection();
            } else{
                JOptionPane.showMessageDialog(this, result, "Updating Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ruleButton.addActionListener(e->{
            String rules=rulesExplain();
            JOptionPane.showMessageDialog(this, rules, "Tutor Assignment Rules", JOptionPane.INFORMATION_MESSAGE);
        });

        backButton.addActionListener(e -> {
            parent.showCard("Home");
            clearAllSelection();
        });

        //the subject box button get changes will set the save changes and discard button become true to edit
        ItemListener subjectBox=e->{
            if (e.getStateChange()== ItemEvent.SELECTED){
                enabledItem();
            }
        };
        subjectBox1.addItemListener(subjectBox);
        subjectBox2.addItemListener(subjectBox);
        subjectBox3.addItemListener(subjectBox);

        //after choose tutor or click discard will get back to the origin state
        tutorBox.addItemListener(e->{
            LoadRoleSelector();
        });
        cancelButton.addActionListener(e->{
            LoadRoleSelector();
        });
    }
    private void formatButton(JButton button){
        button.setFont(buttonFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    public void refreshTutorBoxAvailable() {
        tutorBox.removeAllItems(); //remove all item first

        boolean hasTutor = false;
        List<String> tutorData = Registration.readFile(TutorFile);
        for (String line : tutorData) {
            String[] parts = line.split(";");
            if (parts.length > 0) {
                String username = parts[0];
                if (Assignment.HasAssignment(username)) {
                    tutorBox.addItem(username); //add again all relevant item
                    hasTutor = true;
                }
            }
        }
        if (!hasTutor) {
            tutorBox.addItem("--- No Available Tutor ---");
        }else{
            tutorBox.insertItemAt("--- Select Current Available Tutor ---", 0);
            tutorBox.setSelectedIndex(0);
        }
    }
    private JPanel createField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 18)); //adjust the size of font
        label.setPreferredSize(new Dimension(100, 35)); //adjust size of label

        field.setFont(new Font("Times New Roman", Font.PLAIN, 16)); //adjust the size of font

        panel.add(label);
        panel.add(field);
        //adjust the vertical length between column
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        return panel;
    }

    private JPanel checkBoxPanel(String Text){
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.LEFT,13,5));

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 18);
        Font checkFont = new Font("Times New Roman", Font.BOLD, 12);

        JLabel label=new JLabel(Text);
        label.setFont(labelFont);
        panel.add(label);

        for(int i=1;i<=5;i++){
            JCheckBox checkBox=new JCheckBox("Form "+i);
            checkBox.setToolTipText("This subject is suitable for Form "+i+" students.");
            checkBox.setFont(checkFont);
            panel.add(checkBox);
            checkBox.setFocusable(false);// Removes the border that appears when the checkbox has focus

            checkBox.addItemListener(e->{ //check the checkBox get selected or not
                updateButton();
            });

            allCheckBox.add(checkBox);
        }
        return panel;
    }
    private void updateButton(){
        boolean selected=allCheckBox.stream().anyMatch(JCheckBox::isSelected); //if any button is selected return true
        updateButton.setEnabled(selected);
        cancelButton.setEnabled(selected); //the button can be use if true
    }
    public void clearAllSelection(){
        for (JCheckBox checkBox:allCheckBox){
            checkBox.setSelected(false); //empty the checkBox
        }
        NameField.setText("");
        qualifyField.setText("");
        subjectBox1.setSelectedIndex(0);
        subjectBox2.setSelectedIndex(0);
        subjectBox3.setSelectedIndex(0);
        tutorBox.setSelectedIndex(0);
        cancelButton.setEnabled(false);
    }
    private void displaySubject(String username){
        for (JCheckBox checkBox:allCheckBox){
            checkBox.setSelected(false); //clear all checkbox
        }
        List<String> subjectData=Registration.readFile(TutorFile);
        for (String line:subjectData){
            String[] parts =line.split(";");
            if (parts.length>=14 && parts[0].equalsIgnoreCase(username)){
                NameField.setText(parts[1]); //the real data of the user selected
                qualifyField.setText(parts[10]);
                subjectBox1.setSelectedItem(parts[11]);
                subjectBox2.setSelectedItem(parts[12]);
                subjectBox3.setSelectedItem(parts[13]);
                break;
            }
        }

        List<String> formData=Registration.readFile(AssignmentFile);
        for (String line:formData){
            String[] parts=line.split(";");
            if (parts.length==0){
                continue;
            }
            if (!parts[0].equalsIgnoreCase(username)){
                continue;
            }

            for (int i=0;i<3 && i+1<parts.length;i++){ //index of subject
                String realForm=parts[i+1].trim();
                if (realForm.isEmpty()){
                    continue;
                }
                String[] forms=realForm.split(","); //split if having more then one form
                for(String form:forms){
                    form=form.trim();
                    for (int j=0;j<5;j++){ //index of the form
                        JCheckBox checkBox=allCheckBox.get(i*5+j);
                        if (checkBox.getText().equalsIgnoreCase(form)){
                            checkBox.setSelected(true);
                        }
                    }
                }
            }
        }
    }
    private Map<String, List<String>> getSelectedItem(){
        Map<String,List<String>> subjectDict=new java.util.HashMap<>();
        String[] subjects={
                ((String)subjectBox1.getSelectedItem()).trim(),
                ((String)subjectBox2.getSelectedItem()).trim(),
                ((String)subjectBox3.getSelectedItem()).trim()
        };

        Set<String> checkDuplicate=new HashSet<>(); //because set cannot have duplicate item
        for (String subject:subjects){
            if (!subject.equals("No")){
                if (!checkDuplicate.add(subject)){
                    JOptionPane.showMessageDialog(this,"Subjects cannot be the same!","Duplicate Subject",JOptionPane.WARNING_MESSAGE);
                    return null;
                }
            }
        }

        for (int sub=0;sub<subjects.length;sub++){  //index of subject
            String subject=subjects[sub];
            if (subject.equals("No")){
                for (int i=0;i<5;i++){
                    JCheckBox checkBox=allCheckBox.get(sub*5+i);
                    if (checkBox.isSelected()){
                        JOptionPane.showMessageDialog(this,
                                "Cannot assign forms for Subject " + (sub + 1) + " because it is set to 'No'.",
                                "Invalid Assignment",
                                JOptionPane.WARNING_MESSAGE);
                        return null;
                    }
                }
                continue;
            }

            List<String> insideForm=new ArrayList<>();
            for (int i=0;i<5;i++){ //index of the form
                JCheckBox checkBox=allCheckBox.get(sub*5+i);
                if(checkBox.isSelected()){
                    insideForm.add(checkBox.getText());
                }
            }

            if(insideForm.isEmpty()){
                JOptionPane.showMessageDialog(this,
                        "No assign levels for Subject " + subject + ".\nThis subject will be removed from the tutor.",
                        "Subject Removed",
                        JOptionPane.WARNING_MESSAGE);
                continue;
            }
            subjectDict.put(subject,insideForm);
        }
        return subjectDict;
    }

    private void enabledItem(){
        updateButton.setEnabled(true);
        cancelButton.setEnabled(true);
    }

    private void LoadRoleSelector(){
        String username=(String) tutorBox.getSelectedItem();
        if (username!=null && !username.equals("--- Select Current Available Tutor ---")){
            displaySubject(username);
        }
        updateButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }
}
