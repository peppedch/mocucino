package gui;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JButton;

public class HomeFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    HomeFrame frame = new HomeFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public HomeFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel welcome_label = new JLabel("Benvenuto su Mòcucino");
        welcome_label.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));

        JLabel login_label = new JLabel("Hai già un account?");

        JLabel registrati_label = new JLabel("Crea un account");

        JRadioButton bottone_accedi = new JRadioButton("Accedi");
        bottone_accedi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginFrame login = new LoginFrame();
                login.setVisible(true);

            }
        });

        JRadioButton bottone_registrati = new JRadioButton("Registrati");
        bottone_registrati.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegisterFrame reg = new RegisterFrame();
                reg.setVisible(true);

            }
        });

        JButton btnLoginAdmin = new JButton("Accedi come amministratore");
        btnLoginAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginAdminFrame().setVisible(true);
                dispose();
            }
        });

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(44)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(login_label)
                                        .addComponent(bottone_accedi, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
                                .addGap(133)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(bottone_registrati, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(registrati_label, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                                .addGap(45)))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(120)
                                .addComponent(welcome_label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(104))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap(230, Short.MAX_VALUE)
                                .addComponent(btnLoginAdmin, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(welcome_label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                .addGap(33)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(login_label)
                                        .addComponent(registrati_label))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(bottone_registrati)
                                        .addComponent(bottone_accedi))
                                .addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                                .addComponent(btnLoginAdmin, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
    }
}