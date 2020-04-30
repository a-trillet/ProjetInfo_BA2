import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.Serializable;

public class Point implements Serializable {
    private static final long serialVersionUID = 1L;
    public double x ;
    public double y;

    public  Point (double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distance(Point p){
        double d =  Math.sqrt(Math.pow((x-p.x), 2) + Math.pow((y - p.y), 2));
        return d;
    }

    public double getX(){
        return x;
    }

    public void setX(double v){
        x = v;
    }

    public void setY(double v){ y = v; }

    public double getY(){
        return y;
    }

}
