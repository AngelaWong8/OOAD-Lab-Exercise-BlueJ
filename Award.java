import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Award {
    private String awardId;
    private String awardName;
    private String criteria;
    private Student winner;
    private List<Student> nominees;
    private Map<String, Integer> votes; // For People's Choice award
    
    // Award types as per requirements
    public static final String BEST_ORAL = "Best Oral Presentation";
    public static final String BEST_POSTER = "Best Poster Presentation";
    public static final String PEOPLES_CHOICE = "People's Choice Award";
    
    public Award(String awardId, String awardName, String criteria) {
        this.awardId = awardId;
        this.awardName = awardName;
        this.criteria = criteria;
        this.nominees = new ArrayList<>();
        this.votes = new HashMap<>();
    }
    
    // Calculate winner based on evaluation scores (for Best Oral/Poster)
    public void calculateWinner(List<Evaluation> evaluations) {
        if (evaluations == null || evaluations.isEmpty()) {
            winner = null;
            return;
        }
        
        double maxScore = -1;
        Evaluation bestEvaluation = null;
        
        for (Evaluation e : evaluations) {
            Student student = e.getStudent();
            double score = e.calculateTotalScore();
            
            // Filter by presentation type for specific awards
            if (awardName.equals(BEST_ORAL)) {
                if (!"Oral".equalsIgnoreCase(student.getPresentationType())) {
                    continue; // Skip non-oral presentations for Best Oral award
                }
            } else if (awardName.equals(BEST_POSTER)) {
                if (!"Poster".equalsIgnoreCase(student.getPresentationType())) {
                    continue; // Skip non-poster presentations for Best Poster award
                }
            } else if (awardName.equals(PEOPLES_CHOICE)) {
                continue; // People's Choice is calculated separately
            }
            
            if (score > maxScore) {
                maxScore = score;
                bestEvaluation = e;
            }
        }
        
        if (bestEvaluation != null) {
            winner = bestEvaluation.getStudent();
            System.out.println(awardName + " awarded to " + winner.getName() 
                + " with score: " + maxScore);
        }
    }
    
    // For People's Choice award - voting system
    public void addVote(String voterId, Student student) {
        if (!nominees.contains(student)) {
            nominees.add(student);
        }
        votes.put(voterId, nominees.indexOf(student));
    }
    
    // Calculate People's Choice winner (most votes)
    public void calculatePeoplesChoiceWinner() {
        if (votes.isEmpty() || nominees.isEmpty()) {
            winner = null;
            return;
        }
        
        // Count votes for each nominee
        Map<Integer, Integer> voteCount = new HashMap<>();
        for (Integer nomineeIndex : votes.values()) {
            voteCount.put(nomineeIndex, voteCount.getOrDefault(nomineeIndex, 0) + 1);
        }
        
        // Find nominee with most votes
        int maxVotes = -1;
        int winnerIndex = -1;
        
        for (Map.Entry<Integer, Integer> entry : voteCount.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winnerIndex = entry.getKey();
            }
        }
        
        if (winnerIndex >= 0 && winnerIndex < nominees.size()) {
            winner = nominees.get(winnerIndex);
            System.out.println(PEOPLES_CHOICE + " awarded to " + winner.getName() 
                + " with " + maxVotes + " votes");
        }
    }
    
    // Add a student to nomination list
    public void addNominee(Student student) {
        if (!nominees.contains(student)) {
            nominees.add(student);
        }
    }
    
    // Getters
    public Student getWinner() {
        return winner;
    }
    
    public String getAwardName() {
        return awardName;
    }
    
    public String getCriteria() {
        return criteria;
    }
    
    public String getAwardId() {
        return awardId;
    }
    
    public List<Student> getNominees() {
        return new ArrayList<>(nominees);
    }
    
    public int getVoteCount() {
        return votes.size();
    }
    
    // Validation
    public boolean validateCriteria() {
        return criteria != null && !criteria.trim().isEmpty();
    }
    
    public boolean isAwardAssigned() {
        return winner != null;
    }
    
    @Override
    public String toString() {
        return awardName + " (" + criteria + ")" 
                + (winner != null ? " - Winner: " + winner.getName() : " - No winner assigned");
    }
    
    // Check if award matches requirements (Best Oral, Best Poster, People's Choice)
    public boolean isStandardAward() {
        return awardName.equals(BEST_ORAL) || 
               awardName.equals(BEST_POSTER) || 
               awardName.equals(PEOPLES_CHOICE);
    }

    public int getNomineeCount() {
        return nominees.size();
    }
    
    public Map<Student, Integer> getVotingResults() {
        Map<Student, Integer> results = new HashMap<>();
        
        if (nominees.isEmpty()) return results;
        
        // Count votes for each nominee
        Map<Integer, Integer> voteCount = new HashMap<>();
        for (Integer nomineeIndex : votes.values()) {
            voteCount.put(nomineeIndex, voteCount.getOrDefault(nomineeIndex, 0) + 1);
        }
        
        // Convert to Student -> Vote count
        for (Map.Entry<Integer, Integer> entry : voteCount.entrySet()) {
            if (entry.getKey() >= 0 && entry.getKey() < nominees.size()) {
                results.put(nominees.get(entry.getKey()), entry.getValue());
            }
        }
        
        return results;
    }
    
    public void removeNominee(Student student) {
        nominees.remove(student);
    }
}
