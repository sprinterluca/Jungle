package view;

import com.sun.tools.javac.Main;
import controller.GameController;
import model.Chessboard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

import static utils.pictureutils.boardpic;
import static utils.pictureutils.icon;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;
    private PlayerColor color=PlayerColor.BLUE;

    private ChessboardComponent chessboardComponent;
    private GameController controller;
    private GameController saveControlll;
    public void setSaveControl(GameController controller){this.saveControlll=controller;}

    public void setController(GameController controll) {
        this.controller = controll;
    }

    public ChessGameFrame(int width, int height) {
        setTitle("斗兽棋"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addLabel();
//        add(playLabel(color.name()));
//        remove(playLabel(color.name()));

        addHelloButton();
        addLoadButton();
        addSaveButton();
        addMessageButton();

        addBackground();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("斗兽棋");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("宋体", Font.BOLD, 50));
        add(statusLabel);
    }

//    public JLabel playLabel(String s){
//        if (controller!=null) s=controller.currentPlayer.name();
//        else s=color.name();
//        JLabel statusLabel = new JLabel("Current Player: "+s);
//        statusLabel.setLocation(HEIGTH-50, HEIGTH / 10 + 420);
//        statusLabel.setSize(300, 30);
//        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 25));
//        return statusLabel;
//    }
//    public void addplayLabel(){
//        if (controller!=null) color=controller.currentPlayer;
//        JLabel statusLabel = new JLabel("Current Player: "+color.name());
//        statusLabel.setLocation(HEIGTH-50, HEIGTH / 10 + 420);
//        statusLabel.setSize(300, 30);
//        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 25));
//        add(statusLabel);
//    }
    private void addBackground(){
        JLabel Background = new JLabel(icon);
        Background.setLocation(0,0);
        Background.setSize(getWidth()-1,getHeight()-1);
        add(Background);

    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    public void addHelloButton() {
        JButton button = new JButton("Restart Game");
        button.addActionListener(e -> {
            controller.initialize();
            controller.setCurrentPlayer(PlayerColor.BLUE);
            chessboardComponent.repaint();
        });

        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addSaveButton(){
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            String path=JOptionPane.showInputDialog(this,"Enter Your Game Name");
            saveControlll.saveGame("/Users/86158/Desktop//jungle/load/"+path+".ser");

        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            String path = JOptionPane.showInputDialog(this,"Input Game here");
            saveControlll.loadGame("/Users/86158/Desktop//jungle/load/"+path+".ser");
            chessboardComponent.repaint();
        });
    }
    private void addMessageButton() {
        JButton button = new JButton("Condition");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            color=controller.currentPlayer;
            JOptionPane.showMessageDialog(null,
                    "Current Player:"+color.name()+"\nRound Number:"+controller.num,
                    "Current Condition",JOptionPane.PLAIN_MESSAGE);
        });
    }

}
