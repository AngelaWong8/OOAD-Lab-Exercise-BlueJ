import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {

    private JRadioButton studentBtn, evaluatorBtn, coordinatorBtn;
    private JButton enterBtn;

    public MainMenuGUI() {
        setTitle("Seminar Management System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        studentBtn = new JRadioButton("Student");
        evaluatorBtn = new JRadioButton("Evaluator");
        coordinatorBtn = new JRadioButton("Coordinator");

        ButtonGroup group = new ButtonGroup();
        group.add(studentBtn);
        group.add(evaluatorBtn);
        group.add(coordinatorBtn);

        enterBtn = new JButton("Enter System");

        enterBtn.addActionListener(e -> {
            if (studentBtn.isSelected()) {
                new StudentGUI().setVisible(true);
            } else if (evaluatorBtn.isSelected()) {
                new EvaluatorGUI().setVisible(true);
            } else if (coordinatorBtn.isSelected()) {
                new CoordinatorGUI().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Please select a role.");
            }
        });

        setLayout(new GridLayout(5,1));
        add(new JLabel("Select Role", SwingConstants.CENTER));
        add(studentBtn);
        add(evaluatorBtn);
        add(coordinatorBtn);
        add(enterBtn);
    }

    public static void main(String[] args) {
        new MainMenuGUI().setVisible(true);
    }
}
