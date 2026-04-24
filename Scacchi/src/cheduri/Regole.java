package cheduri;

public class Regole {
    public boolean mossaValida(String pM, String pD, int r1, int c1, int r2, int c2, String[][] g, int colEP, boolean reM, boolean t1M, boolean t2M) {
        if (r1 == r2 && c1 == c2) return false;
        if (!pD.equals("") && pM.charAt(pM.length() - 1) == pD.charAt(pD.length() - 1)) return false;
        int dR = Math.abs(r1 - r2), dC = Math.abs(c1 - c2);

        if (pM.startsWith("R_")) {
            if (dR <= 1 && dC <= 1) return true;
            if (!reM && r1 == r2 && dC == 2) {
                if (c2 == 6 && !t2M && g[r1][5].equals("") && g[r1][6].equals("")) return true;
                if (c2 == 2 && !t1M && g[r1][1].equals("") && g[r1][2].equals("") && g[r1][3].equals("")) return true;
            }
            return false;
        }
        if (pM.startsWith("P_")) {
            int dir = pM.endsWith("B") ? -1 : 1;
            if (c1 == c2 && r2 == r1 + dir && pD.equals("")) return true;
            if (c1 == c2 && r1 == (pM.endsWith("B")?6:1) && r2 == r1+(2*dir) && pD.equals("") && g[r1+dir][c1].equals("")) return true;
            if (dR == 1 && dC == 1 && r2 == r1 + dir && !pD.equals("")) return true;
            if (dR == 1 && dC == 1 && r2 == r1 + dir && pD.equals("") && r1 == (pM.endsWith("B")?3:4) && c2 == colEP) return true;
            return false;
        }
        if (pM.startsWith("C_")) return (dR == 2 && dC == 1) || (dR == 1 && dC == 2);
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
}

