public class Evaluator extends User {

    public Evaluator(String userId, String name, String email) {
        super(userId, name, email);
    }

    public void reviewPresentation() {
        System.out.println(getName() + " is reviewing the submission...");
    }

    public void evaluateStudents() {
        System.out.println("Evaluator " + getName() + " has triggered student evaluation.");
    }

    public void comment(Evaluation eval, String commentText) {
        System.out.println("Evaluator is adding a comment to the evaluation record.");
    }
}
