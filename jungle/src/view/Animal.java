package view;
import model.PlayerColor;
import utils.pictureutils;
import javax.swing.*;
import java.awt.*;
public abstract class Animal extends JComponent {
    /**
     * This is the equivalent of the ChessPiece class,
     * but this class only cares how to draw Chess on ChessboardComponent
     */
        private PlayerColor owner;

        private boolean selected;
        private String name;

        public Animal(){};

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
}


