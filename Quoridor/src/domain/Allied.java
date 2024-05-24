package src.domain;

import java.awt.*;
import java.io.Serializable;

public class Allied extends Barrier implements Serializable {
    Color alliedColor;

    public Allied(Color color, boolean horizontal) {
        super(color, horizontal, 2);
        alliedColor = color;
    }

    @Override
    public String getType() {
        return "Allied";
    }

    @Override
    public boolean peonCanPassThrough(Color color){
        return alliedColor.equals(color);
    }

}
