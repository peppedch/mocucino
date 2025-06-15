package gui;

import javax.swing.JOptionPane;		//aggiunto io per mostrare POPUp di errore password

//per rendere la piattaforma un safe environment avrei potuto magari inserire nel form di registrazione anche data e luogo nascita + codice fiscale e farne un check, per garantire una tracciabilità degli utenti e garantendo, nei limiti, tutti utenti come persone reali. ma superfluo e non richiesto dalla traccia

import java.awt.EventQueue;
import controller.AccessoController;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import dto.UtenteDTO;

public class RegisterFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField username_inserisci;
    private JTextField email_inserisci;
    private JTextField nome_inserisci;
    private JTextField cognome_inserisci;
    private JPasswordField password_inserisci;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegisterFrame frame = new RegisterFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //METODI UTILI ALL'INPUT VALIDATION NEL FORM, danno maggiore modularità;  chiaramente private in questo caso, attenendoci alle best practice

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false; // almeno una maiuscola
        if (!password.matches(".*[a-z].*")) return false; // almeno una minuscola
        if (!password.matches(".*\\d.*")) return false;   // almeno un numero
        return true;
    }

    private boolean isEmailValid(String email) {	//validazione mail piu generica possibile senza check su provider tipo @libero ecc.
        if (email == null || email.isEmpty()) {
            return false;
        }

        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");

        if (atIndex <= 0) {
            return false; // manca testo prima della @ o manca proprio
        }

        if (dotIndex <= atIndex + 1) {
            return false; // manca il punto dopo la @	(chiaramente per il ".it" o ".com" finale)s
        }

        if (dotIndex >= email.length() - 1) {
            return false; // il punto è alla fine, quindi non non valido
        }

        return true;
    }

    private boolean isNomeValido(String input) {
        if (input == null) return false;

        input = input.trim();
        if (input.isEmpty()) return false;

        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }

        return true;
    }

    /**
     * Create the frame.
     */
    public RegisterFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel registrazione_label = new JLabel("Schermata registrazione");

        JLabel username_label = new JLabel("Username");
        username_inserisci = new JTextField();
        username_inserisci.setColumns(10);

        JLabel email_label = new JLabel("Email");
        email_inserisci = new JTextField();
        email_inserisci.setColumns(10);

        JLabel nome_label = new JLabel("Nome");
        nome_inserisci = new JTextField();
        nome_inserisci.setColumns(10);

        JLabel cognome_label = new JLabel("Cognome");
        cognome_inserisci = new JTextField();
        cognome_inserisci.setColumns(10);

        JLabel password_label = new JLabel("Password");
        password_inserisci = new JPasswordField();

        //listener registrati button
        JButton registrati_bottone = new JButton("Registrati");
        registrati_bottone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = username_inserisci.getText();
                String nome = nome_inserisci.getText();
                String cognome = cognome_inserisci.getText();
                String email = email_inserisci.getText();
                String password = new String(password_inserisci.getPassword());

                //INPUT VALIDATION DOPO AVER RACCOLTO I DATI INSERITI, GRAZIE AI METODI MESSI SOPRA PRIMA DEL COSTRUTTORE

                if (!isPasswordValid(password)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "La password deve contenere almeno:\n- 8 caratteri\n- una lettera maiuscola\n- una lettera minuscola\n- un numero",
                            "Password non valida",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isEmailValid(email)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Inserisci un'email valida (es: nome@dominio.com)",
                            "Email non valida",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isNomeValido(nome)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Inserisci un nome valido (solo lettere, nessun numero o simbolo)",
                            "Nome non valido",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!isNomeValido(cognome)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Inserisci un cognome valido (solo lettere, nessun numero o simbolo)",
                            "Cognome non valido",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Creo un DTO con i dati validati
                UtenteDTO utenteDTO = new UtenteDTO(
                    username,
                    nome,
                    cognome,
                    email,
                    password
                );

                AccessoController controller = AccessoController.getInstance();
                boolean success = controller.registraUtente(utenteDTO);

                if (success) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Registrazione completata con successo!",
                            "Successo",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Errore durante la registrazione. Riprovare.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(148)
                                .addComponent(registrazione_label, GroupLayout.DEFAULT_SIZE, 1240, Short.MAX_VALUE)
                                .addGap(166))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(password_label, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                        .addComponent(email_label, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                        .addComponent(nome_label, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                        .addComponent(cognome_label, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                        .addComponent(username_label, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(cognome_inserisci, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(email_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(username_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nome_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(password_inserisci, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
                                .addGap(1365))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(197)
                                .addComponent(registrati_bottone)
                                .addContainerGap(1258, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(22)
                                .addComponent(registrazione_label, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addGap(20)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(username_label)
                                        .addComponent(username_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(20)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(email_label)
                                        .addComponent(email_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(16)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(nome_label)
                                        .addComponent(nome_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(12)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cognome_label)
                                        .addComponent(cognome_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(password_label)
                                        .addComponent(password_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(29)
                                .addComponent(registrati_bottone)
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        contentPane.setLayout(gl_contentPane);
    }
}
