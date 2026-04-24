package com.example.chess;

import javax.swing.*;

public class GameTimer {
    private ChessGame game;
    private JLabel label;
    private int whiteTimeRemaining = 600; // 10 minuti per giocatore
    private int blackTimeRemaining = 600;
    private Timer timer;
    private PieceColor currentPlayer;

    public GameTimer(ChessGame game, JLabel label) {
        this.game = game;
        this.label = label;
        this.currentPlayer = PieceColor.WHITE;
    }

    public void start() {
        timer = new Timer(1000, e -> {
            if(currentPlayer == PieceColor.WHITE) {
                whiteTimeRemaining--;
                if(whiteTimeRemaining <= 0) {
                    timer.stop();
                    game.endGameByTime(PieceColor.BLACK);
                }
            } else {
                blackTimeRemaining--;
                if(blackTimeRemaining <= 0) {
                    timer.stop();
                    game.endGameByTime(PieceColor.WHITE);
                }
            }
            updateDisplay();
        });
        timer.start();
        updateDisplay();
    }

    private void updateDisplay() {
        int minutes, seconds;
        if(currentPlayer == PieceColor.WHITE) {
            minutes = whiteTimeRemaining / 60;
            seconds = whiteTimeRemaining % 60;
        } else {
            minutes = blackTimeRemaining / 60;
            seconds = blackTimeRemaining % 60;
        }
        String timeStr = String.format("%02d:%02d", minutes, seconds);
        String playerStr = currentPlayer == PieceColor.WHITE ? "BIANCO" : "NERO";
        game.updateTimer(timeStr + " (" + playerStr + ")");
    }

    public void switchPlayer(PieceColor newPlayer) {
        this.currentPlayer = newPlayer;
        updateDisplay();
    }

    public void stop() {
        if(timer != null) {
            timer.stop();
        }
    }
}