import java.util.Date;

public class Session {
    private String sessionID;
    private Date date;
    private String venue;
    private String sessionType;

    public Session(String sessionID, Date date, String venue, String sessionType) {
        this.sessionID = sessionID;
        this.date = date;
        this.venue = venue;
        this.sessionType = sessionType;
    }
}
