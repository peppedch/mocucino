package gui;

//anche qui importati i vari moduli javax.swing e il modulo CommentoDTO nel package dto da me creato
//serve per visualizzare i 3 commenti recenti una volta cliccata la ricetta da feed (e quindi ora ci troviamo nella ricetta in dettaglio comn tutto)
//nel costruttore sotto infatti c'è List<CommentoDTO> commentiRecenti
import controller.GestoreController;
import dto.CommentoDTO;
import dto.RicettaDTO;


import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;

public class DettaglioRicettaFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private String titolo;
    private String autore;
    private int like;
    private String ingredienti;
    private String descrizione;
    private int tempoPreparazione;
    private List<String> tag;

    private RicettaDTO ricetta;     //mi serve per avere un "riferimento " alla ricetta  da aprire nel dettaglio.
    private String username; //  utente loggato, NON autore della ricetta, passato sempre da feedframe a partire dal login.

    /**
     * Launch the application.
     */
    public static void main(String[] args) {        //creo ricetta fittizia necessaria per passarla al costruttore dell'eseguibile e testare. chiaramente il vero flusso parte dal costruttore a riga 74, e d lì gli viene passata la ricetta effettiva.
        // Creo una lista di ingredienti fittizi
        List<dto.IngredienteDTO> ingredienti = new ArrayList<>();
        ingredienti.add(new dto.IngredienteDTO("Farina", "200", "g"));
        ingredienti.add(new dto.IngredienteDTO("Acqua", "100", "ml"));
        ingredienti.add(new dto.IngredienteDTO("Sale", "1", "pz"));

        // Creo una lista di tag fittizi
        List<String> tag = new ArrayList<>();
        tag.add("Dolce");
        tag.add("Vegano");

        // Creo una RicettaDTO fittizia, QUESTO SOLO PER QUESTO MAIN, OPER RUNNARLO E TESTARLO, MA IL VERO OGGETTO RICETTA DTO è PASSATO DA FEEDFRAME o da ricettaraccltaframe
            RicettaDTO ricetta = new RicettaDTO(
            "Paneveloce",
            "Mescola .",
            30,
            ingredienti,
            tag
        );

        String username = "utenteprova"; // Simulazione di un utente loggato. Once again, i veri parametri sono passati da FeedFrame e il costruttore da vedere è quello sotto, questo è il main per testare.

        EventQueue.invokeLater(() -> {
            try {
                DettaglioRicettaFrame frame = new DettaglioRicettaFrame(ricetta, username);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    //ORA INIZIA IL COSTRUTTORE DELLA FINESTRA DETTAGLIO RICETTA, CHE SI APRE QUANDO L'UTENTE CLICCA SULLA RICETTA NELLA FEEDFRAME O NELLA RACCOLTA. QUELLO SOPRA SERVIVA SOLO PER TESTARE IL RUNNABLE.
    /**
     * Create the frame.
     */
    public DettaglioRicettaFrame(RicettaDTO ricetta, String username) {

        this.ricetta = ricetta; // Salvo la ricetta passata come parametro
        this.username = username; // Salvo l'username dell'utente loggato passato come parametro.

        this.titolo = ricetta.getTitolo();
        this.autore = ricetta.getAutoreUsername();
        this.like = ricetta.getNumeroLike();
        this.ingredienti = ricetta.getIngredientiAsString();    // Converto lista di IngredientiDTO in stringa leggibile
        this.descrizione = ricetta.getDescrizione();
        this.tempoPreparazione = ricetta.getTempoPreparazione();
        this.tag = ricetta.getTag();


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  //chiudo solo finestra corrente, non tutto il programma->altrimenti EXIT_ON_CLOSE
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));	//meglio una disposizione verticale in caso di commenti lunghi


        // Costruzione UI dinamica
        contentPane.removeAll();	// Pulisce tutto quello che c’era prima
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentPane.add(new JLabel("Titolo: " + titolo));
        contentPane.add(new JLabel("Autore: " + autore));
        contentPane.add(new JLabel("Like: " + like));
        contentPane.add(new JLabel("Ingredienti: " + ingredienti));

        JTextArea areaDescrizione = new JTextArea(descrizione);
        areaDescrizione.setLineWrap(true);
        areaDescrizione.setWrapStyleWord(true);
        areaDescrizione.setEditable(false);

        // ScrollPane per la descrizione, necessariamente per scrollare che non entra tutta la decsrizione
        JScrollPane scrollDescrizione = new JScrollPane(areaDescrizione);
        scrollDescrizione.setPreferredSize(new Dimension(400, 120));	//se la descrizione supera 120 px di altezza compare lo scroll verticale. 400 px di larghezza fissi
        scrollDescrizione.setMaximumSize(new Dimension(400, 120));
        scrollDescrizione.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentPane.add(new JLabel("Descrizione:"));
        contentPane.add(scrollDescrizione);


        contentPane.add(new JLabel("Tempo di preparazione: " + tempoPreparazione + " min"));
        contentPane.add(new JLabel("Tag: " + String.join(", ", tag)));

        // COMMENTI RECENTI
        contentPane.add(new JLabel("Commenti recenti:"));

        List<CommentoDTO> commenti = ricetta.getCommentiRecenti();
        if (commenti != null && !commenti.isEmpty()) {
            for (CommentoDTO c : commenti) {
                JTextArea area = new JTextArea(c.getAutore() + " (" + c.getData() + "): " + c.getTesto());
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setEditable(false);            //ovviamente i commenti non sono editabili
                area.setBackground(contentPane.getBackground());
                area.setAlignmentX(Component.LEFT_ALIGNMENT);
                area.setMaximumSize(new Dimension(400, 40)); // limita larghezza e altezza  senno si allarga troppo

                contentPane.add(area);
                contentPane.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        } else {
            contentPane.add(new JLabel("Nessun commento disponibile."));
        }


        //  Sezione Interazione: Like + Commento
        JButton btnLike = new JButton("Metti Like");
        btnLike.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(btnLike);

        //LISTENER PER IL LIKE
        btnLike.addActionListener(e -> {
            GestoreController controller =  GestoreController.getInstance();
            System.out.println("LIKE su ID Ricetta: " + ricetta.getIdRicetta());    //stampa debug per vedere se il like funziona correttamente
            boolean likeAggiunto = controller.toggleLike(username, ricetta.getIdRicetta());     //RICORDA: ricetta l'oggetto DTO che ho passato al costruttore e lo stesso username

            ricetta.setNumeroLike(ricetta.getNumeroLike() + (likeAggiunto ? 1 : -1));
            JOptionPane.showMessageDialog(this,
                    likeAggiunto ? "Hai messo like!" : "Like rimosso.");
        });

        JTextField txtCommento = new JTextField();
        txtCommento.setMaximumSize(new Dimension(400, 25));
        contentPane.add(new JLabel("Lascia un commento:"));
        contentPane.add(txtCommento);

        JButton btnCommenta = new JButton("Commenta");
        btnCommenta.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.add(btnCommenta);

        //LISTENER PER IL COMMENTO
        btnCommenta.addActionListener(e -> {
            String testo = txtCommento.getText().trim();
            if (testo.isBlank()) {
                JOptionPane.showMessageDialog(this, "Il commento non può essere vuoto.");
                return;
            }

            GestoreController controller =  GestoreController.getInstance();
            boolean successo = controller.aggiungiCommento(username, ricetta.getIdRicetta(), testo);

            if (successo) {
                JOptionPane.showMessageDialog(this, "Commento aggiunto!");
                txtCommento.setText("");  // pulisci il campo
                ricetta.setNumCommenti(ricetta.getNumCommenti() + 1);  // aggiorna il numero di commenti nella ricetta
            } else {
                JOptionPane.showMessageDialog(this, "Errore nell'aggiunta del commento.");
            }
        });


        contentPane.revalidate();
        contentPane.repaint();



    }

}