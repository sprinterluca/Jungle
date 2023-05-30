package view;

import utils.pictureutils;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color background;
    private String name;

    public CellComponent(String name,Color background, Point location, int size) {
        this.name=name;
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        switch (this.name) {
            case "river" -> g.drawImage(pictureutils.river, 0, 0, getWidth() , getHeight() , this);
            case "cave" -> {
                g.setColor(background);
                g.fillRect(0,0,getWidth(),getHeight());
                g.drawImage(pictureutils.cave, 0, 0, getWidth() , getHeight() , this);
            }
            case "trap" -> {
                g.setColor(background);
                g.fillRect(0,0,getWidth(),getHeight());
                g.drawImage(pictureutils.trap, 0, 0, getWidth(), getHeight() , this);
            }
            default -> g.drawImage(pictureutils.grass, 0, 0, getWidth() , getHeight() , this);
        }
    }
}
