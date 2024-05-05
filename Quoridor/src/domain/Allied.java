package src.domain;

import java.awt.*;

public class Allied extends Barrier{
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
    public boolean isAllied(Color color){
        return alliedColor.equals(color);
    }

}
