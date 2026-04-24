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
            if (r == rigaSorgente && c == colSorgente) {
                rigaSorgente = -1; colSorgente = -1;
            } else {
                // Verifichiamo se la mossa è legale prima di spostare
                // Passiamo 'false' come ultimo parametro per ora (gestione scacco base)
                boolean legale = regole.mossaValida(vista.griglia[rigaSorgente][colSorgente], vista.griglia[r][c], rigaSorgente, colSorgente, r, c, false);
                
                if (legale) {
                    vista.griglia[r][c] = vista.griglia[rigaSorgente][colSorgente];
                    vista.griglia[rigaSorgente][colSorgente] = "";
                    turnoBianco = !turnoBianco; 
                }
                rigaSorgente = -1; colSorgente = -1;
            }
        
        }
        aggiornaStatoBottoni(); // Importante: aggiorna i colori e i permessi dopo ogni click
        vista.disegna(); 
    }
    

    public void aggiornaStatoBottoni() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (rigaSorgente == -1) {
                    String pezzo = vista.griglia[r][c];
                    boolean mioTurno = (turnoBianco && pezzo.endsWith("_B")) || (!turnoBianco && pezzo.endsWith("_N"));
                    vista.bottoni[r][c].setEnabled(!pezzo.equals("") && mioTurno);
                } else {
                    boolean legale = regole.mossaValida(vista.griglia[rigaSorgente][colSorgente], vista.griglia[r][c], rigaSorgente, colSorgente, r, c, false);
                    
                    // Se è la cella di partenza o una mossa legale, abilita il bottone
                    boolean selezionato = (r == rigaSorgente && c == colSorgente);
                    vista.bottoni[r][c].setEnabled(legale || selezionato);
                    
                    if (legale) {
                        vista.bottoni[r][c].setBackground(Color.YELLOW);
                    } else if (selezionato) {
                        vista.bottoni[r][c].setBackground(Color.CYAN); // Evidenzia il pezzo scelto
                    }
                }
            }
        }
    }

            
        
    }

