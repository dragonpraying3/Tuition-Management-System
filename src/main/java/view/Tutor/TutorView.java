package view.Tutor;

import view.LoginPage;

import javax.swing.*;
import java.awt.*;



public class TutorView extends JFrame {

    private JPanel mainPanel;


    public TutorView() {
        setSize(800, 650);
        setTitle("Tutor Dashboard");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(createHomePanel(), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        //Homepage
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(createHomePanel(), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        //Menu bar
        JMenuBar menuBar = new JMenuBar();

        //Tutor Management Menu
        JMenu classMenu = new JMenu("Tutor Management");

        //Add Class
        JMenuItem addClassItem = new JMenuItem("Add Class ");


        //Add ClassPanel
        addClassItem.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(new AddClass(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        classMenu.add(addClassItem);


        //Update Class
        JMenuItem updateClassItem = new JMenuItem("Update Class");


        //Update ClassPanel
        updateClassItem.addActionListener(e ->{
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(new UpdateClass(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        classMenu.add(updateClassItem);


        //Delete Class
        JMenuItem deleteClassItem = new JMenuItem("Delete Class");

        //Delete ClassPanel
        deleteClassItem.addActionListener(e ->{
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(new DeleteClass(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        classMenu.add(deleteClassItem);

        // Enrolled Students Schedule Menu
        JMenu enrolledScheduleMenu = new JMenu("Enrolled Students");

        JMenuItem enrolledStudentsItem = new JMenuItem("Enrolled Students");
        enrolledScheduleMenu.add(enrolledStudentsItem);

        enrolledStudentsItem.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(new EnrolledStudents(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });



        //Profile
        JMenu profileMenu = new JMenu("Profile");
        JMenuItem updateProfileItem = new JMenuItem("Update Profile");

        updateProfileItem.addActionListener(e -> {
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(new TutorUpdateProfile(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        JMenuItem logoutItem = new JMenuItem("Logout");
        profileMenu.add(updateProfileItem);
        profileMenu.add(logoutItem);

        logoutItem.addActionListener(e ->{
            int confirm = JOptionPane.showConfirmDialog(
                    this, "Are you sure you want to logout?",
                    "Confirm Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(confirm == JOptionPane.YES_OPTION ){
                dispose();
                new LoginPage().setVisible(true);
            }

        });


        // Add Menus to MenuBar
        menuBar.add(classMenu);
        menuBar.add(enrolledScheduleMenu);
        menuBar.add(profileMenu);
        setJMenuBar(menuBar);
    }

    //
    private JPanel createHomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Title label - centered at top
        JLabel welcome = new JLabel("Welcome to Tutor Dashboard", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        welcome.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        panel.add(welcome, BorderLayout.NORTH);

        // Load image icon from resources folder (make sure path is correct)
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/tutor.jpg"));
        // Resize image to bigger size
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImg);

        JLabel imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(imageLabel, BorderLayout.CENTER);

        panel.setBackground(Color.WHITE); // Set background white or any color you like

        return panel;
    }



    public void showHomePanel() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(createHomePanel(), BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();

    }
}
