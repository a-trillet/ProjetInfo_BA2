import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Point {
    public DoubleProperty x = new SimpleDoubleProperty();
    public DoubleProperty y = new SimpleDoubleProperty();

    public  Point (double x, double y){
        this.x.setValue(x);
        this.y.setValue(y);
    }

    public double distance(Point p){
        double d =  Math.sqrt(Math.pow((x.getValue()-p.x.getValue()), 2) + Math.pow((y.getValue() - p.y.getValue()), 2));
        return d;
    }

    public double getX(){
        return x.getValue();
    }

    public void setX(double v){
        x.setValue(v);
    }

    public void setY(double v){
        y.setValue(v);
    }

    public double getY(){
        return y.getValue();
    }

}
