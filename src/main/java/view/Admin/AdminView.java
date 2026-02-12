package view.Admin;

import controller.Registration;
import controller.ValidUser;
import model.User;
import view.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static controller.Registration.*;

public class AdminView extends JFrame {
    private final String loginUser;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    JPanel HomePanel,sidebar,topBar,registerSub,manageSub,assSub,centerBox,card,textPanel,mainBox,
    viewPanel,statusPanel,leftBar,rightBar;
    RegisterTutorForm registerTutorForm;
    RegisterReceptionistForm registerReceptionistForm;
    TutorView tutorView;
    ReceptionistView receptionistView;
    AssignTutorPage assignTutorPage;
    UpdateTutorPage updateTutorPage;
    ViewAssignment viewAssignment;
    IncomeReport incomeReport;
    AdminProfile adminProfile;
    ImageIcon homeIcon,manageIcon,assIcon,incomeIcon,profileIcon,logoutIcon,registerIcon,studentImg,tutorImg,receptionistImg;
    JButton logout,homeButton,button,registerButton,manageButton,changeButton,sbutton,
    tutorRegister,receptionistRegister,tutorManage,receptionistManage,assButton,viewTutor,assignTutor,updateTutor,incomeButton,
    profileButton;
    private boolean sideBarVisible=true;
    JScrollPane sp;
    JLabel welcomeLabel,timeLabel,onlineStatus,greenDot,iconLabel,titleLabel,countLabel,studentLabel,tutorLabel,receptionistLabel
            ,commandLabel;

