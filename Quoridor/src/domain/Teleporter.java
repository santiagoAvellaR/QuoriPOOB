package src.domain;

import java.awt.Color;

public class Teleporter extends Square{
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
        if(hasPeon()){
            otherTeleporter.setPeon(peon);
            peon.setPosition(otherTeleporter.getRow(), otherTeleporter.getColumn());
            setPeon(null);
            throw new QuoridorException(QuoridorException.PEON_HAS_BEEN_TELEPORTED);
        }
    }

}
