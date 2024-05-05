package src.domain;

import java.awt.*;

public class Long extends Barrier{

    public Long(Color color, boolean horizontal){
        super(color, horizontal, 3);
    }

    @Override
    public String getType() {
        return "Long";
    }

}
