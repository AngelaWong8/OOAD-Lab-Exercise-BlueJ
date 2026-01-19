import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CoordinatorGUI extends JFrame {
    private Coordinator coordinator;
    private JTextArea outputArea;
    
    public CoordinatorGUI(Coordinator coordinator) {
        super("Coordinator: " + coordinator.getName());
        this.coordinator = coordinator;
        setSize(650, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250);
        
        // Left panel - Actions
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel actionLabel = new JLabel("Coordinator Actions", SwingConstants.CENTER);
        actionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(actionLabel, BorderLayout.NORTH);
        
        JPanel actionPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        
        // Create buttons without dynamic counts
        JButton createSessionBtn = new JButton("📋 Create Session");
        JButton viewSessionsBtn = new JButton("👁️ View Sessions");
        JButton viewScheduleBtn = new JButton("📅 View Schedule");
        JButton manageAwardsBtn = new JButton("🎯 Manage Awards");
        JButton generateReportBtn = new JButton("📊 Generate Final Report");
        JButton showAnalyticsBtn = new JButton("📈 View Analytics");
        JButton exportReportsBtn = new JButton("📤 Export Reports");
        JButton profileBtn = new JButton("👤 Profile");
        JButton logoutBtn = new JButton("🚪 Logout");
        
        // Add action listeners
        createSessionBtn.addActionListener(e -> createSession());
        viewSessionsBtn.addActionListener(e -> viewSessions());
        viewScheduleBtn.addActionListener(e -> viewSchedule());
        manageAwardsBtn.addActionListener(e -> manageAwards());
        generateReportBtn.addActionListener(e -> generateFinalReport());
        showAnalyticsBtn.addActionListener(e -> showAnalytics());
        exportReportsBtn.addActionListener(e -> exportReports());
        profileBtn.addActionListener(e -> showProfile());
        logoutBtn.addActionListener(e -> dispose());
        
        // Add buttons to panel
        actionPanel.add(createSessionBtn);
        actionPanel.add(viewSessionsBtn);
        actionPanel.add(viewScheduleBtn);
        actionPanel.add(manageAwardsBtn);
        actionPanel.add(generateReportBtn);
        actionPanel.add(showAnalyticsBtn);
        actionPanel.add(exportReportsBtn);
        actionPanel.add(profileBtn);
        actionPanel.add(logoutBtn);
        
        leftPanel.add(actionPanel, BorderLayout.CENTER);
        
        // Right panel - Output
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setText("Welcome, " + coordinator.getName() + "!\n\n" +
                          "ID: " + coordinator.getUserId() + "\n" +
                          "Email: " + coordinator.getEmail() + "\n" +
                          "Managed Sessions: " + coordinator.getManagedSessions().size() + "\n" +
                          "Active Awards: " + coordinator.getAwards().size());
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(new JScrollPane(outputArea));
        add(splitPane);
        setVisible(true);
    }
    
    private void createSession() {
        JTextField idField = new JTextField("SESS-" + (int)(Math.random()*1000));
        JTextField dateField = new JTextField(new java.text.SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        JTextField timeField = new JTextField("09:00 AM");
        JTextField venueField = new JTextField("Main Hall");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Oral", "Poster"});
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Session ID:")); panel.add(idField);
        panel.add(new JLabel("Date (dd/mm/yyyy):")); panel.add(dateField);
        panel.add(new JLabel("Time (hh:mm AM/PM):")); panel.add(timeField);
        panel.add(new JLabel("Venue:")); panel.add(venueField);
        panel.add(new JLabel("Type:")); panel.add(typeBox);
        
        if (JOptionPane.showConfirmDialog(this, panel, "Create Session", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Date sessionDate = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm a").parse(dateField.getText() + " " + timeField.getText());
                coordinator.createSession(idField.getText(), sessionDate, venueField.getText(), (String)typeBox.getSelectedItem());
                outputArea.append("\n✓ Session created: " + idField.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void viewSessions() {
        if (coordinator.getManagedSessions().isEmpty()) {
            outputArea.setText("No sessions created yet.");
            return;
        }
        StringBuilder sb = new StringBuilder("=== MANAGED SESSIONS ===\n\n");
        sb.append("Total Sessions: ").append(coordinator.getManagedSessions().size()).append("\n\n");
        
        int i = 1;
        for (Session s : coordinator.getManagedSessions()) {
            sb.append(i++).append(". ").append(s.toString()).append("\n\n");
        }
        outputArea.setText(sb.toString());
    }
    
    private void viewSchedule() {
        outputArea.setText("=== SEMINAR SCHEDULE ===\n\n");
        outputArea.append(coordinator.generateSchedule());
    }
    
    private void manageAwards() {
        String[] options = {"Create Award", "View Awards", "Generate Winners", "Award Ceremony Agenda"};
        String choice = (String) JOptionPane.showInputDialog(this, 
            "Select Award Management Action:", 
            "Award Oversight", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]);
        
        if (choice == null) return;
        
        switch(choice) {
            case "Create Award":
                createAward();
                break;
            case "View Awards":
                viewAwards();
                break;
            case "Generate Winners":
                generateWinners();
                break;
            case "Award Ceremony Agenda":
                showAwardCeremonyAgenda();
                break;
        }
    }
    
    private void createAward() {
        String[] awardTypes = {Award.BEST_ORAL, Award.BEST_POSTER, Award.PEOPLES_CHOICE};
        String awardName = (String) JOptionPane.showInputDialog(this, 
            "Select award type:", 
            "Create Award", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            awardTypes, 
            awardTypes[0]);
        
        if (awardName != null) {
            String criteria = JOptionPane.showInputDialog(this, "Enter award criteria:");
            if (criteria != null && !criteria.trim().isEmpty()) {
                Award newAward = coordinator.createAward(awardName, criteria);
                outputArea.append("\n✓ Award created: " + awardName + " - " + criteria);
                
                // Add nominees for People's Choice
                if (awardName.equals(Award.PEOPLES_CHOICE)) {
                    Student s1 = new Student("S1", "Alice", "alice@uni.edu", 
                        "AI Research", "Abstract on AI", "Oral");
                    Student s2 = new Student("S2", "Bob", "bob@uni.edu", 
                        "ML Research", "Abstract on ML", "Poster");
                    Student s3 = new Student("S3", "Charlie", "charlie@uni.edu", 
                        "Data Science", "Abstract on DS", "Oral");
                    
                    newAward.addNominee(s1);
                    newAward.addNominee(s2);
                    newAward.addNominee(s3);
                    outputArea.append("\n✓ Added 3 nominees for People's Choice award");
                }
            }
        }
    }
    
    private void viewAwards() {
        if (coordinator.getAwards().isEmpty()) {
            outputArea.setText("No awards created yet.");
            return;
        }
        
        StringBuilder sb = new StringBuilder("=== AWARDS OVERVIEW ===\n\n");
        sb.append("Total Awards: ").append(coordinator.getAwards().size()).append("\n\n");
        
        int i = 1;
        for (Award award : coordinator.getAwards()) {
            sb.append(i++).append(". 🏆 ").append(award.getAwardName()).append("\n");
            sb.append("   ID: ").append(award.getAwardId()).append("\n");
            sb.append("   Criteria: ").append(award.getCriteria()).append("\n");
            sb.append("   Winner: ").append(award.getWinner() != null ? award.getWinner().getName() : "Not assigned").append("\n");
            
            if (award.getAwardName().equals(Award.PEOPLES_CHOICE)) {
                sb.append("   Nominees: ");
                for (Student nominee : award.getNominees()) {
                    sb.append(nominee.getName()).append(", ");
                }
                if (!award.getNominees().isEmpty()) {
                    sb.delete(sb.length()-2, sb.length());
                }
                sb.append("\n   Total Votes: ").append(award.getVoteCount()).append("\n");
            }
            sb.append("\n");
        }
        outputArea.setText(sb.toString());
    }
    
    private void generateWinners() {
        // Create pre-set data for demonstration
        Student s1 = new Student("S1", "Alice", "alice@uni.edu", 
            "AI Research", "Abstract on AI", "Oral");
        Student s2 = new Student("S2", "Bob", "bob@uni.edu", 
            "ML Research", "Abstract on ML", "Poster");
        Student s3 = new Student("S3", "Charlie", "charlie@uni.edu", 
            "Data Science", "Abstract on DS", "Oral");
        
        Evaluation e1 = new Evaluation("E1", "Rubric", 88, "Excellent presentation", s1);
        Evaluation e2 = new Evaluation("E2", "Rubric", 92, "Outstanding poster", s2);
        Evaluation e3 = new Evaluation("E3", "Rubric", 85, "Good research", s3);
        
        List<Evaluation> evaluations = Arrays.asList(e1, e2, e3);
        
        outputArea.setText("=== GENERATING AWARD WINNERS ===\n\n");
        outputArea.append("Using pre-set evaluation data:\n");
        outputArea.append("1. Alice (Oral) - 88/100\n");
        outputArea.append("2. Bob (Poster) - 92/100\n");
        outputArea.append("3. Charlie (Oral) - 85/100\n\n");
        
        for (Award award : coordinator.getAwards()) {
            if (award.getAwardName().equals(Award.PEOPLES_CHOICE)) {
                // Simulate votes for People's Choice
                award.addVote("V1", s1);
                award.addVote("V2", s1);
                award.addVote("V3", s2);
                award.addVote("V4", s1);
                award.addVote("V5", s3);
                award.calculatePeoplesChoiceWinner();
                outputArea.append("🏆 People's Choice Award:\n");
                outputArea.append("   Total Votes: " + award.getVoteCount() + "\n");
            } else {
                award.calculateWinner(evaluations);
                outputArea.append("🏆 " + award.getAwardName() + ":\n");
            }
            
            if (award.getWinner() != null) {
                outputArea.append("   Winner: " + award.getWinner().getName() + "\n");
                
                // Find winner's score
                for (Evaluation e : evaluations) {
                    if (e.getStudent().equals(award.getWinner())) {
                        outputArea.append("   Score: " + e.calculateTotalScore() + "/100\n");
                        break;
                    }
                }
            } else {
                outputArea.append("   No winner assigned\n");
            }
            outputArea.append("\n");
        }
        
        outputArea.append("✓ All award winners have been computed!");
    }
    
    private void showAwardCeremonyAgenda() {
        StringBuilder agenda = new StringBuilder();
        agenda.append("=== AWARD CEREMONY AGENDA ===\n\n");
        agenda.append("Date: 15th January 2026\n");
        agenda.append("Time: 7:00 PM - 9:00 PM\n");
        agenda.append("Venue: FCI Grand Auditorium\n\n");
        agenda.append("AGENDA:\n");
        agenda.append("1. 7:00 PM - Welcome Address by Dean\n");
        agenda.append("2. 7:15 PM - Keynote Speech\n");
        agenda.append("3. 7:45 PM - Award Presentations:\n\n");
        
        if (coordinator.getAwards().isEmpty()) {
            agenda.append("   No awards scheduled yet.\n");
        } else {
            int awardNumber = 1;
            for (Award award : coordinator.getAwards()) {
                agenda.append("   ").append(awardNumber++).append(". ");
                agenda.append(award.getAwardName()).append("\n");
                
                if (award.getWinner() != null) {
                    agenda.append("      Winner: ").append(award.getWinner().getName()).append("\n");
                    agenda.append("      Presented by: Faculty Coordinator\n");
                } else {
                    agenda.append("      (To be announced)\n");
                }
                agenda.append("\n");
            }
        }
        
        agenda.append("4. 8:30 PM - Closing Remarks\n");
        agenda.append("5. 8:45 PM - Reception & Networking\n");
        
        outputArea.setText(agenda.toString());
    }
    
    private void generateFinalReport() {
        // Create pre-set data
        Student s1 = new Student("S1", "Alice", "alice@uni.edu", 
            "AI Research", "Abstract on AI", "Oral");
        Student s2 = new Student("S2", "Bob", "bob@uni.edu", 
            "ML Research", "Abstract on ML", "Poster");
        Student s3 = new Student("S3", "Charlie", "charlie@uni.edu", 
            "Data Science", "Abstract on DS", "Oral");
        
        Evaluation e1 = new Evaluation("E1", "Rubric", 88, "Excellent presentation", s1);
        Evaluation e2 = new Evaluation("E2", "Rubric", 92, "Outstanding poster", s2);
        Evaluation e3 = new Evaluation("E3", "Rubric", 85, "Good research", s3);
        
        List<Evaluation> evaluations = Arrays.asList(e1, e2, e3);
        
        // Create report
        Report report = new Report("R001", Report.SEMINAR_FINAL, evaluations, coordinator.getAwards());
        String reportContent = report.generate();
        
        // Display report
        outputArea.setText(reportContent);
        
        // Ask if user wants to export
        int response = JOptionPane.showConfirmDialog(this, 
            "Report generated successfully!\n\nWould you like to export as a file?", 
            "Export Report", 
            JOptionPane.YES_NO_OPTION);
        
        if (response == JOptionPane.YES_OPTION) {
            exportReportToFile(reportContent);
        }
    }
    
    private void showAnalytics() {
        // Create pre-set data for analytics
        Student s1 = new Student("S1", "Alice", "alice@uni.edu", 
            "AI Research", "Abstract on AI", "Oral");
        Student s2 = new Student("S2", "Bob", "bob@uni.edu", 
            "ML Research", "Abstract on ML", "Poster");
        Student s3 = new Student("S3", "Charlie", "charlie@uni.edu", 
            "Data Science", "Abstract on DS", "Oral");
        
        Evaluation e1 = new Evaluation("E1", "Rubric", 88, "Excellent presentation", s1);
        Evaluation e2 = new Evaluation("E2", "Rubric", 92, "Outstanding poster", s2);
        Evaluation e3 = new Evaluation("E3", "Rubric", 85, "Good research", s3);
        
        List<Evaluation> evaluations = Arrays.asList(e1, e2, e3);
        
        // Calculate analytics
        double totalScore = 0;
        double maxScore = -1;
        double minScore = 101;
        int oralCount = 0, posterCount = 0;
        Student topStudent = null;
        
        for (Evaluation e : evaluations) {
            double score = e.calculateTotalScore();
            totalScore += score;
            
            if (score > maxScore) {
                maxScore = score;
                topStudent = e.getStudent();
            }
            if (score < minScore) {
                minScore = score;
            }
            
            if ("Oral".equalsIgnoreCase(e.getStudent().getPresentationType())) {
                oralCount++;
            } else {
                posterCount++;
            }
        }
        
        double averageScore = totalScore / evaluations.size();
        int awardsAssigned = 0;
        for (Award a : coordinator.getAwards()) {
            if (a.isAwardAssigned()) awardsAssigned++;
        }
        
        // Display analytics
        StringBuilder analytics = new StringBuilder();
        analytics.append("=== DATA ANALYTICS ===\n\n");
        analytics.append("📊 PERFORMANCE METRICS:\n");
        analytics.append("Total Evaluations: ").append(evaluations.size()).append("\n");
        analytics.append(String.format("Average Score: %.2f/100\n", averageScore));
        analytics.append(String.format("Highest Score: %.1f (%s)\n", maxScore, 
            topStudent != null ? topStudent.getName() : "N/A"));
        analytics.append(String.format("Lowest Score: %.1f\n\n", minScore));
        
        analytics.append("📊 PRESENTATION DISTRIBUTION:\n");
        analytics.append("Oral Presentations: ").append(oralCount).append("\n");
        analytics.append("Poster Presentations: ").append(posterCount).append("\n");
        analytics.append("Total Participants: ").append(evaluations.size()).append("\n\n");
        
        analytics.append("📊 AWARD STATUS:\n");
        analytics.append("Total Awards: ").append(coordinator.getAwards().size()).append("\n");
        analytics.append("Awards Assigned: ").append(awardsAssigned).append("\n");
        analytics.append("Pending Assignment: ").append(coordinator.getAwards().size() - awardsAssigned).append("\n\n");
        
        analytics.append("📊 SCORE DISTRIBUTION:\n");
        int[] scoreRanges = new int[5]; // 0-59, 60-69, 70-79, 80-89, 90-100
        for (Evaluation e : evaluations) {
            double score = e.calculateTotalScore();
            if (score < 60) scoreRanges[0]++;
            else if (score < 70) scoreRanges[1]++;
            else if (score < 80) scoreRanges[2]++;
            else if (score < 90) scoreRanges[3]++;
            else scoreRanges[4]++;
        }
        
        analytics.append(String.format("  0-59 : %2d students\n", scoreRanges[0]));
        analytics.append(String.format("  60-69: %2d students\n", scoreRanges[1]));
        analytics.append(String.format("  70-79: %2d students\n", scoreRanges[2]));
        analytics.append(String.format("  80-89: %2d students\n", scoreRanges[3]));
        analytics.append(String.format("  90-100: %2d students\n", scoreRanges[4]));
        
        outputArea.setText(analytics.toString());
    }
    
    private void exportReports() {
        String[] options = {"Export Final Report (TXT)", "Export Analytics (TXT)", "Export CSV", "Export All"};
        String choice = (String) JOptionPane.showInputDialog(this, 
            "Select Export Format:", 
            "Export Reports", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]);
        
        if (choice == null) return;
        
        // Create pre-set data
        Student s1 = new Student("S1", "Alice", "alice@uni.edu", 
            "AI Research", "Abstract on AI", "Oral");
        Student s2 = new Student("S2", "Bob", "bob@uni.edu", 
            "ML Research", "Abstract on ML", "Poster");
        Student s3 = new Student("S3", "Charlie", "charlie@uni.edu", 
            "Data Science", "Abstract on DS", "Oral");
        
        Evaluation e1 = new Evaluation("E1", "Rubric", 88, "Excellent presentation", s1);
        Evaluation e2 = new Evaluation("E2", "Rubric", 92, "Outstanding poster", s2);
        Evaluation e3 = new Evaluation("E3", "Rubric", 85, "Good research", s3);
        
        List<Evaluation> evaluations = Arrays.asList(e1, e2, e3);
        
        Report report = new Report("R001", Report.SEMINAR_FINAL, evaluations, coordinator.getAwards());
        
        switch(choice) {
            case "Export Final Report (TXT)":
                exportReportToFile(report.generate());
                break;
            case "Export Analytics (TXT)":
                showAnalytics();
                String analyticsText = outputArea.getText();
                exportToFile(analyticsText, "analytics_report.txt");
                break;
            case "Export CSV":
                exportToFile(report.generateCSV(), "seminar_data.csv");
                break;
            case "Export All":
                exportReportToFile(report.generate());
                showAnalytics();
                exportToFile(outputArea.getText(), "analytics_report.txt");
                exportToFile(report.generateCSV(), "seminar_data.csv");
                JOptionPane.showMessageDialog(this, "All reports exported successfully!");
                break;
        }
    }
    
    private void exportReportToFile(String reportContent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setSelectedFile(new java.io.File("Seminar_Final_Report.txt"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            exportToFile(reportContent, fileToSave.getAbsolutePath());
        }
    }
    
    private void exportToFile(String content, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(content);
            JOptionPane.showMessageDialog(this, 
                "File exported successfully!\nLocation: " + filename, 
                "Export Complete", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error exporting file: " + ex.getMessage(), 
                "Export Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showProfile() {
        JOptionPane.showMessageDialog(this, 
            "=== COORDINATOR PROFILE ===\n\n" +
            "ID: " + coordinator.getUserId() + "\n" +
            "Name: " + coordinator.getName() + "\n" +
            "Email: " + coordinator.getEmail() + "\n" +
            "Managed Sessions: " + coordinator.getManagedSessions().size() + "\n" +
            "Active Awards: " + coordinator.getAwards().size(), 
            "Profile", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Original login method
    public static Coordinator login() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        JRadioButton rb1 = new JRadioButton("Dr. Smith (C001)");
        JRadioButton rb2 = new JRadioButton("Prof. Johnson (C002)");
        JRadioButton rb3 = new JRadioButton("Ms. Williams (C003)");
        JRadioButton rb4 = new JRadioButton("➕ New Coordinator");
        
        ButtonGroup group = new ButtonGroup();
        group.add(rb1); group.add(rb2); group.add(rb3); group.add(rb4);
        rb1.setSelected(true);
        panel.add(rb1); panel.add(rb2); panel.add(rb3); panel.add(rb4);
        
        if (JOptionPane.showConfirmDialog(null, panel, "Coordinator Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) 
            return null;
        
        if (rb1.isSelected()) return new Coordinator("C001", "Dr. Smith", "smith@uni.edu");
        if (rb2.isSelected()) return new Coordinator("C002", "Prof. Johnson", "johnson@uni.edu");
        if (rb3.isSelected()) return new Coordinator("C003", "Ms. Williams", "williams@uni.edu");
        
        JPanel regPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField idField = new JTextField("C" + String.format("%03d", (int)(Math.random()*1000)));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        regPanel.add(new JLabel("ID:")); regPanel.add(idField);
        regPanel.add(new JLabel("Name:")); regPanel.add(nameField);
        regPanel.add(new JLabel("Email:")); regPanel.add(emailField);
        
        if (JOptionPane.showConfirmDialog(null, regPanel, "New Coordinator", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) 
            return null;
        
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name required", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String email = emailField.getText().trim();
        if (email.isEmpty()) email = name.toLowerCase().replace(" ", ".") + "@uni.edu";
        
        return new Coordinator(idField.getText().trim(), name, email);
    }
}
