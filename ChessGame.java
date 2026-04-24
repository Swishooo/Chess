package com.example.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ChessGame extends JFrame {
    private Board board;
    private PieceButton[][] buttons = new PieceButton[8][8];
    private PieceColor currentPlayer = PieceColor.WHITE;
    private Position selected = null;
    private List<Position> possibleMoves = null;
    private JLabel timerLabel;
    private JLabel whiteCapturedLabel;
    private JLabel blackCapturedLabel;
    private JLabel playerLabel;
    private GameTimer gameTimer;
    private boolean gameOver = false;

    public ChessGame() {
        board = new Board();
        setTitle("Chess Game - 10 min per giocatore");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                buttons[i][j] = new PieceButton();
                buttons[i][j].addActionListener(new ButtonListener(i,j));
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(java.awt.Color.LIGHT_GRAY);
        playerLabel = new JLabel("Turno: BIANCO ⚪");
        playerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(playerLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        updateBoard();
        gameTimer = new GameTimer(this, timerLabel);
        gameTimer.start();

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.setBackground(java.awt.Color.LIGHT_GRAY);

        timerLabel = new JLabel("Tempo: 10:00 (BIANCO)");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(timerLabel);

        panel.add(new JSeparator(JSeparator.VERTICAL));

        JLabel whiteLbl = new JLabel("Pedine Bianche Mangiate:");
        whiteLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(whiteLbl);
        
        whiteCapturedLabel = new JLabel("Nessuno");
        whiteCapturedLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(whiteCapturedLabel);

        panel.add(new JSeparator(JSeparator.VERTICAL));

        JLabel blackLbl = new JLabel("Pedine Nere Mangiate:");
        blackLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(blackLbl);
        
        blackCapturedLabel = new JLabel("Nessuno");
        blackCapturedLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(blackCapturedLabel);

        return panel;
    }

    private void updateBoard() {
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                Piece p = board.getPiece(i, j);
                buttons[i][j].setPiece(p);
                buttons[i][j].setBackground((i+j)%2==0 ? java.awt.Color.WHITE : new java.awt.Color(200, 150, 100));
                
                // Evidenzia il pezzo selezionato
                if(selected != null && selected.row == i && selected.col == j) {
                    buttons[i][j].setSelected(true);
                } else {
                    buttons[i][j].setSelected(false);
                }
                
                // Evidenzia le mosse possibili in verde
                if(possibleMoves != null && isPossibleMove(i, j)) {
                    buttons[i][j].setPossibleMove(true);
                } else {
                    buttons[i][j].setPossibleMove(false);
                }
            }
        }

        whiteCapturedLabel.setText(board.getWhiteCapturedPieces());
        blackCapturedLabel.setText(board.getBlackCapturedPieces());
        playerLabel.setText("Turno: " + (currentPlayer == PieceColor.WHITE ? "BIANCO ⚪" : "NERO ⚫"));
    }

    private boolean isPossibleMove(int row, int col) {
        if(possibleMoves == null) return false;
        for(Position p : possibleMoves) {
            if(p.row == row && p.col == col) return true;
        }
        return false;
    }

    private class ButtonListener implements ActionListener {
        int row, col;
        public ButtonListener(int r, int c) { row=r; col=c; }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameOver) return;
            
            if(selected == null) {
                Piece p = board.getPiece(row, col);
                if(p != null && p.getColor() == currentPlayer) {
                    selected = new Position(row, col);
                    possibleMoves = p.getPossibleMoves(board, selected);
                    updateBoard();
                }
            } else {
                Position to = new Position(row, col);
                if(isValidMove(to)) {
                    Piece capturedPiece = board.getPiece(row, col);
                    board.setPiece(row, col, board.getPiece(selected.row, selected.col));
                    board.setPiece(selected.row, selected.col, null);
                    
                    if(capturedPiece != null) {
                        board.addCapturedPiece(capturedPiece);
                        if(capturedPiece.getType() == PieceType.KING) {
                            endGame(currentPlayer);
                        }
                    }
                    
                    selected = null;
                    possibleMoves = null;
                    currentPlayer = currentPlayer == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
                    gameTimer.switchPlayer(currentPlayer);
                    updateBoard();
                } else {
                    selected = null;
                    possibleMoves = null;
                    updateBoard();
                }
            }
        }
    }

    private boolean isValidMove(Position to) {
        if(possibleMoves == null) return false;
        for(Position p : possibleMoves) {
            if(p.row == to.row && p.col == to.col) return true;
        }
        return false;
    }

    public void updateTimer(String time) {
        timerLabel.setText("Tempo: " + time);
    }

    public void endGameByTime(PieceColor winner) {
        gameOver = true;
        gameTimer.stop();
        String winnerName = winner == PieceColor.WHITE ? "BIANCO" : "NERO";
        JOptionPane.showMessageDialog(this, 
            "TEMPO SCADUTO!\n\nVittoria di " + winnerName + "!", 
            "Game Over", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void endGame(PieceColor winner) {
        gameOver = true;
        gameTimer.stop();
        String winnerName = winner == PieceColor.WHITE ? "BIANCO" : "NERO";
        JOptionPane.showMessageDialog(this, 
            "RE CATTURATO!\n\nVittoria di " + winnerName + "!", 
            "Game Over", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGame game = new ChessGame();
            game.setVisible(true);
        });
    }
}