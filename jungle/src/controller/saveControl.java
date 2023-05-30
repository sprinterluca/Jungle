package controller;

import model.Chessboard;
import model.PlayerColor;
import view.ChessboardComponent;

import java.io.Serializable;

public class saveControl implements Serializable {
    public int countnum=1;
    public PlayerColor curt;
    public Chessboard saveModel;
    public saveControl( Chessboard model,int num,PlayerColor curt) {
        this.saveModel=model;
        this.countnum=num;
        this.curt=curt;
    }


}
