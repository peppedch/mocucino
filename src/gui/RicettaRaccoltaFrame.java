package gui;

import controller.GestoreController;
import dto.CommentoDTO;
import dto.RicettaDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class RicettaRaccoltaFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JList<String> ricetteList;
    private DefaultListModel<String> ricetteModel;

    private List<RicettaDTO> ricette; // per avere accesso al DTO della ricetta selezionato
    private String username;
    private String nomeRaccolta;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String username = "testUser";
                    String nomeRaccolta = "Test_Raccolta"; // esempio di nome raccolta
                    RicettaRaccoltaFrame frame = new RicettaRaccoltaFrame(nomeRaccolta, username);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public RicettaRaccoltaFrame(String nomeRaccolta, String username) {
        this.username = username;
        this.nomeRaccolta = nomeRaccolta;

        setTitle("Ricette nella raccolta: " + nomeRaccolta);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        ricetteModel = new DefaultListModel<>();
        ricetteList = new JList<>(ricetteModel);
        JScrollPane scrollPane = new JScrollPane(ricetteList);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        //  CHIAMO IL CONTROLLER PER PRENDERE LE RICETTE DELLA RACCOLTA
        GestoreController controller =  GestoreController.getInstance();
        this.ricette = controller.getRicetteDaRaccolta(nomeRaccolta, username);      //recupera tutte le ricette della raccolta selezionata precedentemente in AreaPersonaleFrame

        for (RicettaDTO r : ricette) {
            ricetteModel.addElement(r.getTitolo() + " - " + r.getNumeroLike() + " like");   //ora gli esce una vista di tutte le ricette della raccolta solo con il titolo e il numero di like
        }

        //ORA INVECE PROCEDIAMO A, PARTIRE DALLA VISTA CON SOLO TITOLO E LIKE, A MOSTRARE IL DETTAGLIO DELLA RICETTA SELEZIONATA DALL'UTENTE:

        // Listener click su una ricetta
        ricetteList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = ricetteList.getSelectedIndex();
                if (index >= 0 && index < ricette.size()) {
                    RicettaDTO selezionata = ricette.get(index);
                    DettaglioRicettaFrame dettaglio = new DettaglioRicettaFrame(selezionata, username); //riusiamo questo Jframe gia usato per feed in cui all'interno tutto è implementato per mostrare la ricetta selezionata con tutti i dettagli, commenti, like, ecc.
                    dettaglio.setVisible(true);             //mi sto accorgendo che c'è una certa riusabilità dei metodi e questa è un'ottima cosa, daje
                }
            }
        });
    }
}

