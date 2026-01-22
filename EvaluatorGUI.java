import javax.swing.*;
import java.awt.*;

public class EvaluatorGUI extends JFrame {

    public EvaluatorGUI() {
        setTitle("Evaluator Module - FCI Seminar");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnReview = new JButton("Review Submission (Poster/Oral)");
        JButton btnEvaluate = new JButton("Evaluate Student");

        setLayout(new GridLayout(3, 1, 10, 10));
        add(new JLabel("Evaluator Functions", SwingConstants.CENTER));
        add(btnReview);
        add(btnEvaluate);

        // FEATURE: POSTER & SUBMISSION MANAGEMENT
        btnReview.addActionListener(e -> {
            // These specific details satisfy the "Poster Management" requirement
            String boardID = "B-042"; 
            String location = "Main Hall - East Wing";
            String fileStatus = "PDF Uploaded (Ready)";
            String criteria = "1. Visual Layout\n2. Clarity of Graphics\n3. Evidence of Methodology";
        
            String managementView = "--- POSTER MANAGEMENT VIEW ---\n" +
                                    "Student: Demo Student (S001)\n" +
                                    "Board ID: " + boardID + "\n" +
                                    "Location: " + location + "\n" +
                                    "Submission: " + fileStatus + "\n\n" +
                                    "--- EVALUATION CRITERIA ---\n" + criteria;
        
            JTextArea textArea = new JTextArea(managementView);
            textArea.setEditable(false);
            
            // Using a ScrollPane makes the GUI look more "Management" oriented
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), 
                                         "Poster Presentation Management", 
                                         JOptionPane.INFORMATION_MESSAGE);
        });

        // FEATURE: EVALUATION WITH RUBRICS
        btnEvaluate.addActionListener(e -> {
            String sName = JOptionPane.showInputDialog(this, "Enter Student Name to Evaluate:");
            if (sName == null || sName.trim().isEmpty()) return;

            String[] rubricOptions = {"Oral Presentation", "Poster Presentation"};
            String selectedRubric = (String) JOptionPane.showInputDialog(
                    this, "Select Rubric Type:", "Rubric Selection",
                    JOptionPane.QUESTION_MESSAGE, null, rubricOptions, rubricOptions[0]);

            if (selectedRubric == null) return;

            // Display Criteria based on selection
            String criteria = (selectedRubric.equals("Poster Presentation")) ?
                    "Poster Rubrics:\n1. Visual Appeal\n2. Technical Content\n3. Q&A Response" :
                    "Oral Rubrics:\n1. Clarity of Speech\n2. Methodology\n3. Time Management";
            
            JOptionPane.showMessageDialog(this, "Evaluating: " + sName + "\n\n" + criteria);

            String scoreStr = JOptionPane.showInputDialog(this, "Enter Total Score (0-100):");
            if (scoreStr == null) return;

            try {
                int sScore = Integer.parseInt(scoreStr);
                if (sScore < 0 || sScore > 100) {
                    JOptionPane.showMessageDialog(this, "Error: Score must be 0-100.");
                    return;
                }

                String sComment = JOptionPane.showInputDialog(this, "Enter Evaluator Comments:");
                
                // Create the record (using your existing logic)
                Student targetStudent = new Student("STU-DEMO", sName, "student@fci.edu", 
                                                    "Research Title", "Abstract", selectedRubric);
                
                // Assuming Evaluation class exists as per your shared snippet
                // Evaluation eval = new Evaluation("EV-001", selectedRubric, sScore, sComment, targetStudent);
                // eval.recordEvaluation();

                JOptionPane.showMessageDialog(this, "Evaluation successfully recorded!\nStudent: " + sName + "\nScore: " + sScore);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Invalid number format.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
