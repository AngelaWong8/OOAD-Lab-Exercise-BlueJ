public class Student extends User {
    private String studentId;
    private String supervisor;
    private String researchTitle;
    private String abstractText;
    private String presentationType;
    private String presentationFilePath;
    private boolean registered;

    public Student(String userId, String name, String email, 
                   String researchTitle, String abstractText, String presentationType) {
        super(userId, name, email);
        this.studentId = userId; // Using userId as studentId
        this.supervisor = "Dr. Supervisor";
        this.researchTitle = researchTitle;
        this.abstractText = abstractText;
        this.presentationType = presentationType;
        this.presentationFilePath = "";
        this.registered = false;
    }

    public Student(String userId, String name, String email, String studentId,
                   String supervisor, String researchTitle, String abstractText, String presentationType) {
        super(userId, name, email);
        this.studentId = studentId;
        this.supervisor = supervisor;
        this.researchTitle = researchTitle;
        this.abstractText = abstractText;
        this.presentationType = presentationType;
        this.presentationFilePath = "";
        this.registered = false;
    }

    public String getStudentId() { return studentId; }
    public String getSupervisor() { return supervisor; }
    public String getResearchTitle() { return researchTitle; }
    public String getAbstractText() { return abstractText; }
    public String getPresentationType() { return presentationType; }
    public String getPresentationFilePath() { return presentationFilePath; }
    public boolean isRegistered() { return registered; }

    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }
    public void setResearchTitle(String researchTitle) { this.researchTitle = researchTitle; }
    public void setAbstractText(String abstractText) { this.abstractText = abstractText; }
    public void setPresentationType(String presentationType) { this.presentationType = presentationType; }
    public void setRegistered(boolean registered) { this.registered = registered; }
    public void setPresentationFilePath(String filePath) { this.presentationFilePath = filePath; }

    public boolean submitPresentation() {
        if (presentationFilePath.isEmpty()) {
            System.out.println("No presentation file to submit!");
            return false;
        }
        System.out.println("Presentation submitted: " + presentationFilePath);
        return true;
    }
    
    public boolean submitPresentation(String filePath) {
        this.presentationFilePath = filePath;
        System.out.println("Presentation submitted: " + filePath);
        return true;
    }

    public void register() {
        System.out.println("Student " + getName() + " is registering...");
        this.registered = true;
    }

    public void registerForSession(String sessionId) {
        System.out.println("Student " + getName() + " registering for session: " + sessionId);
        this.registered = true;
    }

    public void uploadPresentation(String filePath, String presentationType) {
        this.presentationFilePath = filePath;
        this.presentationType = presentationType;
        System.out.println("Presentation uploaded: " + filePath + " (" + presentationType + ")");
    }

    public String receiveAwardNotification() {
        return "Award notification received for " + getName();
    }

    public String getStudentInfo() {
        return "ID: " + getUserId() + "\n" +
               "Name: " + getName() + "\n" +
               "Email: " + getEmail() + "\n" +
               "Student ID: " + studentId +
               "\nSupervisor: " + supervisor +
               "\nResearch Title: " + researchTitle +
               "\nAbstract: " + (abstractText.length() > 50 ? abstractText.substring(0, 50) + "..." : abstractText) +
               "\nPresentation Type: " + presentationType +
               "\nRegistered: " + (registered ? "Yes" : "No") +
               "\nPresentation File: " + (presentationFilePath.isEmpty() ? "Not uploaded" : presentationFilePath);
    }

    public void confirmRegistration(String sessionId) {
        System.out.println("Registration confirmed for session: " + sessionId);
        this.registered = true;
    }
    
    @Override
    public String toString() {
        return getName() + " (" + studentId + ") - " + researchTitle;
    }
}
