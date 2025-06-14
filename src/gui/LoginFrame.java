package gui;

import java.awt.EventQueue;
import controller.AccessoController;
import controller.GestoreController;
import entity.Utente;
import dto.UtenteDTO;

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

                AccessoController controller = AccessoController.getInstance(); //singleton controller per accesso.
                UtenteDTO utenteDTO = controller.getUtenteAutenticato(email, password);   //check se le credenziali sono corrette ed eventualmente restituisce l'utente autenticato come DTO.

                if (utenteDTO != null) {   //se l'utente esiste nel db
                    String usernameReale = utenteDTO.getUsername();    //prendo lo username dell'utente autenticato- fondamentale perchè è la pk nella table Utenti e mi serve ad esempio per fare una discrimazione importante: ovvero, in feedframe, mi devono uscire solo ricette di ALTRI AUTORI (ALTRI USERNAME) e non quella dell'utente corrente che ha effettuato l'accesso. Quindi, per fare questo, devo passare lo username dell'utente autenticato al feedframe, e poi in feedframe fare una query che mi restituisce le ricette di ALTRI AUTORI (ovvero con username diverso da quello passato come parametro al feedframe, che sarebbe l'utente loggato che sta usando la piattaforma).

                    //per debug
                    System.out.println("Autore username: " + usernameReale);

                    // Imposta l'utente corrente nel GestoreController passando il DTO, e poi è il controller che "spacchetta" il DTO e istanzia l'oggetto Utente.
                    GestoreController.getInstance().setUtenteCorrente(utenteDTO);  //per quanto commentato appena sopra, importante. sarà importante anche per altre query, visualizzare le rispettive raccolte in area personale di quelL'UTENTE SPECIFICO.       

                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Accesso effettuato!",
                            "Successo",
                            JOptionPane.INFORMATION_MESSAGE);

                    FeedFrame feed = new FeedFrame(usernameReale);      //mostro in automatico il feed all'utente appena loggato. ecco l'importanza di passare lo username (pk) dell'utente appena loggato.
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
