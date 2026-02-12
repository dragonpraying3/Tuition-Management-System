package view.Admin;

import controller.Assignment;
import controller.Registration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AssignTutorPage extends AssignmentTemplate {
    JLabel titleLabel,selectLabel;
    JTextField NameField,qualifyField,subject1Field,subject2Field,subject3Field;
    JPanel topPanel,topSection,selectPanel,leftPanel,rightPanel,totalPanel,titlePanel,wrapper,leftButtonPanel,rightButtonPanel,buttonPanel;
    JComboBox<String> tutorBox;
    JButton backButton,assignButton,cancelButton,ruleButton;
    static String TutorFile="src/main/resources/tutor.txt";
    private List<JCheckBox> allCheckBox=new ArrayList<>();
    Font buttonFont;

    public AssignTutorPage(AdminView parent){
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
        topPanel=new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.X_AXIS));
        titleLabel = new JLabel("New Tutor Assignment");
        titleLabel.setFont(titleFont);

        ImageIcon icon=new ImageIcon("src/main/resources/icons/rule.png");
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon smallIcon = new ImageIcon(scaledImage);
        ruleButton=new JButton();
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
        refreshTutorBox();
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
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.add(createLabelField("Full Name:", NameField = new JTextField(18)));
        leftPanel.add(Box.createRigidArea(new Dimension(0,36)));
        leftPanel.add(createLabelField("Subject 1:", subject1Field = new JTextField(18)));
        leftPanel.add(Box.createRigidArea(new Dimension(0,36)));
        leftPanel.add(createLabelField("Subject 2:", subject2Field = new JTextField(18)));
        leftPanel.add(Box.createRigidArea(new Dimension(0,36)));
        leftPanel.add(createLabelField("Subject 3:", subject3Field = new JTextField(18)));
        makeReadOnly(NameField, subject1Field, subject2Field, subject3Field);

        // Right Form
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
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

        totalPanel=new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel,BoxLayout.X_AXIS));
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
        this.add(Box.createVerticalStrut(20)); //move larger gap at bottom so that it can push the form upper

        buttonPanel=new JPanel(new BorderLayout());
        leftButtonPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));
        rightButtonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));

        backButton=new JButton("Back");
        backButton.setPreferredSize(new Dimension(90,40));
        backButton.setBackground(new Color(168, 169, 173));
        backButton.setForeground(new Color(255,255,255));
        formatButton(backButton);

        assignButton=new JButton("Assign");
        assignButton.setPreferredSize(new Dimension(90,40));
        assignButton.setBackground(new Color(26, 162, 96));
        assignButton.setForeground(new Color(255,255,255));
        formatButton(assignButton);

        cancelButton=new JButton("Reset All");
        cancelButton.setPreferredSize(new Dimension(120,40));
        cancelButton.setBackground(new Color(255, 140, 0));
        cancelButton.setForeground(new Color(255,255,255));
        formatButton(cancelButton);

        leftButtonPanel.add(backButton);
        rightButtonPanel.add(assignButton);
        rightButtonPanel.add(cancelButton);
        assignButton.setEnabled(false);
        cancelButton.setEnabled(false);
        buttonPanel.add(leftButtonPanel,BorderLayout.WEST);
        buttonPanel.add(rightButtonPanel,BorderLayout.EAST);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.add(Box.createVerticalStrut(50));
        AdminView.changeColour(this);

        ruleButton.addActionListener(e->{
            String rules=rulesExplain();
            JOptionPane.showMessageDialog(this, rules, "Tutor Assignment Rules", JOptionPane.INFORMATION_MESSAGE);
        });

        assignButton.addActionListener(e->{
            String TutorName=(String) tutorBox.getSelectedItem();
            Map<String,List<String>> form=getSelectedForm();
            String result=Assignment.assignTutor(TutorName, form);
            boolean hasSubjectNoForm=false;
            String[] subjects={subject1Field.getText().trim(),subject2Field.getText().trim(),subject3Field.getText().trim()};

            if (TutorName.equals("--- Select New Tutor ---")) {
                JOptionPane.showMessageDialog(this, "Please select a tutor before assigning.", "No Tutor Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }

            for (int sub=0;sub<subjects.length;sub++){
                String subject=subjects[sub];
                if (!subject.equals("No")){
                    boolean hasChecked=false;
                    for (int i=0;i<5;i++){
                        JCheckBox checkBox=allCheckBox.get(sub*5+i);
                        if (checkBox.isSelected()){ //if the subject is not empty and is selected then return true
                            hasChecked=true;
                            break;
                        }
                    }
                    if (!hasChecked){ //return false because has subject but no form selected
                        hasSubjectNoForm=true;
                        break;
                    }
                }
            }
            if (hasSubjectNoForm) {
                JOptionPane.showMessageDialog(this,
                        "Each filled-in subject must have at least one form selected.",
                        "Missing Form Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (result.equals("Success")){
                refreshTutorBox();
                UpdateTutorPage updateTutorPage=parent.getUpdateTutorPage(); //refresh combo box
                if (updateTutorPage!=null){
                    updateTutorPage.refreshTutorBoxAvailable();
                }
                ViewAssignment viewAssignment=parent.getViewAssignment(); //refresh table
                if (viewAssignment != null){
                    viewAssignment.refreshTable();
                }
                clearSelection();
                JOptionPane.showMessageDialog(this, "Tutor "+ TutorName +" assign successfully.");
            }else{
                JOptionPane.showMessageDialog(this,result,"Assigning Error",JOptionPane.ERROR_MESSAGE);
            }

        });

        tutorBox.addActionListener(e->{  //show the page of the relevant tutor
            cancelButton.setEnabled(true);
            String username=(String) tutorBox.getSelectedItem();
            if (username !=null){
                displaySubject(username);
            }
        });

        backButton.addActionListener(e -> {
            parent.showCard("Home");
            clearSelection();
        });

        cancelButton.addActionListener(e->{
            clearSelection();
        });
    }
    private void formatButton(JButton button){
        button.setFont(buttonFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    public void refreshTutorBox(){
        tutorBox.removeAllItems(); //remove all item first

        boolean hasTutor=false;
        List<String> tutorData = Registration.readFile(TutorFile);
        for (String line : tutorData) {
            String[] parts = line.split(";");
            if (parts.length > 0) {
                String username = parts[0];
                if (!Assignment.HasAssignment(username)) {
                    tutorBox.addItem(username); //add again all relevant item
                    hasTutor=true;
                }
            }
        }
        if (!hasTutor){
            tutorBox.addItem("--- No tutor available ---");
        } else {
            tutorBox.insertItemAt("--- Select New Tutor ---", 0);
            tutorBox.setSelectedIndex(0);
        }
    }
    private JPanel checkBoxPanel(String Text){
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.LEFT,13,5));

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 18);
        Font checkFont=new Font("Times New Roman",Font.BOLD,12);

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
    public void displaySubject(String username){
        List<String> subjectData=Registration.readFile(TutorFile);
        for (String line:subjectData){
            String[] parts =line.split(";");
            if (parts.length>=14 && parts[0].equalsIgnoreCase(username)){
                NameField.setText(parts[1]); //the real data of the user selected
                qualifyField.setText(parts[10]);
                subject1Field.setText(parts[11]);
                subject2Field.setText(parts[12]);
                subject3Field.setText(parts[13]);
            }
        }
    }
    private void updateButton(){
        boolean selected=allCheckBox.stream().anyMatch(JCheckBox::isSelected); //if any button is selected return true
        assignButton.setEnabled(selected);
        cancelButton.setEnabled(selected); //the button can be use if true
    }
    public void clearSelection(){
        for (JCheckBox checkBox:allCheckBox){
            checkBox.setSelected(false); //empty the checkBox
        }
        NameField.setText("");
        qualifyField.setText("");
        subject1Field.setText("");
        subject2Field.setText("");
        subject3Field.setText("");
        tutorBox.setSelectedIndex(0);
        cancelButton.setEnabled(false); //after empty the cancel button also cannot click
    }
    private Map<String, List<String>> getSelectedForm(){
        Map<String,List<String>> subjectDict=new java.util.HashMap<>();
        String[] subjects={subject1Field.getText().trim(),subject2Field.getText().trim(),subject3Field.getText().trim()};

        for (int sub=0;sub<subjects.length;sub++){
            String subject=subjects[sub];
            if (subject.isEmpty()){
                continue;
            }
            List<String> insideForm=new ArrayList<>();
            for (int i=0;i<5;i++){
                JCheckBox checkBox=allCheckBox.get(sub*5+i); //get the index number of the tally checkBox
                if(checkBox.isSelected()){
                    insideForm.add(checkBox.getText());
                }
            }

            if (!insideForm.isEmpty()){
                subjectDict.put(subject,insideForm);
            }
        }
        return subjectDict;
    }

}
