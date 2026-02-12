package view.Admin;

import controller.Registration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

import static controller.Registration.AssignmentFile;
import static controller.Registration.TutorFile;

public class ViewAssignment extends AssignmentTemplate {
    JPanel titlePanel,tablePanel,buttonPanel,leftPanel;
    JLabel titleLabel;
    JTable table;
    JButton backButton;
    private final JScrollPane sp;
    private final DefaultTableModel model;
    Font buttonFont;

    public ViewAssignment(AdminView parent){
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        buttonFont=new Font("Rockwell",Font.BOLD,15);

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 20));
        titleLabel = new JLabel("Tutor Assignment List",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel,BorderLayout.CENTER);
        this.add(titlePanel);
        this.add(Box.createVerticalStrut(20));  //gap between title and table

        tablePanel=new JPanel();
        tablePanel.setLayout(new BorderLayout());
        String[] columnName={"Username","Full Name","Subject 1","Teach's Levels","Subject 2","Teach's Levels","Subject 3","Teach's Levels"};
        model=new DefaultTableModel(columnName,0){ //made the table in non-editable mode
            public boolean isCellEditable(int row, int column) {
                return false;
            }};
        table=new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //support horizontal scroll
        //can only allow to select row but not column
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setRowHeight(28);
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

        //call the table
        refreshTable();

        sp=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(10,Integer.MAX_VALUE));
        sp.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
        sp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); //remove the border of scroll bar

        tablePanel.add(sp,BorderLayout.CENTER);
        this.add(tablePanel);
        this.add(Box.createVerticalStrut(10));

        buttonPanel =new JPanel(new BorderLayout());
        leftPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));
        backButton=new JButton("Back");
        backButton.setPreferredSize(new Dimension(90,40));
        backButton.setBackground(new Color(168, 169, 173));
        backButton.setForeground(new Color(255,255,255));
        formatButton(backButton);

        leftPanel.add(backButton);
        buttonPanel.add(leftPanel,BorderLayout.WEST);
        this.add(buttonPanel,BorderLayout.SOUTH);
        AdminView.changeColour(this);

        backButton.addActionListener(e -> {
            parent.showCard("Home");;
        });

    }
    public void refreshTable(){
        model.setRowCount(0); //delete all table data
        Map<String, String[]> assignmentMap = new HashMap<>();
        List<String> AssInfo=Registration.readFile(AssignmentFile);
        for (String line:AssInfo){
            String[] parts=line.split(";");
            String[] values=new String[]{"","",""}; //default the empty field with ""
            if (parts.length>=2){  //if the field length is 2 which is 0,1
                values[0]=parts[1]; //assign the first values "" with the parts[1] form value if have ,others fill with ""
                //{"Form 1","",""}
            }
            if (parts.length>=3){
                values[1]=parts[2];
            }
            if (parts.length>=4){
                values[2]=parts[3];
            }
            assignmentMap.put(parts[0],values);
        }

        List<String> tutorInfo=Registration.readFile(TutorFile);
        for (String line:tutorInfo){
            String[] parts=line.split(";");
            if (parts.length>=14){
                String username=parts[0];
                String name=parts[1];
                String sub1=parts[11];
                String sub2=parts[12];
                String sub3=parts[13];

                String[] forms=assignmentMap.getOrDefault(username,new String[]{"","",""}); //if cannot find the username info will default as ""
                String form1="";
                String form2="";
                String form3="";

                //solve the return null issue
                if (!sub1.equals("No") && forms.length>0 && forms[0]!=null){
                    form1=forms[0]; //if form length is 1 then the index 0 is form 1
                }
                if (!sub2.equals("No") && forms.length>1 && forms[1]!=null){
                    form2=forms[1];
                }
                if (!sub3.equals("No") && forms.length>2 && forms[2]!=null){
                    form3=forms[2];
                }

                String[] row={username,name,sub1,form1,sub2,form2,sub3,form3};
                model.addRow(row);
            }
        }
    }
    private void formatButton(JButton button){
        button.setFont(buttonFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
}
