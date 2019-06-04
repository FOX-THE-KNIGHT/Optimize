package com.whytail.optimize.algorithms;

import com.whytail.optimize.*;
import java.util.*;

public class SimplexMethod {
    private Function analyzeFunc;
    private Simplex startSimplex;
    private ArrayList<Simplex> simplexes;
    private ArrayList<ArrayList<Double>> functionsByVertexes;
    private Point bestPoint;
    private double edgeLength;

    public SimplexMethod(Function analyzFunc, double startLength, boolean isCenterOfMass) {
        this.analyzeFunc = analyzFunc;
        startSimplex = new Simplex(analyzFunc.getInitialPoint(), startLength, isCenterOfMass);
        simplexes = new ArrayList<>();
        edgeLength = startLength;
        functionsByVertexes = new ArrayList<>();
        bestPoint = null;
    }
    
    public void analyze(){
        while(edgeLength >= analyzeFunc.getEpsilon()){
            simplexes.add(startSimplex);
            Point localWorst = getWorstPoint(startSimplex);
            Point edgeVertexes[] = getEdgeVertexes(startSimplex, localWorst);
            Point newPoint = getNewPoint(edgeVertexes[0], edgeVertexes[1], localWorst);
            Point pointsOfNewSimplex[] = new Point[]{newPoint, edgeVertexes[0], edgeVertexes[1]};
            Simplex newSimplex = new Simplex(pointsOfNewSimplex, edgeLength);
            if(isWorstPoint(newSimplex, newPoint)){
                edgeLength /= 2;
                startSimplex = new Simplex(getBestPoint(newSimplex), edgeLength, false);
                continue;
            }
            startSimplex = new Simplex(newSimplex);
        }
        bestPoint = getBestPoint(startSimplex);
    }
    public Point getWorstPoint(Simplex s){
        ArrayList<Double> functionDatas = new ArrayList<>();
        Point worst = s.getX1();
        for(Point p : s.getSimplexVertexes()){
            functionDatas.add(analyzeFunc.functionInvoke(p));
            if(analyzeFunc.functionInvoke(p) > analyzeFunc.functionInvoke(worst)){
                worst = p;
            }
            
        }
        functionsByVertexes.add(functionDatas);
        return worst;
    }
    
     public Point[] getEdgeVertexes(Simplex s, Point worst){
        ArrayList<Point> result = new ArrayList<>();
        for(Point p : s.getSimplexVertexes()){
            if(analyzeFunc.functionInvoke(p) < analyzeFunc.functionInvoke(worst))
                result.add(p);
        }
        return result.toArray(new Point[result.size()]);
    }
     
    public Point getResultPoint(){
        return bestPoint;
    }
     
    public Point getNewPoint(Point a, Point b, Point worst){
        Point massCenter = new Point((a.getX() + b.getX())/2, (a.getY() + b.getY())/2);
        return new Point(massCenter.getX() + (massCenter.getX() - worst.getX()), massCenter.getY() + (massCenter.getY() - worst.getY()));
    }
    
    public Point getBestPoint(Simplex s){
        Point result = s.getX1();
        for(Point p : s.getSimplexVertexes()){
            if(analyzeFunc.functionInvoke(p) < analyzeFunc.functionInvoke(result))
                result = p;
        }
        return result;
    }
     
    public boolean isWorstPoint(Simplex s, Point possible){
        for(Point p : s.getSimplexVertexes()){
            if(analyzeFunc.functionInvoke(possible) < analyzeFunc.functionInvoke(p))
                return false;
        }
        return true;
    }
    
    public void printResult(){
        for(int i = 0; i < simplexes.size(); i ++){
            System.out.println("Симплекс " + i + " : " + simplexes.get(i) + "\nСо значениями функции");
            printFunctions(functionsByVertexes.get(i));
            System.out.println();
        }
        System.out.println("Лучшая точка - " + getResultPoint() + "f(best) = " + analyzeFunc.functionInvoke(getResultPoint()));
    }
    
    private void printFunctions(ArrayList<Double> values){
        for(int i = 0; i < values.size(); i++){
            System.out.print("f(" + i + ") = " + values.get(i) + " ");
        }
    }
}