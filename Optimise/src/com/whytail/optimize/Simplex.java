package com.whytail.optimize;

public class Simplex{
    private Point x1;
    private Point x2;
    private Point x3;
    private Point simplexVertex[];
    private double edgeLength;
    
    public Simplex(Point initPoint, double edgeLength, boolean initialPointCenterOfMass){
       if(initialPointCenterOfMass){
            x1 = new Point(initPoint.getX() - edgeLength/2, initPoint.getY() - edgeLength/(2 * Math.sqrt(3)));
            x2 = new Point(initPoint.getX() * edgeLength/2, initPoint.getY() - edgeLength/(2 * Math.sqrt(3)));
            x3 = new Point(initPoint.getX(), initPoint.getY() + edgeLength/Math.sqrt(3));
       } else {
           double position1 = (Math.sqrt(3) + 1)/(Math.sqrt(2)*2);
           double position2 = (Math.sqrt(3) - 1)/(Math.sqrt(2)*2);
           x1 = new Point(initPoint.getX(), initPoint.getY());
           x2 = new Point(initPoint.getX() + position1, initPoint.getY() + position2);
           x3 = new Point(initPoint.getX() + position2, initPoint.getY() + position1);
       }
       simplexVertex = new Point[]{x1, x2, x3};
       this.edgeLength = edgeLength;
    }

    public Simplex(Point points[], double edgeLength) {
        x1 = new Point(points[0]);
        x2 = new Point(points[1]);
        x3 = new Point(points[2]);
        simplexVertex = new Point[]{x1, x2, x3};
        this.edgeLength = edgeLength;
    }
    
    public Simplex(Simplex s){
        x1 = new Point(s.getX1());
        x2 = new Point(s.getX2());
        x3 = new Point(s.getX3());
        this.edgeLength = s.getLength();
        simplexVertex = new Point[]{x1, x2, x3};
    }
    
    public Point getX1(){
        return x1;
    }
    public Point getX2(){
        return x2;
    }
    public Point getX3(){
        return x3;
    }
    public double getLength(){
        return edgeLength;
    }
    
    public Point[] getSimplexVertexes(){
        return simplexVertex;
    }
    
    public void setLength(double newLength){
        this.edgeLength = newLength;
    }
    
    @Override
    public String toString(){
        return "Simplex[x1:" + x1 + ", x2:" + x2 + ", x3:" + x3 +"]";
    }
}