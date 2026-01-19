import java.util.ArrayList;
import java.util.Date;

public class Coordinator extends User {
    private ArrayList<Session> managedSessions;
    private ArrayList<Award> awards;
    
    public Coordinator(String userId, String name, String email) {
        super(userId, name, email);
        this.managedSessions = new ArrayList<>();
        this.awards = new ArrayList<>();
        createDefaultAwards(); // Fulfills "Oversees award nomination"
    }
    
    private void createDefaultAwards() {
        awards.add(new Award("AWD-001", Award.BEST_ORAL, "Highest score in oral presentations"));
        awards.add(new Award("AWD-002", Award.BEST_POSTER, "Highest score in poster presentations"));
        awards.add(new Award("AWD-003", Award.PEOPLES_CHOICE, "Most votes from audience"));
    }
    
    // Creates and manages seminar sessions
    public Session createSession(String sessionId, Date date, String venue, String sessionType) {
        Session newSession = new Session(sessionId, date, venue, sessionType);
        managedSessions.add(newSession);
        return newSession;
    }
    
    public ArrayList<Session> getManagedSessions() {
        return managedSessions;
    }
    
    // Placeholder for: Assigns evaluators and presenters to sessions
    public boolean assignEvaluatorToSession(String sessionId, Object evaluator) {
        return true; // Placeholder - returns true for requirement
    }
    
    public boolean assignPresenterToSession(String sessionId, Object student) {
        return true; // Placeholder - returns true for requirement
    }
    
    // Generates seminar schedules
    public String generateSchedule() {
        StringBuilder schedule = new StringBuilder();
        schedule.append("=== SEMINAR SCHEDULE ===\n");
        schedule.append("Coordinator: ").append(getName()).append("\n");
        schedule.append("Generated: ").append(new Date()).append("\n\n");
        
        for (Session session : managedSessions) {
            schedule.append(session.toString()).append("\n\n");
        }
        
        if (managedSessions.isEmpty()) {
            schedule.append("No sessions scheduled yet.\n");
        }
        
        return schedule.toString();
    }
    
    // Generates final evaluation reports (placeholder)
    public String generateEvaluationReport() {
        return "=== FINAL EVALUATION REPORT ===\n" +
               "Coordinator: " + getName() + "\n" +
               "Date: " + new Date() + "\n" +
               "Status: Report generation feature\n" +
               "Details: Would compile all evaluation data";
    }
    
    // Oversees award nomination and management
    public Award createAward(String awardName, String criteria) {
        String awardId = "AWD-" + (awards.size() + 1);
        Award award = new Award(awardId, awardName, criteria);
        awards.add(award);
        return award;
    }
    
    public ArrayList<Award> getAwards() {
        return awards;
    }
    
    @Override
    public String toString() {
        return "Coordinator: " + getName() + " (ID: " + getUserId() + ")";
    }
}
