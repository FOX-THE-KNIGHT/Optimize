package com.whytail.optimize;

public class Function {
    private Point initialPoint;
    private double accuracy;
    private ElipseAxis EA1, EA2;
    public Function(double gr1[], double gr2[], double initialCoords[], double accuracy){
        this.initialPoint = new Point(initialCoords);
        this.EA1 = new ElipseAxis(gr1);
        this.EA2 = new ElipseAxis(gr2);
        this.accuracy = accuracy;
    }
    public double functionInvoke(double x, double y){
        return  Math.pow((x * EA1.getAX() + y * EA1.getAY() + EA1.getB()), 2.0) +  Math.pow((x * EA2.getAX() + y * EA2.getAY() + EA2.getB()), 2.0);
    }
    
    public double functionInvoke(Point p){
        return  Math.pow((p.getX() * EA1.getAX() + p.getY() * EA1.getAY() + EA1.getB()), 2.0) +  Math.pow((p.getX() * EA2.getAX() + p.getY() * EA2.getAY() + EA2.getB()), 2.0);
    }
    
    public double getEpsilon(){
        return accuracy;
    }
    public Point getInitialPoint(){
        return initialPoint;
    }
}

class ElipseAxis{
    private double ax, ay, b;
    public ElipseAxis(double ax, double ay, double b){
        this.ax = ax;
        this.ay = ay;
        this.b = b;
    }
    public ElipseAxis(double coefficients[]){
        this.ax = coefficients[0];
        this.ay = coefficients[1];
        this.b = coefficients[2];
    }
    //Setters
    public void setAX(double ax){
        this.ax = ax;
    }
    public void setAY(double ay){
        this.ay = ay;
    }
    public void setB(double b){
        this.b = b;
    }
    //Getters
    public double getAX(){
        return ax;
    }
    public double getAY(){
        return ay;
    }
    public double getB(){
        return b;
    }
}
