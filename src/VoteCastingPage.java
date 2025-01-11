import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VoteCastingPage extends JFrame {

    private boolean hasVoted = false;

    public VoteCastingPage() {
        setTitle("Vote Casting");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblInstructions = new JLabel("Please select your party:");
        lblInstructions.setForeground(new Color(0, 123, 255));

        JButton btnBJP = new JButton("BJP");
        JButton btnShivsena = new JButton("Shivsena");
        JButton btnCongress = new JButton("Congress");
        JButton btnMNS = new JButton("MNS");
        JButton btnNOTA = new JButton("NOTA");

        btnBJP.setBackground(new Color(255, 193, 7));
        btnShivsena.setBackground(new Color(255, 87, 34));
        btnCongress.setBackground(new Color(33, 150, 243));
        btnMNS.setBackground(new Color(76, 175, 80));
        btnNOTA.setBackground(new Color(158, 158, 158));

        btnBJP.setForeground(Color.WHITE);
        btnShivsena.setForeground(Color.WHITE);
        btnCongress.setForeground(Color.WHITE);
        btnMNS.setForeground(Color.WHITE);
        btnNOTA.setForeground(Color.WHITE);

        ActionListener voteListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!hasVoted) {
                    JButton sourceButton = (JButton) e.getSource();
                    String party = sourceButton.getText();
                    String voterId = "123";

                    recordVote(party, voterId);

                    JOptionPane.showMessageDialog(VoteCastingPage.this, "You voted for " + party + "!");
                    hasVoted = true;

                    btnBJP.setEnabled(false);
                    btnShivsena.setEnabled(false);
                    btnCongress.setEnabled(false);
                    btnMNS.setEnabled(false);
                    btnNOTA.setEnabled(false);

                    dispose();
                    new MainPage().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(VoteCastingPage.this, "You have already voted!");
                }
            }
        };

        btnBJP.addActionListener(voteListener);
        btnShivsena.addActionListener(voteListener);
        btnCongress.addActionListener(voteListener);
        btnMNS.addActionListener(voteListener);
        btnNOTA.addActionListener(voteListener);

        Dimension buttonSize = new Dimension(150, 40);
        btnBJP.setPreferredSize(buttonSize);
        btnShivsena.setPreferredSize(buttonSize);
        btnCongress.setPreferredSize(buttonSize);
        btnMNS.setPreferredSize(buttonSize);
        btnNOTA.setPreferredSize(buttonSize);

        btnBJP.setMaximumSize(buttonSize);
        btnShivsena.setMaximumSize(buttonSize);
        btnCongress.setMaximumSize(buttonSize);
        btnMNS.setMaximumSize(buttonSize);
        btnNOTA.setMaximumSize(buttonSize);

        lblInstructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBJP.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnShivsena.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCongress.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMNS.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNOTA.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(lblInstructions);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnBJP);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnShivsena);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnCongress);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnMNS);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnNOTA);

        add(panel);
    }

    private void recordVote(String party, String voterId) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system", "root", "yourMySQLpassword")) {
            String insertVoteQuery = "INSERT INTO votes (party, voter_id) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertVoteQuery)) {
                ps.setString(1, party);
                ps.setString(2, voterId);
                ps.executeUpdate();
            }

            String updateVoteCountQuery = "UPDATE vote_counts SET vote_count = vote_count + 1 WHERE party = ?";
            try (PreparedStatement ps = con.prepareStatement(updateVoteCountQuery)) {
                ps.setString(1, party);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred while recording your vote.");
        }
    }

    public static void main(String[] args) {
        new VoteCastingPage().setVisible(true);
    }
}