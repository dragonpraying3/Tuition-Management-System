package view.Admin;

import controller.Profile;
import controller.Registration;
import controller.ValidUser;
import model.Admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

import static controller.Registration.AdminFile;
import static controller.Registration.AccountFile;

public class AdminProfile extends JPanel implements ActionListener {
    private final String loginUsername;
    JPanel topPanel, leftPanel, rightPanel, bottomPanel, formRow, formWrapper, l_panel, r_panel;
    ImageIcon image;
    JLabel iconLabel, nameLabel, roleLabel;
    JTextField nameField, roleField, FullNameField, dobField, genderField, emailField, icField, salaryField, dateJoinedField,
             ageField, contactField, raceField;
    JPasswordField passwordField;
    JTextArea addressArea;
    JButton backButton, saveButton, resetButton,updateProfile,removeProfile,visibleButton;
    JScrollPane scrollPane;
    JComboBox<String> workStatus;
    Image scaledImage;
    ImageIcon smallIcon,defaultIcon,hidePassword,newHidePss,showPassword,newShowPss;
    static String profileDIR="src/main/resources/avatars";
    private static final String defaultProfile = "src/main/resources/icons/panda.png";
    private boolean passwordView=false;
    Font buttonFont;

    public AdminProfile(AdminView parent, String loginUser) {
        super(); //initialize the JPanel to ensure the panel function normally
        this.loginUsername=loginUser;

        Font labelFont = new Font("Serif", Font.BOLD, 16);
        Font textFont = new Font("Serif", Font.BOLD, 14);
        buttonFont=new Font("Rockwell",Font.BOLD,15);

        //find the profile of the tally admin
        String role = "";
        Admin currentAdmin = null;
        for (Admin admin : Registration.getAllAdmin()) {
            if (admin.getUsername().equalsIgnoreCase(loginUser)) {
                currentAdmin = admin;
                if (currentAdmin.getRole().equalsIgnoreCase("admin")) {
                    role = "Admin";
                }
                break;
            }
        }
        if (currentAdmin == null) {
            this.add(new JLabel("Admin not found."), BorderLayout.CENTER);
            return;
        }

        this.setLayout(new BorderLayout());

        topPanel=new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0)); //padding
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(5,10,5,10); //gap
        gbc.fill=GridBagConstraints.NONE; //will not expand the panel
        gbc.anchor=GridBagConstraints.NORTHWEST; //the panel will at northwest if no fill up the panel (must no fill)

        //at (0,0)
        String profileImagePath=getProfileImagePath(loginUsername); //use method to get the path, otherwise is origin picture
        File profileImageFile=new File(profileImagePath); //get the file path
        if (profileImageFile.exists()){
            image=new ImageIcon(profileImagePath);
        }else{
            image=new ImageIcon(defaultProfile);
        }
        scaledImage = image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        smallIcon = new ImageIcon(scaledImage);

        iconLabel=new JLabel(smallIcon);
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridheight=2;
        topPanel.add(iconLabel,gbc);

        //at (1,0)
        JPanel usernamePanel =new JPanel(new FlowLayout(FlowLayout.LEFT,5,2));
        nameLabel = new JLabel("Username : ", SwingConstants.RIGHT);
        nameLabel.setPreferredSize(new Dimension(120, 28));
        nameField = new JTextField(15);
        nameField.setEditable(false);
        nameField.setPreferredSize(new Dimension(200, 28));
        nameField.setFont(textFont);
        nameLabel.setFont(labelFont);
        usernamePanel.add(nameLabel);
        usernamePanel.add(nameField);
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridheight=1;
        topPanel.add(usernamePanel,gbc);

        //at (1,1)
        JPanel rolePanel=new JPanel(new FlowLayout(FlowLayout.LEFT,5,2));
        roleLabel = new JLabel("Role : ");
        roleLabel.setPreferredSize(new Dimension(120, 28));
        roleLabel.setHorizontalAlignment(SwingConstants.RIGHT); //push the role label to align right
        roleField = new JTextField(15);
        roleField.setEditable(false);
        roleField.setPreferredSize(new Dimension(200, 28));
        roleField.setFont(textFont);
        roleLabel.setFont(labelFont);
        rolePanel.add(roleLabel);
        rolePanel.add(roleField);
        gbc.gridx=1;
        gbc.gridy=1;
        topPanel.add(rolePanel,gbc);

        //set text
        nameField.setText(currentAdmin.getUsername());
        roleField.setText(role);

        //at (2,0)
        JPanel profilePanel=new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel,BoxLayout.Y_AXIS));

        //the button
        updateProfile = new JButton("Change Photo");
        formatButton(updateProfile);
        updateProfile.setBackground(new Color(52, 152, 219));
        updateProfile.setForeground(new Color(255,255,255));

        removeProfile = new JButton("Remove Photo");
        formatButton(removeProfile);
        removeProfile.setBackground(new Color(	220, 53, 69));
        removeProfile.setForeground(new Color(255,255,255));

        profilePanel.add(updateProfile);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(removeProfile);
        gbc.gridx=2;
        gbc.gridy=0;
        gbc.gridheight=2; //fll 2 row
        gbc.anchor=GridBagConstraints.NORTH; //fill to north
        gbc.insets=new Insets(0,200,5,0); //the gap between username ,role and button
        topPanel.add(profilePanel,gbc);

        this.add(topPanel,BorderLayout.NORTH);

        updateProfile.addActionListener(this);
        removeProfile.addActionListener(this);

        JLabel empty=new JLabel("");

        //left panel
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(createLabelField("Full Name :", FullNameField = new JTextField(17),empty));
        leftPanel.add(createLabelField("Date of Birth :", dobField = new JTextField(17),empty));
        leftPanel.add(createLabelField("Gender :", genderField = new JTextField(17),empty));
        leftPanel.add(createLabelField("Email :", emailField = new JTextField(17),empty));
        leftPanel.add(createLabelField("IC / Passport :", icField = new JTextField(17),empty));
        leftPanel.add(createLabelField("Salary :", salaryField = new JTextField(17),empty));
        leftPanel.add(createLabelField("Date Joined :", dateJoinedField = new JTextField(17),empty));

        //address area
        addressArea = new JTextArea(4, 20);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        addressArea.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane = new JScrollPane(addressArea);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10,Integer.MAX_VALUE));

        //work status combo box
        String[] status={"Active","Inactive","On Leave","Terminated","Suspended"};
        workStatus=new JComboBox<>(status);

        //the password eye picture
        hidePassword=new ImageIcon("src/main/resources/icons/showPSS.png");
        Image hidePss=hidePassword.getImage().getScaledInstance(16,20,Image.SCALE_SMOOTH);
        newHidePss=new ImageIcon(hidePss);

        showPassword=new ImageIcon("src/main/resources/icons/hidePSS.png");
        Image showPss=showPassword.getImage().getScaledInstance(16,20,Image.SCALE_SMOOTH);
        newShowPss=new ImageIcon(showPss);

        visibleButton=new JButton();
        formatButton(visibleButton);
        visibleButton.setIcon(newShowPss);

        //right panel
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(createLabelField("Password :", passwordField = new JPasswordField(17),visibleButton));
        rightPanel.add(createLabelField("Age :", ageField = new JTextField(17),empty));
        rightPanel.add(createLabelField("Contact :", contactField = new JTextField(17),empty));
        rightPanel.add(createLabelField("Race :", raceField = new JTextField(17),empty));
        rightPanel.add(createLabelField("Address :",scrollPane,empty));
        rightPanel.add(createLabelField("Work Status :",workStatus,empty));

        formRow = new JPanel();
        formRow.setLayout(new BoxLayout(formRow, BoxLayout.X_AXIS));
        formRow.add(leftPanel);
        formRow.add(Box.createRigidArea(new Dimension(80, 0))); //gap between left and right panel
        formRow.add(rightPanel);

        formWrapper = new JPanel(new GridBagLayout());
        formWrapper.add(formRow);
        this.add(formWrapper);

        displayInfo(loginUsername); //display the tally admin information
        readOnly(dobField,genderField,icField,salaryField,dateJoinedField,ageField,raceField); //set the non-editable field

        //bottom panel
        bottomPanel = new JPanel(new BorderLayout());
        l_panel=new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        r_panel=new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(90,40));
        backButton.setBackground(new Color(168, 169, 173));
        backButton.setForeground(new Color(255,255,255));
        formatButton(backButton);

        saveButton = new JButton("Save Changes");
        saveButton.setEnabled(false);
        saveButton.setPreferredSize(new Dimension(150,40));
        saveButton.setBackground(new Color(26, 162, 96));
        saveButton.setForeground(new Color(255,255,255));
        formatButton(saveButton);

        resetButton = new JButton("Discard");
        resetButton.setEnabled(false);
        resetButton.setPreferredSize(new Dimension(110,40));
        resetButton.setBackground(new Color(200, 63, 73));
        resetButton.setForeground(new Color(255,255,255));
        formatButton(resetButton);

        l_panel.add(backButton);
        r_panel.add(saveButton);
        r_panel.add(resetButton);

        bottomPanel.add(l_panel,BorderLayout.WEST);
        bottomPanel.add(r_panel,BorderLayout.EAST);
        this.add(bottomPanel,BorderLayout.SOUTH);
        AdminView.changeColour(this);

        //add change listener to all changeable field
        changeListener(FullNameField);
        changeListener(emailField);
        changeListener(passwordField);
        changeListener(contactField);

        addressArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {enabledItem();}
            @Override
            public void removeUpdate(DocumentEvent e) {enabledItem();}
            @Override
            public void changedUpdate(DocumentEvent e) {enabledItem();}
        });

        workStatus.addActionListener(e -> enabledItem());

        visibleButton.addActionListener(e->{
            passwordView=!passwordView;

            if(passwordView){ //show the password
                passwordField.setEchoChar((char)0); //strongly convert the symbol to origin word
                visibleButton.setIcon(newHidePss);
            }else{
                passwordField.setEchoChar('●');
                visibleButton.setIcon(newShowPss);
            }
        });
        resetButton.addActionListener(e->{
            displayInfo(loginUsername);
            saveButton.setEnabled(false);
            resetButton.setEnabled(false);
        });

        backButton.addActionListener(e -> {
            displayInfo(loginUsername);
            parent.showCard("Home");
            saveButton.setEnabled(false);
            resetButton.setEnabled(false);
        });

        saveButton.addActionListener(e->{
            String name=FullNameField.getText();
            String password=passwordField.getText();
            String email=emailField.getText();
            String contact=contactField.getText();
            String address=addressArea.getText();
            String work=(String) workStatus.getSelectedItem();

            Profile.updateAdminProfile(loginUsername,name,password,contact,email,address,work);

            StringBuilder errorMessage=validateField(name,password,contact,email,address);
            if (!errorMessage.isEmpty()){
                JOptionPane.showMessageDialog(this, errorMessage.toString(), "Validation Errors", JOptionPane.ERROR_MESSAGE);
            }else{
                String result=Profile.updateAdminProfile(loginUsername,name,password,contact,email,address,work);
                if (result.equals("Success")){
                    JOptionPane.showMessageDialog(this, "Admin Profile Update Successfully");
                    displayInfo(loginUsername);
                    saveButton.setEnabled(false);
                    resetButton.setEnabled(false);
                }else {
                    JOptionPane.showMessageDialog(this, result, "Update Profile Issue", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private void formatButton(JButton button){
        button.setFont(buttonFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == updateProfile) {
            JFileChooser fileChooser = new JFileChooser();

            //set image file filter
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Image Files (*.jpg,*.png,*.jpeg,*.gif)", "jpg", "png", "jpeg", "gif"
            );
            fileChooser.setFileFilter(filter);

            //bring to default directory to open file
            int response = fileChooser.showOpenDialog(null); //select file to open

            //if user no click close or cancel button
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                String newFileName=loginUsername+".png"; //force png for consistent output
                File profileDir=new File(profileDIR); //the file path
                profileDir.mkdirs(); //if no exist then open a directory

                //delete old profile images
                File[] existingFiles =profileDir.listFiles((dir,name)->name.startsWith(loginUsername+".")&&
                        (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || (name.endsWith(".gif"))));
                if (existingFiles !=null){
                    for (File existingFile:existingFiles){
                        existingFile.delete();
                    }
                }

                //avatars/admin.png
                File dest=new File(profileDir,newFileName); //overwrite the path to save image

                try{
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image scaledImage = bufferedImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    ImageIcon newIcon = new ImageIcon(scaledImage);

                    //convert ImageIcon to Buffered Image
                    //create a blank image in memory with the newIcon width and height. the TYPE_INT_ARGB is supporting transparent image
                    BufferedImage scaledBuffered=new BufferedImage(newIcon.getIconWidth(),newIcon.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
                    //create graphic context ,a drawing tool
                    Graphics g= scaledBuffered.createGraphics();
                    //draw imageIcon onto buffered image
                    //null=no bound
                    newIcon.paintIcon(null,g,0,0);
                    //clean up resources associated with graphic context ,use with graphics and graphics 2d
                    g.dispose();

                    //make image round
                    BufferedImage circleIcon=makeRoundedImage(scaledBuffered,100);

                    ImageIO.write(circleIcon,"png",dest);

                    ImageIcon finalIcon=new ImageIcon(circleIcon); //converting  back to image icon
                    iconLabel.setIcon(finalIcon);

                    JOptionPane.showMessageDialog(this, "Profile photo updated successfully.");
                }catch (IOException e){
                    JOptionPane.showMessageDialog(this, "Failed to update profile photo.\n" + e.getMessage(),
                            "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (evt.getSource() == removeProfile) {
            try {
                File dir = new File(profileDIR);
                //find the tally user profile image (old)
                // 'd' is lambda only ,can put any name for the File dir object
                File[] existingFiles = dir.listFiles((d, name) -> name.startsWith(loginUsername + ".") &&
                        (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg")));
                if (existingFiles ==null ||existingFiles.length==0) {
                    JOptionPane.showMessageDialog(null, "No profile photo found to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
                }else{
                    for (File file : existingFiles) {
                        file.delete(); //delete the profile image
                    }
                    //default profile
                    BufferedImage bufferedImage = ImageIO.read(new File(defaultProfile)); //read the image
                    scaledImage = bufferedImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    defaultIcon = new ImageIcon(scaledImage);
                    iconLabel.setIcon(defaultIcon); //set the profile image icon to default

                    JOptionPane.showMessageDialog(this, "Profile photo removed.");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error removing photo: " + e.getMessage(),
                        "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private StringBuilder validateField(String fullName,String password,
                                        String contact, String email, String address) {
        StringBuilder errorMessage = new StringBuilder();

        if (ValidUser.isEmpty(fullName)) errorMessage.append("- Full Name is required.\n");
        if (ValidUser.isEmpty(password)) errorMessage.append("- Password is required.\n");
        if (ValidUser.isEmpty(contact)) errorMessage.append("- Contact is required.\n");
        if (ValidUser.isEmpty(email)) errorMessage.append("- Email is required.\n");
        if (ValidUser.isEmpty(address)) errorMessage.append("- Address is required.\n");

        if (!ValidUser.isValidName(fullName)) errorMessage.append("- Full Name format is invalid.\n");
        if (!ValidUser.isValidEmail(email)) errorMessage.append("- Email format is invalid.\n");
        if (!ValidUser.isValidContact(contact)) errorMessage.append("- Contact number must be 9-11 digits.\n");

        return errorMessage;
    }
    protected JPanel createLabelField(String labelText, JComponent field,JComponent view) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
        Font textFont = new Font("Serif", Font.BOLD, 14);
        Font label2Font = new Font("Serif", Font.BOLD, 16);

        JLabel label = new JLabel(labelText);
        label.setFont(label2Font);
        label.setPreferredSize(new Dimension(120, 28));
        field.setFont(textFont);

        if (field instanceof JScrollPane) {
            field.setPreferredSize(new Dimension(200, 60)); // Address
        } else if (field instanceof JComboBox<?>){
            field.setPreferredSize(new Dimension(200, 28)); // Work status
        } else {
            field.setPreferredSize(new Dimension(200, 28));
            field.setMaximumSize(new Dimension(200, 35));
            field.setMinimumSize(new Dimension(200, 35)); //all text field
        }

        if (view instanceof JButton){
            ((JButton) view).setContentAreaFilled(false); //no filling button colour
            ((JButton) view).setBorderPainted(false); //remove the border
        }

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(2, 0))); //gap between label and field
        panel.add(field);
        panel.add(view);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        return panel;
    }

    private void displayInfo(String username){
        List<String> adminData=Registration.readFile(AdminFile);
        List<String> accountData=Registration.readFile(AccountFile);

        for (String line:adminData){
            String[] parts=line.split(";");
            if (parts.length>=13 && parts[0].equalsIgnoreCase(username)){
                FullNameField.setText(parts[1]);
                dobField.setText(parts[2]);
                ageField.setText(parts[3]);
                genderField.setText(parts[4]);
                contactField.setText(parts[5]);
                emailField.setText(parts[6]);
                raceField.setText(parts[7]);
                icField.setText(parts[8]);
                addressArea.setText(parts[9]);
                salaryField.setText("RM "+parts[10]);
                String currentStatus=parts[11];
                workStatus.setSelectedItem(currentStatus);
                dateJoinedField.setText(parts[12]);

                for (String row:accountData){
                    String[] field=row.split(";");
                    if (field.length>=3 && field[0].equalsIgnoreCase(username)){
                        passwordField.setText(field[1]);
                        break;
                    }
                }
            }
        }
    }
    private void readOnly(JTextField... fields) { //...is Varargs ,can pass lots of argument
        for (JTextField field : fields) {
            field.setEditable(false);
            field.setBackground(Color.LIGHT_GRAY);
            field.setToolTipText("No access to edit this field.");
        }
    }
    //this method is use for checking the TextField have get any changed or not
    private void changeListener(JTextField field){
        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                enabledItem();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enabledItem();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enabledItem();
            }
        });
    }

    private void enabledItem(){
        saveButton.setEnabled(true);
        resetButton.setEnabled(true);
    }
    private String getProfileImagePath(String username){
        String[] extensions ={".jpg", ".png", ".jpeg", ".gif"};
        for (String ext:extensions){
            File file=new File(profileDIR+File.separator+username+ext);
            if (file.exists()){
                return file.getAbsolutePath();
            }
        }
        return defaultProfile;
    }
    /**
     * Creates a circle image
     *
     * @param source The original BufferedImage to be transformed.
     * @param size   The width and height of the output image in pixels (creates a square) (110x110)
     * @return A new BufferedImage with the input image clipped into a circle.
     */
    private BufferedImage makeRoundedImage(BufferedImage source, int size) {
        // Scale the source image to the desired size (width and height)
        Image scaled = source.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        // Create an output image with transparency (ARGB)
        BufferedImage output = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        // Get the graphics context for drawing
        Graphics2D g2 = output.createGraphics();
        // Enable antialiasing for smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Define a circular clipping area (a perfect circle)
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size);
        // Apply the circular clipping region to the graphics context
        g2.setClip(circle);
        // Draw the scaled image within the circular clipping area
        g2.drawImage(scaled, 0, 0, null);
        // Clean up graphics resources
        g2.dispose();
        // Return the final circular image
        return output;
    }
}
