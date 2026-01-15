import javax.swing.*;
import java.awt.*;

public class EvaluatorGUI extends JFrame {

    public EvaluatorGUI() {
        setTitle("Evaluator Module");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnReview = new JButton("Review Presentation");
        JButton btnEvaluate = new JButton("Evaluate Student");

        setLayout(new GridLayout(3, 1));
        add(new JLabel("Evaluator Functions", SwingConstants.CENTER));
        add(btnReview);
        add(btnEvaluate);

        btnReview.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Presentation reviewed (demo)"));

        btnEvaluate.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Evaluation submitted (demo)"));
    }
}
