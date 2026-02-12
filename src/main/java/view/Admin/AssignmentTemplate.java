package view.Admin;

import javax.swing.*;
import java.awt.*;

public abstract class AssignmentTemplate extends JPanel {

    protected JPanel createLabelField(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,5,5));

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 18);
        Font fieldFont = new Font("Times New Roman", Font.PLAIN, 16);

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(100, 35));

        field.setFont(fieldFont);
        field.setPreferredSize(new Dimension(190, 35));
        field.setMaximumSize(new Dimension(190, 35));
        field.setMinimumSize(new Dimension(190, 35));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(2, 0))); //gap between label and field
        panel.add(field);
        return panel;
    }
    protected void makeReadOnly(JTextField... fields) { //...is Varargs ,can pass lots of argument
        for (JTextField field : fields) {
            field.setEditable(false);
            field.setBackground(Color.LIGHT_GRAY); // optional for visual feedback
        }
    }
    protected String rulesExplain(){
        return """
                ----------Tutor Assignment Rules----------\n
                -SPM/STPM Level : Form 1 | Form 2 | Form 3\n
                -Postgraduate Diploma in Education : Form 1 | Form 2 | Form 3 | Form 4 | Form 5 \n
                -Bachelor's in Education : Form 1 | Form 2 | Form 3 | Form 4 | Form 5 \n
                -Bachelor's in Science/Arts : Form 4 | Form 5 \n
                -Master’s Degree : Form 4 | Form 5 \n
                -PhD : Form 4 | Form 5 \n
                -Teaching Certification : Form 1 | Form 2 | Form 3 | Form 4 | Form 5 \n
                -TESL/TEFL Certification : Form 1 | Form 2 | Form 3 | Form 4 | Form 5
                """;
    }
}
