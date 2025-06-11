package gui;

import java.awt.EventQueue;
import javax.swing.ButtonGroup;

import dto.IngredienteDTO;
import controller.GestoreController;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;					//queste librerie importate perchè fatto alcune cose da codice anziche da design come per inserimento ingredienti.

import java.util.List;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import dto.RicettaDTO;
import javax.swing.JRadioButton;

public class NuovaRicettaFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField titolo_inserisci;
    private JTextField tempo_inserisci;
    private JTextField textField_ingrediente;
    private JTextField textField_quantita;
    private JTable ingredienti_table;

    private JCheckBox checkboxsalato;
    private JCheckBox checkboxdolce;
    private JCheckBox chechkboxdieta;
    private JCheckBox chckbox_spuntino;
    private JCheckBox chckbox_vegetariano;
    private JCheckBox chckbox_veloce;
    private JCheckBox chckbox_senzaglutine;
    private JCheckBox chckbox_primopiatto;
    private JCheckBox chckbox_vegano;
    private JCheckBox chckbx_italiano;

    private JLabel titolo_label;
    private JLabel descrizione_label;
    private JLabel tempo_label;
    private JLabel tag_label;
    private JButton pubblica_bottone;
    private JComboBox combo_unitamisura;
    private JLabel ingredienti_label;
    private JButton addingrediente_button;
    private JLabel aggiungi_ingrediente_label;
    private JLabel quantita_ingredienti_label;
    private JLabel misura_ingredienti_label;
    private JTextArea descrizione_inserisci;
    private JScrollPane scrollPane;
    private JLabel visbilita_label;
    private JRadioButton pubblica_button;
    private JRadioButton privata_button;

    private String username;    //passato dal login

    //QUESTA CLASSE è INCOCATA DA GUI.FEEDFRAME A RIGA 74, PER CREARE UNA NUOVA RICETTA DAL FRAME. DEVO PASSARGLI LO STESSO USERNAME DELL'UTENTE IN FEEDFRAME

    //per validazione di avere almeno un ingrediente, usata nel liestener del bottone "pubblica"
    private boolean haAlmenoUnIngrediente() {
        DefaultTableModel model = (DefaultTableModel) ingredienti_table.getModel();
        return model.getRowCount() > 0;
    }


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String username="utenteprova";
                    NuovaRicettaFrame frame = new NuovaRicettaFrame(username);
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
    //COSTRUTTORE da qui
    public NuovaRicettaFrame(String username) {     //è qui che recupoero effettivamente lo stesso username di feedframe che a sua volta è stato passato dal login
        this.username = username;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        titolo_label = new JLabel("Titolo");

        titolo_inserisci = new JTextField();
        titolo_inserisci.setColumns(10);

        descrizione_label = new JLabel("Descrizione");

        descrizione_inserisci = new JTextArea();
        //limiti senno se allargo finestra si sballa tutto. l'ing de luca a lezione ha cosniglioto come layout grid o flow ma group è piu figo, bisogna solo applicare qualche vincolo dimensione
        descrizione_inserisci.setLineWrap(true);
        descrizione_inserisci.setWrapStyleWord(true);
        descrizione_inserisci.setPreferredSize(new Dimension(300, 100));
        descrizione_inserisci.setMaximumSize(new Dimension(300, 100));

        //  Limita a max 800 caratteri
        descrizione_inserisci.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (descrizione_inserisci.getText().length() >= 800)
                    e.consume();
            }
        });

        tempo_label = new JLabel("Tempo (min)");

        tempo_inserisci = new JTextField();
        tempo_inserisci.setColumns(10);
        tempo_inserisci.setMaximumSize(new Dimension(80, 25));

        tag_label = new JLabel("Tag (selezionane almeno uno)");

        //LISTENER BOTTONE PUBBLICA
        pubblica_bottone = new JButton("Pubblica");
        pubblica_bottone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validazione visibilità
                if (!pubblica_button.isSelected() && !privata_button.isSelected()) {
                    JOptionPane.showMessageDialog(NuovaRicettaFrame.this,
                            "Seleziona la visibilità della ricetta",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //primo check
                if (!haAlmenoUnIngrediente()) {
                    JOptionPane.showMessageDialog(null,
                            "Inserisci almeno un ingrediente.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<IngredienteDTO> ingredienti = new ArrayList<>();
                DefaultTableModel model = (DefaultTableModel) ingredienti_table.getModel();

                for (int i = 0; i < model.getRowCount(); i++) {
                    String nome = model.getValueAt(i, 0).toString();
                    String quantita = model.getValueAt(i, 1).toString();
                    String unita = model.getValueAt(i, 2).toString();

                    ingredienti.add(new IngredienteDTO(nome, quantita, unita)); //creo dto e gli passo gli ingredienti raccolti
                }


                List<String> tagSelezionati = new ArrayList<>();    //aggiungo i tag spuntati
                if (checkboxsalato.isSelected()) tagSelezionati.add("salato");
                if (checkboxdolce.isSelected()) tagSelezionati.add("dolce");
                if (chechkboxdieta.isSelected()) tagSelezionati.add("salutare");
                if (chckbox_spuntino.isSelected()) tagSelezionati.add("spuntino");
                if (chckbox_vegetariano.isSelected()) tagSelezionati.add("vegetariano");
                if (chckbox_veloce.isSelected()) tagSelezionati.add("veloce");
                if (chckbox_senzaglutine.isSelected()) tagSelezionati.add("senza glutine");
                if (chckbox_primopiatto.isSelected()) tagSelezionati.add("primo piatto");
                if (chckbox_vegano.isSelected()) tagSelezionati.add("vegano");
                if (chckbx_italiano.isSelected()) tagSelezionati.add("italiano");

                //secondo check
                //la traccia richiede esplicitamente che l'utente deve selezionare almeno un tag per la creazione della ricetta
                if (tagSelezionati.isEmpty()) {
                    JOptionPane.showMessageDialog(NuovaRicettaFrame.this,
                            "Seleziona almeno un tag!",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //check titolo non vuoto
                String titolo = titolo_inserisci.getText().trim();
                if (titolo.isEmpty()) {
                    JOptionPane.showMessageDialog(NuovaRicettaFrame.this,
                            "Inserisci un titolo per la ricetta.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }


                //check per tempo intero positivo
                String tempoStr = tempo_inserisci.getText().trim();
                int tempo;

                try {
                    tempo = Integer.parseInt(tempoStr);
                    if (tempo <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(NuovaRicettaFrame.this,
                            "Inserisci un tempo valido (numero intero positivo).",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //check descrizione non nulla
                String descrizione = descrizione_inserisci.getText().trim();
                if (descrizione.isEmpty()) {
                    JOptionPane.showMessageDialog(NuovaRicettaFrame.this,
                            "Inserisci una descrizione per la ricetta.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }


                // Qui poi farai validazioni su altri campi + costruzione DTO

                // SCELTA RACCOLTA
                String nomeRaccoltaSelezionato = null;
                int idRaccolta = -1;

                String[] opzioni = {"Sì, raccolta esistente", "Sì, nuova raccolta", "No, usa raccolta di default"};
                int scelta = JOptionPane.showOptionDialog(NuovaRicettaFrame.this,
                        "Vuoi inserire la ricetta in una raccolta?",
                        "Gestione raccolta",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opzioni,
                        opzioni[0]);

                //ISTANZIO IL CONTROLLER PER INVOCARE I SUOI METODI E PARTIRE CON IL WORKFLOW FINO A DB!
                GestoreController controller = new GestoreController();

                if (scelta == 0) {      //prima opzione form
                    // RACCOLTA ESISTENTE
                    List<String> raccolteUtenteList = controller.getRaccolteUtente(username);   //prelevo le raccolte dell'utente per mostrargliele
                    String[] raccolteUtente = raccolteUtenteList.toArray(new String[0]);

                    String raccoltaSelezionata = (String) JOptionPane.showInputDialog(
                            NuovaRicettaFrame.this,
                            "Seleziona una raccolta:",
                            "Raccolte",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            raccolteUtente,
                            raccolteUtente.length > 0 ? raccolteUtente[0] : null);

                    if (raccoltaSelezionata != null) {
                        nomeRaccoltaSelezionato = raccoltaSelezionata;  //dopo averla selezionata. seleziona praticamente il titolo e non l'id, quindi devo recuperarlo dopo, infatti la riga sotto c'è questo metodo.
                        idRaccolta = controller.getIdRaccoltaByTitolo(nomeRaccoltaSelezionato, username);
                    }

                } else if (scelta == 1) {   //seconda opzione form, la crea
                    // NUOVA RACCOLTA
                    String nuovaRaccolta = JOptionPane.showInputDialog(NuovaRicettaFrame.this, "Inserisci nome nuova raccolta:");
                    if (nuovaRaccolta != null && !nuovaRaccolta.isBlank()) {
                        boolean creata = controller.creaNuovaRaccolta(nuovaRaccolta, username); //controller istanziato sopra a riga 258, vedi!
                        if (creata) {
                            nomeRaccoltaSelezionato = nuovaRaccolta;
                            //ne recupero anche l'id della raccolta appena creata, perche va assocciata come fk alla ricetta. ovviamente la creo prima nel db e solo in un secondo momento ne recupero l'id GRAZIE SL TITOLO CHE HA MESSO L'UTENTE. PERCIO LA NECESSITA DI AVERE UN RECUPERO TRAMITE IL TITOLO!
                            idRaccolta = controller.getIdRaccoltaByTitolo(nuovaRaccolta, username);
                        } else {
                            JOptionPane.showMessageDialog(NuovaRicettaFrame.this,
                                    "Errore durante la creazione della raccolta.",
                                    "Errore",
                                    JOptionPane.ERROR_MESSAGE);
                            return; //se non riesce a creare la raccolta, non posso procedere con la creazione della ricetta
                        }
                    }
                } else {    //terza opzione form, la default
                    nomeRaccoltaSelezionato = "Default";
                    idRaccolta = controller.getIdRaccoltaByTitolo("Default", username);
                }
                boolean visibilita = pubblica_button.isSelected();  // true se è selezionato "Pubblica", false se "Privata"


                //FINO AD ORA ABBIAMO SOLO "SELEZIONATO" LA RISPETTIVA RACCOLTA. ORA DEVO SALVARE LA RICETTA NORMALMENTE, MA ANCHE AGGIUNGERLA ALLA RISPETTIVA RACCOLTA DESIDERATA.
                //questo viene fatto esattamente a partire dal metodo entity.Piattaforma.creaRicetta(), richiamato però dal controller, rispettando i layer bced.
                //creo il dto e passo i dati raccolti al controller
                RicettaDTO nuovaRicetta = new RicettaDTO(
                        titolo,
                        descrizione,
                        tempo,
                        ingredienti,
                        tagSelezionati
                );
                nuovaRicetta.setVisibilita(visibilita);

                //passo a Ricetta DTO anche la raccolta selezionata ovviamente
                nuovaRicetta.setNomeRaccolta(nomeRaccoltaSelezionato);
                //passo al dto di Ricetta anche l'autore della ricetta, cioe lo username ottenuto inizialmente dal login, passato al costruttore FeedFrame, passato a NuovaRicettaFeedFrame!
                nuovaRicetta.setAutoreUsername(username);
                //setto anche l'id della raccolta selezionata
                idRaccolta = controller.getIdRaccoltaByTitolo(nomeRaccoltaSelezionato, username);
                nuovaRicetta.setIdRaccolta(idRaccolta);



                //invochiamo il controller nuovamente (già istanziato sopra) e salviamo la ricetta, passandogli tutti i dati dell'oggetto DTO come parametro
                boolean success = controller.creaRicetta(nuovaRicetta); //DA QUI IN POI SALVA SIA LA RICETTA CHE LA RICETTA NELLA RISPETTIVA RACCOLTA

                if (success) {
                    JOptionPane.showMessageDialog(NuovaRicettaFrame.this,
                            "Ricetta pubblicata con successo!",
                            "Successo",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(NuovaRicettaFrame.this,
                            "Errore durante la pubblicazione.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        checkboxsalato = new JCheckBox("Salato");

        checkboxdolce = new JCheckBox("Dolce");

        chechkboxdieta = new JCheckBox("Salutare");

        chckbox_spuntino = new JCheckBox("Spuntino");

        chckbox_vegetariano = new JCheckBox("Vegetariano");

        chckbox_veloce = new JCheckBox("Veloce");

        chckbox_senzaglutine = new JCheckBox("Senza glutine");

        chckbox_primopiatto = new JCheckBox("Primo piatto");

        chckbox_vegano = new JCheckBox("Vegano");

        chckbx_italiano = new JCheckBox("Italiano");

        textField_ingrediente = new JTextField();
        textField_ingrediente.setColumns(10);

        textField_quantita = new JTextField();
        textField_quantita.setColumns(10);

        textField_ingrediente.setPreferredSize(new Dimension(100, 25));
        textField_quantita.setPreferredSize(new Dimension(50, 25));


        combo_unitamisura = new JComboBox();
        combo_unitamisura.setMaximumSize(new Dimension(60, 25));
        combo_unitamisura.setModel(new DefaultComboBoxModel<>(new String[] {
                "g", "ml", "pz", "cucchiai", "tazze"
        }));


        ingredienti_label = new JLabel("Aggiungi Ingredienti");

        //LISTENER BOTTONE AGGIUNGI INGREDIENTE
        addingrediente_button = new JButton("Aggiungi");
        addingrediente_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = textField_ingrediente.getText().trim();
                String quantita = textField_quantita.getText().trim();
                String unita = (String) combo_unitamisura.getSelectedItem();

                if (nome.isEmpty() || quantita.isEmpty() || unita == null || unita.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Completa tutti i campi dell'ingrediente.");
                    return;
                }

                // Validazione quantità (DECIMAL(10,2))
                try {
                    double qty = Double.parseDouble(quantita);
                    if (qty <= 0) {
                        JOptionPane.showMessageDialog(null, "Inserisci una quantità valida (numero positivo).");
                        return;
                    }
                    // Verifica che non superi il limite di 10 cifre totali con 2 decimali
                    if (quantita.contains(".")) {
                        String[] parts = quantita.split("\\.");
                        if (parts[0].length() > 8 || parts[1].length() > 2) {
                            JOptionPane.showMessageDialog(null, "Inserisci una quantità valida (max 8 cifre intere e 2 decimali).");
                            return;
                        }
                    } else if (quantita.length() > 8) {
                        JOptionPane.showMessageDialog(null, "Inserisci una quantità valida (max 8 cifre intere).");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Inserisci una quantità valida (numero con al massimo 2 decimali).");
                    return;
                }

                DefaultTableModel model = (DefaultTableModel) ingredienti_table.getModel();
                model.addRow(new Object[] { nome, quantita, unita });

                //  RESETTO I CAMPI PER INSERIRE NUOVO INGREDIENTE
                textField_ingrediente.setText("");
                textField_quantita.setText("");
                combo_unitamisura.setSelectedIndex(0);
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(300, 80));	//attenzione se allarghi finestra
        aggiungi_ingrediente_label = new JLabel("Nome Ingrediente");

        quantita_ingredienti_label = new JLabel("Quantità");

        misura_ingredienti_label = new JLabel("Unità di misura");

        visbilita_label = new JLabel("Visibilità");

        pubblica_button = new JRadioButton("Pubblica");

        privata_button = new JRadioButton("Privata");

        ButtonGroup visibilitaGroup = new ButtonGroup();
        visibilitaGroup.add(pubblica_button);
        visibilitaGroup.add(privata_button);



        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(tempo_label)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(tempo_inserisci, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(descrizione_label)
                                                .addGap(10)
                                                .addComponent(descrizione_inserisci, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(ingredienti_label, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(39)
                                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                                                                        .addComponent(aggiungi_ingrediente_label)
                                                                                        .addComponent(textField_ingrediente, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
                                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                                                        .addComponent(textField_quantita, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(quantita_ingredienti_label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
                                                                                .addGap(31)
                                                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                                                        .addComponent(misura_ingredienti_label)
                                                                                        .addComponent(combo_unitamisura, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
                                                                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                .addComponent(tag_label)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
                                                                        .addComponent(chechkboxdieta, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(chckbox_senzaglutine, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                                .addComponent(checkboxsalato)
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(checkboxdolce)
                                                                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(chckbox_spuntino))
                                                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                                                .addComponent(chckbox_primopiatto)
                                                                                .addGap(16)
                                                                                .addComponent(chckbox_vegano)))))
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                                        .addComponent(chckbox_vegetariano)
                                                        .addComponent(chckbx_italiano))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(chckbox_veloce, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(addingrediente_button)))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(titolo_label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(titolo_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(237)
                                                .addComponent(pubblica_bottone))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(21)
                                                .addComponent(visbilita_label, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(pubblica_button)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(privata_button)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(20)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(titolo_label)
                                                        .addComponent(titolo_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(11)
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(aggiungi_ingrediente_label, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(quantita_ingredienti_label)))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap(50, Short.MAX_VALUE)
                                                .addComponent(misura_ingredienti_label, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(ingredienti_label)
                                        .addComponent(textField_ingrediente, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textField_quantita, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addingrediente_button)
                                        .addComponent(combo_unitamisura, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                .addGap(11)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(descrizione_inserisci, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(descrizione_label))
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(tempo_label)
                                        .addComponent(tempo_inserisci, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(32)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(tag_label)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(chckbox_senzaglutine)
                                                .addComponent(checkboxsalato)
                                                .addComponent(checkboxdolce)
                                                .addComponent(chckbox_spuntino)
                                                .addComponent(chckbox_vegetariano)
                                                .addComponent(chckbox_veloce)))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(chechkboxdieta)
                                        .addComponent(chckbox_primopiatto)
                                        .addComponent(chckbox_vegano)
                                        .addComponent(chckbx_italiano))
                                .addGap(27)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(visbilita_label)
                                        .addComponent(privata_button)
                                        .addComponent(pubblica_button))
                                .addGap(17)
                                .addComponent(pubblica_bottone)
                                .addGap(22))
        );

        ingredienti_table = new JTable();
        ingredienti_table.setFont(new Font("Tahoma", Font.PLAIN, 10));
        ingredienti_table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Ingrediente", "Quantit\u00E0", "Unit\u00E0"
                }
        ));
        scrollPane.setViewportView(ingredienti_table);
        contentPane.setLayout(gl_contentPane);
    }
}
