
package scacchi;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame finestra = new JFrame("Turno: BIANCO"); 
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.add(new Scacchiera(finestra)); 
        finestra.pack();
        finestra.setLocationRelativeTo(null);
        finestra.setVisible(true);
    }
}