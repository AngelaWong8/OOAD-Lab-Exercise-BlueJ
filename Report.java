import java.util.Date;
import java.util.List;

public class Report {
    private String reportId;
    private String reportType;
    private Date generateDate;
    private List<Evaluation> evaluations;
    private List<Award> awards;

    public Report(String reportId, String reportType,
                  List<Evaluation> evaluations, List<Award> awards) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.evaluations = evaluations;
        this.awards = awards;
        this.generateDate = new Date();
    }

    public String generate() {
        String result = "=== SEMINAR REPORT ===\n\n";
        for (Evaluation e : evaluations) {
            result += e.getStudent().getName()
                    + " | Score: " + e.calculateTotalScore() + "\n";
        }
        return result;
    }

    public void exportPDF() {
    }

    public List<Award> getAwards() {
        return awards;
    }
}
