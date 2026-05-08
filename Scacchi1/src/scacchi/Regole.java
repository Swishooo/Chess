package scacchi;
import javax.swing.JButton;

public class Regole {

   
    public boolean mossaValida(String p1, String p2, int r1, int c1, int r2, int c2, String[][] g) {
        if (r1 == r2 && c1 == c2) return false;
        if (!p2.equals("") && p1.charAt(p1.length()-1) == p2.charAt(p2.length()-1)) return false;

        int dR = Math.abs(r1 - r2);
        int dC = Math.abs(c1 - c2);

        if (p1.startsWith("R_")) return dR <= 1 && dC <= 1;
        if (p1.startsWith("C_")) return (dR == 2 && dC == 1) || (dR == 1 && dC == 2);
        
        if (p1.startsWith("T_")) {
            if (r1 == r2 || c1 == c2) return percorsoLibero(r1, c1, r2, c2, g);
        }
        if (p1.startsWith("A_")) {
            if (dR == dC) return percorsoLibero(r1, c1, r2, c2, g);
        }
        if (p1.startsWith("Q_")) {
            if (r1 == r2 || c1 == c2 || dR == dC) return percorsoLibero(r1, c1, r2, c2, g);
        }
        if (p1.startsWith("R_")) {
            if (dR <= 1 && dC <= 1) {
                // Il Re si muove di 1, ma la destinazione r2, c2 non deve essere attaccata
                String mioColore = p1.endsWith("B") ? "B" : "N";
                return !casellaSottoAttacco(r2, c2, mioColore, g);
            }
            return false;
        }
        if (p1.startsWith("P_")) {
            boolean bianco = p1.endsWith("B");
            int dir = bianco ? -1 : 1;
            int rigaIniziale = bianco ? 6 : 1;
            if (c1 == c2 && p2.equals("") && r2 == r1 + dir) return true;
            if (c1 == c2 && p2.equals("") && r1 == rigaIniziale && r2 == r1 + (2 * dir)) {
                return g[r1 + dir][c1].equals("");
            }
            if (dR == 1 && dC == 1 && !p2.equals("") && r2 == r1 + dir) return true;
        }
        return false;
    }

    public boolean casellaSottoAttacco(int r, int c, String colDif, String[][] g) {
        String att = colDif.equals("B") ? "N" : "B";
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (g[i][j].endsWith("_" + att)) {
                    // Passiamo g (griglia di stringhe) a mossaValida
                    if (mossaValida(g[i][j], g[r][c], i, j, r, c, g)) return true;
                }
            }
        }
        return false;
    }

    public boolean percorsoLibero(int r1, int c1, int r2, int c2, String[][] g) {
        int sR = Integer.compare(r2, r1);
        int sC = Integer.compare(c2, c1);
        int r = r1 + sR, col = c1 + sC;
        while (r != r2 || col != c2) {
            if (!g[r][col].equals("")) return false;
            r += sR; col += sC;
        }
        return true;
    }
}

