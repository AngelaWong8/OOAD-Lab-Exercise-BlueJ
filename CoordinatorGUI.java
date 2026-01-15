import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CoordinatorGUI extends JFrame {

    public CoordinatorGUI() {
        setTitle("Coordinator Module");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnCreateSession = new JButton("Create Session");
        JButton btnAssignUsers = new JButton("Assign Users");
        JButton btnGenerateAward = new JButton("Generate Awards");
        JButton btnGenerateReport = new JButton("Generate Report");

        setLayout(new GridLayout(5, 1));
        add(new JLabel("Coordinator Functions", SwingConstants.CENTER));
        add(btnCreateSession);
        add(btnAssignUsers);
        add(btnGenerateAward);
        add(btnGenerateReport);

        btnCreateSession.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Session created (demo)"));

        btnAssignUsers.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Users assigned (demo)"));

        btnGenerateAward.addActionListener(e -> openAwardGUI());

        btnGenerateReport.addActionListener(e -> openReportGUI());
    }

    // -------------------------
    // DEMO DATA + AWARD GUI
    // -------------------------
    private void openAwardGUI() {

        Student s1 = new Student("S1","Alice","a@mail.com",
                "AI","AI research","Oral");
        Student s2 = new Student("S2","Bob","b@mail.com",
                "ML","ML research","Poster");

        Evaluation e1 = new Evaluation("E1","Rubric",85,"Good",s1);
        Evaluation e2 = new Evaluation("E2","Rubric",90,"Excellent",s2);

        List<Evaluation> evaluations = Arrays.asList(e1, e2);

        Award a1 = new Award("A1","Best Oral","Highest Score");
        Award a2 = new Award("A2","Best Poster","Highest Score");

        List<Award> awards = Arrays.asList(a1, a2);

        new AwardGUI(awards, evaluations).setVisible(true);
    }

    // -------------------------
    // DEMO DATA + REPORT GUI
    // -------------------------
    private void openReportGUI() {

        Student s1 = new Student("S1","Alice","a@mail.com",
                "AI","AI research","Oral");

        Evaluation e1 = new Evaluation("E1","Rubric",85,"Good",s1);

        List<Evaluation> evaluations = Arrays.asList(e1);

        Award a1 = new Award("A1","Best Oral","Highest Score");
        a1.calculateWinner(evaluations);

        List<Award> awards = Arrays.asList(a1);

        Report report = new Report("R1",
                "Final Seminar Report",
                evaluations, awards);

        new ReportGUI(report).setVisible(true);
    }
}
