package gui;

import java.awt.EventQueue;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import dto.CommentoDTO;
import java.awt.Font;


public class FeedFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JButton areapersonale_button;
    private JButton logoutBtn;
    private JButton pubblica_bottone;
    private String username;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String username = "utenteprova";
                    FeedFrame frame = new FeedFrame(username);
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
    public FeedFrame(String username) {
        this.username=username;
        System.out.println("Username ricevuto in FeedFrame: " + this.username); //stampa per debug, verifico che ho ricevuto correttamente lo username da login
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JLabel feedricette = new JLabel("Feed Ricette");
        feedricette.setFont(new Font("Cambria", Font.BOLD, 20));

        JScrollPane scrollPane = new JScrollPane();

        //LISTENER PUBBLICA RICETTA
        pubblica_bottone = new JButton("Pubblica Ricetta");
        pubblica_bottone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                NuovaRicettaFrame nuova = new NuovaRicettaFrame(username);	//collegato al prossimo Frame x crear la ricetta
                nuova.setVisible(true);

            }
        });

        //LISTENER BOTTONE AREA PERSONALE
        areapersonale_button = new JButton("Area Personale");
        areapersonale_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AreaPersonaleFrame areaFrame = new AreaPersonaleFrame();
                areaFrame.setVisible(true);
            }
        });

        //listener logout
        logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            int scelta = JOptionPane.showConfirmDialog(this,
                    "Sei sicuro di voler effettuare il logout?",
                    "Conferma Logout",
                    JOptionPane.YES_NO_OPTION);

            if (scelta == JOptionPane.YES_OPTION) {
                dispose();  // chiude il FeedFrame

                // avrei potuto fare Reset utente corrente usando quest'ultimo come singleton e poi:
                // UtenteCorrente.clear(); ma non complichiamoci la vita ora

                // Torno a LoginFrame
                LoginFrame login = new LoginFrame();
                login.setVisible(true);
            }
        });

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(39)
                                                .addComponent(feedricette)
                                                .addGap(219)
                                                .addComponent(areapersonale_button))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(85)
                                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(46, Short.MAX_VALUE))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(176)
                                .addComponent(pubblica_bottone)
                                .addGap(118)
                                .addComponent(logoutBtn)
                                .addContainerGap(37, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(14)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(feedricette, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(areapersonale_button))
                                .addGap(18)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(pubblica_bottone, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(logoutBtn))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table = new JTable();
        scrollPane.setViewportView(table);
        contentPane.setLayout(gl_contentPane);

        //ESEMPIO PER PROVARE, PI VEDIAMO CON accesso al DB, mostrare solo una vista compatta delle ricette nel feed, ora implemento un modom che cliccando su una rucetta mostra tutto!
        String[] colonne = {"Titolo", "Autore", "Like"};
        Object[][] dati = {
                {"Tiramisù", "Luca", 15},
                {"Carbonara", "Giulia", 22},
                {"Pizza Margherita", "Marco", 30}
        };

        table.setModel(new javax.swing.table.DefaultTableModel(dati, colonne));


        //inizialmente ho usato mouseclicked
		/*
		 table.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
       e il resto uguale come quello sotto, unica differenza le prime due righe.
});
		 */

        //ma così permette di renderlo compatibile anche a versione mobile come richiesto.

        //MODO PER, UNA VOLTA VISUALIZZATA UNA VISTA DELLE RICETTE, CLICCANDONE UNA ESCE TUTTO->DETTAGLIORICETTAFRAME!

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int rigaSelezionata = table.getSelectedRow();
                if (rigaSelezionata != -1) {
                    String titolo = table.getValueAt(rigaSelezionata, 0).toString();
                    String autore = table.getValueAt(rigaSelezionata, 1).toString();
                    int like = Integer.parseInt(table.getValueAt(rigaSelezionata, 2).toString());

                    //esempio per provare . queste righe poi vanno tolte perchè proviamo dal db
                    List<String> tag = List.of("dolci", "facile");
                    List<CommentoDTO> commenti = new ArrayList<>();
                    commenti.add(new CommentoDTO("Mario", "Ottima ricetta!", LocalDate.now().minusDays(1)));
                    commenti.add(new CommentoDTO("Sara", "L'ho provata, buonissima", LocalDate.now().minusDays(2)));
                    commenti.add(new CommentoDTO("Luca", "Facile e veloce!", LocalDate.now().minusDays(3)));


                    //ECCO QUI. GUARDARE FRAME "DettaglioRicettaFrame.java", stai instanziando un oggeto di DettaglioRicettaFrame per vedere la ricetta in dettaglio
                    DettaglioRicettaFrame dettaglio = new DettaglioRicettaFrame(titolo, autore, like, "Uova, mascarpone, savoiardi, caffè","Sbatti i tuorli con lo zucchero e il mascarpone, dopodichè inserisci nel microonde a 180 gradi per 10 minuti. Una volta fuori procedi con......",30,tag,commenti);
                    dettaglio.setVisible(true);		//chiaramente sto mettendo gli ultimi 4 vLORI Hardcoded, COME ESEMPI, poi andranno pasaati dall'input poi al db ecc..
                }
            }
        });




    }
}

