import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {

    private JButton btnVoteCasting;

    public MainPage() {
        setTitle("Online Voting System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(60, 63, 65));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Dimension buttonSize = new Dimension(200, 40);

        JButton btnVoterVerification = new JButton("Voter Verification");
        btnVoterVerification.setBackground(new Color(0, 123, 255));
        btnVoterVerification.setForeground(Color.WHITE);
        btnVoterVerification.setPreferredSize(buttonSize);
        btnVoterVerification.setMaximumSize(buttonSize);

        btnVoteCasting = new JButton("Vote Casting");
        btnVoteCasting.setBackground(new Color(40, 167, 69));
        btnVoteCasting.setForeground(Color.WHITE);
        btnVoteCasting.setPreferredSize(buttonSize);
        btnVoteCasting.setMaximumSize(buttonSize);
        btnVoteCasting.setEnabled(false);

        JButton btnWinnerAnnouncement = new JButton("Winner Announcement");
        btnWinnerAnnouncement.setBackground(new Color(220, 53, 69));
        btnWinnerAnnouncement.setForeground(Color.WHITE);
        btnWinnerAnnouncement.setPreferredSize(buttonSize);
        btnWinnerAnnouncement.setMaximumSize(buttonSize);

        btnVoterVerification.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VoterVerificationPage voterVerificationPage = new VoterVerificationPage() {
                    @Override
                    public void dispose() {
                        super.dispose();
                        enableVoteCastingButton();
                    }
                };
                voterVerificationPage.setVisible(true);
                dispose();
            }
        });

        btnVoteCasting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VoteCastingPage().setVisible(true);
                dispose();
            }
        });

        btnWinnerAnnouncement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WinnerAnnouncementPage().setVisible(true);
                dispose();
            }
        });

        btnVoterVerification.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVoteCasting.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnWinnerAnnouncement.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(40));
        panel.add(btnVoterVerification);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnVoteCasting);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnWinnerAnnouncement);

        add(panel);
    }

    private void enableVoteCastingButton() {
        btnVoteCasting.setEnabled(true);
    }

    public static void main(String[] args) {
        new MainPage().setVisible(true);
    }
}