package gui;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;	//in dto.Commento meglio data così che come stringa per fare i filtraggi su report


import dto.CommentoDTO;
public class RicettaRaccoltaFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JList<String> ricetteList;
    private DefaultListModel<String> ricetteModel;

    /**
     * Launch the application (test temporaneo).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {	//test poi va cambiato
                    RicettaRaccoltaFrame frame = new RicettaRaccoltaFrame("Dolci al cucchiaio"); // test
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Crea il frame con nome raccolta
     */
    public RicettaRaccoltaFrame(String nomeRaccolta) {
        setTitle("Ricette nella raccolta: " + nomeRaccolta);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Lista ricette
        ricetteModel = new DefaultListModel<>();
        ricetteList = new JList<>(ricetteModel);
        JScrollPane scrollPane = new JScrollPane(ricetteList);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // MOCK: ricette della raccolta
        ricetteModel.addElement("Tiramisù - 120 like");
        ricetteModel.addElement("Panna cotta - 95 like");
        ricetteModel.addElement("Mousse al cioccolato - 78 like");

        // Click su ricetta (in futuro: apri dettaglio)
        ricetteList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String riga = ricetteList.getSelectedValue();
                if (riga == null) return;

                String titolo = riga.split("-")[0].trim(); // "Tiramisù"
                int like = Integer.parseInt(riga.replaceAll("[^0-9]", "")); // "120"

                // Dati simulati (mock) — sostituibili con query DAO future
                String autore = "Marco Bianchi";
                String ingredienti = "Mascarpone, uova, savoiardi, caffè, cacao";
                String descrizione = "Mescola tutto, inzuppa i savoiardi nel caffè, raffredda e servi con cacao.";
                int tempo = 15;
                List<String> tag = List.of("dolci", "dessert", "facile");

                List<CommentoDTO> commenti = new ArrayList<>();
                commenti.add(new CommentoDTO("Alice", "Buonissimo!", LocalDate.of(2025, 9, 10)));
                commenti.add(new CommentoDTO("Luca", "L’ho provato ieri, top!", LocalDate.of(2025, 6, 1)));
                commenti.add(new CommentoDTO("Giulia", "Facilissimo e veloce",LocalDate.of(2025, 7, 2)));


                DettaglioRicettaFrame frame = new DettaglioRicettaFrame(titolo, autore, like, ingredienti, descrizione, tempo, tag, commenti);
                frame.setVisible(true);
            }
        });
    }
}

