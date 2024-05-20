package src.domain;

import java.awt.*;
import java.io.Serializable;

public class Normal extends Barrier implements Serializable {

    public Normal(Color color, boolean horizontal) {
        super(color, horizontal, 2);
    }

    @Override
    public String getType() {
        return "Normal";
    }
}
