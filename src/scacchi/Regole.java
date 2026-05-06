package scacchi;
import javax.swing.JButton;

public class Regole {
    // Aggiunto l'ultimo parametro JButton[][] b
	public boolean mossaValida(String p1, String p2, int r1, int c1, int r2, int c2, JButton[][] b) {
	    // 1. Non muovere sulla stessa casella
	    if (r1 == r2 && c1 == c2) return false;
	    
	    // 2. Non mangiare pezzo dello stesso colore
	    if (!p2.equals("") && p1.charAt(p1.length()-1) == p2.charAt(p2.length()-1)) return false;

	    int dR = Math.abs(r1 - r2);
	    int dC = Math.abs(c1 - c2);

	    // RE e CAVALLO
	    if (p1.startsWith("R_")) return dR <= 1 && dC <= 1;
	    if (p1.startsWith("C_")) return (dR == 2 && dC == 1) || (dR == 1 && dC == 2);
	    
	    // TORRE, ALFIERE, REGINA (usano libera per controllare il percorso)
	    if (p1.startsWith("T_")) {
	        if (r1 == r2 || c1 == c2) return libera(r1, c1, r2, c2, b);
	    }
	    if (p1.startsWith("A_")) {
	        if (dR == dC) return libera(r1, c1, r2, c2, b);
	    }
	    if (p1.startsWith("Q_")) {
	        if (r1 == r2 || c1 == c2 || dR == dC) return libera(r1, c1, r2, c2, b);
	    }

	    // PEDONE
	    if (p1.startsWith("P_")) {
	        boolean bianco = p1.endsWith("B");
	        int dir = bianco ? -1 : 1;
	        int rigaIniziale = bianco ? 6 : 1;

	        // Avanzamento dritto di 1
	        if (c1 == c2 && p2.equals("") && r2 == r1 + dir) return true;
	        
	        // Avanzamento dritto di 2 (solo alla prima mossa)
	        if (c1 == c2 && p2.equals("") && r1 == rigaIniziale && r2 == r1 + (2 * dir)) {
	            // Controlla che anche la casella intermedia sia libera
	            return b[r1 + dir][c1].getText().equals("");
	        }
	        
	        // Cattura diagonale
	        if (dR == 1 && dC == 1 && !p2.equals("") && r2 == r1 + dir) return true;
	        
	        //re che non si puo muovere in caselle sotto attacco
	    }

	    return false;
	}


    // NUOVO: Metodo per controllare se ci sono pezzi tra la partenza e l'arrivo
    private boolean libera(int r1, int c1, int r2, int c2, JButton[][] b) {
        int sR = Integer.compare(r2, r1); // Direzione riga (1, -1 o 0)
        int sC = Integer.compare(c2, c1); // Direzione colonna (1, -1 o 0)
        int r = r1 + sR;
        int c = c1 + sC;
        
        while (r != r2 || c != c2) {
            if (!b[r][c].getText().equals("")) return false; // C'è qualcosa in mezzo!
            r += sR;
            c += sC;
        }
        return true;
    }

    public boolean casellaSottoAttacco(int r, int c, String colDif, String[][] g) {
        String att = colDif.equals("B") ? "N" : "B";
        for (int i=0; i<8; i++) for (int j=0; j<8; j++) {
            if (g[i][j].endsWith("_" + att)) {
                int dR = Math.abs(i - r), dC = Math.abs(j - c);
                if (g[i][j].startsWith("P_")) { if (dR == 1 && dC == 1 && r == i + (g[i][j].endsWith("B") ? -1 : 1)) return true; }
                else if (mossaSemplice(g[i][j], i, j, r, c, g)) return true;
            }
        }
        return false;
    }
    private boolean mossaSemplice(String pM, int r1, int c1, int r2, int c2, String[][] g) {
        int dR = Math.abs(r1 - r2), dC = Math.abs(c1 - c2);
        if (pM.startsWith("C_")) return (dR == 2 && dC == 1) || (dR == 1 && dC == 2);
        if (pM.startsWith("R_")) return dR <= 1 && dC <= 1;
        boolean ret = (r1 == r2 || c1 == c2), dia = (dR == dC);
        if ((pM.startsWith("T_") && ret) || (pM.startsWith("A_") && dia) || (pM.startsWith("Q_") && (ret || dia))) return percorsoLibero(r1, c1, r2, c2, g);
        return false;
    }
    public boolean percorsoLibero(int r1, int c1, int r2, int c2, String[][] g) {
        int sR = Integer.compare(r2, r1), sC = Integer.compare(c2, c1);
        int r = r1 + sR, c = c1 + sC;
        while (r != r2 || c != c2) {
            if (!g[r][c].equals("")) return false;
            r += sR; c += sC;
        }
        return true;
    }
}

