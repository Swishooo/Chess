package cheduri;

import java.awt.Color;

public class Click {
    private Scacchiera vista;
    private Regole regole = new Regole();
    private int rigaSorgente = -1, colSorgente = -1;
    private boolean turnoBianco = true;

    public Click(Scacchiera vista) {
        this.vista = vista;
    }

    public void eseguiAzione(int r, int c) {
        if (rigaSorgente == -1) {
           
            rigaSorgente = r;
            colSorgente = c;
        } else {
            // 
            if (r == rigaSorgente && c == colSorgente) {
                rigaSorgente = -1; colSorgente = -1;
            } else {
               
                vista.griglia[r][c] = vista.griglia[rigaSorgente][colSorgente];
                vista.griglia[rigaSorgente][colSorgente] = "";
                rigaSorgente = -1; colSorgente = -1;
                turnoBianco = !turnoBianco; 
            }
        }
        vista.disegna(); 
    }

    public void aggiornaStatoBottoni() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (rigaSorgente == -1) {
                   
                    String pezzo = vista.griglia[r][c];
                    boolean mioTurno = (turnoBianco && pezzo.endsWith("_B")) || (!turnoBianco && pezzo.endsWith("_N"));
                    vista.bottoni[r][c].setEnabled(mioTurno);
                } else {
                   
                    boolean legale = regole.mossaValida(vista.griglia[rigaSorgente][colSorgente], vista.griglia[r][c], rigaSorgente, colSorgente, r, c);
                    vista.bottoni[r][c].setEnabled(legale || (r == rigaSorgente && c == colSorgente));
                    if (legale) vista.bottoni[r][c].setBackground(Color.YELLOW);
                }
            }
        }
    }
}

