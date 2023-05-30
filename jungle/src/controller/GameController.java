package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;

import javax.swing.*;
import java.io.*;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener,Serializable{
    public int num=1;
    public Chessboard model;
    public ChessboardComponent view;
    public PlayerColor currentPlayer;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessGameFrame colorchange;

    public void setCurrentPlayer(PlayerColor currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void setColorchange(ChessGameFrame colorchange) {
        this.colorchange = colorchange;
    }

    public void initialize() {
        model.initializeAll();
        view.initialAll();
        model.initPieces();
        view.initiateChessComponent(model);
        view.repaint();
        this.currentPlayer = PlayerColor.BLUE;
        this.num=1;
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        num++;
    }

    public void saveGame(String path){
        saveControl saveAndLoad=new saveControl(this.model,this.num,this.currentPlayer);
//        Chessboard SAVE=new Chessboard();
//        SAVE=this.model;
        try
        {
            File file=new File("/Users/86158/Desktop//jungle/load");
            if (!file.exists()) file.mkdirs();
            FileOutputStream ft = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(ft);
            out.writeObject(saveAndLoad);
            out.close();
            ft.close();
            System.out.print("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void loadGame(String path){
        try{
            FileInputStream ip=new FileInputStream(path);
            ObjectInputStream oi=new ObjectInputStream(ip);
//            saveControl load=(saveControl) oi.readObject();
            saveControl LOAD=(saveControl) oi.readObject();
            this.model.initializeAll();
            this.view.initialAll();
            this.model=LOAD.saveModel;
            this.num=LOAD.countnum;
            this.currentPlayer=LOAD.curt;
            this.view.initiateChessComponent(this.model);
            this.view.repaint();
            oi.close();
            ip.close();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }
    private boolean win() {
        // TODO: Check the board if there is a winner

        if (model.getGrid()[0][3].getPiece()!=null) {
            if (currentPlayer.equals(PlayerColor.RED)) {
                if (model.getGrid()[0][3].getPiece().getOwner().equals(currentPlayer)) return true;
            }
        }
        if (model.getGrid()[8][3].getPiece()!=null) {
            if (currentPlayer.equals(PlayerColor.BLUE)) {
                if (model.getGrid()[8][3].getPiece().getOwner().equals(currentPlayer)) return true;
            }
        }
        boolean b=true;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (model.getGrid()[i][j].getPiece()!=null) {
                    if (currentPlayer.equals(PlayerColor.RED)) {
                        if (model.getGrid()[i][j].getPiece().getOwner().equals(PlayerColor.BLUE)) return false;
                    }
                    if (currentPlayer.equals(PlayerColor.BLUE)) {
                        if (model.getGrid()[i][j].getPiece().getOwner().equals(PlayerColor.RED)) return false;
                    }
                }
            }
        }
        return b;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            if (win()) {
                System.out.printf("%s wins!!!",currentPlayer.name());
                ImageIcon icon = new ImageIcon("resource/victory.png");
                JOptionPane.showMessageDialog(null, currentPlayer.name()+" wins!!!",
                        "Congratulations", JOptionPane.INFORMATION_MESSAGE,icon);
            }
            swapColor();
            view.repaint();
            // TODO: if the chess enter Dens or Traps and so on
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, Animal component) {
        System.out.println(component.getName());
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else if (model.isValidCapture(selectedPoint, point)){
            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;

            swapColor();
            view.repaint();
        }
        if (win()) System.out.printf("%s wins!!!",currentPlayer.name());
        // TODO: Implement capture function
    }

}
