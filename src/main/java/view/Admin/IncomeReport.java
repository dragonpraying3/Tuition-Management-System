package view.Admin;

import controller.Financial;
import controller.IncomeReportGenerator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IncomeReport extends JPanel {
    JPanel titlePanel,filterPanel,tablePanel,bottomPanel,countPanel,incomeRow,peopleRow;
    JLabel titleLabel,filterLabel,incomeLabel,peopleLabel;
    JButton applyButton,resetButton,backButton,reportButton;
    JTable table;
    JScrollPane sp;
    JTextField incomeText,peopleText;
    DefaultTableModel model;
    JComboBox<String> monthBox,yearBox,levelBox,subjectBox;
    Font buttonFont;

    public IncomeReport(AdminView parent){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Font titleFont = new Font("Serif", Font.BOLD, 26);
        Font labelFont = new Font("Times New Roman", Font.PLAIN, 18);
        buttonFont=new Font("Rockwell",Font.BOLD,15);

        //title
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titleLabel=new JLabel("Monthly Income Report",SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(titleFont);
        titlePanel.add(titleLabel);
        this.add(titlePanel);

        //filtering option
        String[] months = {"Month","Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
        monthBox = new JComboBox<>(months);
        String[] years = {"Year","1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006",
                "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019",
        "2020","2021","2022","2023","2024","2025"};
        yearBox = new JComboBox<>(years);
        String[] levels={"Level","Form 1","Form 2","Form 3","Form 4","Form 5"};
        levelBox=new JComboBox<>(levels);
        String[] subjects={"Subject","Bahasa Melayu", "English","Chinese Language", "Mathematics", "Additional Mathematics", "Science",
                "Physics", "Chemistry", "Biology", "History", "Geography", "Moral Education", "Pendidikan Islam",
                "Economics", "Business Studies", "Accounting"};
        subjectBox=new JComboBox<>(subjects);

        filterPanel=new JPanel();
        filterLabel=new JLabel("Filter By: ",SwingConstants.LEFT);
        applyButton=new JButton("Apply");
        applyButton.setBackground(new Color(43, 101, 236));
        applyButton.setForeground(new Color(255,255,255));
        resetButton=new JButton("Reset");
        resetButton.setBackground(new Color(248, 186, 0));
        resetButton.setForeground(new Color(255,255,255));
        formatButton(applyButton);
        formatButton(resetButton);

        filterLabel.setFont(labelFont);
        filterPanel.add(filterLabel);
        filterPanel.add(monthBox);
        filterPanel.add(yearBox);
        filterPanel.add(levelBox);
        filterPanel.add(subjectBox);
        filterPanel.add(resetButton);
        filterPanel.add(applyButton);
        this.add(filterPanel);
        filterPanel.add(Box.createRigidArea(new Dimension(0, 40))); //add gap

        //table panel
        tablePanel=new JPanel();
        tablePanel.setLayout(new BorderLayout());

        String[] columnName={"Student Username","Student Name","Subject","Level","Date","Charges (RM)","Payment Method",
                "Payment Status"};
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
        Financial f=new Financial();
        List<String[]> paidList=f.getPaidStudent();
        for (String[] row:paidList){
            model.addRow(row);
        }
        sp=new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(10,Integer.MAX_VALUE)); //make the scroll bar line more thin
        sp.getHorizontalScrollBar().setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
        sp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); //remove the border of scroll bar

        tablePanel.add(sp,BorderLayout.CENTER);
        this.add(tablePanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));

        //show total count & total income
        countPanel=new JPanel();
        countPanel.setLayout(new BoxLayout(countPanel,BoxLayout.Y_AXIS));
        countPanel.setBackground(new Color(245, 245, 245));
        countPanel.setPreferredSize(new Dimension(400, 80));
        countPanel.setMaximumSize(new Dimension(30000, 120)); //zoom in horizontal size

        TitledBorder title=new TitledBorder("Income Summary"); //set title font
        title.setTitlePosition(TitledBorder.TOP);
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleFont(new Font("Serif", Font.BOLD, 16));
        countPanel.setBorder(BorderFactory.createCompoundBorder(
                title,
                BorderFactory.createEmptyBorder(20, 30, 20, 30) // top, left, bottom, right
        ));

        //income row
        incomeRow=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        incomeRow.setOpaque(false);
        incomeLabel=new JLabel("Total Income : ");
        incomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        incomeLabel.setForeground(Color.BLUE);
        incomeText=new JTextField(20); //set a text field
        incomeText.setEditable(false);
        incomeText.setFont(new Font("Serif",Font.BOLD,14));
        incomeText.setHorizontalAlignment(JTextField.RIGHT);
        incomeRow.add(incomeLabel);
        incomeRow.add(incomeText);

        //people row
        peopleRow=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        peopleRow.setOpaque(false); //transparent background
        peopleLabel=new JLabel("Total Students : ");
        peopleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        peopleLabel.setForeground(Color.BLUE);
        peopleText=new JTextField(20); //set a text field for the column
        peopleText.setEditable(false);
        peopleText.setFont(new Font("Serif",Font.BOLD,14));
        peopleText.setHorizontalAlignment(JTextField.RIGHT);
        peopleRow.add(peopleLabel);
        peopleRow.add(peopleText);

        //set alignment
        incomeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        peopleLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        countPanel.add(incomeRow);
        countPanel.add(Box.createRigidArea(new Dimension(0,5)));
        countPanel.add(peopleRow);
        countPanel.add(Box.createRigidArea(new Dimension(0,10)));

        //call the calculation method
        int totalIncome=f.calcIncome(paidList);
        int totalPeople=f.calcPeople(paidList);

        incomeText.setText("RM " + totalIncome); //update the label
        peopleText.setText(String.valueOf(totalPeople)); //update the label //convert int to string
        this.add(countPanel);
        this.add(Box.createRigidArea(new Dimension(0,10)));

        //back button & generate report button
        bottomPanel = new JPanel(new BorderLayout());
        backButton=new JButton("Back");
        backButton.setPreferredSize(new Dimension(90,50));
        backButton.setBackground(new Color(168, 169, 173));
        backButton.setForeground(new Color(255,255,255));
        formatButton(backButton);

        reportButton=new JButton("Generate Report");
        reportButton.setPreferredSize(new Dimension(180,50));
        reportButton.setBackground(new Color(43, 101, 236));
        reportButton.setForeground(new Color(255,255,255));
        formatButton(reportButton);

        bottomPanel.add(backButton,BorderLayout.WEST);
        bottomPanel.add(reportButton,BorderLayout.EAST);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 20));
        this.add(bottomPanel);
        this.add(Box.createRigidArea(new Dimension(0,20)));
        AdminView.changeColour(this);

        backButton.addActionListener(e -> {
            parent.showCard("Home");
            resetFilter();
        });
        applyButton.addActionListener(e->{
            applyFilter();
        });
        resetButton.addActionListener(e->{
            resetFilter();
        });
        reportButton.addActionListener(e->{
            List<String[]> data=modelToList(model);

            if (data.isEmpty()){
                JOptionPane.showMessageDialog(this, "No data to generate report.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean success=IncomeReportGenerator.generateReport(data);

            if (success) {
                JOptionPane.showMessageDialog(this, "PDF report generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to generate PDF report.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void formatButton(JButton button){
        button.setFont(buttonFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    //change model to list
    private List<String[]> modelToList(DefaultTableModel model){
        List<String[]> list=new ArrayList<>();
        for (int i=0;i<model.getRowCount();i++){
            String[] row =new String[model.getColumnCount()];
            for (int j=0;j<model.getColumnCount();j++){
                row[j]=model.getValueAt(i,j).toString();
            }
            list.add(row);
        }
        return list;
    }
    protected void applyFilter(){
        Financial f=new Financial();
        List<String[]> fullList=f.getPaidStudent();

        String selectedMonth=(String) monthBox.getSelectedItem();
        String selectedYear=(String) yearBox.getSelectedItem();
        String selectedLevel=(String) levelBox.getSelectedItem();
        String selectedSubject=(String) subjectBox.getSelectedItem();

        model.setRowCount(0); //clear table

        for (String[] row:fullList){
            String username=row[0];
            String fullName=row[1];
            String subject=row[2];
            String level=row[3];
            String date=row[4];
            String charges=row[5];
            String paymentMethod=row[6];
            String paymentStatus=row[7];

            boolean matches=true;

            //filter by month
            if (!selectedMonth.equals("Month")){
                String[] dateSplit=date.split("-");
                try{
                    int month=Integer.parseInt(dateSplit[1]);
                    int selectedMonthIndex=monthBox.getSelectedIndex();
                    if (month !=selectedMonthIndex){ //check the index of monthBox and month number tally or not
                        matches=false;
                    }
                } catch (NumberFormatException e) {
                    matches=false;
                }
            }

            //filter by year
            if (!selectedYear.equals("Year")){
                if (!date.startsWith(selectedYear)){
                    matches=false;
                }
            }

            //filter by level
            if (!selectedLevel.equals("Level") && !level.equals(selectedLevel)){
                matches=false;
            }

            //filter by subject
            if (!selectedSubject.equals("Subject") && !subject.equals(selectedSubject)){
                matches=false;
            }

            if (matches){
                model.addRow(row); //if item match then return the match row
            }
        }
        //convert model to list
        List<String[]> currentList=modelToList(model);
        incomeText.setText("RM "+ String.valueOf(f.calcIncome(currentList)));
        peopleText.setText(String.valueOf(f.calcPeople(currentList)));
    }

    protected void resetFilter(){
        monthBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
        levelBox.setSelectedIndex(0);
        subjectBox.setSelectedIndex(0);

        //reload full data
        Financial f =new Financial();
        List<String[]> paidList=f.getPaidStudent();
        model.setRowCount(0);
        for (String[] row:paidList){
            model.addRow(row);
        }

        incomeText.setText("RM " + f.calcIncome(paidList));
        peopleText.setText(String.valueOf(f.calcPeople(paidList)));
    }
    public void refreshTable(){ //use when receptionist update the payment table (need open object)
        Financial f=new Financial();
        List<String[]> paidList=f.getPaidStudent();
        model.setRowCount(0);
        for (String[] row: paidList){
            model.addRow(row);
        }
        incomeText.setText("RM " + f.calcIncome(paidList));
        peopleText.setText(String.valueOf(f.calcPeople(paidList)));
    }
}
