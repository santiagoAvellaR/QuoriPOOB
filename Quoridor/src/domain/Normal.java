package src.domain;

import java.awt.*;

public class Normal extends Barrier{

    public Normal(Color color, boolean horizontal) {
        super(color, horizontal, 2);
    }

    @Override
    public String getType() {
        return "Normal";
    }
}
