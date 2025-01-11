import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WinnerAnnouncementPage extends JFrame {

    public WinnerAnnouncementPage() {
        setTitle("Winner Announcement");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(204, 255, 204));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton btnResult = new JButton("Result");
        btnResult.setBackground(new Color(0, 123, 255));
        btnResult.setForeground(Color.WHITE);
        btnResult.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayWinner();
            }
        });

        panel.add(Box.createVerticalStrut(100));
        panel.add(btnResult);

        add(panel);
    }

    private void displayWinner() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system", "root", "yourMySQLpassword")) {
            // Get the party with the maximum vote count
            String maxVoteCountQuery = "SELECT MAX(vote_count) AS max_vote_count FROM vote_counts";
            int maxVoteCount = 0;
            try (PreparedStatement ps = con.prepareStatement(maxVoteCountQuery);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    maxVoteCount = rs.getInt("max_vote_count");
                }
            }

            String tieQuery = "SELECT party FROM vote_counts WHERE vote_count = ?";
            try (PreparedStatement ps = con.prepareStatement(tieQuery)) {
                ps.setInt(1, maxVoteCount);
                try (ResultSet rs = ps.executeQuery()) {
                    StringBuilder winners = new StringBuilder();
                    boolean tie = false;
                    while (rs.next()) {
                        if (winners.length() > 0) {
                            winners.append(", ");
                        }
                        winners.append(rs.getString("party"));
                        if (winners.indexOf(", ") != -1) {
                            tie = true;
                        }
                    }

                    if (maxVoteCount == 0) {
                        JOptionPane.showMessageDialog(this, "Voting in process.");
                    } else if (tie) {
                        JOptionPane.showMessageDialog(this, "Tie between the following parties: " + winners.toString());
                    } else {
                        JOptionPane.showMessageDialog(this, "The winner is " + winners.toString() + "!");
                    }
                }
            }

            resetVoteCounts(con);
            resetVoterVerification(con);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred while fetching the winner.");
        }
    }

    private void resetVoteCounts(Connection con) {
        String updateQuery = "UPDATE vote_counts SET vote_count = 0";
        try (PreparedStatement ps = con.prepareStatement(updateQuery)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred while resetting vote counts.");
        }
    }

    private void resetVoterVerification(Connection con) {
        String updateQuery = "UPDATE voters SET is_verified = FALSE";
        try (PreparedStatement ps = con.prepareStatement(updateQuery)) {
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred while resetting voter verification.");
        }
    }

    public static void main(String[] args) {
        new WinnerAnnouncementPage().setVisible(true);
    }
}
