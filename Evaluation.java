public class Evaluation {
    private String evaluationId;
    private String rubricType;
    private int score;
    private String comment;
    private Student student;
    private Evaluator evaluator;

    public Evaluation(String evaluationId, String rubricType, int score,
                      String comment, Student student) {
        this.evaluationId = evaluationId;
        this.rubricType = rubricType;
        this.score = score;
        this.comment = comment;
        this.student = student;
        this.evaluator = null;
    }

    public Evaluation(String evaluationId, String rubricType, Student student, Evaluator evaluator) {
        this.evaluationId = evaluationId;
        this.rubricType = rubricType;
        this.student = student;
        this.evaluator = evaluator;
        this.score = 0;
        this.comment = "";
    }

    public void recordEvaluation() {
        System.out.println("----- EVALUATION RECORDED -----");
        System.out.println("ID: " + evaluationId);
        System.out.println("Student Name: " + student.getName());
        System.out.println("Research Title: " + student.getResearchTitle()); 
        System.out.println("Rubric: " + rubricType);
        System.out.println("Final Score: " + calculateTotalScore());
        System.out.println("Evaluator Comment: " + comment);
        System.out.println("-------------------------------");
    }

    public void addComment(String comment) {
        this.comment = comment;
    }

    public double calculateTotalScore() {
        return score;
    }

    // Getters
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
    
    public String getEvaluationId() {
        return evaluationId;
    }
    
    public Evaluator getEvaluator() {
        return evaluator;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }
}
