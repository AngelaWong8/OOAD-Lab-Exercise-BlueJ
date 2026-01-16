import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {
    private JRadioButton studentBtn, evaluatorBtn, coordinatorBtn;
    
    public MainMenuGUI() {
        setTitle("Seminar System");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        studentBtn = new JRadioButton("Student");
        evaluatorBtn = new JRadioButton("Evaluator");
        coordinatorBtn = new JRadioButton("Coordinator");
        
        ButtonGroup group = new ButtonGroup();
        group.add(studentBtn);
        group.add(evaluatorBtn);
        group.add(coordinatorBtn);
        
        JButton enterBtn = new JButton("Enter");
        enterBtn.addActionListener(e -> {
            if (studentBtn.isSelected()) {
                new StudentGUI().setVisible(true); 
            } else if (evaluatorBtn.isSelected()) {
                new EvaluatorGUI().setVisible(true);  
            } else if (coordinatorBtn.isSelected()) {
                // Use new login system but keep CoordinatorGUI functionality
                Coordinator coord = CoordinatorGUI.login();
                if (coord != null) new CoordinatorGUI(coord);
            } else {
                JOptionPane.showMessageDialog(this, "Select role");
            }
        });
        
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Select Role:"));
        panel.add(studentBtn);
        panel.add(evaluatorBtn);
        panel.add(coordinatorBtn);
        panel.add(enterBtn);
        
        add(panel);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new MainMenuGUI();
    }
}
