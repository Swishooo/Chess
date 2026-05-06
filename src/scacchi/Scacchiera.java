package scacchi;
import javax.swing.*;
import java.awt.*;

public class Scacchiera extends JPanel {
    public JButton[][] bottoni = new JButton[8][8];
    public JFrame finestra; 
    public String[][] griglia = new String[8][8];

    public Scacchiera(JFrame f) {
        this.finestra = f;
        setLayout(new GridLayout(8, 8));
        Click gestore = new Click(this);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                bottoni[r][c] = new JButton();
                if((r + c) % 2 == 0) {
                	bottoni[r][c].setBackground(Color.white);
                }else {
                	bottoni[r][c].setBackground(Color.GRAY);
                }
                
                bottoni[r][c].setPreferredSize(new Dimension(60, 60)); 
                bottoni[r][c].addActionListener(gestore);
                add(bottoni[r][c]);
            }
        }
        caricaPezzi();
    }

    private void caricaPezzi() {
        String[] pezzi = {"T", "C", "A", "Q", "R", "A", "C", "T"};
        for (int i = 0; i < 8; i++) {
            bottoni[0][i].setText(pezzi[i] + "_N");
            bottoni[1][i].setText("P_N");
            bottoni[6][i].setText("P_B");
            bottoni[7][i].setText(pezzi[i] + "_B");
           
        } 
        for (int i=0; i<8; i++) {
                griglia[0][i]=pezzi[i]+"_N"; griglia[1][i]="P_N";
                griglia[6][i]="P_B"; griglia[7][i]=pezzi[i]+"_B";
                for (int j=2; j<6; j++) griglia[j][i]="";
            }
    }
}

