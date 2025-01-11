import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VoterVerificationPage extends JFrame {

    private JTextField txtName, txtVoterId;
    private JPasswordField txtPassword;

    public VoterVerificationPage() {
        setTitle("Voter Verification");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblName = new JLabel("Name:");
        JLabel lblVoterId = new JLabel("Voter ID:");
        JLabel lblPassword = new JLabel("Password:");

        lblName.setForeground(new Color(0, 123, 255));
        lblVoterId.setForeground(new Color(0, 123, 255));
        lblPassword.setForeground(new Color(0, 123, 255));

        txtName = new JTextField(10);
        txtVoterId = new JTextField(10);
        txtPassword = new JPasswordField(10);

        JButton btnVerify = new JButton("Verify");
        btnVerify.setBackground(new Color(0, 123, 255));
        btnVerify.setForeground(Color.WHITE);

        btnVerify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyVoter();
            }
        });

        panel.add(Box.createVerticalStrut(10));
        addLabeledField(panel, lblName, txtName);
        addLabeledField(panel, lblVoterId, txtVoterId);
        addLabeledField(panel, lblPassword, txtPassword);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnVerify);

        add(panel);
    }

    private void addLabeledField(JPanel panel, JLabel label, JTextField field) {
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        subPanel.setBackground(new Color(255, 255, 255));
        subPanel.add(label);
        subPanel.add(field);
        panel.add(subPanel);
    }

    private void verifyVoter() {
        String name = txtName.getText();
        String voterId = txtVoterId.getText();
        String password = new String(txtPassword.getPassword());

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system", "root", "yourMySQLpassword");
            String query = "SELECT * FROM voters WHERE voter_name = ? AND voter_id = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, voterId);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolean verified = rs.getBoolean("is_verified");
                if (verified) {
                    JOptionPane.showMessageDialog(this, "You are already verified!");
                } else {
                    String updateQuery = "UPDATE voters SET is_verified = TRUE WHERE voter_id = ?";
                    try (PreparedStatement updatePs = con.prepareStatement(updateQuery)) {
                        updatePs.setString(1, voterId);
                        updatePs.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(this, "Verification Successful!");
                    new VoteCastingPage().setVisible(true);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Verification Failed!");
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new VoterVerificationPage().setVisible(true);
    }
}
