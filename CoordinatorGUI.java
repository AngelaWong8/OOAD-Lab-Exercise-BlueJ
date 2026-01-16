import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class CoordinatorGUI extends JFrame {
    private Coordinator coordinator;
    
    public CoordinatorGUI(Coordinator coordinator) {
        super("Coordinator: " + coordinator.getName());
        this.coordinator = coordinator;
        setSize(550, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250);
        
        // Left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel actionLabel = new JLabel("Actions", SwingConstants.CENTER);
        actionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(actionLabel, BorderLayout.NORTH);
        
        JPanel actionPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        String[] actions = {"📋 Create Session", "👁️ View Sessions (" + coordinator.getManagedSessions().size() + ")", 
                           "📅 View Schedule", "🎯 Manage Awards", "📊 Statistics", "👤 Profile", "🚪 Logout"};
        
        for (String action : actions) {
            JButton btn = new JButton(action);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.addActionListener(e -> handleAction(action));
            actionPanel.add(btn);
        }
        leftPanel.add(actionPanel, BorderLayout.CENTER);
        
        // Right panel
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setText("Welcome, " + coordinator.getName() + "!\n\nID: " + coordinator.getUserId() + 
                          "\nEmail: " + coordinator.getEmail() + "\nSessions: " + coordinator.getManagedSessions().size());
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(new JScrollPane(outputArea));
        add(splitPane);
        setVisible(true);
    }
    
    private void handleAction(String action) {
        if (action.startsWith("📋")) createSession();
        else if (action.startsWith("👁️")) viewSessions();
        else if (action.startsWith("📅")) viewSchedule();
        else if (action.startsWith("🎯")) manageAwards();
        else if (action.startsWith("📊")) showStats();
        else if (action.startsWith("👤")) showProfile();
        else if (action.startsWith("🚪")) dispose();
    }
    
    private void createSession() {
        JTextField idField = new JTextField("SESS-" + (int)(Math.random()*1000));
        JTextField dateField = new JTextField(new java.text.SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        JTextField timeField = new JTextField("09:00 AM");
        JTextField venueField = new JTextField("Main Hall");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Oral", "Poster"});
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Session ID:")); panel.add(idField);
        panel.add(new JLabel("Date (dd/mm/yyyy):")); panel.add(dateField);
        panel.add(new JLabel("Time (hh:mm AM/PM):")); panel.add(timeField);
        panel.add(new JLabel("Venue:")); panel.add(venueField);
        panel.add(new JLabel("Type:")); panel.add(typeBox);
        
        if (JOptionPane.showConfirmDialog(this, panel, "Create Session", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                Date sessionDate = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm a").parse(dateField.getText() + " " + timeField.getText());
                coordinator.createSession(idField.getText(), sessionDate, venueField.getText(), (String)typeBox.getSelectedItem());
                JOptionPane.showMessageDialog(this, "Session created!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void viewSessions() {
        if (coordinator.getManagedSessions().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No sessions yet.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Session s : coordinator.getManagedSessions()) sb.append("• ").append(s).append("\n\n");
        JOptionPane.showMessageDialog(this, sb.toString(), "Sessions", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void viewSchedule() {
        JOptionPane.showMessageDialog(this, coordinator.generateSchedule(), "Schedule", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void manageAwards() {
        String[] options = {"Create Award", "View Winners"};
        String choice = (String) JOptionPane.showInputDialog(this, "Select:", "Awards", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == null) return;
        
        if (choice.equals("Create Award")) {
            String[] awardTypes = {Award.BEST_ORAL, Award.BEST_POSTER, Award.PEOPLES_CHOICE};
            String awardName = (String) JOptionPane.showInputDialog(this, "Select type:", "Create Award", 
                JOptionPane.QUESTION_MESSAGE, null, awardTypes, awardTypes[0]);
            if (awardName != null) {
                String criteria = JOptionPane.showInputDialog(this, "Enter criteria:");
                if (criteria != null && !criteria.trim().isEmpty()) {
                    coordinator.createAward(awardName, criteria);
                    JOptionPane.showMessageDialog(this, "Award created: " + awardName);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "• Best Oral: Not assigned\n• Best Poster: Not assigned\n• People's Choice: Not assigned", 
                "Award Winners", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showStats() {
        JOptionPane.showMessageDialog(this, "Sessions: " + coordinator.getManagedSessions().size() + 
            "\nAwards: " + coordinator.getAwards().size(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showProfile() {
        JOptionPane.showMessageDialog(this, "ID: " + coordinator.getUserId() + "\nName: " + coordinator.getName() + 
            "\nEmail: " + coordinator.getEmail() + "\nSessions: " + coordinator.getManagedSessions().size(), "Profile", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static Coordinator login() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        JRadioButton rb1 = new JRadioButton("Dr. Smith (C001)");
        JRadioButton rb2 = new JRadioButton("Prof. Johnson (C002)");
        JRadioButton rb3 = new JRadioButton("Ms. Williams (C003)");
        JRadioButton rb4 = new JRadioButton("➕ New Coordinator");
        
        ButtonGroup group = new ButtonGroup();
        group.add(rb1); group.add(rb2); group.add(rb3); group.add(rb4);
        rb1.setSelected(true);
        panel.add(rb1); panel.add(rb2); panel.add(rb3); panel.add(rb4);
        
        if (JOptionPane.showConfirmDialog(null, panel, "Coordinator Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) 
            return null;
        
        if (rb1.isSelected()) return new Coordinator("C001", "Dr. Smith", "smith@uni.edu");
        if (rb2.isSelected()) return new Coordinator("C002", "Prof. Johnson", "johnson@uni.edu");
        if (rb3.isSelected()) return new Coordinator("C003", "Ms. Williams", "williams@uni.edu");
        
        JPanel regPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField idField = new JTextField("C" + String.format("%03d", (int)(Math.random()*1000)));
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        regPanel.add(new JLabel("ID:")); regPanel.add(idField);
        regPanel.add(new JLabel("Name:")); regPanel.add(nameField);
        regPanel.add(new JLabel("Email:")); regPanel.add(emailField);
        
        if (JOptionPane.showConfirmDialog(null, regPanel, "New Coordinator", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) 
            return null;
        
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name required", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        String email = emailField.getText().trim();
        if (email.isEmpty()) email = name.toLowerCase().replace(" ", ".") + "@uni.edu";
        
        return new Coordinator(idField.getText().trim(), name, email);
    }
}
