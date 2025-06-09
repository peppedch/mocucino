package gui;

//anche qui importati i vari moduli javax.swing e il modulo CommentoDTO nel package dto da me creato
//serve per visualizzare i 3 commenti recenti una volta cliccata la ricetta da feed (e quindi ora ci troviamo nella ricetta in dettaglio comn tutto)
//nel costruttore sotto infatti c'è List<CommentoDTO> commentiRecenti
import dto.CommentoDTO;


import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
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

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DettaglioRicettaFrame frame = new DettaglioRicettaFrame();
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
    public DettaglioRicettaFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));	//meglio una disposizione verticale in caso di commenti lunghi

    }

    //questo sopra è il costruttore di default di winodows builder
    //Costruttore personalizzato per aprire il dettaglio con i dati:

    public DettaglioRicettaFrame(String titolo, String autore, int like, String ingredienti,
                                 String descrizione, int tempoPreparazione, List<String> tag, List<CommentoDTO> commentiRecenti) {

        this(); // richiama il costruttore base (mantiene compatibilità con Design)

        // Assegna ai campi interni
        this.titolo = titolo;
        this.autore = autore;
        this.like = like;
        this.ingredienti = ingredienti;
        this.descrizione = descrizione;
        this.tempoPreparazione = tempoPreparazione;
        this.tag = tag;


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

        //per i commenti recenti
        contentPane.add(new JLabel("Commenti recenti:"));
        if (commentiRecenti == null || commentiRecenti.isEmpty()) {
            contentPane.add(new JLabel("Nessun commento disponibile."));
        } else {
            for (CommentoDTO c : commentiRecenti) {
                String line = "- " + c.getAutore() + " (" + c.getData() + "): " + c.getTesto();
                contentPane.add(new JLabel(line));
            }
        }

        //  Sezione Interazione: Like + Commento
        JButton btnLike = new JButton("Metti Like");
        btnLike.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPane.add(btnLike);

        JTextField txtCommento = new JTextField();
        txtCommento.setMaximumSize(new Dimension(400, 25));
        contentPane.add(new JLabel("Lascia un commento:"));
        contentPane.add(txtCommento);

        JButton btnCommenta = new JButton("Commenta");
        btnCommenta.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.add(btnCommenta);

        // Azioni (placeholder)	//fare il check se gia l 'ha messso e toggle che si leva se clixìcca di nuovo
        btnLike.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Hai messo Like a: " + titolo);
        });

        btnCommenta.addActionListener(e -> {
            String commento = txtCommento.getText();
            if (commento.isBlank()) {
                JOptionPane.showMessageDialog(this, "Il commento non può essere vuoto.");
            } else {
                JOptionPane.showMessageDialog(this, "Hai commentato: \"" + commento + "\"");
                txtCommento.setText("");
            }
        });

        contentPane.revalidate();
        contentPane.repaint();

    }

}