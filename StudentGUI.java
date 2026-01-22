import javax.swing.*;
import java.awt.*;
import java.io.File;

public class StudentGUI extends JFrame {

    public StudentGUI() {
        setTitle("Student Module");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnRegister = new JButton("Register for Seminar");
        JButton btnUpload = new JButton("Upload Presentation");
        JButton btnViewAward = new JButton("View Award");

        setLayout(new GridLayout(4, 1));
        add(new JLabel("Student Functions", SwingConstants.CENTER));
        add(btnRegister);
        add(btnUpload);
        add(btnViewAward);

        btnRegister.addActionListener(e -> {
            Student student = new Student("S001", "Demo Student", "demo@student.edu",
                                         "AI Research Project", "Research abstract here...", "Oral");
            student.register();
            JOptionPane.showMessageDialog(this,
                    "Registration submitted successfully!\n" +
                    "Student: " + student.getName() + "\n" +
                    "Research: " + student.getResearchTitle());
        });

        /**btnUpload.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Presentation File");
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                String[] options = {"Oral", "Poster"};
                String presentationType = (String) JOptionPane.showInputDialog(
                    this,
                    "Select presentation type:",
                    "Presentation Type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
                );
                
                if (presentationType != null) {

                    Student currentStudent = new Student("S001", "Demo Student", "demo@student.edu",
                                                        "AI Research", "Abstract...", presentationType);
                    
                    currentStudent.uploadPresentation(filePath, presentationType);
                    
                    JOptionPane.showMessageDialog(this, 
                        "File Uploaded Successfully!\n" +
                        "File: " + selectedFile.getName() + "\n" +
                        "Type: " + presentationType + "\n" +
                        "Size: " + (selectedFile.length() / 1024) + " KB");
                }
            }
        });
        */

        //new code:
        btnUpload.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Presentation File (Slides or Poster PDF)");
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                // Logic: Update the demo student's file path
                Student currentStudent = new Student("S001", "Demo Student", "demo@student.edu",
                                                    "AI Research", "Abstract...", "Poster");
                currentStudent.uploadPresentation(filePath, "Poster");

                // Visual confirmation of the submission
                String msg = "Submission Received!\n" +
                             "File: " + selectedFile.getName() + "\n" +
                             "Path: " + filePath + "\n\n" +
                             "Your materials have been sent to evaluators.";
                
                JOptionPane.showMessageDialog(this, msg, "Upload Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });        

        btnViewAward.addActionListener(e -> {

            Student student = new Student("S001", "Demo Student", "demo@student.edu",
                                         "AI Research", "Abstract...", "Oral");
            String notification = student.receiveAwardNotification();
            
            JOptionPane.showMessageDialog(this,
                    notification + "\n\n" +
                    "Award results:\n" +
                    "• Best Oral Presentation: Pending\n" +
                    "• Best Poster: Pending\n" +
                    "• People's Choice: Pending\n\n" +
                    "Awards will be announced after evaluation.");
        });
    }
}
