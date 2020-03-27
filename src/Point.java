public class Point {
    // public DoubleProperty x = new SimpleDoubleProperty();
    // public DoubleProperty y = new SimpleDoubleProperty();
    public double x;
    public double y;

    public  Point (double x, double y){
        // this.x.setValue(x);
        // this.y.setValue(y);
        this.x = x;
        this.y = y;
    }

    public double distance(Point p){
        // double d =  Math.sqrt(Math.pow((x.getValue()-p.x.getValue()), 2) + Math.pow((y.getValue() - p.y.getValue()), 2));
        double d =  Math.sqrt(Math.pow((x-p.x), 2) + Math.pow((y - p.y), 2));
        return d;
    }

    public double getX(){
        return x; // x.getValue();
    }

    public void setX(double v){
        x = v; //.setValue(v);
    }


    public void setY(double v){
        y = v; //.setValue(v);
    }

    public double getY(){
        return y; //.getValue();
    }

}