package com.example.chess;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece[][] board = new Piece[8][8];
    private List<Piece> whiteCaptured = new ArrayList<>();
    private List<Piece> blackCaptured = new ArrayList<>();

    public Board() {
        PieceType[] types = {PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK};
        for(int i=0; i<8; i++) {
            board[0][i] = new Piece(types[i], PieceColor.BLACK);
            board[1][i] = new Piece(PieceType.PAWN, PieceColor.BLACK);
            board[6][i] = new Piece(PieceType.PAWN, PieceColor.WHITE);
            board[7][i] = new Piece(types[i], PieceColor.WHITE);
        }
    }

    public Piece getPiece(int row, int col) {
        if(row < 0 || row >= 8 || col < 0 || col >= 8) return null;
        return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    public void addCapturedPiece(Piece piece) {
        if(piece.getColor() == PieceColor.WHITE) {
            whiteCaptured.add(piece);
        } else {
            blackCaptured.add(piece);
        }
    }

    public String getWhiteCapturedPieces() {
        StringBuilder sb = new StringBuilder();
        for(Piece p : whiteCaptured) {
            sb.append(getPieceSymbol(p)).append(" ");
        }
        return sb.toString().isEmpty() ? "Nessuno" : sb.toString();
    }

    public String getBlackCapturedPieces() {
        StringBuilder sb = new StringBuilder();
        for(Piece p : blackCaptured) {
            sb.append(getPieceSymbol(p)).append(" ");
        }
        return sb.toString().isEmpty() ? "Nessuno" : sb.toString();
    }

    private String getPieceSymbol(Piece piece) {
        switch(piece.getType()) {
            case KING: return "K";
            case QUEEN: return "Q";
            case ROOK: return "R";
            case BISHOP: return "B";
            case KNIGHT: return "N";
            case PAWN: return "P";
        }
        return "";
    }
}