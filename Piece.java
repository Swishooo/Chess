package com.example.chess;

import java.util.List;
import java.util.ArrayList;

public class Piece {
    private PieceType type;
    private PieceColor color;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() { return type; }
    public PieceColor getColor() { return color; }

    public List<Position> getPossibleMoves(Board board, Position pos) {
        List<Position> moves = new ArrayList<>();
        switch(type) {
            case PAWN -> {
                int dir = color == PieceColor.WHITE ? -1 : 1;
                int startRow = color == PieceColor.WHITE ? 6 : 1;
                
                // Mossa avanti di 1
                int r = pos.row + dir;
                if(r >= 0 && r < 8 && board.getPiece(r, pos.col) == null) {
                    moves.add(new Position(r, pos.col));
                    
                    // Mossa avanti di 2 dalla posizione iniziale
                    if(pos.row == startRow) {
                        int r2 = pos.row + 2*dir;
                        if(board.getPiece(r2, pos.col) == null) {
                            moves.add(new Position(r2, pos.col));
                        }
                    }
                }
                
                // Cattura diagonale
                for(int dc = -1; dc <= 1; dc += 2) {
                    int c = pos.col + dc;
                    if(c >= 0 && c < 8 && r >= 0 && r < 8) {
                        Piece p = board.getPiece(r, c);
                        if(p != null && p.getColor() != color) {
                            moves.add(new Position(r, c));
                        }
                    }
                }
            }
            case ROOK -> addLineMoves(board, pos, moves, new int[]{-1,0,1,0}, new int[]{0,1,0,-1});
            case BISHOP -> addLineMoves(board, pos, moves, new int[]{-1,-1,1,1}, new int[]{-1,1,-1,1});
            case QUEEN -> addLineMoves(board, pos, moves, new int[]{-1,-1,-1,0,0,1,1,1}, new int[]{-1,0,1,-1,1,-1,0,1});
            case KNIGHT -> {
                int[] dr = {-2,-2,-1,-1,1,1,2,2};
                int[] dc = {-1,1,-2,2,-2,2,-1,1};
                for(int i = 0; i < 8; i++) {
                    int nr = pos.row + dr[i];
                    int nc = pos.col + dc[i];
                    if(nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                        Piece p = board.getPiece(nr, nc);
                        if(p == null || p.getColor() != color) {
                            moves.add(new Position(nr, nc));
                        }
                    }
                }
            }
            case KING -> {
                for(int dr = -1; dr <= 1; dr++) {
                    for(int dc = -1; dc <= 1; dc++) {
                        if(dr == 0 && dc == 0) continue;
                        int nr = pos.row + dr;
                        int nc = pos.col + dc;
                        if(nr >= 0 && nr < 8 && nc >= 0 && nc < 8) {
                            Piece p = board.getPiece(nr, nc);
                            if(p == null || p.getColor() != color) {
                                moves.add(new Position(nr, nc));
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }
    
    private void addLineMoves(Board board, Position pos, List<Position> moves, int[] dr, int[] dc) {
        for(int d = 0; d < dr.length; d++) {
            for(int i = 1; i < 8; i++) {
                int nr = pos.row + dr[d] * i;
                int nc = pos.col + dc[d] * i;
                if(nr < 0 || nr >= 8 || nc < 0 || nc >= 8) break;
                Piece p = board.getPiece(nr, nc);
                if(p == null) {
                    moves.add(new Position(nr, nc));
                } else {
                    if(p.getColor() != color) moves.add(new Position(nr, nc));
                    break;
                }
            }
        }
    }
}