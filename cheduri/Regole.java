package cheduri;

public class Regole {
    public boolean mossaValida(String pInMano, String pDest, int r1, int c1, int r2, int c2, boolean metteInScaccoIlRe) {
        // Non si può restare sul posto o muoversi se la mossa espone il proprio Re allo scacco
        if ((r1 == r2 && c1 == c2) || metteInScaccoIlRe) return false;
        
        // Non si può mangiare un pezzo dello stesso colore
        if (!pDest.equals("")) {
            if (pInMano.charAt(pInMano.length()-1) == pDest.charAt(pDest.length()-1)) return false;
        }

        int dR = Math.abs(r1 - r2);
        int dC = Math.abs(c1 - c2);

        // Regole Pezzi standard
        if (pInMano.startsWith("R_")) return dR <= 1 && dC <= 1;
        if (pInMano.startsWith("T_")) return r1 == r2 || c1 == c2;
        if (pInMano.startsWith("A_")) return dR == dC;
        if (pInMano.startsWith("Q_")) return r1 == r2 || c1 == c2 || dR == dC;
        if (pInMano.startsWith("C_")) return (dR == 2 && dC == 1) || (dR == 1 && dC == 2);

        // Regole Pedone (Movimento e Cattura)
        if (pInMano.startsWith("P_")) {
            int direzione = pInMano.endsWith("B") ? -1 : 1; // Bianco sale (-1), Nero scende (+1)
            
            // Movimento dritto (solo se la destinazione è vuota)
            if (c1 == c2 && pDest.equals("")) {
                return r2 == r1 + direzione;
            }
            // Cattura diagonale (solo se la destinazione NON è vuota)
            if (dR == 1 && dC == 1 && !pDest.equals("")) {
                return r2 == r1 + direzione;
            }
        }

        return false;
    }
}

