package src.domain;

import java.awt.Color;

public class Teleporter extends Square{
    private Teleporter otherTeleporter;
    private Peon peon;

    public Teleporter(int row, int column, Color color) {
        super(color, row, column);
        otherTeleporter = null;
        peon = null;
    }

    public void setOtherTeleporter(Teleporter otherTeleporter) {
        this.otherTeleporter = otherTeleporter;
    }

    @Override
    public String getType(){return hasPeon() ? "TeleporterPeon" : "Teleporter";}

    @Override
    public void applySpecialAction() throws QuoridorException{
        if(peon != null){
            int newRow = otherTeleporter.getRow();
            int newColumn = otherTeleporter.getColumn();
            peon.move(newRow, newColumn);
            throw new QuoridorException(QuoridorException.PEON_HAS_BEEN_TELEPORTED);
        }
    }

}
