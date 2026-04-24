package cheduri;
import javax.swing.*;
import java.awt.Color;

public class Click {
    private Scacchiera vista;
    private Regole regole = new Regole();
    private int rS = -1, cS = -1, colEP = -1, tB = 300, tN = 300;
    private boolean turnoB = true, reBM = false, reNM = false, tB1M = false, tB2M = false, tN1M = false, tN2M = false;
    private Timer timer;

    public Click(Scacchiera vista) { this.vista = vista; avviaTimer(); }

    private void avviaTimer() {
        timer = new Timer(1000, e -> {
            if (turnoB) tB--; else tN--;
            vista.labelTimer.setText(String.format("Bianco: %02d:%02d | Nero: %02d:%02d", tB/60, tB%60, tN/60, tN%60));
            if (tB <= 0 || tN <= 0) finePartita("Tempo scaduto!");
        });
        timer.start();
    }

    public void eseguiAzione(int r, int c) {
        if (rS == -1) {
            String p = vista.griglia[r][c];
            if (!p.equals("") && ((turnoB && p.endsWith("B")) || (!turnoB && p.endsWith("N")))) { rS = r; cS = c; }
        } else {
            if (mossaSicura(rS, cS, r, c)) {
                String pM = vista.griglia[rS][cS], pD = vista.griglia[r][c];
                if (pM.startsWith("R_") && Math.abs(c - cS) == 2) {
                    int cTP = (c == 6) ? 7 : 0, cTA = (c == 6) ? 5 : 3;
                    vista.griglia[r][cTA] = vista.griglia[r][cTP]; vista.griglia[r][cTP] = "";
                }
                if (pM.startsWith("P_") && c != cS && pD.equals("")) vista.griglia[rS][c] = "";
                vista.griglia[r][c] = pM; vista.griglia[rS][cS] = "";
                if (pM.startsWith("P_") && (r == 0 || r == 7)) promo(r, c, pM.endsWith("B")?"_B":"_N");
                
                aggMem(pM, rS, cS);
                colEP = (pM.startsWith("P_") && Math.abs(r - rS) == 2) ? c : -1;
                turnoB = !turnoB;
                controllaFineMossa();
            }
            rS = -1; cS = -1;
        }
        vista.disegna();
    }

    public boolean mossaSicura(int r1, int c1, int r2, int c2) {
        String pM = vista.griglia[r1][c1], pD = vista.griglia[r2][c2];
        boolean reM = turnoB?reBM:reNM, t1 = turnoB?tB1M:tN1M, t2 = turnoB?tB2M:tN2M;
        if (!regole.mossaValida(pM, pD, r1, c1, r2, c2, vista.griglia, colEP, reM, t1, t2)) return false;
        vista.griglia[r2][c2] = pM; vista.griglia[r1][c1] = "";
        int[] rePos = trovaRe(turnoB ? "B" : "N");
        boolean sicuro = !regole.casellaSottoAttacco(rePos[0], rePos[1], turnoB ? "B" : "N", vista.griglia);
        vista.griglia[r1][c1] = pM; vista.griglia[r2][c2] = pD;
        return sicuro;
    }

    private void controllaFineMossa() {
        String colAvv = turnoB ? "B" : "N";
        int[] reAvv = trovaRe(colAvv);
        boolean sottoScacco = regole.casellaSottoAttacco(reAvv[0], reAvv[1], colAvv, vista.griglia);
        boolean haMosse = false;
        outer: for(int i=0; i<8; i++) for(int j=0; j<8; j++) if(vista.griglia[i][j].endsWith("_"+colAvv))
            for(int x=0; x<8; x++) for(int y=0; y<8; y++) if(mossaSicura(i, j, x, y)) { haMosse = true; break outer; }
        if (!haMosse) finePartita(sottoScacco ? "SCACCO MATTO!" : "STALLO!");
    }

    private void promo(int r, int c, String col) {
        String[] op = {"Regina", "Torre", "Alfiere", "Cavallo"};
        int s = JOptionPane.showOptionDialog(vista, "Promozione", "Scegli", 0, 3, null, op, op[0]);
        String[] cd = {"Q", "T", "A", "C"};
        vista.griglia[r][c] = (s >= 0 ? cd[s] : "Q") + col;
    }

    private int[] trovaRe(String col) {
        for(int i=0; i<8; i++) for(int j=0; j<8; j++) if(vista.griglia[i][j].equals("R_"+col)) return new int[]{i, j};
        return new int[]{0,0};
    }

    private void finePartita(String msg) { timer.stop(); JOptionPane.showMessageDialog(vista, msg); vista.resetGioco(); }
    
    public void aggiornaStatoBottoni() {
        for (int r=0; r<8; r++) for (int c=0; c<8; c++) {
            if (rS == -1) {
                String p = vista.griglia[r][c];
                vista.bottoni[r][c].setEnabled(!p.equals("") && ((turnoB && p.endsWith("B")) || (!turnoB && p.endsWith("N"))));
            } else {
                boolean ok = mossaSicura(rS, cS, r, c);
                vista.bottoni[r][c].setEnabled(ok || (r == rS && c == cS));
                if (ok) vista.bottoni[r][c].setBackground(Color.YELLOW);
                if (r == rS && c == cS) vista.bottoni[r][c].setBackground(Color.CYAN);
            }
        }
    }
    private void aggMem(String p, int r, int c) {
        if (p.equals("R_B")) reBM = true; if (p.equals("R_N")) reNM = true;
        if (p.equals("T_B")) { if (c==0) tB1M=true; if (c==7) tB2M=true; }
        if (p.equals("T_N")) { if (c==0) tN1M=true; if (c==7) tN2M=true; }
    }
    public void reset() { turnoB=true; tB=300; tN=300; colEP=-1; reBM=reNM=tB1M=tB2M=tN1M=tN2M=false; timer.restart(); }
}


