package com.example.chess;

import javax.swing.*;
import java.awt.*;

public class PieceButton extends JButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Piece piece;
    private boolean isSelected;
    private boolean isPossibleMove;
    
    public PieceButton() {
        setFocusPainted(false);
        setOpaque(true);
        piece = null;
        isSelected = false;
        isPossibleMove = false;
    }
    
    public void setPiece(Piece p) {
        this.piece = p;
        repaint();
    }
    
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint();
    }
    
    public void setPossibleMove(boolean possible) {
        this.isPossibleMove = possible;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(isPossibleMove) {
            g.setColor(new Color(100, 200, 100, 150));
            g.fillOval(getWidth()/3, getHeight()/3, getWidth()/3, getHeight()/3);
        }
        
        if(piece != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setFont(new Font("Segoe UI Symbol", Font.BOLD, 40));
            
            String icon = getPieceIcon(piece);
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(icon)) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            
            if(piece.getColor() == PieceColor.WHITE) {
                g2d.setColor(new Color(80, 80, 80));
                g2d.drawString(icon, x-2, y-2);
                g2d.drawString(icon, x+2, y-2);
                g2d.drawString(icon, x-2, y+2);
                g2d.drawString(icon, x+2, y+2);
                
                g2d.setColor(Color.WHITE);
                g2d.drawString(icon, x, y);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.drawString(icon, x-2, y-2);
                g2d.drawString(icon, x+2, y-2);
                g2d.drawString(icon, x-2, y+2);
                g2d.drawString(icon, x+2, y+2);
                
                g2d.setColor(Color.BLACK);
                g2d.drawString(icon, x, y);
            }
        }
        
        if(isSelected) {
            g.setColor(Color.YELLOW);
            ((Graphics2D) g).setStroke(new BasicStroke(3));
            ((Graphics2D)g).drawRect(2, 2, getWidth()-4, getHeight()-4);
        }
    }
    
    private String getPieceIcon(Piece p) {
        if(p.getColor() == PieceColor.WHITE) {
            switch(p.getType()) {
                case KING: return "♔";
                case QUEEN: return "♕";
                case ROOK: return "♖";
                case BISHOP: return "♗";
                case KNIGHT: return "♘";
                case PAWN: return "♙";
            }
        } else {
            switch(p.getType()) {
                case KING: return "♚";
                case QUEEN: return "♛";
                case ROOK: return "♜";
                case BISHOP: return "♝";
                case KNIGHT: return "♞";
                case PAWN: return "♟";
            }
        }
        return "";
    }
}