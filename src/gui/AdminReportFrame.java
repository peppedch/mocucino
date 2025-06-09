package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

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

            // MOCK, in futuro chiameremo DAO con from/to
            periodoResultArea.setText("Numero ricette pubblicate tra " + from + " e " + to + ": 18 (mock)");
        });

        // 2. Autori più attivi
        JPanel panelAutori = new JPanel(new BorderLayout());
        tabbedPane.addTab("Autori più attivi", null, panelAutori, null);

        JTextArea autoriArea = new JTextArea("1. @cucinaconluca - 12 ricette\n2. @giulia.food - 9 ricette\n3. @mariochef - 8 ricette");
        autoriArea.setEditable(false);
        panelAutori.add(new JScrollPane(autoriArea), BorderLayout.CENTER);

        // 3. Tag più usati
        JPanel panelTag = new JPanel(new BorderLayout());
        tabbedPane.addTab("Tag più usati", null, panelTag, null);

        JTextArea tagArea = new JTextArea("#dolci (21)\n#salato (15)\n#vegetariano (12)");
        tagArea.setEditable(false);
        panelTag.add(new JScrollPane(tagArea), BorderLayout.CENTER);

        // 4. Ricette con più interazioni
        JPanel panelTopRicette = new JPanel(new BorderLayout());
        tabbedPane.addTab("Top Ricette per interazioni", null, panelTopRicette, null);

        JTextArea topRicetteArea = new JTextArea("Tiramisù di Nonna Maria - 54 interazioni\nCarbonara Sbagliata - 49 interazioni");
        topRicetteArea.setEditable(false);
        panelTopRicette.add(new JScrollPane(topRicetteArea), BorderLayout.CENTER);
    }
}
