package src.domain;

import java.awt.*;
import java.io.Serializable;

public class Long extends Barrier implements Serializable {

    public Long(Color color, boolean horizontal){
        super(color, horizontal, 3);
    }

    @Override
    public String getType() {
        return "Long";
    }

}
