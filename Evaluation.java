public class Evaluation {
    private String evaluationId;
    private String rubricType;
    private int score;
    private String comment;
    private Student student;

    public Evaluation(String evaluationId, String rubricType, int score,
                      String comment, Student student) {
        this.evaluationId = evaluationId;
        this.rubricType = rubricType;
        this.score = score;
        this.comment = comment;
        this.student = student;
    }

    public void recordEvaluation() {
    }

    public void addComment(String comment) {
        this.comment = comment;
    }

    public double calculateTotalScore() {
        return score;
    }

    public Student getStudent() {
        return student;
    }
    
    public String getRubricType() {
        return rubricType;
    }
    
    public String getComments() {
        return comment;
    }
    
    public int getScore() {
        return score;
    }
}
