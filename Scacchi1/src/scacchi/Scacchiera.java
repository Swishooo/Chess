package scacchi;



import javax.swing.*;
import java.awt.*;

public class Scacchiera extends JPanel {
    public JButton[][] bottoni = new JButton[8][8];
    public JFrame finestra; 
    public String[][] griglia = new String[8][8];

    ImageIcon T_N = new ImageIcon("img/T_N.png");
    ImageIcon T_B = new ImageIcon("img/T_B.png");
    ImageIcon C_B = new ImageIcon("img/C_B.png");
    ImageIcon C_N = new ImageIcon("img/C_N.png");
    ImageIcon A_B = new ImageIcon("img/A_B.png");
    ImageIcon A_N = new ImageIcon("img/A_N.png");
    ImageIcon Q_B = new ImageIcon("img/Q_B.png");
    ImageIcon Q_N = new ImageIcon("img/Q_N.png");
    ImageIcon R_B = new ImageIcon("img/R_B.png");
    ImageIcon R_N = new ImageIcon("img/R_N.png");
    ImageIcon P_B = new ImageIcon("img/P_B.png");
    ImageIcon P_N = new ImageIcon("img/P_N.png");
    
    public Scacchiera(JFrame f) {
        this.finestra = f;
        setLayout(new GridLayout(8, 8));
        Click gestore = new Click(this);
        inizializzaGriglia();
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
    private void inizializzaGriglia() {
    	
	   
	    String[] pezzi = {"T", "C", "A", "Q", "R", "A", "C", "T"};
	
	    for (int i = 0; i < 8; i++) {
	        griglia[0][i] = pezzi[i] + "_N";
	        griglia[1][i] = "P_N";
	        griglia[6][i] = "P_B";
	        griglia[7][i] = pezzi[i] + "_B";
	    }
	    for(int i =2; i <6 ; i++) {
	    	for(int j =0; j<8;j++) {
	    		griglia[i][j]="";
	    	}
	    }
	  
    }
    

    private void caricaPezzi() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                
                if(!griglia[r][c].equals("")){
                ImageIcon icon = getIcon(griglia[r][c]);
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon f = new ImageIcon(img);
                bottoni[r][c].setIcon(f);
                bottoni[r][c].setText(griglia[r][c]);
                bottoni[r][c].setHorizontalAlignment(SwingConstants.CENTER);
                bottoni[r][c].setVerticalAlignment(SwingConstants.CENTER);
                bottoni[r][c].setVerticalTextPosition(SwingConstants.BOTTOM);
                bottoni[r][c].setHorizontalTextPosition(SwingConstants.CENTER);
                bottoni[r][c].setIconTextGap(0);
                bottoni[r][c].setFont(new Font("Arial", Font.PLAIN, 0));
                }
            }
        }
    }
    private ImageIcon getIcon(String codice) {
        if (codice == null || codice.equals("")) return null;

        switch (codice) {
            case "T_N": return T_N;
            case "T_B": return T_B;
            case "C_N": return C_N;
            case "C_B": return C_B;
            case "A_N": return A_N;
            case "A_B": return A_B;
            case "Q_N": return Q_N;
            case "Q_B": return Q_B;
            case "R_N": return R_N;
            case "R_B": return R_B;
            case "P_N": return P_N;
            case "P_B": return P_B;
            default: return null;
        }
    }
}