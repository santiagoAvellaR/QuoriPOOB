package src.domain;

import java.awt.Color;

public class Transporter extends Square{

    public Transporter(int row, int column, Color color){
        super(color, row, column);
    }

    @Override
    public String getType(){return hasPeon() ? "TransporterPeon" + peon.getPlayerNumber() : "Transporter";}

    @Override
    public void applySpecialAction() {
        if (hasPeon()) {
            peon.setSquareType("Transporter");
            peon.passThroughSquare("TR");
        }
    }

}