    public AdminView(User Admin) {
        this.setTitle("Admin Dashboard");
        this.setSize(950, 610);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.loginUser=Admin.getUsername(); //store the username to a new variable name

        //creating object so can save the original data but not different temporarily data
        registerTutorForm=new RegisterTutorForm(this);
        registerReceptionistForm=new RegisterReceptionistForm(this);
        tutorView=new TutorView(this);
        receptionistView=new ReceptionistView(this);
        assignTutorPage=new AssignTutorPage(this);
        updateTutorPage=new UpdateTutorPage(this);
        viewAssignment=new ViewAssignment(this);
        incomeReport=new IncomeReport(this);
        adminProfile=new AdminProfile(this,loginUser);

        //topBar
//        topBar=new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar=new JPanel(new BorderLayout());
        topBar.setBackground(new Color(60,60,60));

        leftBar=new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftBar.setBackground(new Color(60,60,60));

        rightBar=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBar.setBackground(new Color(60,60,60));

        changeButton=new JButton("☰  Menu");
        changeButton.setFocusPainted(false);
        changeButton.setFont(new Font("Segeo UI",Font.BOLD,14));
        changeButton.setBackground(new Color(60,60,60));
        changeButton.setForeground(new Color(255,255,255));
        changeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changeButton.setPreferredSize(new Dimension(70,30));
        changeButton.addActionListener(e->{sideBar();});
        changeButton.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));

        logoutIcon=loadIcon("src/main/resources/icons/Logout.png",20,20);
        logout=menuButton("Logout",logoutIcon);
        logout.setBackground(new Color(60,60,60));
        leftBar.add(changeButton);
        rightBar.add(logout);
        topBar.add(leftBar,BorderLayout.WEST);
        topBar.add(rightBar,BorderLayout.EAST);
        this.add(topBar,BorderLayout.NORTH);

        //left sidebar
        sidebar=new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar,BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(30,30,30));
        sidebar.setPreferredSize(new Dimension(180,getHeight())); //width is 200 but height same as the size defined
        sp=new JScrollPane(sidebar,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        sp.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        sp.setBorder(null); //remove the border of scroll bar
        sp.getVerticalScrollBar().setUnitIncrement(16); //the speed

        //main panel
        HomePanel=new JPanel();
        HomePanel.setBackground(Color.WHITE);
        HomePanel.setLayout(new BorderLayout());

        cardLayout=new CardLayout();
        cardPanel=new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(750,getHeight()));
        cardPanel.setMinimumSize(new Dimension(750,getHeight()));

        cardPanel.add(HomePanel, "Home");
        cardPanel.add(registerTutorForm, "registerTutor");
        cardPanel.add(registerReceptionistForm,"registerReceptionist");
        cardPanel.add(tutorView,"viewTutor");
        cardPanel.add(receptionistView,"viewReceptionist");
        cardPanel.add(viewAssignment,"viewAssignment");
        cardPanel.add(assignTutorPage,"assign");
        cardPanel.add(updateTutorPage,"update");
        controller.PageRefresh.setIncomeReport(incomeReport); //use setter to store incomeReport in the controller
        cardPanel.add(incomeReport,"income"); //use the data coming from receptionist
        cardPanel.add(adminProfile,"profile");

        initMenu();
        initHomePanel();
        this.add(sp,BorderLayout.WEST);
        this.add(cardPanel,BorderLayout.CENTER);
        this.setVisible(true);
    }
    private void initHomePanel() {
        HomePanel.setLayout(new BorderLayout());

        //welcome label
        welcomeLabel = new JLabel("Welcome, " + loginUser);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //online status
        greenDot = new JLabel("●");
        onlineStatus = new JLabel(" Online");
        onlineStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        onlineStatus.setForeground(new Color(0, 200, 0));
        greenDot.setForeground(new Color(0, 200, 0));

        //add online status to a panel
        statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        statusPanel.setOpaque(false); //no background
        statusPanel.add(greenDot);
        statusPanel.add(onlineStatus);

        //time label
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Timer timer = new Timer(1000, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | hh:mm:ss a");
            timeLabel.setText(LocalDateTime.now().format(formatter));
        });
        timer.start();

        //top panel
        centerBox = new JPanel();
        centerBox.setLayout(new BoxLayout(centerBox, BoxLayout.Y_AXIS));
        centerBox.setBackground(Color.WHITE);
        centerBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerBox.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Top padding
        centerBox.add(welcomeLabel);
        centerBox.add(Box.createVerticalStrut(10));
        centerBox.add(statusPanel);
        centerBox.add(Box.createVerticalStrut(10));
        centerBox.add(timeLabel);

        //load icon
        studentImg = loadIcon("src/main/resources/icons/student.png", 50, 50);
        tutorImg = loadIcon("src/main/resources/icons/tutor.png", 50, 50);
        receptionistImg = loadIcon("src/main/resources/icons/receptionist.png", 50, 50);

        //add the card ,flow layout auto align to center
        viewPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        viewPanel.setOpaque(false);
        viewPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        viewPanel.add(createStatCard(studentImg, "Total Students", getStudentCount()));
        viewPanel.add(createStatCard(tutorImg, "Total Tutors", getTutorCount()));
        viewPanel.add(createStatCard(receptionistImg, "Total Receptionists", getReceptionistCount()));

        //an instruction line
        commandLabel=new JLabel("Please use the left-side menu to navigate between pages.\n" +
                " Click the ☰ button to hide the menu if needed.",SwingConstants.CENTER);
        commandLabel.setFont(new Font("Segeo UI",Font.PLAIN,12));
        commandLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //combining
        mainBox = new JPanel();
        mainBox.setLayout(new BoxLayout(mainBox, BoxLayout.Y_AXIS));
        mainBox.setBackground(Color.WHITE);
        mainBox.add(centerBox);
        mainBox.add(viewPanel);
        mainBox.add(Box.createVerticalStrut(40)); //push
        mainBox.add(commandLabel);
        mainBox.add(Box.createVerticalStrut(10));

        //add to home panel
        HomePanel.add(mainBox, BorderLayout.CENTER);
    }

    private JPanel createStatCard(ImageIcon img,String title, int count) {
        card = new JPanel(new BorderLayout(20,10));
        card.setPreferredSize(new Dimension(220, 90));
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        //icon label
        iconLabel=new JLabel();
        iconLabel.setIcon(img);
//        iconLabel.setPreferredSize(new Dimension(30,30));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);

        //text panel
        textPanel=new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(245, 245, 245));

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        countLabel = new JLabel(String.valueOf(count));
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(countLabel);

        card.add(iconLabel,BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        //tally the label
        if (title.equals("Total Students")){
            studentLabel=countLabel;
        }else if (title.equals("Total Tutors")){
            tutorLabel=countLabel;
        }else if(title.equals("Total Receptionists")){
            receptionistLabel=countLabel;
        }

        return card;
    }

    private void initMenu(){
        homeIcon=loadIcon("src/main/resources/icons/home.png",20,20);
        registerIcon=loadIcon("src/main/resources/icons/register.png",20,20);
        manageIcon=loadIcon("src/main/resources/icons/manage.png",20,20);
        assIcon=loadIcon("src/main/resources/icons/assignment.png",20,20);
        incomeIcon=loadIcon("src/main/resources/icons/income.png",20,20);
        profileIcon=loadIcon("src/main/resources/icons/Profile.png",20,20);
//        logoutIcon=loadIcon("src/main/resources/icons/Logout.png",20,20);

        homeButton=menuButton("Home",homeIcon);
        registerButton=menuButton("Registration ▼",registerIcon);
        manageButton=menuButton("Manage        ▼",manageIcon);
        assButton=menuButton("Assignment  ▼",assIcon);
        incomeButton=menuButton("Financial",incomeIcon);
        profileButton=menuButton("Profile",profileIcon);
//        logout=menuButton("Logout",logoutIcon);

        //for register
        registerSub=new JPanel();
        registerSub.setLayout(new BoxLayout(registerSub,BoxLayout.Y_AXIS));
        registerSub.setBackground(new Color(40,40,40));
        registerSub.setVisible(false); //hide first

        tutorRegister=subMenuButton("Tutor");
        receptionistRegister=subMenuButton("Receptionist");
        registerSub.add(tutorRegister);
        registerSub.add(receptionistRegister);

        //for manage
        manageSub=new JPanel();
        manageSub.setLayout(new BoxLayout(manageSub,BoxLayout.Y_AXIS));
        manageSub.setBackground(new Color(40,40,40));
        manageSub.setVisible(false);

        tutorManage=subMenuButton("Tutor");
        receptionistManage=subMenuButton("Receptionist");
        manageSub.add(tutorManage);
        manageSub.add(receptionistManage);

        //for assignment
        assSub=new JPanel();
        assSub.setLayout(new BoxLayout(assSub,BoxLayout.Y_AXIS));
        assSub.setBackground(new Color(40,40,40));
        assSub.setVisible(false);

        viewTutor=subMenuButton("View Assignment");
        assignTutor=subMenuButton("Assign New Tutor");
        updateTutor=subMenuButton("Update Tutor");
        assSub.add(viewTutor);
        assSub.add(assignTutor);
        assSub.add(updateTutor);

        registerButton.addActionListener(e->{
            registerSub.setVisible(!registerSub.isVisible()); //true is mean the submenu is visible currently
            sidebar.revalidate();
        });
        manageButton.addActionListener(e->{
            manageSub.setVisible(!manageSub.isVisible());
            sidebar.revalidate();
        });
        assButton.addActionListener(e->{
            assSub.setVisible(!assSub.isVisible());
            sidebar.revalidate();
        });

        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(homeButton);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(registerButton);
        sidebar.add(registerSub); //the hidden panel
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(manageButton);
        sidebar.add(manageSub); //the hidden panel
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(assButton);
        sidebar.add(assSub); //hidden panel
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(incomeButton);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(profileButton);
        sidebar.add(Box.createVerticalStrut(15));
        sidebar.add(Box.createVerticalGlue());
//        sidebar.add(logout);
        sidebar.add(Box.createVerticalStrut(100));

        sidebar.setMinimumSize(null); //prevent BoxLayout min the high

        //the panel of the button
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"Home");
            }
        });
        tutorRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"registerTutor");
            }
        });
        receptionistRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"registerReceptionist");
            }
        });
        tutorManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"viewTutor");
            }
        });
        receptionistManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"viewReceptionist");
            }
        });
        viewTutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"viewAssignment");
            }
        });
        assignTutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"assign");
            }
        });
        updateTutor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"update");
            }
        });
        incomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"income");
            }
        });
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"profile");
            }
        });
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); //close the adminView window
                new LoginPage().setVisible(true);
            }
        });

    }
    private JButton menuButton(String title,ImageIcon image){
        button=new JButton(title);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
        button.setBackground(new Color(50,50,50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (image !=null){
            button.setIcon(image);
            button.setIconTextGap(10);
        }
        return button;
    }
    private JButton subMenuButton(String title){
        sbutton=new JButton("   " + title);
        sbutton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sbutton.setHorizontalAlignment(SwingConstants.LEFT);
        sbutton.setAlignmentX(Component.LEFT_ALIGNMENT);
        sbutton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        sbutton.setBackground(new Color(60, 60, 60));
        sbutton.setForeground(Color.WHITE);
        sbutton.setFocusPainted(false);
        sbutton.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 10));
        sbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return sbutton;
    }
    private void sideBar(){
        sideBarVisible=!sideBarVisible;
        sidebar.setVisible(sideBarVisible);

        if (sideBarVisible){
            sp.setPreferredSize(new Dimension(180,getHeight()));
        }else{
            sp.setPreferredSize(new Dimension(0,getHeight()));
        }
        revalidate(); //refresh layout
        repaint(); //redraw the layout
    }

    public AssignTutorPage getAssignTutorPage(){
        return assignTutorPage;
    }
    public UpdateTutorPage getUpdateTutorPage(){
        return updateTutorPage;
    }
    public TutorView getTutorView(){return tutorView;}
    public ReceptionistView getReceptionistView(){return receptionistView;}
    public ViewAssignment getViewAssignment(){return viewAssignment;}
    public IncomeReport getIncomeReport(){return incomeReport;}

    public void showCard(String name){
        cardLayout.show(cardPanel,name);
    }

    public int getStudentCount(){
        return Registration.readFile(StudentFile).size();
    }
    public int getTutorCount(){
        return Registration.readFile(TutorFile).size();
    }
    public int getReceptionistCount(){
        return  Registration.readFile(ReceptionistFile).size();
    }

    private ImageIcon loadIcon(String path,int width,int height){
        ImageIcon origin=new ImageIcon(path);
        Image scaledImage=origin.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

//    refresh the counting number
    public void refreshCount(){
        studentLabel.setText(String.valueOf(getStudentCount()));
        tutorLabel.setText(String.valueOf(getTutorCount()));
        receptionistLabel.setText(String.valueOf(getReceptionistCount()));
    }

    public static void changeColour(Container container){
        Color color1=new Color(222, 227, 238);
        if (container instanceof JPanel || container instanceof JRadioButton || container instanceof JCheckBox){
            container.setBackground(color1);
        }

        for (Component com:container.getComponents()){ //get all the components (JPanel, JButton, JLabel)
            if (com instanceof Container){ //if it is container (inside got stuff) ,then continue changing color
                changeColour((Container) com);
            }
        }
    }
}
