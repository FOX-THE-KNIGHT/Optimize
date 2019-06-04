package com.whytail.optimize.algorithms;

import com.whytail.optimize.*;
import java.util.*;

public class NulderMid {
    private Function analyzeFunc;
    private Simplex startSimplex;
    private double edgeLength, alpha, beta;
    private Point worst, best, result;
    
    public NulderMid(Function analyzeFunc, double edgeLength){
        this.analyzeFunc = analyzeFunc;
        startSimplex = new Simplex(analyzeFunc.getInitialPoint(), edgeLength, true);
        worst = new Point(analyzeFunc.getInitialPoint());
        best = new Point(this.analyzeFunc.getInitialPoint());
        result = null;
        alpha = 1;
        beta = 0.5;
    }
    
    //getters
    public HashMap<String, Point> getWorstBestPoint(Simplex s){
        Point worst = s.getX1();
        Point best = s.getX1();
        HashMap<String, Point> worstBest = new HashMap<String, Point>();
        for(Point p : s.getSimplexVertexes()){
            if(analyzeFunc.functionInvoke(p) > analyzeFunc.functionInvoke(worst)){
                worst = p;
            } else if(analyzeFunc.functionInvoke(p) < analyzeFunc.functionInvoke(best)){
                best = p;
            }
        }
        worstBest.put("worst", worst);
        worstBest.put("best", best);
        return worstBest;
    }
    //main algorithm
    public void analyze(){
        do{
            HashMap<String, Point> wb = getWorstBestPoint(startSimplex);
            this.worst = wb.get("worst");
            this.best = wb.get("best"); 
            Point[] edges = _getEdgePoints(startSimplex);
            Point centerOfMass = _getCenterOfMassPoint(edges);
            Point newPoint = _getNewPoint(centerOfMass);
            Point createPoint = null;
            if(analyzeFunc.functionInvoke(newPoint) < analyzeFunc.functionInvoke(best)){
                //Если значение функции в отображенной точке newPoint меньше чем в лучшей в предыдущем симплексе best, то делаем растяжение
                beta = 2;
                Point pointFromExpand = _getMutatedPoint(newPoint, centerOfMass);
                //Если после растяжения точка pointFromExpand стала лучше, то тогда она и будет новой точкой симплекса, иначе новой точкой будет newPoint
                createPoint = (analyzeFunc.functionInvoke(pointFromExpand) < analyzeFunc.functionInvoke(newPoint)) ? pointFromExpand : newPoint;
                startSimplex = new Simplex(new Point[]{createPoint, edges[0], edges[1]}, edgeLength);
            }
            else if(analyzeFunc.functionInvoke(worst) > analyzeFunc.functionInvoke(newPoint) && _isWorstThanOthers(edges, newPoint)){
                beta = 0.5;
                createPoint = _getMutatedPoint(newPoint, centerOfMass);
                startSimplex = new Simplex(new Point[]{createPoint, edges[0], edges[1]}, edgeLength);
            } else if(_isWorstThanWas(startSimplex, newPoint)){
                edgeLength /= 2;
                Point x2 = new Point(best.getX() + 0.5*(edges[0].getX() - best.getX()), best.getY() + 0.5*(edges[0].getY() - best.getY()));
                Point x3 = new Point(best.getX() + 0.5*(edges[1].getX() - best.getX()), best.getY() + 0.5*(edges[1].getY() - best.getY()));
                startSimplex = new Simplex(new Point[]{best, x2, x3}, edgeLength);
            }
            
        } while(_calculateStopValue(startSimplex) >= analyzeFunc.getEpsilon());
        result = best;
    }
    //printer
    public void printResult(){
        System.out.println("Проведен метод Нальдера-Мида");
        System.out.println("Лучшая точка - " + result + ", f(x, y) = " + analyzeFunc.functionInvoke(result));
    }
    private void _printFValues(ArrayList<Double> values){
        for(int i = 0; i < values.size(); i++){
            System.out.print("f(" + i + ") = " + values.get(i) + " ");
        }
        System.out.println("________________________________________");
    }
    //private algorithms
    private double _calculateStopValue(Simplex s){
        Point[] edges = _getEdgePoints(s);
        Point centerOfMass = _getCenterOfMassPoint(edges);
        double summ = 0;
        for(Point p : edges){
            summ += Math.pow(Math.abs(analyzeFunc.functionInvoke(p) - analyzeFunc.functionInvoke(centerOfMass)), 2);
        }
        return Math.sqrt(summ/3);
    }
    //Private getters
    private Point _getMutatedPoint(Point newPoint, Point centerOfMass){
        double x = beta * newPoint.getX() + (1 - beta) * centerOfMass.getX();
        double y = beta * newPoint.getY() + (1 - beta) * centerOfMass.getY();
        return new Point(x, y);
    }
    private Point[] _getEdgePoints(Simplex s){
        ArrayList<Point> edges = new ArrayList<>();
        for(Point p : s.getSimplexVertexes()){
            if(analyzeFunc.functionInvoke(p) < analyzeFunc.functionInvoke(worst)){
                edges.add(p);
            }
        }
        return edges.toArray(new Point[edges.size()]);
    }
    private Point _getCenterOfMassPoint(Point[] points){
        return new Point((points[0].getX() + points[1].getX())/2, (points[0].getY() + points[1].getY())/2);
    }
    private Point _getNewPoint(Point centerOfMass){
        double newX = centerOfMass.getX() + alpha * (centerOfMass.getX() - worst.getX());
        double newY = centerOfMass.getY() + alpha * (centerOfMass.getY() - worst.getY());
        return new Point(newX, newY);
    }
    //Закрытые логические утверждения
    private boolean _isWorstThanOthers(Point[] points, Point supposed){
        int successCounter = 0;
        for(Point p : points)
            if(analyzeFunc.functionInvoke(supposed) > analyzeFunc.functionInvoke(p))
                successCounter++;
        return successCounter == points.length;
    }
    private boolean _isWorstThanWas(Simplex s, Point supposed){
        for(Point p : s.getSimplexVertexes())
            if(analyzeFunc.functionInvoke(p) > analyzeFunc.functionInvoke(supposed))
                return false;
        return true;
    }
}