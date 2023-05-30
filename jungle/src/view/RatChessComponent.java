package view;
import model.PlayerColor;
import utils.pictureutils;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class RatChessComponent extends Animal {
    private PlayerColor owner;

    private boolean selected;

    public RatChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(owner.getColor());
        g.fillRect(0,0,getWidth()-1,getHeight()-1);
        g.drawImage(pictureutils.rat,0,0,getWidth()-1,getHeight()-1,this);

        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.BLACK);
            g.drawOval(0, 0, getWidth() , getHeight());
        }

    }
}

