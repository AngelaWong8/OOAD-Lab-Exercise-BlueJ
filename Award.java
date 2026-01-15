import java.util.List;

public class Award {
    private String awardId;
    private String awardName;
    private String criteria;
    private Student winner;

    public Award(String awardId, String awardName, String criteria) {
        this.awardId = awardId;
        this.awardName = awardName;
        this.criteria = criteria;
    }

    public void calculateWinner(List<Evaluation> evaluations) {
        double max = -1;
        for (Evaluation e : evaluations) {
            if (e.calculateTotalScore() > max) {
                max = e.calculateTotalScore();
                winner = e.getStudent();
            }
        }
    }

    public Student getWinner() {
        return winner;
    }

    public String getAwardName() {
        return awardName;
    }

    public boolean validateCriteria() {
        return true;
    }
}
