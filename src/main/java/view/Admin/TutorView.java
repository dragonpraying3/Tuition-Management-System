package view.Admin;
import controller.Registration;
import model.Tutor;

import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import javax.swing.JOptionPane;

public class TutorView extends JPanel {
    JPanel titlePanel,tablePanel,LeftButtonPanel,RightButtonPanel,buttonPanel;
    private final JLabel titleLabel;
    private final JTable table;
    private final JScrollPane sp;
    private final DefaultTableModel model;
    private final JButton deleteButton,cancelButton,backButton;
    static String TutorFile="src/main/resources/tutor.txt";
    static String AccountFile = "src/main/resources/users.txt";
    Font buttonFont;

    public TutorView (AdminView parent){
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        buttonFont=new Font("Rockwell",Font.BOLD,15);

        //title label
        titlePanel=new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        titleLabel=new JLabel("Tutor List",SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titlePanel.add(titleLabel);
        this.add(titlePanel);

        //add table that only can view whole list of tutor
        tablePanel=new JPanel();
        tablePanel.setLayout(new BorderLayout());

        //column name
        String[] columnName={"Username","Password","Full Name","Date of Birth","Age","Gender","Contact",
        "Email","Race","IC/Passport","Address","Qualification","Subject 1","Subject 2","Subject 3"};
        model=new DefaultTableModel(columnName,0){ //made the table in non-editable mode
            public boolean isCellEditable(int row, int column) {
            return false;
        }};
        table=new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //support horizontal scroll
        //can only allow to select row but not column
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false); //cannot modify the title
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14)); //the font of the header
        table.getTableHeader().setBackground(new Color(173, 222, 203)); //set header background colour
        table.setFont(new Font("Serif", Font.PLAIN, 14)); //the font of the column
        table.setBackground(new Color(253, 246, 231));
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(200);
        }

        //import read file from registration
        //read from user.txt first
        Map<String,String> usernamePasswordMap=new LinkedHashMap<>(); //control the sequence
        List<String> user=Registration.readFile(AccountFile);
        for (String line:user){
            String[] parts = line.split(";");
            if (parts.length >=3 &&parts[2].equalsIgnoreCase("tutor")){
                usernamePasswordMap.put(parts[0],parts[1]); //put the username and password in a map
            }
        }
        //second read from tutor.txt
        List<String> tutor=Registration.readFile(TutorFile);
        for (String line:tutor){
            String[] information = line.split(";");
            if (information.length >=14){
                String username=information[0]; //put username at first column
                String password =usernamePasswordMap.get(username); //find the tally password
                if (password!=null){
                    //whole table
                    String[] row=new String[16];
                    row[0]=username;
                    row[1]=password;
                    for (int i=1;i<information.length;i++){
                        row[i+1] =information[i]; //switch by two role
                    }
                    model.addRow(row);
                }
            }
        }
        sp=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(10,Integer.MAX_VALUE));
        sp.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
        sp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); //remove the border of scroll bar

        tablePanel.add(sp,BorderLayout.CENTER);
        this.add(tablePanel);

        buttonPanel =new JPanel(new BorderLayout());
        LeftButtonPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));
        RightButtonPanel=new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));

        backButton=new JButton("Back");
        backButton.setPreferredSize(new Dimension(90,40));
        backButton.setBackground(new Color(168, 169, 173));
        backButton.setForeground(new Color(255,255,255));
        formatButton(backButton);

        deleteButton=new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(90,40));
        formatButton(deleteButton);

        cancelButton=new JButton("Cancel Selection");
        cancelButton.setPreferredSize(new Dimension(180,40));
        cancelButton.setBackground(new Color(152, 175, 199));
        cancelButton.setForeground(new Color(255,255,255));
        formatButton(cancelButton);

        LeftButtonPanel.add(backButton);
        RightButtonPanel.add(deleteButton);
        RightButtonPanel.add(cancelButton);
        buttonPanel.add(LeftButtonPanel,BorderLayout.WEST);
        buttonPanel.add(RightButtonPanel,BorderLayout.EAST);
        deleteButton.setEnabled(false);
        cancelButton.setEnabled(false);
        this.add(buttonPanel,BorderLayout.SOUTH);

        AdminView.changeColour(this);

        backButton.addActionListener(e -> {
            parent.showCard("Home");
        });

        table.getSelectionModel().addListSelectionListener(e->{
            boolean rowSelected=table.getSelectedRow() != -1;
            deleteButton.setEnabled(rowSelected);
            cancelButton.setEnabled(rowSelected);

            if (rowSelected){
                deleteButton.setForeground(new Color(255,255,255));
                deleteButton.setBackground(new Color(200, 63, 73));
            }else{
                deleteButton.setForeground(null);
                deleteButton.setBackground(null);
            }
        });
        deleteButton.addActionListener(e->{
            int SelectedRow=table.getSelectedRow();
            if (SelectedRow==-1)return; //if no choose then back
            String username=table.getValueAt(SelectedRow,0).toString(); //convert the selected username to string
            String fullName=table.getValueAt(SelectedRow,2).toString();

            //tell the pane which is the parent window
            Window parentWindow = SwingUtilities.getWindowAncestor(deleteButton);
            int option =JOptionPane.showConfirmDialog(
                    parentWindow,"Are you sure want to delete Tutor \""+fullName+"\"(Username:"+username+")?",
                    "Confirm Deletion",JOptionPane.YES_NO_OPTION);

            if (option==JOptionPane.YES_OPTION){
                String result=DeleteTutorAll(username);
                if (result.equals("Success")){
                    JOptionPane.showMessageDialog(this, "Tutor "+username+" delete successfully.");
                    ((DefaultTableModel)table.getModel()).removeRow(SelectedRow); //update the GUI of the table
                    AssignTutorPage assignPage=parent.getAssignTutorPage(); //refresh the comboBox
                    if (assignPage!=null){
                        assignPage.refreshTutorBox();
                    }
                    UpdateTutorPage updateTutorPage=parent.getUpdateTutorPage(); //refresh the comboBox
                    if (updateTutorPage != null){
                        updateTutorPage.refreshTutorBoxAvailable();
                    }
                    ViewAssignment viewAssignment=parent.getViewAssignment();
                    if (viewAssignment !=null){
                        viewAssignment.refreshTable();
                    }
                    parent.refreshCount();
                }else{
                    JOptionPane.showMessageDialog(this, "Failed to delete tutor: " + result);
                }
            }
        });

        cancelButton.addActionListener(e->{
            table.clearSelection();
        });

    }
    private void formatButton(JButton button){
        button.setFont(buttonFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    //this is for refresh the table in assignment panel
    public void refreshTable(){
        DefaultTableModel model=(DefaultTableModel) table.getModel(); //change the table in model mode (can change)
        model.setRowCount(0); //clear the row in the table
        List<Tutor> tutors = Registration.getAllTutor(); //use the method to get the content

        for (Tutor tutor:tutors){
            Object[] rowData={ //every message become an object list
                    tutor.getUsername(),
                    tutor.getPassword(),
                    tutor.getFullName(),
                    tutor.getDateOfBirth(),
                    tutor.getAge(),
                    tutor.getGender(),
                    tutor.getContactNumber(),
                    tutor.getEmail(),
                    tutor.getRace(),
                    tutor.getIC(),
                    tutor.getAddress(),
                    tutor.getQualification(),
                    tutor.getSubject1(),
                    tutor.getSubject2(),
                    tutor.getSubject3()
            };
            model.addRow(rowData);
        }
    }
    public static String DeleteTutorAll(String username){
        String r1 = Registration.DeleteTutor(username);
        String r2 = Registration.CopyDeleting(username);
        if (r1.equals("Success") && r2.equals("Success")) {
            return "Success";
        } else if (!r1.equals("Success")) {
            return r1;
        } else {
            return r2;
        }
    }

}
