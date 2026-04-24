package cheduri;
import javax.swing.*;
import java.awt.*;

public class Scacchiera extends JPanel {
    public JButton[][] bottoni = new JButton[8][8];
    public String[][] griglia = new String[8][8];
    public JLabel labelTimer = new JLabel("Bianco: 05:00 | Nero: 05:00", SwingConstants.CENTER);
    private Click controller;

    public Scacchiera() {
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(8, 8));
        inizializzaPezzi();
        for (int r=0; r<8; r++) for (int c=0; c<8; c++) {
            bottoni[r][c] = new JButton();
            bottoni[r][c].setPreferredSize(new Dimension(80, 80));
            int rr=r, cc=c;
            bottoni[r][c].addActionListener(e -> controller.eseguiAzione(rr, cc));
            p.add(bottoni[r][c]);
        }
        controller = new Click(this);
        labelTimer.setFont(new Font("Arial", Font.BOLD, 16));
        add(labelTimer, BorderLayout.NORTH); add(p, BorderLayout.CENTER);
        disegna();
    }

    public void inizializzaPezzi() {
        String[] b = {"T", "C", "A", "Q", "R", "A", "C", "T"};
        for (int c=0; c<8; c++) {
            griglia[0][c]=b[c]+"_N"; griglia[1][c]="P_N";
            griglia[6][c]="P_B"; griglia[7][c]=b[c]+"_B";
            for (int r=2; r<6; r++) griglia[r][c]="";
        }
    }

    public void resetGioco() { inizializzaPezzi(); controller.reset(); disegna(); }

    public void disegna() {
        for (int r=0; r<8; r++) for (int c=0; c<8; c++) {
            String p = griglia[r][c];
            bottoni[r][c].setBackground((r+c)%2==0 ? Color.DARK_GRAY: Color.GRAY);
            if (!p.equals("")) {
                try {
                    ImageIcon icon = new ImageIcon("immagini/" + p + ".png");
                    Image img = icon.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH);
                    ImageIcon f = new ImageIcon(img);
                    bottoni[r][c].setIcon(f); bottoni[r][c].setDisabledIcon(f);
                    bottoni[r][c].setText("");
                } catch (Exception e) { bottoni[r][c].setText(p); }
            } else { bottoni[r][c].setIcon(null); bottoni[r][c].setDisabledIcon(null); bottoni[r][c].setText(""); }
        }
        if (controller != null) controller.aggiornaStatoBottoni();
    }
}


