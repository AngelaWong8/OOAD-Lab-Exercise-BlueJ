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
        System.out.println("----- EVALUATION RECORDED -----");
        System.out.println("ID: " + evaluationId);
        System.out.println("Student Name: " + student.getName()); // Inherited from User
        System.out.println("Research Title: " + student.getPresentationType()); 
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
