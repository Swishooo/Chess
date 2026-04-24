package cheduri;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame finestra = new JFrame("Scacchi: Struttura Professionale");
        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.add(new Scacchiera());
        finestra.pack();
        finestra.setLocationRelativeTo(null);
        finestra.setVisible(true);
    }
}
