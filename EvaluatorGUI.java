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
            //Create/Reference the student (Bob)
            Student s2 = new Student("S2", "Bob", "bob@uni.edu", "ML Research", "Abstract...", "Poster");
            
            s2.setBoardID("B-001"); 

            String boardID = s2.getBoardID();
            
            String location = "Main Hall - East Wing";
            String fileStatus = "PDF Uploaded (Ready)";
            String criteria = "1. Visual Layout\n2. Clarity of Graphics\n3. Evidence of Methodology";
        
            String managementView = "--- POSTER MANAGEMENT VIEW ---\n" +
                                    "Student: " + s2.getName() + " (" + s2.getStudentId() + ")\n" +
                                    "Board ID: " + boardID + "\n" + // Changed to dynamic variable
                                    "Location: " + location + "\n" +
                                    "Submission: " + fileStatus + "\n\n" +
                                    "--- EVALUATION CRITERIA ---\n" + criteria;
        
            JTextArea textArea = new JTextArea(managementView);
            textArea.setEditable(false);
            
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
        
            String criteria = selectedRubric.equals("Poster Presentation") ? 
                "POSTER RUBRICS:\n1. Visual Appeal & Layout\n2. Content Accuracy\n3. Q&A Interaction" :
                "ORAL RUBRICS:\n1. Delivery & Clarity\n2. Slide Design\n3. Time Management";
            
            JOptionPane.showMessageDialog(this, "Evaluating: " + sName + "\n\n" + criteria);
        
            String scoreStr = JOptionPane.showInputDialog(this, "Enter Total Score (0-100):");
            
            if (scoreStr != null) {
                try {
                    int score = Integer.parseInt(scoreStr);
                    JOptionPane.showMessageDialog(this, "Score of " + score + " saved for " + sName);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Error: Invalid number.");
                }
            }
        });
    }
}
