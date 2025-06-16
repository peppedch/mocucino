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
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
        setBounds(100, 100, 800, 700);
        setMinimumSize(new java.awt.Dimension(600, 600));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel welcome_label = new JLabel("Benvenuto su Mòcucino");
        welcome_label.setFont(new Font("Lucida Handwriting", Font.BOLD, 14));

        // Logo statico come prima
        JLabel logoLabel = new JLabel(new ImageIcon("docs/photos/mocucino_logo.png"));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Messaggio di benvenuto sotto il logo
        JLabel welcomeMsg = new JLabel("Per usufruire dei contenuti della piattaforma culinaria, accedi o registrati.");
        welcomeMsg.setHorizontalAlignment(JLabel.CENTER);
        welcomeMsg.setFont(new Font("Tahoma", Font.PLAIN, 14));

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

        // Creo un pannello per il bottone admin in basso a destra (corretto)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(btnLoginAdmin);
        bottomPanel.setOpaque(false);
        bottomPanel.add(rightPanel, BorderLayout.SOUTH);
        btnLoginAdmin.setVisible(getWidth() >= 900 && getHeight() >= 700);

        // Listener per mostrare/nascondere il bottone in base alla dimensione
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                btnLoginAdmin.setVisible(getWidth() >= 900 && getHeight() >= 700);
            }
        });

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.CENTER)
                .addComponent(welcome_label)
                .addComponent(logoLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(welcomeMsg, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.CENTER)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(login_label)
                            .addGap(40)
                            .addComponent(registrati_label))
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(bottone_accedi)
                            .addGap(40)
                            .addComponent(bottone_registrati))
                    )
                )
                .addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createSequentialGroup()
                .addGap(20)
                .addComponent(welcome_label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(logoLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(welcomeMsg)
                .addGap(20)
                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                    .addComponent(login_label)
                    .addComponent(registrati_label))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                    .addComponent(bottone_accedi)
                    .addComponent(bottone_registrati))
                .addGap(40)
                .addComponent(bottomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap()
        );
        contentPane.setLayout(gl_contentPane);
    }
}