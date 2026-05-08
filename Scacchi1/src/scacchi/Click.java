package scacchi;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Click implements ActionListener {
    Scacchiera s;
    private boolean turnoB = true, reBM = false, reNM = false, tB1M = false, tB2M = false, tN1M = false, tN2M = false;
    Regole rgl = new Regole();
    int r1 =-1, c1 =-1;
    String pezzoInMano = "";
    boolean turnoBianco = true;
	private String vincitore;
	private  JButton bottones = new JButton();
    public Click(Scacchiera s) {
    		this.s = s; 
    	}

    public void actionPerformed(ActionEvent e) {
        bottones = (JButton) e.getSource();
        int r2 = -1,c2 = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (s.bottoni[i][j] == bottones) {
                	r2 = i; 
                	c2 = j; 
                }
            }
        }

        if (pezzoInMano.equals("")) {
            String pezzo = bottones.getText();
            if (turnoBianco && pezzo.endsWith("_B")) {
                pezzoInMano = pezzo;
                r1 = r2; 
                c1 = c2;
                evidenziaMosse(r1, c1, pezzoInMano);
            } else if (turnoBianco == false && pezzo.endsWith("_N")) {
                pezzoInMano = pezzo;
                r1 = r2; 
                c1 = c2;
                evidenziaMosse(r1, c1, pezzoInMano);
            }
        } else {
            
            if (rgl.mossaValida(pezzoInMano, bottones.getText(), r1, c1, r2, c2, s.griglia)&& mossaSicura(r1, c1, r2, c2)){ 
                
            

                if (bottones.getText().equals("R_N") || bottones.getText().equals("R_B")) {
                	 if(turnoBianco) {
                		 String vincitore = "BIANCO";
                     }else {
                    	 String vincitore = "NERO";
                     }
                    
                    javax.swing.JOptionPane.showMessageDialog(s, "Il Re è caduto! Vince il " + vincitore);
                    System.exit(0); 
                }
                ImageIcon icona = (ImageIcon) s.bottoni[r1][c1].getIcon();
                s.bottoni[r2][c2].setText(pezzoInMano);
                s.bottoni[r2][c2].setIcon(icona);
                s.bottoni[r1][c1].setText("");
                s.bottoni[r1][c1].setIcon(null);;
                s.griglia[r2][c2] = pezzoInMano;
                s.griglia[r1][c1] = "";
                s.bottoni[r2][c2].setHorizontalAlignment(SwingConstants.CENTER);
                s.bottoni[r2][c2].setVerticalAlignment(SwingConstants.CENTER);
                s.bottoni[r2][c2].setVerticalTextPosition(SwingConstants.BOTTOM);
             
                s.bottoni[r2][c2].setHorizontalTextPosition(SwingConstants.CENTER);
                s.bottoni[r2][c2].setIconTextGap(0);
                
            
             s.bottoni[r2][c2].setFont(new Font("Arial", Font.PLAIN, 0));
             for (int i = 0; i < 8; i++) {
                 for (int j = 0; j < 8; j++) {
                 	if ((i + j) % 2 == 0) {
                         s.bottoni[i][j].setBackground(Color.WHITE);
                     } else {
                         s.bottoni[i][j].setBackground(Color.GRAY);
                     }
                     s.bottoni[i][j].setOpaque(true);
                 }
             }
                 
                
             
                if(turnoBianco) {
                	turnoBianco = false;
                }else {
                	 turnoBianco = true;
                }
                
                if(turnoBianco) {
                	s.finestra.setTitle("Turno: " +  "BIANCO" );
                }else {
                	s.finestra.setTitle("Turno: " +  "NERO" );
                }
                
            }
            pezzoInMano = "";
        }
    }
    public boolean mossaSicura(int r1, int c1, int r2, int c2) {
        String pM = s.griglia[r1][c1]; 
        String pD = s.griglia[r2][c2];
        s.griglia[r2][c2] = pM; 
        s.griglia[r1][c1] = "";
        String coloreMio = turnoBianco ? "B" : "N";
        int[] rePos = trovaRe(coloreMio);
        boolean sicuro = !rgl.casellaSottoAttacco(rePos[0], rePos[1], coloreMio, s.griglia);
        s.griglia[r1][c1] = pM; 
        s.griglia[r2][c2] = pD;

        return sicuro;
    }
    private int[] trovaRe(String col) {
        for(int i=0; i<8; i++) {
        	for(int j=0; j<8; j++) {
        		if(s.griglia[i][j].equals("R_"+col)) {
        			return new int[]{i, j};
        		}
        	}
        }
        return new int[]{0,0};
    }
    
    
    public void evidenziaMosse(int r1, int c1, String pezzo) {
    	 for (int i = 0; i < 8; i++) {
             for (int j = 0; j < 8; j++) {
             	if ((i + j) % 2 == 0) {
                     s.bottoni[i][j].setBackground(Color.WHITE);
                 } else {
                     s.bottoni[i][j].setBackground(Color.GRAY);
                 }
                 s.bottoni[i][j].setOpaque(true);
             }
         }
            int mossePossibili=0;
        
        if (pezzo.equals("")) {
        	return;
        }
        for (int r2 = 0; r2 < 8; r2++) {
            for (int c2 = 0; c2 < 8; c2++) {
               if (rgl.mossaValida(pezzo, s.bottoni[r2][c2].getText(), r1, c1, r2, c2, s.griglia)&& mossaSicura(r1, c1, r2, c2))  {
                    s.bottoni[r2][c2].setBackground(Color.PINK);
                    mossePossibili++;
                }
            }
        }
        
		if (mossePossibili == 0) {
            
            s.bottoni[r1][c1].setBackground(Color.RED);
            javax.swing.JOptionPane.showMessageDialog(s, "Il Re è sotto scacco " + vincitore);
            System.exit(0);
             }
    }
}

        
       


