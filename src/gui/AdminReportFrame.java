package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import controller.GestoreController;
import dto.ReportAutoriDTO;
import dto.ReportTagDTO;
import dto.ReportTopRicetteDTO;
import java.sql.Date;

public class AdminReportFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea reportArea;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminReportFrame frame = new AdminReportFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminReportFrame() {
        setTitle("Dashboard Amministratore - Reportistica");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 600);

        GestoreController controller = new GestoreController();

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        // 1. Ricette per intervallo di tempo
        JPanel panelPeriodo = new JPanel(new BorderLayout());
        tabbedPane.addTab("Ricette per intervallo", null, panelPeriodo, null);

        JPanel inputPanel = new JPanel();
        JLabel lblDal = new JLabel("Dal (YYYY-MM-DD):");
        JTextField fromField = new JTextField(10);
        JLabel lblAl = new JLabel("Al (YYYY-MM-DD):");
        JTextField toField = new JTextField(10);
        JButton cercaBtn = new JButton("Cerca");

        inputPanel.add(lblDal);
        inputPanel.add(fromField);
        inputPanel.add(lblAl);
        inputPanel.add(toField);
        inputPanel.add(cercaBtn);
        panelPeriodo.add(inputPanel, BorderLayout.NORTH);

        JTextArea periodoResultArea = new JTextArea();
        periodoResultArea.setEditable(false);
        panelPeriodo.add(new JScrollPane(periodoResultArea), BorderLayout.CENTER);

        cercaBtn.addActionListener(e -> {
            String from = fromField.getText().trim();
            String to = toField.getText().trim();
            if (from.isEmpty() || to.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci entrambe le date.");
                return;
            }
            try {
                Date fromDate = Date.valueOf(from);
                Date toDate = Date.valueOf(to);
                // GUI -> Controller: Richiesta report 1
                // Chiamata a GestoreController.generaReportNumRicette() [riga X]
                int totale = controller.generaReportNumRicette(fromDate, toDate);
                periodoResultArea.setText("Numero ricette pubblicate tra " + from + " e " + to + ": " + totale);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato data non valido. Usa YYYY-MM-DD.");
            }
        });

        // 2. Autori più attivi
        JPanel panelAutori = new JPanel(new BorderLayout());
        tabbedPane.addTab("Autori più attivi", null, panelAutori, null);

        JTextArea autoriArea = new JTextArea();
        autoriArea.setEditable(false);
        panelAutori.add(new JScrollPane(autoriArea), BorderLayout.CENTER);

        // 3. Tag più usati
        JPanel panelTag = new JPanel(new BorderLayout());
        tabbedPane.addTab("Tag più usati", null, panelTag, null);

        JTextArea tagArea = new JTextArea();
        tagArea.setEditable(false);
        panelTag.add(new JScrollPane(tagArea), BorderLayout.CENTER);

        // 4. Ricette con più interazioni
        JPanel panelTopRicette = new JPanel(new BorderLayout());
        tabbedPane.addTab("Top ricette per interazioni", null, panelTopRicette, null);

        JTextArea topRicetteArea = new JTextArea();
        topRicetteArea.setEditable(false);
        panelTopRicette.add(new JScrollPane(topRicetteArea), BorderLayout.CENTER);

        // Listener per aggiornare i report quando si seleziona il tab
        tabbedPane.addChangeListener(e -> {
            int idx = tabbedPane.getSelectedIndex();
            if (idx == 1) { // Autori più attivi
                // GUI -> Controller: Richiesta report 2
                // Chiamata a GestoreController.generaReportAutori() [riga X]
                List<ReportAutoriDTO> autori = controller.generaReportAutori();
                StringBuilder sb = new StringBuilder();
                int pos = 1;
                for (ReportAutoriDTO a : autori) {
                    sb.append(pos++).append(". ").append(a.getAutore()).append(" - ").append(a.getNumeroRicette()).append(" ricette\n");
                }
                autoriArea.setText(sb.toString());
            } else if (idx == 2) { // Tag più usati
                // GUI -> Controller: Richiesta report 3
                // Chiamata a GestoreController.generaReportTag() [riga X]
                List<ReportTagDTO> tag = controller.generaReportTag();
                StringBuilder sb = new StringBuilder();
                for (ReportTagDTO t : tag) {
                    sb.append("#").append(t.getTag()).append(" (").append(t.getConteggio()).append(")\n");
                }
                tagArea.setText(sb.toString());
            } else if (idx == 3) { // Top ricette per interazioni
                // GUI -> Controller: Richiesta report 4
                // Chiamata a GestoreController.generaReportTopRicette() [riga X]
                List<ReportTopRicetteDTO> ricette = controller.generaReportTopRicette();
                StringBuilder sb = new StringBuilder();
                for (ReportTopRicetteDTO r : ricette) {
                    sb.append(r.getTitoloRicetta()).append(" - ").append(r.getNumeroInterazioni()).append(" interazioni\n");
                }
                topRicetteArea.setText(sb.toString());
            }
        });
    }
}
