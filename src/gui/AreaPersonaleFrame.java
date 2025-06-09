package gui;

import gui.RicettaRaccoltaFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AreaPersonaleFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextArea bioTextArea;
    private JLabel imageLabel;
    private JButton caricaImgBtn;
    private JButton salvaBtn;
    private JLabel likeTotaliLabel;
    private JLabel commentiTotaliLabel;
    private JLabel ricettaTopLabel;
    private JButton tornaFeedBtn;

    private String imgPath = null;		//serve per salvare percorso immagine del profilo caricata
    private JFileChooser fileChooser;
    private ImageIcon icon;

    private JList<String> raccolteList;
    private DefaultListModel<String> raccolteModel;
    private JPanel raccoltepanel;
    private JScrollPane raccolteScroll;

    private RicettaRaccoltaFrame frame;
    private JButton creaRaccoltaBtn;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AreaPersonaleFrame frame = new AreaPersonaleFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AreaPersonaleFrame() {
        setTitle("Area Personale");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // Dati profilo
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 20, 80, 25);
        getContentPane().add(lblNome);

        nomeField = new JTextField();
        nomeField.setBounds(150, 20, 200, 25);
        getContentPane().add(nomeField);

        JLabel lblCognome = new JLabel("Cognome:");
        lblCognome.setBounds(20, 60, 80, 25);
        getContentPane().add(lblCognome);

        cognomeField = new JTextField();
        cognomeField.setBounds(150, 60, 200, 25);
        getContentPane().add(cognomeField);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 100, 80, 25);
        getContentPane().add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(150, 100, 200, 25);
        getContentPane().add(emailField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(20, 140, 80, 25);
        getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 140, 200, 25);
        getContentPane().add(passwordField);

        JLabel lblBio = new JLabel("Biografia:");
        lblBio.setBounds(20, 180, 80, 25);
        getContentPane().add(lblBio);

        bioTextArea = new JTextArea();
        JScrollPane scrollBio = new JScrollPane(bioTextArea);
        scrollBio.setBounds(150, 180, 200, 60);
        getContentPane().add(scrollBio);

        imageLabel = new JLabel("Nessuna immagine");
        imageLabel.setBounds(400, 20, 150, 150);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        getContentPane().add(imageLabel);

        //LISTENER BOTTONE CARICA IMMAGINE
        caricaImgBtn = new JButton("Carica Immagine");
        caricaImgBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleziona immagine profilo");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Immagini JPG e PNG", "jpg", "jpeg", "png"));

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileChooser.getSelectedFile();
                    imgPath = file.getAbsolutePath();						//qui salvo il percorso, in imgPath

                    // Carica l'immagine e la ridimensiona a 100x100
                    icon = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                    //imagelabel l'ho settata a 150 px x 150, faccio lo stesso qui
                    imageLabel.setIcon(icon);
                    imageLabel.setText(""); // rimuove eventuale testo "Nessuna immagine"
                }
            }
        });



        caricaImgBtn.setBounds(400, 180, 150, 25);
        getContentPane().add(caricaImgBtn);

        //LISTENER SALVA MODIFICHE BUTTON
        salvaBtn = new JButton("Salva modifiche");
        salvaBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String bio = bioTextArea.getText();

                // Validazione semplice
                if (nome.isBlank() || email.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Nome ed email sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validazione email
                if (!email.contains("@") || email.lastIndexOf(".") < email.indexOf("@")) {
                    JOptionPane.showMessageDialog(null, "Email non valida.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validazione password: almeno 8 caratteri, 1 maiuscola, 1 minuscola, 1 numero
                if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
                    JOptionPane.showMessageDialog(null,
                            "La password deve contenere almeno 8 caratteri, una maiuscola, una minuscola e un numero.",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }



                // Messaggio di conferma (niente salvataggio DB per ora)
                JOptionPane.showMessageDialog(null, "Modifiche salvate (simulazione).", "OK", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        salvaBtn.setBounds(220, 260, 160, 30);
        getContentPane().add(salvaBtn);

        // Statistiche
        JPanel statistichePanel = new JPanel();
        statistichePanel.setBorder(BorderFactory.createTitledBorder("Statistiche Personali"));
        statistichePanel.setBounds(20, 432, 540, 93);
        statistichePanel.setLayout(new GridLayout(3, 1));
        getContentPane().add(statistichePanel);

        likeTotaliLabel = new JLabel("Like ricevuti: 0");
        commentiTotaliLabel = new JLabel("Commenti ricevuti: 0");
        ricettaTopLabel = new JLabel("Ricetta più apprezzata: -");

        statistichePanel.add(likeTotaliLabel);
        statistichePanel.add(commentiTotaliLabel);
        statistichePanel.add(ricettaTopLabel);

        //LISTENER TORNA AL FEED BUTTON
        tornaFeedBtn = new JButton("Torna al Feed");
        tornaFeedBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();	//chiude la finestra di area personale e torna al feed
                //new FeedFrame().setVisible(true); meglio di no questa riga, mi piace che rimanga in background e ci torna
                //però in questo caso devo fare un metodo che permetta tipo un refresh, per mostrare dati aggiornati a run time
            }
        });
        tornaFeedBtn.setBounds(257, 547, 160, 30);
        getContentPane().add(tornaFeedBtn);

        raccoltepanel = new JPanel();
        raccoltepanel.setBorder(BorderFactory.createTitledBorder("Le mie raccolte"));
        raccoltepanel.setBounds(31, 307, 209, 113);
        getContentPane().add(raccoltepanel);
        raccoltepanel.setLayout(new BorderLayout());

        raccolteModel = new DefaultListModel<>();
        raccolteList = new JList<>(raccolteModel);
        raccolteScroll = new JScrollPane(raccolteList);	//per scrollare lista

        raccoltepanel.add(raccolteScroll, BorderLayout.CENTER);
        getContentPane().add(raccoltepanel);

        creaRaccoltaBtn = new JButton("Crea nuova raccolta");
        creaRaccoltaBtn.addActionListener(e -> {
            JTextField titoloField = new JTextField();
            JTextField descrizioneField = new JTextField();
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Titolo raccolta:"));
            panel.add(titoloField);
            panel.add(new JLabel("Descrizione raccolta:"));
            panel.add(descrizioneField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Crea nuova raccolta",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String titolo = titoloField.getText().trim();
                String descrizione = descrizioneField.getText().trim();

                if (titolo.isEmpty() || descrizione.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {
                    // MOCK: aggiunta alla lista raccolte
                    raccolteModel.addElement(titolo);  // per ora solo titolo nella lista
                    JOptionPane.showMessageDialog(this, "Raccolta '" + titolo + "' creata con successo.");
                }
            }
        });

        creaRaccoltaBtn.setBounds(300, 330, 160, 30);
        getContentPane().add(creaRaccoltaBtn);


        //TEST ESEMPIO, POI LO TOLGO PER PROVARE DON DB
        // MOCK: raccolte dell’utente, POI LI CARICHEREMO CON RaccoltaDAO.getRaccolteByUtenteId(...)
        raccolteModel.addElement("Dolci al cucchiaio");
        raccolteModel.addElement("Pranzo veloce");
        raccolteModel.addElement("Cene leggere");

        //LISTENER SE CLICCO SU UNA RICETTA
        raccolteList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String raccoltaSelezionata = raccolteList.getSelectedValue();
                frame = new RicettaRaccoltaFrame(raccoltaSelezionata);
                frame.setVisible(true);

                // In futuro: carica le ricette della raccolta
            }
        });

    }
}
