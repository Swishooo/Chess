package cheduri;

public class Regole {
    public boolean mossaValida(String pInMano, String pDest, int r1, int c1, int r2, int c2) {
        if (r1 == r2 && c1 == c2) return false;
        
        
        if (!pDest.equals("")) {
            if (pInMano.charAt(pInMano.length()-1) == pDest.charAt(pDest.length()-1)) return false;
        }

        int dR = Math.abs(r1 - r2);
        int dC = Math.abs(c1 - c2);

        if (pInMano.startsWith("R_")) return dR <= 1 && dC <= 1;
        if (pInMano.startsWith("T_")) return r1 == r2 || c1 == c2;
        if (pInMano.startsWith("A_")) return dR == dC;
        if (pInMano.startsWith("Q_")) return r1 == r2 || c1 == c2 || dR == dC;
        if (pInMano.startsWith("C_")) return (dR == 2 && dC == 1) || (dR == 1 && dC == 2);
        if (pInMano.equals("P_B")) return c1 == c2 && r2 == r1 - 1 && pDest.equals("");
        if (pInMano.equals("P_N")) return c1 == c2 && r2 == r1 + 1 && pDest.equals("");

        return false;
    }
}

