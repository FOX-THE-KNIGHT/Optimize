package com.whytail.optimize;

public class Point {
    private double x, y;
    public Point(){
        this.x = this.y = 0;
    }
    public Point(Point p){
        x = p.getX();
        y = p.getY();
    }
    
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Point(double coords[]){
        this.x = coords[0];
        this.y = coords[1];
    }
    //Setters
    public void setX(double x){
        this.x = x; 
    }
    public void setY(double y){
        this.y = y; 
    }
    //Getters
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    
    @Override
    public String toString(){
        return "Point[X: " + getX() + ", Y: " + getY() + "]";
    }
}
