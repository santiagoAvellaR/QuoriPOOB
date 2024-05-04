package src.domain;

import java.awt.*;

public class Peon {
    private String state;
    private int row;
    private int column;
    private Color color;
    private Board board;

    public Peon(String state, int row, int column, Color color) {
        this.row = row;
        this.column = column;
        this.color = color;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public void moveForward(){
    }
}
