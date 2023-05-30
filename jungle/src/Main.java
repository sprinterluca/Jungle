import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;


import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            Chessboard board=new Chessboard();
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), board);
            gameController.setColorchange(mainFrame);
            mainFrame.setController(gameController);
            mainFrame.setSaveControl(gameController);
            mainFrame.setVisible(true);
        });
    }
}
