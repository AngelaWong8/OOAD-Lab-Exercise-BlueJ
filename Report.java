import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class Report {
    private String reportId;
    private String reportType; // "Evaluation", "Award", "Summary", "Attendance"
    private Date generateDate;
    private List<Evaluation> evaluations;
    private List<Award> awards;
    private List<Student> participants;
    private String sessionInfo;
    private String coordinatorNotes;
    
    public Report(String reportId, String reportType,
                  List<Evaluation> evaluations, List<Award> awards) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.evaluations = evaluations;
        this.awards = awards;
        this.generateDate = new Date();
        this.participants = new ArrayList<>();
        this.coordinatorNotes = "";
    }
    
    public Report(String reportId, String reportType,
                  List<Evaluation> evaluations, List<Award> awards,
                  List<Student> participants, String sessionInfo) {
        this(reportId, reportType, evaluations, awards);
        this.participants = participants;
        this.sessionInfo = sessionInfo;
    }
    
    // Generate comprehensive report based on type
    public String generate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        StringBuilder report = new StringBuilder();
        
        report.append("=== SEMINAR MANAGEMENT SYSTEM REPORT ===\n");
        report.append("Report ID: ").append(reportId).append("\n");
        report.append("Report Type: ").append(reportType).append("\n");
        report.append("Generated Date: ").append(sdf.format(generateDate)).append("\n");
        report.append("=========================================\n\n");
        
        switch (reportType) {
            case "Evaluation":
                generateEvaluationReport(report);
                break;
            case "Award":
                generateAwardReport(report);
                break;
            case "Summary":
                generateSummaryReport(report);
                break;
            case "Attendance":
                generateAttendanceReport(report);
                break;
            default:
                generateComprehensiveReport(report);
        }
        
        if (!coordinatorNotes.isEmpty()) {
            report.append("\n\nCoordinator Notes:\n");
            report.append(coordinatorNotes).append("\n");
        }
        
        report.append("\n=== END OF REPORT ===\n");
        return report.toString();
    }
    
    private void generateEvaluationReport(StringBuilder report) {
        report.append("EVALUATION RESULTS\n");
        report.append("==================\n");
        
        int totalEvaluations = evaluations.size();
        double totalScore = 0;
        
        for (Evaluation e : evaluations) {
            Student s = e.getStudent();
            double score = e.calculateTotalScore();
            totalScore += score;
            
            report.append("\nStudent: ").append(s.getName())
                  .append(" (").append(s.getStudentId()).append(")\n");
            report.append("Presentation: ").append(s.getPresentationType())
                  .append(" - ").append(s.getResearchTopic()).append("\n");
            report.append("Total Score: ").append(String.format("%.2f", score)).append("/100\n");
            report.append("Comments: ").append(e.getComments() != null ? e.getComments() : "No comments").append("\n");
            report.append("Evaluated by: ").append(e.getEvaluator() != null ? e.getEvaluator().getName() : "N/A").append("\n");
            report.append("----------------------------------------\n");
        }
        
        report.append("\nSUMMARY STATISTICS:\n");
        report.append("Total Evaluations: ").append(totalEvaluations).append("\n");
        if (totalEvaluations > 0) {
            report.append("Average Score: ").append(String.format("%.2f", totalScore / totalEvaluations)).append("\n");
        }
    }
    
    private void generateAwardReport(StringBuilder report) {
        report.append("AWARD CEREMONY RESULTS\n");
        report.append("======================\n\n");
        
        int awardsGenerated = 0;
        for (Award a : awards) {
            if (a.isGenerated() && a.getWinner() != null) {
                awardsGenerated++;
                report.append(a.generateAwardAgenda()).append("\n");
                report.append("----------------------------------------\n\n");
            }
        }
        
        report.append("Total Awards Generated: ").append(awardsGenerated).append("/").append(awards.size()).append("\n");
    }
    
    private void generateSummaryReport(StringBuilder report) {
        report.append("SEMINAR SUMMARY REPORT\n");
        report.append("======================\n\n");
        
        // Participant Statistics
        report.append("PARTICIPANT STATISTICS:\n");
        int oralCount = 0, posterCount = 0;
        for (Student s : participants) {
            if (s.getPresentationType().equalsIgnoreCase("Oral")) {
                oralCount++;
            } else if (s.getPresentationType().equalsIgnoreCase("Poster")) {
                posterCount++;
            }
        }
        report.append("Total Participants: ").append(participants.size()).append("\n");
        report.append("Oral Presentations: ").append(oralCount).append("\n");
        report.append("Poster Presentations: ").append(posterCount).append("\n\n");
        
        // Evaluation Statistics
        if (!evaluations.isEmpty()) {
            report.append("EVALUATION STATISTICS:\n");
            double minScore = 100, maxScore = 0, totalScore = 0;
            for (Evaluation e : evaluations) {
                double score = e.calculateTotalScore();
                if (score < minScore) minScore = score;
                if (score > maxScore) maxScore = score;
                totalScore += score;
            }
            report.append("Total Evaluations: ").append(evaluations.size()).append("\n");
            report.append("Highest Score: ").append(String.format("%.2f", maxScore)).append("\n");
            report.append("Lowest Score: ").append(String.format("%.2f", minScore)).append("\n");
            report.append("Average Score: ").append(String.format("%.2f", totalScore / evaluations.size())).append("\n\n");
        }
        
        // Award Summary
        if (!awards.isEmpty()) {
            report.append("AWARD SUMMARY:\n");
            for (Award a : awards) {
                report.append("- ").append(a.getAwardName());
                if (a.getWinner() != null) {
                    report.append(": ").append(a.getWinner().getName());
                } else {
                    report.append(": Not assigned");
                }
                report.append("\n");
            }
        }
    }
    
    private void generateAttendanceReport(StringBuilder report) {
        report.append("ATTENDANCE REPORT\n");
        report.append("=================\n\n");
        report.append("Session Info: ").append(sessionInfo != null ? sessionInfo : "N/A").append("\n\n");
        
        report.append("PARTICIPANTS LIST:\n");
        for (Student s : participants) {
            report.append("- ").append(s.getName())
                  .append(" (").append(s.getStudentId()).append(")")
                  .append(" - ").append(s.getPresentationType()).append("\n");
        }
    }
    
    private void generateComprehensiveReport(StringBuilder report) {
        generateSummaryReport(report);
        report.append("\n\n");
        generateEvaluationReport(report);
        report.append("\n\n");
        generateAwardReport(report);
    }
    
    // Export functionality (placeholder for actual PDF generation)
    public void exportPDF(String filePath) {
        // In a real implementation, you would use a PDF library like iText
        // For now, we'll just save the report as text
        try {
            java.nio.file.Files.write(
                java.nio.file.Paths.get(filePath),
                generate().getBytes()
            );
            System.out.println("Report exported to: " + filePath);
        } catch (Exception e) {
            System.err.println("Error exporting report: " + e.getMessage());
        }
    }
    
    public void exportCSV(String filePath) {
        try {
            StringBuilder csv = new StringBuilder();
            csv.append("Student Name,Student ID,Presentation Type,Research Topic,Score,Comments\n");
            
            for (Evaluation e : evaluations) {
                Student s = e.getStudent();
                csv.append("\"").append(s.getName()).append("\",");
                csv.append("\"").append(s.getStudentId()).append("\",");
                csv.append("\"").append(s.getPresentationType()).append("\",");
                csv.append("\"").append(s.getResearchTopic()).append("\",");
                csv.append(e.calculateTotalScore()).append(",");
                csv.append("\"").append(e.getComments() != null ? e.getComments().replace("\"", "\"\"") : "").append("\"\n");
            }
            
            java.nio.file.Files.write(
                java.nio.file.Paths.get(filePath),
                csv.toString().getBytes()
            );
            System.out.println("CSV exported to: " + filePath);
        } catch (Exception e) {
            System.err.println("Error exporting CSV: " + e.getMessage());
        }
    }
    
    // Data analytics methods
    public double calculateAverageScore() {
        if (evaluations.isEmpty()) return 0;
        double total = 0;
        for (Evaluation e : evaluations) {
            total += e.calculateTotalScore();
        }
        return total / evaluations.size();
    }
    
    public Student getTopPerformer() {
        if (evaluations.isEmpty()) return null;
        Evaluation topEval = evaluations.get(0);
        for (Evaluation e : evaluations) {
            if (e.calculateTotalScore() > topEval.calculateTotalScore()) {
                topEval = e;
            }
        }
        return topEval.getStudent();
    }
    
    // Getters and Setters
    public List<Award> getAwards() {
        return awards;
    }
    
    public List<Evaluation> getEvaluations() {
        return evaluations;
    }
    
    public String getReportId() {
        return reportId;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public Date getGenerateDate() {
        return generateDate;
    }
    
    public void setCoordinatorNotes(String notes) {
        this.coordinatorNotes = notes;
    }
    
    public void setSessionInfo(String sessionInfo) {
        this.sessionInfo = sessionInfo;
    }
    
    public void setParticipants(List<Student> participants) {
        this.participants = participants;
    }
}
