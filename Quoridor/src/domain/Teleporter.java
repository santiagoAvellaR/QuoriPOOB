package src.domain;

import java.awt.Color;
import java.io.Serializable;

public class Teleporter extends Square implements Serializable {
    private Teleporter otherTeleporter;

    public Teleporter(int row, int column, Color color) {
        super(color, row, column);
        otherTeleporter = null;
    }

    public void setOtherTeleporter(Teleporter otherTeleporter) {
        this.otherTeleporter = otherTeleporter;
    }

    @Override
    public String getType(){return hasPeon() ? "TeleporterPeon" + peon.getPlayerNumber() : "Teleporter";}

    @Override
    public void applySpecialAction() throws QuoridorException{
        if (hasPeon()) {
            peon.setSquareType("Teleporter");
            peon.passThroughSquare("T");
            if (!otherTeleporter.hasPeon()) {
                otherTeleporter.setPeon(peon);
                peon.setPosition(otherTeleporter.getRow(), otherTeleporter.getColumn());
                peon.passThroughSquare("T");
                setPeon(null);
                throw new QuoridorException(QuoridorException.PEON_HAS_BEEN_TELEPORTED);
            }
        }
    }

}
