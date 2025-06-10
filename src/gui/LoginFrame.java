package gui;

import java.awt.EventQueue;
import controller.AccessoController;
import entity.Utente;

//import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField email_inserisci;
    private JPasswordField password_inserisci;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame frame = new LoginFrame();
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
    public LoginFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel login_label = new JLabel("Schermata login");

        JLabel email_label = new JLabel("Email");

        email_inserisci = new JTextField();
        email_inserisci.setColumns(10);

        password_inserisci = new JPasswordField();

        JLabel password_label = new JLabel("Password");

        JButton accedi_bottone = new JButton("Accedi");

        //LISTENER BOTTONE di accedi, dopo averlo premuto
        accedi_bottone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String email = email_inserisci.getText();
                String password = new String(password_inserisci.getPassword());

                AccessoController controller = new AccessoController();
                boolean success = controller.autenticaUtente(email, password);  //passo al controller stringhe email e password
                //CONTROLLO PRIMA SE ESISTE QUESTO UTENTE

                if (success) {
                    // POI prendi anche l'oggetto. IMPORTANTE PRELEVARE USERNAME
                    Utente utente = controller.getUtenteAutenticato(email, password);
                    String usernameReale = utente.getUsername(); //username pk del database, mostro il feed a lui. FONDAMENTALE

                    //per debug
                    System.out.println("Autore username: " + usernameReale);


                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Accesso effettuato!",
                            "Successo",
                            JOptionPane.INFORMATION_MESSAGE);

                    FeedFrame feed = new FeedFrame(usernameReale);
                    feed.setVisible(true);  // passo anche lo username!
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Email o password errati.",
                            "Errore di accesso",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(178)
                                .addComponent(login_label, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                .addGap(132))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(18)
                                                .addComponent(email_label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(password_label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(49)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                                        .addComponent(password_inserisci)
                                        .addComponent(email_inserisci))
                                .addContainerGap(246, Short.MAX_VALUE))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(190)
                                .addComponent(accedi_bottone)
                                .addContainerGap(201, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(30)
                                .addComponent(login_label)
                                .addGap(27)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(email_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(email_label))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(password_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(password_label))
                                .addGap(31)
                                .addComponent(accedi_bottone)
                                .addContainerGap(87, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
    }

}
