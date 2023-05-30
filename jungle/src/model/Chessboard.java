package model;

import java.awt.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard implements Serializable {
    private Cell[][] grid;
    private final Set<Cell> riverCell = new HashSet<>();
    private final Set<Cell> caveCellred = new HashSet<>();
    private final Set<Cell> caveCellblue = new HashSet<>();
    private final Set<Cell> trapCellred = new HashSet<>();
    private final Set<Cell> trapCellblue = new HashSet<>();

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }
    public void initializeAll(){
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j]!=null) grid[i][j].setPiece(null);
            }
        }
    }

    public void initPieces() {
        grid[0][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));


        riverCell.add(grid[3][1]);
        riverCell.add(grid[3][2]);
        riverCell.add(grid[4][1]);
        riverCell.add(grid[4][2]);
        riverCell.add(grid[5][1]);
        riverCell.add(grid[5][2]);

        riverCell.add(grid[3][4]);
        riverCell.add(grid[3][5]);
        riverCell.add(grid[4][4]);
        riverCell.add(grid[4][5]);
        riverCell.add(grid[5][4]);
        riverCell.add(grid[5][5]);

        trapCellblue.add(grid[0][2]);
        trapCellblue.add(grid[0][4]);
        trapCellblue.add(grid[1][3]);
        trapCellred.add(grid[8][2]);
        trapCellred.add(grid[8][4]);
        trapCellred.add(grid[7][3]);

        caveCellblue.add(grid[0][3]);
        caveCellred.add(grid[8][3]);
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        } else setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        } else setChessPiece(dest, removeChessPiece(src));
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidJump(ChessboardPoint src, ChessboardPoint dest) {
        boolean isValidJump = true;
        if (dest.getRow() == src.getRow()) {
            if (dest.getCol() >= src.getCol()) {
                for (int i = src.getCol() + 1; i < dest.getCol(); i++) {
                    if (grid[src.getRow()][i].isPieceExist()) return false;
                    else if (!riverCell.contains(grid[src.getRow()][i])) isValidJump = false;
                }
            } else {
                for (int i = dest.getCol() + 1; i < src.getCol(); i++) {
                    if (grid[src.getRow()][i].isPieceExist()) return false;
                    else if (!riverCell.contains(grid[src.getRow()][i])) isValidJump = false;
                }
            }
        } else if (dest.getCol() == src.getCol()) {
            if (dest.getRow() >= src.getRow()) {
                for (int i = src.getRow() + 1; i < dest.getRow(); i++) {
                    if (grid[i][src.getCol()].isPieceExist()) return false;
                    else if (!riverCell.contains(grid[i][src.getCol()])) isValidJump = false;
                }
            } else {
                for (int i = dest.getRow() + 1; i < src.getRow(); i++) {
                    if (grid[i][src.getCol()].isPieceExist()) return false;
                    else if (!riverCell.contains(grid[i][src.getCol()])) isValidJump = false;
                }
            }
        }else isValidJump=false;
        return isValidJump;
    }

        public boolean isValidMove (ChessboardPoint src, ChessboardPoint dest){
            if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) return false;
            else if (getChessPieceAt(src).getName().equals("Tiger") || getChessPieceAt(src).getName().equals("Lion")) {
                if (riverCell.contains(getGridAt(dest))) return false;
                if (calculateDistance(src, dest) >= 2) {
                    if (isValidJump(src, dest)) return true;
                }
            }
            else if (getChessPieceAt(src).getName().equals("Rat")) {
                return calculateDistance(src, dest) == 1;
            } else if (riverCell.contains(getGridAt(dest))) return false;
             return calculateDistance(src, dest) == 1;
        }


        public boolean isValidCapture (ChessboardPoint src, ChessboardPoint dest){
            // TODO:Fix this method
            boolean i = false;
            if (getChessPieceAt(src).getName().equals("Rat")) {
                if (getChessPieceAt(src).getOwner().equals(PlayerColor.BLUE)&&trapCellblue.contains(getGridAt(dest))&&(!getChessPieceOwner(src).equals(getChessPieceOwner(dest))))
                    return calculateDistance(src, dest) == 1;
                if (getChessPieceAt(src).getOwner().equals(PlayerColor.RED)&&trapCellred.contains(getGridAt(dest))&&(!getChessPieceOwner(src).equals(getChessPieceOwner(dest))))
                    return calculateDistance(src, dest) == 1;
                if (riverCell.contains(getGridAt(src))) return false;
                if (getChessPieceAt(src).canCapture(getChessPieceAt(dest))) return calculateDistance(src, dest) == 1;
            }
            if (getChessPieceAt(src).getOwner().equals(PlayerColor.BLUE)&&trapCellblue.contains(getGridAt(dest))&&(!getChessPieceOwner(src).equals(getChessPieceOwner(dest))))
                return calculateDistance(src, dest) == 1;
            if (getChessPieceAt(src).getOwner().equals(PlayerColor.RED)&&trapCellred.contains(getGridAt(dest))&&(!getChessPieceOwner(src).equals(getChessPieceOwner(dest))))
                return calculateDistance(src, dest) == 1;
            if (getChessPieceAt(src).canCapture(getChessPieceAt(dest))&&(!riverCell.contains(getGridAt(dest)))) i = true;
            if (getChessPieceAt(src).getName().equals("Tiger") || getChessPieceAt(src).getName().equals("Lion")) {
                if (riverCell.contains(getGridAt(dest))) i= false;
                if (calculateDistance(src, dest) >= 2) {
                    if (isValidJump(src, dest)) if (i) return true;
                }
            }

            if (riverCell.contains(getGridAt(dest))) return false;
            return i&&calculateDistance(src, dest) == 1;
        }

}