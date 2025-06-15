package gui;

import controller.GestoreController;
import dto.StatisticheDTO;
import javax.swing.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.Image;
import dto.ProfiloUtenteDTO;

public class
AreaPersonaleFrame extends JFrame {

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

    private String imgPath = null;		//serve per salvare percorso immagine del profilo caricata, chiaramente ci vorrebbe un server apposito per gestire le immagini, ma per uno scopo accademico va bene per il momento.
    private JFileChooser fileChooser;
    private ImageIcon icon;

    private JList<String> raccolteList;
    private DefaultListModel<String> raccolteModel;
    private JPanel raccoltepanel;
    private JScrollPane raccolteScroll;

    private RicettaRaccoltaFrame frame;

    private String username;
    private GestoreController controller;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String username = "testUser"; // nella realtà lo prendo da login e passato al feed e ora passato qui, questo è solo un mockup per testare come eseguibile.
                    AreaPersonaleFrame frame = new AreaPersonaleFrame(username);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AreaPersonaleFrame(String username) {
        this.username = username;
        this.controller =  GestoreController.getInstance();
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

        // Carica i dati del profilo
        ProfiloUtenteDTO profilo = controller.getProfiloUtente(username);
        if (profilo != null) {
            nomeField.setText(profilo.getNome());
            cognomeField.setText(profilo.getCognome());
            emailField.setText(profilo.getEmail());
            passwordField.setText(profilo.getPassword());
            bioTextArea.setText(profilo.getBiografia());
            
            if (profilo.getImmagine() != null && !profilo.getImmagine().isEmpty()) {
                ImageIcon icon = new ImageIcon(new ImageIcon(profilo.getImmagine()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
                imageLabel.setText("");
                imgPath = profilo.getImmagine();
            }
        }

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
                    imgPath = file.getAbsolutePath();

                    // Carica l'immagine e la ridimensiona a 150x150
                    icon = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                    imageLabel.setIcon(icon);
                    imageLabel.setText("");
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

                // Crea DTO con i dati aggiornati
                ProfiloUtenteDTO profiloAggiornato = new ProfiloUtenteDTO(
                    username,
                    nome,
                    cognome,
                    email,
                    password,
                    bio,
                    imgPath
                );

                // Salva le modifiche nel database
                boolean success = controller.aggiornaProfiloUtente(profiloAggiornato);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Modifiche salvate con successo.", "OK", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Errore durante il salvataggio delle modifiche.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
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

        // GUI -> Controller: Richiesta statistiche utente
        // Chiamata al controller per ottenere le statistiche
        // Implementata in GestoreController.getStatisticheUtente() riga 134
        StatisticheDTO stats = controller.getStatisticheUtente(username);

        likeTotaliLabel = new JLabel("Like ricevuti: " + stats.getTotalLikes());
        commentiTotaliLabel = new JLabel("Commenti ricevuti: " + stats.getTotalComments());
        ricettaTopLabel = new JLabel("Ricetta più apprezzata: " + stats.getMostLikedRecipe());

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

        //OCCUPIAMOCI DEL PANNELLO PER "LE MIE RACCOLTE"
        raccoltepanel = new JPanel();
        raccoltepanel.setBorder(BorderFactory.createTitledBorder("Le mie raccolte"));   //eccoci, da qui  l'inferno
        raccoltepanel.setBounds(31, 307, 209, 113);
        getContentPane().add(raccoltepanel);
        raccoltepanel.setLayout(new BorderLayout());

        raccolteModel = new DefaultListModel<>();
        raccolteList = new JList<>(raccolteModel);  //lista delle raccolte dell'utente
        raccolteScroll = new JScrollPane(raccolteList);	//per scrollare lista raccolte dell'utente

        raccoltepanel.add(raccolteScroll, BorderLayout.CENTER);
        getContentPane().add(raccoltepanel);

        // GUI a Controller: Richiesta raccolte utente
        // Chiamata al controller per ottenere le raccolte dell'utente
        // Implementata in GestoreController.getRaccolteUtente()
        List<String> raccolteUtente = controller.getRaccolteUtente(username);
        raccolteModel.clear();
        for (String r : raccolteUtente) {
            raccolteModel.addElement(r);        //gli mostro le raccolte dell'utente nella lista.
        }


        //LISTENER SE CLICCO SU UNA RACCOLTA, deve darmi le ricette che contiene, quindi apro un nuovo JFrame (RicettaRaccoltaFrame) con le ricette della raccolta selezionata
        raccolteList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String raccoltaSelezionata = raccolteList.getSelectedValue();
                frame = new RicettaRaccoltaFrame(raccoltaSelezionata, username);    //APRO QUESTO NUOVO JFRAME PER VISUALIZARE LE RICETTE DELLA RACCOLTA SELEZIONATA!
                frame.setVisible(true);


            }
        });

    }
}
