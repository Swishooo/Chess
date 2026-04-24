package cheduri;

import javax.swing.*;
import java.awt.*;

public class Scacchiera extends JPanel {
    public JButton[][] bottoni = new JButton[8][8];
    public String[][] griglia = new String[8][8];
    private Click controller; 

    public Scacchiera() {
        setLayout(new GridLayout(8, 8));
        
        // 1. Prima i dati
        inizializzaPezzi();
        
        // 2. Poi creiamo i bottoni (senza disegnarli ancora)
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                bottoni[r][c] = new JButton();
                bottoni[r][c].setPreferredSize(new Dimension(80, 80));
                
                // Necessario per rendere visibili i colori su alcuni sistemi
                bottoni[r][c].setOpaque(true);
                bottoni[r][c].setContentAreaFilled(true);
                bottoni[r][c].setBorderPainted(true);
                
                int riga = r; int colonna = c;
                bottoni[r][c].addActionListener(e -> controller.eseguiAzione(riga, colonna));
                add(bottoni[r][c]);
            }
        }
        
        // 3. Poi il controller (ora che i bottoni esistono!)
        controller = new Click(this);
        
        // 4. Infine disegniamo tutto
        disegna();
    }

    private void inizializzaPezzi() {
        String[] base = {"T", "C", "A", "Q", "R", "A", "C", "T"};
        for (int c = 0; c < 8; c++) {
            griglia[0][c] = base[c] + "_N";
            griglia[1][c] = "P_N";
            griglia[6][c] = "P_B";
            griglia[7][c] = base[c] + "_B";
            for (int r = 2; r < 6; r++) griglia[r][c] = "";
        }
    }

    public void disegna() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                // Scriviamo il testo del pezzo
                bottoni[r][c].setText(griglia[r][c]);
                
                // Coloriamo la scacchiera base
                if ((r + c) % 2 == 0) bottoni[r][c].setBackground(Color.WHITE);
                else bottoni[r][c].setBackground(Color.LIGHT_GRAY);
            }
        }
        // Applichiamo le regole di abilitazione e i colori delle mosse (Giallo)
        controller.aggiornaStatoBottoni(); 
    }
}

